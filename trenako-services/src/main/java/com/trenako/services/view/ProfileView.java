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
package com.trenako.services.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import com.trenako.activities.Activity;
import com.trenako.entities.CategoriesCount;
import com.trenako.entities.Collection;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.WishList;
import com.trenako.entities.WishListItem;

/**
 * It represents an immutable view for user data like his collections
 * and wish lists.
 *
 * @author Carlo Micieli
 */
public class ProfileView {

    private final Iterable<Activity> userActivity;
    private final ProfileOptions options;
    private final Collection collection;
    private final Iterable<WishList> wishLists;
    private final Map<String, String> wishListNames;

    /**
     * Creates a new {@code ProfileView}.
     *
     * @param collection the user collection
     * @param wishLists  the user wish lists
     */
    public ProfileView(Iterable<Activity> userActivity,
                       Collection collection,
                       Iterable<WishList> wishLists,
                       ProfileOptions options) {

        this.userActivity = userActivity;
        this.collection = collection;
        this.wishLists = wishLists;
        this.options = options;
        this.wishListNames = extractWishListNames(wishLists);
    }

    public Collection getCollection() {
        return collection;
    }

    public Iterable<WishList> getWishLists() {
        return wishLists;
    }

    public Iterable<Activity> getUserActivity() {
        return userActivity;
    }

    public Map<String, String> getWishListNames() {
        return wishListNames;
    }

    public CategoriesCount getCategoriesCount() {
        if (collection == null) {
            return new CategoriesCount();
        }

        return collection.getCategories();
    }

    public Iterable<ItemView> getCollectionItems() {
        List<ItemView> items = new ArrayList<ItemView>();
        for (CollectionItem item : collection.getItems()) {
            items.add(ItemView.createView(collection, item));
        }

        return sortedSublist(items,
                new ItemViewComparator(),
                options.getNumberOfItemsForCollections());
    }

    public Iterable<ItemView> getWishListItems() {
        List<ItemView> items = new ArrayList<ItemView>();
        for (WishList list : wishLists) {
            for (WishListItem it : list.getItems()) {
                items.add(ItemView.createView(list, it));
            }
        }

        return sortedSublist(items,
                new ItemViewComparator(),
                options.getNumberOfItemsForWishLists());
    }

    private List<ItemView> sortedSublist(List<ItemView> elements, Comparator<ItemView> comparator, int size) {
        Collections.sort(elements, comparator);
        List<ItemView> items = new ArrayList<ItemView>(size);

        int numOfElements = elements.size() > size ? size : elements.size();
        for (int i = 0; i < numOfElements; i++) {
            items.add(elements.get(i));
        }

        return items;
    }

    private Map<String, String> extractWishListNames(Iterable<WishList> wishLists) {
        Map<String, String> mapNames = new LinkedHashMap<String, String>();
        for (WishList list : wishLists) {
            mapNames.put(list.getSlug(), list.getName());
        }

        return Collections.unmodifiableMap(mapNames);
    }

    private static class ItemViewComparator implements Comparator<ItemView> {

        @Override
        public int compare(ItemView item1, ItemView item2) {
            return item2.getAddedAt().compareTo(item1.getAddedAt());
        }

    }
}