package com.example.repository;



import com.example.domain.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by pyshankov on 24.06.16.
 */
@RepositoryRestResource(collectionResourceRel = "car", path = "car")
public interface CarRepository  extends CrudRepository<Car,Long> {

   Car findByModel(String model);

   Car save(Car car);
}

