package passage1;

public class Solution1 {
    public int uniquePaths(int m, int n) {
        int result=1;
        int max = Math.max(m,n)-1;
        int min = Math.min(m,n)-1;
        System.out.println(max+":"+min);
        int[] mn=new int[min];
        int[] mins=new int[min];
        for (int i=1;i<=min;i++){
            mn[i-1]=max+i;
            mins[i-1]=i;
        }

        for (int i=0;i<mins.length;i++){

                for (int j=0;j<mn.length;j++){
                    int coms=comDiv(mn[j],mins[i]);
                    if (coms !=1){
                        mn[j]/=coms;
                        mins[i]/=coms;
                    }else{
                        continue;
                    }
                    if (mins[i]==1){
                        break;
                    }
                }

        }


        for (int i=0;i<mn.length;i++){
            result*=mn[i];

        }
        return result;
    }

    public int comDiv(int a,int b){
        int c=a%b;
        if(c==0){
            return b;
        }else{
            return comDiv(b,c);
        }
    }

    public static void main(String[] args) {
        Solution1 s = new Solution1();
        System.out.println(s.uniquePaths(9,13));

        System.out.println(s.comDiv(91,18));
    }
}
