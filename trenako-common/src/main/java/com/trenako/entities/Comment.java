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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trenako.mapping.WeakDbRef;

/**
 * It represents a user comment to a rolling stock.
 *
 * @author Carlo Micieli 
 */
@Document(collection = "comments")
public class Comment {
	@Id
	private ObjectId id;

	@NotNull(message = "comment.author.required")
	private WeakDbRef<Account> author;

	@NotNull(message = "comment.rollingStock.required")
	private WeakDbRef<RollingStock> rollingStock;

	@NotBlank(message = "comment.content.required")
	@Size(max = 150, message = "comment.content.size.notmet")
	private String content;

	private Date postedAt;

	/**
	 * Creates an empty {@code Comment}.
	 */
	public Comment() {
	}
	
	/**
	 * Creates a new {@code Comment} for a rolling stock model.
	 * 
	 * @param author the comment's author
	 * @param rollingStock the rolling stock model under review
	 * @param content the comment content
	 */
	public Comment(Account author, RollingStock rollingStock, String content) {
		this.setAuthor(author);
		this.setRollingStock(rollingStock);
		this.content = content;
	}
	
	/**
	 * Returns the {@code Comment} unique id.
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * Sets the {@code Comment} unique id.
	 * @param id the id
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * Returns the {@code Comment}'s author.
	 * @return the author
	 */
	public WeakDbRef<Account> getAuthor() {
		return author;
	}

	/**
	 * Sets the {@code Comment}'s author.
	 * @param author the author
	 */
	public void setAuthor(WeakDbRef<Account> author) {
		this.author = author;
	}
	
	/**
	 * Sets the {@code Comment}'s author.
	 * @param author the author
	 */
	public void setAuthor(Account author) {
		this.author = WeakDbRef.buildRef(author);
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
	public void setRollingStock(RollingStock rollingStock) {
		this.rollingStock = WeakDbRef.buildRef(rollingStock);
	}

	/**
	 * Sets the rolling stock to be commented.
	 * @param rollingStock the rolling stock
	 */
	public void setRollingStock(WeakDbRef<RollingStock> rollingStock) {
		this.rollingStock = rollingStock;
	}
	
	/**
	 * Returns the comment's content.
	 * @return the content
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
	 * Returns the time this {@code Comment} has been posted.
	 * @return the posted time
	 */
	public Date getPostedAt() {
		return postedAt;
	}

	/**
	 * Sets the time this {@code Comment} has been posted.
	 * @param postedAt the posted time
	 */
	public void setPostedAt(Date postedAt) {
		this.postedAt = postedAt;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Comment)) return false;
		
		Comment other = (Comment) obj;
		return new EqualsBuilder()
			.append(this.content, other.content)
			.append(this.author, other.author)
			.append(this.rollingStock, other.rollingStock)
			.isEquals();
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append("comment{author: ")
			.append(getAuthor().getSlug())
			.append(", content: ")
			.append(getContent())
			.append(", rs: ")
			.append(getRollingStock().getSlug())
			.append("}")
			.toString();	
	}
}