package Test;

import Algorithm.Solution;
import org.junit.jupiter.api.Test;
import Algorithm.NEH;

class NEHTest {

    @Test
    void copy() throws Exception {

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

        NEH neh = new NEH();

        Solution initial = neh.initialSol(P, WE, WT, de, dt, J, I, F);

        System.out.println(initial.toString());
    }

}