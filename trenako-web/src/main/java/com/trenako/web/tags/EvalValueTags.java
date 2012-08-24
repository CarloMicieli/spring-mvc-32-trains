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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.trenako.values.LocalizedEnum;

/**
 * 
 * @author Carlo Micieli
 * @param <T>
 *
 */
@SuppressWarnings("serial")
public class EvalValueTags<T extends Enum<T>> extends SpringTagSupport {

	private final static String VALUES_PACKAGE_NAME = "com.trenako.values";

	private MessageSource messageSource;
	private String expression;
	private String typeName;
	
	@Autowired 
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setType(String typeName) {
		this.typeName = typeName;
	}

	protected MessageSource getMessageSource() {
		return this.messageSource;
	}

	protected String getTypeName() {
		return typeName;
	}

	protected String getExpression() {
		return expression;
	}

	@SuppressWarnings("unchecked")
	protected Class<T> getEnumClass() {
		try {
			Class<?> type = Class.forName(VALUES_PACKAGE_NAME + "." + getTypeName());
			return (Class<T>) type;
		}
		catch (Exception ex) {
			return null;
		}
	}

	@Override
	protected int writeTagContent(JspWriter jspWriter, String contextPath)
			throws JspException {
	
		String value = getExpression();
		if (StringUtils.isBlank(value)) {
			return SKIP_BODY;
		}

		Class<T> clazz = getEnumClass();
		if (clazz == null) {
			return SKIP_BODY;
		}

		try {
			LocalizedEnum<?> val = LocalizedEnum.parseString(value, 
					getMessageSource(), 
					clazz);
			jspWriter.append(val.getLabel());
		} 
		catch (IOException ex) {
			throw new JspException(ex);
		}
		
		return SKIP_BODY;
	}
}