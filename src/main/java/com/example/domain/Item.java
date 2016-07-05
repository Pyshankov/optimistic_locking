package com.example.domain;

import javax.persistence.Entity;

/**
 * Created by pyshankov on 05.07.16.
 */
@Entity
public class Item extends AbstractOptimisticLockEntity {

    private String name;

    public Item() {
    }

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                "version "+ version  +
                '}';
    }
}
