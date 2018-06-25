import java.util.concurrent.locks.ReentrantLock;

class AThread extends Thread {

    private ReentrantLock lock1;

    private ReentrantLock lock2;

    /**
     * @param lock1
     * @param lock2
     */
    public AThread(ReentrantLock lock1, ReentrantLock lock2) {
        super();
        this.lock1 = lock1;
        this.lock2 = lock2;
        this.setName("two");
    }



    public void run() {
        try {
            lock1.lock();
            Thread.sleep(3000);
            //  必须获取两个锁后才执行操作
            lock2.lock();
            System.out.println("A: I have all Locks!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock2.unlock();
            lock1.unlock();
        }
    }

}

class BThread extends Thread {

    private ReentrantLock lock1;

    private ReentrantLock lock2;

    /**
     * @param lock1
     * @param lock2
     */
    public BThread(ReentrantLock lock1, ReentrantLock lock2) {
        super();
        this.lock1 = lock1;
        this.lock2 = lock2;
        this.setName("one");
    }



    public void run() {
        try {
            lock2.lock();
            Thread.sleep(1000);
            //  必须获取两个锁后才执行操作
            lock1.lock();
            System.out.println("B: I have all Locks!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock1.unlock();
            lock2.unlock();
        }
    }



    //  测试程序主函数
    public static void main(String[] args) throws InterruptedException {
        final ReentrantLock lock1 = new ReentrantLock();
        final ReentrantLock lock2 = new ReentrantLock();
        new AThread(lock1, lock2).start();
        new BThread(lock1, lock2).start();
    }



}
