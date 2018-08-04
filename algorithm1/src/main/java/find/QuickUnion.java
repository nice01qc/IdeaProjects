package find;

public class QuickUnion {
    private int[] a;
    QuickUnion(int num){
        a = new int[num];
        for(int i = 0; i < num; i++) a[i] = i;
    }

    public int find(int i){
        while(i != a[i]) i = a[i];
        return i;
    }

    public void union(int i, int j){
        int headI = find(i);
        int headJ = find(j);
        if (headI == headJ) return ;

        a[i] = headJ;
    }

    public boolean pan(int i, int j){
        return find(i) == find(j);
    }

    public static void main(String[] args){
        QuickUnion quickUnion = new QuickUnion(10);
        quickUnion.union(1,2);
        quickUnion.union(4,3);
        quickUnion.union(7,2);

        System.out.println(quickUnion.pan(6,7));
    }

}
