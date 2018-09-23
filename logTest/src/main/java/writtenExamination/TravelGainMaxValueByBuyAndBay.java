package writtenExamination;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * 一路上上只能买一次，卖一次，一路上赚取的前最大为多少
 */
public class TravelGainMaxValueByBuyAndBay {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int num = Integer.parseInt(in.nextLine().trim());
        int[] a = new int[num];
        String[] aStrings = in.nextLine().trim().split(" ");
        for (int i = 0; i < num; i++) a[i] = Integer.parseInt(aStrings[i]);

        LinkedList<Integer> result = findMaxValueWay(0, a, 0, 1);
        System.out.println(result.get(0) + " " + result.get(1));
    }

    public static LinkedList<Integer> findMaxValueWay(int value, int[] a, int begin, int num) {
        LinkedList<Integer> result = new LinkedList<Integer>();
        if (begin >= a.length - 1) {
            result.add(0);
            result.add(0);
            return result;
        }
        int[] value1 = new int[a.length];
        int bigValue = Integer.MIN_VALUE;
        int next = 0;
        int nextValue = 0;
        for (int i = begin; i < a.length; i++) {
            value1[i] = findBigDiffValue(a, begin, i);
            if (next == 0 && value1[i] > next) {
                next = i;
                nextValue = value1[i];
            }
            if (value1[i] > bigValue) {
                bigValue = value1[i];
            }
        }
        result.add(bigValue + value);
        result.add(num * 2);

        LinkedList<Integer> other = null;
        if (next < a.length - 1) {
            other = findMaxValueWay(nextValue + value, a, next + 1, num + 1);
        }
        if (other != null && other.get(0) != 0) {
            return result.get(0) > other.get(0) ? result : other;
        } else {
            return result;
        }
    }

    private static int findBigDiffValue(int[] a, int begin, int end) {
        boolean[] pan = new boolean[a.length];
        int max = 0;
        for (int i = end; i >= begin; i--) {
            if (pan[i]) continue;
            for (int j = i - 1; j >= begin; j--) {
                if (a[i] - a[j] > max) max = a[i] - a[j];
                if (a[j] < a[i]) pan[j] = true;
            }
        }

        return max;
    }
}
