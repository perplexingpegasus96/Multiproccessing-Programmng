package com.company;

import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentCounterImpl implements Counter{
    private final AtomicInteger value = new AtomicInteger(0);

    @Override
    public void increment() {
        while(true){
            int currentValue = getValue();
            int newValue = currentValue + 1;
            if (value.compareAndSet(currentValue, newValue)){
                return;
            }
        }
    }

    @Override
    public int getValue() {
        return value.get();
    }
}
