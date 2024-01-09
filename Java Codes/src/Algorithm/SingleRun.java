package Algorithm;

public class SingleRun {
    public static void main(String[] args) throws Exception {
        //=========================================  Algorithm Settings  ===============================================
//        int maxIteration = 300;
//        int tempThreshold = 10;
//        int tabuActivator = 60;
//        int maxNOImprove = 3 * tabuActivator;
//        int maxNOImprove = 70;
//
//        int minT = 1;
//        int maxD = 20;
//
//        int tabuMaxNOImprove = 5;
//        int tabuMaxIter = 120;
//        int tabuMaxNeighbor = 50;
//        int TT = 6;
//        int alpha = 6;
//        4 , 29, 54

// =========================================  for 50  ===============================================
        int maxIteration = 1100;
        int tempThreshold = 40;
        int tabuActivator = 10;
        int maxNOImprove = 15;

        int minT = 1;
        int maxD = 15;

        int tabuMaxNOImprove = 5;
        int tabuMaxIter = 10;
        int tabuMaxNeighbor = 8;
        int maxTime = 2000;
        int TT = 3;
        int alpha = 5;

        int[] success = {0, 0, 0};
//        IteratedGreedy IG = new IteratedGreedy(maxNOImprove, tempThreshold, tabuActivator, maxIteration, minT, maxD, alpha,
//                tabuMaxNOImprove, tabuMaxIter, tabuMaxNeighbor, TT, success);

        //=========================================== Problem Settings =================================================

        int F = 7;
        int J = 500;
        int I = 20;
        int Replication = 1;
        double Tt = 0.2;
        double R = 0.2;
        int W = 20;



        String inputDir = "G:\\Kasra\\Thesis\\Codes\\Data\\Large\\" + F + "\\";

        Data data = new Data(inputDir, F, J, I, Replication, Tt, R, W);
        double time = System.currentTimeMillis();
//      =========================  IG  =========================
        IteratedGreedy IG = new IteratedGreedy(maxTime,maxNOImprove, tempThreshold, tabuActivator, maxIteration, minT, maxD, alpha,
               tabuMaxNOImprove, tabuMaxIter, tabuMaxNeighbor, TT, success);
        IG.solve(data.P, data.WE, data.WT, data.de, data.dt, data.J, data.I, data.F);
//      =========================  Tabu  =========================

//       NEH neh = new NEH();
//        Solution initial = neh.initialSol(data.P, data.WE, data.WT, data.de, data.dt, data.J, data.I, data.F);
//        Solution bestSol = Search.tabu(tabuMaxNOImprove, tabuMaxIter, tabuMaxNeighbor, TT, alpha,
//                initial, 5, data.P, data.WE, data.WT, data.de, data.dt, data.J, data.I);
//        time = (System.currentTimeMillis() - time) / 1000;
//        System.out.println(bestSol.toString());
//        System.out.println("elapsed time: "+time);
    }
}
