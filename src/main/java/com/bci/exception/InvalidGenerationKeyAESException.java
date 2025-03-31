package com.bci.exception;

public class InvalidGenerationKeyAESException extends RuntimeException {
	    private static final long serialVersionUID = 1L;

		public InvalidGenerationKeyAESException(String message) {
	        super(message);
	    }
}
