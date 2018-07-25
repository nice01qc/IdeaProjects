package javaSock.test;

import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.LinkedList;

public class TestSelector {

    private static final int MAXSIZE = 5;

    public static void main(String[] args) {
        Selector[] selectors = new Selector[MAXSIZE];

        int i = 0;
        try {
            for (i = 0; i < MAXSIZE; ++i) {
                selectors[i] = Selector.open();
            }
            System.out.println("finised ...");
            Thread.sleep(300000);
//            printTrack();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println(i);

            Exception e = new Exception("just log...");
            e.printStackTrace();
        }




    }


    public static void printTrack() {
        StackTraceElement[] st = Thread.currentThread().getStackTrace();
        if (st == null) {
            System.out.println("无堆栈...");
            return;
        }
        StringBuffer sbf = new StringBuffer();
        for (StackTraceElement e : st) {
            if (sbf.length() > 0) {
                sbf.append(" <- ");
                sbf.append(System.getProperty("line.separator"));
            }
            sbf.append(java.text.MessageFormat.format("{0}.{1}() {2}"
                    , e.getClassName()
                    , e.getMethodName()
                    , e.getLineNumber()));
        }
        System.out.println(sbf.toString());


    }
}
