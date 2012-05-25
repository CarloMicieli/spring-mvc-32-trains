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
package com.trenako.services.mongo

import spock.lang.*

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query

import static org.springframework.data.mongodb.core.query.Query.*
import static org.springframework.data.mongodb.core.query.Criteria.*

import com.trenako.entities.Railway;

/**
 * 
 * @author Carlo Micieli
 *
 */
@ContextConfiguration(locations = "classpath:META-INF/spring-context.xml")
class RailwaysServiceSpecification extends Specification {

	@Autowired MongoTemplate mongoTemplate
	@Autowired RailwaysServiceImpl service
	
	def railways = Railway.class
	
	def setup() {
		def collection = [
			new Railway(name: "Die Bahn", companyName: "Deutsche Bahn AG",
				country: "DEU", operatingSince: 1994),
			new Railway(name: "DB", companyName: "Deutsche Bundesbahn",
				country: "DEU", operatingSince: 1949, operatingUntil: 1994),
			new Railway(name: "Sncf", companyName: "Société Nationale des Chemins de fer Français",
				country: "FRA", operatingSince: 1938)
			]
		mongoTemplate.insert collection, railways
	}
	
	def cleanup() {
		def all = new Query()
		mongoTemplate.remove all, railways
	}
	
	def "should find all railways"() {
		when:
		def result = service.findAll()
		
		then:
		result != null
		result.size == 3
	}
	
	def "should find railways by id"() {
		given:
		def id = mongoTemplate.findOne(query(where("name").is("DB")), railways).id
		
		when:
		def railway = service.findById id
		
		then:
		railway != null
		railway.id == id
		railway.name == "DB"		
	}
	
	def "should find railways by country"() {
		when:
		def result = service.findByCountry "DEU"
		
		then:
		result != null
		result.size == 2
		result.collect {it.name} == ["DB", "Die Bahn"]
	}
	
	def "should find railways by slug"() {
		when:
		def railway = service.findBySlug "die-bahn"
		
		then:
		railway != null
		railway.name == "Die Bahn"
	}
	
	def "should find railways by name"() {
		when:
		def railway = service.findByName "Sncf"

		then:
		railway != null
		railway.name == "Sncf"
	}
	
	def "should create new railways"() {
		given:
		def newRailway = new Railway(name: "FS", companyName: "Ferrovie dello stato",
			country: "ITA", operatingSince: 1903)
		
		when:
		service.save newRailway

		then:
		newRailway.id != null
		
		def railway = mongoTemplate.findById newRailway.id, railways
		railway.name == "FS"
		railway.slug == "fs"
		railway.companyName == "Ferrovie dello stato"
		railway.country == "ITA"
		railway.operatingSince == 1903
	}
	
	def "should remove railways"() {
		given:
		def railway = mongoTemplate.findOne query(where("name").is("DB")), railways
		
		when:
		service.remove railway
		
		then:
		def r = mongoTemplate.findById railway.id, railways
		r == null
	}
}
