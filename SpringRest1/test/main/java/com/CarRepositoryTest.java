package com;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.config.AppConfig;
import com.config.DataConfig;
import com.data.CarNotFoundException;
import com.data.CarRepository;
import com.data.JdbcCarRepository;
import com.domain.Car;

import org.junit.Assert;

@ContextConfiguration(classes={AppConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class CarRepositoryTest {
	
	@Autowired
	CarRepository repo;

	@Test(expected=CarNotFoundException.class)
	public void repoTest() {
      Car toyota = new Car(1L, "Toyota", "Corolla", 1398.0f, 2008); 
	  Car punto = new Car(2L, "Fiat", "Punto", 1000.0f, 1995);
	  
	  repo.add(toyota);
	  repo.add(punto);
	  List<Car> cars = repo.findAll();
	  System.out.println(cars);
	  
	  Assert.assertEquals(2, cars.size());
	  Assert.assertEquals(toyota, repo.find(1L));
	  Assert.assertEquals(punto, repo.find(2L));
	  
	  // This should throw exception:
	  repo.find(3L);
	}
}
