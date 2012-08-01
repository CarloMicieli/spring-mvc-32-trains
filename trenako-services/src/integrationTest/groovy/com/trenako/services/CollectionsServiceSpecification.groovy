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

import spock.lang.*

import org.springframework.test.context.ContextConfiguration
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query

import static org.springframework.data.mongodb.core.query.Query.*
import static org.springframework.data.mongodb.core.query.Criteria.*

import com.trenako.entities.Account
import com.trenako.entities.Brand
import com.trenako.entities.Railway
import com.trenako.entities.Scale
import com.trenako.entities.RollingStock
import com.trenako.entities.Collection
import com.trenako.entities.CollectionItem

/**
 * 
 * @author Carlo Micieli
 *
 */
@ContextConfiguration(locations = "classpath:META-INF/spring-context.xml")
class CollectionsServiceSpecification { //extends Specification {

	@Autowired MongoTemplate mongo
	@Autowired CollectionsService service
	
	def setup() {
		def acme = new Brand(name: "ACME")
		mongo.save acme
		
		def H0 = new Scale(name: "H0", ratio: 87)
		mongo.save H0
		
		def DB = new Railway(name: "DB", country: 'DEU')
		mongo.save DB
		
		
		def rs1 = new RollingStock(brand: acme, itemNumber: "69501",
			description: "Gr 685 172",
			category: "STEAM_LOCOMOTIVES",
			powerMethod: "DC_DCC_SOUND",
			era: "III",
			railway: DB,
			tags: ['museum'],
			scale: H0)
		def rs2 = new RollingStock(brand: acme, itemNumber: "43858",
			description: "Electric loco 101 0004-0",
			category: "ELECTRIC_LOCOMOTIVES",
			powerMethod: "AC",
			era: "V",
			railway: DB,
			scale: H0)
		def rs3 = new RollingStock(brand: acme, itemNumber: "123456",
			description: "Electric loco 105",
			category: "ELECTRIC_LOCOMOTIVES",
			powerMethod: "DC",
			era: "V",
			railway: DB,
			scale: H0)
		def collection = [rs1, rs2, rs3]
		mongo.insert collection, RollingStock.class
		
		def collectionItems = [
			new CollectionItem(rollingStock: rs1, rsSlug: rs1.slug, addedAt: new Date()),
			new CollectionItem(rollingStock: rs2, rsSlug: rs2.slug, addedAt: new Date())
			]
		
		def coll = new Collection(ownerName: 'bob', 
			items: collectionItems,
			visible: true)
		mongo.save coll
	}
	
	def cleanup() {
		def all = new Query()
		mongo.remove all, Collection.class
		mongo.remove all, RollingStock.class
		mongo.remove all, Scale.class
		mongo.remove all, Brand.class
		mongo.remove all, Railway.class
	}
		
	def "should find collections by owner's name"() {
		when:
		def coll = service.findByOwnerName "bob"
		
		then:
		coll != null
		coll.ownerName == "bob"
	}
	
	def "should fine collections by id"() {
		given:
		def c = mongo.findOne query(where("ownerName").is("bob")), Collection.class
		assert c != null
		def id = c.id
		
		when:
		def coll = service.findById id
		
		then:
		coll != null
		coll.id == id
		coll.ownerName == "bob"
	} 
	
	def "should check if the collection contains a rolling stock"() {
		given:
		def rs = mongo.findOne query(where("slug").is("acme-69501")), RollingStock.class
		assert rs != null
		
		and:
		def c = mongo.findOne query(where("ownerName").is("bob")), Collection.class
		assert c != null
		def id = c.id
		
		when:
		boolean ret = service.containsRollingStock id, rs
		
		then:
		ret == true
	}
	
	def "should check if the user collection contains a rolling stock"() {
		given:
		def rs = mongo.findOne query(where("slug").is("acme-69501")), RollingStock.class
		assert rs != null
		
		when:
		boolean ret = service.containsRollingStock "bob", rs
		
		then:
		ret == true
	}
	
	def "should add items to collection"() {
		given:
		def coll = mongo.findOne query(where("ownerName").is("bob")), Collection.class
		assert coll != null
		
		and:
		def rs = mongo.findOne query(where("slug").is("acme-123456")), RollingStock.class
		assert rs != null
		
		and:
		def newItem = new CollectionItem(rollingStock: rs, 
			rsSlug: "acme-123456",
			addedAt: new Date())
		
		when:
		service.addItem coll.id, newItem
		
		then:
		def num = mongo.count query(where("items.rsSlug").is("acme-123456")), Collection.class
		num == 1
	}
	
	def "should add items to user's collection"() {
		given:
		def rs = mongo.findOne query(where("slug").is("acme-123456")), RollingStock.class
		assert rs != null
		
		and:
		def newItem = new CollectionItem(rollingStock: rs,
			rsSlug: "acme-123456",
			addedAt: new Date())
		
		when:
		service.addItem "bob", newItem
		
		then:
		def num = mongo.count query(where("items.rsSlug").is("acme-123456")), Collection.class
		num == 1
	}
	
	def "should create new collections"() {
		given:
		def newColl = new Collection(ownerName: "alice")
		
		when:
		service.save newColl
		
		then:
		newColl.id != null
	}
	
	def "should remove collections"() {
		given:
		def c = mongo.findOne query(where("ownerName").is("bob")), Collection.class
		assert c != null
		
		when:
		service.remove c
		
		then:
		def coll = mongo.findById c.id, Collection.class
		coll == null
	}

}
