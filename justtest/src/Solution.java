import java.util.LinkedList;

public class Solution {
    public int JumpFloorII(int target) {
        if (target <= 0) return target;
        LinkedList<Integer> allnumList = new LinkedList<>();
        int result = 0;
        int tmp = 0;
        while (target-- > 0) {
            tmp = getSumList(allnumList) +1;
            allnumList.add(tmp);
        }
        return allnumList.get(allnumList.size()-1);
    }

    public int getSumList(LinkedList<Integer> linkedList){
        if (linkedList.isEmpty()) return 0;
        int sum = 0;
        for (int i = 0; i < linkedList.size(); i++){
            sum += linkedList.get(i);
        }
        return sum;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.JumpFloorII(12));
        System.out.println((int)Math.pow(2,12-1));
        System.out.println(2<<(12-2));
        System.out.println(2<<1);
    }
}