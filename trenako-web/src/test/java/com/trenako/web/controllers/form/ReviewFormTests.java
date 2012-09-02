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
package com.trenako.web.controllers.form;

import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.entities.Account;
import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;
import com.trenako.security.AccountDetails;
import com.trenako.web.security.UserContext;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ReviewFormTests {

	@Mock UserContext mockUserContext;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(mockUserContext.getCurrentUser()).thenReturn(new AccountDetails(loggedUser()));
	}
	
	@Test
	public void shouldCheckWhetherTwoReviewFormsAreEquals() {
		ReviewForm x = ReviewForm.newForm(rollingStock(), mockUserContext);
		ReviewForm y = ReviewForm.newForm(rollingStock(), mockUserContext);
		assertTrue("Review forms are different", x.equals(x));
		assertTrue("Review forms are different", x.equals(y));
	}

	@Test
	public void shouldCheckWhetherTwoReviewFormsAreDifferent() {
		ReviewForm x = ReviewForm.newForm(rollingStock(), mockUserContext);
		ReviewForm y = ReviewForm.newForm(rollingStock2(), mockUserContext);
		assertFalse("Review forms are equals", x.equals(y));
	}
	
	@Test
	public void shouldReturnNullWhenCreatingReviewFormsForAnonymousUsers() {
		when(mockUserContext.getCurrentUser()).thenReturn(null);
		
		ReviewForm form = ReviewForm.newForm(rollingStock(), mockUserContext);
		
		assertNull("Review form is not null", form);
	}
	
	@Test
	public void shouldCreateReviewFormsForAuthenticatedUsers() {
		
		ReviewForm form = ReviewForm.newForm(rollingStock(), mockUserContext);
		
		assertNotNull("Review form is null", form);
		assertNotNull("Review is null", form.getReview());
		assertEquals(loggedUser().getSlug(), form.getReview().getAuthor());
		assertEquals(rollingStock().getSlug(), form.getRsSlug());
		assertEquals(rollingStock().getLabel(), form.getRsLabel());
		assertEquals("", form.getReview().getTitle());
		assertEquals("", form.getReview().getContent());
		assertEquals(0, form.getReview().getRating());
	}
	
	@Test
	public void shouldBuildNewReviewsWithPostedFormValues() {
		Review newReview = postedForm().buildReview(date("2012/09/02"), mockUserContext);
		
		assertNotNull("Review is null", newReview);
		assertEquals("My title", newReview.getTitle());
		assertEquals("My content", newReview.getContent());
		assertEquals(3, newReview.getRating());
		assertEquals(date("2012/09/02"), newReview.getPostedAt());
	}
	
	ReviewForm postedForm() {
		ReviewForm rf = new ReviewForm();
		rf.setRsLabel(rollingStock().getLabel());
		rf.setRsSlug(rollingStock().getSlug());
		
		Review r = new Review();
		r.setAuthor(loggedUser().getSlug());
		r.setTitle("My title");
		r.setContent("My content");
		r.setRating(3);
		rf.setReview(r);
		
		return rf;
	}
	
	Account loggedUser() { 
		return new Account.Builder("mail@mail.com")
			.displayName("Bob")
			.build();
	}
	
	RollingStock rollingStock() {
		return new RollingStock.Builder(acme(), "123456")
			.railway(fs())
			.scale(scaleH0())
			.description("Desc")
			.build();
	}

	RollingStock rollingStock2() {
		return new RollingStock.Builder(acme(), "123458")
			.railway(fs())
			.scale(scaleH0())
			.description("Desc")
			.build();
	}
}
