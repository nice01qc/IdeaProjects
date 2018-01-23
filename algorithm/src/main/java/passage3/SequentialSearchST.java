package passage3;

import java.util.Iterator;

public class SequentialSearchST<Key,Value> {
    private Node first;
    private class Node{
        Key key;
        Value val;
        Node next;
        public Node(Key key,Value val,Node next){
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    public Value get(Key key){
        for (Node x = first;x!=null;x = x.next){
            if (x.key.equals(key)){
                return x.val;
            }
        }
        return null;
    }

    public void put(Key key,Value val){
        for (Node x= first;x!=null;x=x.next){
            if (x.key.equals(key)){
                x.val = val;
                return;
            }
        }
        first = new Node(key,val,first);
    }

    public int size(){
        int i = 0;
        for (Node x = first;x!=null;x=x.next){
            i++;
        }
        return i;
    }

    public int size(Key lo,Key hi){
        int num =0;
        int i =0;
        for (Node x = first;x!=null;x= x.next,i++){
            if (x.key.equals(lo)&&x.key.equals(hi)){
                num++;
                if (num == 1){
                    i =0;
                }else if (num ==2){
                    return i;
                }
            }
        }
        return -1;
    }

    Iterator<Key> keys(){
        return new newIterator();
    }

    class newIterator implements Iterator{

        Node x = first;
        public boolean hasNext() {
            return x!=null;
        }

        public Key next() {
            Node xx=x;
            x = x.next;
            return xx.key;
        }

        public void remove() {

        }
    }

    public void delete(Key key){
        Node before = null;
        Node after = null;
        if (key.equals(first.key)){
            first = first.next;
            return;
        }

        for (Node x = first;x.next!=null;x=x.next){
            if (x.next.key.equals(key)){
                before = x;
                if (x.next.next!=null){
                    after = x.next.next;
                }else {
                    before.next =null;
                    return;
                }
                before.next = after;
                return;
            }
        }
    }



}
