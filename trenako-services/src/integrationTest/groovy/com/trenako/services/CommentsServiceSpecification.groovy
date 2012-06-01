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

import java.util.List;

import spock.lang.*

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query

import static org.springframework.data.mongodb.core.query.Query.*
import static org.springframework.data.mongodb.core.query.Criteria.*

import com.trenako.entities.Account
import com.trenako.entities.Brand
import com.trenako.entities.Scale
import com.trenako.entities.Comment
import com.trenako.entities.Railway
import com.trenako.entities.RollingStock
import com.trenako.services.CommentsService

/**
 * 
 * @author Carlo Micieli
 *
 */
@ContextConfiguration(locations = "classpath:META-INF/spring-context.xml")
class CommentsServiceSpecification extends Specification {
	@Autowired MongoTemplate mongo
	@Autowired CommentsService service;
	
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
		def collection = [rs1, rs2]
		mongo.insert collection, RollingStock.class
		
		def bob = new Account(emailAddress: "bob@mail.com", password: "secret", displayName: 'Bob')
		def alice = new Account(emailAddress: "alice@mail.com", password: "secret", displayName: 'Alice')
		def authors = [bob, alice]
		mongo.insert authors, Account.class

		def comments = [ 
			new Comment(author: bob, rollingStock: rs1, content: "Comment1"),
			new Comment(author: bob, rollingStock: rs2, content: "Comment2"),
			new Comment(author: alice, rollingStock: rs2, content: "Comment3"),
			]
		mongo.insert comments, Comment.class
		
	}
	
	def cleanup() {
		def all = new Query()
		
		mongo.remove all, Comment.class
		mongo.remove all, RollingStock.class
		mongo.remove all, Scale.class
		mongo.remove all, Brand.class
		mongo.remove all, Railway.class
	}
	
	def "should find comments by id"() {
		given:
		def c = mongo.findOne query(where("content").is("Comment1")), Comment.class
		assert c != null
		def id = c.id
		
		when:
		def comment = service.findById id
		
		then:
		comment != null
	}

	def "should find comments by author"() {
		given:
		def author = mongo.findOne query(where("emailAddress").is("bob@mail.com")), Account.class
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
		def authorName = "alice"
		
		when:
		def results = service.findByAuthor authorName

		then:
		results != null
		results.size == 1
	}
	
	def "should find comments by rolling stock"() {
		given:
		def rs = mongo.findOne query(where("slug").is("acme-69501")), RollingStock.class
		assert rs != null
		
		when:
		def results = service.findByRollingStock rs
		
		then:
		results != null
		results.size == 1
		results.collect{ it.content } == ["Comment1"]
	}
	
	def "should find comments by rolling stock slug"() {
		when:
		def results = service.findByRollingStock "acme-69501"
		
		then:
		results != null
		results.size == 1
		results.collect{ it.content } == ["Comment1"]
	}
	
	def "should save comments"() {
		given:
		def rs = mongo.findOne query(where("slug").is("acme-69501")), RollingStock.class
		assert rs != null

		and:
		def author = mongo.findOne query(where("emailAddress").is("bob@mail.com")), Account.class
		assert author != null
		
		and:
		def newComment = new Comment(author: author, rollingStock: rs, content: "My comment")
		
		when:
		service.save newComment
		
		then:
		newComment.id != null
		
		and:
		def c = mongo.findById newComment.id, Comment.class
		
		c != null
		c.authorName == "bob"
		c.rsSlug == "acme-69501"
		c.postedAt != null 
	}
	
	def "should remove comments"() {
		given:
		def comment = mongo.findOne query(where("content").is("Comment1")), Comment.class
		assert comment != null
		
		when:
		service.remove comment
		
		then:
		def c = mongo.findById comment.id, Comment.class
		c == null
	}
}
