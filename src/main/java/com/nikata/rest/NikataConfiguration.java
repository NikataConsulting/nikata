/**
 * 
 */
package com.nikata.rest;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestTemplate;

import com.nikata.rest.constant.Property;

/**
 * @author Gaurav Oli
 * @date May 13, 2017 10:19:27 PM
 */
@Configuration
public class NikataConfiguration {
	@Autowired
	private Property property;

	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(property.getDriver());
		dataSource.setUrl(property.getUrl());
		dataSource.setUsername(property.getUsername());
		dataSource.setPassword(property.getPassword());
		return dataSource;
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
