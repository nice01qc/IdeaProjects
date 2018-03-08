package com;

import javafx.scene.layout.Priority;
import sun.applet.Main;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.function.Consumer;

public class MainTest implements Iterable,Iterator,Comparator<Integer> {
    public static FinalizeEscapeGC savehook = null;
    public static void main(String[] args) throws InterruptedException {

//        List<String> list = new ArrayList<>();
//        int i = 0;
//        while (true){
//            list.add(String.valueOf(i++).intern());
//        }


        PriorityQueue<String> nice = new PriorityQueue<>();
        nice.add("d");
        nice.add("c");
        nice.add("b");

        System.out.println(nice.peek());
        MainTest mainTest1 = new MainTest();
        MainTest mainTest2 = new MainTest();

        System.out.println(mainTest1.compare(3,5));


    }

    @Override
    public Iterator iterator() {

        return null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }

    @Override
    public int compare(Integer o1, Integer o2) {
        return o1.compareTo(o2);
    }
}
