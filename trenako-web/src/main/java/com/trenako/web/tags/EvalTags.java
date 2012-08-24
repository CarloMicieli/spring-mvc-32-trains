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
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.trenako.mapping.LocalizedField;

/**
 * It represents a custom JSP tag to evaluate localized
 * text stored in a {@code LocalizedField} field.
 * 
 * @author Carlo Micieli
 *
 */
@SuppressWarnings("serial")
public class EvalTags extends SpringTagSupport {

	private LocalizedField<?> expression;
	private int maxLength;
	
	protected LocalizedField<?> getExpression() {
		return expression;
	}

	public void setExpression(LocalizedField<?> expression) {
		this.expression = expression;
	}

	protected int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	@Override
	protected int writeTagContent(JspWriter jspWriter, String contextPath)
			throws JspException {
		
		Locale userLocale = getRequestContext().getLocale();
		
		Object value = getExpression().get(userLocale);
		if (value == null) {
			return SKIP_BODY;
		}

		try {
			String out = value.toString();
			if (getMaxLength() > 0 && getMaxLength() < out.length()) {
				jspWriter.append(out.substring(0, getMaxLength()) + "...");
			}
			else {
				jspWriter.append("<p>");
				jspWriter.append(out.replaceAll(System.getProperty("line.separator"), "</p><p>"));
				jspWriter.append("</p>");
			}
		} 
		catch (IOException ex) {
			throw new JspException(ex);
		}
		
		return SKIP_BODY;
	}
}
