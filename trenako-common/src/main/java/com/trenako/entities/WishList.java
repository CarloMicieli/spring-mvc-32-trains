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
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.bson.types.ObjectId;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trenako.values.Visibility;
import com.trenako.utility.Slug;

/**
 * It represents a rolling stocks {@code WishList}.
 * 
 * <p>
 * Users can have more than one wish list; each one of them has
 * a name unique for the owner (ie different users can have wish lists
 * with the same name).
 * </p>
 * <p>
 * The wish lists have a visibility flag, giving to the collection owners the option to make their 
 * wish lists {@code private} or {@code public}. By default the wish lists are {@code public}.
 * </p>
 *
 * @author Carlo Micieli
 * 
 */
@Document(collection = "wishLists")
public class WishList implements Comparable<WishList> {

	static final String DEFAULT_LIST_NAME = "New list";

	@Id
	private ObjectId id;
	
	@Indexed(unique = true)
	private String slug;
	
	@NotNull(message = "wishList.name.required")
	@Size(max = 25, message = "wishList.name.size.notmet")
	private String name;
	
	@NotNull(message = "wishList.owner.required")
	@Indexed
	private String owner;
	
	@Size(max = 250, message = "wishList.name.size.notmet")
	private String notes;

	@Valid
	private List<WishListItem> items;

	private Money totalPrice;
	private Money budget;
	private int numberOfItems;
	private String visibility;
	private Date lastModified;
	
	/**
	 * Creates an empty {@code WishList}.
	 */
	public WishList() {
	}
	
	/**
	 * Creates an empty {@code WishList} with the provided slug.
	 *
	 * @param slug the wish list slug
	 */
	public WishList(String slug) {
		this.slug = slug;
	}
	
	/**
	 * Creates a new public {@code WishList} for the provided user.
	 *
	 * @param owner the wish list owner
	 * @param name the name
	 */
	public WishList(Account owner, String name) {
		this(owner, name, defaultVisibility());
	}

	/**
	 * Creates a new {@code WishList}.
	 *
	 * @param owner the wish list owner
	 * @param name the name
	 * @param visibility the visibility
	 */
	public WishList(Account owner, String name, Visibility visibility) {
		this(owner, name, null, visibility, null);
	}
	
	/**
	 * Creates a new {@code WishList}.
	 *
	 * @param owner the wish list owner
	 * @param name the name
	 * @param visibility the visibility
	 * @param budget the budget
	 */
	public WishList(Account owner, String name, String notes, Visibility visibility, Money budget) {
		this.owner = owner(owner);
		this.name = name;
		this.notes = notes;
		this.slug = slug(this.owner, this.name);
		this.visibility = visibility(visibility);
		this.budget = budget;
		this.lastModified = new Date();
	}

	/**
	 * Creates the default {@code WishList} for the provided user.
	 * @param owner the {@code WishList} owner
	 */
	public static WishList defaultList(Account owner) {
		return new WishList(owner, DEFAULT_LIST_NAME);
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
			slug = slug(this.owner, this.name);
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
	public String getOwner() {
		return owner;
	}

	/**
	 * Sets the {@code WishList} owner.
	 * @param owner the owner
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * Returns the rolling stocks for the {@code WishList}.
	 * <p>
	 * This method returns an immutable {@code List}.
	 * </p>
	 * @return the rolling stocks
	 */
	public List<WishListItem> getItems() {
		if (items == null) {
			items = Collections.emptyList();
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
		if (StringUtils.isBlank(visibility)) {
			visibility = defaultVisibility().label();
		}
		return visibility;
	}

	/**
	 * Returns the {@code WishList} visibility as one of {@code Visibility} value.
	 * @return the visibility
	 */
	public Visibility getVisibilityValue() {
		return Visibility.parse(getVisibility());
	}

	/**
	 * Sets the {@code WishList} visibility.
	 * @param visibility the visibility
	 */
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	
	/**
	 * Returns the total price for the {@code WishList}.
	 * @return the total price
	 */
	public Money getTotalPrice() {
		if (totalPrice == null) {
			return Money.nullMoney();
		}
		return totalPrice;
	}

	/**
	 * Sets the total price for the {@code WishList}.
	 * @param totalPrice the total price
	 */
	public void setTotalPrice(Money totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * The current budget for the {@code WishList}.
	 * @return the budget
	 */
	public Money getBudget() {
		if (budget == null) {
			return Money.nullMoney();
		}
		return budget;
	}

	/**
	 * Sets the current budget for the {@code WishList}.
	 * @param budget the budget
	 */
	public void setBudget(Money budget) {
		this.budget = budget;
	}

	/**
	 * Returns the number of {@code WishList} items.
	 * @return the number of items
	 */
	public int getNumberOfItems() {
		return numberOfItems;
	}

	/**
	 * Sets the number of {@code WishList} items.
	 * @param numberOfItems the number of items
	 */
	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	/**
	 * Returns the {@code WishList} notes.
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Sets the {@code WishList} notes.
	 * @param notes the notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
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
			.isEquals();
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append("wishList{")
			.append("slug: ").append(getSlug())
			.append(", owner: ").append(getOwner())
			.append(", name: ").append(getName())
			.append(", visibility: ").append(getVisibility())
			.append("}")
			.toString();
	}

	@Override
	public int compareTo(WishList other) {
		int ret = 0;
		
		if (this.equals(other)) {
			return 0;
		}
		
		ret = String.CASE_INSENSITIVE_ORDER.compare(this.getOwner(), other.getOwner());
		if (ret != 0) return ret;
		
		ret = String.CASE_INSENSITIVE_ORDER.compare(this.getName(), other.getName());
		if (ret != 0) return ret;
		
		return 0;
	}
	
	/**
	 * Encode a new {@code WishList} slug with the values.
	 * @return the new slug
	 */
	public final String slug() {
		return slug(getOwner(), getName());
	}
	
	/**
	 * Encode a new {@code WishList} slug for the provided name.
	 * @param newName the new name
	 * @return the new slug
	 */
	public final String slug(String newName) {
		return slug(getOwner(), newName);
	}

	/**
	 * Returns the default {@code Visibility} for wish lists.
	 * @return the default visibility
	 */
	public static Visibility defaultVisibility() {
		return Visibility.PUBLIC;
	}

	/**
	 * Checks whether the user is the {@code WishList} owner.
	 * @param user the user
	 * @return {@code true} if the user is the owner; {@code false} otherwise
	 */
	public boolean isOwnedBy(Account user) {
		return getOwner().equals(owner(user));
	}
	
	private String slug(String owner, String name) {
		String s = new StringBuilder()
			.append(owner)
			.append(" ")
			.append(name)
			.toString();
		return Slug.encode(s);
	}
	
	private String visibility(Visibility visibility) {
		return visibility.label();
	}

	private String owner(Account owner) {
		return owner.getSlug();
	}
}