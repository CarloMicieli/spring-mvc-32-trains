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
import com.trenako.entities.Comment
import com.trenako.entities.Railway
import com.trenako.entities.RollingStock

import com.mongodb.DBRef

import com.trenako.services.CommentsService

/**
 * 
 * @author Carlo Micieli
 *
 */
class CommentsServiceSpecification extends MongoSpecification {

	@Autowired CommentsService service;
	
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

		db.comments << [ 
			[authorName: 'bob', rsSlug: 'acme-69501', content: 'Comment1'],
			[authorName: 'bob', rsSlug: 'acme-69502', content: 'Comment2'],
			[authorName: 'alice', rsSlug: 'acme-69501', content: 'Comment3']]
	}
	
	def cleanup() {
		db.comments.remove([:])
		db.rollingStocks.remove([:])
		db.scales.remove([:])
		db.brands.remove([:])
		db.railways.remove([:])
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
		def doc = db.accounts.findOne(emailAddress: 'bob@mail.com')
		def author = new Account([id: doc._id, slug: doc.slug])
		assert author != null
		
		when:
		def results = service.findByAuthor author

		then:
		results != null
		results.size == 2
		results.collect{ it.content }.sort() == ["Comment1", "Comment2"]
	}
	
	def "should find comments by author name"() {
		given:
		def authorName = 'alice'
		
		when:
		def results = service.findByAuthor authorName

		then:
		results != null
		results.size == 1
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
		results.collect{ it.content } == ['Comment1', 'Comment3']
	}
	
	def "should find comments by rolling stock slug"() {
		when:
		def results = service.findByRollingStock "acme-69502"
		
		then:
		results != null
		results.size == 1
		results.collect{ it.content } == ['Comment2']
	}
	
	def "should save comments"() {
		given:
		def doc = db.rollingStocks.findOne(slug: 'acme-69501')
		def rs = new RollingStock(id: doc._id, slug: doc.slug)
		assert rs != null

		and:
		def docAuthor = db.accounts.findOne(emailAddress: 'bob@mail.com')
		def author = new Account(id: docAuthor._id, slug: docAuthor.slug)
		assert author != null
		
		and:
		def newComment = new Comment(author: author, rollingStock: rs, content: "My comment")
		
		when:
		service.save newComment
		
		then:
		newComment.id != null
		
		and:
		def c = db.comments.findOne(_id: newComment.id)
		
		c != null
		c.authorName == "bob"
		c.rsSlug == "acme-69501"
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
