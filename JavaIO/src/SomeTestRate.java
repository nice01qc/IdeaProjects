import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.*;

public class SomeTestRate {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        int[] data  = {1,4};
        int num = 100000;
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < num; i++)
            for (int j=0; j<num; j++)swap1(data,0,1);
        long t2 = System.currentTimeMillis();
        System.out.println("判断的代价：" + (t2-t1));


        t1 = System.currentTimeMillis();
        for (int i = 0; i < num; i++)
            for (int j=0; j<num; j++)swap2(data,0,1);
        t2 = System.currentTimeMillis();
        System.out.println("交换的代价：" + (t2-t1));

        t1 = System.currentTimeMillis();
        for (int i = 0; i < num; i++)
            for (int j=0; j<num; j++)swap3();
        t2 = System.currentTimeMillis();
        System.out.println("自增的代价：" + (t2-t1));

    }

    private static void swap1(int[] data, int i, int j)
    {
        if (data[i] < data[j]){

        }
    }
    private static void swap3()
    {
        int i = 0;
        i++;
    }


    private static void swap2(int[] data, int i, int j)
    {
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }
}
