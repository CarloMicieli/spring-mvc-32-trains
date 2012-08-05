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
	
	private ObjectId id = new ObjectId();
	private RollingStock rollingStock = new RollingStock.Builder(acme(), "123456")
		.railway(fs())
		.scale(scaleH0())
		.build();
	private Account owner = new Account.Builder("mail@mail.com")
		.displayName("user")
		.build();
	
	@Mock CollectionsRepository repo;
	CollectionsService service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		// mocking repository
		Collection value = new Collection(owner);
		when(repo.findById(eq(id))).thenReturn(value);
		when(repo.findBySlug(eq("slug"))).thenReturn(value);
		when(repo.findByOwner(eq(owner))).thenReturn(value);
		when(repo.containsRollingStock(eq(owner), eq(rollingStock))).thenReturn(true);
		
		service = new CollectionsServiceImpl(repo);
	}

	@Test
	public void shouldFindCollectionsById() {
		Collection coll = service.findById(id);
		
		assertNotNull(coll);
		verify(repo, times(1)).findById(eq(id));
	}
	
	@Test
	public void shouldFindCollectionsBySlug() {
		String slug = "slug";
		Collection coll = service.findBySlug(slug);
		
		assertNotNull(coll);
		verify(repo, times(1)).findBySlug(eq(slug));
	}
	
	@Test
	public void shouldFindCollectionsByOwner() {
		Collection coll = service.findByOwner(owner);

		assertNotNull(coll);
		verify(repo, times(1)).findByOwner(eq(owner));
	}
	
	@Test
	public void shouldCheckIfUserCollectionContainsRollingStock() {
		boolean ret = service.containsRollingStock(owner, rollingStock);
		
		assertTrue(ret);
		verify(repo, times(1)).containsRollingStock(eq(owner), eq(rollingStock));
	}
	
	@Test
	public void shouldAddItemsToUserCollection() {
		CollectionItem item = null;
		
		service.addRollingStock(owner, item);
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
