package passage5;

import edu.princeton.cs.algs4.Insertion;

import java.util.Arrays;

public class MSD {

    private static int R = 256;
    private static final int M = 15;
    private static String[] auxiliary;
    private static int charAt(String s, int d){
        if (d < s.length()) return s.charAt(d);
        else return -1;
    }

    public static void sort(String[] a)
    {
        int N = a.length;
        auxiliary = new String[N];
        sort(a,0,N-1,0);
    }
    private static void sort(String[] a, int lo, int hi, int d)
    {
        if (lo >= hi) return;
        int[] count = new int[R+2];
        for (int i = lo; i <= hi;i++)
        {
            count[charAt(a[i],d)+2]++;
        }
        for (int r = 0; r < R+1; r++)
        {
            count[r+1] += count[r];
        }
        for (int i = lo; i <= hi; i++)
        {
            auxiliary[count[charAt(a[i],d) + 1]++] = a[i];
        }
        for (int i = lo; i <= hi; i++){
            a[i] = auxiliary[i-lo];
        }
        System.out.println(Arrays.toString(count));
        for (int r = 0; r < R; r++){
            sort(a,lo + count[r],lo+count[r+1]-1,d+1);
        }
    }


    public static void main(String[] args) {
        String[] a = {"4pdf","2das","56sd","dk23","23ds","23df","56sd","dk23","23ds","23df","56sd","dk23","23ds","23df"};
        sort(a);
        System.out.println(Arrays.toString(a));
    }

}
