package com.example.scope;


import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by pyshankov on 03.08.16.
 */
public class I2Impl implements I2 {

    @Override
    public void doExecute() {
        System.out.println("I2");
    }

    @PostConstruct
    public void init() {
        System.out.println("start request 1");
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("ends request 1");
    }
}
