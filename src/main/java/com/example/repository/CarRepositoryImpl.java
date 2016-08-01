package com.example.repository;

import com.example.domain.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by pyshankov on 01.08.16.
 */
@Component
public class CarRepositoryImpl extends  OptimisticRepository<Car,Long> {

    @Autowired
    private CarRepository repository;

    @Override
    protected Car performSave(Car s) {
        return repository.save(s);
    }

    @Override
    protected Car performGet(long aLong) {
        return repository.findOne(aLong);
    }
}
