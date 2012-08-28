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
package com.trenako.validation;

import java.util.Currency;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.trenako.entities.Money;
import com.trenako.validation.constraints.ValidMoney;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class MoneyValidator implements ConstraintValidator<ValidMoney, Money> {

	@Override
	public void initialize(ValidMoney constraintAnnotation) {
	}

	@Override
	public boolean isValid(Money money, ConstraintValidatorContext context) {
		if (money == null || money.isEmpty()) {
			return true;
		}
		
		if (money.getValue() <= 0) {
			return false;
		}
		
		if (!validCurrency(money)) {
			return false;
		}
		
		return true;
	}

	private static boolean validCurrency(Money m) {
		if (StringUtils.isBlank(m.getCurrency())) return false;
		
		try {
			Currency.getInstance(m.getCurrency());
			return true;
		}
		catch (IllegalArgumentException ex) {
			// currencyCode is not a supported ISO 4217 code
		}
		
		return false;
	}
}
