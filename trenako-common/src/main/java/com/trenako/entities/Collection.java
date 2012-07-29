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
package com.trenako.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trenako.values.Category;

/**
 * It represents a user collection of rolling stocks.
 * 
 * <p>
 * Every account can have one collection, after a new collection is created the 
 * user can start adding rolling stock models.
 * </p>
 * <p>
 * Especially for cars, either passenger or freight, it is quite possible one user 
 * owns the same exact item more than once, for this reason the application 
 * doesn't enforce any check.
 * </p>
 * <p>
 * The application will store some information for each model the users have purchased, 
 * for instance: the price, rolling stock condition (ie {@code new} or {@code pre owned}) 
 * and the addition date.
 * </p>
 * <p>
 * The collections have a visibility flag, giving the owner the chance to make his 
 * collection private. The application creates public collections by default.
 * </p>
 * 
 * @author Carlo Micieli
 * 
 */
@Document(collection = "collections")
public class Collection {

	@Id
	private ObjectId id;
	
    @Indexed(unique = true)
    private String ownerName;

    @DBRef
    @NotNull(message = "collection.owner.required")
    private Account owner;

    private List<CollectionItem> items;

    private Map<String, Integer> counters;

    private boolean visible = true;
  
    // required by spring data
    Collection() {
	}

    /**
     * Creates a new {@code Collection} with the provided owner.
     * @param owner the user who owns this collection
     */
	public Collection(Account owner) {
		this.owner = owner;
	}

	/**
	 * Returns the {@code Collection} id.
	 * @return the unique id
	 */
	public ObjectId getId() {
		return id;
	}
    
	/**
	 * Returns the {@code Collection}'s owner name.
	 * <p>
	 * If this value is null then the method will return {@link Account#getSlug()}.
	 * </p>
	 * @return the owner name
	 */
	public String getOwnerName() {
		if( ownerName==null ) ownerName = getOwner().getSlug();
		return ownerName;
	}

	/**
	 * Returns the account of the {@code Collection}'s owner.
	 * @return the owner
	 */
	public Account getOwner() {
		return owner;
	}

	/**
	 * Sets the account of the collection's owner.
	 * @param owner the owner
	 */
	public void setOwner(Account owner) {
		this.owner = owner;
	}

	/**
	 * Returns the list of rolling stock added to the collection.
	 * @return the list of collection items
	 */
	public List<CollectionItem> getItems() {
		return items;
	}

	/**
	 * Adds a new item to the user's collection.
	 * @param item the item to be added
	 */
	public void addItem(CollectionItem item) {
		if( items==null ) items = new ArrayList<CollectionItem>();
		items.add(item);
		
//		String key = item.getRollingStock().getCategory();
//		int count = count(key);
//		setCounter(key, count + 1);
	}
	
	/**
	 * Returns the number of items in the collection for the provided
	 * category.
	 * 
	 * @param category the category name
	 * @return the number of items
	 */
	public int count(String category) {
		int count = 0;
		String key = category;
		if( getCounters().containsKey(key) )
			count = getCounters().get(key);
		return count;
	}
	
	/**
	 * Returns an unmodifiable view of the categories counter map.
	 * <p>
	 * The categories are not sorted in alphabetic order, but they are
	 * listed as they are defined in the {@link Category} enumeration.
	 * </p>
	 * @return the counter map
	 */
	public SortedMap<String, Integer> getCategories() {
		final SortedMap<String, Integer> m = new TreeMap<String, Integer>();
		for( Category c : Category.values() ) {
			m.put(c.label(), count(c.label()));
		}
		return Collections.unmodifiableSortedMap(m);
	}
	
	/**
	 * Indicates whether the collection is public.
	 * 
	 * @return <em>true</em> if the collection is public; <em>false</em> otherwise
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Sets the collection visibility.
	 * <p>
	 * By default the collections are created public, only the owner
	 * can see the collection if its visibility is set to private.
	 * </p>
	 * @param visible <em>true</em> if the collection is public; <em>false</em> otherwise
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	// helper method to set the counter for a category
//	private void setCounter(String category, int count) {
//		getCounters().put(category, count);
//	}
	
	// helper method to get the counter map, with lazy initialization
	private Map<String, Integer> getCounters() {
		if( counters==null ) counters = new HashMap<String, Integer>();
		return counters;
	}
}
