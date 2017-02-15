package com.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Car {
	private Long id;
	private String make;
	private String model;
	private float engineSize;
	private int year;
	
	public Car() {}
	public Car(Long id, String make, String model, float engineSize, int year) {
		this.id = id;
		this.make = make;
		this.model = model;
		this.engineSize = engineSize;
		this.year = year;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the make
	 */
	public String getMake() {
		return make;
	}
	/**
	 * @param make the make to set
	 */
	public void setMake(String make) {
		this.make = make;
	}
	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * @return the engineSize
	 */
	public float getEngineSize() {
		return engineSize;
	}
	/**
	 * @param engineSize the engineSize to set
	 */
	public void setEngineSize(float engineSize) {
		this.engineSize = engineSize;
	}
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Car [id=" + id + ", make=" + make + ", model=" + model + ", engineSize=" + engineSize + ", year=" + year
				+ "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "id");
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object that) {
		return EqualsBuilder.reflectionEquals(this, that, "id");
	}
}
