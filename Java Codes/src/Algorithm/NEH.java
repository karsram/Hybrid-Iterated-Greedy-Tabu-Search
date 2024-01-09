package Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NEH {
    public NEH() {
    }

    public Solution initialSol(int[][] P, int[] WE, int[] WT, int[] de, int[] dt, int J, int I, int F) {
        int minObj = 1000000;
        Solution newSol;
        Solution bestSol;
        List<Integer> orderSeq;

        String initial = "";


        bestSol = new Solution();
        List<List<Integer>> seq = new ArrayList<>();
        if (F >= J) {
            for (int j = 0; j < J; j++) {
                seq.add(Arrays.asList(j));
            }
            bestSol = new Solution(seq, P, WE, WT, de, dt, J, I);
        } else {
//            orderSeq = EDD(dt, J);
//            newSol = calculateTWET(orderSeq, P, WE, WT, de, dt, J, I, F);
//            if (newSol.getObj() < bestSol.getObj()) {
//                bestSol = newSol.copy();
//                initial = "EDD";
//            }
//
//            orderSeq = LSL(P, dt, J, I);
//            newSol = calculateTWET(orderSeq, P, WE, WT, de, dt, J, I, F);
//            if (newSol.getObj() < bestSol.getObj()) {
//                bestSol = newSol.copy();
//                initial = "LSL";
//            }
//
//            orderSeq = WET(WT, WE, J);
//            newSol = calculateTWET(orderSeq, P, WE, WT, de, dt, J, I, F);
//            if (newSol.getObj() < bestSol.getObj()) {
//                bestSol = newSol.copy();
//                initial = "WET";
//            }

            orderSeq = EDDWET(WT, WE, dt, J);
            newSol = calculateTWET(orderSeq, P, WE, WT, de, dt, J, I, F);
            if (newSol.getObj() < bestSol.getObj()) {
                bestSol = newSol.copy();
                initial = "EDDWET";
            }

//            orderSeq = LSLWED(P, WT, WE, dt, J, I);
//            newSol = calculateTWET(orderSeq, P, WE, WT, de, dt, J, I, F);
//            if (newSol.getObj() < bestSol.getObj()) {
//                bestSol = newSol.copy();
//                initial = "LSLWED";
//            }


        }
        System.out.println(initial);
        return bestSol;

    }

    private List<Integer> EDD(int[] dt, int J) {
        int[] seq = KminSort.indexOfSorted(dt);
        List<Integer> orderSeq = new ArrayList<>();
        for (int ind = J - 1; ind >= 0; ind--) {
            orderSeq.add(seq[ind]);
        }
        return orderSeq;
    }

    private List<Integer> LSL(int[][] P, int[] dt, int J, int I) {

        int[] SL = new int[J];
        for (int j = 0; j < J; j++) {
            SL[j] = dt[j] - P[j][I - 1];
        }
        int[] seq = KminSort.indexOfSorted(SL);
        List<Integer> orderSeq = new ArrayList<>();
        for (int ind = J - 1; ind >= 0; ind--) {
            orderSeq.add(seq[ind]);
        }
        return orderSeq;
    }

    private List<Integer> WET(int[] WT, int[] WE, int J) {

        int[] GT = new int[J];
        int[] GE = new int[J];

        for (int j = 0; j < J; j++) {
            GT[j] = 100000;
            GE[j] = 100000;
            if (WT[j] >= WE[j]) {
                GT[j] = WT[j];
            } else {
                GE[j] = WT[j];
            }
        }
        List<Integer> orderSeq = new ArrayList<>();
        int[] GT_list = KminSort.indexOfSorted(GT);
        int[] GE_list = KminSort.indexOfSorted(GE);

        for (int ind = 0; ind < J; ind++) {
            if (GT[GT_list[ind]] < 100000) {
                orderSeq.add(GT_list[ind]);
            }
        }
        for (int ind = J - 1; ind >= 0; ind--) {
            if (GE[GE_list[ind]] < 100000) {
                orderSeq.add(GE_list[ind]);
            }
        }
        return orderSeq;
    }

    private List<Integer> EDDWET(int[] WT, int[] WE, int[] dt, int J) {
        List<Integer> orderSeq = new ArrayList<>();
        int[] GT = new int[J];
        int[] GE = new int[J];

        for (int j = 0; j < J; j++) {
            GT[j] = 1000000;
            if (WT[j] >= WE[j]) {
                GT[j] = WT[j];
            } else {
                GE[j] = WT[j];
            }
        }
        int[] GT_list = KminSort.indexOfSorted(GT);
        int[] GE_list = KminSort.indexOfSorted(GE, true);

        List<Integer> GT_temp = new ArrayList<>();
        List<Integer> GE_temp = new ArrayList<>();
        for (int i : GT_list) {
            if (GT[i] < 1000000) {
                GT_temp.add(i);
            }
        }
        for (int i : GE_list) {
            if (GE[i] > 0) {
                GE_temp.add(i);
            }
        }

        int lenGT = GT_temp.size();
        int lenGE = GE_temp.size();

        for (int ind = 0; ind < Math.min(lenGT, lenGE); ind++) {
            if (dt[GT_list[ind]] < dt[GE_list[ind]]) {
                int job = GT_list[ind];
                orderSeq.add(job);
                GT_temp.remove(new Integer(job));
                orderSeq.add(GE_list[ind]);
                GE_temp.remove(new Integer(GE_list[ind]));
            } else {
                int job = GE_list[ind];
                orderSeq.add(job);
                GE_temp.remove(new Integer(job));
                orderSeq.add(GT_list[ind]);
                GT_temp.remove(new Integer(GT_list[ind]));
            }
        }


        if (!GT_temp.isEmpty()) {
            for (int j : GT_temp) {
                orderSeq.add(j);
            }
        } else {
            for (int j : GE_temp) {
                orderSeq.add(j);
            }


        }
        return orderSeq;
    }

    private List<Integer> LSLWED(int[][] P, int[] WT, int[] WE, int[] dt, int J, int I) {
        List<Integer> orderSeq = new ArrayList<>();
        int[] GT = new int[J];
        int[] GE = new int[J];

        for (int j = 0; j < J; j++) {
            GT[j] = 1000000;
            if (WT[j] >= WE[j]) {
                GT[j] = WT[j];
            } else {
                GE[j] = WT[j];
            }
        }
        int[] GT_list = KminSort.indexOfSorted(GT);
        int[] GE_list = KminSort.indexOfSorted(GE, true);

        List<Integer> GT_temp = new ArrayList<>();
        List<Integer> GE_temp = new ArrayList<>();
        for (int i : GT_list) {
            if (GT[i] < 1000000) {
                GT_temp.add(i);
            }
        }
        for (int i : GE_list) {
            if (GE[i] > 0) {
                GE_temp.add(i);
            }
        }

        int lenGT = GT_temp.size();
        int lenGE = GE_temp.size();

        for (int ind = 0; ind < Math.min(lenGT, lenGE); ind++) {
            if (dt[GT_list[ind]] - P[GT_list[ind]][I - 1] < dt[GE_list[ind]] - P[GE_list[ind]][I - 1]) {
                int job = GT_list[ind];
                orderSeq.add(job);
                GT_temp.remove(new Integer(job));
                orderSeq.add(GE_list[ind]);
                GE_temp.remove(new Integer(GE_list[ind]));
            } else {
                int job = GE_list[ind];
                orderSeq.add(job);
                GE_temp.remove(new Integer(job));
                orderSeq.add(GT_list[ind]);
                GT_temp.remove(new Integer(GT_list[ind]));
            }
        }
        if (!GT_temp.isEmpty()) {
            for (int j : GT_temp) {
                orderSeq.add(j);
            }
        } else {
            for (int j : GE_temp) {
                orderSeq.add(j);
            }

        }
        return orderSeq;
    }

    private Solution calculateTWET(List<Integer> orderSeq, int[][] P, int[] WE,
                                   int[] WT, int[] de, int[] dt, int J, int I, int F) {

        List<List<Integer>> seq_current = new ArrayList<>();
        List<List<Integer>> best_seq = new ArrayList<>();
        ;
        for (int f = 0; f < F; f++) {
            List<Integer> temp = new ArrayList<>();
            temp.add(orderSeq.get(f));
            seq_current.add(temp);
        }

        for (int j = F; j < J; j++) {
            int minObj = 100000000;
            for (int f = 0; f < F; f++) {
                for (int pos = 0; pos < seq_current.get(f).size() + 1; pos++) {
                    List<List<Integer>> tempSeq = insertion(seq_current, f, pos, orderSeq.get(j));
                    int objTemp = BaseFunctions.calculateOBJ(tempSeq, P, WE, WT, de, dt, J, I);
                    if (minObj > objTemp) {
                        best_seq = new ArrayList<>();
                        for (List<Integer> seqF : tempSeq) {
                            best_seq.add(new ArrayList<>(seqF));
                        }
                        minObj = objTemp;
                    }
                }
            }
            seq_current = new ArrayList<>();
            for (List<Integer> seqF : best_seq) {
                seq_current.add(new ArrayList<>(seqF));
            }
        }

        Solution sol = new Solution(seq_current, P, WE, WT, de, dt, J, I);
        return sol;
    }

    private List<List<Integer>> insertion(List<List<Integer>> seq, int f, int index_position, int value) {
        List<List<Integer>> newSeq = new ArrayList<>();
        for (List<Integer> seqF : seq) {
            newSeq.add(new ArrayList<>(seqF));
        }
        newSeq.get(f).add(index_position, value);
        return newSeq;
    }

}
