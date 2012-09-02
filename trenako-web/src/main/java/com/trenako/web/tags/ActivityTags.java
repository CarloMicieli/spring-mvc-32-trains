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

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.trenako.activities.Activity;
import com.trenako.activities.ActivityVerb;
import com.trenako.entities.Account;
import com.trenako.services.AccountsService;
import com.trenako.values.LocalizedEnum;

import static com.trenako.utility.PeriodUtils.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
@SuppressWarnings("serial")
public class ActivityTags extends SpringTagSupport {

	private MessageSource messageSource;
	private Activity activity;
	private AccountsService accountsService;

	@Autowired
	protected void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@Autowired
	protected void setAccountsService(AccountsService accountsService) {
		this.accountsService = accountsService;
	}
	
	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	AccountsService users() {
		return accountsService;
	}
	
	Activity activity() {
		return activity;
	}
	
	MessageSource messageSource() {
		return messageSource;
	}
	
	@Override
	protected int writeTagContent(JspWriter jspWriter, String contextPath)
			throws JspException {
		
		StringBuilder sb = new StringBuilder();
		
		Account user = users().findBySlug(activity().getActor());
		
		String slug = user == null ? activity().getActor() : user.getSlug();
		String displayName = user == null ? activity().getActor() : user.getDisplayName();
		sb.append("\n<a href=\"").append(contextPath).append("/users/").append(slug).append("\">")
			.append(displayName)
			.append("</a>");
		
		LocalizedEnum<ActivityVerb> verb = LocalizedEnum.parseString(
				activity().getVerb(), 
				messageSource(), 
				ActivityVerb.class);
		sb.append(" ").append(verb.getLabel()).append(" ");
		
		String objectName = activity().getObject().getDisplayName();
		String objectUrl = activity().getObject().getUrl();
		
		sb.append("\n<a href=\"").append(contextPath).append(objectUrl).append("\">")
			.append(objectName)
			.append("</a>");
		
		if (activity().getContext() != null) {
			sb.append(" ").append(activity().getContext().getContextType());
		}
		
		Pair<String, Integer> p = periodUntilNow(activity().getRecorded());
		String periodText = messageSource().getMessage(p.getKey(), 
				new Object[] { p.getValue() }, 
				p.getKey(), 
				getRequestContext().getLocale());
		
		sb.append("\n<br/><strong>").append(periodText).append("</strong>");
				
		try {
			jspWriter.append(sb.toString());
		} catch (IOException e) {
			throw new JspException(e);
		}
		
		return SKIP_BODY;
	}

}
