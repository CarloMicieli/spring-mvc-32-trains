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
package com.trenako.web.controllers.admin;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Option;
import com.trenako.services.OptionsService;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.OptionFamily;
import com.trenako.web.controllers.form.OptionForm;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AdminOptionsControllerTests {

	private static List<Option> options(OptionFamily of) {
		return Arrays.asList(
				new Option("op1", of), 
				new Option("op2", of));
	}

	private final static List<Option> COUPLERS = options(OptionFamily.COUPLER);
	private final static List<Option> DCC_INTERFACES = options(OptionFamily.DCC_INTERFACE);
	private final static List<Option> HEADLIGHTS = options(OptionFamily.HEADLIGHTS);
	private final static List<Option> TRANSMISSIONS = options(OptionFamily.TRANSMISSION);
	
	@Mock RedirectAttributes redirectAtts;
	@Mock BindingResult bindingResult;
	@Mock OptionsService service;
	private AdminOptionsController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new AdminOptionsController(service);
		
		when(service.findByFamily(eq(OptionFamily.COUPLER))).thenReturn(COUPLERS);
		when(service.findByFamily(eq(OptionFamily.DCC_INTERFACE))).thenReturn(DCC_INTERFACES);
		when(service.findByFamily(eq(OptionFamily.HEADLIGHTS))).thenReturn(HEADLIGHTS);
		when(service.findByFamily(eq(OptionFamily.TRANSMISSION))).thenReturn(TRANSMISSIONS);
	}
	
	@Test
	public void shouldReturnTheOptionFamiliesList() {
		Iterable<LocalizedEnum<OptionFamily>> families = controller.families();
		assertEquals("[(dcc-interface), (headlights), (transmission), (coupler)]", families.toString());
	}
	
	@Test
	public void shouldRenderNewOptionForms() {
		ModelMap model = new ModelMap();

		String viewName = controller.newOption(model);
		
		assertEquals("option/new", viewName);
		assertTrue(model.containsAttribute("newForm"));
	}
	
	@Test
	public void shouldShowTheOptionsList() {
		ModelMap model = new ModelMap();
		
		String viewName = controller.list(model);
		
		assertEquals("option/list", viewName);
		assertTrue("Coupler options not found", model.containsAttribute("couplerOptions"));
		assertTrue("DCC options not found", model.containsAttribute("dccOptions"));
		assertTrue("Headlight options not found", model.containsAttribute("headlightsOptions"));
		assertTrue("Transmission options not found", model.containsAttribute("transmissionOptions"));
	}
	
	@Test
	public void shouldCreateNewOptions() throws IOException {
		ModelMap model = new ModelMap();
		Option option = postedForm().buildOption();
		
		when(bindingResult.hasErrors()).thenReturn(false);
		
		String viewName = controller.create(postedForm(), bindingResult, model, redirectAtts);
		
		assertEquals("redirect:/admin/options", viewName);
		verify(service, times(1)).save(eq(option));
	}
	
	@Test
	public void shouldRedirectAfterValidationErrorsCreatingNewOptions(){
		ModelMap model = new ModelMap();
		when(bindingResult.hasErrors()).thenReturn(true);
		
		String viewName = controller.create(postedForm(), bindingResult, model, redirectAtts);
		
		assertEquals("option/new", viewName);
		assertTrue(model.containsAttribute("newForm"));
		verify(service, times(0)).save(isA(Option.class));
	}
	
	@Test
	public void shouldRedirectAfterIOExceptionCreatingNewOptions() throws IOException {
		ModelMap model = new ModelMap();
		when(bindingResult.hasErrors()).thenReturn(false);
		OptionForm mockForm = mock(OptionForm.class);
		doThrow(new IOException()).when(mockForm).buildOption();
		
		String viewName = controller.create(mockForm, bindingResult, model, redirectAtts);
		
		assertEquals("option/new", viewName);
		assertTrue(model.containsAttribute("newForm"));
		verify(service, times(0)).save(isA(Option.class));
	}
	
	OptionForm postedForm() {
		OptionForm form = new OptionForm();
		
		form.setOption(new Option("name", "dcc-interface", null));
		form.setFile(new MockMultipartFile("file.png", "content".getBytes()));
		
		return form;
	}
}
