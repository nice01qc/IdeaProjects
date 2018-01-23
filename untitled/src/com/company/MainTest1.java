package com.company;


import java.io.File;

public class MainTest1 {
    public static void main(String[] args) {
        File file = new File("*");

        System.out.println(file.getAbsoluteFile());
        System.out.println();


    }
}
