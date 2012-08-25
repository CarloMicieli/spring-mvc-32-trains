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

import com.trenako.entities.Account;

import de.bripkens.gravatar.DefaultImage;
import de.bripkens.gravatar.Gravatar;
import de.bripkens.gravatar.Rating;

/**
 * <tk:gravatar size="48" user="${slug}"/>
 * @author Carlo Micieli
 *
 */
@SuppressWarnings("serial")
public class AvatarTags extends SpringTagSupport {
	
	private int size;
	private Account user;
	private boolean onlyPicture;

	public void setSize(int size) {
		this.size = size;
	}

	public void setUser(Account user) {
		this.user = user;
	}
	
	public void setOnlyPicture(boolean onlyPicture) {
		this.onlyPicture = onlyPicture;
	}
	
	int getSize() {
		if (size == 0) return 48;
		return size;
	}

	Account getUser() {
		return user;
	}

	boolean onlyPicture() {
		return onlyPicture;
	}
	
	@Override
	protected int writeTagContent(JspWriter jspWriter, String contextPath)
			throws JspException {

		String gravatarImageURL = new Gravatar()
			.setSize(getSize())
			.setHttps(true)
			.setRating(Rating.PARENTAL_GUIDANCE_SUGGESTED)
			.setStandardDefaultImage(DefaultImage.MYSTERY_MAN)
			.getUrl(getUser().getEmailAddress());

		try {
			
			StringBuilder sb = new StringBuilder();
			if (!onlyPicture()) {
				sb.append("\n<ul class=\"thumbnails\">");
				sb.append("\n<li class=\"span4\"><div class=\"thumbnail\">");
			}
			
			sb.append("\n<a href=\"http://gravatar.com/emails/\">");
			sb.append("\n<img height=\"" + getSize() + "\" width=\"" + getSize() + "\" src=\""+gravatarImageURL+"\" />");
			sb.append("\n</a>");
			
			if (!onlyPicture()) {
				sb.append("\n</div></li>");
				sb.append("\n</ul>");
			}
			jspWriter.append(sb.toString());
		} catch (IOException e) {
		}
				
		return SKIP_BODY;
	}

}