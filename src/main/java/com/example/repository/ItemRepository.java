package com.example.repository;

import com.example.domain.Item;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by pyshankov on 05.07.16.
 */
public interface ItemRepository extends CrudRepository<Item,Long> {
    Item save(Item item);
}

