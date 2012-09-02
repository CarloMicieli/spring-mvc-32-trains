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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.UnsupportedEncodingException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.trenako.entities.Account;
import com.trenako.services.AccountsService;
import com.trenako.web.test.AbstractSpringTagsTest;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AvatarTagsTests extends AbstractSpringTagsTest {

	private final static String GRAVATAR_URL = "https://secure.gravatar.com/avatar/7905d373cfab2e0fda04b9e7acc8c879.jpg";
	
	private @Mock AccountsService accountsService;
	private AvatarTags tag;
	
	@Override
	protected void setupTag(PageContext pageContext, MessageSource messageSource) {
		MockitoAnnotations.initMocks(this);
		
		tag = new AvatarTags();
		tag.setPageContext(pageContext);
		tag.setAccountsService(accountsService);		
	}
	
	@Test
	public void shouldRenderEmptyTagWhenUserWasNotFound() throws JspException, UnsupportedEncodingException {
		String slug = "bob";
		when(accountsService.findBySlug(eq(slug))).thenReturn(null);
		
		tag.setUser(slug);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		String output = renderTag();
		assertEquals("", output);
	}
	
	@Test
	public void shouldRenderGravatarImages() throws JspException, UnsupportedEncodingException {
		String slug = "bob";
		when(accountsService.findBySlug(eq(slug))).thenReturn(user());
		
		tag.setUser(slug);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		String output = renderTag();
		String expected = "\n<img "+
				"height=\"48\" " +
				"width=\"48\" " +
				"src=\"" + GRAVATAR_URL + "?s=48&r=pg&d=mm&\" />";
		assertEquals(expected, output);
	}
	
	@Test
	public void shouldRenderGravatarImagesWithDifferentSize() throws JspException, UnsupportedEncodingException {
		int size = 64;
		String slug = "bob";
		when(accountsService.findBySlug(eq(slug))).thenReturn(user());
		
		tag.setUser(slug);
		tag.setSize(size);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		String output = renderTag();
		String expected = "\n<img "+
				"height=\""+ size + "\" " +
				"width=\""+ size + "\" " +
				"src=\"" + GRAVATAR_URL + "?s="+ size + "&r=pg&d=mm&\" />";
		assertEquals(expected, output);
	}
	
	@Test
	public void shouldRenderGravatarImagesWithUserDisplayName() throws JspException, UnsupportedEncodingException {
		String slug = "bob";
		when(accountsService.findBySlug(eq(slug))).thenReturn(user());
		
		tag.setUser(slug);
		tag.setShowName(true);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		String output = renderTag();
		String expected = "\n<img "+
				"height=\"48\" " +
				"width=\"48\" " +
				"src=\"" + GRAVATAR_URL + "?s=48&r=pg&d=mm&\" />" +
				"\n<br/>" +
				"\nBob";
		assertEquals(expected, output);
	}
	
	@Test
	public void shouldRenderImagesWithGravatarLink() throws JspException, UnsupportedEncodingException {
		String slug = "bob";
		when(accountsService.findBySlug(eq(slug))).thenReturn(user());
		
		tag.setUser(slug);
		tag.setShowGravatarLink(true);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		String output = renderTag();
		String expected = "\n<a href=\"http://gravatar.com/emails/\">" + 
				"\n<img "+
				"height=\"48\" " +
				"width=\"48\" " +
				"src=\"" + GRAVATAR_URL + "?s=48&r=pg&d=mm&\" />" +
				"\n</a>";
		assertEquals(expected, output);
	}
	
	Account user() { 
		return new Account.Builder("mail@mail.com")
			.displayName("Bob")
			.build();
	}
}
