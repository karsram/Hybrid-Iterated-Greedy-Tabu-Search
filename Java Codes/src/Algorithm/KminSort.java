package Algorithm;

import java.util.*;
import java.util.stream.Collectors;

public class KminSort {




    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */

    /**
     * @author pc
     */
    public KminSort() {
    }


    public static int[] indexOfSorted(double[] array, int Kmins, boolean[] Filter) {

        double[] FliterdArray = new double[array.length];

        for (int i = 0; i < array.length; i++) {
            if (Filter[i]) {
                FliterdArray[i] = 1e7;
            } else {
                FliterdArray[i] = array[i];
            }

        }
        int[] indexes = new int[array.length];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }
        mergeSort(FliterdArray, indexes, 0, indexes.length - 1);
        int[] Newindexes = new int[Kmins];
        System.arraycopy(indexes, 0, Newindexes, 0, Kmins);
        return Newindexes;

    }

    public static double[] valueSorted(double[] array) {

        double[] FliterdArray = new double[array.length];
        System.arraycopy(array, 0, FliterdArray, 0, array.length);
        int[] indexes = new int[array.length];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }
        mergeSort(FliterdArray, indexes, 0, indexes.length - 1);
        double[] SortedArray;
        SortedArray = new double[array.length];
        for (int k = 0; k < array.length; k++) {
            SortedArray[k] = array[indexes[k]];
        }
        return FliterdArray;

    }

    public static int[] indexOfSorted(double[] array, int Kmins) {

        double[] FliterdArray = new double[array.length];

        System.arraycopy(array, 0, FliterdArray, 0, array.length);

        int[] indexes = new int[array.length];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }
        mergeSort(FliterdArray, indexes, 0, indexes.length - 1);
        int[] Newindexes = new int[Kmins];
        System.arraycopy(indexes, 0, Newindexes, 0, Kmins);
        return Newindexes;

    }

    public static int[] indexOfSorted(double[] array) {

        double[] FliterdArray = new double[array.length];

        System.arraycopy(array, 0, FliterdArray, 0, array.length);

        int[] indexes = new int[array.length];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }
        mergeSort(FliterdArray, indexes, 0, indexes.length - 1);
        int[] Newindexes = new int[array.length];
        System.arraycopy(indexes, 0, Newindexes, 0, array.length);
        return Newindexes;

    }

    public static int[] indexOfSorted(int[] array) {

        double[] FliterdArray = new double[array.length];

        for (int i = 0; i < array.length; i++) {
            FliterdArray[i] = array[i];
        }

        int[] indexes = new int[array.length];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }
        mergeSort(FliterdArray, indexes, 0, indexes.length - 1);
        int[] Newindexes = new int[array.length];
        System.arraycopy(indexes, 0, Newindexes, 0, array.length);
        return Newindexes;

    }

    public static int[] indexOfSorted(int[] array, boolean reverse) {
        int[] ind = new int[array.length];
        if (reverse) {
            int[] newArray = new int[array.length];
            for (int i = 0; i < array.length; i++) {
                newArray[i] = -array[i];
                ind = indexOfSorted(newArray);
            }
        }else{
            ind = indexOfSorted(array);
        }
        return ind;
    }


    public static List<Integer> indexOfSorted(List<Double> inputList, int Kmins) {

        double[] array = new double[inputList.size()];

        for (int i = 0; i < inputList.size(); i++) {
            array[i] = inputList.get(i);
        }

        double[] FliterdArray = new double[array.length];

        System.arraycopy(array, 0, FliterdArray, 0, array.length);

        int[] indexes = new int[array.length];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }
        mergeSort(FliterdArray, indexes, 0, indexes.length - 1);
        List<Integer> Newindexes = new ArrayList<>();
        for (int i = 0; i < Kmins; i++) {
            Newindexes.add(indexes[i]);
        }
        return Newindexes;
    }

    public static List<Integer> indexOfSorted(List<Double> inputList) {

        double[] array = new double[inputList.size()];

        for (int i = 0; i < inputList.size(); i++) {
            array[i] = inputList.get(i);
        }

        double[] FliterdArray = new double[array.length];

        System.arraycopy(array, 0, FliterdArray, 0, array.length);

        int[] indexes = new int[array.length];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }
        mergeSort(FliterdArray, indexes, 0, indexes.length - 1);
        List<Integer> Newindexes = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            Newindexes.add(indexes[i]);
        }
        return Newindexes;

    }

    public static double[] ValSort(double[] array, int Kmins) {

        double[] FliterdArray = new double[array.length];

        System.arraycopy(array, 0, FliterdArray, 0, array.length);

        int[] indexes = new int[array.length];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }
        mergeSort(FliterdArray, indexes, 0, indexes.length - 1);
        double[] SortedVals = new double[Kmins];
        System.arraycopy(FliterdArray, 0, SortedVals, 0, Kmins);
        return SortedVals;

    }

    private static void mergeSort(double[] array, int[] indexes, int start, int end) {
        if (start >= end) {
            return;
        }
        int middle = (end - start) / 2 + start;
        mergeSort(array, indexes, start, middle);
        mergeSort(array, indexes, middle + 1, end);
        merge(array, indexes, start, middle, end);
    }

    private static void merge(double[] array, int[] indexes, int start, int middle, int end) {
        int len1 = middle - start + 1;
        int len2 = end - middle;
        double[] leftArray = new double[len1];
        int[] leftIndex = new int[len1];
        double[] rightArray = new double[len2];
        int[] rightIndex = new int[len2];
        for (int i = 0; i < len1; ++i) {
            leftArray[i] = array[i + start];
        }
        for (int i = 0; i < len1; ++i) {
            leftIndex[i] = indexes[i + start];
        }
        for (int i = 0; i < len2; ++i) {
            rightArray[i] = array[i + middle + 1];
        }
        for (int i = 0; i < len2; ++i) {
            rightIndex[i] = indexes[i + middle + 1];
        }
        //merge
        int i = 0, j = 0, k = start;
        while (i < len1 && j < len2) {
            if (leftArray[i] < rightArray[j]) {
                array[k] = leftArray[i];
                indexes[k] = leftIndex[i];
                ++i;
            } else {
                array[k] = rightArray[j];
                indexes[k] = rightIndex[j];
                ++j;
            }
            ++k;
        }
        while (i < len1) {
            array[k] = leftArray[i];
            indexes[k] = leftIndex[i];
            ++i;
            ++k;
        }
        while (j < len2) {
            array[k] = rightArray[j];
            indexes[k] = rightIndex[j];
            ++j;
            ++k;
        }
    }

    public int[] ReshapeIndex(int index, int column) {
        int[] Ind = new int[2];

        int r = index / column;
        int c = 0;
        if (r * column == index) {
        } else {
            c = index - r * column;
        }

        Ind[0] = r;
        Ind[1] = c;
        return Ind;
    }

    public List<Integer> unique(List<Integer> R) {

        List<Integer> uniqueList = R.stream().distinct().collect(Collectors.toList());

        return uniqueList;
    }
    // function to sort hashmap by values


    public static List<Integer> IndexOfSortedList(HashMap<Integer, Double> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Double>> list
                = new LinkedList<Map.Entry<Integer, Double>>(hm.entrySet());

        // indexOfSorted the list
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
            public int compare(Map.Entry<Integer, Double> o1,
                               Map.Entry<Integer, Double> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        List<Integer> temp = new ArrayList<>();
        for (Map.Entry<Integer, Double> aa : list) {
            temp.add(aa.getKey());
        }
        return temp;
    }

//    bestClusters.sort(Comparator.comparing(List::size));

}
