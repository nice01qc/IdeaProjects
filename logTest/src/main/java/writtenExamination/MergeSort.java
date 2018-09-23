package writtenExamination;

import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] a = new int[]{1,99,2,32,12,87,12};

        merge(a);
        System.out.println(Arrays.toString(a));
    }

    public static void merge(int[] a){
        merge(a,0,a.length-1);
    }
    private static void merge(int[] a, int i, int j) {
        if (i >= j) return;
        int mid = i + (j- i) / 2;
        merge(a, i, mid);
        merge(a, mid + 1, j);
        merge(a,i,mid,j);
    }

    private static void merge(int[] a, int begin, int mid, int end) {
        int[] tmp = new int[a.length];
        for (int i = begin; i <= end; i++) {
            tmp[i] = a[i];
        }
        int p1 = begin;
        int p2 = mid + 1;
        for (int i = begin; i <= end; i++){
            if (p1 > mid) a[i] = tmp[p2++];
            else if (p2 > end) a[i] = tmp[p1++];
            else if (tmp[p1] < tmp[p2]) a[i] = tmp[p1++];
            else a[i] = tmp[p2++];
        }
    }
}
