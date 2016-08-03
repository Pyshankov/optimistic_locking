package com.example;

import com.example.scope.I;
import com.example.scope.I1Impl;
import com.example.scope.I2Impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;


@RestController
@SpringBootApplication
public class DemoApplication<T> {

//	@Autowired
//	BaseRepository<Car,Long> carLongBaseRepository;
//	@Autowired
//	BaseRepository<Item,Long> itemLongBaseRepository;

	@Bean
	@Scope(scopeName = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public I getI() {
		if((long)session().getAttribute("id")==1) return new I1Impl();
		else return new I2Impl();
	}


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}


//	@Bean
//	public CommandLineRunner demo() {
//		return (args) -> {
//			System.out.println("hello world");
//
//			Car car1 = new Car("lexus","bla bla bla");
//			Car car2 = new Car("mercedes","bla bla mercedes");
//			Car car3 = new Car("lada","bla bla bla lada");
//
//			Item i = new Item("blab bla");
//
//			System.out.println(itemLongBaseRepository.persist(i));
//
//			car3.setItems(new ArrayList<>());
//			car3.getItems().add(i);
//
//			System.out.println(carLongBaseRepository.persist(car1));
//			System.out.println(carLongBaseRepository.persist(car2));
//			System.out.println(carLongBaseRepository.persist(car3));
//		};
//	}
//
//	@RequestMapping(value = "/update",method = RequestMethod.PATCH)
//	public void updateEntity(@RequestBody Car car){
//		carLongBaseRepository.persist(car);
//	}

	@Autowired
	I request;

	@RequestMapping(value = "/api/{id}" ,method = RequestMethod.GET)
	public String getScope(@PathVariable long id){
		session().setAttribute("id",id);
		request.doExecute();
		return  id+"";
	}

	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(true); // true == allow create
	}

}
