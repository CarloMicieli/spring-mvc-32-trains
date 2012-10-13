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
package com.trenako.web.controllers.form;

import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.Test;

import com.trenako.AppGlobals;
import com.trenako.entities.Brand;
import com.trenako.entities.Scale;
import com.trenako.services.FormValuesService;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class BrandFormTests {

	private final static Iterable<Scale> SCALES = Arrays.asList(scaleH0(), scaleN());
	FormValuesService mockService() {
		FormValuesService service = mock(FormValuesService.class);
		when(service.scales()).thenReturn(SCALES);
		return service;
	}
	
	@Test
	public void shouldReturnTheCountriesList() {
		BrandForm form = new BrandForm();
		assertEquals(AppGlobals.countries(), form.getCountriesList());
	}
	
	@Test
	public void shouldBuildFormForNewBrands() {
		BrandForm form = BrandForm.newForm(new Brand(), mockService());
		assertNotNull(form);
		assertEquals(new Brand(), form.getBrand());
	}
	
	@Test
	public void shouldReturnTheScalesList() {
		BrandForm form = BrandForm.newForm(new Brand(), mockService());
		assertEquals("[h0, n]", form.getScalesList().toString());
	}
	
	@Test
	public void shouldCheckWhetherTwoBrandFormsAreEquals() {
		BrandForm x = BrandForm.newForm(acme(), mockService());
		BrandForm y = BrandForm.newForm(acme(), mockService());
		assertTrue(x.equals(x));
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldCheckWhetherTwoBrandFormsAreDifferents() {
		BrandForm x = BrandForm.newForm(acme(), mockService());
		BrandForm y = BrandForm.newForm(roco(), mockService());
		assertFalse(x.equals(y));
	}
}
