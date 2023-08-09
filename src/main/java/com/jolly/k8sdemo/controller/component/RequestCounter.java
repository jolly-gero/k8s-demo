package com.jolly.k8sdemo.controller.component;

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
