package com.bci.exception;

public class InvalidEncryptDataException extends RuntimeException {
	    private static final long serialVersionUID = 1L;

		public InvalidEncryptDataException(String message) {
	        super(message);
	    }
}
