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

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException

import com.trenako.entities.Brand
import com.trenako.entities.Railway
import com.trenako.entities.RollingStock
import com.trenako.entities.Scale
import com.trenako.mapping.LocalizedField
import com.trenako.values.Category
import com.trenako.values.DeliveryDate;
import com.trenako.values.Era
import com.trenako.values.PowerMethod

import com.trenako.services.RollingStocksService

/**
 * 
 * @author Carlo Micieli
 *
 */
class RollingStocksServiceSpecification  extends MongoSpecification {

	@Autowired RollingStocksService service
	
	def setup() {
		
		db.brands << [[name: 'ACME', slug: 'acme'], 
			[name: 'LS Models', slug: 'ls-models'], 
			[name: 'Roco', slug: 'roco']]
		db.scales << [[name: 'H0', slug: 'h0', ratio: 870],
			[name: '1', slug: '1', ratio: 325],
			[name: 'N', slug: 'n', ratio: 1600]]
		db.railways << [[name: 'DB', slug: 'db', country: 'de'],
			[name: 'FS', slug: 'fs', country: 'it'],
			[name: 'Sncf', slug: 'sncf', country: 'fr']]
				
		db.rollingStocks << [
			[brand: [slug: 'roco', label: 'Roco'],
				itemNumber: '62193',
				slug: 'roco-62193',
				railway: [slug: 'db', label: 'DB'],
				scale: [slug: 'h0', label: 'H0'],
				description: [en: 'Steam locomotive BR 10 002'],
				details: [en: 'The model additionally features a smoke generator, sound decoder and speakers'],
				category: 'steam-locomotives',
				era: 'iii',
				powerMethod: 'dc',
				country: 'de',
				totalLength: 305,
				lastModified: new Date()],
			[brand: [slug: 'acme', label: 'ACME'],
				itemNumber: '69501',
				slug: 'acme-69501',
				railway: [slug: 'fs', label: 'FS'],
				scale: [slug: 'h0', label: 'H0'],
				description: [en: 'Steam locomotive Gr 685 172'],
				category: 'steam-locomotives',
				era: 'iii',
				powerMethod: 'dc',
				country: 'it',
				totalLength: 242,
				lastModified: new Date()],
			[brand: [slug: 'bemo', label: 'BEMO'],
				itemNumber: '1262 256',
				slug: 'bemo-1262-256',
				railway: [slug: 'mgb', label: 'MGB'],
				scale: [slug: 'h0m', label: 'H0m'],
				description: [en: 'HGe 4/4 II 106'],
				category: 'electric-locomotives',
				era: 'iv',
				powerMethod: 'dc',
				country: 'ch',
				tags: ["glacier", "express"],
				totalLength: 140,
				lastModified: new Date()],
			[brand: [slug: 'roco', label: 'Roco'],
				itemNumber: '43858',
				slug: 'roco-43858',
				railway: [slug: 'db', label: 'DB'],
				scale: [slug: 'h0', label: 'H0'],
				description: [en: 'Electric locomotive 101 0004-0'],
				category: 'electric-locomotives',
				era: 'v',
				powerMethod: 'ac',
				country: 'de',
				totalLength: 220,
				lastModified: new Date()]
			]
	}
	
	def cleanup() {
		db.rollingStocks.remove([:])
		db.scales.remove([:])
		db.railways.remove([:])
		db.brands.remove([:])
	}
	
	def "should find a rolling stock by id"() {
		given:
		def rs = db.rollingStocks.findOne(slug: 'bemo-1262-256')
		def id = rs._id
			
		when:
		def rollingStock = service.findById id
		
		then:
		rollingStock != null
		rollingStock.slug == 'bemo-1262-256'
		rollingStock.brand.slug == 'bemo'
		rollingStock.itemNumber == '1262 256'
	}
	
	def "should find a rolling stock by slug"() {
		when:
		def rollingStock = service.findBySlug 'bemo-1262-256'

		then:
		rollingStock != null
		rollingStock.slug == 'bemo-1262-256'
		rollingStock.brand.slug == 'bemo'
		rollingStock.itemNumber == '1262 256'
	}
	
	def "should fill the list of brands for rolling stocks creation"() {
		when:
		def results = service.brands()
		
		then:
		results != null
		results.size == 3
		results.collect { it.slug }.sort() == ['acme', 'ls-models', 'roco']
	}
		
	def "should fill the list of railways for rolling stocks creation"() {
		when:
		def results = service.railways()
		
		then:
		results != null
		results.size == 3 
		results.collect { it.slug }.sort() == ['db', 'fs', 'sncf']
	}
	
	def "should fill the list of scales for rolling stocks creation"() {
		when:
		def results = service.scales()
		
		then:
		results != null
		results.size == 3
		results.collect { it.slug }.sort() == ['1', 'h0', 'n']
	}
	
	def "should throw an exception if rolling stock slug is already used"() {
		given:
		def newRs = new RollingStock(
			itemNumber: "62193",
			description: LocalizedField.localize([en: 'Description']),
			category: 'eletric-locomotives',
			era: 'iv',
			country: 'it',
			)
		newRs.setBrand('roco')
		newRs.setScale('h0')
		newRs.setRailway('db')
		
		when:
		service.save newRs

		then:
		thrown(DuplicateKeyException)
		newRs.id == null
	}
	
	def "should create new rolling stocks"() {
		given:
		def newRs = new RollingStock(
			itemNumber: "123456", 
			description: LocalizedField.localize([en: 'Description']),
			details: LocalizedField.localize([en: 'Details']),
			category: 'eletric-locomotives',
			era: 'iv',
			country: 'it',
			deliveryDate: new DeliveryDate(2012, 1)
			)
		newRs.setBrand('acme')
		newRs.setScale('h0')
		newRs.setRailway('db')
		
		when:
		service.save newRs

		then:
		newRs.id != null
		
		def rsDoc = db.rollingStocks.findOne(slug: 'acme-123456')
		rsDoc != null
		rsDoc.itemNumber == '123456'
		rsDoc.brand == [slug: 'acme', label: 'ACME']
		rsDoc.scale == [slug: 'h0', label: 'H0 (1:87)']
		rsDoc.railway == [slug: 'db', label: 'DB']
		rsDoc.description == [en: 'Description']
		rsDoc.details == [en: 'Details']
		rsDoc.category == 'eletric-locomotives'
		rsDoc.era == 'iv'
		rsDoc.country == 'de'
		rsDoc.deliveryDate == [year: 2012, quarter: 1]
		
		and:
		rsDoc.slug == "acme-123456"
		rsDoc.lastModified != null
	}
	
	def "should remove rolling stocks"() {
		given:
		def doc = db.rollingStocks.findOne(slug: 'acme-69501')
		def rs = new RollingStock(id: doc._id)
		
		when:
		service.remove rs
		
		then:
		def dbDoc = db.rollingStocks.findOne(slug: 'acme-69501')
		dbDoc == null
	}
}
