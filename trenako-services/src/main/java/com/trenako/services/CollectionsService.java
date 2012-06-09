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
     * Finds the {@link Collection} with the provided id.
     * @param id the collection id
     * @return a {@code Collection} if found; {@code null} otherwise
     */ 
    Collection findById(ObjectId id);

    /**
     * Finds the {@link Collection} with the provided owner.
     * @param owner the collection's owner
     * @return a {@code Collection} if found; {@code null} otherwise
     */
    Collection findByOwner(Account owner);
    
    /**
     * Finds the {@link Collection} with the provided owner name.
     * @param ownerName the collection's owner name
     * @return a {@code Collection} if found; {@code null} otherwise
     */
    Collection findByOwnerName(String ownerName);

    /**
     * Checks whether the {@link Collection} contains the provided {@link RollingStock}.
     * <p>
     * It is valid (and quite common for passenger and freight cars) to have the same {@code RollingStock}
     * more than once in the same {@code Collection}.
     * </p>
     * <p>
     * In the case the {@code Collection} contains the same {@code RollingStock} more than once
     * this method will return {@code true}.
     * </p>
     * 
     * @param collectionId the collection id
     * @param rollingStock the {@code RollingStock} to be checked
     * @return {@code true} if one instance is found in the collection; {@code false} otherwise
     */
    boolean containsRollingStock(ObjectId collectionId, RollingStock rollingStock);

    /**
     * Checks whether the collection for this owner's name contains the provided rolling stock.
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
    boolean containsRollingStock(String ownerName, RollingStock rollingStock);

    /**
     * Adds a new {@link CollectionItem} to the user's collection.
     * 
     * @param collectionId the user's collection
     * @param item the {@code CollectionItem} to be added
     */
    void addItem(ObjectId collectionId, CollectionItem item);

    /**
     * Adds a new {@link CollectionItem} to the user's collection.
     * 
     * <p>
     * Every user can have only one collection, so it is possible to add new items 
     * using just the {@code Account} information.
     * </p>
     * 
     * @param ownerName the collection's owner name
     * @param item the {@code CollectionItem} to be added
     */
    void addItem(String ownerName, CollectionItem item);

	/**
	 * Persists the {@link Collection} changes in the data store.
	 * <p>
	 * This method performs a "upsert": if the {@code Collection} is not present in the data store
	 * a new {@code Collection} is created; otherwise the method will update the existing {@code Collection}. 
	 * </p>
	 * @param collection the {@code Collection} to be saved
	 */ 
    void save(Collection collection);

	/**
	 * Removes a {@link Collection} from the data store.
	 * @param collection the {@code Collection} to be removed
	 */
    void remove(Collection collection);
}
