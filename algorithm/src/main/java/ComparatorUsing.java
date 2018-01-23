import java.util.Comparator;

/**
 * comparator 的使用，对于比较只要传入实现了comparator接口的类就好了，
 * 每个实现类都有各自的比较方法
 */
public class ComparatorUsing {

    public static void sort(Object[] a, Comparator c){
        int  N = a.length;
        //此处使用了插入排序
        for (int i = 1; i<N;i++){
            for (int j = i;j>0&&less(c,a[j],a[j-1]);j--){
                exch(a,j,j-1);
            }
        }
    }


    private static boolean less(Comparator c,Object v,Object w){
        return c.compare(v,w)<0;
    }

    private static void exch(Object[] a, int i ,int j){
        Object t = a[i];
        a[i] = a[j];
        a[j] = a[i];
    }
}

//字符串的比较
class StringComparator implements  Comparator<String>{
    public int compare(String o1, String o2) {
        if (o1.length()<o2.length())return -1;
        else return 1;
    }
}
//整数的比较
class IntegerComparator implements  Comparator<Integer>{
    public int compare(Integer o1, Integer o2) {
        if (o1<o2)return -1;
        else return 1;
    }
}
