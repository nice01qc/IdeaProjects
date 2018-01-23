package passage3;

import java.util.Comparator;

public class BinarySearchST<Key extends Comparable<Key>,Value> {
    private Key[] keys;
    private Value[] vals;
    private int N;

    public BinarySearchST(int capcity){
        keys = (Key[]) new Comparable[capcity];
        vals = (Value[]) new Object[capcity];
    }

    public int size(){
        return N;
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public Value get(Key key){
        if (isEmpty()){
            return null;
        }
        int i = rank(key);
        System.out.println(i<N );
        if (i<N && key.compareTo(keys[i]) == 0){
            return vals[i];
        }else{
            return null;
        }
    }

    public void put(Key key,Value val){
        int i = rank(key);
        if (i<N && key.compareTo(keys[i])==0){
            vals[i] = val;
            return;
        }
        for (int j = N;j>i;j--){
            keys[j] = keys[j-1];
            vals[j] = vals[j-1];
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    public void delete(Key key){
        int i = rank(key);
        if (key.compareTo(keys[i])!=0){
            return ;
        }
        for (int j = i;j<N;j++){
            keys[j] = keys[j+1];
            vals[j] = vals[j+1];
        }
        keys[N-1] = null;
        vals[N-1] = null;
        N--;
    }

    public Key minkey(){
        return keys[0];
    }

    public Key maxKey(){
        return keys[N-1];
    }

    public Key selectKey(int k){
        return keys[k];
    }

    public Key ceilling(Key key){
        return keys[rank(key)];
    }

    public Key floor(Key key){
        int i = rank(key);
        if (i==0) {
            return keys[0];
        }
        return keys[i-1];
    }

    //递归查找
    public int rank(Key key,int lo,int hi){
        if (hi<lo)return lo;
        int mid = lo + (hi-lo)/2;
        int cmp = key.compareTo(keys[mid]);
        if (cmp<0){
            return rank(key,lo,mid-1);
        }else if (cmp>0){
            return rank(key,mid+1,hi);
        }else{
            return mid;
        }
    }
    //迭代查找
    public int rank(Key key){
        int lo = 0,hi = N-1;
        while(lo<=hi){
            int mid = lo + (hi-lo)/2;
            int comp = key.compareTo(keys[mid]);
            if (comp<0){
                hi = mid-1;
            }else if (comp>0){
                lo = mid+1;
            }else {
                return mid;
            }
        }
        return lo;
    }

}
