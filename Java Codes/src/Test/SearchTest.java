package Test;

import Algorithm.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {

    @Test
    void test_tabu() throws FileNotFoundException, ExecutionException, InterruptedException {
        String inputDir = "F:\\Job\\Projects\\Turkey\\Thesise\\Python Codes\\Small\\2\\";

        int tabuMaxNOImprove = 5;
        int tabuMaxIter = 40;
        int tabuMaxNeighbor = 100;
        int TT = 3;
        int maxTime=100;

        int F = 2;
        int J = 16;
        int I = 3;
        int Replication = 5;
        double Tt = 0.2;
        double R = 0.6;
        int W = 20;
        int alpha = 5;
        Data data = new Data(inputDir, F, J, I, Replication, Tt, R, W);
        NEH neh = new NEH();
        Solution initial = neh.initialSol(data.P, data.WE, data.WT, data.de, data.dt, data.J, data.I, data.F);
double currentTime = 0;
        Solution optimal = Search.tabu(currentTime,maxTime,tabuMaxNOImprove, tabuMaxIter, tabuMaxNeighbor, TT, alpha,
                initial, 5, data.P, data.WE, data.WT, data.de, data.dt, data.J, data.I);

        System.out.println(optimal.toString());

    }
}