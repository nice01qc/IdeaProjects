public class Solution {
    public static boolean Find(int target, int [][] array) {

        for(int i=0,j=0;i<array.length;i++){
            for (; j < array[i].length && j >= 0;j++){
                if (array[i][j] < target) continue;
                else if (array[i][j] == target) return true;
                else {
                    if (j > 0 && array[i][j-1] > target){
                        j-=2;
                        continue;
                    }else if (j > 0 && array[i][j-1] == target){
                        return true;
                    }else {
                        j--;
                        break;
                    }
                }
            }
            if (j >= array[i].length) j=0;
        }
        return false;


    }


    public static void main(String[] args) {
        int[][] a = new int[][]{{1,2,3,4},{2,4,9,12},{4,7,10,13},{6,8,11,15}};
        System.out.println(Find(7,a));
    }
}