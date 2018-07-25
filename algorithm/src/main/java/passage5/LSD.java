package passage5;

import java.util.Arrays;

// 低位优先的字符串排序
public class LSD {

    /**
     * @param W  通过前W个字符将a[] 排序
     */
    public static void sort(String[] a , int W)
    {
        int N = a.length;
        int R = 256;
        String[] auxiliary = new String[N];

        for (int d = W-1; d >= 0;d--)
        {
            int[] count = new int[R+1];
            for (int i = 0; i < N; i++){
                count[a[i].charAt(d) + 1]++;
            }
            for (int r = 0; r < R; r++){
                count[r+1] += count[r];
            }
            for (int i = 0; i < N; i++){
                auxiliary[count[a[i].charAt(d)]++] = a[i];
            }
            for (int i = 0; i < N; i++){
                a[i] = auxiliary[i];
            }
        }
    }


    public static void main(String[] args) {
        String[] a = {"4pdf","2das","56sd","dk23","23ds","23df"};
        sort(a,4);
        System.out.println(Arrays.toString(a));
    }


}
