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
package com.trenako.security.permissions;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.springframework.security.core.Authentication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.entities.Account;
import com.trenako.entities.Collection;
import com.trenako.entities.WishList;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TrenakoPermissionEvaluatorTests {

	@Mock Authentication auth;
	@Mock Permission permission;
	@Mock PermissionsHolder holder;
	private TrenakoPermissionEvaluator evaluator;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		// mocking permission: always evaluate to true
		when(auth.getPrincipal()).thenReturn(user());
		when(holder.permission(isNotNull())).thenReturn(permission);

		evaluator = new TrenakoPermissionEvaluator(holder);
	}

	@Test
	public void shouldAlwaysEvaluateToTrueForUnknownObjectTypes() {
		evaluator = new TrenakoPermissionEvaluator();
		boolean result = evaluator.hasPermission(authentication(), "unknown", "read");
		assertTrue(result);
	}
	
	@Test 
	public void shouldCheckPermissionsForCollections() {
		mockPermissionOn(collection(), true);

		boolean result = evaluator.hasPermission(authentication(), collection(), "read");

		assertTrue(result);
		verify(holder, times(1)).permission(eq(collection()));
		verify(permission, times(1)).evaluate(eq(user()), eq(collection()), eq(read()));
	}

	@Test 
	public void shouldCheckPermissionsForWishLists() {
		mockPermissionOn(wishList(), true);

		boolean result = evaluator.hasPermission(authentication(), wishList(), "read");

		assertTrue(result);
		verify(holder, times(1)).permission(eq(wishList()));
		verify(permission, times(1)).evaluate(eq(user()), eq(wishList()), eq(read()));
	}

	Authentication authentication() {
		return auth;
	}

	void mockPermissionOn(Object targetDomainObject, boolean result) {
		when(permission.evaluate(eq(user()), eq(targetDomainObject), eq(read())))
			.thenReturn(result);
	}

	Object read() {
		return "read";
	}

	Account user() {
		return new Account.Builder("mail@mail.com")
			.displayName("Bob")
			.build();
	}

	Collection collection() {
		return new Collection(user());
	}

	WishList wishList() {
		return new WishList(user(), "My list");
	}
}
