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
 * @author Carlo Micieli
 */ 
public interface CollectionsService
{
    /**
     * Finds the collection by its unique id.
     * @param id the collection id
     * @return the collection if found; <em>null</em> otherwise
     */ 
    Collection findById(ObjectId id);

    /**
     * Finds the collection by its owner.
     * @param owner the collection's owner
     * @return the collection if found; <em>null</em> otherwise
     */
    Collection findByOwner(Account owner);

    /**
     * Finds the collection by its owner name
     * @param ownerName the collection's owner name
     * @return the collection if found; <em>null</em> otherwise
     */
    Collection findByOwnerName(String ownerName);

    /**
     * Returns whether the collection contains the provided rolling stock.
     * 
     * <p>
     * Although the service provide this method it is allowed to have the
     * same rolling stock item more than once.
     * </p>
     * <p>
     * In the case the collection contains the same rolling stock more than once
     * this method will return <em>true</em>.
     * </p>
     * 
     * @param collectionId the user's collection id
     * @param rollingStock the rolling stock to be checked
     * @return <em>true</em> if one instance is found in the collection; <em>false</em> otherwise
     */
    boolean containsRollingStock(ObjectId collectionId, RollingStock rollingStock);

    /**
     * Returns whether the collection for this owner's name contains the provided rolling stock.
     * 
     * <p>
     * Although the service provide this method it is allowed to have the
     * same rolling stock item more than once.
     * </p>
     * <p>
     * In the case the collection contains the same rolling stock more than once
     * this method will return <em>true</em>.
     * </p>
     * 
     * @param ownerName the owner's collection name
     * @param rollingStock the rolling stock to be checked
     * @return <em>true</em> if one instance is found in the collection; <em>false</em> otherwise
     */
    boolean containsRollingStock(String ownerName, RollingStock rollingStock);

    /**
     * Adds a new item to the collection.
     * 
     * @param collectionId the user's collection
     * @param item the new item to be added
     */
    void addItem(ObjectId collectionId, CollectionItem item);

    /**
     * Adds a new item to the provided user name's collection.
     * 
     * <p>
     * Every user can have only one collection, so it is possible
     * to add new items using the account information only.
     * </p>
     * 
     * @param ownerName the collection's owner name
     * @param item the new item to be added
     */
    void addItem(String ownerName, CollectionItem item);

    /**
     * Saves the collection.
     * @param collection the collection to be saved
     */ 
    void save(Collection collection);

    /**
     * Removes the collection.
     * @param collection the collection to be saved
     */ 
    void remove(Collection collection);
}
