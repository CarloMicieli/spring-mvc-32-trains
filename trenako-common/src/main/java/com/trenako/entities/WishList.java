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
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.bson.types.ObjectId;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trenako.mapping.WeakDbRef;
import com.trenako.values.Visibility;
import com.trenako.utility.Slug;

/**
 * It represents a rolling stocks {@code WishList}.
 *
 * @author Carlo Micieli
 * 
 */
@Document(collection = "wishLists")
public class WishList {

	public static final String DEFAULT_LIST_NAME = "New wish list";

	@Id
	private ObjectId id;
	
	@Indexed(unique = true)
	private String slug;
	
	@NotNull(message = "wishList.name.required")
	@Size(max = 25, message = "wishList.name.size.notmet")
	private String name;
	
	private boolean defaultList;

	@NotNull(message = "wishList.owner.required")
	@Indexed(name = "owner.slug")
	private WeakDbRef<Account> owner;

	@Valid
	@Indexed(name = "items.rollingStock.slug", unique = true)
	private List<WishListItem> items;

	private String visibility;
	
	private Date lastModified;
	
	/**
	 * Creates an empty {@code WishList}.
	 */
	public WishList() {
	}
	
	/**
	 * Creates a new {@code WishList} for the provided user.
	 *
	 * @param owner the wish list owner
	 * @param name the name
	 * @param visibility the visibility
	 */
	public WishList(Account owner, String name, Visibility visibility) {
		this.setOwner(owner);
		this.setVisibility(visibility);
		this.name = name;
		this.slug = initSlug();
		this.lastModified = new Date();
	}

	/**
	 * Returns the {@code WishList} unique id.
	 * @return the wish list id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * Sets the {@code WishList} unique id.
	 * @param id the wish list id
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * Returns the {@code WishList} slug.
	 * @return the slug
	 */
	public String getSlug() {
		if (slug == null) {
			slug = initSlug();
		}
		return slug;
	}

	/**
	 * Sets the {@code WishList} slug.
	 * @param slug the slug
	 */
	public void setSlug(String slug) {
		this.slug = slug;
	}

	/**
	 * Returns the {@code WishList} name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the {@code WishList} name.
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Checks whether the current {@code WishList} is the default list for the user.
	 * @return {@code true} if this is the default list; {@code false} otherwise
	 */
	public boolean isDefaultList() {
		return defaultList;
	}

	/**
	 * Checks whether the current {@code WishList} is the default list for the user.
	 * @param isDefault {@code true} if this is the default list; {@code false} otherwise
	 */
	public void setDefaultList(boolean isDefault) {
		this.defaultList = isDefault;
	}

	/**
	 * Returns the {@code WishList} owner.
	 * @return the owner
	 */
	public WeakDbRef<Account> getOwner() {
		return owner;
	}

	/**
	 * Sets the {@code WishList} owner.
	 * @param owner the owner
	 */
	public void setOwner(Account owner) {
		this.owner = WeakDbRef.buildRef(owner);
	}
	
	/**
	 * Sets the {@code WishList} owner.
	 * @param owner the owner
	 */
	public void setOwner(WeakDbRef<Account> owner) {
		this.owner = owner;
	}

	/**
	 * Returns the rolling stocks for the {@code WishList}.
	 * @return the rolling stocks
	 */
	public List<WishListItem> getItems() {
		if (items == null) {
			items = new ArrayList<WishListItem>();
		}
		return items;
	}

	/**
	 * Sets the rolling stocks for the {@code WishList}.
	 * @param items the rolling stocks
	 */
	public void setItems(List<WishListItem> items) {
		this.items = items;
	}

	/**
	 * Returns the {@code WishList} visibility.
	 * @return the visibility
	 */
	public String getVisibility() {
		return visibility;
	}

	/**
	 * Sets the {@code WishList} visibility.
	 * @param visibility the visibility
	 */
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	/**
	 * Sets the {@code WishList} visibility.
	 * @param visibility the visibility
	 */
	public void setVisibility(Visibility visibility) {
		this.visibility = visibility.label();
	}
	
	/**
	 * Returns the last modified timestamp.
	 * @return the timestamp
	 */
	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * Sets the last modified timestamp.
	 * @param lastModified the timestamp
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof WishList)) return false;
		
		WishList other = (WishList) obj;
		
		return new EqualsBuilder()
			.append(this.slug, other.slug)
			.append(this.owner, other.owner)
			.isEquals();
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append("wishList{owner: ")
			.append(getOwner())
			.append(", name: ")
			.append(getName())
			.append(", visibility: ")
			.append(getVisibility())
			.append("}")
			.toString();
	}
	
	private String initSlug() {
		String s = new StringBuilder()
			.append(getOwner().getSlug())
			.append(" ")
			.append(getName())
			.toString();
		return Slug.encode(s);
	}
}