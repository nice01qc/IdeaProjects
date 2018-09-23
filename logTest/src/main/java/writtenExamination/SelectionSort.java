package writtenExamination;
import java.util.*;

public class SelectionSort {

    public static void main(String[] args){
        int[] a = new int[]{1,99,2,32,12,87};

        selectionSort(a);
        System.out.println(Arrays.toString(a));
    }

    public static void selectionSort(int[] a){
        for(int i = 0; i < a.length; i++){
            for(int j = i + 1; j < a.length-1; j++){
                if(a[j] > a[j+1]){
                    exch(a,j,j+1);
                }
            }
        }
    }
    private static void exch(int[] a, int i, int j){
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }


}
