package Algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.Math.*;

public class Search {
    public static Solution local(Solution sol, int[][] P, int[] WE, int[] WT, int[] de, int[] dt, int J, int I) {

        Solution bestSol = sol.copy();
        for (int i = 0; i < 10; i++) {

            Solution newSol = singleInsertion(bestSol, P, WE, WT, de, dt, J, I);
            while (newSol.getObj() < bestSol.getObj()) {
                bestSol = newSol.copy();
                newSol = singleInsertion(bestSol, P, WE, WT, de, dt, J, I);
            }

            newSol = blockInsertion(bestSol, P, WE, WT, de, dt, J, I);
            while (newSol.getObj() < bestSol.getObj()) {
                bestSol = newSol.copy();
                newSol = blockInsertion(bestSol, P, WE, WT, de, dt, J, I);
            }

            newSol = exchange(bestSol, P, WE, WT, de, dt, J, I);
            while (newSol.getObj() < bestSol.getObj()) {
                bestSol = newSol.copy();
                newSol = exchange(bestSol, P, WE, WT, de, dt, J, I);
            }
        }

        return bestSol;
    }

    private static Solution singleInsertion(Solution sol, int[][] P, int[] WE, int[] WT, int[] de, int[] dt, int J, int I) {

        Solution newSol = sol.copy();
        Solution bestSol = sol.copy();
        List<Integer> feasibleFactories = new ArrayList<>();
        int F = sol.getSeq().size();

        for (int f = 0; f < F; f++) {
            if (sol.getSeqF(f).size() > 0) {
                feasibleFactories.add(f);
            }
        }

        int fInd = RandomFunctions.randInt(0, feasibleFactories.size() - 1);

        int chosenFactory = feasibleFactories.get(fInd);
        int chosenJobInd = RandomFunctions.randInt(0, sol.getSeqF(chosenFactory).size() - 1);
        int chosenJob = sol.getSeqF(chosenFactory).get(chosenJobInd);
        newSol.removeJob(chosenFactory, chosenJob);
        bestSol = Construction.singleInsertion(chosenJob, newSol, P, WE, WT, de, dt, J, I);
        return bestSol;
    }

    private static Solution blockInsertion(Solution sol, int[][] P, int[] WE, int[] WT, int[] de, int[] dt, int J, int I) {
        Solution newSol = sol.copy();
        Solution bestSol = sol.copy();
        List<Integer> feasibleFactories = new ArrayList<>();
        int F = sol.getSeq().size();

        for (int f = 0; f < F; f++) {
            if (sol.getSeqF(f).size() > 0) {
                feasibleFactories.add(f);
            }
        }

        int fInd = RandomFunctions.randInt(0, feasibleFactories.size() - 1);

        int chosenFactory = feasibleFactories.get(fInd);
        List<Integer> block = BaseFunctions.getBlock(sol.getSeqF(chosenFactory));
        newSol.removeBlockJob(chosenFactory, block);
        for (int f = 0; f < F; f++) {
            for (int ind = 0; ind < newSol.getSeqF(f).size() + 1; ind++) {

                newSol.insertBlockIndex(f, ind, block);
                newSol.evaluate(P, WE, WT, de, dt, J, I);
                if (newSol.getObj() < bestSol.getObj()) {
                    bestSol = newSol.copy();
                }
                newSol.removeBlockJob(f, block);
            }
        }
        return bestSol;
    }

