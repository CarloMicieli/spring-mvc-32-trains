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
import org.junit.Test;

import com.trenako.entities.Account;
import com.trenako.entities.RollingStock;
import com.trenako.entities.WishList;
import com.trenako.values.Visibility;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class WishListPermissionTests {

	Permission permission = new WishListPermission();

	@Test(expected = IllegalArgumentException.class)
	@SuppressWarnings("unused")
	public void shouldThrowExceptionsIfTheEvaluatedObjectsAreNotWishLists() {
		boolean results = permission.evaluate(bob(), rollingStock(), "write");
	}
	
	@Test
	public void userShouldHaveAllPermissionsOnHisWishLists() {
		WishList list = bobWishList(Visibility.PRIVATE);
		
		boolean canRead = permission.evaluate(bob(), list, Permission.READ);
		assertTrue("User can't read his wish list", canRead);

		boolean canWrite = permission.evaluate(bob(), list, Permission.WRITE);
		assertTrue("User can't write his wish list", canWrite);
		
		boolean canDelete = permission.evaluate(bob(), list, Permission.DELETE);
		assertTrue("User can't delete his wish list", canDelete);
	}

	@Test
	public void userShouldReadOtherUsersWishListsOnlyWhenPublic() {
		boolean readPublic = permission.evaluate(eve(), bobWishList(Visibility.PUBLIC), Permission.READ);
		assertTrue("User can't read public wish lists", readPublic);
		
		boolean readPrivate = permission.evaluate(eve(), bobWishList(Visibility.PRIVATE), Permission.READ);
		assertFalse("User can read private wish lists", readPrivate);
	}
	
	@Test
	public void userShouldNotModifyOtherUserWishLists() {
		WishList list = bobWishList(Visibility.PUBLIC);

		boolean canWrite = permission.evaluate(eve(), list, Permission.WRITE);
		assertFalse("User can write other user's wish list", canWrite);
		
		boolean canDelete = permission.evaluate(eve(), list, Permission.DELETE);
		assertFalse("User can delete other user's wish list", canDelete);
	}
	
	RollingStock rollingStock() {
		return new RollingStock();
	}
	
	WishList bobWishList(Visibility visibility) {
		return new WishList(bob(), "my list", visibility);
	}
	
	Account bob() {
		return new Account.Builder("mail@mail.com")
			.displayName("Bob")
			.build();
	}
	
	Account eve() {
		return new Account.Builder("mail@mail.com")
			.displayName("Eve")
			.build();
	}
}

