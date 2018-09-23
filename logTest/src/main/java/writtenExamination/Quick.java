package writtenExamination;

import java.util.*;
public class Quick {


    public static void main(String[] args) {
        int[] a = new int[]{10,9,44,1,22,312,12};
        quickSort(a);
        System.out.println(Arrays.toString(a));

    }

    public static void quickSort(int[] a){
        sort(a,0,a.length-1);
    }

    private static void sort(int[] a, int i, int j){
        if(i >= j) return;
        int mid = partition(a,i,j);
        sort(a,i,mid-1);
        sort(a,mid+1,j);
    }

    private static int partition(int[] a, int begin, int end){
       int i = begin;
       int j = end + 1;
       int judge = a[begin];
       while(true){
           while(a[++i] < judge)if(i >= end) break;
           while(a[--j] > judge)if(j <= begin) break;
           if(i >= j) break;
           exch(a,i,j);
       }
        exch(a,begin,j);
        return j;
    }

    private static void exch(int[]a,int i,int j){
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }


}
