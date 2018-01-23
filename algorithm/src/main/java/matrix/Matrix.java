package matrix;

public class Matrix {
    //向量点乘
    public static double dot(double[] x, double[] y){
        double result =0;
        if (x.length != y.length){
            return -1;
        }
        for (int i=0;i<x.length;i++){
            result+=x[i]*y[i];
        }

        return result;
    }

    //矩阵和矩阵之积
    public static double[][] mult(double[][] a,double[][] b){
        if (a[0].length!=b.length){
            return null;
        }
        double[][] result = new double[a.length][b[0].length];
        for (int i=0;i<a.length;i++){
            for (int j=0;j<b[0].length;j++){
                for (int m =0;m<b[0].length;m++){
                    result[i][j]+=a[i][m]*b[m][j];
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
//        System.out.println(dot(new double[]{1,2,3},new double[]{1,2,3}));
//        double[][] result = mult(new double[][]{new double[]{1,2},new double[]{1,2},new double[]{1,2}},new double[][]{new double[]{1,1},new double[]{1,1}});
////        result=new double[][]{new double[]{1,2},new double[]{1,2},new double[]{1,2}};
//        for (int i=0;i<result.length;i++){
//            for (int j=0;j<result[0].length;j++){
//
//                System.out.print(result[i][j]+"\t");
//            }
//            System.out.println();
//        }
        System.out.println(1.0/36.0);
    }
}






















