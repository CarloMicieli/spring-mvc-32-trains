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
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;

import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.Mongo;
import com.mongodb.WriteConcern;

/**
 * The configuration class for the Spring application context.
 * @author Carlo Micieli
 */
@Configuration
@ComponentScan(basePackages = "com.trenako")
@Profile("production")
@PropertySource("classpath:META-INF/app.properties")
@ImportResource(value = {"classpath:META-INF/spring/spring-data.xml", 
		"classpath:META-INF/spring/spring-security.xml"})
public class ApplicationConfig extends AbstractMongoConfiguration {
	
	private @Autowired Environment env;
	
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
	 * Returns the mongodb factory for the production environment.
	 * @return the mongodb factory bean.
	 * @throws Exception if the mongo settings are not correct
	 */
	public @Bean SimpleMongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(mongo(), getDatabaseName());
	}
	
	/**
	 * Returns the mongodb template.
	 * @return the mongodb template bean.
	 * @throws Exception
	 */
	public @Bean MongoTemplate mongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
		mongoTemplate.setWriteConcern(WriteConcern.SAFE);
		return mongoTemplate;
	}

	@Override
	public String getDatabaseName() {
		return env.getProperty("mongo.databaseName");
	}

	@Override
	public Mongo mongo() throws Exception {
		return new Mongo(env.getProperty("mongo.hostName"), 
				env.getProperty("mongo.portNumber", Integer.class));
	}
	
	@Override
	public String getMappingBasePackage() {
		return "com.trenako.mapping";
	}
}
