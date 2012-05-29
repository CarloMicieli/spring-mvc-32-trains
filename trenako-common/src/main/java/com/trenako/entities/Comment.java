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
import javax.validation.constraints.Size;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * It represents a user comment to a rolling stock.
 *
 * @author Carlo Micieli 
 */
@Document(collection = "comments")
public class Comment {
	@Id
	private ObjectId id;
	
	@Indexed(unique = false)
	private String authorName;
	
	@DBRef
	@NotNull(message = "comment.author.required")
	private Account author;
	
	@Indexed(unique = false)
	private String rsSlug;
	
	@DBRef
	@NotNull(message = "comment.rollingStock.required")
	private RollingStock rollingStock;

	@NotBlank(message = "comment.content.required")
	@Size(max = 150, message = "comment.content.size.notmet")	 
	private String content;
	
	private Date postedAt;

	// required by spring data
	Comment() {
	}
	
	/**
	 * Creates a new {@link Comment} for a rolling stock model.
	 * 
	 * @param author the comment's author
	 * @param rollingStock the rolling stock model under review
	 * @param content the comment content
	 */
	public Comment(Account author, RollingStock rollingStock, String content) {
		this.author = author;
		this.rollingStock = rollingStock;
		this.content = content;
	}
	
	/**
	 * Returns the comment unique id.
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * Returns the comment's author name.
	 * 
	 * If the author's name is not provided, this value will be
	 * filled with the {@link Account#getSlug()} value.
	 * 
	 * @return the author's name
	 */
	public String getAuthorName() {
		if( authorName==null ) authorName = author.getSlug();
		return authorName;
	}

	/**
	 * Sets the comment's author name.
	 * @param authorName the author's name
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	/**
	 * Returns the comment's author.
	 * @return the author
	 */
	public Account getAuthor() {
		return author;
	}

	/**
	 * Sets the comment's author.
	 * @param author the author
	 */
	public void setAuthor(Account author) {
		this.author = author;
	}

	/**
	 * Returns the rolling stock slug.
	 * 
	 * If the rolling stock slug is not provided, this value will be
	 * filled with the {@link RollingStock#getSlug()} value.
	 * 
	 * @return the slug
	 */
	public String getRsSlug() {
		if( rsSlug==null ) rsSlug = rollingStock.getSlug();
		return rsSlug;
	}

	/**
	 * Sets the rolling stock slug.
	 * @param rsSlug  the slug
	 */
	public void setRsSlug(String rsSlug) {
		this.rsSlug = rsSlug;
	}

	/**
	 * Returns the commented rolling stock.
	 * @return the rolling stock
	 */
	public RollingStock getRollingStock() {
		return rollingStock;
	}

	/**
	 * Sets the commented rolling stock.
	 * @param rollingStock the rolling stock
	 */
	public void setRollingStock(RollingStock rollingStock) {
		this.rollingStock = rollingStock;
	}

	/**
	 * Returns the comment's content.
	 * @return the comment
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the comment's content.
	 * @param content the content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Returns the time this comment was posted.
	 * @return the posted time
	 */
	public Date getPostedAt() {
		return postedAt;
	}

	/**
	 * Sets the time this comment was posted.
	 * @param postedAt the posted time
	 */
	public void setPostedAt(Date postedAt) {
		this.postedAt = postedAt;
	}
	
	/**
	 * Indicates whether some other object is "equal to" this one.
	 * @param obj the reference object with which to compare.
	 * @return <em>true</em> if this object is the same as the obj argument; <em>false</em> otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if( this==obj ) return true;
		if( !(obj instanceof Comment) ) return false;
		
		Comment other = (Comment) obj;
		return content.equals(other.content) &&
				author.equals(other.author) &&
				rollingStock.equals(other.rollingStock);
	}
	
	/**
	 * Returns a string representation of the object.
	 * @return a string representation of the object.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getAuthorName());
		sb.append(" posted '");
		sb.append(getContent());
		sb.append("' on ");
		sb.append(getRsSlug());
		return sb.toString();	
	}
}
