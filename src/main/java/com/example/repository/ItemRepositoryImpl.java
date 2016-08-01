package com.example.repository;

import com.example.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by pyshankov on 01.08.16.
 */
@Component
public class ItemRepositoryImpl extends OptimisticRepository<Item,Long> {

    @Autowired
    ItemRepository repository;

    @Override
    protected Item performSave(Item s) {
        return repository.save(s);
    }

    @Override
    protected Item performGet(long aLong) {
        return repository.findOne(aLong);
    }
}
