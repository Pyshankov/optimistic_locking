package com.example;

import com.example.domain.Car;
import com.example.domain.Item;
import com.example.repository.BaseRepository;
import com.example.repository.CarRepository;
import com.example.repository.ItemRepository;
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

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@SpringBootApplication
public class DemoApplication<T> {

	@Autowired
	BaseRepository<Car,Long> carLongBaseRepository;
	@Autowired
	BaseRepository<Item,Long> itemLongBaseRepository;


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}




	@Bean
	public CommandLineRunner demo() {
		return (args) -> {
			System.out.println("hello world");

			Car car1 = new Car("lexus","bla bla bla");
			Car car2 = new Car("mercedes","bla bla mercedes");
			Car car3 = new Car("lada","bla bla bla lada");

			Item i = new Item("blab bla");

			System.out.println(itemLongBaseRepository.persist(i));

			car3.setItems(new ArrayList<>());
			car3.getItems().add(i);

			System.out.println(carLongBaseRepository.persist(car1));
			System.out.println(carLongBaseRepository.persist(car2));
			System.out.println(carLongBaseRepository.persist(car3));
		};
	}

	@RequestMapping(value = "/update",method = RequestMethod.PATCH)
	public void updateEntity(@RequestBody Car car){
		carLongBaseRepository.persist(car);
	}

}
