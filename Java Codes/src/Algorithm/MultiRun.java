package Algorithm;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiRun {
    public static void main(String[] args) throws Exception {

        //=========================================  Algorithm Settings  ===============================================
//        int maxIteration = 300;
//        int tempThreshold = 10;
//        int tabuActivator = 60;
//        int maxNOImprove = 70;
//
//        int minT = 1;
//        int maxD = 15;
//
//        int tabuMaxNOImprove = 20;
//        int tabuMaxIter = 80;
//        int tabuMaxNeighbor = 100;
//        int maxTime = 10;
//        int TT = 3;
//        int alpha = 37;
        int maxIteration = 1000;
        int tempThreshold = 40;
        int tabuActivator = 100;
        int maxNOImprove = 50;

        int minT = 1;
        int maxD = 10;

        int tabuMaxNOImprove = 5;
        int tabuMaxIter = 10;
        int tabuMaxNeighbor = 8;
        int maxTime = 2000;
        int TT = 3;
        int alpha = 5;

        int repeats = 1;
        int[] success = {0, 0, 0};
        IteratedGreedy IG;

        //=========================================== Problem Settings =================================================

        String inputDir = "G:\\Kasra\\Thesis\\Codes\\Data\\Large\\7\\";
        String outDir = inputDir + "results.txt";


        double[] Tset = {0.2, 0.4};
        double[] Rset = {0.2, 0.6};
        int[] Wset = {20, 50};


        double[] times = new double[repeats];
        int[] objectives = new int[repeats];


        Solution bestSol = new Solution();
        Solution currentSol;
//        TripleSet = (F,J,I)
//        int[][] TripleSet = {{2, 4, 3}, {2, 4, 4}, {2, 4, 5}, {2, 10, 3}, {2, 10, 4}, {2, 10, 5}, {2, 16, 3}, {2, 16, 4}, {2, 16, 5}};
//        int[][] TripleSet = {{4, 20, 5}, {4, 20, 10}, {4, 20, 20}, {4, 50, 5}, {4, 50, 10}, {4, 50, 20}, {4, 100, 5}, {4, 100, 10}, {4, 100, 20}, {4, 200, 10}, {4, 200, 20}};
        int[][] TripleSet = {{7, 500, 20}};
        boolean newRun = true;
        for (int[] triple : TripleSet) {
            int F = triple[0];
            int J = triple[1];
            int I = triple[2];
            for (double R : Rset) {
                for (double Tt : Tset) {
                    for (int W : Wset) {
                        for (int Replication = 1; Replication < 6; Replication++) {
                            Data data = new Data(inputDir, F, J, I, Replication, Tt, R, W);
                            int bestObj = (int) 1e10;
                            for (int r = 0; r < repeats; r++) {

                                double time = System.currentTimeMillis();
//                          =========================  IG  =========================
                                IG = new IteratedGreedy(maxTime,maxNOImprove, tempThreshold, tabuActivator, maxIteration, minT, maxD, alpha,
                                        tabuMaxNOImprove, tabuMaxIter, tabuMaxNeighbor, TT, success);
                                currentSol = IG.solve(data.P, data.WE, data.WT, data.de, data.dt, data.J, data.I, data.F);
//                          =========================  Tabu  =========================
//                            NEH neh = new NEH();
//                            Solution initial = neh.initialSol(data.P, data.WE, data.WT, data.de, data.dt, data.J, data.I, data.F);
//
//                            currentSol = Search.tabu(tabuMaxNOImprove, tabuMaxIter, tabuMaxNeighbor, TT, alpha,
//                                    initial, 5, data.P, data.WE, data.WT, data.de, data.dt, data.J, data.I);
//                          ===========================================================

                                time = (System.currentTimeMillis() - time) / 1000;

                                times[r] = time;
                                objectives[r] = currentSol.getObj();

                                if (currentSol.getObj() < bestObj) {
                                    bestSol = currentSol.copy();
                                    bestObj = currentSol.getObj();
                                }
                            }//for repeats

                            outAppender(outDir, F, J, I, R, Tt,
                                    W, Replication, bestSol.getObj(), std(objectives), mean(times), bestSol.getSeq(), newRun);
                            newRun = false;
                        }
                    }
                }
            }
        }
    }

    public static void printOutput(String outDir) throws FileNotFoundException {

        try (PrintWriter out = new PrintWriter(outDir)) {
            out.println("F\tJ\tI\tReplication\tTt\tR\tW\tBestObj\tSTD\tMeanTime\tBestSol");
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static double mean(double[] input) {
        return Arrays.stream(input).sum() / input.length;
    }

    public static double std(int[] input) {
        double mean = Arrays.stream(input).sum() / input.length;
        double std = 0;
        for (int i = 0; i < input.length; i++) {
            std += (input[i] - mean) * (input[i] - mean);
        }
        return Math.sqrt(std);
    }

    public static void outAppender(String fileDir,
                                   int F, int J, int I, double R, double Tt,
                                   int W, int Replication, int bestObj, double std, double meanTime, List<List<Integer>> bestSeq, boolean newRun) throws FileNotFoundException {
        if (newRun) {
            printOutput(fileDir);
        }
        String s = "";
        int count = 0;
        for (List<Integer> seqF : bestSeq) {
            s += "f" + count + ":";
            s += seqF;
            s += "  ";
            count++;
        }
        try (FileWriter fw = new FileWriter(fileDir, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(F + "\t" + J + "\t" + I + "\t" + Replication + "\t"+ Tt + "\t" + R + "\t" + W + "\t"  +
                    bestObj + "\t" + std + "\t" + meanTime + "\t" + s);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }


    }
}


