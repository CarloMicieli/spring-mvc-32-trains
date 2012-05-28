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
	private String author;
	
	@DBRef
	@NotNull(message = "comment.author.required")
	private Account account;
	
	@Indexed(unique = false)
	private String rsSlug;
	
	@DBRef
	@NotNull(message = "comment.rollingStock.required")
	private RollingStock rollingStock;

	@NotBlank(message = "comment.content.required")
	@Size(max = 150, message = "comment.content.size.notmet")	 
	private String content;
	
	private Date postedAt;

	Comment() {
	}
	
	/**
	 * Creates a new {@link Comment} for a rolling stock model.
	 * @param author the comment's author
	 * @param rollingStock the rolling stock model under review
	 * @param content the comment content
	 */
	public Comment(Account author, RollingStock rollingStock, String content) {
		this.account = author;
		this.rollingStock = rollingStock;
		this.content = content;
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getAuthor() {
		if( author==null ) author = account.getSlug();
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getRsSlug() {
		if( rsSlug==null ) rsSlug = rollingStock.getSlug();
		return rsSlug;
	}

	public void setRsSlug(String rsSlug) {
		this.rsSlug = rsSlug;
	}

	public RollingStock getRollingStock() {
		return rollingStock;
	}

	public void setRollingStock(RollingStock rollingStock) {
		this.rollingStock = rollingStock;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(Date postedAt) {
		this.postedAt = postedAt;
	}
}
