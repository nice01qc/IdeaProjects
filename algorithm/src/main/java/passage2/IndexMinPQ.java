package passage2;

public class IndexMinPQ <Key extends Comparable<Key>> {
    private int maxN;   //索引优先队列中元素的最大个数
    private int N;      //当前索引优先队列中元素的个数
    private int[] pq;   //使用一级索引的二叉堆
    private int[] qp;   //pq的对称映射 qp[pq[i]] = pq[qp[i]] = i，用于映射key索引对应pq二叉堆中的位置
    private Key[] keys; //keys[i] = priority of i


    //初始化shuzu
    public IndexMinPQ(int maxN){
        this.maxN = maxN;
        this.N = 0;
        pq = new int[maxN+1];
        qp = new int[maxN+1];
        keys = (Key[]) new Comparable[maxN+1];
        for (int i=0;i<maxN+1;i++){
            qp[i] = -1;
        }
    }

    //插入
   public void insert(int key,Key keyValue){
        if (contains(key)){
            System.out.println("此元素已经存在！");
            return;
        }
        pq[++N] = key;
        qp[key] = N;
        keys[key] = keyValue;
        swim(N);
    }

    //改变这个keyValue的值
    public void change(int key,Key keyValue){
       if (!contains(key)){
           System.out.println("此元素不存在，不能修改");
           return;
       }
       keys[key] = keyValue;
       int k = qp[key];
       swim(k);
       sink(k);
    }

    //删除k
    public void delete(int key){
        if (!contains(key)){
            System.out.println("此元素不存在，无法删除！");
            return ;
        }
        int result = qp[key];

        exch(result,N);
        qp[pq[N--]] = -1;
        keys[key] = null;
        sink(result);
    }

    //返回最小值
    public Key min(){
        return keys[pq[1]];
    }

    //删除最小元素并返回Key
    public int delMin(){
        int key = pq[1];
        delete(key);
        return key;
    }

    //查看是否包含此元素
    public boolean contains(int key){
        return qp[key] != -1;
    }

    //判断是否为空
    public boolean isEmpty(){
        return N<=0;
    }

    //返回队列数量
    public int size(){
        return N;
    }

    private boolean less(int i,int j){
        int ki = pq[i];
        int kj = pq[j];
        return keys[ki].compareTo(keys[kj])<0;
    }

    private void exch(int i, int j){
        int mid = pq[i];
        qp[(pq[i] = pq[j])] = i;
        qp[(pq[j] = mid)] = j;
    }

    private void swim(int k){
        while(k>1&&less(k,k/2)){
            exch(k,k/2);
            k = k/2;
        }
    }

    private void sink(int k){
        while(2*k<=N){
            int j = 2*k;
            if (j<N && less(j+1,j))j++;
            if (less(k,j))break;
            exch(k,j);
            k = j;
        }
    }


}
