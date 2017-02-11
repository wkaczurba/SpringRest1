package com.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.data.CarRepository;
import com.domain.Car;

// Requires: com.fasterxml.jackson.core:jackson-databind!!!
// Otherwise it will not produce JSON

@RestController
@RequestMapping("/cars")
public class CarRestController {
	
	@Autowired
	CarRepository repo;
	
	// No need for @ResponseBody: Given the @RestController annotation -> @RequestMapping will suffice.
	@RequestMapping(method=RequestMethod.GET, produces="application/json")
	public List<Car> getAll() {
		//return new List<Car>;
		List<Car> list = repo.findAll();

		System.out.println("response: " + list);
		return list;
	}
	
//	 @RequestMapping(value="/{id}", method=RequestMethod.GET, produces="application/json")
//	  public Spittle spittleById(@PathVariable Long id) {

	@RequestMapping(path="/{id}", method=RequestMethod.GET, produces="application/json") 
	public Car get(@PathVariable long id) {
		
		return repo.find(id); // TODO: This can throw an exception.
	}
	
	private Car exampleCar = new Car(2L, "Fiat", "Punto", 1000.0f, 1995); 
	@RequestMapping(path="/example", method=RequestMethod.GET, produces="application/json")
	public Car getStatic() {
		return exampleCar;
	}

	// How should I do that?
	// {"id":1,"make":"Toyota","model":"Corolla","engineSize":1398.0,"year":2008}
	@RequestMapping(path="/add", method=RequestMethod.POST, consumes="application/json")
	public Car add(@RequestBody Car car) {
		System.out.println("add: car" + car);		
		repo.add(car);
		return repo.find(car.getId());
		
		// TODO: Should add resource address.
	}
	
	
	/*@RequestMapping(path="/debugadd", method=RequestMethod.POST, produces="application/json")
	public Car dbgadd() {
		Car car = new Car(2L, "Fiat", "Punto", 1000.0f, 1995);
		repo.add(car);
		return car;
		
	}*/
	// [{"id":1,"make":"Toyota","model":"Corolla","engineSize":1398.0,"year":2008},{"id":2,"make":"Toyota","model":"Corolla","engineSize":1398.0,"year":2008},{"id":3,"make":"Toyota","model":"Corolla","engineSize":1398.0,"year":2008},{"id":4,"make":"Toyota","model":"Corolla","engineSize":1398.0,"year":2008},{"id":5,"make":"Toyota","model":"Corolla","engineSize":1398.0,"year":2008}]

}
