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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.number.AbstractNumberFormatter;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CurrencyFormatter extends AbstractNumberFormatter {

	private final static BigDecimal RATIO = BigDecimal.valueOf(100);
	
	@Override
	public String print(Number object, Locale locale) {
		BigDecimal number = new BigDecimal((Integer)object).divide(RATIO);
		return super.print(number, locale);
	}

	@Override
	public Integer parse(String text, Locale locale) throws ParseException {
		Integer val = null;
		BigDecimal d = (BigDecimal) super.parse(text, locale);
		if (d!=null) {
			val = d.multiply(RATIO).intValue();
		}
		return val;
	}

	@Override
	protected NumberFormat getNumberFormat(Locale locale) {
		DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(locale);
		format.setParseBigDecimal(true);
		return format;
	}

}
