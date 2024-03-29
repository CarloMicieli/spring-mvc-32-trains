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
package com.trenako.activities;

import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.BeforeClass;
import org.junit.Test;

import com.trenako.entities.Account;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ActivityValidationTests {
	private static Validator validator;
	
	@BeforeClass
	public static void initValidator() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	@Test
	public void shouldValidateValidActivities() {
		Activity activity = new Activity(user(), ActivityVerb.REVIEW, new ActivityObject());
		Set<ConstraintViolation<Activity>> violations = validator.validate(activity);
		assertEquals(0, violations.size());
	}
	
	@Test
	public void shouldValidateInvalidActivities() {
		Activity activity = new Activity();
		Set<ConstraintViolation<Activity>> violations = validator.validate(activity);
		assertEquals(3, violations.size());
	}
	
	private Account user() {
		return new Account.Builder("mail@mail.com")
			.displayName("Bob")
			.build();
	}

}
