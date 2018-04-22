import java.util.concurrent.TimeUnit;

public class WaitNotify {
    static Object lock = new Object();


    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new Wait());
        thread.start();

        TimeUnit.SECONDS.sleep(1);
        synchronized (lock){
            lock.notify();
        }

    }


    static class Wait implements Runnable{
        @Override
        public void run() {
            synchronized (lock){
                try {
                    lock.wait(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("continue : ");
            }
        }
    }
}
