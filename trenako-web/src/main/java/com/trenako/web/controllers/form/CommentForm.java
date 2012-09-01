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

import javax.validation.Valid;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.trenako.entities.Account;
import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;
import com.trenako.security.AccountDetails;
import com.trenako.web.security.UserContext;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CommentForm {
	
	private String rsSlug;
	private String rsLabel;
	
	@Valid
	private Comment comment;
	
	public CommentForm() {
	}
	
	private CommentForm(RollingStock rs, Account author, String content) {
		this.rsLabel = rs.getLabel();
		this.rsSlug = rs.getSlug();
		
		this.comment = new Comment(author, content);
	}
	
	public static CommentForm newForm(RollingStock rs, Account author) {
		return new CommentForm(rs, author, "");
	}
	
	public static CommentForm newForm(RollingStock rs, UserContext secContext) {
		Account author = authenticatedUser(secContext);
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
		
	private static Account authenticatedUser(UserContext secContext) {
		if (secContext == null) {
			return null;
		}

		AccountDetails accountDetails = secContext.getCurrentUser();
		if (accountDetails == null) {
			return null;
		}
		
		return accountDetails.getAccount();
	}
}
