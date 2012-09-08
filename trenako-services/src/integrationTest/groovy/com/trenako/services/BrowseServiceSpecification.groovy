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

import java.util.Arrays
import java.util.List

import spock.lang.*

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired
import com.trenako.values.Category
import com.trenako.values.DeliveryDate
import com.trenako.values.Era
import com.trenako.values.PowerMethod

import com.trenako.criteria.SearchRequest
import com.trenako.entities.Brand
import com.trenako.entities.Railway
import com.trenako.entities.RollingStock
import com.trenako.entities.Scale

import com.trenako.results.RangeRequest
import com.trenako.services.BrowseService

/**
 * 
 * @author Carlo Micieli
 *
 */
class BrowseServiceSpecification extends MongoSpecification {

	@Autowired BrowseService service

	def setup() {
		cleanup()

		db.brands << [[name: 'ACME', slug: 'acme'], 
			[name: 'Bemo', slug: 'bemo'],
			[name: 'LS Models', slug: 'ls-models'], 
			[name: 'Liliput', slug: 'liliput'],
			[name: 'Roco', slug: 'roco']]
		db.scales << [[name: 'H0', slug: 'h0', ratio: 870],
			[name: '1', slug: '1', ratio: 325],
			[name: 'H0m', slug: 'h0m', ratio: 870],
			[name: 'N', slug: 'n', ratio: 1600]]
		db.railways << [[name: 'DB', slug: 'db', country: 'de'],
			[name: 'FS', slug: 'fs', country: 'it'],
			[name: 'OBB', slug: 'obb', country: 'at'],
			[name: 'Sncb', slug: 'sncb', country: 'be'],
			[name: 'Sncf', slug: 'sncf', country: 'fr']]

		db.rollingStocks << [
			[brand: [slug: 'roco', label: 'Roco'], // 1
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
			[brand: [slug: 'acme', label: 'ACME'], // 2
				itemNumber: '52623',
				slug: 'acme-52623',
				railway: [slug: 'obb', label: 'OBB'],
				scale: [slug: 'h0', label: 'H0'],
				description: [en: 'UIC-Z coach 1st class'],
				category: 'passenger-cars',
				era: 'v',
				powerMethod: 'dc',
				country: 'at',
				totalLength: 303,
				lastModified: new Date()],
			[brand: [slug: 'acme', label: 'ACME'], // 3
				itemNumber: '40153',
				slug: 'acme-40153',
				railway: [slug: 'fs', label: 'FS'],
				scale: [slug: 'h0', label: 'H0'],
				description: [en: 'Dm 99000'],
				category: 'freight-cars',
				era: 'iv',
				powerMethod: 'dc',
				country: 'it',
				totalLength: 105,
				lastModified: new Date()],
			[brand: [slug: 'roco', label: 'Roco'], // 4
				itemNumber: '72500',
				slug: 'roco-72500',
				railway: [slug: 'fs', label: 'FS'],
				scale: [slug: 'h0', label: 'H0'],
				description: [en: 'Electric locomotive E.444 037 Tartaruga'],
				category: 'electric-locomotives',
				era: 'iv',
				powerMethod: 'dc',
				country: 'it',
				totalLength: 195,
				lastModified: new Date()],
			[brand: [slug: 'ls-models', label: 'LS Models'], // 5
				itemNumber: '16025',
				slug: 'ls-models-16025',
				railway: [slug: 'db', label: 'DB'],
				scale: [slug: 'h0', label: 'H0'],
				description: [en: 'Electric locomotive Class E310'],
				category: 'electric-locomotives',
				era: 'iii',
				powerMethod: 'dc',
				country: 'de',
				totalLength: 195,
				lastModified: new Date()],
			[brand: [slug: 'roco', label: 'Roco'], // 6
				itemNumber: '62307',
				slug: 'roco-62307',
				railway: [slug: 'sncf', label: 'Sncf'],
				scale: [slug: 'h0', label: 'H0'],
				description: [en: 'Steam locomotive 231 E.236'],
				category: 'steam-locomotives',
				era: 'iii',
				powerMethod: 'dc',
				country: 'fr',
				totalLength: 272,
				lastModified: new Date()],
			[brand: [slug: 'roco', label: 'Roco'], // 7
				itemNumber: '62974',
				slug: 'roco-62974',
				railway: [slug: 'db', label: 'DB'],
				scale: [slug: 'h0', label: 'H0'],
				description: [en: 'Diesel locomotive Class 364 920-9'],
				category: 'diesel-locomotives',
				era: 'v',
				powerMethod: 'dc',
				country: 'de',
				totalLength: 120,
				lastModified: new Date()],
			[brand: [slug: 'roco', label: 'Roco'], // 8
				itemNumber: '62800',
				slug: 'roco-62800',
				railway: [slug: 'db', label: 'DB'],
				scale: [slug: 'h0', label: 'H0'],
				description: [en: 'Diesel locomotive Class 236 117-8'],
				category: 'diesel-locomotives',
				era: 'iv',
				powerMethod: 'dc',
				country: 'de',
				totalLength: 110,
				lastModified: new Date()],
			[brand: [slug: 'roco', label: 'Roco'], // 9
				itemNumber: '62982',
				slug: 'roco-62982',
				railway: [slug: 'sncf', label: 'Sncf'],
				scale: [slug: 'h0', label: 'H0'],
				description: [en: 'Diesel locomotive CC7200'],
				category: 'diesel-locomotives',
				era: 'v',
				powerMethod: 'dc',
				country: 'fr',
				totalLength: 232,
				lastModified: new Date()],
			[brand: [slug: 'bemo', label: 'BEMO'], // 10
				itemNumber: '3253 145',
				slug: 'bemo-3253-145',
				railway: [slug: 'rhb', label: 'RHb'],
				scale: [slug: 'h0m', label: 'H0m'],
				description: [en: '2° KL. Arosa Express'],
				category: 'passenger-cars',
				era: 'iv',
				powerMethod: 'dc',
				country: 'ch',
				totalLength: 195,
				lastModified: new Date()],
			[brand: [slug: 'acme', label: 'ACME'], // 11
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
			[brand: [slug: 'bemo', label: 'BEMO'], // 12
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
			[brand: [slug: 'roco', label: 'Roco'], // 13
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
		
		assert db.rollingStocks.count([:]) == 13
	}

	def cleanup() {
		db.rollingStocks.remove([:])
		db.scales.remove([:])
		db.railways.remove([:])
		db.brands.remove([:])
	}

	def "should fill the list of brands for rolling stock browsing filters"() {
		when:
		def results = service.brands()

		then:
		results != null
		results.size == 5
		results.collect { it.slug }.sort() == ['acme', 'bemo', 'liliput', 'ls-models', 'roco']
	}
	
	def "should find the brand with the provided slug"() {
		when:
		def brand = service.findBrand('acme')
		
		then:
		brand != null
		brand.slug == 'acme'
		brand.name == 'ACME'
	}

	def "should fill the list of railways for rolling stocks browsing filters"() {
		when:
		def results = service.railways()

		then:
		results != null
		results.size == 5
		results.collect { it.slug }.sort() == ['db', 'fs', 'obb', 'sncb', 'sncf']
	}

	def "should find the railway with the provided slug"() {
		when:
		def railway = service.findRailway('fs')
		
		then:
		railway != null
		railway.slug == 'fs'
		railway.name == 'FS'
	}
	
	def "should fill the list of scales for rolling stocks browsing filters"() {
		when:
		def results = service.scales()

		then:
		results != null
		results.size == 4
		results.collect { it.slug }.sort() == ['1', 'h0', 'h0m', 'n']
	}
	
	def "should find the scale with the provided slug"() {
		when:
		def scale = service.findScale('h0')
		
		then:
		scale != null
		scale.slug == 'h0'
		scale.name == 'H0'
	}
	
	def "should return all rolling stocks when criteria are not set"() {
		given:
		def criteria = new SearchRequest()
		def range = new RangeRequest(size: 25)
		
		when:
		def results = service.findByCriteria(criteria, range)
		
		then:
		results != null
		results.items != null
		results.isEmpty() == false
		results.items.size() == 13
	}
	
	def "should find the rolling stocks with a provided brand"() {
		given:
		def criteria = new SearchRequest(brand: 'acme')
		def range = new RangeRequest(size: 10)
		
		when:
		def results = service.findByCriteria(criteria, range)
		
		then:
		results != null
		results.items != null
		results.items.size() == 3
		results.items.collect { it.slug }.sort() == ['acme-40153', 'acme-52623', 'acme-69501']		 
	}
	
	def "should return an empty result set when no rolling stock exists for the provided brand"() {
		given:
		def criteria = new SearchRequest(brand: 'liliput')
		def range = new RangeRequest(size: 10)
		
		when:
		def results = service.findByCriteria(criteria, range)
		
		then:
		results != null
		results.isEmpty() == true
		results.items != null
		results.items.size() == 0
	}
	
	def "should ignore the brand search criteria if no brand exists with the provided slug"() {
		given:
		def criteria = new SearchRequest(brand: 'not-found')
		def range = new RangeRequest(size: 20)
		
		when:
		def results = service.findByCriteria(criteria, range)
		
		then:
		results != null
		results.items != null
		results.items.size() == 13
	}
	
	def "should find the rolling stocks with a provided railway"() {
		given:
		def criteria = new SearchRequest(railway: 'db')
		def range = new RangeRequest(size: 10)
		
		when:
		def results = service.findByCriteria(criteria, range)
		
		then:
		results != null
		results.items != null
		results.items.size() == 5
		results.items.collect { it.slug }.sort() == ['ls-models-16025', 'roco-43858', 'roco-62193', 'roco-62800', 'roco-62974']
	}
	
	def "should return an empty result set when no rolling stock exists for the provided railway"() {
		given:
		def criteria = new SearchRequest(railway: 'sncb')
		def range = new RangeRequest(size: 10)
		
		when:
		def results = service.findByCriteria(criteria, range)
		
		then:
		results != null
		results.isEmpty() == true
		results.items != null
		results.items.size() == 0
	}
	
	def "should ignore the railway search criteria if no railway exists with the provided slug"() {
		given:
		def criteria = new SearchRequest(railway: 'not-found')
		def range = new RangeRequest(size: 20)
		
		when:
		def results = service.findByCriteria(criteria, range)
		
		then:
		results != null
		results.items != null
		results.items.size() == 13
	}
	
	def "should find the rolling stocks with a provided scale"() {
		given:
		def criteria = new SearchRequest(scale: 'h0m')
		def range = new RangeRequest(size: 10)
		
		when:
		def results = service.findByCriteria(criteria, range)
		
		then:
		results != null
		results.items != null
		results.items.size() == 2
		results.items.collect { it.slug }.sort() == ['bemo-1262-256', 'bemo-3253-145']
	}
	
	def "should return an empty result set when no rolling stock exists for the provided scale"() {
		given:
		def criteria = new SearchRequest(scale: '1')
		def range = new RangeRequest(size: 10)
		
		when:
		def results = service.findByCriteria(criteria, range)
		
		then:
		results != null
		results.isEmpty() == true
		results.items != null
		results.items.size() == 0
	}
	
	def "should ignore the scale search criteria if no scale exists with the provided slug"() {
		given:
		def criteria = new SearchRequest(scale: 'not-found')
		def range = new RangeRequest(size: 20)
		
		when:
		def results = service.findByCriteria(criteria, range)
		
		then:
		results != null
		results.items != null
		results.items.size() == 13
	}
	
	def "should find the rolling stocks with a provided era"() {
		given:
		def criteria = new SearchRequest(era: 'iii')
		def range = new RangeRequest(size: 10)
		
		when:
		def results = service.findByCriteria(criteria, range)
		
		then:
		results != null
		results.items != null
		results.items.size() == 4
		results.items.collect { it.slug }.sort() == ['acme-69501', 'ls-models-16025', 'roco-62193', 'roco-62307']
	}
	
	def "should find the rolling stocks with a provided category"() {
		given:
		def criteria = new SearchRequest(category: 'electric-locomotives')
		def range = new RangeRequest(size: 10)
		
		when:
		def results = service.findByCriteria(criteria, range)
		
		then:
		results != null
		results.items != null
		results.items.size() == 4
		results.items.collect { it.slug }.sort() == ['bemo-1262-256', 'ls-models-16025', 'roco-43858', 'roco-72500']
	}
	
	def "should find the rolling stocks with a provided power method"() {
		given:
		def criteria = new SearchRequest(powermethod: 'ac')
		def range = new RangeRequest(size: 10)
		
		when:
		def results = service.findByCriteria(criteria, range)
		
		then:
		results != null
		results.items != null
		results.items.size() == 1
		results.items.collect { it.slug }.sort() == ['roco-43858']
	}
	
	def "should find the rolling stocks with the provided brand and category"() {
		given:
		def criteria = new SearchRequest(brand: 'bemo', category: 'electric-locomotives')
		def range = new RangeRequest(size: 10)
		
		when:
		def results = service.findByCriteria(criteria, range)
		
		then:
		results != null
		results.items != null
		results.items.size() == 1
		results.items.collect { it.slug }.sort() == ['bemo-1262-256']
	}
	
	def "should find the rolling stocks using all criteria"() {
		given:
		def criteria = new SearchRequest(brand: 'bemo', 
			railway: 'mgb',
			era: 'iv',
			powermethod: 'dc',
			scale: 'h0m',
			category: 'electric-locomotives')
		def range = new RangeRequest(size: 10)
		
		when:
		def results = service.findByCriteria(criteria, range)
		
		then:
		results != null
		results.items != null
		results.items.size() == 1
		results.items.collect { it.slug }.sort() == ['bemo-1262-256']
	}
	
	def "should return the first page of rolling stock paginated results"() {
		given:
		def criteria = new SearchRequest()
		def range = new RangeRequest(size: 5)
		
		when:
		def results = service.findByCriteria(criteria, range)
		
		then:
		results != null
		results.items != null
		results.items.size() == 5
		
		and:
		results.hasPreviousPage() == false
		results.hasNextPage() == true
	}
}
