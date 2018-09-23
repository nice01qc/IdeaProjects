import java.util.concurrent.locks.LockSupport;

public class Test {
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LockSupport.park();
                System.out.println("I am parked....");
            }
        });
        thread.start();

        Thread.sleep(1000);

        System.out.println(" I can unparked you...");
        LockSupport.unpark(thread);

    }
}
