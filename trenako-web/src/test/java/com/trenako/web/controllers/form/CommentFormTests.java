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
import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;
import com.trenako.security.AccountDetails;
import com.trenako.web.security.UserContext;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CommentFormTests {
	
	@Mock UserContext mockUserContext;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(mockUserContext.getCurrentUser()).thenReturn(new AccountDetails(loggedUser()));
	}
	
	@Test
	public void shouldCheckWhetherTwoCommentFormsAreEquals() {
		CommentForm x = CommentForm.newForm(rollingStock(), mockUserContext);
		CommentForm y = CommentForm.newForm(rollingStock(), mockUserContext);
		assertTrue("Comment forms are different", x.equals(x));
		assertTrue("Comment forms are different", x.equals(y));
	}
	
	@Test
	public void shouldCheckWhetherTwoCommentFormsAreDifferent() {
		CommentForm x = CommentForm.newForm(rollingStock(), mockUserContext);
		CommentForm y = CommentForm.newForm(rollingStock2(), mockUserContext);
		assertFalse("Comment forms are equals", x.equals(y));
	}
	
	@Test
	public void shouldReturnNullWhenCreatingNewCommentFormsForAnonymousUsers() {
		when(mockUserContext.getCurrentUser()).thenReturn(null);
		CommentForm form = CommentForm.newForm(rollingStock(), mockUserContext);
		assertNull("Comment form is not null", form);
	}
	
	@Test
	public void shouldCreateNewCommentFormsForAuthenticateUsers() {
		CommentForm form = CommentForm.newForm(rollingStock(), mockUserContext);
		
		assertNotNull("Comment form is null", form);
		assertEquals(rollingStock().getSlug(), form.getRsSlug());
		assertEquals(rollingStock().getLabel(), form.getRsLabel());
		
		assertNotNull("Comment is null", form.getComment());
		assertEquals(loggedUser().getSlug(), form.getComment().getAuthor());
		assertEquals("", form.getComment().getContent());
		assertNull("Posting date is not null", form.getComment().getPostedAt());
	}
	
	@Test
	public void shouldBuildCommentsUsingTheNewCommentFormValues() {
		Comment newComment = postedForm().buildComment(date("2012/09/01"));
		
		assertNotNull("Comment is null", newComment);
		assertEquals(loggedUser().getSlug(), newComment.getAuthor());
		assertEquals("My comment", newComment.getContent());
		assertEquals(date("2012/09/01"), newComment.getPostedAt());
		assertEquals("12-09-01-00-00-00_bob", newComment.getCommentId());
	}
	
	CommentForm postedForm() {
		Comment c = new Comment();
		c.setAuthor(loggedUser().getSlug());
		c.setContent("My comment");
		
		CommentForm cf = new CommentForm();
		cf.setRsLabel(rollingStock().getLabel());
		cf.setRsSlug(rollingStock().getSlug());
		cf.setComment(c);
		return cf;
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
