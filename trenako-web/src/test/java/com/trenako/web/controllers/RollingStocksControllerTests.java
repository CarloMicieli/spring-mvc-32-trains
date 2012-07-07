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
package com.trenako.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;
import com.trenako.services.RollingStocksService;
import com.trenako.services.SelectOptionsService;

@RunWith(MockitoJUnitRunner.class)
public class RollingStocksControllerTests {
	
	@Mock RollingStocksService service;
	@Mock SelectOptionsService soService;
	RollingStocksController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new RollingStocksController(service, soService);
	}
	
	@Test
	public void shouldShowRollingStocks() {
		String slug = "rs-slug";
		RollingStock value = new RollingStock();
		when(service.findBySlug(eq(slug))).thenReturn(value);
		
		ModelMap model = new ExtendedModelMap();
		
		String viewName = controller.show(slug, model);
		
		assertEquals("rollingstock/show", viewName);
		assertTrue(model.containsAttribute("rollingStock"));
		assertEquals(value, model.get("rollingStock"));
	}
	
	@Test
	public void shouldRenderNewRollingStockForms() {		
	
		List<Brand> brandValue = Arrays.asList(new Brand(), new Brand());
		List<Railway> railwayValue = Arrays.asList(new Railway(), new Railway());
		List<Scale> scaleValue = Arrays.asList(new Scale(), new Scale());

		when(soService.brands()).thenReturn(brandValue);
		when(soService.railways()).thenReturn(railwayValue);
		when(soService.scales()).thenReturn(scaleValue);
		
		List<String> value = Arrays.asList("aaa", "bbb");
		when(soService.categories()).thenReturn(value);
		when(soService.eras()).thenReturn(value);
		when(soService.powerMethods()).thenReturn(value);
		
		ModelAndView mav = controller.createNew();
		
		assertViewName(mav, "rollingstock/new");
		assertAndReturnModelAttributeOfType(mav, "rollingStock", RollingStock.class);
		assertCompareListModelAttribute(mav, "brands", brandValue);
		assertCompareListModelAttribute(mav, "railways", railwayValue);
		assertCompareListModelAttribute(mav, "scales", scaleValue);
		assertModelAttributeAvailable(mav, "categories");
		assertModelAttributeAvailable(mav, "eras");
		assertModelAttributeAvailable(mav, "powerMethods");
	}
}
