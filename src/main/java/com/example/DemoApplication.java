package com.example;

import com.example.classloaderexample.MyClassLoader;
import com.example.component.*;
import com.example.domain.ForProxy;
import com.example.domain.ForProxyImpl;
import com.example.domain.OptimisticLockingHandler;
import com.example.repository.BaseRepository;
import com.example.scope.I;
import com.example.scope.I1Impl;
import com.example.scope.I2Impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;


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



	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {


		ClassLoader parentClassLoader = MyClassLoader.class.getClassLoader();
		MyClassLoader classLoader = new MyClassLoader(parentClassLoader);
		Class myObjectClass = classLoader.loadClass("com.example.domain.ForProxyImpl");

		ForProxy object1 = (ForProxy) myObjectClass.newInstance();


		//create new class loader so classes can be reloaded.
//		classLoader = new MyClassLoader(parentClassLoader);
		myObjectClass = classLoader.loadClass("com.example.domain.ForProxyImpl");

		ForProxy object2 = (ForProxy) myObjectClass.newInstance();


		System.out.println(object1.getClass());
		System.out.println(object1.getClass());



		InvocationHandler handler = (Object proxy, Method method, Object[] args1) -> {
				if(method.isAnnotationPresent(OptimisticLockingHandler.class)){
					System.out.println("begin");
					method.invoke(new ForProxyImpl());
					System.out.println("end");
				}else {
					method.invoke(new ForProxyImpl());
				}
				return proxy;
		};


		ForProxy proxy = (ForProxy) Proxy.newProxyInstance(
				ForProxy.class.getClassLoader(),
				new Class[] { ForProxy.class },
				handler);


		proxy.one();
		System.out.println();
		proxy.two();

//		Component letter = new Letter(new AvarageHeader(),new Body(),new SpecialFooter());
//		letter.print();

//		SpringApplication.run(DemoApplication.class, args);

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
		return  request.toString();
	}

	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(true); // true == allow create
	}


	public static List<Method> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation) {
		final List<Method> methods = new ArrayList<Method>();
		Class<?> klass = type;
		while (klass != Object.class) { // need to iterated thought hierarchy in order to retrieve methods from above the current instance
			// iterate though the list of methods declared in the class represented by klass variable, and add those annotated with the specified annotation
			final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));
			for (final Method method : allMethods) {
				if (method.isAnnotationPresent(annotation)) {
					Annotation annotInstance = method.getAnnotation(annotation);
					// TODO process annotInstance
					methods.add(method);
				}
			}
			// move to the upper class in the hierarchy in search for more methods
			klass = klass.getSuperclass();
		}
		return methods;
	}




}
