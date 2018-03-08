package com;

public class Test {
    public static void main(String[] args) {

        Priority<Integer> priority = new Priority<>();

        priority.insert(3);
        priority.insert(31);
        priority.insert(32);
        priority.insert(35);


        for (int i = priority.getSize(); i > 0 ; i--){
            System.out.println(priority.deMax());
        }

        System.out.println(priority.getSize());
    }
}
