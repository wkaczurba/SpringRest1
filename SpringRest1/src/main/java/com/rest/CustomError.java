package com.rest;

public class CustomError {
	private long code;
	private String message;
	
	public CustomError(long code, String message) {
		this.code = code;
		this.message = message;
	}
	/**
	 * @return the code
	 */
	public long getCode() {
		return code;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
}
