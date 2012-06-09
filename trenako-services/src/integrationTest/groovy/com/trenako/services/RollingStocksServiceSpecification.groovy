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
import com.trenako.entities.DeliveryDate;
import com.trenako.entities.Era;
import com.trenako.entities.PowerMethod;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;
import com.trenako.services.RollingStocksServiceImpl;

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
		
		def H0m = new Scale(name: "H0m", ratio: 87)
		def H0 = new Scale(name: "H0", ratio: 87)
		def N = new Scale(name: "N", ratio: 160)
		def scales = [H0m, H0, N]
		mongoTemplate.insert scales, Scale.class
		
		def MGB = new Railway(name: "MGB", country: 'SWI')
		def DRG = new Railway(name: "DRG", country: 'DEU')
		def FS = new Railway(name: "FS", country: 'ITA')
		def DB = new Railway(name: "DB", country: 'DEU')
		def railways = [MGB, DRG, FS, DB]
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
				scale: H0m),
			new RollingStock(brand: acme, itemNumber: "69501",
				description: "Gr 685 172",
				category: Category.STEAM_LOCOMOTIVES.keyValue(),
				powerMethod: PowerMethod.DC_DCC_SOUND.keyValue(),
				era: Era.III.name(),
				railway: FS,
				tags: ['museum'],
				scale: H0),
			new RollingStock(brand: roco, itemNumber: "43858",
				description: "Electric loco 101 0004-0",
				category: Category.ELECTRIC_LOCOMOTIVES.keyValue(),
				powerMethod: PowerMethod.AC.keyValue(),
				era: Era.V.name(),
				railway: DB,
				scale: H0)
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
		def results = service.findAll(null)
		
		then:
		results != null
		results.size == 4
	}
	
	def "should find a rolling stock by id"() {
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
	
	def "should find a rolling stock by slug"() {
		when:
		def rollingStock = service.findBySlug "bemo-1262-256"

		then:
		rollingStock != null
		rollingStock.slug == "bemo-1262-256"
		rollingStock.brand.name == "Bemo"
		rollingStock.itemNumber == "1262 256"
	}
	
	def "should find all rolling stocks for a brand"() {
		when:
		def results = service.findByBrand "ACME"
		
		then:
		results != null
		results.size == 1
		results.collect{it.slug} == ['acme-69501']		 
	}
	
	def "should find all rolling stocks by railway"() {
		when:
		def results = service.findByRailwayName "FS"
		
		then:
		results != null
		results.size == 1
		results.collect{it.slug} == ['acme-69501']
	}
	
	def "should find all rolling stocks by brand and category"() {
		when:
		def results = service.findByBrandAndCategory "Bemo", "electric-locomotives"
		
		then:
		results != null
		results.size == 1
		results.collect{it.slug} == ['bemo-1262-256']
	}
	
	def "should find all rolling stocks by brand and era"() {
		when:
		def results = service.findByBrandAndEra "ACME", "III"
	
		then:
		results != null
		results.size == 1
		results.collect{it.slug} == ['acme-69501']
	}
	
	def "should find all rolling stocks by brand and railway"() {
		when:
		def results = service.findByBrandAndRailway "Roco", "DB"
	
		then:
		results != null
		results.size == 1
		results.collect{it.slug} == ['roco-43858']
	}
	
	def "should find all rolling stocks by brand and scale"() {
		when:
		def results = service.findByBrandAndScale "Bemo", "H0m"
	
		then:
		results != null
		results.size == 2
		results.collect{it.slug}.sort() == ['bemo-1262-256', 'bemo-3267-254']
	}
	
	def "should find all rolling stocks by category"() {
		when:
		def results = service.findByCategory "electric-locomotives"
	
		then:
		results != null
		results.size == 2
		results.collect{it.slug}.sort() == ['bemo-1262-256', 'roco-43858']
	}
	
	def "should find all rolling stocks by era"() {
		when:
		def results = service.findByEra "V"
	
		then:
		results != null
		results.size == 2
		results.collect{it.slug}.sort() == ['bemo-3267-254', 'roco-43858']
	}
	
	def "should find all rolling stocks by power method"() {
		when:
		def results = service.findByPowerMethod "ac"
	
		then:
		results != null
		results.size == 1
		results.collect{it.slug}.sort() == ['roco-43858']
	}
	
	def "should find all rolling stocks by scale"() {
		when:
		def results = service.findByScale "H0"
	
		then:
		results != null
		results.size == 2
		results.collect{it.slug}.sort() == ['acme-69501', 'roco-43858']
	}
	
	def "should find all rolling stocks by tag"() {
		when:
		def results = service.findByTag "museum"
	
		then:
		results != null
		results.size == 1
		results.collect{it.slug}.sort() == ['acme-69501']
	}
	
	def "should create new rolling stocks"() {
		given:
		def brand = mongoTemplate.findOne query(where("name").is("ACME")), Brand.class
		def scale = mongoTemplate.findOne query(where("name").is("H0")), Scale.class
		def railway = mongoTemplate.findOne query(where("name").is("DB")), Railway.class
		
		def newRs = new RollingStock(brand: brand, 
			itemNumber: "123456", 
			description: 'Description',
			category: 'eletric-locomotives',
			details: 'Details',
			scale: scale,
			railway: railway,
			era: "IV",
			deliveryDate: new DeliveryDate(2012, 1)
			)
		
		when:
		service.save newRs

		then:
		newRs.id != null
		
		def rs = mongoTemplate.findOne query(where("slug").is("acme-123456")), rollingStocks
		rs != null
		rs.brandName == "ACME"
		rs.itemNumber == "123456"
		rs.description == "Description"
		rs.details == "Details"
		
		rs.slug == "acme-123456"
		rs.lastModified != null
	}
	
	def "should remove rolling stocks"() {
		given:
		def rs = mongoTemplate.findOne query(where("slug").is("acme-69501")), rollingStocks
		
		when:
		service.remove rs
		
		then:
		def rs2 = mongoTemplate.findOne query(where("slug").is("acme-69501")), rollingStocks
		rs2 == null
	}
}
