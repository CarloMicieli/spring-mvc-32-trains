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
import com.trenako.utility.Slug;

/**
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

	@NotNull(message = "wishList.owner.required")
	@Indexed(name = "owner.slug", unique = true)
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
	 * Creates a new {@code WishList}
	 * @param owner the wish list owner
	 * @param name the name
	 * @param visibility the visibility
	 */
	public WishList(Account owner, String name, String visibility) {
		this.setOwner(owner);
		this.name = name;
		this.visibility = visibility;
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
			String s = new StringBuilder()
				.append(getOwner().getSlug())
				.append(" ")
				.append(getName())
				.toString();
			slug = Slug.encode(s);
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

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof WishList)) return false;
		
		WishList other = (WishList) obj;
		
		return new EqualsBuilder()
			.append(this.name, other.name)
			.append(this.owner, other.owner)
			.isEquals();
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append("wishList{owner: ")
			.append(getOwner().getLabel())
			.append(", name: ")
			.append(getName())
			.append(", visibility: ")
			.append(getVisibility())
			.append("}")
			.toString();
	}	
}