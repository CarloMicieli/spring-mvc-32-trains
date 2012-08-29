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
package com.trenako.web.controllers;

import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;
import com.trenako.services.CommentsService;
import com.trenako.services.RollingStocksService;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CommentsControllerTests {

	@Mock BindingResult mockResult;
	@Mock RedirectAttributes mockRedirectAtts;
	@Mock CommentsService mockService;
	@Mock RollingStocksService mockRsService;
	private CommentsController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new CommentsController(mockService, mockRsService);
	}
	
	@Test
	public void shouldPostNewComments() {
		String slug = "rs-slug";
		ModelMap model = new ModelMap();
		
		when(mockResult.hasErrors()).thenReturn(false);
		when(mockRsService.findBySlug(eq(slug))).thenReturn(rollingStock());
		
		String redirect = controller.postComment(slug, comment(), mockResult, model, mockRedirectAtts);
		
		assertEquals("redirect:/rollingstocks/{slug}", redirect);
		verify(mockService, times(1)).postComment(eq(rollingStock()), eq(comment()));
		verify(mockRedirectAtts, times(1)).addFlashAttribute(eq("message"), eq(CommentsController.COMMENT_POSTED_MSG));
	}
	
	@Test
	public void shouldRedirectAfterValidationErrorsDuringCommentPosting() {
		when(mockResult.hasErrors()).thenReturn(true);
		String slug = "rs-slug";
		ModelMap model = new ModelMap();
		
		String redirect = controller.postComment(slug, comment(), mockResult, model, mockRedirectAtts);
		
		assertEquals("redirect:/rollingstocks/{slug}", redirect);
		assertEquals(comment(), (Comment) model.get("comment"));
		verify(mockService, times(0)).postComment(eq(rollingStock()), eq(comment()));
	}
	
	Comment comment() {
		Comment c = new Comment("bob", "my comment", date("2012/08/01"));
		return c;
	}
	
	RollingStock rollingStock() {
		return new RollingStock.Builder(acme(), "123456")
			.railway(fs())
			.scale(scaleH0())
			.description("Desc")
			.build();
	}
}
