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

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired

import com.trenako.entities.Account
import com.trenako.entities.Money
import com.trenako.entities.RollingStock
import com.trenako.entities.Collection
import com.trenako.entities.CollectionItem
import com.trenako.mapping.WeakDbRef

import com.trenako.values.Visibility

/**
 * 
 * @author Carlo Micieli
 *
 */
class CollectionsServiceSpecification extends MongoSpecification {

	@Autowired CollectionsService service
	
	def now = new GregorianCalendar(2010, Calendar.JULY, 22, 1, 30, 00)
	
	def setup() {
		cleanup()
		
		db.collections.insert(
			slug: 'the-rocket',
			owner: 'the-rocket',
			items: [
				[itemId: '2012-01-01_acme-123456', 
					rollingStock: [label: 'ACME 123456', slug: 'acme-123456'], 
					price: [val: 15000, cur: 'USD'], 
					condition: 'new', 
					notes: 'My notes', 
					category: 'electric-locomotives', 
					addedAt: new Date()],
				[itemId: '2012-01-01_acme-123457', 
					rollingStock: [label: 'ACME 123457', slug: 'acme-123457'], 
					price: [val: 15000, cur: 'USD'], 
					condition: 'new', 
					notes: 'My notes', 
					category: 'freight-cars', 
					addedAt: new Date()]],
			categories: [electricLocomotives: 1, freightCars: 1],
			visibility: 'public',
			lastModified: now.time)
	}
	
	def cleanup() {
		db.collections.remove([:])
	}
	
	def "should find the collection with the provided id"() {
		given:
		def doc = db.collections.findOne(slug: 'the-rocket')
		def id = doc._id
		
		when:
		def col = service.findById(id)
		
		then:
		col != null
		col.owner == 'the-rocket'
		col.categories.electricLocomotives == 1
		col.categories.freightCars == 1
		col.lastModified == now.time
		col.items.size() == 2
		col.items.collect { it.itemId }.sort() == ['2012-01-01_acme-123456', '2012-01-01_acme-123457']
	}
	
	def "should return null if no collection with the provided id exists"() {
		given:
		def id = new ObjectId()
		
		when:
		def col = service.findById(id)
		
		then:
		col == null
	}
	
	def "should find the collection with the provided slug"() {
		when:
		def col = service.findBySlug('the-rocket')
		
		then:
		col != null
		col.owner == 'the-rocket'
		col.categories.electricLocomotives == 1
		col.categories.freightCars == 1
		col.lastModified == now.time
		col.items.size() == 2
		col.items.collect { it.itemId }.sort() == ['2012-01-01_acme-123456', '2012-01-01_acme-123457']
	}
	
	def "should return null if no collection with the provided slug exists"() {
		when:
		def col = service.findBySlug('not-found')
		
		then:
		col == null
	}
	
	def "should find the collection with the provided owner"() {
		given:
		def owner = new Account(slug: 'the-rocket')
		
		when:
		def col = service.findByOwner(owner)

		then:
		col != null
		col.owner == 'the-rocket'
	}
	
	def "should return null if no collection with the provided owner exists"() {
		given:
		def owner = new Account(slug: 'not-exists')
		
		when:
		def col = service.findByOwner(owner)
		
		then:
		col == null
	}
	
	def "should check whether the collection contains the provided rolling stock"() {
		given:
		def owner = new Account(slug: 'the-rocket')
		def rs = new RollingStock(slug: 'acme-123456')
		
		when:
		def rv = service.containsRollingStock(owner, rs)
		
		then:
		rv == true
	}
	
	def "should check whether the collection doesn't contain the provided rolling stock"() {
		given:
		def owner = new Account(slug: 'the-rocket')
		def rs = new RollingStock(slug: 'acme-999999')
		
		when:
		def rv = service.containsRollingStock(owner, rs)
		
		then:
		rv == false
	}
	
	def "should change collections visibility"() {
		given:
		def owner = new Account(slug: 'the-rocket')
		
		when:
		service.changeVisibility(owner, Visibility.PRIVATE)
		
		then:
		def doc = db.collections.findOne(slug: 'the-rocket')
		doc.visibility == 'private'
		doc.lastModified != now.time
	}
	
	def "should add new rolling stocks to the collection"() {
		given:
		def owner = new Account(slug: 'the-rocket')
		
		and:
		def date = new GregorianCalendar(2010, Calendar.JULY, 22, 1, 30, 00).time
		def rs = new WeakDbRef<RollingStock>(slug: 'acme-123457', label: 'ACME 123457')
		def newItem = new CollectionItem(
			rollingStock: rs, 
			price: new Money(1000, 'USD'), 
			condition: 'new', 
			notes: 'My notes', 
			category: 'freight-cars', 
			addedAt: date)
		
		when:
		service.addRollingStock(owner, newItem)
		
		then:
		def doc = db.collections.findOne(slug: 'the-rocket')
		doc.categories.electricLocomotives == 1
		doc.categories.freightCars == 2
		doc.lastModified != now.time
		doc.items.size() == 3
		doc.items.collect { it.itemId }.sort() == ['2010-07-22_acme-123457', '2012-01-01_acme-123456', '2012-01-01_acme-123457']
	}
	
	def "should update the collection items"() {
		given:
		def owner = new Account(slug: 'the-rocket')
		and:
		def date = new GregorianCalendar(2010, Calendar.JULY, 22, 1, 30, 00).time
		def rs = new WeakDbRef<RollingStock>(slug: 'acme-123456', label: 'ACME 123456')
		def item = new CollectionItem(
			itemId: '2012-01-01_acme-123456',
			category: 'electric-locomotives',
			rollingStock: rs,
			price: new Money(1000, 'USD'),
			condition: 'pre-owned',
			notes: 'My updated notes',
			addedAt: date)

		when:
		service.updateItem(owner, item)
		
		then:
		def doc = db.collections.findOne(slug: 'the-rocket')
		
		and: "the last modified timestamp was updated"
		doc.lastModified != now
		
		and:
		doc.items.size() == 2
		doc.items[0].itemId == '2012-01-01_acme-123456'
		doc.items[0].rollingStock == [slug: 'acme-123456', label: 'ACME 123456']
		doc.items[0].notes == 'My updated notes'
		doc.items[0].condition == 'pre-owned'
		doc.items[0].price == [val: 1000, cur: 'USD']
	}
	
	def "should remove a rolling stock from the collection"() {
		given:
		def owner = new Account(slug: 'the-rocket')
		def item = new CollectionItem(
			itemId: '2012-01-01_acme-123456', 
			category: 'electric-locomotives')
		
		when:
		service.removeRollingStock(owner, item)
		
		then:
		def doc = db.collections.findOne(slug: 'the-rocket')
		doc.categories.electricLocomotives == 0
		doc.categories.freightCars == 1
		doc.lastModified != now.time
		doc.items.size() == 1
		doc.items.collect { it.itemId } == ['2012-01-01_acme-123457']
	}
	
	def "should create a public and empty collection for a user"() {
		given:
		def owner = new Account(displayName: 'George', slug: 'george')
		
		when:
		service.createNew(owner)
		
		then:
		def doc = db.collections.findOne(slug: 'george')
		doc != null
		doc.slug == 'george'
		doc.visibility == 'public'
		doc.lastModified != null
	}
	
	def "should delete a user collection"() {
		given:
		def doc = db.collections.findOne(slug: 'the-rocket')
		def coll = new Collection(id: doc._id)
		
		when:
		service.remove(coll)
		
		then:
		def notfound = db.collections.findOne(slug: 'the-rocket')
		notfound == null
	}
}
