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

import java.util.GregorianCalendar;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.trenako.entities.Account;
import com.trenako.entities.Collection;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.RollingStock;
import com.trenako.repositories.CollectionsRepository;
import com.trenako.values.Condition;
import com.trenako.values.Visibility;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CollectionsRepositoryTests extends AbstractMongoRepositoryTests {

	private RollingStock rollingStock = new RollingStock.Builder(acme(), "123456")
		.railway(fs())
		.scale(scaleH0())
		.build();
	private Account owner = new Account.Builder("mail@mail.com")
		.displayName("Bob")
		.build();
	
	CollectionsRepository repo;

	@Override
	void initRepository(MongoTemplate mongo) {
		repo = new CollectionsRepositoryImpl(mongo);
	}

	@Test
	public void shouldfindCollectionById() {
		ObjectId id = new ObjectId();
		Collection value = new Collection(owner);
		when(mongo().findById(eq(id), eq(Collection.class))).thenReturn(value);
		
		Collection coll = repo.findById(id);
		
		assertNotNull(coll);
		verify(mongo(), times(1)).findById(eq(id), eq(Collection.class));
	}
	
	@Test
	public void shouldfindCollectionBySlug() {
		String slug = "slug";
		Collection value = new Collection(owner);
		when(mongo().findOne(isA(Query.class), eq(Collection.class))).thenReturn(value);
		
		Collection coll = repo.findBySlug(slug);
		
		assertNotNull(coll);
		assertEquals("{ \"slug\" : \"slug\"}", verifyFindOne(Collection.class).toString());
	}
	
	@Test
	public void shouldfindCollectionByOwner() {
		Collection value = new Collection(owner);
		when(mongo().findOne(isA(Query.class), eq(Collection.class))).thenReturn(value);
		
		Collection coll = repo.findByOwner(owner);

		assertNotNull(coll);
		assertEquals("{ \"owner.slug\" : \"bob\"}", verifyFindOne(Collection.class).toString());
	}
	
	@Test
	public void shouldCheckIfCollectionContainsRollingStocks() {
		when(mongo().count(isA(Query.class), eq(Collection.class))).thenReturn(1L);
		
		boolean ret = repo.containsRollingStock(owner, rollingStock);
		
		assertTrue(ret);
		assertEquals("{ \"owner.slug\" : \"bob\" , \"items.rollingStock.slug\" : \"acme-123456\"}", verifyCount(Collection.class).toString());
	}
	
	@Test
	public void shouldCheckIfCollectionNotContainsRollingStocks() {
		when(mongo().count(isA(Query.class), eq(Collection.class))).thenReturn(0L);
		
		boolean ret = repo.containsRollingStock(owner, rollingStock);
		
		assertFalse(ret);
	}
	
	@Test
	public void shouldAddNewItemsToCollection() {
		((CollectionsRepositoryImpl)repo).setCurrentTimestamp(new GregorianCalendar(2012, 6, 1, 10, 0).getTime());
		
		CollectionItem item = new CollectionItem.Builder(rollingStock)
			.addedAt(new GregorianCalendar(2012, 0, 1).getTime())
			.category("electric-locomotives")
			.notes("My notes")
			.condition(Condition.NEW)
			.build();
		
		repo.addItem(owner, item);
	
		ArgumentCaptor<Query> argQuery = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> argUpdate = ArgumentCaptor.forClass(Update.class);
		verify(mongo(), times(1)).upsert(argQuery.capture(), argUpdate.capture(), eq(Collection.class));
		assertEquals("{ \"owner.slug\" : \"bob\"}", queryObject(argQuery).toString());
		
		String expected = "{ \"$set\" : { \"owner\" : { \"slug\" : \"bob\" , \"label\" : \"Bob\"} , "+
				"\"lastModified\" : { \"$date\" : \"2012-07-01T08:00:00.000Z\"}} , "+
				"\"$push\" : { \"items\" : { \"rollingStock\" : { \"slug\" : \"acme-123456\" , \"label\" : \"ACME 123456\"} , "+
				"\"price\" : 0 , \"condition\" : \"new\" , \"notes\" : \"My notes\" , \"category\" : \"electric-locomotives\" , "+
				"\"quantity\" : 1 , \"addedAt\" : { \"$date\" : \"2011-12-31T23:00:00.000Z\"}}} , \"$inc\" : { \"categories.electricLocomotives\" : 1}}";
		assertEquals(expected, updateObject(argUpdate).toString());
	}
	
	@Test
	public void shouldRemoveItemsFromUserCollection() {
		((CollectionsRepositoryImpl)repo).setCurrentTimestamp(new GregorianCalendar(2012, 6, 1, 10, 0).getTime());
		
		CollectionItem item = new CollectionItem.Builder(rollingStock)
			.addedAt(new GregorianCalendar(2012, 0, 1).getTime())
			.category("electric-locomotives")
			.build();

		repo.removeItem(owner, item);
		
		ArgumentCaptor<Query> argQuery = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> argUpdate = ArgumentCaptor.forClass(Update.class);
		verify(mongo(), times(1)).updateFirst(argQuery.capture(), argUpdate.capture(), eq(Collection.class));
		assertEquals("{ \"owner.slug\" : \"bob\"}", queryObject(argQuery).toString());
		
		String expected = "{ \"$pull\" : { \"items\" : { \"itemId\" : \"acme-123456-2012-01-01\"}} , "+
				"\"$inc\" : { \"categories.electricLocomotives\" : -1} , " +
				"\"$set\" : { \"lastModified\" : { \"$date\" : \"2012-07-01T08:00:00.000Z\"}}}";
		assertEquals(expected, updateObject(argUpdate).toString());
	}
	
	@Test
	public void shouldChangeCollectionVisibility() {
		repo.changeVisibility(owner, Visibility.PUBLIC);
		
		ArgumentCaptor<Query> argQuery = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> argUpdate = ArgumentCaptor.forClass(Update.class);
		verify(mongo(), times(1)).updateFirst(argQuery.capture(), argUpdate.capture(), eq(Collection.class));
		assertEquals("{ \"owner.slug\" : \"bob\"}", queryObject(argQuery).toString());
		assertEquals("{ \"$set\" : { \"visibility\" : \"public\"}}", updateObject(argUpdate).toString());
	}
	
	@Test
	public void shouldSaveCollections() {
		Collection c = new Collection();
		repo.save(c);
		verify(mongo(), times(1)).save(eq(c));
	}
	
	@Test
	public void shouldRemoveCollections() {
		Collection c = new Collection();
		repo.remove(c);
		verify(mongo(), times(1)).remove(eq(c));
	}	
}
