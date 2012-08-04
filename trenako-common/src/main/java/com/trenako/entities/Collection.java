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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trenako.mapping.WeakDbRef;
import com.trenako.values.Category;
import com.trenako.values.Visibility;

/**
 * It represents a user collection of rolling stocks.
 * 
 * <p>
 * Every account can have one collection, after a new collection is created the 
 * user can start adding rolling stock models.
 * </p>
 * <p>
 * Particularly for cars categories, both passenger and freight, it is quite possible one user 
 * owns the same exact rolling stock item more than once, for this reason the application 
 * doesn't enforce any check.
 * </p>
 * <p>
 * The application will store additional information for each model the users have purchased, 
 * for instance: the price, rolling stock condition (ie {@code new} or {@code pre owned}) 
 * and the purchasing date.
 * </p>
 * <p>
 * The collections have a visibility flag, giving the owner the chance to make his 
 * collection private. The collections are public by default.
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
	private String slug;

	@NotNull(message = "collection.owner.required")
	@Indexed(name = "owner.slug", unique = true)
	private WeakDbRef<Account> owner;

	private List<CollectionItem> items;

	private Map<String, Integer> categories;

	private String visibility;

	private Date lastModified;

	/**
	 * Creates an empty {@code Collection} without owner.
	 */
	public Collection() {
	}

	/**
	 * Creates a new {@code Collection} with the provided owner.
	 * @param owner the user who owns this collection
	 */
	public Collection(Account owner) {
		this.setOwner(owner);
	}
	
	/**
	 * Returns the {@code Collection} id.
	 * @return the unique id
	 */
	public ObjectId getId() {
		return id;
	}
	
	/**
	 * Sets the {@code Collection} id.
	 * @param id the unique id
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	/**
	 * Returns the {@code Collection} slug.
	 * @return the slug
	 */
	public String getSlug() {
		if (slug == null) {
			slug = getOwner().getSlug();
		}
		return slug;
	}
	
	/**
	 * Sets the {@code Collection} slug.
	 * @param slug the slug
	 */
	public void setSlug(String slug) {
		this.slug = slug;
	}
	
	/**
	 * Returns the account of the {@code Collection}'s owner.
	 * @return the owner
	 */
	public WeakDbRef<Account> getOwner() {
		return owner;
	}

	/**
	 * Sets the account of the {@code Collection}'s owner.
	 * @param owner the owner
	 */
	public void setOwner(Account owner) {
		this.owner = WeakDbRef.buildRef(owner);
	}
	
	/**
	 * Sets the account of the {@code Collection}'s owner.
	 * @param owner the owner
	 */
	public void setOwner(WeakDbRef<Account> owner) {
		this.owner = owner;
	}

	/**
	 * Sets the {@code Collection} rolling stocks list.
	 * @param items the rolling stocks list
	 */
	public void setItems(List<CollectionItem> items) {
		this.items = items;
	}

	/**
	 * Returns the {@code Collection} rolling stocks list.
	 * @return the rolling stocks list
	 */
	public List<CollectionItem> getItems() {
		return items;
	}

	/**
	 * Adds a new item to the user's collection.
	 * @param category the item category
	 * @param item the item to be added
	 */
	public void addItem(String category, CollectionItem item) {
		if (items == null) {
			items = new ArrayList<CollectionItem>();
		}
		items.add(item);
		incCounter(category);
	}

	/**
	 * Returns the number of items in the collection for the provided
	 * category.
	 * 
	 * @param category the category name
	 * @return the number of items
	 */
	public int count(String category) {
		if (getCounters() != null && getCounters().containsKey(category)) {
			return getCounters().get(category);
		}
		return 0;
	}

	/**
	 * Returns an immutable map with the counters by rolling stock category.
	 * <p>
	 * The categories are not sorted in alphabetic order, but they are
	 * listed as they are defined in the {@link Category} enumeration.
	 * </p>
	 * @return the counter map
	 */
	public Map<String, Integer> getCategories() {
		Map<String, Integer> m = new LinkedHashMap<String, Integer>();
		for( Category c : Category.values() ) {
			m.put(c.label(), count(c.label()));
		}
		return Collections.unmodifiableMap(m);
	}

	/**
	 * Indicates whether the {@code Collection} is visible.
	 * 
	 * @return {@code true} if the collection is public; {@code false} otherwise
	 */
	public boolean isVisible() {
		return true;
	}

	/**
	 * Sets the {@code Collection} visibility.
	 * <p>
	 * By default the collections are created {@link Visibility#PUBLIC}, only the owner
	 * can see the collection if its visibility is set to {@link Visibility#PRIVATE}.
	 * </p>
	 * @param visibility the collection visibility
	 */
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	
	/**
	 * Returns the {@code Collection} visibility.
	 * @return the collection visibility
	 */
	public String getVisibility() {
		return visibility;
	}

	/**
	 * Returns the timestamp for the last {@code Collection} changes.
	 * @return the last modified timestamp
	 */
	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * Sets the timestamp for the last {@code Collection} changes.
	 * @param lastModified the last modified timestamp
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	// helper method to set the counter for a category

	private void incCounter(String category) {
		getCounters().put(category, count(category) + 1);
	}

	// helper method to get the counter map, with lazy initialization
	private Map<String, Integer> getCounters() {
		if (categories == null) {
			categories = new LinkedHashMap<String, Integer>();
		}
		return categories;
	}
}