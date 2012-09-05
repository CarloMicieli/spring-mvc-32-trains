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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.trenako.entities.Account;
import com.trenako.entities.Collection;
import com.trenako.entities.RollingStock;
import com.trenako.values.Visibility;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CollectionPermissionTests {
	Permission permission = new CollectionPermission();

	@Test(expected = IllegalArgumentException.class)
	@SuppressWarnings("unused")
	public void shouldThrowExceptionsIfTheEvaluatedObjectsAreNotCollections() {
		boolean results = permission.evaluate(bob(), rollingStock(), "write");
	}
	
	@Test
	public void userShouldHaveAllPermissionsOnHisCollections() {
		Collection list = bobCollection(Visibility.PRIVATE);
		
		boolean canRead = permission.evaluate(bob(), list, Permission.READ);
		assertTrue("User can't read his collection", canRead);

		boolean canWrite = permission.evaluate(bob(), list, Permission.WRITE);
		assertTrue("User can't write his collection", canWrite);
		
		boolean canDelete = permission.evaluate(bob(), list, Permission.DELETE);
		assertTrue("User can't delete his collection", canDelete);
	}

	@Test
	public void userShouldReadOtherUsersCollectionsOnlyWhenPublic() {
		boolean readPublic = permission.evaluate(eve(), bobCollection(Visibility.PUBLIC), Permission.READ);
		assertTrue("User can't read public collection", readPublic);
		
		boolean readPrivate = permission.evaluate(eve(), bobCollection(Visibility.PRIVATE), Permission.READ);
		assertFalse("User can read private collection", readPrivate);
	}
	
	@Test
	public void userShouldNotModifyOtherUserCollections() {
		Collection list = bobCollection(Visibility.PUBLIC);

		boolean canWrite = permission.evaluate(eve(), list, Permission.WRITE);
		assertFalse("User can write other user's collection", canWrite);
		
		boolean canDelete = permission.evaluate(eve(), list, Permission.DELETE);
		assertFalse("User can delete other user's collection", canDelete);
	}
	
	RollingStock rollingStock() {
		return new RollingStock();
	}
	
	Collection bobCollection(Visibility visibility) {
		return new Collection(bob(), visibility);
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
