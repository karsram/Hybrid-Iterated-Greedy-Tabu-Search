package Test;

import Algorithm.BaseFunctions;
import Algorithm.Data;
import Algorithm.RandomFunctions;
import Algorithm.Solution;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BaseFunctionsTest {

    @Test
    void test_calculateET() {

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

        List<Integer> seqF = Arrays.asList(8, 4, 5);
        int[] ET = BaseFunctions.calculateET(seqF, P, de, dt, WE, WT, J, I);
        assertEquals(ET[8], 152);
        assertEquals(ET[4], 203);
        assertEquals(ET[5], 196);

        seqF = Arrays.asList(0, 2);
        ET = BaseFunctions.calculateET(seqF, P, de, dt, WE, WT, J, I);
        assertEquals(ET[0], 41);
        assertEquals(ET[2], 126);
    }

    @Test
    void test_calculateC() {
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

        List<Integer> seqF = Arrays.asList(8, 5);

        int[][] C = BaseFunctions.calculateC(seqF, P, de, dt, WE, WT, J, I);
        assertEquals(C[8][0], 97);
        assertEquals(C[8][1], 166);
        assertEquals(C[8][2], 206);
        assertEquals(C[8][3], 292);
        assertEquals(C[5][0], 166);
        assertEquals(C[5][1], 192);
        assertEquals(C[5][2], 292);
        assertEquals(C[5][3], 303);
    }

    @Test
    void test_metro() {
        int newObj = 60;
        int bestObj = 50;
        int T = (int) (50 * .2);
        double s = Math.exp(-(newObj - bestObj) / T);
        System.out.println(s);
    }

    @Test
    void test_caclulateObj() throws FileNotFoundException {
        String inputDir = "F:\\Job\\Projects\\Turkey\\Thesise\\Python Codes\\Small\\2\\";
        int F = 2;
        int J = 10;
        int I = 3;
        int Replication = 1;
        double Tt = 0.2;
        double R = 0.2;
        int W = 20;
        Data data = new Data(inputDir, F, J, I, Replication, Tt, R, W);
        List<List<Integer>> seq = new ArrayList<>();
        seq.add(Arrays.asList(4, 2, 0, 8, 9));
        seq.add(Arrays.asList(5, 7, 1, 3, 6));
        int obj = BaseFunctions.calculateOBJ(seq, data.P, data.WE, data.WT, data.de, data.dt, data.J, data.I);
        assertEquals(obj, 686);

    }

    @Test
    void test_roullet() {
        List<Double> value = Arrays.asList(100.0, 5.0, 12.0);
        for (int i = 0; i < 100; i++) {
            System.out.println(RandomFunctions.rouletteWheel(value,false));

        }



    }

}