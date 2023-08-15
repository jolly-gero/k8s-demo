package com.jolly.k8sdemo.component;

import org.springframework.stereotype.Component;

@Component
public class RequestCounter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }
}
