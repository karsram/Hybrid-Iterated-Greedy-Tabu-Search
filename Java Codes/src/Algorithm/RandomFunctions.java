package Algorithm;

import java.util.ArrayList;
import java.util.List;


public class RandomFunctions {
    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */

    /**
     * @author pc
     */

    public static int randInt(double min, double max) {

        int random_int = (int) (Math.random() * (max - min + 1) + min);
        return random_int;
    }

    public static int randInt(double max) {

        int random_int = (int) (Math.random() * (max - 0 + 1) + 0);
        return random_int;
    }

    public static double Rand() {

        double random = Math.random();
        return random;
    }

    public static double uniform(double min, double max) {

        double random = (Math.random() * (max - min) + min);
        return random;
    }


    public static int rouletteWheel(List<Double> Values, boolean maxPriority) {

        KminSort Ks = new KminSort();
        int[] Sortedindices;
        double SumValue = 0;
        double[] MoveChance = new double[Values.size()];

        for (int i = 0; i < Values.size(); i++) {
            Double val;

            if (maxPriority) {
                val = Values.get(i);
            } else {
                val = 1 / (Values.get(i) + 1e-6);
            }

            SumValue += val;
        }

        for (int i = 0; i < Values.size(); i++) {
            Double val;

            if (maxPriority) {
                val = Values.get(i);
            } else {
                val = 1 / (Values.get(i) + 1e-6);
            }
            MoveChance[i] = -val / SumValue;

        }

        Sortedindices = Ks.indexOfSorted(MoveChance, MoveChance.length);

        double p = 0;
        int SortedInd = 0;
        int ChosenMoveInd = 0;
        double r = Rand();
        while (p < r) {

            ChosenMoveInd = Sortedindices[SortedInd];

            p = -MoveChance[ChosenMoveInd] + p;

            SortedInd++;
        }//while

        return ChosenMoveInd;
    }

    public static int rouletteWheel(double[] Values, boolean maxPriority) {
        KminSort Ks = new KminSort();
        int[] Sortedindices;
        double SumValue = 0;
        double[] MoveChance = new double[Values.length];

        for (int i = 0; i < Values.length; i++) {
            Double val;

            if (maxPriority) {
                val = Values[i];
            } else {
                val = 1 / (Values[i] + 1e-6);
            }
            SumValue += val;
        }

        for (int i = 0; i < Values.length; i++) {
            Double val;

            if (maxPriority) {
                val = Values[i];
            } else {
                val = 1 / (Values[i] + 1e-6);
            }
            MoveChance[i] = -val / SumValue;
        }

        Sortedindices = Ks.indexOfSorted(MoveChance, MoveChance.length);

        double p = 0;
        int SortedInd = 0;
        int ChosenMoveInd = 0;
        double r = Rand();
        while (p < r) {

            ChosenMoveInd = Sortedindices[SortedInd];

            p = -MoveChance[ChosenMoveInd] + p;

            SortedInd++;
        }//while

        return ChosenMoveInd;
    }

    public static int[] Choice(int TotalInstances, int NumberOfInstances) {

        KminSort ks = new KminSort();
        double[] Array = new double[TotalInstances];

        for (int i = 0; i < TotalInstances; i++) {
            Array[i] = Rand();
        }
        NumberOfInstances = Math.min(NumberOfInstances, Array.length);
        return ks.indexOfSorted(Array, NumberOfInstances);

    }

    public static List<Integer> Choice(List<Integer> InputList, int NumberOfInstances) {

        KminSort ks = new KminSort();
        double[] Array = new double[InputList.size()];

        for (int i = 0; i < InputList.size(); i++) {
            Array[i] = Rand();
        }
        NumberOfInstances = Math.min(NumberOfInstances, Array.length);
        int[] Indices = ks.indexOfSorted(Array, NumberOfInstances);

        List<Integer> OutPut = new ArrayList();

        for (int i = 0; i < Indices.length; i++) {
            int Indice = Indices[i];
            OutPut.add(InputList.get(Indice));
        }

        return OutPut;

    }

    public static double factorial(int n) {
        double result = 1;

        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static double Binom(int s, int t, double prob) {

        double combinotrial = 1;

        double[] face = new double[t];
        double[] ass1 = new double[s];
        double[] ass2 = new double[t - s];

        for (int coef = s + 1; coef <= t; coef++) {
            combinotrial = combinotrial * coef / (coef - s);
        }

        return combinotrial * Math.pow(prob, s) * Math.pow(1 - prob, t - s);

    }
}
