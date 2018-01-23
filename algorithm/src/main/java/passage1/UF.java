package passage1;


public class UF {
    private int [] id;  //分量id，以触点为索引
    private int count;  //分量数量
    public UF(int N){
        count = N;
        id = new int[N];
        for (int i=0; i<N;i++){
            id[i] = i;
        }
    }

    public int count(){
        return count;
    }
    public boolean connected(int p,int q){
        return find(p) == find(q);
    }

    public int find(int p){
        return id[p];
    }
    public void union(int p,int q){

    }

    public static void main(String[] args) {

    }

}
