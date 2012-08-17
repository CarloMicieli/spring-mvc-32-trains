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
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;
import com.trenako.services.FormValuesService;
import com.trenako.values.Category;
import com.trenako.values.DeliveryDate;
import com.trenako.values.Era;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.PowerMethod;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RollingStockFormTests {
	@Mock FormValuesService mockService;
	
	static final List<Brand> BRANDS = Arrays.asList(acme(), marklin(), roco());
	static final List<Railway> RAILWAYS = Arrays.asList(db(), fs());
	static final List<Scale> SCALES = Arrays.asList(scaleH0(), scaleN());

	static final List<LocalizedEnum<Era>> ERAS = (List<LocalizedEnum<Era>>) LocalizedEnum.list(Era.class); 
	static final List<LocalizedEnum<PowerMethod>> POWERMETHODS = (List<LocalizedEnum<PowerMethod>>) LocalizedEnum.list(PowerMethod.class);
	static final List<LocalizedEnum<Category>> CATEGORIES = (List<LocalizedEnum<Category>>) LocalizedEnum.list(Category.class);
	static final List<DeliveryDate> DELIVERY_DATES = Arrays.asList(new DeliveryDate(2012, 1), new DeliveryDate(2012, 2));
	
	private final static RollingStock RS = new RollingStock.Builder(acme(), "123456")
		.scale(scaleH0())
		.railway(fs())
		.description("Description")
		.build();
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldCreateRollingStockForms() {
		RollingStockForm form = RollingStockForm.newForm(RS, mockService);
		assertEquals(RS, form.getRs());
	}
	
	@Test
	public void shouldFillDropDownListsForForms() {
		when(mockService.brands()).thenReturn(BRANDS);
		when(mockService.railways()).thenReturn(RAILWAYS);
		when(mockService.scales()).thenReturn(SCALES);
		when(mockService.categories()).thenReturn(CATEGORIES);
		when(mockService.eras()).thenReturn(ERAS);
		when(mockService.powerMethods()).thenReturn(POWERMETHODS);
		when(mockService.deliveryDates()).thenReturn(DELIVERY_DATES);
		
		RollingStockForm form = RollingStockForm.newForm(RS, mockService);
		Iterable<Brand> brands = form.getBrandsList();
		Iterable<Scale> scales = form.getScalesList();
		Iterable<Railway> railways = form.getRailwaysList();
		Iterable<LocalizedEnum<Era>> eras = form.getErasList();
		Iterable<LocalizedEnum<PowerMethod>> powerMethods = form.getPowerMethodsList();
		Iterable<DeliveryDate> deliveryDates = form.getDeliveryDates();
		
		assertNotNull("Brands list is empty", brands);
		assertNotNull("Scales list is empty", scales);
		assertNotNull("Railways list is empty", railways);
		assertNotNull("Eras list is empty", eras);
		assertNotNull("Power methods list is empty", powerMethods);
		assertNotNull("Delivery dates list is empty", deliveryDates);
	}
	
	@Test
	public void shouldLoadRollingStockReferences() {
		when(mockService.getBrand(eq(acme().getSlug()))).thenReturn(acme());
		when(mockService.getRailway(eq(fs().getSlug()))).thenReturn(fs());
		when(mockService.getScale(eq(scaleH0().getSlug()))).thenReturn(scaleH0());
		
		RollingStockForm form = new RollingStockForm(RS);
		RollingStock rs = form.getRsLoadingRefs(mockService);
		
		assertEquals("{slug: acme, label: ACME}", rs.getBrand().toCompleteString());
		assertEquals("{slug: fs, label: FS (Ferrovie dello stato)}", rs.getRailway().toCompleteString());
		assertEquals("{slug: h0, label: H0 (1:87)}", rs.getScale().toCompleteString());
	}
}
