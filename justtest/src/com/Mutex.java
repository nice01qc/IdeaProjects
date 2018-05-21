package com;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Mutex implements Lock {
    private final Sync sync = new Sync();
    // 静态内部类，自定义同步器
    private static class Sync extends AbstractQueuedSynchronizer{
        // 是否处于占有状态
        protected boolean isHeldExclusive(){
            return getState() == 1; // 有且只有一个获取锁
        }
        // 尝试性获取锁
        public boolean tryAcquire(int acquires){
            if (compareAndSetState(0,1)){
                return true;
            }
            return false;
        }
        // 释放锁，将状态设置为0
        protected boolean tryRelease(int releases){
            if (getState() == 0){
                throw new IllegalMonitorStateException(null);
            }
            setExclusiveOwnerThread(null);// 存放一个线程，其他线程可以安全获取
            setState(0);
            return true;

        }
        // 返回一个Condition, 每个condition 都包含了一个condition队列
        Condition newCondition(){
            return new ConditionObject();
        }

    }

    public void lock() {
        sync.acquire(1);
    }

    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    public void unlock() {
        sync.tryRelease(1);
    }

    public Condition newCondition() {
        return sync.newCondition();
    }

    public void lockInterruptibly() throws InterruptedException {

    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }
}
