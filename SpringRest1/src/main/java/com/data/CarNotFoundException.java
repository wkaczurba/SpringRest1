package com.data;

public class CarNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6700207655637564090L;
	private long id;
	
	public CarNotFoundException(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

}
