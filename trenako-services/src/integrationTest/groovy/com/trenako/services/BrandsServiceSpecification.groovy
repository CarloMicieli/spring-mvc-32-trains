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

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import com.trenako.mapping.LocalizedField
import spock.lang.*
import com.gmongo.GMongo
import com.mongodb.MongoOptions;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.dao.DuplicateKeyException

import static org.springframework.data.mongodb.core.query.Query.*
import static org.springframework.data.mongodb.core.query.Criteria.*

import com.trenako.entities.Brand
import com.trenako.services.BrandsService

/**
 * 
 * @author Carlo Micieli
 *
 */
class BrandsServiceSpecification extends MongoSpecification {

	@Autowired BrandsService service
	
	def setup() {
		db.brands << [
			[name: 'ACME', slug: 'acme', description: localize('Acme description'), 
				scales: ['h0'], emailAddress: 'mail@acme.com', industrial: true, website: "http://www.acmetreni.com"],
			[name: 'Roco', slug: 'roco', description: localize('Roco description'),
				scales: ['h0', 'tt', 'n'], emailAddress: 'mail@roco.cc', industrial: true, website: "http://www.roco.cc"],
			[name: 'Märklin', slug: 'marklin', description: localize('Märklin description'),
				scales: ['1', 'h0', 'n', 'z'], emailAddress: 'mail@maerklin.de', industrial: true, website: "http://www.maerklin.de"],
			[name: 'LS Models', slug: 'ls-models', description: localize('Ls Models description'),
				scales: ['h0', 'n'], emailAddress: 'mail@lsmodels.com', industrial: true, website: "http://www.lsmodels.com"]]
	}
	
	def cleanup() {
		db.brands.remove([:])
	}
	
	def "should return Null if no brand is found"() {
		when:
		def brand = service.findByName "AAAA"

		then:
		brand == null
	}
	
	def "should find a brand by name"() {
		when:
		def brand = service.findByName "ACME"
		
		then:
		brand != null
		brand.name == "ACME"
	}
	
	def "should find a brand by slug"() {
		when:
		def brand = service.findBySlug "ls-models"

		then:
		brand != null
		brand.name == "LS Models"
	}
	
	def "should find all the brands"() {
		when:
		def brands = service.findAll()
		
		then:
		brands != null
		brands.size == 4
	}

	def "should throw exception if the brand name is already used"() {
		given: "a brand with an already used slug"
		def newBrand = new Brand(
			name: 'Roco',
			slug: 'brawa',
			description: localizedDesc('Brand description'),
			emailAddress: 'mail@brawa.de',
			industrial: true,
			website: 'http://www.brawa.de')
		
		when:
		service.save newBrand

		then:
		thrown(DuplicateKeyException)
		newBrand.id == null
	}
		
	def "should throw exception if the brand slug is already used"() {
		given: "a brand with an already used slug"
		def newBrand = new Brand(
			name: 'Brawa',
			slug: 'roco',
			description: localizedDesc('Brand description'),
			emailAddress: 'mail@brawa.de',
			industrial: true,
			website: 'http://www.brawa.de')
		
		when:
		service.save newBrand

		then:
		thrown(DuplicateKeyException)
		newBrand.id == null
	}
	
	def "should create new brands"() {
		given:
		def newBrand = new Brand(
			name: 'Brawa',
			description: localizedDesc('Brawa description'),
			emailAddress: 'mail@brawa.de',
			industrial: true,
			website: 'http://www.brawa.de')
			
		when:
		service.save newBrand
		
		then:
		newBrand.id != null
		
		def brand = db.brands.findOne(_id: newBrand.id)
		brand.name == "Brawa"
		brand.description == localizedDesc('Brawa description')
		brand.emailAddress == "mail@brawa.de"
		brand.website == "http://www.brawa.de"

		// added automatically 
		brand.slug == "brawa"
		brand.lastModified != null
	}
	
	def "should find brands by id"() {
		given:
		def b = db.brands.findOne(slug: 'acme')
		def id = b._id
		assert id != null
		
		when:
		def brand = service.findById id
		
		then:
		brand != null
		brand.id == id
		brand.name == "ACME"
	}
	
	def "should remove brand"() {
		given:
		def doc = db.brands.findOne(slug: 'acme')
		def brand = new Brand(id: doc._id)
		
		when:
		service.remove brand
		
		then:
		def notFound = db.brands.findOne(slug: 'acme')
		notFound == null
	}
}

