package passage2;

import java.util.Random;

public class Selection {
    //全为升序
    //选择排序
    public static void sort1(Comparable[] a) throws InterruptedException {
        int N = a.length;
        for(int i =0;i<N;i++){

            for (int j=i+1;j<N;j++){
                Comparable max;
                if (less(a[j],a[i])){
                    max = a[i];
                    a[i]=a[j];
                    a[j]=max;
                }
            }
        }
    }
    //插入排序
    public static void sort2(Comparable[] a) throws InterruptedException {
        int N = a.length;
        for (int i =1;i<N;i++) {
            for (int j = i; j>0&&less(a[j],a[j-1]);j--){
                exch(a,j,j-1);
            }
        }
    }

    //希尔排序
    public static void sort3(Comparable[] a) throws InterruptedException {
        int N = a.length;
        int h=1;
        while(h<N/3)h=3*h+1;
        while(h>=1){
            for (int i=0;i<N;i++){
                for (int j=i;j>=h&&less(a[j],a[j-h]);j-=h){
                    exch(a,j,j-h);
                }
            }
            h/=3;
        }
    }

//自上而下的归并排序
    private static void merge(Comparable[] a,int lo, int mid,int hi){
        int i = lo,j=mid+1;
        Comparable[] aux = new Comparable[a.length];
        for (int k= lo;k<=hi;k++){
            aux[k] = a[k];
        }

        for (int k=lo;k<=hi;k++){
            if (i>mid)a[k]=aux[j++];
            else if (j>hi) a[k]=aux[i++];
            else if (less(aux[j],aux[i]))a[k]=aux[j++];
            else a[k]=aux[i++];
        }
    }
    private static Comparable[] aux=null;
    public static void sort4(Comparable[] a){
        aux = new Comparable[a.length];
        sort(a,0,a.length-1);
    }
    private static void sort(Comparable[] a,int lo,int hi){
        if (hi<=lo)return;
        if (hi-lo<50){
            sortbySort4(a,lo,hi);
            return;
        }
        int mid =lo+(hi-lo)/2;
        sort(a,lo,mid);
        sort(a,mid+1,hi);
        merge(a,lo,mid,hi);
    }
    private static void sortbySort4(Comparable[] a,int lo,int hi){
        if (hi-lo<=0)return ;
        int l=hi-lo;
        for (int i =lo;i<=l;i++){
            for (int j=i;j>lo && less(a[j],a[j-1]);j--)exch(a,j,j-1);
        }
    }
    //自下而上的归并排序
    public static void sort44(Comparable[] a){
        int N = a.length;
        aux =new Comparable[N];
        for (int sz=1;sz<N;sz+=sz){
            for (int lo=0;lo<N-sz;lo+=2*sz){
                merge(a,lo,lo+sz-1,Math.min(lo+2*sz,N-1));
            }
        }
    }

    //快速排序
    public static void sort5(Comparable[] a){
//        StdRandom.shuffle(a);
        sort55(a,0,a.length-1);
    }
    private static void sort55(Comparable[] a,int lo,int hi){
        if (hi<=lo)return;
        int j = partition(a,lo,hi);
        sort55(a,lo,j);
        sort55(a,j+1,hi);
    }

    private static int partition(Comparable[] a,int lo,int hi){
        int i = lo,j= hi+1;
        Comparable v = a[lo];
        while(true){
            while(less(a[++i],v))if (i==hi)break;
            while(less(v,a[--j]))if (j==lo)break;
            if (i>=j)break;
            exch(a,i,j);
        }
        exch(a,lo,j);
        return j;
    }





    public static boolean less(Comparable v,Comparable w){
        return v.compareTo(w)<=0;
    }

    public static void exch(Comparable[] a,int i,int j){
        Comparable t = a[i];
        a[i]=a[j];
        a[j]=t;
    }

    public static void show(Comparable[] a){
        for (int i=0;i<a.length;i++){
            System.out.print(a[i]+"\t");
        }
        System.out.println();
    }

    public static boolean isSorted(Comparable[] a){
        for (int i=1;i<a.length;i++){
            if (less(a[i],a[i-1]))return false;
        }
        return true;
    }
//     public static void drawRantangle(Comparable[] a) throws InterruptedException {
//         int num = a.length;
//         StdDraw.setXscale(0,1.1);
//         StdDraw.setYscale(0,10);
//         for (int i=0; i<num;i++){
//             double x = 1.1*i/num;
//
//
//         }
//         Thread.sleep(4000);
//         StdDraw.clear();
//
//
//     }

    public static void main(String[] args) throws InterruptedException {
         int num =200;
        Random random = new Random();
        Double[] integers = new Double[num];
        for (int i=0;i<num;i++){
            integers[i]=random.nextDouble()*10;
        }
        sort3(integers);

    }
}

