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

import java.util.Date;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.apache.commons.lang3.tuple.Pair;

import static com.trenako.utility.PeriodUtils.*;

/**
 * <blockquote>
 * <pre>
 * <tk:period since="${date}"/>
 * </pre>
 * </blockquote>
 *
 * @author Carlo Micieli
 *
 */
@SuppressWarnings("serial")
public class PeriodTags extends SpringTagSupport {
	private MessageSource messageSource;
	private Date startDate;

	@Autowired 
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	protected MessageSource getMessageSource() {
		return this.messageSource;
	}

	public void setSince(Date startDate) {
		this.startDate = startDate;
	}

	public Date getSince() {
		return this.startDate;
	}

	@Override
	protected int writeTagContent(JspWriter jspWriter, String contextPath)
			throws JspException {

		Pair<String, Integer> p = periodUntilNow(getSince());
		String msg = getMessageSource().getMessage(p.getKey(), 
				new Object[] { p.getValue() }, 
				p.getKey(), 
				getRequestContext().getLocale());

		try {
			jspWriter.append(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return SKIP_BODY;
	}
}