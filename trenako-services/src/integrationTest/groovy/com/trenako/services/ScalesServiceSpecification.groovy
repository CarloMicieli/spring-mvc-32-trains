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
package com.trenako.services

import java.util.List;

import spock.lang.*

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query

import static org.springframework.data.mongodb.core.query.Query.*
import static org.springframework.data.mongodb.core.query.Criteria.*

import com.trenako.entities.Scale;
import com.trenako.services.ScalesServiceImpl;

/**
 * 
 * @author Carlo Micieli
 *
 */
@ContextConfiguration(locations = "classpath:META-INF/spring-context.xml")
class ScalesServiceSpecification {
	@Autowired MongoTemplate mongoTemplate
	@Autowired ScalesServiceImpl service
	
	def scales = Scale.class
	
	
	def setup() {
		def collection = [
			new Scale(name: "1", ratio: 32, gauge: 44.45, narrow: false),
			new Scale(name: "0", ratio: 43.5, gauge: 32, narrow: false),
			new Scale(name: "H0", ratio: 87, gauge: 16.5, narrow: false),
			new Scale(name: "N", ratio: 160, gauge: 9, narrow: false)
			]
		mongoTemplate.insert collection, scales
	}
	
	def cleanup() {
		def all = new Query()
		mongoTemplate.remove all, scales
	}
	
	def "should find all scales"() {
		when:
		def result = service.findAll()
		
		then:
		result != null
		result.size == 4
	}

	def "should find scales by id"() {
		given:
		def id = mongoTemplate.findOne(query(where("name").is("H0")), scales).id
		
		when:
		def scale = service.findById id
		
		then:
		scale != null
		scale.name == "H0"
	}
	
	def "should find scales by name"() {
		when:
		def scale = service.findByName "H0"

		then:
		scale != null
		scale.name == "H0"
	}
	
	def "should create new scales"() {
		given:
		def newScale = new Scale(name: "H0m", ratio: 87, gauge: 9, narrow: false)

		when:
		repo.save newScale
		
		then:
		newScale.id != null
	}
	
	def "should remove scales"() {
		given:
		def scale = mongoTemplate.findOne query(where("name").is("H0")), scales
		
		when:
		service.remove scale 
		
		then:
		def s = mongoTemplate.findOne query(where("name").is("H0")), scales
		s == null
	}
}
