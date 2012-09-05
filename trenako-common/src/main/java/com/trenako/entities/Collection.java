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

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
	@Indexed(unique = true)
	private String owner;

	@Valid
	private List<CollectionItem> items;

	private CategoriesCount categories;
	private String visibility;
	private Date lastModified;

	/**
	 * Creates an empty {@code Collection} without owner.
	 */
	public Collection() {
	}
	
	/**
	 * Creates a new {@code Collection} with the provided owner.
	 * @param owner the collection owner
	 */
	public Collection(Account owner) {
		this(owner, defaultVisibility());
	}

	/**
	 * Creates a new {@code Collection} for the user with the 
	 * provided visibility.
	 * @param owner the collection owner
	 * @param visibility the collection visibility
	 */
	public Collection(Account owner, Visibility visibility) {
		this.owner = owner(owner);
		this.visibility = visibility(visibility);
		this.slug = slug(owner);
		this.lastModified = new Date();
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
	public String getOwner() {
		return owner;
	}

	/**
	 * Sets the account of the {@code Collection}'s owner.
	 * @param owner the owner
	 */
	public void setOwner(String owner) {
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
	 * <p>
	 * This method returns an immutable list of collection items.
	 * </p>
	 * 
	 * @return the rolling stocks list
	 */
	public List<CollectionItem> getItems() {
		if (items == null) {
			items = Collections.emptyList();
		}
		return items;
	}

	/**
	 * Returns the counters by rolling stock category.
	 * @return the counter map
	 */
	public void setCategories(CategoriesCount categories) {
		this.categories = categories;
	}
	
	/**
	 * Returns the counters by rolling stock category.
	 * @return the counter map
	 */
	public CategoriesCount getCategories() {
		if (categories == null) {
			return CategoriesCount.EMPTY;
		}
		return categories;
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
		if (StringUtils.isBlank(visibility)) {
			visibility = defaultVisibility().label();
		}
		return visibility;
	}

	/**
	 * Returns the {@code Collection} visibility as one of {@code Visibility} value.
	 * @return the visibility
	 */
	public Visibility getVisibilityValue() {
		return Visibility.parse(getVisibility());
	}

	/**
	 * Returns the default {@code Visibility} for collections.
	 * @return the default visibility
	 */
	public static Visibility defaultVisibility() {
		return Visibility.PUBLIC;
	}
	
	/**
	 * Returns the last modification timestamp for the {@code Collection}.
	 * @return the last modified timestamp
	 */
	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * Sets the last modification timestamp for the {@code Collection}.
	 * @param lastModified the last modified timestamp
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	/**
	 * Checks whether the provided user is {@code Collection} owner
	 * @param user the user
	 * @return {@code true} if the user is the owner; {@code false} otherwise
	 */
	public boolean isOwnedBy(Account user) {
		return getOwner().equals(owner(user));
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append("collection{owner: ")
			.append(getOwner())
			.append(", visibility: ")
			.append(getVisibility())
			.append(", item(s): ")
			.append(getItems().size())
			.append("}")
			.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof Collection)) return false;
		
		Collection other = (Collection) obj;
		return getOwner().equals(other.getOwner());
	}

	private static String visibility(Visibility visibility) {
		return visibility.label();
	}
	
	private static String slug(Account owner) {
		return owner.getSlug();
	}
	
	private static String owner(Account owner) {
		return owner.getSlug();
	}
}