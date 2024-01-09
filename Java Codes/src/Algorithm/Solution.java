package Algorithm;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    private List<List<Integer>> seq;
    private List<Integer> removed;
    private int obj;

    public Solution() {
        seq= new ArrayList<>();
        removed = new ArrayList<>();
        obj = (int) 1e10;
    }

    public Solution(List<List<Integer>> seq, int[][] P, int[] WE, int[] WT, int[] de, int[] dt, int J, int I) {
        this.seq = seq;
        this.obj = BaseFunctions.calculateOBJ(seq, P, WE, WT, de, dt, J, I);
        removed = new ArrayList<>();

    }


    public Solution copy() {
        List<List<Integer>> seq = new ArrayList<>();
        for (List<Integer> seqF : this.seq) {
            seq.add(new ArrayList<>(seqF));
        }
        List<Integer> removed = new ArrayList<>();
        for (int j : this.removed) {
            removed.add(j);
        }

        Solution copySol = new Solution();
        copySol.setSeq(seq);
        copySol.setObj(this.obj);
        copySol.setRemoved(removed);
        return copySol;
    }


    public void removeJob(int f, int j) {

        removed.add(j);
        seq.get(f).remove(new Integer(j));

    }
    public void removeBlockJob(int f,List<Integer>  block) {
        for (int j : block) {
            removeJob(f, j);
        }
    }

    public void removeJobIndex(int f, int ind) {
        removed.add(seq.get(f).get(ind));
        seq.get(f).remove(ind);

    }

    public void insertJobIndex(int f, int ind, int job) {
        removed.remove(new Integer(job));
        seq.get(f).add(ind, job);

    }

    public void insertBlockIndex(int f, int ind, List<Integer> block) {

        for (int index = block.size()-1; index >=0 ; index--) {
            int job = block.get(index);
            insertJobIndex(f,ind,job);
        }
    }

    public void evaluate(int[][] P, int[] WE, int[] WT, int[] de, int[] dt, int J, int I) {
        this.obj = BaseFunctions.calculateOBJ(seq, P, WE, WT, de, dt, J, I);
    }

    public List<Integer> getSeqF(int f) {
        return seq.get(f);
    }


    public List<List<Integer>> getSeq() {
        return seq;
    }

    public int getObj() {
        return obj;
    }

    public void setSeq(List<List<Integer>> seq) {
        this.seq = seq;
    }

    public void setObj(int obj) {
        this.obj = obj;
    }

    public void setRemoved(List<Integer> removed) {
        this.removed = removed;
    }

    public List<Integer> getRemoved() {
        return removed;
    }

    @Override
    public String toString() {
        String s = "";
        int count = 0;
        for (List<Integer> seqF : seq) {
            s += "f" + count + ":";
            s += seqF;
            s += "  ";
            count++;
        }
        s += "\nobj=" + obj + "\n";
        return s;
    }
}
