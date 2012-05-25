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

import java.util.Arrays;
import java.util.List;

import spock.lang.*

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query

import static org.springframework.data.mongodb.core.query.Query.*
import static org.springframework.data.mongodb.core.query.Criteria.*

import com.trenako.entities.Brand;
import com.trenako.entities.Category;
import com.trenako.entities.Era;
import com.trenako.entities.PowerMethod;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;

/**
 * 
 * @author Carlo Micieli
 *
 */
@ContextConfiguration(locations = "classpath:META-INF/spring-context.xml")
class RollingStocksServiceSpecification extends Specification {

	@Autowired MongoTemplate mongoTemplate
	@Autowired RollingStocksServiceImpl service
	
	def rollingStocks = RollingStock.class
	
	def setup() {
		def acme = new Brand(name: "ACME")
		def bemo = new Brand(name: "Bemo")
		def roco = new Brand(name: "Roco")
		def brands = [acme, bemo, roco]
		mongoTemplate.insert brands, Brand.class
		
		def H0m = new Scale(name: "H0m")
		def H0 = new Scale(name: "H0")
		def scales = [H0m, H0]
		mongoTemplate.insert scales, Scale.class
		
		def MGB = new Railway(name: "MGB");
		def DRG = new Railway(name: "DRG");
		def railways = [MGB, DRG]
		mongoTemplate.insert railways, Railway.class
		
		def collection = [
			new RollingStock(brand: bemo, itemNumber: "1262 256",
				description: "MGB HGe 4/4 II 106 'St.Gotthard/Gottardo' Zahnradlok 'Glacier-Express'",
				category: Category.ELECTRIC_LOCOMOTIVES.keyValue(),
				era: Era.VI.name(),
				powerMethod: PowerMethod.DC.keyValue(),
				railway: MGB,
				scale: H0m,
				tags: ["glacier", "express"]),
			new RollingStock(brand: bemo, itemNumber: "3267 254",
				description: "MGB B 4254 Leichtmetallwagen",
				category: Category.PASSENGER_CARS.keyValue(),
				era: Era.V.name(),
				railway: MGB,
				scale: H0m)
			]
		mongoTemplate.insert collection, rollingStocks
	}
	
	def cleanup() {
		def all = new Query()
		
		mongoTemplate.remove all, rollingStocks
		mongoTemplate.remove all, Scale.class
		mongoTemplate.remove all, Brand.class
		mongoTemplate.remove all, Railway.class
	}
	
	def "should find all the rolling stocks"() {
		when:
		def results = service.findAll()
		
		then:
		results != null
		results.size == 2
	}
	
	def "should find rolling stocks by id"() {
		given:
		def id = mongoTemplate.findOne(query(where("slug").is("bemo-1262-256")), rollingStocks).id
		
		when:
		def rollingStock = service.findById id
		
		then:
		rollingStock != null
		rollingStock.slug == "bemo-1262-256"
		rollingStock.brand.name == "Bemo"
		rollingStock.itemNumber == "1262 256"
	}
	
	def "should find rolling stocks by slug"() {
		when:
		def rollingStock = service.findBySlug "bemo-1262-256"

		then:
		rollingStock != null
		rollingStock.slug == "bemo-1262-256"
		rollingStock.brand.name == "Bemo"
		rollingStock.itemNumber == "1262 256"
	}
	
}
