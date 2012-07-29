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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.trenako.entities.Account;
import com.trenako.entities.Collection;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.RollingStock;
import com.trenako.repositories.CollectionsRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CollectionsRepositoryTests {

	private RollingStock rollingStock = new RollingStock.Builder(acme(), "123456")
		.railway(fs())
		.scale(scaleH0())
		.build();
	
	@Mock MongoTemplate mongo;
	CollectionsRepository repo;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		repo = new CollectionsRepositoryImpl(mongo);
	}

	@Test
	public void shouldfindCollectionById() {
		ObjectId id = new ObjectId();
		Collection value = new Collection(
			new Account.Builder("mail@mail.com").build());
		when(mongo.findById(eq(id), eq(Collection.class))).thenReturn(value);
		
		Collection coll = repo.findById(id);
		
		assertNotNull(coll);
		verify(mongo, times(1)).findById(eq(id), eq(Collection.class));
	}
	
	@Test
	public void shouldfindCollectionByOwnerName() {
		String owner = "user-name";
		Collection value = new Collection(
				new Account.Builder("mail@mail.com").build());
		when(mongo.findOne(isA(Query.class), eq(Collection.class))).thenReturn(value);
		
		Collection coll = repo.findByOwnerName(owner);

		assertNotNull(coll);
		verify(mongo, times(1)).findOne(isA(Query.class), eq(Collection.class));
	}
	
	@Test
	public void shouldCheckIfCollectionIdContainRollingStock() {
		ObjectId id = new ObjectId();
		
		
		when(mongo.count(isA(Query.class), eq(Collection.class))).thenReturn(1L);
		
		boolean ret = repo.containsRollingStock(id, rollingStock);
		
		assertTrue(ret);
		verify(mongo, times(1)).count(isA(Query.class), eq(Collection.class));
	}
	
	@Test
	public void shouldCheckIfCollectionsContainRollingStock() {
		String owner = "user-name";
		
		when(mongo.count(isA(Query.class), eq(Collection.class))).thenReturn(1L);
		
		boolean ret = repo.containsRollingStock(owner, rollingStock);
		
		assertTrue(ret);
		verify(mongo, times(1)).count(isA(Query.class), eq(Collection.class));
	}
	
	@Test
	public void shouldAddNewItemsToCollection() {
		CollectionItem item = null;
		ObjectId collectionId = new ObjectId();
		
		repo.addItem(collectionId, item);
	
		verify(mongo, times(1)).updateFirst(isA(Query.class), isA(Update.class), eq(Collection.class));
	}
	
	@Test
	public void shouldAddNewItemsToUserCollection() {
		CollectionItem item = null;
		
		repo.addItem("user-name", item);
	
		verify(mongo, times(1)).updateFirst(isA(Query.class), isA(Update.class), eq(Collection.class));
	}
	
	@Test
	public void shouldSaveCollections() {
		
		Collection c = null;
		repo.save(c);
		verify(mongo, times(1)).save(eq(c));
	}
	
	@Test
	public void shouldRemoveCollections() {
		
		Collection c = null;
		repo.remove(c);
		verify(mongo, times(1)).remove(eq(c));
	}	
}
