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
package com.trenako.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.springframework.beans.factory.annotation.Autowired;

import com.trenako.entities.Account;
import com.trenako.services.AccountsService;

import de.bripkens.gravatar.DefaultImage;
import de.bripkens.gravatar.Gravatar;
import de.bripkens.gravatar.Rating;

/**
 * It represents the tag to show the user's gravatar image.
 * 
 * <blockquote>
 * <pre>
 * <tk:gravatar size="48" user="${slug}" showGravatarLink="true" showName="true" />
 * </pre>
 * </blockquote>
 * 
 * @author Carlo Micieli
 *
 */
@SuppressWarnings("serial")
public class AvatarTags extends SpringTagSupport {
	
	private static final int DEFAULT_SIZE = 48;

	private AccountsService userService;
	
	private int size;
	private String user;
	private boolean showGravatarLink;
	private boolean showName;
	
	public void setSize(int size) {
		this.size = size;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public void setShowGravatarLink(boolean showGravatarLink) {
		this.showGravatarLink = showGravatarLink;
	}
	
	public void setShowName(boolean showName) {
		this.showName = showName;
	}
	
	@Autowired
	public void setAccountsService(AccountsService accountsService) {
		this.userService = accountsService;
	}
	
	int getSize() {
		if (size <= 0) { 
			return DEFAULT_SIZE;
		}
		return size;
	}

	String getUser() {
		return user;
	}

	boolean showGravatarLink() {
		return showGravatarLink;
	}
	
	boolean showUsername() {
		return showName;
	}
	
	@Override
	protected int writeTagContent(JspWriter jspWriter, String contextPath)
			throws JspException {

		Account account = userService.findBySlug(getUser());
		if (account == null) {
			return SKIP_BODY;
		}
				
		String gravatarImageURL = new Gravatar()
			.setSize(getSize())
			.setHttps(true)
			.setRating(Rating.PARENTAL_GUIDANCE_SUGGESTED)
			.setStandardDefaultImage(DefaultImage.MYSTERY_MAN)
			.getUrl(account.getEmailAddress());

		try {
			StringBuilder sb = new StringBuilder();

			if (showGravatarLink()) {
				sb.append("\n<a href=\"http://gravatar.com/emails/\">");
			}
			
			sb.append("\n<img ")
				.append("height=\"").append(getSize()).append("\" ")
				.append("width=\"").append(getSize()).append("\" ")
				.append("src=\"").append(gravatarImageURL).append("\" />");
			
			if (showGravatarLink()) {
				sb.append("\n</a>");
			}
			
			if (showUsername()) {
				sb.append("\n<br/>");
				sb.append("\n" + account.getDisplayName());
			}
			
			jspWriter.append(sb.toString());
		} catch (IOException e) {
			throw new JspException(e);
		}
				
		return SKIP_BODY;
	}

}