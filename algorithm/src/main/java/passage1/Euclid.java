package passage1;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Random;

/**
 * 欧几里算法
 */
public class Euclid {
    //求两个数的最大公约数
    public static int gcd(int p,int q){
        if (q == 0) {
            return p;
        }
        int r = p % q;
        return gcd(q,r);
    }

    public static void main(String[] args) {
        System.out.println(gcd(200,488));
//        int N=100;
//        StdDraw.setXscale(0,N);
//        StdDraw.setYscale(0,N);
//        StdDraw.setPenColor(Color.blue);
//        StdDraw.setPenRadius(0.001);

//        int N = 50;
//        StdDraw.setXscale(-0.5,1.5);
//        StdDraw.setYscale(0,1.5);
//        double[] a = new double[N];
//        for (int i = 0; i<N;i++) {
//            a[i] = new Random().nextDouble();
//        }
//
//        for (int i = 0; i < N; i++ ) {
//            double x = 1.0*i/N;
//            double y = a[i]/2.0;
//            double rw = 0.3/N;
//            double rh = a[i]/2.0;
//            StdDraw.filledRectangle(x,y,rw,rh);
//        }
        int N = 100;
        StdDraw.setXscale(0,N);
        StdDraw.setYscale(0,N);
        Random random = new Random();
        StdDraw.setPenRadius(0.01);


        for (int i=0; i<10; i++) {
//            StdDraw.circle(random.nextInt(100),
//                    random.nextInt(100),1);
            double[] x = {random.nextInt(100),random.nextInt(100),random.nextInt(100),random.nextInt(100)};
            double[] y = {random.nextInt(100),random.nextInt(100),random.nextInt(100),random.nextInt(100)};

            StdDraw.text(30,30,"nice");
            StdDraw.setPenColor(random.nextInt(225),
                    random.nextInt(225),random.nextInt(225));

        }



    }
}
