package passage2;

public class Linked {
    private int count=0;
    private Linked next=null;

    public Linked getNext() {
        return next;
    }

    public void setNext(Linked next) {
        this.next = next;
    }

    public static void main(String[] args) {
        Linked linked = new Linked();
        Linked begin= linked;

        for (int i=1;i<1;i++){
            linked.setNext(new Linked());
            linked= linked.getNext();
            linked.count+=i;
        }

        while(begin.getNext()!=null){
            System.out.print(begin.count);
            begin=begin.getNext();
        }

        System.out.println("ab".hashCode());
        System.out.println("ab".hashCode());


    }
}
