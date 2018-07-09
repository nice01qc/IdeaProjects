public class DaJiang {

    public static void main(String[] args) {
        int a[] = {2,3,1,1,1,2,7,1,1,2,7};
        System.out.println(getMinJump(a,0));
    }

    public static int getMinJump(int[] a,int beginIndex){

        if (a == null || a.length < 1 || beginIndex >= a.length || beginIndex <0) return 0;

        int needStep = a.length -1 - beginIndex;
        if (a[beginIndex] >= needStep) return 2;
        if (a[a[beginIndex]] + a[beginIndex] >= needStep) return 1;

        int step = a[beginIndex] + a[a[beginIndex]];
        int index = 1;
        for (int i = 1; i <= a[beginIndex] && i+a[beginIndex] < a.length; i++){
            int tmp = i + a[beginIndex+i];
            if (tmp >= needStep) return 2;
            if (tmp > step){
                step = tmp;
                index=a[beginIndex+i];
            }
        }

        return 1+getMinJump(a,beginIndex+index);
    }

}
