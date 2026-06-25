package com.nt.exceptions;

public class InvalidSSNException extends RuntimeException {
	public InvalidSSNException() {
		super();
	}
	public InvalidSSNException(String msg) {
		super(msg);
	}

}
