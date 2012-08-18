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
package com.trenako.format;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.format.number.NumberFormatter;
import org.springframework.util.StringValueResolver;

import com.trenako.format.annotations.IntegerFormat;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class IntegerAnnotationFormatterFactory
	implements AnnotationFormatterFactory<IntegerFormat>, EmbeddedValueResolverAware {

	private StringValueResolver resolver;
	
	@SuppressWarnings("unchecked")
	private final static Set<Class<?>> TYPES =
			new HashSet<Class<?>>(Arrays.asList(Integer.class));
	
	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		this.resolver = resolver;
	}

	@Override
	public Set<Class<?>> getFieldTypes() {
		return TYPES;
	}

	@Override
	public Printer<?> getPrinter(IntegerFormat annotation, Class<?> fieldType) {
		return getFormatter(annotation);
	}

	@Override
	public Parser<?> getParser(IntegerFormat annotation, Class<?> fieldType) {
		return getFormatter(annotation);
	}
	
	private Formatter<Number> getFormatter(IntegerFormat annotation) {
		String pattern = annotation.pattern();
		if (pattern != null && !pattern.isEmpty()) {
			return new NumberFormatter(resolveEmbeddedValue(pattern));
		}
		else {
			switch(annotation.style()) {
			case SCALE_GAUGE:
				return new GaugeFormatter();
			case CURRENCY:
				return new CurrencyFormatter();				
			case SCALE_RATIO:
				return new RatioFormatter();
			default:
				return new NumberFormatter();					
			}
		}
	}
	
	protected String resolveEmbeddedValue(String value) {
		return (resolver != null ? resolver.resolveStringValue(value) : value);
	}

}
