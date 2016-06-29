package com.example;

import com.example.domain.Car;
import com.example.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	private CarRepository carRepository;


	@Bean
	public CommandLineRunner demo(CarRepository repository) {
		return (args) -> {

			Car car1 = new Car("lexus","bla bla bla");
			Car car2 = new Car("mercedes","bla bla mercedes");
			Car car3 = new Car("lada","bla bla bla lada");

			System.out.println(repository.save(car1));
			System.out.println(repository.save(car2));
			System.out.println(repository.save(car3));
		};
	}

	@RequestMapping(value = "/update",method = RequestMethod.PATCH)
	public ResponseEntity<?> updateEntity(@RequestBody Car car){
		Car car2 = carRepository.findByModel(car.getModel());
		if (car.getLastVisibleVersion() != car2.getVersion() )  return new ResponseEntity(HttpStatus.CONFLICT);
		else {
			car2.setDescription(car.getDescription());
			carRepository.save(car2);
			return new ResponseEntity(HttpStatus.OK);
		}
	}



}
