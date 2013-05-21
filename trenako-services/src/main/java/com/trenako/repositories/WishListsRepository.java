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

import com.trenako.entities.Account;
import com.trenako.entities.Money;
import com.trenako.entities.RollingStock;
import com.trenako.entities.WishList;
import com.trenako.entities.WishListItem;
import com.trenako.values.Visibility;

/**
 * The interface for the {@code WishList} repository.
 *
 * @author Carlo Micieli
 */
public interface WishListsRepository {

    /**
     * Returns the list of {@code WishList} for the provided owner.
     * <p>
     * This method is loading the wish lists header only; no items information
     * will be available.
     * </p>
     *
     * @param owner the owner
     * @returns a list of {@code WishList}
     */
    Iterable<WishList> findByOwner(Account owner);

    /**
     * Returns the list of {@code WishList} for the provided owner.
     * <p>
     * This method is loading the items for the {@code WishList}; the number
     * of returned wish list items is limited by the {@code maxNumberOfItems} value.
     * </p>
     *
     * @param owner            the owner
     * @param maxNumberOfItems the max number of returned items
     * @return a {@code WishList} list
     */
    Iterable<WishList> findAllByOwner(Account owner, int maxNumberOfItems);

    /**
     * Returns the {@code WishList} with the provided slug.
     *
     * @param slug the slug
     * @returns a {@code WishList} if found; {@code null} otherwise
     */
    WishList findBySlug(String slug);

    /**
     * Checks whether the provided {@code WishList} contains a given rolling stock.
     *
     * @param wishList the {@code WishList}
     * @param rs       the rolling stock
     * @return {@code true} if the list contains the rolling stock; {@code false} otherwise
     */
    boolean containsRollingStock(WishList wishList, RollingStock rs);

    /**
     * Adds a new item to the provided {@code WishList}.
     *
     * @param wishList the {@code WishList} to be modified
     * @param newItem  the item to be added
     */
    void addItem(WishList wishList, WishListItem newItem);

    /**
     * Updates an item for the provided {@code WishList}.
     *
     * @param wishList the {@code WishList} to be modified
     * @param item     the item to be saved
     */
    void updateItem(WishList wishList, WishListItem item);

    /**
     * Removes the new item from the provided {@code WishList}.
     *
     * @param wishList the {@code WishList} to be modified
     * @param item     the item to be removed
     */
    void removeItem(WishList wishList, WishListItem item);

    /**
     * Changes the {@code WishList} visibility (either {@code public} or {@code private}).
     *
     * @param wishList   the {@code WishList} to be modified
     * @param visibility the new visibility
     */
    void changeVisibility(WishList wishList, Visibility visibility);

    /**
     * Changes the {@code WishList} name.
     *
     * @param wishList the {@code WishList} to be modified
     * @param newName  the new name
     */
    void changeName(WishList wishList, String newName);

    /**
     * Changes the {@code WishList} budget.
     *
     * @param wishList  the {@code WishList} to be modified
     * @param newBudget the new budget for the wish list
     */
    void changeBudget(WishList wishList, Money newBudget);

    /**
     * Saves the {@code WishList}.
     *
     * @param wishList the {@code WishList} to be saved
     */
    void save(WishList wishList);

    /**
     * Saves the {@code WishList} changes.
     *
     * @param wishList the {@code WishList} to be saved
     */
    void saveChanges(WishList wishList);

    /**
     * Removes the {@code WishList}.
     *
     * @param wishList the {@code WishList} to be removed
     */
    void remove(WishList wishList);
}