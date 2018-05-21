package com;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairAndUnfairTest {
    private static Lock fairLock = new ReentrantLock2(true);
    private static Lock unFairLock = new ReentrantLock2(false);

    @Test
    public void unfair() {
        testLock(unFairLock);
    }

    @Test
    public void fair() {
        testLock(fairLock);
    }

    private void testLock(Lock lock) {
        for (int i = 0; i < 15; i++) {
            Job job = new Job(lock);
            job.start();
        }
    }

    private static class Job extends Thread {
        private Lock lock;
        public Job(Lock lock) {
            this.lock = lock;
        }

        public void run() {
            for (int i = 0; i < 2; i++) {
                lock.lock();
                System.out.print("curentThread:" + Thread.currentThread().getName() + "\tother:[");
                Collection<Thread> collection = ((ReentrantLock2) lock).getQueuedThreads();
                for (Thread thread : collection){
                    System.out.print(thread.getName().replaceAll("Thread-","") + " ");
                }
                System.out.println("]");
                lock.unlock();
            }

        }
    }


    private static class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        public Collection<Thread> getQueuedThreads() {
            List<Thread> arrayList = new ArrayList<Thread>(super.getQueuedThreads());
            Collections.reverse(arrayList);

            return arrayList;
        }
    }
}
