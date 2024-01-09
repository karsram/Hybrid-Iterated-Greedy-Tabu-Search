package Algorithm;

import java.util.ArrayList;
import java.util.List;

public class Construction {
    public static Solution removedSequence(Solution sol, int[][] P, int[] WE, int[] WT, int[] de, int[] dt, int J, int I) {
        Solution newSol = sol.copy();
        for (int job : sol.getRemoved()) {
            newSol = singleInsertion(job, newSol, P, WE, WT, de, dt, J, I);
//            int f = RandomFunctions.randInt(0, newSol.getSeq().size() - 1);
//            int ind = RandomFunctions.randInt(0, newSol.getSeqF(f).size());
//            newSol.insertJobIndex(f, ind, job);
        }
        newSol.evaluate(P, WE, WT, de, dt, J, I);
        return newSol;
    }

    public static Solution randomSelection(Solution sol, int[][] P, int[] WE, int[] WT, int[] de, int[] dt, int J, int I) {
        Solution newSol = sol.copy();
        List<Integer> permutedList = RandomFunctions.Choice(sol.getRemoved(), sol.getRemoved().size());
        for (int job : permutedList) {
//            int f = RandomFunctions.randInt(0, newSol.getSeq().size() - 1);
//            int ind = RandomFunctions.randInt(0, newSol.getSeqF(f).size());
//            newSol.insertJobIndex(f, ind, job);
            newSol = singleInsertion(job, newSol, P, WE, WT, de, dt, J, I);
        }
        newSol.evaluate(P, WE, WT, de, dt, J, I);
        return newSol;
    }

    public static Solution greedySelection(Solution sol, int[][] P, int[] WE, int[] WT, int[] de, int[] dt, int J, int I) {

        Solution newSol = sol.copy();
        Solution bestSol = sol.copy();
        int F = sol.getSeq().size();
        int bestFactory = -1;
        int bestInd = -1;
        int bestJob = -1;

        List<Integer> removedList = new ArrayList<>(sol.getRemoved());

        while (!removedList.isEmpty()) {
            int minIncrement = (int) 1e10;
            for (int job : removedList) {
                for (int f = 0; f < F; f++) {
                    int factoryObj = BaseFunctions.calculateFactoryObj(newSol.getSeqF(f), P, WE, WT, de, dt, J, I);
                    for (int ind = 0; ind < newSol.getSeqF(f).size() + 1; ind++) {
                        newSol.insertJobIndex(f, ind, job);
                        int newObj = BaseFunctions.calculateFactoryObj(newSol.getSeqF(f), P, WE, WT, de, dt, J, I);
                        int increment = newObj - factoryObj;
                        if (increment < minIncrement) {
                            bestFactory = f;
                            bestInd = ind;
                            bestJob = job;
                            minIncrement = increment;
                        }
                        newSol = bestSol.copy();
                    }
                }
            }
            bestSol.insertJobIndex(bestFactory, bestInd, bestJob);
            newSol = bestSol.copy();
            removedList.remove(new Integer(bestJob));
        }
        bestSol.evaluate(P, WE, WT, de, dt, J, I);
        return bestSol;
    }

    public static Solution k_regret(int K, Solution sol, int[][] P, int[] WE, int[] WT, int[] de, int[] dt, int J, int I) {

        Solution newSol;
        Solution bestSol = sol.copy();
        List<Integer> removedList = new ArrayList<>(sol.getRemoved());
        int F = sol.getSeq().size();

        while (!removedList.isEmpty()) {
            int[] bestFactory = new int[J];
            int[] bestInd = new int[J];
            List<Integer>[] obj = new ArrayList[J];

            for (int j = 0; j < J; j++) {
                obj[j] = new ArrayList<>();
            }
            newSol = bestSol.copy();

            for (int job : removedList) {
                int minIncrement = (int) 1e10;
                for (int f = 0; f < F; f++) {
                    int factoryObj = BaseFunctions.calculateFactoryObj(newSol.getSeqF(f), P, WE, WT, de, dt, J, I);
                    for (int ind = 0; ind < newSol.getSeqF(f).size() + 1; ind++) {
                        newSol.insertJobIndex(f, ind, job);
                        int newObj = BaseFunctions.calculateFactoryObj(newSol.getSeqF(f), P, WE, WT, de, dt, J, I);
                        int increment = newObj - factoryObj;
                        if (increment < minIncrement) {
                            bestFactory[job] = f;
                            bestInd[job] = ind;
                            minIncrement = increment;
                        }
                        newSol = bestSol.copy();
//                        obj[job].add(newObj+(int) (1+RandomFunctions.uniform(-.5,.5)*newObj));
                        obj[job].add(newObj);
                    }
                }
            }

            int[] regret = new int[J];

            for (int j = 0; j < J; j++) {
                regret[j] = -100;
            }

            for (int j : removedList) {
                regret[j] = 0;
                double[] sortedObj = KminSort.valueSorted(obj[j].stream().mapToDouble(d -> d).toArray());

                for (int k = 1; k < Math.min(K, sortedObj.length); k++) {
                    regret[j] += sortedObj[k] - sortedObj[0];
                }
            }

            int finalJob = KminSort.indexOfSorted(regret, true)[0];

            bestSol.insertJobIndex(bestFactory[finalJob], bestInd[finalJob], finalJob);

            removedList.remove(new Integer(finalJob));
        }
        bestSol.evaluate(P, WE, WT, de, dt, J, I);
        return bestSol;
    }

    public static Solution singleInsertion(int job, Solution sol, int[][] P, int[] WE, int[] WT, int[] de, int[] dt, int J, int I) {
        int F = sol.getSeq().size();
        int minIncrement = (int) 1e10;
        Solution newSol = sol.copy();
        Solution bestSol = sol.copy();
        List<Integer> bestFactory = new ArrayList<>();
        List<Integer> bestInd = new ArrayList<>();
        List<Integer> obj = new ArrayList<>();


        for (int f = 0; f < F; f++) {
            for (int ind = 0; ind < newSol.getSeqF(f).size() + 1; ind++) {
                newSol.insertJobIndex(f, ind, job);
                int newObj = BaseFunctions.calculateFactoryObj(newSol.getSeqF(f), P, WE, WT, de, dt, J, I);
                newSol.removeJobIndex(f, ind);
                obj.add(newObj);
                bestFactory.add(f);
                bestInd.add(ind);
            }
        }
        int[] sortedIndex = KminSort.indexOfSorted(obj.stream().mapToDouble(d -> d).toArray(), Math.min(5, obj.size()));

        List<Double> chosenObj = new ArrayList<>();

        for (int i : sortedIndex) {
            chosenObj.add((double) obj.get(i));
        }
        int indexInChosenJob = RandomFunctions.rouletteWheel(chosenObj, false);
        int index = sortedIndex[indexInChosenJob];
        bestSol.insertJobIndex(bestFactory.get(index), bestInd.get(index), job);
        return bestSol;
    }
}