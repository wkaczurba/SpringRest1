package com.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
	public Car add(Car car) {
		//jdbc.update("INSERT INTO CARS (make, model, engineSize, year) values (?, ?, ?, ?)", 
		//		car.getMake(), car.getModel(), car.getEngineSize(), car.getYear());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement("INSERT INTO CARS (make, model, engineSize, year) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, car.getMake());
				ps.setString(2, car.getModel());
				ps.setFloat(3, car.getEngineSize());
				ps.setInt(4,  car.getYear());
				return ps;
				
			}
		}, keyHolder);
		
		car.setId(keyHolder.getKey().longValue());
		return car;
/*
String sql = "INSERT INTO tbl (col) VALUES (?)";
preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1, col);
preparedStatement.executeUpdate();
generatedKeys = preparedStatement.getGeneratedKeys();
if (generatedKeys.next()) {
    long id = generatedKeys.getLong(1);
} else {
    // Throw exception?
} */
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
