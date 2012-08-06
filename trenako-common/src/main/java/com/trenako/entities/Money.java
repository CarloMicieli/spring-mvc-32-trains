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
package com.trenako.entities;

import java.math.BigDecimal;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import org.hibernate.validator.constraints.Range;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * It represents a class which encapsulates all information about amount of money, 
 * such as its value and its currency.
 *
 * @author Carlo Micieli
 *
 */
public class Money {

	private static BigDecimal MONEY_VALUE_FACTOR = BigDecimal.valueOf(100);

	@Field("val")
	@Range(min = 0, max = 999900, message = "money.value.range.notmet")
	private int value;
	
	@Field("cur")
	private String currency;
	
	/**
	 * Creates an empty {@code Money}.
	 */
	public Money() {
	}
	
	/**
	 * Creates a new {@code Money}.
	 * @param value the {@code Money} value
	 * @param currency the {@code Money} currency
	 */
	public Money(int value, String currency) {
		this.value = value;
		this.currency = currency;
	}
	
	/**
	 * Creates a new {@code Money} using the currency from 
	 * the provided Locale.
	 *
	 * @param value the {@code Money} value
	 * @param currency the {@code Money} currency
	 */
	public Money(int value, Locale locale) {
		this(value, Currency.getInstance(locale).getCurrencyCode());
	}
	
	/**
	 * Returns the {@code Money} value.
	 * @return the {@code Money} value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Sets the {@code Money} value.
	 * @param value the {@code Money} value
	 */	
	public void setValue(int value) {
		this.value = value;
	}
	
	/**
	 * Returns the {@code Money} currency.
	 * @return the {@code Money} currency
	 */	
	public String getCurrency() {
		return currency;
	}
	
	/**
	 * Sets the {@code Money} currency.
	 * @param currency the {@code Money} currency
	 */	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@Override 
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof Money)) return false;
		
		Money other = (Money) obj;
		return this.value == other.value &&
			this.currency.equals(other.currency);
	}
	
	@Override
	public String toString() {
		BigDecimal v = BigDecimal.valueOf(getValue()).divide(MONEY_VALUE_FACTOR);

		NumberFormat nf = NumberFormat.getCurrencyInstance();

		// the user may have selected a different currency than the default
		Currency currency = Currency.getInstance(getCurrency());
		nf.setCurrency(currency);
		nf.setMinimumFractionDigits(2);

		return nf.format(v);
	}
}