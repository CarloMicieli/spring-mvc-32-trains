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
package com.trenako.repositories.mongo;

import static com.trenako.test.TestDataBuilder.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.mockito.ArgumentCaptor;


import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.DBObject;
import com.trenako.entities.Account;
import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;
import com.trenako.entities.RollingStockReviews;
import com.trenako.repositories.ReviewsRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ReviewsRepositoryTests extends AbstractMongoRepositoryTests {

	private RollingStock rollingStock = new RollingStock.Builder(acme(), "123456")
		.railway(fs())
		.scale(scaleH0())
		.build();

	private ReviewsRepository repo;
	
	@Override
	void initRepository(MongoTemplate mongo) {
		this.repo = new ReviewsRepositoryImpl(mongo);
	}

	@Test
	public void shouldFindRollingStockReviewsById() {
		ObjectId id = new ObjectId();
		RollingStockReviews value = new RollingStockReviews();
		when(mongo().findById(eq(id), eq(RollingStockReviews.class))).thenReturn(value);
		
		RollingStockReviews rsReviews = repo.findById(id);
		
		assertNotNull("Reviews are null", rsReviews);
		verify(mongo(), times(1)).findById(eq(id), eq(RollingStockReviews.class));
	}

	@Test
	public void shouldFindRollingStockReviewsBySlug() {
		String slug = "review-slug";
		RollingStockReviews value = new RollingStockReviews();
		when(mongo().findOne(isA(Query.class), eq(RollingStockReviews.class))).thenReturn(value);
		
		RollingStockReviews rsReviews = repo.findBySlug(slug);
		
		assertNotNull("Reviews are null", rsReviews);
		
		ArgumentCaptor<Query> arg = ArgumentCaptor.forClass(Query.class);
		verify(mongo(), times(1)).findOne(arg.capture(), eq(RollingStockReviews.class));
		assertEquals("{ \"slug\" : \"review-slug\"}", queryObject(arg).toString());
	}

	@Test
	public void shouldFindRollingStockReviewsByRollingStock() {
		RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.railway(fs())
			.scale(scaleH0())
			.build();
			
		RollingStockReviews value = new RollingStockReviews();
		when(mongo().findOne(isA(Query.class), eq(RollingStockReviews.class))).thenReturn(value);
		
		RollingStockReviews rsReviews = repo.findByRollingStock(rs);
		
		assertNotNull("Reviews are null", rsReviews);
		
		ArgumentCaptor<Query> arg = ArgumentCaptor.forClass(Query.class);
		verify(mongo(), times(1)).findOne(arg.capture(), eq(RollingStockReviews.class));
		assertEquals("{ \"rollingStock.slug\" : \"acme-123456\"}", queryObject(arg).toString());
	}
	
	@Test
	public void shouldAddNewReviewsToRollingStockReviews() {
		Review review = newReview();
		
		repo.addReview(rollingStock, review);
		
		ArgumentCaptor<Query> argQuery = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> argUpdate = ArgumentCaptor.forClass(Update.class);
		verify(mongo(), times(1)).upsert(argQuery.capture(), argUpdate.capture(), eq(RollingStockReviews.class));
		assertEquals("{ \"slug\" : \"acme-123456\"}", queryObject(argQuery).toString());
		
		DBObject updateObject = updateObject(argUpdate);
		String expected = "{ \"$set\" : { \"rollingStock\" : { \"slug\" : \"acme-123456\" , \"label\" : \"ACME 123456\"}} , " +
			"\"$push\" : { \"reviews\" : { \"author\" : \"user-name\" , " +
			"\"title\" : \"Title\" , \"content\" : \"Review\" , \"lang\" : \"en\" , \"rating\" : 3}} , "+
			"\"$inc\" : { \"numberOfReviews\" : 1 , \"totalRating\" : 3}}";
		assertEquals(expected, updateObject.toString());
	}
	
	@Test
	public void shouldRemoveReviewsFromRollingStockReviews() {
		Review review = newReview();
		repo.removeReview(rollingStock, review);
		
		ArgumentCaptor<Query> argQuery = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> argUpdate = ArgumentCaptor.forClass(Update.class);
		verify(mongo(), times(1)).updateFirst(argQuery.capture(), argUpdate.capture(), eq(RollingStockReviews.class));
		assertEquals("{ \"slug\" : \"acme-123456\"}", queryObject(argQuery).toString());
		
		assertEquals("{ \"$pull\" : { \"reviews\" : { \"author\" : \"user-name\"}} , \"$inc\" : { \"numberOfReviews\" : -1 , \"totalRating\" : -3}}", 
			updateObject(argUpdate).toString());
	}

	@Test
	public void shouldSaveRollingStockReviews() {
		RollingStockReviews r = new RollingStockReviews();
		repo.save(r);
		verify(mongo(), times(1)).save(eq(r));
	}

	@Test
	public void shouldRemoveRollingStockReviews() {
		RollingStockReviews r = new RollingStockReviews();
		repo.remove(r);
		verify(mongo(), times(1)).remove(eq(r));
	}
	
	private Review newReview() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();

		return new Review(author, "Title", "Review", 3);
	}

}