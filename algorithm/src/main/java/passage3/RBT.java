package passage3;

public class RBT<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    public Node root;

    public boolean isEmpty() {
        return root == null;
    }

    public class Node {
        Key key;
        Value val;
        Node left,right;
        int N;
        boolean color;
        public Node(Key key,Value val,int N,boolean color){
            this.key = key;
            this.val = val;
            this.N = N;
            this.color = color;
        }
    }

    //判断红黑
    public boolean isRed(Node x){
        if (x == null){
            return false;
        }
        return x.color;
    }

    //插入
    public void put(Key key,Value val){
        root = put(root,key,val);
        root.color = BLACK;
    }
    private Node put(Node h,Key key,Value val){
        if (h == null){
            return new Node(key,val,1,RED);
        }
        int cmp = key.compareTo(h.key);
        if (cmp<0) {
            h.left = put(h.left,key,val);
        }else if (cmp > 0){
            h.right = put(h.right,key,val);
        }else{
            h.val = val;
        }

        return balance(h);
    }

    //删除最小键

    private Node deleteMin(Node h){
        if (h.left == null){
            return null;
        }
        if (isRed(h.left)             && !isRed(h.left.left)) {
            h = moveRedLeft(h);
        }
        h.left = deleteMin(h.left);
        return balance(h);
    }

    public void deleteMin(){
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = deleteMin(root);
        if (!isEmpty()){
            root.color = BLACK;
        }
    }

    //删除最大键
    public void deleteMax(){
        if (! isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
            root = deleteMax(root);
            if (!isEmpty()){
                root.color = BLACK;
            }
        }
    }
    private Node deleteMax(Node h){
        if (isRed(h.left)) {
            h = rotateRight(h);
        }
        if (h.right == null){
            return null;
        }
        if (!isRed(h.right) && ! isRed(h.right.left)) {
            h = moveRedRight(h);
        }
        h.right = deleteMax(h.right);
        return balance(h);
    }

    //根据key来删除

    private Node delete(Node h,Key key){

        if (h == null){
            return null;
        }

        if (key.compareTo(h.key) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left)) {
                h = moveRedLeft(h);
            }
            h.left = delete(h.left,key);
        }else {
            if (isRed(h.left)) {
                h = rotateRight(h);
            }
            if (key.compareTo(h.key) == 0 && h.right == null){
                return null;
            }
            if (!isRed(h.right) && !isRed(h.right.left)) {
                h = moveRedRight(h);
            }
            if (key.compareTo(h.key) == 0) {
                h.key = min(h.right);
                h.val = get(h.right,h.key);
                h.right = deleteMin(h.right);
            }
        }
        return balance(h);
    }

    //根据key来获取value
    private Value get(Node h, Key key) {
        int cmp = key.compareTo(h.key);

        if (h == null){
            return null;
        }
        if (cmp < 0){
            return get(h.left,key);
        }else if (cmp > 0){
            return get(h.right,key);
        }else{
            return h.val;
        }
    }

    //旋转
    Node rotateLeft(Node h){
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left)+size(h.right);
        return x;
    }
    Node rotateRight(Node h){
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left)+size(h.right);
        return x;
    }

    // 向右边借个兄弟过来，移到左边
    private Node moveRedLeft( Node h){
        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h.right);
            flipColors(h);
        }
        return h;
    }

    //向左边借个兄弟过来，移到右边
    private Node moveRedRight(Node h){
        flipColors(h);
        if (isRed(h.left.left)){
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }


    //颜色转换
    private Node flipColors(Node h){
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
        return h;
    }

    //平衡结构
    private Node balance(Node h){
        if (isRed(h.right) && !isRed(h.left)){
            h = rotateLeft(h);
        }
        if (isRed(h.left)&&isRed(h.left.left) ){
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)){
            h = flipColors(h);
        }

        h.N = 1 + size(h.left)+size(h.right);
        return h;
    }

    //大小
    private int size(Node node){
        if (node==null){
            return 0;
        }else {
            return node.N;
        }
    }


    private Key min(Node h)
    {
        Node x = h;
        while (x.left != null) x = x.left;
        if (x == null) return null;
        else return x.key;
    }

    //最小
    public Key min()
    {
        Node x = root;
        while (x.left != null) x = x.left;
        if (x == null) return null;
        else return x.key;
    }

}
