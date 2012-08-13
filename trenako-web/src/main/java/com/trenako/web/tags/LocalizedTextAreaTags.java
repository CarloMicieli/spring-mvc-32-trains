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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.support.BindStatus;
import org.springframework.web.servlet.support.RequestDataValueProcessor;
import org.springframework.web.servlet.tags.NestedPathTag;
import org.springframework.web.util.ExpressionEvaluationUtils;

import com.trenako.mapping.LocalizedField;
import com.trenako.web.tags.html.HtmlTag;
import static com.trenako.web.tags.html.HtmlBuilder.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
@SuppressWarnings("serial")
public class LocalizedTextAreaTags extends SpringTagSupport {

	private MessageSource messageSource;
	
	public static final String ROWS_ATTRIBUTE = "rows";
	public static final String COLS_ATTRIBUTE = "cols";

	private String rows;
	private String cols;
	private String path;
	private String cssClass;

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	
	protected String getCssClass() {
		return this.cssClass;
	}
	
	@Autowired 
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	protected MessageSource getMessageSource() {
		return this.messageSource;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	protected String getPath() {
		return this.path;
	}
	
	public void setRows(String rows) {
		this.rows = rows;
	}

	protected String getRows() {
		return this.rows;
	}

	public void setCols(String cols) {
		this.cols = cols;
	}

	protected String getCols() {
		return this.cols;
	}
		
	protected Object evaluate(String attributeName, Object value) throws JspException {
		if (value instanceof String) {
			return ExpressionEvaluationUtils.evaluate(attributeName, (String) value, this.pageContext);
		}
		else {
			return value;
		}
	}
	
	protected String resolvePath() throws JspException {
		String resolvedPath = (String) evaluate("path", getPath());
		return (resolvedPath != null ? resolvedPath : "");
	}

	protected String getName(Locale loc) throws JspException {
		String expression = getBindStatus().getExpression();
		if (expression == null) {
			return "";
		}
		
		return new StringBuilder()
			.append(expression)
			.append("['").append(loc.getLanguage()).append("']")
			.toString();
	}
	
	protected String getValue(LocalizedField<?> field, Locale locale) {
		if (field == null || !field.containsLocale(locale)) {
			return "";
		}
		
		return field.get(locale).toString();
	}
	
	protected BindStatus getBindStatus() throws JspException {
		String resolvedPath = ExpressionEvaluationUtils.evaluateString("path", getPath(), pageContext);
		String nestedPath = (String) pageContext.getAttribute(
				NestedPathTag.NESTED_PATH_VARIABLE_NAME, 
				PageContext.REQUEST_SCOPE);

		if (nestedPath != null && 
				!resolvedPath.startsWith(nestedPath) &&
				!resolvedPath.equals(nestedPath.substring(0, nestedPath.length() - 1))) {
			resolvedPath = nestedPath + resolvedPath;
		}

		return new BindStatus(getRequestContext(), resolvedPath, isHtmlEscape());
	}

	protected final String processFieldValue(String name, String value, String type) {
		RequestDataValueProcessor processor = getRequestContext().getRequestDataValueProcessor();
		ServletRequest request = this.pageContext.getRequest();
		if (processor != null && (request instanceof HttpServletRequest)) {
			value = processor.processFormFieldValue((HttpServletRequest) request, name, value, type);
		}
		return value;
	}
	
	protected Object getBoundValue() throws JspException {
		return getBindStatus().getValue();
	}

	@Override
	protected int writeTagContent(JspWriter jspWriter, String contextPath)
			throws JspException {
		LocalizedField<?> value = (LocalizedField<?>) getBoundValue();
		Locale userLocale = getRequestContext().getLocale();

		List<HtmlTag> tags = new ArrayList<HtmlTag>();
		
		List<String> errorMessages = new ArrayList<String>();
		errorMessages.addAll(Arrays.asList(getBindStatus().getErrorMessages()));
		
		
		
		String cssClass = errorMessages.size() == 0 
				? "control-group" : "control-group error";
		
		for (Locale loc : LocalizedField.locales(userLocale)) {
			String errorMsg = errorMessage(loc, errorMessages);
			
			tags.add(div(
						label(labelMessage(loc)).forId(getName(loc)).cssClass("control-label"),
						div(
							textArea(getValue(value, loc)).cssClass("input-xlarge").rows(getRows()).cols(getCols()).id(getName(loc)).name(getName(loc)),
							span(errorMsg).cssClass("help-inline")	
								).cssClass("controls")
					).cssClass(cssClass));
		}
			
		try {
			jspWriter.append(snippet(tags(tags)).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return SKIP_BODY;
	}
	
	private String labelMessage(Locale locale) throws JspException {
		String code = getBindStatus().getPath() + ".label";
		return getMessageSource().getMessage(code, 
				new Object[] { locale.getDisplayLanguage() }, 
				code, 
				null);
	}
	
	private String errorMessage(Locale locale, List<String> errorMessages) {
		if (!LocalizedField.isDefaultLocale(locale)) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		for (String err : errorMessages) {
			sb.append(getMessageSource().getMessage(err, null, err, null));
		}
		return sb.toString();
	}
}
