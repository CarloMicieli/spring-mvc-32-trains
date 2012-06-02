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
package com.trenako.services;

import static org.mockito.Mockito.*;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

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
public class CollectionsServiceTests {

	@Mock CollectionsRepository repo;
	CollectionsService service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new CollectionsServiceImpl(repo);
	}

	@Test
	public void shouldFindCollectionsById() {
		ObjectId id = new ObjectId();
		
		service.findById(id);
		verify(repo, times(1)).findById(eq(id));
	}
	
	@Test
	public void shouldFindCollectionsByOwnerName() {
		String owner = "user-name";
		
		service.findByOwnerName(owner);
		verify(repo, times(1)).findByOwnerName(eq(owner));
	}
	
	@Test
	public void shouldFindCollectionsByOwner() {
		Account owner = new Account.Builder("mail@mail.com")
			.displayName("user")
			.build();
		
		service.findByOwner(owner);
		verify(repo, times(1)).findByOwnerName(eq(owner.getSlug()));
	}

	@Test
	public void shouldCheckIfCollectionContainsRollingStock() {
		ObjectId collectionId = new ObjectId();
		RollingStock rollingStock = new RollingStock.Builder("ACME", "123456").build();
		
		service.containsRollingStock(collectionId, rollingStock);
		verify(repo, times(1)).containsRollingStock(eq(collectionId), eq(rollingStock));
	}
	
	@Test
	public void shouldCheckIfUserCollectionContainsRollingStock() {
		String owner = "user-name";
		RollingStock rollingStock = new RollingStock.Builder("ACME", "123456").build();
		
		service.containsRollingStock(owner, rollingStock);
		verify(repo, times(1)).containsRollingStock(eq(owner), eq(rollingStock));
	}
	
	@Test
	public void shouldAddItemsToCollection() {
		ObjectId collectionId = new ObjectId();
		CollectionItem item = null;
		
		service.addItem(collectionId, item);
		verify(repo, times(1)).addItem(eq(collectionId), eq(item));
	}
	
	@Test
	public void shouldAddItemsToUserCollection() {
		String owner = "user-name";
		CollectionItem item = null;
		
		service.addItem(owner, item);
		verify(repo, times(1)).addItem(eq(owner), eq(item));
	}
	
	@Test
	public void shouldSaveCollections() {
		Collection coll = null;
		service.save(coll);
		verify(repo, times(1)).save(eq(coll));
	}
	
	@Test
	public void shouldRemoveCollections() {
		Collection coll = null;
		service.remove(coll);
		verify(repo, times(1)).remove(eq(coll));
	}
}
