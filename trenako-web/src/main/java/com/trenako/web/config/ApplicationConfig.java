/*
 * Copyright 2012 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.trenako.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;

import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.WriteConcern;

/**
 * The configuration class for the Spring application context.
 * @author Carlo Micieli
 */
@Configuration
@ComponentScan(basePackages = "com.trenako")
@Profile("default")
@EnableMongoRepositories("com.trenako.repositories")
@ImportResource(value = {"classpath:META-INF/spring/spring-data.xml", 
		"classpath:META-INF/spring/spring-security.xml"})
public class ApplicationConfig extends AbstractMongoConfiguration {
	
	// Autowired from 'spring-data.xml' context
	private @Autowired SimpleMongoDbFactory mongoDbFactory;	
	
	/**
	 * Return the message source for multi-language management.
	 * @return the message source bean
	 */
	public @Bean MessageSource messageSource() {
		ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
		ms.setBasenames(new String[] { "locale/Messages", "locale/Errors" });
		return ms;
	}
	
	/**
	 * Returns a {@code MongoTemplate} instance.
	 * @return the mongodb template bean
	 * @throws Exception
	 */
	public @Bean MongoTemplate mongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
		mongoTemplate.setWriteConcern(WriteConcern.SAFE);
		return mongoTemplate;
	}
	
	@Override
	public SimpleMongoDbFactory mongoDbFactory() throws Exception {
		return mongoDbFactory;
	}
	
	@Override
	public String getDatabaseName() {
		return mongoDbFactory.getDb().getName();
	}
	
	@Override
	public Mongo mongo() throws Exception {
		return mongoDbFactory.getDb().getMongo();
	}
	
	@Override
	public String getMappingBasePackage() {
		return "com.trenako.mapping";
	}
}
