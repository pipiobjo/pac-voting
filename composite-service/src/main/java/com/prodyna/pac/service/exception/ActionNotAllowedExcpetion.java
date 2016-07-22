/**
 * 
 */
package com.prodyna.pac.service.exception;

/**
 * @author bjoern
 *
 */
public class ActionNotAllowedExcpetion extends VotingServiceException {

	/**
	 * 
	 */
	public ActionNotAllowedExcpetion() {
	}

	/**
	 * @param message
	 */
	public ActionNotAllowedExcpetion(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ActionNotAllowedExcpetion(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ActionNotAllowedExcpetion(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ActionNotAllowedExcpetion(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
