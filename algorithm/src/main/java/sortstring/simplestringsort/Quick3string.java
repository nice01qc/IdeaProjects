package sortstring.simplestringsort;

public class Quick3string {
    private static String[] aux;
    private static int charat(String s,int d){
        if (s==null)throw new NullPointerException("string is null");
        if (s.length()>d)return s.charAt(d);
        else return -1;
    }

    public static void sort(String[] a){

        aux = new String[a.length];
        sort(a,0,a.length-1,0,aux);
    }
    private static void sort(String[] a,int lo,int hi,int d,String[] aux){
        if (hi<=lo)return ;
        int lt = lo,gt = hi;
        int v=charat(a[lo],d);
        int i= lo+1;
        while(i<=gt){
            int t = charat(a[i],d);
            if (t<v)exch(a,lt++,i++);
            else if (t>v)exch(a,i,gt--);
            else i++;
        }
        StringTest2.sort(a,lo,lt-1,d,aux);
        if (v>=0)StringTest2.sort(a,lt,gt,d+1,aux);
        StringTest2.sort(a,gt+1,hi,d,aux);
    }
    private static void exch(String[] a,int m,int n){
        String temp = a[m];
        a[m] = a[n];
        a[n] = temp;
    }

    public static void main(String[] args){
        String[] data = new String[]{
                "4PGC938","2IYE0","3CI0720","1ICK750",
                "126V845","4J520","1K750","3CI750",
                "11HV845","12845","2RLA629","24L29","3TW723"
        };
        Quick3string.sort(data);
        StringTest2.myout(data);
    }
}
