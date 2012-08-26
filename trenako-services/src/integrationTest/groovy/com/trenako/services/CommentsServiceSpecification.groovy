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

import static com.trenako.test.TestDataBuilder.*

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired

import com.trenako.entities.Account
import com.trenako.entities.Comment
import com.trenako.entities.RollingStockComments
import com.trenako.entities.RollingStock

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
		
		db.comments.insert(
			slug: 'acme-69501',
			rollingStock: [slug: 'acme-69501', label: 'ACME 69501'],
			numberOfComments: 2,
			items: [
				[commentId: '20120101', 
					author: new ObjectId('47cc67093475061e3d95369e'), 
					content: 'Comment 1', 
					postedAt: new Date()],
				[commentId: '20120102', 
					author: new ObjectId('47cc67093475061e3d95369f'), 
					content: 'Comment 2', 
					postedAt: new Date()]
				]
			)
		
		db.comments.insert(
			slug: 'acme-69502',
			rollingStock: [slug: 'acme-69502', label: 'ACME 69502'],
			numberOfComments: 3,
			items: [
				[commentId: '20120101',
					author: new ObjectId('47cc67093475061e3d95369e'),
					content: 'Comment 3',
					postedAt: new Date(),
					answers: [
						[commentId: '20120102',
							author: new ObjectId('47cc67093475061e3d95369f'),
							content: 'Answer 1',
							postedAt: new Date()],
						[commentId: '20120103',
							author: new ObjectId('47cc67093475061e3d95369f'),
							content: 'Answer 2',
							postedAt: new Date()]
						]
					]
				])

	}
	
	def cleanup() {
		db.comments.remove([:])
		db.rollingStocks.remove([:])
	}
	
	def "should find comments by rolling stock"() {
		given:
		def rs = new RollingStock(slug: 'acme-69501')
		
		when:
		def results = service.findByRollingStock rs
		
		then:
		results != null
		results.numberOfComments == 2
		results.items.size() == 2
		results.items.collect{ it.content }.sort() == ['Comment 1', 'Comment 2']
	}
	
	def "should post comments"() {
		given:
		def rs = new RollingStock.Builder(roco(), "69503")
			.scale(scaleH0())
			.railway(db())
			.description("Desc")
			.build()
			
		and:
		def authorId = new ObjectId()
		def newComment = new Comment(authorId: authorId, content: 'My comment')
		
		when:
		service.postComment(rs, newComment)
		
		then:
		def comments = db.comments.findOne(slug: 'roco-69503')
		
		comments != null
		comments.numberOfComments == 1
		comments.rollingStock == [slug: 'roco-69503', label: 'Roco 69503']
		comments.items != null
		comments.items.size() == 1
		
		and:
		def comment = comments.items[0]
		comment.authorId == authorId
		comment.content == 'My comment'
		
		comment.commentId != null
		comment.postedAt != null
	}
	
	def "should post comment answers"() {
		given:
		def rs = new RollingStock.Builder(acme(), "69501")
			.scale(scaleH0())
			.railway(db())
			.description("Desc")
			.build()
			
		and:
		def authorId = new ObjectId()
		def parent = new Comment(commentId: '20120101')
		def newAnswer = new Comment(authorId: authorId, content: 'My answer')
		
		when:
		service.postAnswer(rs, parent, newAnswer)
		
		then:
		def comments = db.comments.findOne(slug: 'acme-69501')
		
		comments != null
		comments.numberOfComments == 3
		comments.items != null
		comments.items.size() == 2
		
		and:
		def comment = comments.items[0]
		comment.answers != null
		comment.answers.size() == 1
		
		def answer = comment.answers[0]
		answer.authorId == authorId
		answer.content == 'My answer'
		answer.postedAt != null
	}
	
	def "should delete comments"() {
		given:
		def rs = new RollingStock.Builder(acme(), "69501")
			.scale(scaleH0())
			.railway(db())
			.description("Desc")
			.build()
			
		and:
		def authorId = new ObjectId()
		def comment = new Comment(commentId: '20120101')
		
		when:
		service.deleteComment(rs, comment)
		
		then:
		def comments = db.comments.findOne(slug: 'acme-69501')
		
		comments != null
		comments.numberOfComments == 1
		comments.items != null
		comments.items.size() == 1		
	}
	
	def "should delete comment answers"() {
		given:
		def rs = new RollingStock.Builder(acme(), "69502")
			.scale(scaleH0())
			.railway(db())
			.description("Desc")
			.build()
			
		and:
		def authorId = new ObjectId()
		def parent = new Comment(commentId: '20120101')
		def answer = new Comment(commentId: '20120103')
		
		when:
		service.deleteAnswer(rs, parent, answer)
		
		then:
		def comments = db.comments.findOne(slug: 'acme-69502')
		
		comments != null
		comments.numberOfComments == 2
		comments.items != null
		comments.items.size() == 1
		
		and:
		def comment = comments.items[0]
		comment.answers != null
		comment.answers.size() == 1
		comment.answers.collect{ it.content } == ['Answer 1']
	}
}
