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
import com.trenako.entities.Brand
import com.trenako.entities.Scale
import com.trenako.entities.Review
import com.trenako.entities.Railway
import com.trenako.entities.RollingStock

import com.mongodb.DBRef

import com.trenako.services.ReviewsService

/**
 * 
 * @author Carlo Micieli
 *
 */
class ReviewsServiceSpecification extends MongoSpecification {

	@Autowired ReviewsService service;
	
	def setup() {
		def acme = [name: 'ACME']
		db.brands.insert(name: 'ACME')
		def H0 = [name: 'H0', ratio: 870]
		db.scales.insert(H0)
		def DB = [name: 'DB', country: 'de']
		db.railways.insert(DB)
		
		db.rollingStocks.insert(
			brand: new DBRef(null, 'brands', acme._id),
			slug: 'acme-69501',
			itemNumber: '69501',
			description: 'Gr 685 172',
			category: 'steam-locomotives',
			powerMethod: "dc",
			era: "III",
			railway: new DBRef(null, 'railways', DB._id),
			tags: ['museum'],
			scale: new DBRef(null, 'scales', H0._id))
		db.rollingStocks.insert(
			brand: new DBRef(null, 'brands', acme._id),
			slug: 'acme-69502',
			itemNumber: '69502',
			description: 'Gr 685 196',
			category: 'steam-locomotives',
			powerMethod: "dc",
			era: "III",
			railway: new DBRef(null, 'railways', DB._id),
			tags: ['museum'],
			scale: new DBRef(null, 'scales', H0._id))
		
		db.accounts << [
			[emailAddress: 'bob@mail.com', slug: 'bob', password: 'secret', displayName: 'Bob'],
			[emailAddress: 'alice@mail.com', slug: 'alice', password: 'secret', displayName: 'Alice']]

		db.reviews << [ 
			[authorName: 'bob', rsSlug: 'acme-69501', title: 'title1', content: 'Review1'],
			[authorName: 'bob', rsSlug: 'acme-69502', title: 'title2', content: 'Review2'],
			[authorName: 'alice', rsSlug: 'acme-69501', title: 'title3', content: 'Review3']]
	}
	
	def cleanup() {
		db.reviews.remove([:])
		db.rollingStocks.remove([:])
		db.scales.remove([:])
		db.brands.remove([:])
		db.railways.remove([:])
	}
	
	def "should find reviews by id"() {
		given:
		def c = db.reviews.findOne(content: 'Review1')
		def id = c._id
		assert id != null
		
		when:
		def review = service.findById id
		
		then:
		review != null
	}

	def "should find reviews by author"() {
		given:
		def doc = db.accounts.findOne(emailAddress: 'bob@mail.com')
		def author = new Account([id: doc._id, slug: doc.slug])
		assert author != null
		
		when:
		def results = service.findByAuthor author

		then:
		results != null
		results.size == 2
		results.collect{ it.content }.sort() == ["Review1", "Review2"]
	}
	
	def "should find reviews by author name"() {
		given:
		def authorName = 'alice'
		
		when:
		def results = service.findByAuthor authorName

		then:
		results != null
		results.size == 1
	}
	
	def "should find reviews by rolling stock"() {
		given:
		def doc = db.rollingStocks.findOne(slug: 'acme-69501')
		def rs = new RollingStock(id: doc._id, slug: doc.slug)
		assert rs != null
		
		when:
		def results = service.findByRollingStock rs
		
		then:
		results != null
		results.size == 2
		results.collect{ it.content } == ['Review1', 'Review3']
	}
	
	def "should find reviews by rolling stock slug"() {
		when:
		def results = service.findByRollingStock "acme-69502"
		
		then:
		results != null
		results.size == 1
		results.collect{ it.content } == ['Review2']
	}
	
	def "should save reviews"() {
		given:
		def doc = db.rollingStocks.findOne(slug: 'acme-69501')
		def rs = new RollingStock(id: doc._id, slug: doc.slug)
		assert rs != null

		and:
		def docAuthor = db.accounts.findOne(emailAddress: 'bob@mail.com')
		def author = new Account(id: docAuthor._id, slug: docAuthor.slug)
		assert author != null
		
		and:
		def newReview = new Review(author: author, rollingStock: rs, title: 'Title4', content: 'Review4')
		
		when:
		service.save newReview
		
		then:
		newReview.id != null
		
		and:
		def c = db.reviews.findOne(_id: newReview.id)
		
		c != null
		c.authorName == "bob"
		c.rsSlug == "acme-69501"
		c.postedAt != null 
	}
	
	def "should remove reviews"() {
		given:
		def doc = db.reviews.findOne(content: 'Review1')
		def review = new Review(id: doc._id)
		assert review != null
		
		when:
		service.remove review
		
		then:
		def c = db.reviews.findOne(_id: review.id)
		c == null
	}
}
