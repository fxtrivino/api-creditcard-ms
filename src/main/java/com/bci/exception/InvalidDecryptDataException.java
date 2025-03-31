package com.bci.exception;

public class InvalidDecryptDataException extends RuntimeException {
	    private static final long serialVersionUID = 1L;

		public InvalidDecryptDataException(String message) {
	        super(message);
	    }
}
