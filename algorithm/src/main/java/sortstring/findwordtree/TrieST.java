package sortstring.findwordtree;


public class TrieST<Value> {
    private static int R = 256;
    private Node root;

    private static class Node{
        private Object val;
        private Node[] next = new Node[R];
    }

    public Value get(String key){
        Node x = get(root,key,0);
        if (x==null)return null;
        return (Value) x.val;
    }
    private Node get(Node x,String key,int d){
        if (x==null)return null;
        if (d==key.length())return x;
        char c= key.charAt(d);   //找到地d个字符所对应的子单词查找树
        return get(x.next[c],key,d+1);
    }

    public void put(String key,Value val){
        root = put(root,key,val,0);
    }
    private Node put(Node x,String key,Value val,int d){
        if (x==null)x=new Node();
        if (d==key.length()){
            x.val=val;
            return x;
        }
        char c = key.charAt(d);     //找到第d个字符所对应的子单词查找树
        x.next[c] = put(x.next[c],key,val,d+1);
        return x;
    }
}























