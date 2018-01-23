package passage2;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Random;

public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        int num = 50;
        StdDraw.setXscale(0,1);
        StdDraw.setYscale(0,10);
        Random random = new Random();
        Double[] integers = new Double[num];
        for (int i=0;i<num;i++){
            integers[i]=random.nextDouble()*10;
        }
        Selection.sort3(integers);

        for (int i=0; i<num;i++){
            double x = 1.0*i/num;
            double y = integers[i]/2.0;
            double rw = 0.5/num;
            double rh = integers[i]/2.0;
            StdDraw.filledRectangle(x,y,rw,rh);
        }


    }
}
