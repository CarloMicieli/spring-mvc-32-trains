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
package com.trenako;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
	classes = {TestConfiguration.class})
@ActiveProfiles("test")
public abstract class AbstractMongoIntegrationTests<T> {

	private @Autowired MongoTemplate mongoOps;
	private final Class<T> clazz;
		
	public AbstractMongoIntegrationTests(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	protected void save(Object obj) {
		mongoOps.save(obj);
	}

	protected void remove(Object obj) {
		mongoOps.remove(obj);
	}
	
	protected void dropCollection(Class<?> clazz) {
		mongoOps.remove(new Query(), clazz);
	}
	
	protected void init(Collection<T> items) {
		mongoOps.insert(items, clazz);
	}
	
	protected void cleanUp() {
		mongoOps.remove(new Query(), clazz);
	}	
}
