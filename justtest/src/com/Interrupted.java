import java.util.concurrent.TimeUnit;

public class Interrupted {

    public static void main(String[] args) throws InterruptedException {
        Thread sleepTahread = new Thread(new SleepRunner(),"");
        sleepTahread.setDaemon(true);

        Thread busyThread = new BusyRunner();
        busyThread.setDaemon(true);
        sleepTahread.start();
        busyThread.start();

        TimeUnit.SECONDS.sleep(1);

        sleepTahread.interrupt();
        busyThread.interrupt();
        System.out.println("sleep:" + sleepTahread.isInterrupted());
        System.out.println("busy: " + busyThread.isInterrupted());

        TimeUnit.SECONDS.sleep(1);

    }


    static class SleepRunner implements Runnable{
        @Override
        public void run() {
            while (true){
                if (Thread.currentThread().isInterrupted()){
                    System.out.println("my sleepRunner is Interrupted: ");
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("finally");
                }
            }

        }
    }


    static class BusyRunner extends Thread{
        @Override
        public void run() {
            while (true){
            }
        }
    }

}
