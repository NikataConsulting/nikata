/**
 * 
 */
package com.nikata.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gaurav Oli
 * @date May 13, 2017 10:16:13 PM
 */
@RestController
public class OTPController {
	
	@RequestMapping(value="/hello")
	public String hello() {
		return "Hello World";
	}

}
