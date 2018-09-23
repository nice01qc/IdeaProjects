package writtenExamination;

import java.util.Arrays;

public class InsertSort {

    public static void main(String[] args) {
        int[] a = new int[]{1,99,2,32,12,87};

        insertSort(a);
        System.out.println(Arrays.toString(a));
    }


    public static void insertSort(int[] a){
        for(int i = 1; i < a.length; i++){
            for(int j = i; j > 0; j--){
                if(a[j] < a[j-1]) exch(a,j,j-1);
                else break;
            }
        }
    }

    private static void exch(int[] a, int i, int j){
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

}
