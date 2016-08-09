package com.example.domain;

/**
 * Created by pyshankov on 09.08.16.
 */
public class ForProxyImpl implements ForProxy {
    @Override
    public void one() {
        System.out.println("one");
    }

    @Override
    public void two() {
        System.out.println("two");
    }
}
