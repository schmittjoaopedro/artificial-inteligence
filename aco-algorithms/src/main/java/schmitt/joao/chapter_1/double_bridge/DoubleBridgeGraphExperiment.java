package schmitt.joao.chapter_1.double_bridge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 09/09/17.
 */
public class DoubleBridgeGraphExperiment {

    public static int b1 = 0;
    public static int b2 = 0;

    public static void main(String[] args) {
        for(int s = 0; s < 1000; s++) {
            execute();
        }
        System.out.println("Short = " + b1 + "\tLonger = " + b2);
    }

    public static void execute() {

        Vertex food = new Vertex();
        food.setSourceFood(true);
        Vertex nest = new Vertex();
        nest.setNest(true);
        Vertex point1 = new Vertex();
        Vertex point2 = new Vertex();

        int antPopSize = 10;
        int antSpeed = 5;
        int timeMax = 1000;

        Bridge bridge1 = new Bridge("Nest", nest, point1, 10);
        Bridge bridge2 = new Bridge("Short", point1, point2, 10);
        Bridge bridge3 = new Bridge("Long", point1, point2, 20);
        Bridge bridge4 = new Bridge("Food", point2, food, 10);

        Ant[] ants = new Ant[antPopSize];
        for(int a = 0; a < antPopSize; a++) {
            ants[a] = new Ant(antSpeed);
            ants[a].setCurrentVertex(nest);
        }

        for(int t = 0; t < timeMax; t++) {
            for(int a = 0; a < antPopSize; a++) {
                moveAnt(ants[a]);
            }
        }
        System.out.printf("B1 = %.2f\tB2 = %.2f\tB3 = %.2f\tB4 = %.2f\n",
                bridge1.getPheromone(),
                bridge2.getPheromone(),
                bridge3.getPheromone(),
                bridge4.getPheromone());

        if(bridge2.getPheromone() > bridge3.getPheromone()) {
            b1++;
        } else {
            b2++;
        }
    }

    public static void moveAnt(Ant ant) {
        //Find next vertex if not contains
        if(ant.getCurrentBridge() == null) {
            Bridge nextBridge = getNextBridge(ant, ant.getCurrentVertex());
            ant.getVertices().add(ant.getCurrentVertex());
            ant.setCurrentBridge(nextBridge);
            ant.setCurrentVertex(nextBridge.getWiredVertex(ant.getCurrentVertex()));
        }
        //Move in direction one step
        if(ant.getAntWalked() < ant.getCurrentBridge().getSize()) {
            ant.setAntWalked(ant.getAntWalked() + ant.getAntSpeed());
        }
        //If was achieved the target
        if(ant.getAntWalked() >= ant.getCurrentBridge().getSize()) {
            Vertex currVertex = ant.getCurrentVertex();
            Bridge currBridge = ant.getCurrentBridge();
            currBridge.setPheromone(currBridge.getPheromone() + 1);
            if(currVertex.isSourceFood() || currVertex.isNest()) {
                ant.clear();
                ant.setCurrentVertex(currVertex);
                ant.setFoundFood(currVertex.isSourceFood());
            } else {
                ant.getBridges().add(ant.getCurrentBridge());
                ant.setCurrentBridge(null);
            }
        }
    }

    public static Bridge getNextBridge(Ant ant, Vertex current) {
        List<Bridge> bridges = new ArrayList<Bridge>();
        double pheromoneSum = 0.0;
        for(Bridge bridge : current.getBridges()) {
            if(!ant.getVertices().contains(bridge.getWiredVertex(current))) {
                bridges.add(bridge);
                pheromoneSum += bridge.getPheromone();
            }
        }
        double p[] = new double[bridges.size()];
        double cumSum = 0.0;
        for(int i = 0; i < bridges.size(); i++) {
            cumSum += bridges.get(i).getPheromone() / pheromoneSum;
            p[i] = cumSum;
        }
        double rand = Math.random();
        for (int j = 0; j < p.length; j++) {
            if (rand <= p[j]) {
                return bridges.get(j);
            }
        }
        return null;
    }

}
