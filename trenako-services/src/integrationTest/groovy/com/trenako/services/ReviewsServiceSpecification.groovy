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
import org.springframework.dao.DuplicateKeyException

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
		cleanup()
		
		db.reviews.insert(
			slug: 'acme-69501',
			rollingStock: [slug: 'acme-69501', label: 'ACME 69501'],
			items: [
				[author: 'alice', title: 'Review title1', content: 'Review content1', lang: 'en', rating: 5, postedAt: new Date()],
				[author: 'george', title: 'Review title2', content: 'Review content2', lang: 'en', rating: 2, postedAt: new Date()]],
			numberOfReviews: 2,
			totalRating: 7)

	}
	
	def cleanup() {
		db.reviews.remove([:])
	}
	
	def "should find rolling stock reviews with the provided slug"() {
		when:
		def result = service.findBySlug('acme-69501')
		
		then:
		result != null
		result.slug == 'acme-69501'
		result.items != null
		result.items.size() == 2
		result.items.collect { it.content }.sort() == ['Review content1', 'Review content2']
	}
	
	def "should return null if no review exists for the provided slug"() {
		when:
		def result = service.findBySlug('acme-123456')
		
		then:
		result == null
	}
	
	def "should find reviews with the provided rolling stock"() {
		given:
		def rollingStock = new RollingStock(slug: 'acme-69501')
		
		when:
		def result = service.findByRollingStock(rollingStock)
		
		then:
		result != null
		result.slug == 'acme-69501'
		result.items != null
		result.items.size() == 2
		result.items.collect { it.content }.sort() == ['Review content1', 'Review content2']
	}
	
	def "should return null if no review exists for the provided rolling stock"() {
		given:
		def rollingStock = new RollingStock(slug: 'acme-123456')
		
		when:
		def result = service.findByRollingStock(rollingStock)
		
		then:
		result == null
	}
	
	def "should post a new rolling stock review"() {
		given:
		def rollingStock = new RollingStock(slug: 'acme-69501')
		rollingStock.setBrand(new Brand('ACME'))
		
		and:
		def author = new Account(displayName: 'Bob')
		def newReview = new Review(author,
			'My third review',
			'My third review content',
			3)
		
		when:
		service.postReview(rollingStock, newReview)
		
		then:
		def doc = db.reviews.findOne(slug: 'acme-69501')
		assert doc != null
		doc.numberOfReviews == 3
		doc.totalRating == 10
		doc.items.size() == 3
	}
	
	def "should initialize the list when posting a review for a new rolling stock"() {
		given:
		def rollingStock = new RollingStock(slug: 'acme-123456')
		rollingStock.setBrand(new Brand('ACME'))
		
		and:
		def author = new Account(displayName: 'Bob')
		def newReview = new Review(author, 
			'My first review', 
			'My first review content',
			3)
		
		when:
		service.postReview(rollingStock, newReview)
		
		then:
		def doc = db.reviews.findOne(slug: 'acme-123456')
		assert doc != null
		doc.numberOfReviews == 1
		doc.totalRating == 3
		doc.items.size() == 1
		doc.items[0].title == 'My first review'
		doc.items[0].content == 'My first review content'
	}
	
	def "should delete rolling stock reviews"() {
		given:
		def rollingStock = new RollingStock(slug: 'acme-69501')
		rollingStock.setBrand(new Brand('ACME'))
		
		and:
		def author = new Account(slug: 'alice')
		def review = new Review(author, '', '', 5)
		
		assert author.slug != null
		
		when:
		service.deleteReview(rollingStock, review)
		
		then:
		def doc = db.reviews.findOne(slug: 'acme-69501')
		assert doc != null
		doc.numberOfReviews == 1
		doc.totalRating == 2
		doc.items.size() == 1
	}
}
