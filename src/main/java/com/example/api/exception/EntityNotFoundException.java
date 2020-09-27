package com.example.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6294329520718655334L;

	public EntityNotFoundException() {
		
	}
	
	public EntityNotFoundException(String message) {
		super(message);
	}
	
	
}
