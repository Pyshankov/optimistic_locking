package com.example.repository;

import com.example.domain.AbstractOptimisticLockEntity;

import java.io.Serializable;

/**
 * Created by pyshankov on 01.08.16.
 */
public interface BaseRepository<T extends AbstractOptimisticLockEntity, ID extends Serializable> {
    T persist(T var1);
}
