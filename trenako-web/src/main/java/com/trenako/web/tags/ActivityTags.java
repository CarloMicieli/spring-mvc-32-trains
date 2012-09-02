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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.trenako.activities.Activity;

/**
 * 
 * @author Carlo Micieli
 *
 */
@SuppressWarnings("serial")
public class ActivityTags extends SpringTagSupport {

	private @Autowired MessageSource messageSource;
	private Activity activity;

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	Activity getActivity() {
		return activity;
	}

	@Override
	protected int writeTagContent(JspWriter jspWriter, String contextPath)
			throws JspException {
		
		
		//jspWriter.append()
		
		
		return SKIP_BODY;
	}

}
