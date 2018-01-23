package passage3;


public class BST<Key extends Comparable<Key>,Value> {
    private Node root;

    private class Node{
        private Key key;
        private Value val;
        private Node left,right;
        private int N;              //以该节点为根的子树中的节点总数,null为0

        public Node(Key key,Value val,int N){
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }
    public int size(){
        return size(root);
    }

    private int size(Node node){
        if (node==null){
            return 0;
        }else {
            return node.N;
        }
    }

    //递归插入节点
    public void put(Key key,Value val){
        root = put(root,key,val);
    }
    private Node put(Node x,Key key,Value val){
        //如果存在则跟新
        //如果不存在则插入
        if (x == null) {
           return new Node(key,val,1);
        }
        int cmp = key.compareTo(x.key);
        if (cmp<0){
            x.left = put(x.left,key,val);
        }else if (cmp>0) {
            x.right = put(x.right,key,val);
        }else {
            x.val = val;
        }
        x.N = size(x.left)+size(x.right)+1;
        return x;
    }

    //递归查找结果
    public Value get(Key key){
        return get(root,key);
    }
    private Value get(Node x,Key key){
        //找到了返回对应的值，没找到返回null
        if (x == null){
            return null;
        }

        int cmp = key.compareTo(x.key);

        if (cmp<0){
            return get(x.left,key);
        }else if (cmp>0){
            return get(x.right,key);
        }else {
            return x.val;
        }
    }

    //删除操作
    public void delete(Key key){
        root = delete(root,key);
    }
    private Node delete(Node x,Key key){
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp<0){
            x.left  = delete(x.left, key);
        }else if (cmp >0){
            x.right = delete(x.right,key);
        }else{
            if (x.left == null){
                x = x.right;
                x.N = size(x.left)+size(x.right)+1;
                return x;
            }
            if (x.right == null){
                x = x.left;
                x.N = size(x.left)+size(x.right)+1;
                return x;
            }
            swin(x.left,x.right);
            x = x.left;
        }
        x.N = size(x.left)+size(x.right)+1;
        return x;
    }
    private void swin(Node x,Node r){

        if (x.right==null){
            x.right = r;
            return ;
        }else if (x.left == null){
            x.left = x.right;
            x.right = r;
        }else{
            Node right = x.right;
            x.right =r;
            swin(x.left,right);
        }
    }





















}
