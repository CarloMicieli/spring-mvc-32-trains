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
		db.reviews.insert(
			slug: 'acme-69501',
			rollingStock: [slug: 'acme-69501', label: 'ACME 69501'],
			reviews: [[author: [slug: 'bob', label: 'Bob'], title: 'Review title', content: 'Review content', lang: 'en', rating: 5, postedAt: new Date()]],
			numberOfReviews: 1,
			totalRating: 5)

	}
	
	def "should find rolling stock reviews with the provided slug"() {
		when:
		def result = service.findBySlug('acme-69501')
		
		then:
		result != null
		result.slug == 'acme-69501'
		result.reviews != null
		result.reviews.size() == 1
		result.reviews.collect { it.content } == ['Review content']
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
		result.reviews != null
		result.reviews.size() == 1
		result.reviews.collect { it.content } == ['Review content']
	}
	
	def "should return null if no review exists for the provided rolling stock"() {
		given:
		def rollingStock = new RollingStock(slug: 'acme-123456')
		
		when:
		def result = service.findByRollingStock(rollingStock)
		
		then:
		result == null
	}
	
	
}
