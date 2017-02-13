package com.data;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.domain.Car;

public interface CarRepository {

	Car add(Car add);
	List<Car> findAll();
	Car find(long id);
	
}
