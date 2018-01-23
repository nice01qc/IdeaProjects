package passage2;

import org.junit.Test;

public class TestRepeate {
    @Test
    public void test1(){
        IndexMinPQ<Integer> nice = new IndexMinPQ<Integer>(10);
//        System.out.println(nice.isEmpty());
        nice.insert(6,22);
        nice.insert(2,33);
        nice.insert(3,110);
        nice.insert(4,44);
        nice.insert(5,55);
        System.out.println(nice.isEmpty());
        System.out.println(nice.contains(5));
        nice.delMin();
        System.out.println(nice.min());
        System.out.println("nice.contains(2)-->"+nice.contains(2));
        System.out.println(nice.contains(4));


        nice.change(4,20);
        System.out.println(nice.size());
        System.out.println(nice.min());




    }
}
