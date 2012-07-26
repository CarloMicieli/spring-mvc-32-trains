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

import com.trenako.entities.Option;
import com.trenako.repositories.OptionsRepository;
import com.trenako.values.OptionFamily;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class OptionsServiceTests {

	@Mock OptionsRepository repo;
	OptionsService service;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new OptionsServiceImpl(repo);
	}
	
	@Test
	public void shouldFindBrandsById() {
		ObjectId id = new ObjectId();
		service.findById(id);
		verify(repo, times(1)).findOne(eq(id));
	}

	@Test
	public void shouldFindByName() {
		String name = "name";
		service.findByName(name);
		verify(repo, times(1)).findByName(eq(name));
	}
	
	@Test
	public void shouldFindByFamily() {
		service.findByFamily(OptionFamily.DCC_INTERFACE);
		verify(repo, times(1)).findByFamily(eq(OptionFamily.DCC_INTERFACE));
	}

	@Test
	public void shouldSaveOptions() {
		Option op = new Option("aaaa", OptionFamily.DCC_INTERFACE);
		service.save(op);
		verify(repo, times(1)).save(eq(op));
	}

	@Test
	public void shouldRemoveOptions() {
		Option op = new Option("aaaa", OptionFamily.DCC_INTERFACE);
		service.remove(op);
		verify(repo, times(1)).delete(eq(op));
	}
}
