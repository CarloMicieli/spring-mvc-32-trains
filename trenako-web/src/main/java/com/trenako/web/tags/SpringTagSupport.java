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

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

/**
 * It represent a jsp tag support class Spring context aware.
 * @author Carlo Micieli
 *
 */
@SuppressWarnings("serial")
public abstract class SpringTagSupport extends RequestContextAwareTag {

	protected void initContext() {
		WebApplicationContext wac = getRequestContext().getWebApplicationContext();
        AutowireCapableBeanFactory acbf = wac.getAutowireCapableBeanFactory();
        acbf.autowireBean(this);
	}
	
	
}
