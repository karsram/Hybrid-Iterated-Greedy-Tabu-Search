package Test;

import Algorithm.Data;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {
    @Test
    void test_calculateET() throws FileNotFoundException {

        String inputDir = "F:\\Job\\Projects\\Turkey\\Thesise\\Python Codes\\Small\\2\\";
        int I = 4;
        int J = 10;
        int F = 2;
        int Replication = 3;
        double Tt = 0.6;
        double R = 0.2;
        int W = 20;

        Data data = new Data(inputDir,F, J, I, Replication, Tt, R, W);


        assertEquals(data.WE[0],3);int[] WE = {3, 8, 3, 7, 7, 6, 2, 7, 8, 3};
        assertEquals(data.WE[1],8);
        assertEquals(data.WE[9],3);
    }

}