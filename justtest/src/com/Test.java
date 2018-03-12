package com;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

    public static void main(String[] args) throws ParseException {

        String str = "2015-01-20 01:13:12";
        String str1 = "2015-01-20 00:13:12";
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = (Date) f.parseObject(str);
        Date d1 = (Date) f.parseObject(str1);

        System.out.println(d.getTime());
        System.out.println(d1.getTime());
        long diff = (d.getTime() - d1.getTime()) / 1000;
        System.out.println(diff);

    }

}
