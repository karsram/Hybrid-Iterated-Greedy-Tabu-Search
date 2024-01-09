package Test;

import Algorithm.NEH;
import Algorithm.RandomFunctions;
import Algorithm.Solution;
import org.junit.jupiter.api.Test;
import Algorithm.IteratedGreedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class IteratedGreedyTest {


    @Test
    void test_fuck() {
        List<Integer> l1 = new ArrayList<>();
        l1.add(1);
        l1.add(2);
        l1.add(3);
        l1.add(4);
        List<Integer> l2 = new ArrayList<>();
        l2.add(3);
        l2.add(4);
        l2.add(5);
        l2.add(6);

        l2.add(l2.size(), 10);

        System.out.println(
                Arrays.toString(RandomFunctions.Choice(2, 2)));


    }

    @Test
    void test_solve() throws ExecutionException, InterruptedException {

        int I = 4;
        int J = 10;
        int F = 2;
        int[][] P = {{34, 22, 14, 97,},
                {70, 87, 48, 85},
                {48, 38, 10, 65},
                {33, 5, 75, 85},
                {95, 92, 3, 41},
                {69, 26, 86, 11},
                {26, 9, 45, 85},
                {1, 11, 46, 30},
                {97, 58, 40, 51},
                {45, 88, 69, 85},
        };

        int[] WE = {3, 8, 3, 7, 7, 6, 2, 7, 8, 3};
        int[] WT = {7, 6, 7, 7, 7, 4, 6, 1, 5, 8};
        int[] de = {154, 163, 140, 144, 163, 147, 177, 163, 168, 135};
        int[] dt = {176, 170, 156, 206, 196, 214, 182, 178, 206, 190};

        int maxNOImprove = 10;
        int tempThreshold = 5;
        int tabuActivator = (int) (maxNOImprove * 0.8);
        int maxIteration = 1500;
        int minT = 1;
        int maxD = 5;
        int maxTime=1000;

        int tabuMaxNOImprove = 5;
        int tabuMaxIter = 40;
        int tabuMaxNeighbor = 100;
        int TT = 3;
        int alpha = 10;

        int[] success = {0, 0, 0};


        IteratedGreedy IG = new IteratedGreedy(maxTime,maxNOImprove, tempThreshold, tabuActivator, maxIteration, minT, maxD, alpha,
                tabuMaxNOImprove, tabuMaxIter, tabuMaxNeighbor, TT, success);
        IG.solve(P, WE, WT, de, dt, J, I, F);
    }

}