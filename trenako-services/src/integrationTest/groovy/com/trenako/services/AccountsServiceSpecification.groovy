/*
* Copyright 2012 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the 'License');
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*   http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an 'AS IS' BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.trenako.services

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException

import com.trenako.entities.Account
import com.trenako.services.AccountsService

/**
 *
 * @author Carlo Micieli
 *
 */
class AccountsServiceSpecification extends MongoSpecification {

	@Autowired AccountsService service
	
	def setup() {
		db.accounts << [
				[slug: 'the-rocket', emailAddress: 'george@stephenson.com', password: 'secret', displayName: 'The rocket'],
				[slug: 'john-doe', emailAddress: 'john@doe.com', password: 'pa$$word', displayName: 'John Doe']
			]
	}
	
	def cleanup() {
		db.accounts.remove([:])
	}
	
	def "should find accounts for the provided email address"() {
		when:
		def user = service.findByEmailAddress 'george@stephenson.com'
		
		then:
		user != null
		user.emailAddress == 'george@stephenson.com'
		user.displayName == 'The rocket'
	}
	
	def "should return null if no account is found for the provided email address"() {
		when:
		def account = service.findByEmailAddress 'mail@notfound.com'
		
		then:
		account == null
	}
	
	def "should find accounts for the provided unique id"() {
		given:
		def user = db.accounts.findOne(slug: 'john-doe')
		def userId = user?._id
		assert userId != null
	
		when:
		def account = service.findById userId
		
		then:
		account != null
		account.emailAddress == 'john@doe.com'
		account.displayName == 'John Doe'
	}
	
	def "should return null if no account is found for the provided unique id"() {
		given:
		def userId = new ObjectId('47cc67093475061e3d95369d')
		
		when:
		def account = service.findById userId
		
		then:
		account == null
	}
	
	def "should find accounts for the provided slug value"() {
		when:
		def user = service.findBySlug 'john-doe'
		
		then:
		user != null
		user.emailAddress == 'john@doe.com'
		user.displayName == 'John Doe'
	}
	
	def "should return null if no account is found for the provided slug value"() {
		when:
		def user = service.findBySlug 'not-found'
		
		then:
		user == null
	}
	
	def "should throw an exception if the email address is already used"() {
		given:
		def newUser = new Account(
			emailAddress: 'george@stephenson.com',
			password: 'secret',
			displayName: 'Jane Doe',
			enabled: true,
			locked: false,
			expired: true)
			
		when:
		service.save newUser
		
		then:
		thrown(DuplicateKeyException)
		newUser.id == null
	}
	
	def "should throw an exception if the slug is already used"() {
		given:
		def newUser = new Account(
			emailAddress: 'jane@doe.com',
			password: 'secret',
			displayName: 'The Rocket',
			enabled: true,
			locked: false,
			expired: true)
			
		when:
		service.save newUser
		
		then:
		thrown(DuplicateKeyException)
		newUser.id == null
	}
	
	def "should create new accounts"() {
		given:
		def newUser = new Account(
			emailAddress: 'jane@doe.com',
			password: 'secret',
			displayName: 'Jane Doe',
			enabled: true,
			locked: false,
			expired: true)
		
		when:
		service.save newUser

		then:
		newUser.id != null
		newUser.emailAddress == 'jane@doe.com'
		newUser.password == 'secret'
		newUser.displayName == 'Jane Doe'
		newUser.enabled == true
		newUser.locked == false
		newUser.expired == true
		
		// added automatically before save
		newUser.slug == 'jane-doe'
	}
	
	def "should save accounts modifications"() {
		given:
		def doc = db.accounts.findOne(slug: 'john-doe')
		def user = new Account(
			id: doc._id,
			slug: doc.slug,
			emailAddress: doc.emailAddress,
			displayName: doc.displayName,
			password: doc.password)
		
		when:
		user.emailAddress = 'new@mail.com'
		user.password = 'newpa$$'
		
		and:
		service.save user
		
		then:
		def savedDoc = db.accounts.findOne(slug: 'john-doe')
		savedDoc.emailAddress == 'new@mail.com'
		savedDoc.password == 'newpa$$'
	}
	
	def "should remove accounts"() {
		given:
		def doc = db.accounts.findOne(slug: 'john-doe')
		def user = new Account(id: doc._id)

		when:
		service.remove user
		
		then:
		def userDb = db.accounts.findOne(slug: 'john-doe')
		userDb == null
	}
}
