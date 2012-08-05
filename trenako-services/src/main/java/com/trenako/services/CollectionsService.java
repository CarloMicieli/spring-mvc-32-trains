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

import org.bson.types.ObjectId;

import com.trenako.entities.Account;
import com.trenako.entities.Collection;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.RollingStock;
import com.trenako.values.Visibility;

/**
 * The interface for the user collections service.
 * <p>
 * The concrete classes that implements the {@code CollectionsService} interface will provide
 * the following functionalities:
 * <ul>
 * <li>finds a {@code Collection} by id;</li>
 * <li>finds a {@code Collection} list by owner;</li>
 * <li>checks whether the {@code Collection} contains a {@link RollingStock};</li>
 * <li>adds new {@link RollingStock} to a {@code Collection};</li>
 * <li>saves/removes a {@code Collection}.</li>
 * </ul>
 * </p>
 *
 * @author Carlo Micieli
 * @see com.trenako.entities.Collection
 * @see com.trenako.entities.CollectionItem
 */
public interface CollectionsService
{
	/**
	 * Finds the {@code Collection} with the provided id.
	 * @param id the collection id
	 * @return a {@code Collection} if found; {@code null} otherwise
	 */ 
	Collection findById(ObjectId id);
	
	/**
	 * Finds the {@code Collection} with the provided slug.
	 * @param slug the collection's slug
	 * @return a {@code Collection} if found; {@code null} otherwise
	 */
	Collection findBySlug(String slug);
	
	/**
	 * Finds the {@code Collection} with the provided owner.
	 * @param owner the collection's owner
	 * @return a {@code Collection} if found; {@code null} otherwise
	 */
	Collection findByOwner(Account owner);
	
	/**
	 * Checks whether the {@code Collection} for this owner's name contains the provided rolling stock.
	 * <p>
	 * It is valid (and quite common for passenger and freight cars) to have the same {@code RollingStock}
	 * more than once in the same {@code Collection}.
	 * </p>
	 * <p>
	 * In the case the {@code Collection} contains the same {@code RollingStock} more than once
	 * this method will return {@code true}.
	 * </p>
	 * 
	 * @param ownerName the owner's collection name
	 * @param rollingStock the rolling stock to be checked
	 * @return {@code true} if one instance is found in the collection; {@code false} otherwise
	 */
	boolean containsRollingStock(Account owner, RollingStock rollingStock);
	
	/**
	 * Adds a new item to the {@code Collection}.
	 * 
	 * @param owner the collection owner
	 * @param item the {@code CollectionItem} to be added
	 */
	void addRollingStock(Account owner, CollectionItem item);
	
	/**
	 * Removes an item from the {@code Collection}.
	 * 
	 * @param owner the collection owner
	 * @param item the {@code CollectionItem} to be removed
	 */
	void removeRollingStock(Account owner, CollectionItem item);
	
	/**
	 * Changes the {@code Collection} visibility (either {@code public} or {@code private}).
	 * 
	 * @param owner the collection owner
	 * @param visibility the new visibility
	 */
	void changeVisibility(Account owner, Visibility visibility);
	
	/**
	 * Creates a new empty and public {@code Collection} for the provided owner.
	 * @param owner the {@code Collection} owner
	 */
	void createNew(Account owner);
	
	/**
	 * Deletes a {@code Collection}.
	 * @param collection the {@code Collection} to be removed
	 */
	void remove(Collection collection);
}
