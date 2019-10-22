package com.company;

import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentCounterImpl implements Counter{
    private final AtomicInteger value = new AtomicInteger(0);

    @Override
    public void increment() {
        value.incrementAndGet();
    }

    @Override
    public long getValue() {
        return value.get();
    }
}
