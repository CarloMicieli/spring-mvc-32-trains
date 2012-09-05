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

import com.trenako.entities.Account;
import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;
import com.trenako.mapping.LocalizedField;
import com.trenako.mapping.WeakDbRef;
import com.trenako.security.AccountDetails;
import com.trenako.services.FormValuesService;
import com.trenako.values.Category;
import com.trenako.values.DeliveryDate;
import com.trenako.values.Era;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.PowerMethod;
import com.trenako.web.security.UserContext;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RollingStockFormTests {
	
	@Mock UserContext userContext;
	@Mock FormValuesService mockService;
	
	static final List<Brand> BRANDS = Arrays.asList(acme(), marklin(), roco());
	static final List<Railway> RAILWAYS = Arrays.asList(db(), fs());
	static final List<Scale> SCALES = Arrays.asList(scaleH0(), scaleN());

	static final List<LocalizedEnum<Era>> ERAS = (List<LocalizedEnum<Era>>) LocalizedEnum.list(Era.class); 
	static final List<LocalizedEnum<PowerMethod>> POWERMETHODS = (List<LocalizedEnum<PowerMethod>>) LocalizedEnum.list(PowerMethod.class);
	static final List<LocalizedEnum<Category>> CATEGORIES = (List<LocalizedEnum<Category>>) LocalizedEnum.list(Category.class);
	static final List<DeliveryDate> DELIVERY_DATES = Arrays.asList(new DeliveryDate(2012, 1), new DeliveryDate(2012, 2));
	

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldCreateRollingStockForms() {
		RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.scale(scaleH0())
			.railway(fs())
			.description("Description")
			.tags("one", "two")
			.build();
	
		RollingStockForm form = RollingStockForm.newForm(rs, mockService);
		assertEquals(rs, form.getRs());
		assertEquals("one,two", form.getTags());
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
		
		RollingStockForm form = RollingStockForm.newForm(postedRs(), mockService);
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
	public void shouldBuildNewRollingStockUsingFormValues() {
		when(mockService.getBrand(eq(acme().getSlug()))).thenReturn(acme());
		when(mockService.getRailway(eq(fs().getSlug()))).thenReturn(fs());
		when(mockService.getScale(eq(scaleH0().getSlug()))).thenReturn(scaleH0());
		
		when(userContext.getCurrentUser()).thenReturn(new AccountDetails(loggedUser()));
		
		RollingStockForm form = postedForm();
		
		RollingStock newRs = form.buildRollingStock(mockService, userContext, date("2012/09/01"));
		
		assertNotNull("New rolling stock is null", newRs);
		assertEquals("{slug: acme, label: ACME}", newRs.getBrand().toCompleteString());
		assertEquals("{slug: fs, label: FS (Ferrovie dello stato)}", newRs.getRailway().toCompleteString());
		assertEquals("{slug: h0, label: H0 (1:87)}", newRs.getScale().toCompleteString());
		assertEquals("[one, two]", newRs.getTags().toString());
		assertEquals(date("2012/09/01"), newRs.getLastModified());
		assertEquals("bob", newRs.getModifiedBy());
		assertEquals("it", newRs.getCountry());
	}

	private RollingStockForm postedForm() {
		RollingStockForm form = new RollingStockForm();
		form.setRs(postedRs());
		form.setTags("one, two");
		return form;
	}
	
	@Test
	public void shouldExtractTagsSet() {
		RollingStockForm x = new RollingStockForm();
		x.setTags(null);
		assertNull("Tags set not null", x.getTagsSet());
		
		RollingStockForm y = new RollingStockForm();
		y.setTags("one , two two, three");
		assertEquals("[one, three, two-two]", y.getTagsSet().toString());
	}
	
	RollingStock postedRs() {
		RollingStock rs = new RollingStock();
		rs.setBrand(WeakDbRef.buildFromSlug("acme", Brand.class));
		rs.setScale(WeakDbRef.buildFromSlug("h0", Scale.class));
		rs.setRailway(WeakDbRef.buildFromSlug("fs", Railway.class));
		rs.setItemNumber("123456");
		rs.setDescription(new LocalizedField<String>("Description"));
		rs.setDetails(new LocalizedField<String>("Details"));
		rs.setEra("iii");
		rs.setCategory("electric-locomotives");
		return rs;
	}
	
	Account loggedUser() {
		return new Account.Builder("mail@mail.com")
			.displayName("Bob")
			.build();
	}
}
