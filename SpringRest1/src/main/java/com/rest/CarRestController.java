package com.rest;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.data.CarNotFoundException;
import com.data.CarRepository;
import com.domain.Car;

// Requires: com.fasterxml.jackson.core:jackson-databind!!!
// Otherwise it will not produce JSON

@RestController
@RequestMapping("/cars")
public class CarRestController {
	
	@Autowired
	CarRepository repo;
	
	// Taken from: http://stackoverflow.com/questions/30532622/handling-cross-domain-preflight-ajax-options-requests-with-spring-mvc-4
	/*@RequestMapping(method = RequestMethod.OPTIONS, value = "/*")
	//@ResponseBody
	public ResponseEntity handleOptions() {
	    return new ResponseEntity(HttpStatus.NO_CONTENT);
	}*/	
	
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
		return repo.find(id); // TODO: This can throw a CarNotFoundException.
	}
	
	@ExceptionHandler(CarNotFoundException.class)
	public ResponseEntity<CustomError> carNotFoundExceptionHandler(CarNotFoundException e) {
		CustomError error = new CustomError(e.getId(), "Could not find car with the specified id");
		ResponseEntity<CustomError> response = new ResponseEntity<CustomError>(error, HttpStatus.NOT_FOUND);
		return response;
	}
	
	private Car exampleCar = new Car(2L, "Fiat", "Punto", 1000.0f, 1995); 
	@RequestMapping(path="/example", method=RequestMethod.GET, produces="application/json")
	public Car getStatic() {
		return exampleCar;
	}

	// How should I do that?
	// {"id":1,"make":"Toyota","model":"Corolla","engineSize":1398.0,"year":2008}
	@CrossOrigin
	@RequestMapping(path="/add", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<Car> add(@RequestBody Car car, UriComponentsBuilder ucb) {
		//System.out.println("add: car" + car);
		System.out.println("Adding car...");
		repo.add(car);
		System.out.println("Car added!");
		
		HttpHeaders headers = new HttpHeaders();
		URI uri = ucb.path("/cars/").path(String.valueOf(car.getId())).build().toUri();
		
		headers.setLocation(uri);
		return new ResponseEntity<Car>(car, headers, HttpStatus.CREATED);
	}
	
	// TODO: Add / Created status.
	
	
	/*@RequestMapping(path="/debugadd", method=RequestMethod.POST, produces="application/json")
	public Car dbgadd() {
		Car car = new Car(2L, "Fiat", "Punto", 1000.0f, 1995);
		repo.add(car);
		return car;
		
	}*/
	// [{"id":1,"make":"Toyota","model":"Corolla","engineSize":1398.0,"year":2008},{"id":2,"make":"Toyota","model":"Corolla","engineSize":1398.0,"year":2008},{"id":3,"make":"Toyota","model":"Corolla","engineSize":1398.0,"year":2008},{"id":4,"make":"Toyota","model":"Corolla","engineSize":1398.0,"year":2008},{"id":5,"make":"Toyota","model":"Corolla","engineSize":1398.0,"year":2008}]

}
