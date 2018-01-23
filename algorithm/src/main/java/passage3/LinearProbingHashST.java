package passage3;

public class LinearProbingHashST<Key, Value> {
    private int N;          //符号表中的键值对总数
    private int M = 16;     //线性探测表的大小
    private Key[] keys;
    private Value[] vals;

    public LinearProbingHashST() {
        keys = (Key[]) new Object[M];
        vals = (Value[]) new Object[M];
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private void resize(int cap) {
        LinearProbingHashST<Key,Value> lph = new LinearProbingHashST();
        for (int i = 0; i < M; i++) {
            if (keys[i] != null){
                lph.put(keys[i],vals[i]);
            }
        }
        keys = lph.keys;
        vals = lph.vals;
        M = lph.M;
    }

    public void put(Key key, Value value) {
        if (N >= M / 2) resize(2 * M);
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                vals[i] = value;
                return;
            }
        }
        keys[i] = key;
        vals[i] = value;
        N++;
    }

    public Value get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (key.equals(keys[i])) {
                return vals[i];
            }
        }
        return null;
    }

    public boolean contain(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (key.equals(keys[i])) {
                return true;
            }
        }
        return false;
    }

    public void delete(Key key) {
        if (!contain(key)) return;
        int i = hash(key);
        while (!key.equals(keys[i])) i = (i + 1) % M;
        keys[i] = null;
        vals[i] = null;
        i = (i + 1) % M;
        while (keys[i] != null) {
            Key keyToRedo = keys[i];
            Value valueToRedo = vals[i];
            keys[i] = null;
            vals[i] = null;
            N--;
            put(keyToRedo, valueToRedo);
            i = (i + 1) % M;
        }
        N--;
        if (N > 0 && N == M / 8) resize(M / 2);
    }


}



























