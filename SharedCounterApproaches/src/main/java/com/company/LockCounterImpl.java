package com.company;

import java.util.concurrent.locks.ReentrantLock;

public class LockCounterImpl implements Counter {
    private ReentrantLock lock = new ReentrantLock();

    private int value;
    @Override
    public void increment() {
        lock.lock();
        try{
            value++;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public long getValue() {
        return value;
    }
}
