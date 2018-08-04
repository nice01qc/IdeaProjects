package find;

import java.util.Arrays;

public class Selection {

    public static void quickSort(int[] a) {
        quickSort(a, 0, a.length - 1);
    }
    private static void quickSort(int[] a, int begin, int end) {
        if (begin >= end) return;
        int euqal = begin;
        int i = begin+1;


    }


    public static void main(String[] args) {
        int[] a = {1, 2, 45, 3, 0};
        quickSort(a);
        System.out.println(Arrays.toString(a));
    }

}
