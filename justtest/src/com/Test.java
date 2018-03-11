package com;

public class Test {

    public static void main(String[] args) {
        RedBlackBST<String,Integer> red = new RedBlackBST<String, Integer>();

        red.put("a",1);
        red.put("b",3);
        red.put("e",2);
        red.put("d",4);
        red.put("c",6);
        red.put("g",7);
        red.put("h",5);
        red.put("i",5);
        red.put("f",5);
        red.put("j",5);
        red.put("l",5);
        red.put("k",5);
        red.put("ff",5);
        red.put("ke",5);
        red.put("ks",5);
        red.put("s",5);
        red.put("x",5);
        red.put("xf",5);
        red.put("wk",5);
        red.put("kd",5);
        red.put("kf",5);
        red.put("kd",5);
        red.put("kd",5);
        red.put("fk",5);
        red.put("bk",5);
        red.put("dk",5);


        for (int i = red.size(); i > 0; i--){
            System.out.println(red.max());
            red.deleteMax();

        }
//        red.delete("fk");
//        red.delete("dk");
//
//        for (int i = red.size(); i > 0; i--){
//            System.out.println(red.min() + " : " + red.size());
//            red.deleteMin();
//
//        }
        red.printnum();


    }

}
