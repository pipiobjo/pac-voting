/**
 * 
 */
package com.prodyna.pac.service.exception;

/**
 * @author bjoern
 *
 */
public class BackendServiceException extends Exception {

	/**
	 * 
	 */
	public BackendServiceException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public BackendServiceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public BackendServiceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BackendServiceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public BackendServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
