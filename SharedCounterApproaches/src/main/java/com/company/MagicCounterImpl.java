package com.company;

public class MagicCounterImpl implements Counter {
    private LamportBackeryLockImpl lamport_backery_lock = new LamportBackeryLockImpl(5);
    private volatile int value = 0;

    @Override
    public void increment() {
        lamport_backery_lock.lock();
        try {
            value++;
        }
        finally {
            lamport_backery_lock.unlock();
        }
    }

    @Override
    public int getValue() {
        return value;
    }
}