    private static Solution exchange(Solution sol, int[][] P, int[] WE, int[] WT, int[] de, int[] dt, int J, int I) {
        Solution newSol = sol.copy();

        List<Integer> feasibleFactories = new ArrayList<>();
        int F = sol.getSeq().size();

        for (int f = 0; f < F; f++) {
            if (!sol.getSeqF(f).isEmpty()) {
                feasibleFactories.add(f);
            }
        }

        int ind1;
        int ind2;
        int index1 =RandomFunctions.randInt(0, feasibleFactories.size() - 1);
        int index2 =RandomFunctions.randInt(0, feasibleFactories.size() - 1);

        int f1 = feasibleFactories.get(index1);
        int f2 = feasibleFactories.get(index2);
        if (f1 == f2) {
            if (sol.getSeqF(f1).size() >= 2) {
                int[] pos = RandomFunctions.Choice(sol.getSeqF(f1).size(), 2);
                ind1 = pos[0];
                ind2 = pos[1];
            } else {
                return newSol;
            }
        } else {
            ind1 = RandomFunctions.randInt(0, sol.getSeqF(f1).size() - 1);
            ind2 = RandomFunctions.randInt(0, sol.getSeqF(f2).size() - 1);
        }

        int job1 = sol.getSeqF(f1).get(ind1);
        int job2 = sol.getSeqF(f2).get(ind2);

        newSol.removeJobIndex(f1, ind1);
        newSol.insertJobIndex(f1, ind1, job2);
        newSol.removeJobIndex(f2, ind2);
        newSol.insertJobIndex(f2, ind2, job1);

        newSol.evaluate(P, WE, WT, de, dt, J, I);
        return newSol;
    }

    public static Solution tabu(double currentTime,int maxTime, int tabuMaxNOImprove, int tabuMaxIter, int tabuMaxNeighbor, int TT, int alpha,
                                Solution sol, int d, int[][] P, int[] WE, int[] WT, int[] de, int[] dt, int J, int I) throws InterruptedException, ExecutionException {
        Solution newSol;
        Solution currentSol = sol.copy();
        Solution globalBestSol = sol.copy();

        int F = sol.getSeq().size();
        List<Integer> allJobs = new ArrayList<>();
        for (int j = 0; j < J; j++) {
            allJobs.add(j);
        }
        boolean [] isForbidden= new boolean[tabuMaxNeighbor];

//        int alphaMin = F;
//        int alphaMax = 20;

        int noImprove = 0;
        int Iter = 0;
//        int alpha = RandomFunctions.randInt(alphaMin, alphaMax);

        int[] TL = new int[J];
        boolean Continue = true;
        while (Continue) {

            int[] ET = new int[J];

            for (List<Integer> seqF : sol.getSeq()) {
                int[] ET_temp = new int[J];
                if (!seqF.isEmpty())
                    ET_temp = BaseFunctions.calculateET(seqF, P, WE, WT, de, dt, J, I);
                for (int j = 0; j < J; j++) {
                    ET[j] += ET_temp[j];
                }
            }
            TL = updateTL(currentSol.getSeq(), alpha, TL, TT, ET, J);

            Solution bestFree = new Solution();
            Solution bestForbidden = new Solution();

            final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

            Collection<Callable<Solution>> callables = new ArrayList<>();

            for (int neighbor = 0; neighbor < tabuMaxNeighbor; neighbor++) {
                List<Integer> chosenJobs = RandomFunctions.Choice(allJobs, d);
                isForbidden[neighbor] =false;
                for (int j : chosenJobs) {
                    if (TL[j] > 0) {
                        isForbidden[neighbor] = true;
                        break;
                    }
                }
                callables.add(new MoveToNegihbor_Runnable( currentSol,  chosenJobs,  P,  WE,  WT, de, dt, J,  I));
            }

            List<Future<Solution>> SolutionPool = pool.invokeAll(callables);

            for (int neighbor = 0; neighbor < tabuMaxNeighbor; neighbor++) {
                newSol = SolutionPool.get(neighbor).get();
                if (isForbidden[neighbor]) {
                    if (newSol.getObj() < bestForbidden.getObj()) {
                        bestForbidden = newSol.copy();
                    }
                } else {
                    if (newSol.getObj() < bestFree.getObj()) {
                        bestFree = newSol.copy();
                    }
                }
            }
            pool.shutdown();

            if (!bestFree.getSeq().isEmpty()) {
                currentSol = bestFree.copy();
            }
            if (bestForbidden.getObj() < globalBestSol.getObj()) {
                currentSol = bestForbidden.copy();
            }
            if (currentSol.getObj() < globalBestSol.getObj()) {
                globalBestSol = currentSol.copy();
                noImprove = 0;
            } else {
                noImprove++;
            }
            double elapsedTime = (System.currentTimeMillis()-currentTime)/1e3;

            if (Iter > tabuMaxIter || noImprove > tabuMaxNOImprove|| elapsedTime>maxTime) {
                Continue = false;
            }
            System.out.println("tabuIter: " +Iter + "   obj: " +globalBestSol.getObj());
            Iter++;
        }//while (Continue)
        return globalBestSol;
    }

