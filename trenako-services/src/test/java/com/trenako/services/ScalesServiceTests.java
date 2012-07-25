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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;

import com.trenako.entities.Scale;
import com.trenako.repositories.ScalesRepository;
import com.trenako.services.ScalesServiceImpl;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ScalesServiceTests {

	@Mock Pageable paging;
	@Mock ScalesRepository repo;
	@InjectMocks ScalesServiceImpl service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldFindScalesById() {
		ObjectId id = new ObjectId();
		service.findById(id);
		verify(repo, times(1)).findOne(eq(id));
	}

	@Test
	public void shouldFindScalesByName() {
		String name = "H0";
		service.findByName(name);
		verify(repo, times(1)).findByName(eq(name));
	}

	@Test
	public void shouldFindScalesBySlug() {
		String slug = "h0";
		service.findBySlug(slug);
		verify(repo, times(1)).findBySlug(eq(slug));
	}
	
	@Test
	public void shouldFindAllScales() {
		service.findAll(paging);
		verify(repo, times(1)).findAll(eq(paging));
	}

	@Test
	public void shouldSaveScales() {
		Scale scale = new Scale("H0");
		service.save(scale);
		verify(repo, times(1)).save(eq(scale));
	}

	@Test
	public void shouldRemoveScales() {
		Scale scale = new Scale("H0");
		service.remove(scale);
		verify(repo, times(1)).delete(eq(scale));
	}

}
