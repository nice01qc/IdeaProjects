import java.util.Arrays;

public class HeapSort {


    public static void main(String[] args) {
        int[] a = {3,5,2,8,9,0,1,4,6,7};
        sort(a);
        System.out.println(Arrays.toString(a));
    }


    public static void sort(int[] a){
        for(int i = a.length/2 + 1; i >= 0; i--){
            nodeDown(a,i,a.length);
        }
        for(int i = a.length -1; i >= 0; i--){
            swap(a,0,i);
            nodeDown(a,0,i);
        }
    }


    private static void nodeDown(int[] a, int num, int n){
        int child = -1;
        while((child = getLeftChild(num)) < n){
            if (child + 1 < n && a[child] < a[child + 1]) child++;
            if (a[num] < a[child]){
                swap(a,num,child);
            }
            num = child;
        }
    }

    private static int getLeftChild(int i){
        return i*2 + 1;
    }

    private static void swap(int[] a, int i, int j){
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

}
