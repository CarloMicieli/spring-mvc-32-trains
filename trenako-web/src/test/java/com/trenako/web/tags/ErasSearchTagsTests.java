package com.trenako.web.tags;

import static com.trenako.web.tags.html.HtmlBuilder.a;
import static com.trenako.web.tags.html.HtmlBuilder.li;
import static com.trenako.web.tags.html.HtmlBuilder.snippet;
import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.junit.Test;
import org.springframework.context.MessageSource;

import com.trenako.criteria.SearchCriteria;
import com.trenako.web.tags.html.HtmlTag;
import com.trenako.web.test.AbstractSpringTagsTest;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ErasSearchTagsTests extends AbstractSpringTagsTest {

	ErasSearchTags tag;
	
	@Override
	protected void setupTag(PageContext pageContext, MessageSource messageSource) {
		tag = new ErasSearchTags();
		tag.setPageContext(pageContext);
		tag.setMessageSource(messageSource);		
	}

	private void setCriteria(SearchCriteria sc) {
		SearchBarTags parent = new SearchBarTags();
		parent.setCriteria(sc);
		tag.setParent(parent);
	}
	
	@Test
	public void shouldRenderErasNavigationList() throws JspException, UnsupportedEncodingException {
		SearchCriteria sc = new SearchCriteria.Builder()
			.railway("DB")
			.build();
		
		setCriteria(sc);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		HtmlTag html = snippet(
			li("era").cssClass("nav-header"),
			snippet(li(a("I").href("/trenako-web", "/rs/railway/DB/era/I"))),
			snippet(li(a("II").href("/trenako-web", "/rs/railway/DB/era/II"))),
			snippet(li(a("III").href("/trenako-web", "/rs/railway/DB/era/III"))),
			snippet(li(a("IV").href("/trenako-web", "/rs/railway/DB/era/IV"))),
			snippet(li(a("V").href("/trenako-web", "/rs/railway/DB/era/V"))),
			snippet(li(a("VI").href("/trenako-web", "/rs/railway/DB/era/VI")))
				);
		
		String output = renderTag();
		assertEquals(html.toString(), output);
	}
	
	@Test
	public void shouldRenderNavigationListWhenEraAlreadySelected() throws JspException, UnsupportedEncodingException {
		SearchCriteria sc = new SearchCriteria.Builder()
			.era("III")
			.railway("DB")
			.build();
		
		setCriteria(sc);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		HtmlTag html = snippet(
				li("era").cssClass("nav-header"),
				li(a("III").href("#")).cssClass("active"),
				li("").cssClass("divider"),
				li(a("remove").href("/trenako-web/rs/railway/DB"))
			);
		
		String output = renderTag();
		assertEquals(html.toString(), output);
	}
}
