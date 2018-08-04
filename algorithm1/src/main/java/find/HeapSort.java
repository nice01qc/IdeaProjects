package find;

import java.util.Arrays;

public class HeapSort {
    static int num = 0;

    public static void main(String[] args) {

        int[] a = {8, 6, 4, 5, 9,8, 6, 4, 5, 9,8, 6, 4, 5, 9};
        int arrayLength = a.length;

        for (int i = 0; i < arrayLength - 1; i++) {
            buildMaxHeap(a, arrayLength - 1 - i);
            swap(a, 0, arrayLength - 1 - i);
            System.out.println(Arrays.toString(a));
        }
        System.out.println("swap num: " + num);
    }

    public static void heapSort(int[] a)
    {
        int arrayLength = a.length;

        for (int i = 0; i < arrayLength - 1; i++)
        {
            buildMaxHeap(a, arrayLength - 1 - i);
            swap(a, 0, arrayLength - 1 - i);
        }
    }
    private static void buildMaxHeap(int[] data, int lastIndex)
    {
        for (int i = (lastIndex - 1) / 2; i >= 0; i--) {
            int k = i;
            if (k * 2 + 1 <= lastIndex) {
                int biggerIndex = 2 * k + 1;
                if (biggerIndex < lastIndex)
                    if (data[biggerIndex] < data[biggerIndex + 1]) biggerIndex++;
                if (data[k] < data[biggerIndex])
                    swap(data, k, biggerIndex);
            }
        }
    }

    // 交换
    private static void swap(int[] data, int i, int j) {
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
        num++;
    }
}