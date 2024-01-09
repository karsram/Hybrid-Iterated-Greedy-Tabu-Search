package Algorithm;

import java.util.ArrayList;
import java.util.List;


public class Destruction {

    public static Solution randomSelection(Solution sol, int d) {

        Solution partial = sol.copy();

        List<Integer> feasibleFactories = new ArrayList<>();

        int F = sol.getSeq().size();

        for (int f = 0; f < F; f++) {
            if (sol.getSeqF(f).size() > 0) {
                feasibleFactories.add(f);
            }
        }

        for (int count = 0; count < d; count++) {
            int fInd = RandomFunctions.randInt(0, feasibleFactories.size() - 1);
            int f = feasibleFactories.get(fInd);
            if (partial.getSeqF(f).size() > 0) {
                partial = singleRemove(partial, f);
            } else {
                feasibleFactories.remove(new Integer(f));
            }

        }
        return partial;
    }

    public static Solution flowLineSelection(Solution sol, int d, int[][] P, int[] de, int[] dt, int[] WE, int[] WT, int J, int I) {

        Solution newSol = sol.copy();
        int factory;
        int flowLine = -1;
        int F = newSol.getSeq().size();
        int[] objectives = new int[newSol.getSeq().size()];

        for (int f = 0; f < F; f++) {
            List<Integer> seqF = newSol.getSeqF(f);
            objectives[f] = BaseFunctions.calculateFactoryObj(seqF, P, de, dt, WE, WT, J, I);
        }

        int[] sortedObjectives_ind = KminSort.indexOfSorted(objectives);

        while (newSol.getRemoved().size() < d) {
            flowLine += 1;
            if (flowLine < F) {
                factory = sortedObjectives_ind[flowLine];
            } else {
                factory = RandomFunctions.randInt(0, F - 1);
            }
            if (!newSol.getSeqF(factory).isEmpty()) {
                newSol = singleRemove(newSol, factory);
            }
        }
        return newSol;

    }

    public static Solution greedySelection(Solution sol, int d, int[][] P, int[] de, int[] dt, int[] WE, int[] WT, int J, int I) {

        Solution newSol = sol.copy();
        int[] T = new int[J];
        int[] E = new int[J];
        int[] ET = new int[J];
        int[] assigned = new int[J];
        int F = newSol.getSeq().size();
        int factory;

        for (int f = 0; f < F; f++) {
            int[] parET = new int[J];
            if (!newSol.getSeqF(f).isEmpty()) {
                parET = BaseFunctions.calculateET(newSol.getSeqF(f), P, de, dt, WE, WT, J, I);
            }
            for (int j : newSol.getSeqF(f)) {
                assigned[j] = f;
                if (parET[j] > 0) {
                    T[j] = parET[j];
                } else {
                    E[j] = -parET[j];
                }
            }
        }

        int[] sortedT_ind = KminSort.indexOfSorted(T, true);
        int[] sortedE_ind = KminSort.indexOfSorted(E, true);

        List<Integer> sortedT = new ArrayList<>();
        List<Integer> sortedE = new ArrayList<>();

        for (int ind : sortedT_ind) {
            if (ET[ind] >= 0) {
                sortedT.add(ind);
            }
        }

        for (int ind : sortedE_ind) {
            if (ET[ind] < 0) {
                sortedE.add(ind);
            }
        }

        int counter = 0;
        for (int j : sortedT) {
            if (counter <= d / 2) {
                sortedE.remove(new Integer(j));
                factory = assigned[j];
                newSol.removeJob(factory, j);
                counter++;

            }
        }
        for (int j : sortedE) {
            if (counter <= d) {
                factory = assigned[j];
                newSol.removeJob(factory, j);
                counter++;
            }
        }
        return newSol;
    }

    public static Solution blockSelection(Solution sol) {

        Solution newSol = sol.copy();
        int F = sol.getSeq().size();
        List<Integer> feasibleFactories = new ArrayList<>();
        for (int f = 0; f < F; f++) {
            if (!sol.getSeqF(f).isEmpty()) {
                feasibleFactories.add(f);
            }
        }

        int index = RandomFunctions.randInt(0, feasibleFactories.size() - 1);

        int factory = feasibleFactories.get(index);
        List<Integer> block = BaseFunctions.getBlock(sol.getSeqF(factory));
        for (int j : block) {
            newSol.removeJob(factory, j);
        }
        return newSol;
    }

    public static Solution criticalPathSelection(Solution sol, int d, int[][] P, int[] de, int[] dt, int[] WE, int[] WT, int J, int I) {

        Solution newSol = sol.copy();
        int F = sol.getSeq().size();

        int criticalFactory = findCritical(sol, P, de, dt, WE, WT, J, I);
        int count = 0;
        while (count < d / 2) {
            if (!newSol.getSeqF(criticalFactory).isEmpty()) {
                newSol = singleRemove(newSol, criticalFactory);
            }
            count++;
        }

        List<Integer> otherFactories = new ArrayList<>();

        for (int f = 0; f < F; f++) {
            if (newSol.getSeqF(f).size() > 0) {
                otherFactories.add(f);
            }
        }
        otherFactories.remove(new Integer(criticalFactory));

        while (newSol.getRemoved().size() <= d && otherFactories.size() > 0) {
            int chosenFactory = RandomFunctions.Choice(otherFactories, 1).get(0);

            if (!newSol.getSeqF(chosenFactory).isEmpty()) {
                newSol = singleRemove(newSol, chosenFactory);
            } else {
                otherFactories.remove(new Integer(chosenFactory));
            }


        }
        return newSol;
    }

    public static Solution singleRemove(Solution sol, int f) {
        Solution newSol = sol.copy();
        int ind = RandomFunctions.randInt(0, newSol.getSeqF(f).size() - 1);
        newSol.removeJobIndex(f, ind);
        return newSol;
    }

    private static int findCritical(Solution sol, int[][] P, int[] de, int[] dt, int[] WE, int[] WT, int J, int I) {

        int maxObj = -1;
        int obj;
        int criticalFactory = -1;
        int F = sol.getSeq().size();

        for (int f = 0; f < F; f++) {
            obj = BaseFunctions.calculateFactoryObj(sol.getSeqF(f), P, de, dt, WE, WT, J, I);
            if (obj > maxObj) {
                maxObj = obj;
                criticalFactory = f;
            }

        }

        return criticalFactory;

    }
}
