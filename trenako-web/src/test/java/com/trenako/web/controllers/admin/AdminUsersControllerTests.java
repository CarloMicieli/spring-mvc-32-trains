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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Account;
import com.trenako.services.AccountsService;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AdminUsersControllerTests {
	
	private final static Page<Account> USERS = new PageImpl<Account>(Arrays.asList(new Account(), new Account()));
	private @Mock AccountsService service;
	private @Mock RedirectAttributes redirectAtts;
	private @Mock BindingResult bindingResult;

	private AdminUsersController controller;
	private ModelMap model = new ModelMap();
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new AdminUsersController(service);
	}
	
	@Test
	public void shouldShowTheUsersList() {
		Pageable pageable = new PageRequest(0, 10);
		when(service.findAll(eq(pageable))).thenReturn(USERS);

		String viewName = controller.usersList(pageable, model);

		assertEquals("user/list", viewName);
		assertEquals(USERS, model.get("users"));
		verify(service, times(1)).findAll(eq(pageable));
	}

	@Test
	public void shouldRenderTheUserEditingForms() {
		Account user = new Account();
		String slug = "bob";
		when(service.findBySlug(eq(slug))).thenReturn(user);

		String viewName = controller.editForm(slug, model, redirectAtts);

		assertEquals("user/edit", viewName);
		assertEquals(user, model.get("account"));
		verify(service, times(1)).findBySlug(eq(slug));
	}

	@Test
	public void shouldRedirectToUsersListWhenUserToEditWasNotFound() {
		String slug = "bob";
		when(service.findBySlug(eq(slug))).thenReturn(null);

		String viewName = controller.editForm(slug, model, redirectAtts);

		assertEquals("redirect:/admin/users", viewName);
		verify(service, times(1)).findBySlug(eq(slug));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminUsersController.USER_NOT_FOUND_MSG));
	}

	@Test
	public void shouldSaveUsers() {
		Account user = new Account();
		when(bindingResult.hasErrors()).thenReturn(false);

		String viewName = controller.saveUser(user, bindingResult, model, redirectAtts);

		assertEquals("redirect:/admin/users", viewName);
		verify(service, times(1)).updateChanges(eq(user));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminUsersController.USER_SAVED_MSG));
	}

	@Test
	public void shouldRedirectAfterValidationErrorsSavingUsers() {
		Account user = new Account();
		when(bindingResult.hasErrors()).thenReturn(true);

		String viewName = controller.saveUser(user, bindingResult, model, redirectAtts);

		assertEquals("user/edit", viewName);
		assertEquals(user, model.get("account"));
		verify(service, times(0)).updateChanges(eq(user));
	}

	@Test
	public void shouldRedirectAfterDatabaseErrorsSavingUsers() {
		Account user = new Account();
		when(bindingResult.hasErrors()).thenReturn(false);
		doThrow(new FakeDataAccessException())
			.when(service)
			.updateChanges(eq(user));

		String viewName = controller.saveUser(user, bindingResult, model, redirectAtts);

		assertEquals("user/edit", viewName);
		assertEquals(user, model.get("account"));
		verify(service, times(1)).updateChanges(eq(user));
	}
	
	@SuppressWarnings("serial")
	private static class FakeDataAccessException extends DataAccessException {
		FakeDataAccessException() {
			super("error");
		}
	}
}
