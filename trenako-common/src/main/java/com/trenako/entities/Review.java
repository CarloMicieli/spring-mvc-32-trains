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

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * It represents a user review to a rolling stock.
 *
 * @author Carlo Micieli
 */
@Document(collection = "reviews")
public class Review {
	@Id
	private ObjectId id;
	
	@Indexed(unique = false)
	private String authorName;
	
	@DBRef
	@NotNull(message = "review.author.required")
	private Account author;
	
	@Indexed(unique = false)
	private String rsSlug;
	
	@DBRef
	@NotNull(message = "review.rollingStock.required")
	private RollingStock rollingStock;
	
	@NotBlank(message = "review.content.required")
	private String content;
	
	@Range(min = 0, max = 5, message = "review.rating.range.notmet")
	private int rating;
	
	private Date postedAt;
	
	Review() {
	}
	
	/**
	 * Creates a new {@link Review} for a rolling stock model.
	 * @param author the review's author
	 * @param rollingStock the rolling stock model under review
	 * @param content the review content
	 */
	public Review(Account author, RollingStock rollingStock, String content) {
		this.author = author;
		this.rollingStock = rollingStock;
		this.content = content;
	}

	/**
	 * Returns the review unique id.
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * Returns the review's author name.
	 * <p>
	 * If the author's name is not provided, this value will be
	 * filled with the {@link Account#getSlug()} value.
	 * </p>
	 * 
	 * @return the author's name
	 */
	public String getAuthorName() {
		if( authorName==null ) authorName = author.getSlug();
		return authorName;
	}

	/**
	 * Sets the review's author name.
	 * @param authorName the author's name
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	/**
	 * Sets the review's author name.
	 * @return the author's name
	 */	
	public Account getAuthor() {
		return author;
	}

	/**
	 * Sets the review's author name.
	 * @param author the author's name
	 */
	public void setAuthor(Account author) {
		this.author = author;
	}

	/**
	 * Returns the rolling stock slug.
	 * <p>
	 * If the rolling stock slug is not provided, this value will be
	 * filled with the {@link RollingStock#getSlug()} value.
	 * </p>
	 * 
	 * @return the slug
	 */
	public String getRsSlug() {
		if( rsSlug==null ) rsSlug = rollingStock.getSlug();
		return rsSlug;
	}

	/**
	 * Sets the rolling stock slug.
	 * @param rsSlug the slug
	 */
	public void setRsSlug(String rsSlug) {
		this.rsSlug = rsSlug;
	}

	/**
	 * Returns the rolling stock under review.
	 * @return the rolling stock
	 */
	public RollingStock getRollingStock() {
		return rollingStock;
	}

	/**
	 * Sets the rolling stock under review.
	 * @param rollingStock the rolling stock
	 */
	public void setRollingStock(RollingStock rollingStock) {
		this.rollingStock = rollingStock;
	}

	/**
	 * Returns the review's content.
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the review's content.
	 * @param content the content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Returns the rating value.	
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * Sets the rating value.
	 * <p>
	 * The rating value is included in the range between 0 and 5.
	 * </p>
	 * @param rating the rating
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * Returns the time this review was posted.
	 * @return the posted time
	 */
	public Date getPostedAt() {
		return postedAt;
	}

	/**
	 * Sets the time this review was posted.
	 * @param postedAt the posted time
	 */
	public void setPostedAt(Date postedAt) {
		this.postedAt = postedAt;
	}
	
	/**
	 * Indicates whether some other object is "equal to" this one.
	 * @param obj the reference object with which to compare
	 * @return <em>true</em> if this object is the same as the obj argument; <em>false</em> otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if( obj==this ) return true;
		if( !(obj instanceof Review) ) return false;
		
		Review other = (Review) obj;
		return content.equals(other.content) &&
				author.equals(other.author) &&
				rollingStock.equals(other.rollingStock); 
	}
}
