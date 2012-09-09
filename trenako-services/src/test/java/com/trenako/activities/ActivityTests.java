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
import com.trenako.entities.CollectionItem;
import com.trenako.entities.Comment;
import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;
import com.trenako.entities.WishList;
import com.trenako.entities.WishListItem;
import com.trenako.mapping.WeakDbRef;
import com.trenako.values.Priority;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ActivityTests {

	@Test
	public void shouldBuildActivitiesFromWishListItems() {
		Activity a = Activity.buildForWishList(wishList(), wishListItem());
		
		assertEquals("bob", a.getActor());
		assertEquals(ActivityVerb.ADD_WISH_LIST.label(), a.getVerb());
		assertEquals("ACME 123456", a.getObject().getDisplayName());
		assertEquals("/rollingstocks/acme-123456", a.getObject().getUrl());
		assertEquals("rollingStock", a.getObject().getObjectType());
		assertEquals(date("2012/02/02"), a.getRecorded());
		assertEquals("bob-my-list", a.getContext().getName());
		assertEquals("wishList", a.getContext().getContextType());
	}
	
	@Test
	public void shouldBuildActivitiesFromCollectionItems() {
		Activity a = Activity.buildForCollection(collection(), collectionItem());
		
		assertEquals("bob", a.getActor());
		assertEquals(ActivityVerb.ADD_COLLECTION.label(), a.getVerb());
		assertEquals("ACME 123456", a.getObject().getDisplayName());
		assertEquals("/rollingstocks/acme-123456", a.getObject().getUrl());
		assertEquals("rollingStock", a.getObject().getObjectType());
		assertEquals("collection", a.getContext().getContextType());
		assertEquals("bob", a.getContext().getName());
		assertNotNull(a.getRecorded());
	}
	
	@Test
	public void shouldBuildActivitiesFromRollingStocksCreation() {
		Activity a = Activity.buildForRsCreate(rollingStock());
		
		assertEquals("bob", a.getActor());
		assertEquals(ActivityVerb.RS_INSERT.label(), a.getVerb());
		assertEquals("ACME 123456", a.getObject().getDisplayName());
		assertEquals("/rollingstocks/acme-123456", a.getObject().getUrl());
		assertEquals("rollingStock", a.getObject().getObjectType());
		assertEquals(date("2012/02/02"), a.getRecorded());
		assertNull(a.getContext());
	}
	
	@Test
	public void shouldBuildActivitiesFromRollingStocksModification() {
		Activity a = Activity.buildForRsChange(rollingStock());
		
		assertEquals("bob", a.getActor());
		assertEquals(ActivityVerb.RS_UPDATE.label(), a.getVerb());
		assertEquals("ACME 123456", a.getObject().getDisplayName());
		assertEquals("/rollingstocks/acme-123456", a.getObject().getUrl());
		assertEquals("rollingStock", a.getObject().getObjectType());
		assertEquals(date("2012/02/02"), a.getRecorded());
		assertNull(a.getContext());
	}
	
	@Test
	public void shouldBuildActivitiesFromUserComments() {
		Activity a = Activity.buildForComment(comment(), rsRef());
		
		assertEquals("bob", a.getActor());
		assertEquals(ActivityVerb.COMMENT.label(), a.getVerb());
		assertEquals("ACME 123456", a.getObject().getDisplayName());
		assertEquals("/rollingstocks/acme-123456", a.getObject().getUrl());
		assertEquals("rollingStock", a.getObject().getObjectType());
		assertEquals(date("2012/02/02"), a.getRecorded());
		assertNull(a.getContext());
	}

	@Test
	public void shouldBuildActivitiesFromUserReviews() {
		Activity a = Activity.buildForReview(review(), rsRef());
		
		assertEquals("bob", a.getActor());
		assertEquals(ActivityVerb.REVIEW.label(), a.getVerb());
		assertEquals("ACME 123456", a.getObject().getDisplayName());
		assertEquals("/rollingstocks/acme-123456", a.getObject().getUrl());
		assertEquals("rollingStock", a.getObject().getObjectType());
		assertEquals(date("2012/02/02"), a.getRecorded());
		assertNull(a.getContext());
	}
	
	@Test
	public void shouldProduceStringRepresentationForActivies() {
		Activity a = new Activity(user(), 
				ActivityVerb.RS_INSERT, 
				ActivityObject.rsObject(rsRef()));
		assertEquals("activity{actor: bob, verb: rs-insert, object: object{type: rollingStock, url: /rollingstocks/acme-123456, name: ACME 123456}, context: null}", a.toString());
	}
	
	@Test
	public void shouldCreateNewActivitiesWithoutContext() {
		Activity a = new Activity(user(), 
			ActivityVerb.RS_INSERT, 
			ActivityObject.rsObject(rsRef()));
		
		assertEquals("bob", a.getActor());
		assertEquals("rs-insert", a.getVerb());
		assertEquals("object{type: rollingStock, url: /rollingstocks/acme-123456, name: ACME 123456}", a.getObject().toString());
		assertNull("Activity context is not null", a.getContext());
	}
	
	@Test
	public void shouldCreateNewActivitiesWithContext() {
		Activity a = new Activity(user(), 
			ActivityVerb.RS_INSERT, 
			ActivityObject.rsObject(rsRef()),
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
				ActivityObject.rsObject(rsRef()),
				ActivityContext.collectionContext(collection()));
		Activity b = new Activity(user(), 
				ActivityVerb.RS_INSERT, 
				ActivityObject.rsObject(rsRef()),
				ActivityContext.collectionContext(collection()));
		assertTrue("Activities are different", a.equals(a));
		assertTrue("Activities are different", a.equals(b));
		
		Activity c = new Activity(user(), 
				ActivityVerb.RS_INSERT, 
				ActivityObject.rsObject(rsRef()));
		Activity d = new Activity(user(), 
				ActivityVerb.RS_INSERT, 
				ActivityObject.rsObject(rsRef()));
		assertTrue("Activities are different", c.equals(d));
	}
	
	@Test
	public void shouldCheckWheterTwoActivitiesAreDifferent() {
		Activity a = new Activity(user(), 
				ActivityVerb.RS_INSERT, 
				ActivityObject.rsObject(rsRef()),
				ActivityContext.collectionContext(collection()));
		Activity b = new Activity(user(), 
				ActivityVerb.RS_UPDATE, 
				ActivityObject.rsObject(rsRef()),
				ActivityContext.collectionContext(collection()));
		assertFalse("Activities are equals", a.equals(b));
	}
	
	WeakDbRef<RollingStock> rsRef() {
		return WeakDbRef.buildRef(rollingStock());
	}
	
	RollingStock rollingStock() { 
		RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.scale(scaleH0())
			.railway(db())
			.build();
		
		 rs.setModifiedBy("bob");
		 rs.setLastModified(date("2012/02/02"));
		 
		 return rs;
	}
	
	Review review() {
		Review r = new Review(user(), "title", "content", 4, null);
		r.setPostedAt(date("2012/02/02"));
		return r;
	}
	
	Comment comment() {
		return new Comment("bob", "my comment", date("2012/02/02"));
	}
	
	Collection collection() {
		return new Collection(user());
	}
	
	CollectionItem collectionItem() {
		return new CollectionItem(rollingStock(), date("2012/02/02"));
	}
	
	Account user() {
		return new Account.Builder("mail@mail.com")
			.displayName("Bob")
			.build();
	}
	
	WishList wishList() {
		return new WishList(user(), "My list");
	}
	
	WishListItem wishListItem() {
		return new WishListItem(rollingStock(), "notes", Priority.LOW, date("2012/02/02"));
	}
}
