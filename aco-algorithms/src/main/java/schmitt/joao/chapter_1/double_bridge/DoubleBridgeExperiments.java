package schmitt.joao.chapter_1.double_bridge;

import java.util.ArrayList;
import java.util.List;

public class DoubleBridgeExperiments {

    /**
     * A comunicação entre várias espécies é baseada no feromônio. Enquanto
     * caminham do ninho para a fonte de comida e vice-versa, as formigas
     * deixam trilhas de ferômonios no chão, formando um trilha de ferômonios.
     *
     * Deneubourg, foram os primeiros a usar uma ponte dupla para conectar o ninho
     * das formigas a uma fonte de comida. Eles rodaram vários experimentos variando
     * a taxa r = ll / ls entre as duas pontes, onde ll representa o tamanho da ponte
     * mais larga e ls representa o tamanho da ponte mais curta.
     *
     * Propoem um modelo estocástico simples que adequadamente descreve a dinâmica da colônio de formigas
     * como observada no experimenta das duas pontes.
     *
     * Neste modelo psi formigas por segundo atravessam a ponte em cada direção a uma velocidade constante
     * de v cm/s, depositando uma unidade de ferômonio no ramo.
     * Dado os tamanhos ls e ll (em centímetros) dos ramos menor e maior. Um formiga escolhendo o menor
     * ramo irá precisar de ts = ls / v segundos para atravesar a ponte, enquanto uma formiga escolhendo
     * o maior ramo irá precisar r * ts segundos, onde r = ll/ls.
     *
     * A probabilidade Pia(t) para que um formiga chegando ao ponto de decisão i E {1,2} selecione um ramo
     * a E {s,l}, onde s e l denotam o menor e o maior ramo, no instante t é configurado como função do
     * montante total de feromônio phiia(t) no ramo, que é proporcional ao número de formigas que usaram
     * aquele ramo até o tempo t. Por exemplo, a probabilidade de escolher o menor ramo phiia(t) é dado por
     *
     * Pis(t) = (ts + phis(t))^a / ((ts + phis(t))^a + (ts + phil(t))^a)            (1.1)
     *
     * onde a forma funcional da equação (1.1), tal qual o valor de a = 2, foi derivada dos experimentos
     * da seguida de trilha.
     *
     * O modelo assume que o montante de feromonio é proporcional ao número de formigas que usaram aquele ramo
     * no passado, em outras palavras não é considerado a evaporação do feromonio.
     *
     * As equações diferencias que descrevem a estocásticidade do sistema são:
     *
     * dphiis/dt = psi*Pjs(t - ts) + psi*Pis(t)     (i = 1, j = 2; i = 2, j = 1)    (1.2)
     * dphiil/dt = psi*Pjl(t - r * ts) + psi*Pil(t) (i = 1, j = 2; i = 2, j = 1)    (1.3)
     *
     * A equação (1.2) pode ser lida como: a variação instantanea, no tempo t, do feromonio no ramo
     * s e no ponto de decisão i é dado pelo fluxo de formigas psi, assumido constante, multiplicado pela
     * probabilidade de escolher o menor ramo no ponto de decisão j no tempo t - ts mais o fluxo de formigas
     *  multiplicado pela probabilidade do ramo mais curto no ponto de decisão i no tempo t. A constante ts
     *  representa o delay de tempo que é necessário para as formigas atravessarem o ramo mais curto. A
     *  equação (1.3) expressa o mesmo para o ramo longo, exceto que nesse caso o delay de tempo é dado por
     *  r * ts.
     *
     *  A dinâmica do sistema definido por essas equações fui simulada usando simulação Monte Carlo. Os resultados
     *  dos dois experimentos consistiram de 1000 simulações cada e os valores das taxas foram r = 1 e r = 2. Pode
     *  ser observado que quando esses ramos tem o mesmo tamanho (r = 1) as formigas convergem a usar um ou outro com
     *  probabilidade igual. Controversiamente, quando um ramo é duas vezes maior que o outro, a maioria das formigas
     *  escolhem o branch mais curto.
     *
     *  Neste modelo as formigas depositam feromonio tanto na ida quanto na volta. Este é um comportamente necessário para
     *  convergencia do sistema.
     */


    public static void main(String[] args) {

        int aMax = 10;
        int sMax = 1000;
        int tMax = 1000;
        int cS = 0;
        int cL = 0;
        double ls = 1.0;
        double ll = 1.0;
        double v = 1.0;
        double ts = ls / v;
        double tl = ts * (ll / ls);
        double alpha = 2.0;

        for(int s = 0; s < sMax; s++) {

            double antCounter[] = new double[aMax];
            int antPath[] = new int[aMax];
            double pS = 0.0;
            double pL = 0.0;

            for(int t = 0; t < tMax; t++) {
                double prob = Math.pow(ts + pS, alpha) / (Math.pow(ts + pS, alpha) + Math.pow(ts + pL, alpha));
                for(int a = 0; a < aMax; a++) {
                    if(antCounter[a] <= 0) {
                        if(Math.random() < prob) {
                            antCounter[a] = ts;
                            antPath[a] = 0;
                        } else {
                            antCounter[a] = tl;
                            antPath[a] = 1;
                        }
                    }
                }
                for(int a = 0; a < aMax; a++) {
                    antCounter[a] -= v;
                    if(antCounter[a] == 0) {
                        if(antPath[a] == 0) {
                            pS++;
                        } else {
                            pL++;
                        }
                    }
                }
            }

            if(pS > pL) {
                cS++;
            } else {
                cL++;
            }

        }

        System.out.printf("Short = %d and Long = %d", cS, cL);
    }

}
