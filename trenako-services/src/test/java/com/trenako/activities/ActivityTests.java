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
package com.trenako.activities;

import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.trenako.entities.Account;
import com.trenako.entities.Collection;
import com.trenako.entities.RollingStock;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ActivityTests {

	@Test
	public void shouldProduceStringRepresentationForActivies() {
		Activity a = new Activity(user(), 
				ActivityVerb.RS_INSERT, 
				ActivityObject.rsObject(rollingStock()));
		assertEquals("activity{actor: bob, verb: rs-insert, object: object{type: rollingStock, url: /rollingstocks/acme-123456, name: ACME 123456}, context: null}", a.toString());
	}
	
	@Test
	public void shouldCreateNewActivitiesWithoutContext() {
		Activity a = new Activity(user(), 
			ActivityVerb.RS_INSERT, 
			ActivityObject.rsObject(rollingStock()));
		
		assertEquals("bob", a.getActor());
		assertEquals("rs-insert", a.getVerb());
		assertEquals("object{type: rollingStock, url: /rollingstocks/acme-123456, name: ACME 123456}", a.getObject().toString());
		assertNull("Activity context is not null", a.getContext());
	}
	
	@Test
	public void shouldCreateNewActivitiesWithContext() {
		Activity a = new Activity(user(), 
			ActivityVerb.RS_INSERT, 
			ActivityObject.rsObject(rollingStock()),
			ActivityContext.collectionContext(collection()));
		
		assertEquals("bob", a.getActor());
		assertEquals("rs-insert", a.getVerb());
		assertEquals("object{type: rollingStock, url: /rollingstocks/acme-123456, name: ACME 123456}", a.getObject().toString());
		assertEquals("activityContext{type: collection, name: bob, description: }", a.getContext().toString());
	}
	
	@Test
	public void shouldCheckWheterTwoActivitiesAreEquals() {
		Activity a = new Activity(user(), 
				ActivityVerb.RS_INSERT, 
				ActivityObject.rsObject(rollingStock()),
				ActivityContext.collectionContext(collection()));
		Activity b = new Activity(user(), 
				ActivityVerb.RS_INSERT, 
				ActivityObject.rsObject(rollingStock()),
				ActivityContext.collectionContext(collection()));
		assertTrue("Activities are different", a.equals(a));
		assertTrue("Activities are different", a.equals(b));
		
		Activity c = new Activity(user(), 
				ActivityVerb.RS_INSERT, 
				ActivityObject.rsObject(rollingStock()));
		Activity d = new Activity(user(), 
				ActivityVerb.RS_INSERT, 
				ActivityObject.rsObject(rollingStock()));
		assertTrue("Activities are different", c.equals(d));
	}
	
	@Test
	public void shouldCheckWheterTwoActivitiesAreDifferent() {
		Activity a = new Activity(user(), 
				ActivityVerb.RS_INSERT, 
				ActivityObject.rsObject(rollingStock()),
				ActivityContext.collectionContext(collection()));
		Activity b = new Activity(user(), 
				ActivityVerb.RS_UPDATE, 
				ActivityObject.rsObject(rollingStock()),
				ActivityContext.collectionContext(collection()));
		assertFalse("Activities are equals", a.equals(b));
	}
	
	private RollingStock rollingStock() { 
		return new RollingStock.Builder(acme(), "123456")
			.scale(scaleH0())
			.railway(db())
			.build();
	}
	
	public Collection collection() {
		return new Collection(user());
	}
	
	private Account user() {
		return new Account.Builder("mail@mail.com")
			.displayName("Bob")
			.build();
	}
}
