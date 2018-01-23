package sortstring.simplestringsort;


import edu.princeton.cs.algs4.MSD;

//高位优先的字符串排序
public class StringTest2 {
    private static int R = 256;
    private static final int M = 0;
    private static String[] aux;


    public static int charAt(String s,int d){
        if (s==null)return -1;
        if (d<s.length())return s.charAt(d);
        else return -1;
    }

    public static void sort(String[] a){
        int N = a.length;
        aux = new String[N];
        sort(a,0,N-1,0,aux);
    }

    public static void sort(String[] a, int lo, int hi, int d,String[] aux) {
        if (hi<=lo+M){
            //可以写个函数把剩下的排了sort(String[] a,int lo,int hi,int d);
            smallSort(a,lo,hi,d);
            return ;
        }

        int[] count = new int[R+2];
        for (int i=lo;i<=hi;i++){
            count[charAt(a[i], d)+2]++;
        }

        for (int r=0;r<R+1;r++){
            count[r+1]+=count[r];
        }

        for (int i=lo;i<=hi;i++){
            int num = count[charAt(a[i],d)+1]++;
            aux[num]=a[i];
//            int num = count[charAt(a[i],d)+1];
//            if (num+lo==i){
//                System.out.println();
//            }
//            String temp=a[i];
////            num--;
//            a[i] = a[num+lo];
//            a[num+lo]=temp;
        }

        for (int i=lo;i<=hi;i++){
            a[i] = aux[i-lo];
        }

        for (int r=0;r<R;r++){
            sort(a,lo+count[r],lo+count[r+1]-1,d+1,aux);
        }
    }
    private static void smallSort(String[] a,int lo,int hi,int d){
        for (int i=lo;i<=hi;i++){
            for (int j=i;j>lo && less(a[j],a[j-1],d);j--){
                exch(a,j,j-1);
            }
        }
    }

    private static void exch(String[] a,int m,int n){
        String temp = a[m];
        a[m] = a[n];
        a[n] = temp;
    }
    public static boolean less(String a,String b,int d){
        return a.substring(d).compareTo(b.substring(d))<0;
    }

    public static void myout(String[] a){
        System.out.println();
        for (int i=0;i<a.length;i++){
            System.out.print(a[i]+"\t");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        String[] data = new String[]{
                "4PGC938","2IYE0","3CI0720","1ICK750",
                "126V845","4J520","1K750","3CI750",
                "11HV845","12845","2RLA629","24L29","3TW723"
        };
        StringTest2.myout(data);
        StringTest2.sort(data);
        StringTest2.myout(data);
        System.out.println();
        System.out.println("j4".compareTo("j333333333"));


//
//        MSD.sort(data);
//        StringTest2.myout(data);

    }
}
