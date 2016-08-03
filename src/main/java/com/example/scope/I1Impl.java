package com.example.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by pyshankov on 03.08.16.
 */
public class I1Impl implements I1 {

    private static int i=0;
    private int j=0;

    @Override
    public void doExecute() {
        System.out.println("I1:"+i + " " + j);
        i++;
        j++;
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
