package com.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.domain.Car;

@Repository //(SKIP FOR NOW).
public class JdbcCarRepository implements CarRepository {
	
	private JdbcOperations jdbc;
	
	@Autowired
	public JdbcCarRepository(JdbcOperations jdbcOperations) {
		this.jdbc = jdbcOperations;
	}

	@Override
	public void add(Car car) {
		jdbc.update("INSERT INTO CARS (make, model, engineSize, year) values (?, ?, ?, ?)", 
				car.getMake(), car.getModel(), car.getEngineSize(), car.getYear());
	}

	@Override
	public List<Car> findAll() {
		// TODO Auto-generated method stub
		return jdbc.query("SELECT id, make, model, engineSize, year FROM CARS", new CarRowMapper());
	}
	
	@Override
	public Car find(long id) {
		try {
			return jdbc.queryForObject("SELECT id, make, model, engineSize, year FROM CARS WHERE id=?", new CarRowMapper(), id);	
		} catch (EmptyResultDataAccessException e) {
			throw new CarNotFoundException(id);
		}
	}

	public static class CarRowMapper implements RowMapper<Car> {

		@Override
		public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Car(
					rs.getLong("id"),
					rs.getString("make"),
					rs.getString("model"),
					rs.getFloat("engineSize"),
					rs.getInt("year"));
		}
		
	}
}
