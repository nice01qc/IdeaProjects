import java.util.List;

public class TestThread implements Runnable{
    public List<Integer> list = null;

    public TestThread(List<Integer> list) {
        this.list = list;
    }

    public void run() {
        int  i = 0;
        for (int x : list){
//            System.out.print(x);
            i++;
        }
        System.out.println("----------" + i + "-----------");
    }
}
