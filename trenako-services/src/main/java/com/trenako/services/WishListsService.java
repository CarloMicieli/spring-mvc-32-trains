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

import com.trenako.entities.Account;
import com.trenako.entities.RollingStock;
import com.trenako.entities.WishList;
import com.trenako.entities.WishListItem;
import com.trenako.values.Visibility;

/**
 * The interface for the {@code WishList} service.
 * @author Carlo Micieli
 *
 */
public interface WishListsService {

	/**
	 * Returns the list of {@code WishList} for the provided owner.
	 * <p>
	 * This method is not loading the items for the {@code WishList}; just the 
	 * header information are returned.
	 * </p>
	 *
	 * @param owner the owner
	 * @returns a list of {@code WishList}
	 */
	Iterable<WishList> findByOwner(Account owner);
		
	/**
	 * Returns the {@code WishList} with the provided slug.
	 * @param slug the slug
	 * @returns a {@code WishList} if found; {@code null} otherwise
	 */
	WishList findBySlug(String slug);
	
	/**
	 * Returns the default {@code WishList} for the provided owner.
	 * @param owner the owner
	 * @returns the default {@code WishList} if found; {@code null} otherwise
	 */
	WishList findDefaultListByOwner(Account owner);
	
	/**
	 * Checks whether the provided {@code WishList} contains a given rolling stock.
	 * @param wishList the {@code WishList}
	 * @param rs the rolling stock
	 * @return {@code true} if the list contains the rolling stock; {@code false} otherwise
	 */
	boolean containsRollingStock(WishList wishList, RollingStock rs);
	
	/**
	 * Adds a new item to the provided {@code WishList}.
	 * @param wishList the {@code WishList} to be modified
	 * @param newItem the item to be added
	 */
	void addItem(WishList wishList, WishListItem newItem);

	/**
	 * Updates an item for the provided {@code WishList}.
	 * @param wishList the {@code WishList} to be modified
	 * @param item the item to be saved
	 */
	void updateItem(WishList wishList, WishListItem item);
	
	/**
	 * Removes the new item from the provided {@code WishList}.
	 * @param wishList the {@code WishList} to be modified
	 * @param item the item to be removed
	 */
	void removeItem(WishList wishList, WishListItem item);
	
	/**
	 * Moves the provided item from the {@code source} wish list to the {@code target} one.
	 * @param source the source {@code WishList}
	 * @param target the target {@code WishList}
	 * @param item the item to be moved
	 */
	void moveItem(WishList source, WishList target, WishListItem item);
	
	/**
	 * Changes the {@code WishList} visibility (either {@code public} or {@code private}).
	 * @param wishList the {@code WishList} to be modified
	 * @param visibility the new visibility
	 */
	void changeVisibility(WishList wishList, Visibility visibility);
	
	/**
	 * Sets the provided {@code WishList} as the default list for the user.
	 * @param owner the owner
	 * @param wishList the {@code WishList} to be modified
	 */
	void setAsDefault(Account owner, WishList wishList);
	
	/**
	 * Creates a new public {@code WishList}.
	 * @param owner the owner
	 * @param name the name
	 */ 
	void createNew(Account owner, String name);

	/**
	 * Removes the {@code WishList}.
	 * @param wishList the {@code WishList} to be removed
	 */ 
	void remove(WishList wishList);
}