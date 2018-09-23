package writtenExamination;

import java.util.Arrays;

public class HeapSort {
    public static void main(String[] args) {
        int[] a = new int[]{1,99,2,32,12,87};
        heapSort(a);
        System.out.println(Arrays.toString(a));

    }
    public static void heapSort(int[] a){
        for(int i = a.length/2 - 1; i >= 0; i--){
            sink(a,i,a.length-1);
        }
        for(int i = a.length -1; i >= 0; i--){
            exch(a,0,i);
            sink(a,0,i);
        }
    }



    private static int getLeftChild(int i){
        return i * 2 + 1;
    }
    private static void exch(int[] a, int i, int j){
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static void sink(int[] a, int i, int end){
        int child;
        int temp;

        for(temp = a[i]; (child = getLeftChild(i)) < end;i = child) {
            if(child != end-1 && a[child] < a[child+1])
                child++;
            if(temp < a[child])
                a[i] = a[child];
            else break;
        }
        a[i] = temp;
    }
}
