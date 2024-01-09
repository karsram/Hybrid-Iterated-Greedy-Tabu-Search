package Algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Data {

    public int[][] P;
    public int[] WE;
    public int[] WT;
    public int[] de;
    public int[] dt;
    public int F;
    public int J;
    public int I;


    public Data(String inputDir, int F, int J, int I, int Replication, double Tt, double R, int W) throws FileNotFoundException {

        this.F = F;
        this.I = I;
        this.J = J;

        P = new int[J][I];
        WE= new int[J];
        WT= new int[J];
        dt= new int[J];
        de= new int[J];



        String txtName = "I_" + F + "_" + J + "_" + I + "_" + Replication + "_" + Tt + "_" + R + "_" + W;
        String dir = inputDir + "\\" + txtName + ".txt";

        File file = new File(dir);
        Scanner input = new Scanner(file);
        input.nextInt();
        input.nextInt();
        input.nextInt();

        for (int j = 0; j < J; j++) {
            for (int i = 0; i < I; i++) {
                input.nextInt();
                P[j][i]=input.nextInt();
            }
        }
        for (int j = 0; j < J; j++) {
            WE[j]=input.nextInt();
        }
        for (int j = 0; j < J; j++) {
            WT[j]=input.nextInt();
        }
        for (int j = 0; j < J; j++) {
            de[j]=input.nextInt();
        }

        for (int j = 0; j < J; j++) {
            dt[j]=input.nextInt();
        }

    }


}
