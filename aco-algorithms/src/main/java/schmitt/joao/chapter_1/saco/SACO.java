package schmitt.joao.chapter_1.saco;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.List;

/**
 * S-ACO (Simple Ant Colony Optimization)
 * <p>
 * S-ACO é um algoritmo que se adapta ao comportamento real das formigas para busca de uma solução de caminho mais
 * curto. Para cada aresta (i,j) do grafo G = (N,A) nós associamos uma variável τij chamada trilha de feromônios
 * artificiais. As trilhas de feromônios são lidas e escritas pelas formigas. O montante (intensidade) de feromônio é
 * proporcional a sua utilidade, como estimado pelas formigas, de usar aquele arco para construir boas soluções.
 */
public class SACO {

    private static int bestPath[];

    private static int countLongPheromone = 0;

    /**
     * Double bridge - percentage of trials that the pheromone was higher for the longer path
     * <p>
     * ant population size (m)  1     2     4     8     16     32     64     128     256     512
     * without path length      46    50    43    39    25     20     5      0       0       0
     * with path length         32    28    34    33    11     4      0      0       0       0
     */

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Parameters.timeSize = 1000;
            Parameters.evaporation = 0.01;
            Parameters.antPopSize = 5;
            Parameters.alpha = 2.0;
            Parameters.pheromoneByPathLength = true;
            execute();
        }
        System.out.println("Longer = " + countLongPheromone);
    }

    public static void execute() {

        //Parameters.configureDoubleBridge();
        Parameters.configureExtendedDoubleBridge();
        //Parameters.configureGenericGraph();

        Ant[] ants = new Ant[Parameters.antPopSize];
        for (int a = 0; a < ants.length; a++) {
            ants[a] = new Ant();
            ants[a].setCurrentNode(Parameters.source);
        }

        initializePheromone();

        for (int t = 0; t < Parameters.timeSize; t++) {
            for (int a = 0; a < ants.length; a++) {
                evaporatePheromone(ants[a]);
                moveAnt(ants[a]);
            }
        }

        System.out.print("Best (" + (bestPath.length - 1) + ") = ");
        for (int i = 0; i < bestPath.length; i++) {
            System.out.print(bestPath[i]);
            if (i < bestPath.length - 1) {
                System.out.print("->");
            }
        }

        double shortest = Parameters.pheromone[0][9];
        double longest = Parameters.pheromone[0][1];
        if (shortest < longest) {
            countLongPheromone++;
        }
        System.out.println(" - Longer (" + longest + ") Shorter (" + shortest + ")");
    }

    public static void printPheromone(int t) {
        System.out.print("Time t = " + t + " - ");
        for(int i = 0; i < Parameters.pheromone.length; i++) {
            for(int j = 0; j < Parameters.pheromone[i].length; j++) {
                if(Parameters.pheromone[i][j] > 0)
                    System.out.print(i+"->"+j+"="+Parameters.pheromone[i][j]+" - ");
            }
        }
        System.out.println();
    }

    public static void moveAnt(Ant ant) {
        //Find next node
        if (ant.getTargetNode() == -1) {
            int nextNode = getNextNode(ant.getCurrentNode(), ant.getPredecessor());
            ant.setTargetNode(nextNode);
        }
        //Move in direction
        if (ant.getDistance() < Parameters.graph[ant.getCurrentNode()][ant.getTargetNode()]) {
            ant.setDistance(ant.getDistance() + Parameters.antSpeed);
        }
        //If was achieved the target
        if (ant.getDistance() >= Parameters.graph[ant.getCurrentNode()][ant.getTargetNode()]) {
            if (ant.getTargetNode() == Parameters.destination) {
                ant.addNode(ant.getCurrentNode());
                ant.addNode(ant.getTargetNode());
                calculatePheromone(ant.getOptimizedPath());
                ant.setCurrentNode(Parameters.source);
                ant.setTargetNode(-1);
                ant.clear();
            } else {
                ant.addNode(ant.getCurrentNode());
                ant.setCurrentNode(ant.getTargetNode());
                ant.setTargetNode(-1);
            }
        }
    }


    public static void evaporatePheromone(Ant ant) {
        int path[] = ant.getOptimizedPath();
        for(int i = 0; i < path.length - 1; i++) {
            Parameters.pheromone[path[i]][path[i + 1]] = (1.0 - Parameters.evaporation) * Parameters.pheromone[path[i]][path[i + 1]];
            Parameters.pheromone[path[i + 1]][path[i]] = Parameters.pheromone[path[i]][path[i + 1]];
        }

    }

    public static void calculatePheromone(int[] path) {
        double pheromone = 1.0;
        if (Parameters.pheromoneByPathLength) {
            pheromone = 1.0 / path.length;
        }
        if (bestPath == null || path.length < bestPath.length) {
            bestPath = path;
        }
        for (int n = 0; n < path.length - 1; n++) {
            Parameters.pheromone[path[n]][path[n + 1]] += pheromone;
            Parameters.pheromone[path[n + 1]][path[n]] += pheromone;
        }
    }

    /**
     * Atribui para cada arco um montante mínimo de feromônio. Ex: τij = 1, ∀(i, j) ∈ A.
     */
    public static void initializePheromone() {
        for (int i = 0; i < Parameters.graph.length; i++) {
            for (int j = 0; j < Parameters.graph[i].length; j++) {
                if (Parameters.graph[i][j] > 0.0) {
                    Parameters.pheromone[i][j] = Parameters.pheromoneMin;
                }
            }
        }
    }

    /**
     * Quando localizado no nó i, uma formiga k usa as trilhas de feromônio τij para computar a probabilidade de
     * escolher j como proximo nó.
     */
    public static int getNextNode(int i, int predecessor) {
        double totalSum = 0.0;
        double[] probabilities = new double[Parameters.N];
        for (int j = 0; j < Parameters.N; j++) {
            if (Parameters.graph[i][j] > 0.0 && j != predecessor) {
                totalSum += Math.pow(Parameters.pheromone[i][j], Parameters.alpha);
                probabilities[j] = Math.pow(Parameters.pheromone[i][j], Parameters.alpha);
            } else {
                probabilities[j] = -1;
            }
        }
        double cumSum = 0.0;
        for (int j = 0; j < Parameters.N; j++) {
            if (probabilities[j] >= 0) {
                probabilities[j] = probabilities[j] / totalSum;
                probabilities[j] += cumSum;
                cumSum = probabilities[j];
            }
        }
        double rand = Math.random();
        for (int j = 0; j < Parameters.N; j++) {
            if (probabilities[j] >= 0 && rand < probabilities[j]) {
                return j;
            }
        }
        return predecessor;
    }


}
