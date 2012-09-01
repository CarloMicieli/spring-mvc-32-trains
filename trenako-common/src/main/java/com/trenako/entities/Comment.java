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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * It represents a user comment to a rolling stock.
 *
 * @author Carlo Micieli 
 */
public class Comment {
	
	@Indexed
	private String commentId;
	
	@NotNull(message = "comment.author.required")
	private String author;

	@NotBlank(message = "comment.content.required")
	@Size(max = 150, message = "comment.content.size.notmet")
	private String content;

	@Valid
	private List<Comment> answers;
	
	private Date postedAt;

	/**
	 * Creates an empty {@code Comment}.
	 */
	public Comment() {
	}
	
	/**
	 * Creates an empty {@code Comment} with the provided id.
	 * @param commentId the comment id
	 */
	public Comment(String commentId) {
		this.commentId = commentId;
	}
	
	/**
	 * Creates a new {@code Comment} for a rolling stock model.
	 * 
	 * @param account the comment's author
	 * @param content the comment content
	 */
	public Comment(Account account, String content) {
		this(account, content, new Date());
	}
	
	/**
	 * Creates a new {@code Comment} for a rolling stock model.
	 * 
	 * @param author the author
	 * @param content the comment content
	 * @param postedAt the posting date
	 */
	public Comment(Account author, String content, Date postedAt) {
		this(author(author), content, postedAt);
	}
	
	/**
	 * Creates a new {@code Comment} for a rolling stock model.
	 * 
	 * @param author the author slug
	 * @param content the comment content
	 * @param postedAt the posting date
	 */
	public Comment(String author, String content, Date postedAt) {
		this.author = author;
		this.content = content;
		this.postedAt = postedAt;

		this.commentId = commentId(this.author, this.postedAt);
	}	
	
	/**
	 * Returns the {@code Comment} id.
	 * @return the comment id
	 */
	public String getCommentId() {
		return commentId;
	}

	/**
	 * Sets the {@code Comment} id.
	 * @param commentId the comment id
	 */
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	/**
	 * Returns the {@code Comment}'s author.
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Sets the {@code Comment}'s author.
	 * @param authorId the author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
		
	/**
	 * Returns the {@code Comment}'s content.
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the {@code Comment}'s content.
	 * @param content the content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * Returns the answers list to this comment.
	 * @return the answers list
	 */
	public List<Comment> getAnswers() {
		return answers;
	}

	/**
	 * Sets the answers list to this comment.
	 * @param answers the answers list
	 */
	public void setAnswers(List<Comment> answers) {
		this.answers = answers;
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
			.append(this.postedAt, other.postedAt)
			.isEquals();
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append("comment{author: ")
			.append(getAuthor())
			.append(", content: ")
			.append(getContent())
			.append(", postedAt: ")
			.append(getPostedAt())
			.append("}")
			.toString();	
	}
	
	private static String author(Account account) {
		return account.getSlug();
	}
	
	private String commentId(String author, Date postedAt) {
		DateFormat df = new SimpleDateFormat("yy-MM-dd-HH-mm-ss");
		return new StringBuilder()
			.append(df.format(postedAt))
			.append("_")
			.append(author)
			.toString();
	}
}