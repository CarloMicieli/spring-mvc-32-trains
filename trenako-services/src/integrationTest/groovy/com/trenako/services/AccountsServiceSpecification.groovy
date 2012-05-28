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

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query

import static org.springframework.data.mongodb.core.query.Query.*
import static org.springframework.data.mongodb.core.query.Criteria.*

import com.trenako.entities.Account;
import com.trenako.services.AccountsServiceImpl;

/**
 *
 * @author Carlo Micieli
 *
 */
@ContextConfiguration(locations = "classpath:META-INF/spring-context.xml")
class AccountsServiceSpecification extends Specification {

	@Autowired MongoTemplate mongo
	@Autowired AccountsServiceImpl service
	
	def setup() {
		def accounts = [
			new Account(emailAddress: "george@stephenson.com",
				password: "segret",
				displayName: "The rocket"),
			new Account(emailAddress: "john@doe.com",
				password: "password",
				displayName: "John Doe")
			]
		mongo.insert accounts, Account.class
	}
	
	def cleanup() {
		def all = new Query()
		mongo.remove all, Account.class
	}
	
	def "should find accounts by email address"() {
		when:
		def user = service.findByEmailAddress "george@stephenson.com"
		
		then:
		user != null
		user.emailAddress == "george@stephenson.com"
		user.displayName == "The rocket"
	}
	
	def "should find accounts by user id"() {
		given:
		def id = mongo.findOne(query(where("slug").is("john-doe")), Account.class).id
	
		when:
		def account = service.findById id
		
		then:
		account != null
		account.emailAddress == "john@doe.com"
		account.displayName == "John Doe"
	}
	
	def "should find accounts by slug"() {
		when:
		def user = service.findBySlug "john-doe"
		
		then:
		user != null
		user.emailAddress == "john@doe.com"
		user.displayName == "John Doe"
	}
	
	def "should save accounts"() {
		given:
		def newUser = new Account(emailAddress: "jane@doe.com",
			password: "secret",
			displayName: "Jane Doe",
			enabled: true,
			locked: false,
			expired: true)
		
		when:
		service.save newUser

		then:
		newUser.id != null
	}
	
	def "should remove accounts"() {
		given:
		def user = mongo.findOne query(where("slug").is("john-doe")), Account.class

		when:
		service.remove user
		
		then:
		def user2 = mongo.findOne query(where("slug").is("john-doe")), Account.class
		user2 == null
	}
}
