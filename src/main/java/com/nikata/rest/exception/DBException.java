/**
 * 
 */
package com.nikata.rest.exception;

/**
 * @author Gaurav Oli
 * @date Mar 8, 2017 8:11:53 PM
 */
public class DBException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 8, 2017 8:13:10 PM
	 * @param arg0
	 * @param e
	 */
	public DBException(Throwable e) {
		super(e);
	}
}
