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
package com.trenako.web.test;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SourceFilteringListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextLoader;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.test.context.support.AbstractContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * A Spring {@link ContextLoader} that establishes a mock Servlet environment
 * and {@link WebApplicationContext} to put under testing the Spring mvc controllers.
 * <p>
 * Original code at <http://tedyoung.me/2011/02/14/spring-mvc-integration-testing-controllers>
 * </p>
 *
 * @author Ted Young (original)
 * @author Carlo Micieli
 */
public class MockWebApplicationContextLoader extends AbstractContextLoader {
	/**
	 * The configuration defined in the {@link MockWebApplication} annotation.
	 */
	private MockWebApplication configuration;

	@Override
	public ApplicationContext loadContext(String... locations) throws Exception {
		return null;
	}

	/**
	 * One of these two methods will get called before
	 * {@link #loadContext(String...)}. We just use this chance to extract the
	 * configuration.
	 */
	@Override
	protected String[] generateDefaultLocations(Class<?> clazz) {
		extractConfiguration(clazz);
		return super.generateDefaultLocations(clazz);
	}

	/**
	 * One of these two methods will get called before
	 * {@link #loadContext(String...)}. We just use this chance to extract the
	 * configuration.
	 */
	@Override
	protected String[] modifyLocations(Class<?> clazz, String... locations) {
		extractConfiguration(clazz);
		return super.modifyLocations(clazz, locations);
	}

	private void extractConfiguration(Class<?> clazz) {
		configuration = AnnotationUtils.findAnnotation(clazz,
				MockWebApplication.class);
		if (configuration == null)
			throw new IllegalArgumentException("Test class " + clazz.getName()
					+ " must be annotated @MockWebApplication.");
	}

	@Override
	protected String getResourceSuffix() {
		return "-context.xml";
	}

	@Override
	public ApplicationContext loadContext(
			MergedContextConfiguration mergedConfig) throws Exception {
		final MockServletContext servletContext = new MockServletContext(
				configuration.webapp(), new FileSystemResourceLoader());
		final MockServletConfig servletConfig = new MockServletConfig(
				servletContext, configuration.name());

		final AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
		servletContext.setAttribute(
				WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
				webContext);
		webContext.getEnvironment().setActiveProfiles(
				mergedConfig.getActiveProfiles());
		webContext.setServletConfig(servletConfig);
		webContext.setConfigLocations(mergedConfig.getLocations());
		webContext.register(mergedConfig.getClasses());

		// Create a DispatcherServlet that uses the previously established
		// WebApplicationContext.
		@SuppressWarnings("serial")
		final DispatcherServlet dispatcherServlet = new DispatcherServlet() {
			@Override
			protected WebApplicationContext createWebApplicationContext(
					ApplicationContext parent) {
				return webContext;
			}
		};

		// Add the DispatcherServlet (and anything else you want) to the
		// context.
		// Note: this doesn't happen until refresh is called below.
		webContext.addBeanFactoryPostProcessor(new BeanFactoryPostProcessor() {
			@Override
			public void postProcessBeanFactory(
					ConfigurableListableBeanFactory beanFactory) {
				beanFactory.registerResolvableDependency(
						DispatcherServlet.class, dispatcherServlet);
				// Register any other beans here, including a ViewResolver if
				// you are using JSPs.
			}
		});

		// Have the context notify the servlet every time it is refreshed.
		webContext.addApplicationListener(new SourceFilteringListener(
				webContext, new ApplicationListener<ContextRefreshedEvent>() {
					@Override
					public void onApplicationEvent(ContextRefreshedEvent event) {
						dispatcherServlet.onApplicationEvent(event);
					}
				}));

		// Prepare the context.
		webContext.refresh();
		webContext.registerShutdownHook();

		// Initialize the servlet.
		dispatcherServlet.setContextConfigLocation("");
		dispatcherServlet.init(servletConfig);

		return webContext;
	}
}