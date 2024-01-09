package Algorithm;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class IteratedGreedy {

    int maxNOImprove;
    int tempThreshold;
    int tabuActivator;
    int tabuCounter;
    int maxIteration;
    int maxTime;
    int minT;
    int maxD;
    int[] success;
    int iteration;
    int noImprove;
    int tabuMaxNOImprove;
    int tabuMaxIter;
    int tabuMaxNeighbor;
    int TT;
    int alpha;

    public IteratedGreedy(int maxTime, int maxNOImprove, int tempThreshold, int tabuActivator, int maxIteration, int minT, int maxD, int alpha,
                          int tabuMaxNOImprove, int tabuMaxIter, int tabuMaxNeighbor, int TT,
                          int[] success) {

        this.maxNOImprove = maxNOImprove;
        this.tempThreshold = tempThreshold;
        this.tabuActivator = tabuActivator;
        this.maxIteration = maxIteration;
        this.minT = minT;
        this.maxD = maxD;
        this.success = success;
        this.noImprove = 0;
        this.maxTime = maxTime;

        this.tabuMaxNOImprove = tabuMaxNOImprove;
        this.tabuMaxIter = tabuMaxIter;
        this.tabuMaxNeighbor = tabuMaxNeighbor;
        this.TT = TT;
        this.alpha = alpha;


        iteration = 1;
        tabuCounter = 0;
    }

    public Solution solve(int[][] P, int WE[], int[] WT, int[] de, int[] dt, int J, int I, int F) throws ExecutionException, InterruptedException {
        NEH neh = new NEH();
        boolean terminate = J <= F;
        int d;

        // maxD = Math.min(J - 1, maxD);

        Solution currentSol = neh.initialSol(P, WE, WT, de, dt, J, I, F);

        Solution bestSol = currentSol.copy();
        Solution newSol = currentSol.copy();

        double T = bestSol.getObj();

        double time = System.currentTimeMillis();
        while (!terminate) {

            d = (int) ((double) ((maxIteration - iteration) / (double) maxIteration) * maxD) + 1;
            d = Math.max(d, 4);
            d = maxD;
//          ==============================  Iterated Greedy  ==============================
            Solution partialSol = destroySol(currentSol, d, P, WE, WT, de, dt, J, I);
            newSol = constructSol(partialSol, P, WE, WT, de, dt, J, I);

//          ==============================  Local Search  ==============================

            if (tabuCounter > tabuActivator) {
                double currentTime = System.currentTimeMillis();
//                newSol = Search.tabu(currentTime,maxTime,tabuMaxNOImprove, tabuMaxIter, tabuMaxNeighbor, TT, alpha,
//                        bestSol, d, P, WE, WT, de, dt, J, I);
            newSol = Search.local(bestSol, P, WE, WT, de, dt, J, I);

                tabuCounter = 0;
            }
            if (newSol.getObj() < bestSol.getObj()) {
                bestSol = newSol.copy();
                currentSol = newSol.copy();
                noImprove = 0;
                tabuCounter = 0;
            } else {
                noImprove++;
                tabuCounter++;
                if (T == 0) {
                    terminate = true;
                } else if (BaseFunctions.metroPoliceCriteria(newSol.getObj(), bestSol.getObj(), bestSol.getObj() * .2)) {
                    currentSol = newSol.copy();
                }
            }
            if (noImprove > tempThreshold) {
                T *= .9;
            }
            double elapsedTime = (System.currentTimeMillis()-time)/1e3;
            if (iteration > maxIteration || noImprove > maxNOImprove || elapsedTime > maxTime) {
                terminate = true;
            }
            System.out.println("Iter: " + iteration + "\tObjective: " + bestSol.getObj() + "\t" + d);

            iteration++;
        }

        System.out.println(bestSol.toString());
        System.out.println("Elapsed Time :" + (System.currentTimeMillis() - time) / 1000);
        return bestSol;

    }

    private Solution destroySol(Solution sol, int d, int[][] P, int[] de, int[] dt, int[] WE, int[] WT, int J,
                                int I) {

//        TODO: active all the destroy operators
        int activeOperator = RandomFunctions.randInt(0, 4);
        activeOperator=0;
        System.out.print("\t destroy=" + activeOperator);
        Solution partialSol = new Solution();

        if (activeOperator == 0) {
            partialSol = Destruction.randomSelection(sol, d);

        } else if (activeOperator == 1) {
            partialSol = Destruction.flowLineSelection(sol, d, P, de, dt, WE, WT, J, I);

        } else if (activeOperator == 2) {

            partialSol = Destruction.greedySelection(sol, d, P, de, dt, WE, WT, J, I);

        } else if (activeOperator == 3) {
            partialSol = Destruction.blockSelection(sol);

        } else if (activeOperator == 4) {
            partialSol = Destruction.criticalPathSelection(sol, d, P, de, dt, WE, WT, J, I);
        }
        return partialSol;

    }

    private Solution constructSol(Solution sol, int[][] P, int[] WE, int[] WT, int[] de, int[] dt, int J,
                                  int I) {
//        TODO: active all the construction operators
        Solution newSol = new Solution();
        int activeOperator = RandomFunctions.randInt(0, 3);
         activeOperator = 2;
        System.out.print("\t construct=" + activeOperator + "\t");
        if (activeOperator == 0) {
            newSol = Construction.removedSequence(sol, P, WE, WT, de, dt, J, I);
        } else if (activeOperator == 1) {
            newSol = Construction.randomSelection(sol, P, WE, WT, de, dt, J, I);
        } else if (activeOperator == 2) {
            newSol = Construction.greedySelection(sol, P, WE, WT, de, dt, J, I);
        } else if (activeOperator == 3) {
            int K = RandomFunctions.randInt(2, 3);
            newSol = Construction.k_regret(K, sol, P, WE, WT, de, dt, J, I);
        }
        return newSol;
    }
}

