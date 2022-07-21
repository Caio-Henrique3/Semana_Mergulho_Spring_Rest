package com.semanaspring.rest.api.exception;

public class NegocioExeception extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NegocioExeception(String message) {
		super(message);
	}

}
