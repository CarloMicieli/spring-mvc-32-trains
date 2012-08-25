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

import com.trenako.entities.Account
import com.trenako.entities.Brand
import com.trenako.entities.Scale
import com.trenako.entities.Railway
import com.trenako.entities.RollingStock

import com.trenako.entities.Comment
import com.trenako.mapping.WeakDbRef
import com.trenako.services.CommentsService

/**
 * 
 * @author Carlo Micieli
 *
 */
class CommentsServiceSpecification extends MongoSpecification {

	@Autowired CommentsService service;
	
	def setup() {
		cleanup()
		
		db.rollingStocks.insert(
			brand: [label: 'ACME', slug: 'acme'],
			slug: 'acme-69501',
			itemNumber: '69501',
			description: [en: 'Gr 685 172'],
			category: 'steam-locomotives',
			powerMethod: "dc",
			era: "iii",
			railway: [label: 'FS', slug: 'fs'],
			scale: [label: 'H0', slug: 'h0'])
		db.rollingStocks.insert(
			brand: [name: 'ACME', slug: 'acme'],
			slug: 'acme-69502',
			itemNumber: '69502',
			description: [en: 'Gr 685 196'],
			category: 'steam-locomotives',
			powerMethod: "dc",
			era: "iii",
			railway: [label: 'FS', slug: 'fs'],
			scale: [label: 'H0', slug: 'h0'])
		
		db.comments << [ 
			[author: [slug: 'bob', label: 'Bob'], 
				rollingStock: [slug: 'acme-69501', label: 'ACME 69501'], 
				content: 'Comment1',
				postedAt: new Date()],
			[author: [slug: 'bob', label: 'Bob'], 
				rollingStock: [slug: 'acme-69502', label: 'ACME 69502'], 
				content: 'Comment2',
				postedAt: new Date()],
			[author: [slug: 'alice', label: 'Alice'],
				rollingStock: [slug: 'acme-69501', label: 'ACME 69501'],
				content: 'Comment3',
				postedAt: new Date()]]
	}
	
	def cleanup() {
		db.comments.remove([:])
		db.rollingStocks.remove([:])
	}
	
	def "should find comments by id"() {
		given:
		def c = db.comments.findOne(content: 'Comment1')
		def id = c._id
		assert id != null
		
		when:
		def comment = service.findById id
		
		then:
		comment != null
	}

	def "should find comments by author"() {
		given:
		def author = new Account(slug: 'bob')
		
		when:
		def results = service.findByAuthor author

		then:
		results != null
		results.size == 2
		results.collect{ it.content }.sort() == ['Comment1', 'Comment2']
	}
	
	def "should find comments by rolling stock"() {
		given:
		def doc = db.rollingStocks.findOne(slug: 'acme-69501')
		def rs = new RollingStock(id: doc._id, slug: doc.slug)
		assert rs != null
		
		when:
		def results = service.findByRollingStock rs
		
		then:
		results != null
		results.size == 2
		results.collect{ it.content }.sort() == ['Comment1', 'Comment3']
	}
	
	def "should save comments"() {
		given:
		def doc = db.rollingStocks.findOne(slug: 'acme-69501')
		def rs = new RollingStock(id: doc._id, slug: doc.slug)
		rs.setBrand(WeakDbRef.buildFromSlug('acme', Brand.class))
		assert rs != null
		
		and:
		def newComment = new Comment(content: 'My comment')
		newComment.setRollingStock(rs)
		newComment.setAuthor(new Account(emailAddress: 'bob@mail.com', displayName: 'Bob'))
		
		when:
		service.save newComment
		
		then:
		newComment.id != null
		
		and:
		def c = db.comments.findOne(_id: newComment.id)
		
		c != null
		c.author.slug == 'bob'
		c.rollingStock.slug == 'acme-69501'
		c.postedAt != null 
	}
	
	def "should remove comments"() {
		given:
		def doc = db.comments.findOne(content: 'Comment1')
		def comment = new Comment(id: doc._id)
		assert comment != null
		
		when:
		service.remove comment
		
		then:
		def c = db.comments.findOne(_id: comment.id)
		c == null
	}
}
