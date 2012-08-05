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
package com.trenako.repositories;

import org.bson.types.ObjectId;

import com.trenako.entities.Account;
import com.trenako.entities.Collection;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.RollingStock;
import com.trenako.values.Visibility;

/**
 * The interface for the {@code Collection} repository.
 * @author Carlo Micieli
 *
 */
public interface CollectionsRepository {
    /**
     * Finds the {@code Collection} with the provided id.
     * @param id the collection id
     * @return a {@code Collection} if found; {@code null} otherwise
     */ 
    Collection findById(ObjectId id);

    /**
     * Finds the {@code Collection} with the provided slug.
     * @param slug the slug
     * @return a {@code Collection} if found; {@code null} otherwise
     */ 
    Collection findBySlug(String slug);
    
    /**
     * Finds the {@code Collection} with the provided owner name
     * @param owner the collection's owner name
     * @return a {@code Collection} if found; {@code null} otherwise
     */
    Collection findByOwner(Account owner);

    /**
     * Returns whether the {@code Collection} for this owner's name contains the provided rolling stock.
     * 
     * <p>
     * Although the service provide this method it is allowed to have the
     * same rolling stock item more than once.
     * </p>
     * <p>
     * In the case the collection contains the same rolling stock more than once
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
     * <p>
     * Every user can have only one collection, so it is possible
     * to add new items using the account information only.
     * </p>
     * 
     * @param owner the collection's owner
     * @param item the new item to be added
     */
    void addItem(Account owner, CollectionItem item);

    /**
     * Removes an item from the {@code Collection}.
     * 
     * <p>
     * Every user can have only one collection, so it is possible
     * to remove items using the account information only.
     * </p>
     * 
     * @param owner the collection's owner
     * @param item the new item to be added
     */
    void removeItem(Account owner, CollectionItem item);
    
	/**
	 * Changes the {@code Collection} visibility (either {@code public} or {@code private}).
	 *
	 * @param owner the collection's owner
     * @param visibility the new visibility
	 */
	void changeVisibility(Account owner, Visibility visibility);
    
    /**
     * Saves the {@code Collection}.
     * @param collection the collection to be saved
     */ 
    void save(Collection collection);

    /**
     * Removes the {@code Collection}.
     * @param collection the collection to be removed
     */ 
    void remove(Collection collection);
}
