package Algorithm;

import java.util.ArrayList;
import java.util.List;

public class BaseFunctions {

    public static int calculateOBJ(List<List<Integer>> seq, int[][] P, int[] WE, int[] WT, int[] de, int[] dt, int J, int I) {
        int totalObj = 0;
        for (List<Integer> seqF : seq) {
            if (!seqF.isEmpty()) {
                totalObj += BaseFunctions.calculateFactoryObj(seqF, P, WE, WT, de, dt, J, I);
            }
        }
        return totalObj;
    }

    public static int calculateFactoryObj(List<Integer> seqF, int[][] P, int[] WE, int[] WT, int[] de, int[] dt, int J, int I) {
        int objF = 0;
        if (seqF.size() > 0) {
            int[] ET = calculateET(seqF, P, WE, WT, de, dt, J, I);

            for (int j : seqF) {
                if (ET[j] > 0) {
                    objF += WT[j] * ET[j];
                } else {
                    objF -= WE[j] * ET[j];
                }
            }
        }
        return objF;

    }

    public static int[] calculateET(List<Integer> seqF, int[][] P, int[] WE, int[] WT, int[] de, int[] dt, int J, int I) {
        int[][] C = calculateC(seqF, P, WE, WT, de, dt, J, I);
        int s = seqF.size();
        List<Integer> SM = new ArrayList<>(seqF);
        int[] ET = obtainET(C, de, dt);
        int[] E = new int[J];
        int[] T = new int[J];
        for (int j = 0; j < J; j++) {
            if (ET[j] < 0) {
                E[j] = -ET[j];
            } else {
                T[j] = ET[j];
            }
        }
        while (s > 0) {
            List<Integer> SE = new ArrayList<>();
            List<Integer> ST = new ArrayList<>();
            List<Integer> SD = new ArrayList<>();

            for (int j : seqF) {
                if (ET[j] < 0) {
                    SE.add(j);
                } else if (ET[j] > 0) {
                    ST.add(j);
                } else {
                    SD.add(j);
                }

            }
            int theta = 0;
            int sumE = sum(WE, SE);
            int sumT = sum(WT, ST);
            if (sumE > sumT) {
                if (!SE.isEmpty() && !SD.isEmpty()) {
                    if (sumE > sum(WT, SD)) {
                        theta = min(E, SE);
                    } else {
                        int[] dt_C = new int[J];
                        for (int j = 0; j < J; j++) {
                            dt_C[j] = dt[j] - C[j][I - 1];
                        }
                        theta = Math.min(min(E, SE), min(dt_C, SD));
                    }
                } else if (!SE.isEmpty()) {
                    theta = min(E, SE);
                } else if (!SD.isEmpty()) {
                    int[] dt_C = new int[J];
                    for (int j = 0; j < J; j++) {
                        dt_C[j] = dt[j] - C[j][I - 1];
                    }
                    theta = min(dt_C, SD);
                }

                for (int j : SM) {
                    C[j][I - 1] = C[j][I - 1] + theta;
                }

                ET = obtainET(C, de, dt);
                for (int j : seqF) {
                    if (ET[j] < 0) {
                        E[j] = -ET[j];
                    } else {
                        T[j] = ET[j];
                    }
                }
            }

            if (theta == 0) {
                s--;
            }
        }
        return ET;
    }

    public static int[][] calculateC(List<Integer> seqF, int[][] P, int[] WE, int[] WT, int[] de, int[] dt, int J, int I) {
        int[][] C = new int[J][I];
        C[seqF.get(0)][0] = P[seqF.get(0)][0];
        int current;
        int previous;

//      Completion time of jobs at first machine
        for (int ind = 1; ind < seqF.size(); ind++) {
            current = seqF.get(ind);
            previous = seqF.get(ind - 1);
            C[current][0] = C[previous][0] + P[current][0];
        }

//      Forward completion time for others
        for (int m = 1; m < I; m++) {
            if (seqF.size() > 1) {
                C[seqF.get(0)][m] = C[seqF.get(0)][m - 1] + P[seqF.get(0)][m];
                for (int ind = 1; ind < seqF.size(); ind++) {
                    current = seqF.get(ind);
                    previous = seqF.get(ind - 1);
                    C[current][m] = Math.max(C[previous][m], C[current][m - 1]) + P[current][m];
                }
                for (int ind = seqF.size() - 1; ind >= 1; ind--) {
                    current = seqF.get(ind);
                    previous = seqF.get(ind - 1);
                    C[previous][m] = Math.max(C[current][m] - P[current][m], C[previous][m]);
                }
            } else {
                C[seqF.get(0)][m] = C[seqF.get(0)][m - 1] + P[seqF.get(0)][m];
            }
        }
        return C;
    }

    public static int[] obtainET(int[][] C, int[] de, int[] dt) {
        int J = C.length;
        int[] ET = new int[J];
        int lastMachine = C[0].length - 1;

        for (int j = 0; j < J; j++) {
            if (C[j][lastMachine] > 0) {
                int e = Math.max(de[j] - C[j][lastMachine], 0);
                int l = Math.max(C[j][lastMachine] - dt[j], 0);
                ET[j] = l - e;
            }
        }
        return ET;

    }

    public static List<Integer> getBlock(List<Integer> seqF) {
        int blockLen;
        int startPoint;
        List<Integer> block = new ArrayList<>();

        if (seqF.size() == 1) {
            blockLen = 1;
            startPoint = 0;
        } else {
            blockLen = RandomFunctions.randInt(1, seqF.size());
            startPoint = RandomFunctions.randInt(0, seqF.size() - blockLen);
        }
        for (int i = startPoint; i < startPoint + blockLen; i++) {
            block.add(seqF.get(i));
        }
        return block;
    }

    public static boolean metroPoliceCriteria(int newObj, int bestObj, double T) {

        return RandomFunctions.Rand() < Math.exp(-(newObj - bestObj) / T);
    }

    public static int sum(int[] values, List<Integer> indices) {
        int sum = 0;
        for (int j : indices) {
            sum += values[j];
        }
        return sum;
    }

    public static int min(int[] values, List<Integer> indices) {
        double[] array = new double[indices.size()];
        for (int ind = 0; ind < indices.size(); ind++) {
            array[ind] = values[indices.get(ind)];
        }
        int min = (int) KminSort.ValSort(array, 1)[0];
        return min;
    }

    public static int min(int[] values) {
        double[] array = new double[values.length];
        for (int ind = 0; ind < values.length; ind++) {
            array[ind] = values[ind];
        }
        int min = (int) KminSort.ValSort(array, 1)[0];
        return min;
    }

    public static int max(int[] values, List<Integer> indices) {
        double[] array = new double[indices.size()];
        for (int ind = 0; ind < indices.size(); ind++) {
            array[ind] = -indices.get(ind);
        }
        int max = -(int) KminSort.ValSort(array, 1)[0];
        return max;
    }

    public static int max(int[] values) {
        double[] array = new double[values.length];
        for (int ind = 0; ind < values.length; ind++) {
            array[ind] = -values[ind];
        }
        int max = -(int) KminSort.ValSort(array, 1)[0];
        return max;
    }


}
