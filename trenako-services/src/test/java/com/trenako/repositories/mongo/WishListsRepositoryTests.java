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
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.GregorianCalendar;

import com.trenako.entities.Account;
import com.trenako.entities.Money;
import com.trenako.entities.RollingStock;
import com.trenako.entities.WishList;
import com.trenako.entities.WishListItem;
import com.trenako.repositories.WishListsRepository;
import com.trenako.values.Priority;
import com.trenako.values.Visibility;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class WishListsRepositoryTests extends AbstractMongoRepositoryTests {
	
	private static Date now = new GregorianCalendar(2012, 6, 1, 10, 0).getTime();
	
	private Account owner = new Account.Builder("mail@mail.com")
		.displayName("Bob")
		.build();	
	private RollingStock rollingStock = new RollingStock.Builder(acme(), "123456")
		.railway(fs())
		.scale(scaleH0())
		.build();

	private WishListsRepository repo;
	
	@Override
	void initRepository(MongoTemplate mongo) {
		this.repo = new WishListsRepositoryImpl(mongo);
		
		// faking the current timestamp
		((WishListsRepositoryImpl) repo).setTimestamp(now);
	}
	
	@Test
	@SuppressWarnings("unused")
	public void shouldFindWishListsByOwnerWithoutLoadingItems() {
		Iterable<WishList> results = repo.findByOwner(owner);
		
		ArgumentCaptor<Query> arg = ArgumentCaptor.forClass(Query.class);
		verify(mongo(), times(1)).find(arg.capture(), eq(WishList.class));
		assertEquals("{ \"owner\" : \"bob\"}", queryObject(arg).toString());
		assertEquals("{ \"items\" : 0}", fieldsObject(arg).toString());
		assertEquals("{ \"name\" : 1}", sortObject(arg).toString());
	}
	
	@Test
	@SuppressWarnings("unused")
	public void shouldFindWishListsByOwnerLoadingTheLatestItems() {
		Iterable<WishList> results = repo.findAllByOwner(owner, 10);
		
		ArgumentCaptor<Query> arg = ArgumentCaptor.forClass(Query.class);
		verify(mongo(), times(1)).find(arg.capture(), eq(WishList.class));
		assertEquals("{ \"owner\" : \"bob\"}", queryObject(arg).toString());
		assertEquals("{ \"items\" : { \"$slice\" : -10}}", fieldsObject(arg).toString());
		assertEquals("{ \"name\" : 1}", sortObject(arg).toString());
	}
	
	@Test
	@SuppressWarnings("unused")
	public void shouldFindWishListsBySlug() {
		String slug = "list-slug";
		
		WishList wishList = repo.findBySlug(slug);
		
		ArgumentCaptor<Query> arg = ArgumentCaptor.forClass(Query.class);
		verify(mongo(), times(1)).findOne(arg.capture(), eq(WishList.class));
		assertEquals("{ \"slug\" : \"list-slug\"}", queryObject(arg).toString());
	}

	@Test
	@SuppressWarnings("unused")
	public void shouldCheckWhetherWishListContainsTheProvidedRollingStock() {
		WishList wishList = newWishList("My list");
		boolean ret = repo.containsRollingStock(wishList, rollingStock);
		
		assertEquals("{ \"slug\" : \"bob-my-list\" , \"items.rollingStock.slug\" : \"acme-123456\"}", 
				verifyCount(WishList.class).toString());
	}
	
	@Test
	public void shouldAddNewItemsToWishLists() {
		WishList wishList = newWishList("My list");
		WishListItem newItem = new WishListItem(rollingStock, "My notes", Priority.LOW, now);
		
		repo.addItem(wishList, newItem);
		
		ArgumentCaptor<Query> argQuery = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> argUpdate = ArgumentCaptor.forClass(Update.class);
		verify(mongo(), times(1)).upsert(argQuery.capture(), argUpdate.capture(), eq(WishList.class));
		assertEquals("{ \"slug\" : \"bob-my-list\"}", queryObject(argQuery).toString());
		
		String expected = "{ \"$set\" : { \"lastModified\" : { \"$date\" : \"2012-07-01T08:00:00.000Z\"} , "+
				"\"owner\" : \"bob\" , \"name\" : \"My list\" , \"visibility\" : \"public\"} , "+
				"\"$inc\" : { \"numberOfItems\" : 1} , \"$push\" : { \"items\" : "+
				"{ \"itemId\" : \"acme-123456_2012-07-01_10-00-00\" , \"rollingStock\" : { \"slug\" : \"acme-123456\" , \"label\" : \"ACME 123456\"} , "+
				"\"notes\" : \"My notes\" , \"priority\" : \"low\" , \"addedAt\" : { \"$date\" : \"2012-07-01T08:00:00.000Z\"}}}}";
		assertEquals(expected, updateObject(argUpdate).toString());
	}
	
	@Test
	public void shouldUpdateItemInTheWishLists() {
		WishList wishList = newWishList("My list");
		WishListItem newItem = new WishListItem(rollingStock, "My notes", Priority.LOW, now);
		
		repo.updateItem(wishList, newItem);
		
		ArgumentCaptor<Query> argQuery = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> argUpdate = ArgumentCaptor.forClass(Update.class);
		verify(mongo(), times(1)).updateFirst(argQuery.capture(), argUpdate.capture(), eq(WishList.class));
		assertEquals("{ \"slug\" : \"bob-my-list\" , \"items.itemId\" : \"acme-123456_2012-07-01_10-00-00\"}", queryObject(argQuery).toString());
		
		String expected = "{ \"$set\" : { \"lastModified\" : { \"$date\" : \"2012-07-01T08:00:00.000Z\"} , "+
			"\"items.$\" : { \"itemId\" : \"acme-123456_2012-07-01_10-00-00\" , \"rollingStock\" : { \"slug\" : \"acme-123456\" , \"label\" : \"ACME 123456\"} , "+
			"\"notes\" : \"My notes\" , \"priority\" : \"low\" , \"addedAt\" : { \"$date\" : \"2012-07-01T08:00:00.000Z\"}}}}";
		assertEquals(expected, updateObject(argUpdate).toString());
	}

	@Test
	public void shouldRemoveItemsFromWishLists() {
		WishList wishList = newWishList("My list");
		WishListItem newItem = new WishListItem(rollingStock, "My notes", Priority.LOW, now);
		
		repo.removeItem(wishList, newItem);
		
		ArgumentCaptor<Query> argQuery = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> argUpdate = ArgumentCaptor.forClass(Update.class);
		verify(mongo(), times(1)).updateFirst(argQuery.capture(), argUpdate.capture(), eq(WishList.class));
		assertEquals("{ \"slug\" : \"bob-my-list\"}", queryObject(argQuery).toString());
		
		String expected = "{ \"$set\" : { \"lastModified\" : { \"$date\" : \"2012-07-01T08:00:00.000Z\"}} , "+
			"\"$inc\" : { \"numberOfItems\" : -1} , \"$pull\" : { \"items\" : { \"itemId\" : \"acme-123456_2012-07-01_10-00-00\" , \"rollingStock\" : { \"slug\" : \"acme-123456\" , \"label\" : \"ACME 123456\"} , "+
			"\"notes\" : \"My notes\" , \"priority\" : \"low\" , \"addedAt\" : { \"$date\" : \"2012-07-01T08:00:00.000Z\"}}}}";
		assertEquals(expected, updateObject(argUpdate).toString());
	}

	@Test
	public void shouldChangeWishListsVisibility() {
		WishList wishList = newWishList("My list");
		
		repo.changeVisibility(wishList, Visibility.PRIVATE);
		
		ArgumentCaptor<Query> argQuery = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> argUpdate = ArgumentCaptor.forClass(Update.class);
		verify(mongo(), times(1)).updateFirst(argQuery.capture(), argUpdate.capture(), eq(WishList.class));
		assertEquals("{ \"slug\" : \"bob-my-list\"}", queryObject(argQuery).toString());
		assertEquals("{ \"$set\" : { \"lastModified\" : { \"$date\" : \"2012-07-01T08:00:00.000Z\"} , \"visibility\" : \"private\"}}", 
				updateObject(argUpdate).toString());
	}
	
	@Test
	public void shouldChangeWishListsName() {
		WishList wishList = newWishList("My list");
		
		repo.changeName(wishList, "My renamed list");
		
		ArgumentCaptor<Query> argQuery = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> argUpdate = ArgumentCaptor.forClass(Update.class);
		verify(mongo(), times(1)).updateFirst(argQuery.capture(), argUpdate.capture(), eq(WishList.class));
		assertEquals("{ \"slug\" : \"bob-my-list\"}", queryObject(argQuery).toString());
		assertEquals("{ \"$set\" : { \"lastModified\" : { \"$date\" : \"2012-07-01T08:00:00.000Z\"} , \"name\" : \"My renamed list\" , \"slug\" : \"bob-my-renamed-list\"}}", 
				updateObject(argUpdate).toString());
	}
	
	@Test
	public void shouldChangeWishBudget() {
		Money newBudget = new Money(100, "USD");
		WishList wishList = newWishList("My list");
		
		repo.changeBudget(wishList, newBudget);
		
		ArgumentCaptor<Query> argQuery = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> argUpdate = ArgumentCaptor.forClass(Update.class);
		verify(mongo(), times(1)).updateFirst(argQuery.capture(), argUpdate.capture(), eq(WishList.class));
		assertEquals("{ \"slug\" : \"bob-my-list\"}", queryObject(argQuery).toString());
		assertEquals("{ \"$set\" : { \"lastModified\" : { \"$date\" : \"2012-07-01T08:00:00.000Z\"} , \"budget\" : { \"val\" : 100 , \"cur\" : \"USD\"}}}", 
				updateObject(argUpdate).toString());
	}
	
	@Test
	public void shouldSaveWishLists() {
		WishList wishList = newWishList("My list");
		repo.save(wishList);
		verify(mongo(), times(1)).save(eq(wishList));
	}
	
	@Test
	public void shouldSaveWishListChanges() {
		WishList wishList = newWishList("My list");
		
		repo.saveChanges(wishList);
		
		ArgumentCaptor<Query> argQuery = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> argUpdate = ArgumentCaptor.forClass(Update.class);
		verify(mongo(), times(1)).updateFirst(argQuery.capture(), argUpdate.capture(), eq(WishList.class));
		assertEquals("{ \"slug\" : \"bob-my-list\"}", queryObject(argQuery).toString());
		assertEquals("{ \"$set\" : { \"name\" : \"My list\" , \"slug\" : \"bob-my-list\" , \"notes\" : \"My notes\" , \"budget\" : { \"val\" : 100 , \"cur\" : \"USD\"} , "+
				"\"visibility\" : \"public\" , \"lastModified\" : { \"$date\" : \"2012-07-01T08:00:00.000Z\"}}}", 
				updateObject(argUpdate).toString());
	}
	
	@Test
	public void shouldRemoveWishLists() {
		WishList wishList = newWishList("My list");
		repo.remove(wishList);
		verify(mongo(), times(1)).remove(eq(wishList));
	}

	private WishList newWishList(String name) {
		return new WishList(owner, name, "My notes", Visibility.PUBLIC, new Money(100, "USD"));
	}
}