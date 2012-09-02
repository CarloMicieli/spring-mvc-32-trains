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

import static com.trenako.test.TestDataBuilder.date;
import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

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

import com.trenako.activities.Activity;
import com.trenako.entities.Account;
import com.trenako.entities.Collection;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.RollingStock;
import com.trenako.services.AccountsService;
import com.trenako.web.test.AbstractSpringTagsTest;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ActivityTagsTests extends AbstractSpringTagsTest {

	private ActivityTags tag;
	private @Mock AccountsService accountsService;
	
	@Override
	protected void setupTag(PageContext pageContext, MessageSource messageSource) {
		MockitoAnnotations.initMocks(this);
		when(accountsService.findBySlug(eq("bob"))).thenReturn(user());
		
		tag = new ActivityTags();
		tag.setPageContext(pageContext);
		tag.setMessageSource(messageSource);
		tag.setAccountsService(accountsService);
	}

	@Test
	public void shouldRenderActivityTags() throws JspException, UnsupportedEncodingException {
		tag.setActivity(activity());
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		String output = renderTag();
		
		String expected = "\n<a href=\"/trenako-web/users/bob\">Bob</a> rs-insert "+ 
				"\n<a href=\"/trenako-web/rollingstocks/acme-123456\">ACME 123456</a>"+
				"\n<br/><strong>interval.days.one.label</strong>";
		assertEquals(expected, output);
	}
	
	@Test
	public void shouldRenderActivityWithContextTags() throws JspException, UnsupportedEncodingException {
		tag.setActivity(collectionActivity());
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		String output = renderTag();
		
		String expected = "\n<a href=\"/trenako-web/users/bob\">Bob</a> add-collection "+ 
				"\n<a href=\"/trenako-web/rollingstocks/acme-123456\">ACME 123456</a> collection"+
				"\n<br/><strong>interval.days.one.label</strong>";
		assertEquals(expected, output);
	}
	
	Activity activity() {
		return Activity.buildForRsCreate(rollingStock());
	}
	
	Activity collectionActivity() {
		return Activity.buildForCollection(collection(), collectionItem());
	}
		
	RollingStock rollingStock() {
		 RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.scale(scaleH0())
			.railway(fs())
			.build();
		 
		 rs.setModifiedBy("bob");
		 rs.setLastModified(date("2012/09/01"));
		 return rs;
	}
	
	Account user() {
		return new Account.Builder("mail@mail.com")
			.displayName("Bob")
			.build();
	}
	
	Collection collection() {
		return new Collection(user());
	}
	
	CollectionItem collectionItem() {
		return new CollectionItem(rollingStock(), date("2012/09/01"));
	}
}
