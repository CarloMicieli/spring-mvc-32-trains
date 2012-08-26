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
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trenako.mapping.WeakDbRef;

/**
 * It represents a list of comments for the same {@code RollingStock}.
 * @author Carlo Micieli
 *
 */
@Document(collection = "comments")
public class RollingStockComments {
	@Id
	private ObjectId id;
	
	@Indexed(unique = true)
	private String slug;
	
	@NotNull(message = "comment.rollingStock.required")
	private WeakDbRef<RollingStock> rollingStock;
	
	@Valid
	private List<Comment> items;
	private int numberOfComments;
	
	/**
	 * Creates a new empty {@code RollingStockComments}.
	 */
	public RollingStockComments() {
	}
	
	/**
	 * Creates an empty {@code RollingStockComments} for the
	 * provided {@code RollingStock}.
	 * @param rs the rolling stock
	 */
	public RollingStockComments(RollingStock rs) {
		this(rs, slug(rs));
	}
	
	public RollingStockComments(RollingStock rs, String slug) {
		this.rollingStock = rollingStock(rs);
		this.slug = slug;	
	}
	
	/**
	 * Returns the {@code RollingStockComments} unique id.
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * Sets the {@code RollingStockComments} unique id.
	 * @param id the id
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * Returns the {@code RollingStockComments} slug.
	 * @return the slug
	 */
	public String getSlug() {
		return slug;
	}

	/**
	 * Sets the {@code RollingStockComments} slug.
	 * @param slug the slug
	 */	
	public void setSlug(String slug) {
		this.slug = slug;
	}

	/**
	 * Returns the rolling stock to be commented.
	 * @return the rolling stock
	 */
	public WeakDbRef<RollingStock> getRollingStock() {
		return rollingStock;
	}

	/**
	 * Sets the rolling stock to be commented.
	 * @param rollingStock the rolling stock
	 */
	public void setRollingStock(WeakDbRef<RollingStock> rollingStock) {
		this.rollingStock = rollingStock;
	}

	/**
	 * Returns the immutable comments list.
	 * @return the comments list
	 */
	public List<Comment> getItems() {
		if (items == null) {
			return Collections.emptyList();
		}
		return items;
	}

	/**
	 * Sets the comments list.
	 * @param items the comments list
	 */
	public void setItems(List<Comment> items) {
		this.items = items;
	}

	/**
	 * Returns the number of comments.
	 * @return the number of comments
	 */
	public int getNumberOfComments() {
		return numberOfComments;
	}

	/**
	 * Sets the number of comments.
	 * @param numberOfComments the number of comments
	 */
	public void setNumberOfComments(int numberOfComments) {
		this.numberOfComments = numberOfComments;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof RollingStockComments)) return false;
		
		RollingStockComments other = (RollingStockComments) obj;
		return new EqualsBuilder()
			.append(this.id, other.id)
			.append(this.slug, other.slug)
			.append(this.rollingStock, other.rollingStock)
			.isEquals();
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append("comments{slug: ")
			.append(getSlug())
			.append(", rollingStock: ")
			.append(getRollingStock().getSlug())
			.append(", count: ")
			.append(getNumberOfComments())
			.append("}")
			.toString();	
	}
	
	private WeakDbRef<RollingStock> rollingStock(RollingStock rs) {
		return WeakDbRef.buildRef(rs);
	}
	
	private static String slug(RollingStock rs) {
		return rs.getSlug();
	}	
}
