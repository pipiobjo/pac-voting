/**
 * 
 */
package com.prodyna.pac.service.exception;

/**
 * @author bjoern
 *
 */
public class PersistenceException extends Exception {

	/**
	 * 
	 */
	public PersistenceException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public PersistenceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public PersistenceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PersistenceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public PersistenceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