    private static int[] updateTL(List<List<Integer>> seq, int alpha, int[] TL, int TT, int[] ET, int J) {
        List<Integer> alpha1 = new ArrayList<>();
        List<Integer> alpha2 = new ArrayList<>();
        int F = seq.size();
        for (int f = 0; f < F; f++) {
            int minE = (int) 1e10;
            int minT = (int) 1e10;
            int minEjob = -1;
            int minTjob = -1;
            for (int j : seq.get(f)) {
                if (-ET[j] <= minE) {
                    minE = -ET[j];
                    minEjob = j;
                }
                if (ET[j] <= minT) {
                    minT = ET[j];
                    minTjob = j;
                }
            }

            alpha1.add(minEjob);
            alpha2.add(minTjob);
        }
        HashSet<Integer> alpha3 = new HashSet<>();
        for (int i : alpha1) {
            alpha3.add(i);
        }
        for (int i : alpha2) {
            alpha3.add(i);
        }
        List<Integer> availableJobs = new ArrayList<>();
        for (int i = 0; i < J; i++) {
            availableJobs.add(i);
        }
        availableJobs.removeAll(alpha3);

        while (alpha3.size() < alpha) {
            int j = RandomFunctions.Choice(availableJobs, 1).get(0);
            alpha3.add(j);
            availableJobs.remove(new Integer(j));
        }
        while (alpha3.contains(-1)) {
            alpha3.remove(new Integer(-1));
        }

        for (int j : alpha3) {
            TL[j] = TT;
        }
        return TL;
    }

    private  static Solution moveToNeighbor(Solution currentSol, List<Integer> chosenJobs, int[][] P, int[] WE, int[] WT, int[] de, int[] dt, int J, int I){
        Solution newSol = currentSol.copy();
        int F = currentSol.getSeq().size();
        for (int j : chosenJobs) {
            for (int f = 0; f < F; f++) {
                if (newSol.getSeqF(f).contains(j)) {
                    newSol.removeJob(f, j);
                    break;
                }
            }
        }

        int k = RandomFunctions.randInt(2, 4);
//        int k  = 3;
        newSol = Construction.k_regret(k, newSol, P, WE, WT, de, dt, J, I);
        return newSol;

    }

    public static final class MoveToNegihbor_Runnable implements Callable {

        Solution currentSol;
        List<Integer> chosenJobs;
        int[][] P; int[] WE;
        int[] WT;
        int[] de;
        int[] dt;
        int J;
        int I;

        public MoveToNegihbor_Runnable(Solution currentSol, List<Integer> chosenJobs, int[][] P, int[] WE, int[] WT, int[] de, int[] dt, int J, int I) {
            this. currentSol=currentSol;
            this. chosenJobs=chosenJobs;
            this. P=P;
            this. WE=WE;
            this. WT=WT;
            this. de=de;
            this. dt=dt;
            this.J=J;
            this.I=I;
        }

        @Override
        public Solution call() {
            return moveToNeighbor( currentSol,  chosenJobs,  P,  WE,  WT, de, dt, J,  I);
        }
    }



}
