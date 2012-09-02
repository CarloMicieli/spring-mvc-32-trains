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
package com.trenako.web.controllers.form;

import java.util.Date;

import javax.validation.Valid;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.trenako.entities.Account;
import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;
import com.trenako.web.security.UserContext;

/**
 * It represents a web form for {@code Comment}s.
 * @author Carlo Micieli
 *
 */
public class CommentForm {
	
	private String rsSlug;
	private String rsLabel;
	
	@Valid
	private Comment comment;
	
	/**
	 * Creates a new empty {@code CommentForm}.
	 */
	public CommentForm() {
	}
	
	private CommentForm(RollingStock rs, Account author, String content) {
		this.rsLabel = rs.getLabel();
		this.rsSlug = rs.getSlug();
		
		this.comment = new Comment(author, content, null);
	}
	
	/**
	 * Creates a new {@code CommentForm} for the currently logged user.
	 * 
	 * @param rs the rolling stock to be commented
	 * @param secContext the security context
	 * @return a {@code CommentForm} if there is a logged user; {@code null} otherwise
	 */
	public static CommentForm newForm(RollingStock rs, UserContext secContext) {
		Account author = UserContext.authenticatedUser(secContext);
		if (author != null) {
			return new CommentForm(rs, author, "");
		}
		
		return null;
	}

	public String getRsSlug() {
		return rsSlug;
	}

	public void setRsSlug(String rsSlug) {
		this.rsSlug = rsSlug;
	}

	public String getRsLabel() {
		return rsLabel;
	}

	public void setRsLabel(String rsLabel) {
		this.rsLabel = rsLabel;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}
	
	/**
	 * Builds the {@code Comment} for the posted form.
	 * @param postedAt the posting date
	 * @return a {@code Comment}
	 */
	public Comment buildComment(Date postedAt) {
		if (getComment() == null) {
			return null;
		}
		
		return new Comment(
				getComment().getAuthor(), 
				getComment().getContent(), 
				postedAt);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof CommentForm)) return false;
		
		CommentForm other = (CommentForm) obj;
		return new EqualsBuilder()
			.append(this.comment, other.comment)
			.append(this.rsSlug, other.rsSlug)
			.append(this.rsLabel, other.rsLabel)
			.isEquals();
	}
}
