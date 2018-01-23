package leedecode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Solution2 {
    public static List<Interval> merge(List<Interval> intervals) {
        if (intervals == null || intervals.size()==1) return intervals;

        int start, end;
        for (int i = 0; i < intervals.size(); i++) {
            start = intervals.get(i).start;
             end = intervals.get(i).end;
            for (int j = i+1; j < intervals.size(); j++) {
                Interval a = intervals.get(j);
                if (a.start >= start && a.start <= end){
                    end = end > a.end ? end : a.end;
                    intervals.remove(j);
                    j = i;
                }else if (a.end >= start && a.end <= end){
                    start = start < a.start ? start : a.start;
                    intervals.remove(j);
                    j=i;
                }else if(a.start < start && a.end > end){
                    start = a.start;
                    end = a.end;
                    intervals.remove(j);
                    j=i;
                }
            }
            intervals.get(i).start = start;
            intervals.get(i).end = end;
        }

        for (int i = 1; i < intervals.size(); i++){
            for (int j = i; j > 0 && intervals.get(j).start<intervals.get(j-1).start;j--){
                Interval temp = intervals.remove(j);
                intervals.add(j-1,temp);
            }
        }
        List<Integer> nums;

        return intervals;
    }

    public boolean isUnique(String str) {
        for (int i =0;i<str.length()-1;i++){
            if (str.charAt(i) == str.charAt(i+1))return false;
        }
        return true;
    }

    public static void main(String[] args) {

    }
}
