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

import com.trenako.SearchCriteria;
import com.trenako.web.tags.html.HtmlTag;
import com.trenako.web.test.AbstractSpringTagsTest;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CategoriesSearchTagsTests extends AbstractSpringTagsTest {

	CategoriesSearchTags tag;
	
	@Override
	protected void setupTag(PageContext pageContext, MessageSource messageSource) {
		tag = new CategoriesSearchTags();
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
			li("category").cssClass("nav-header"),
			snippet(li(a("steam-locomotives").href("/trenako-web", "/rs/railway/DB/category/steam-locomotives"))),
			snippet(li(a("diesel-locomotives").href("/trenako-web", "/rs/railway/DB/category/diesel-locomotives"))),
			snippet(li(a("electric-locomotives").href("/trenako-web", "/rs/railway/DB/category/electric-locomotives"))),
			snippet(li(a("railcars").href("/trenako-web", "/rs/railway/DB/category/railcars"))),
			snippet(li(a("electric-multiple-unit").href("/trenako-web", "/rs/railway/DB/category/electric-multiple-unit"))),
			snippet(li(a("freight-cars").href("/trenako-web", "/rs/railway/DB/category/freight-cars"))),
			snippet(li(a("passenger-cars").href("/trenako-web", "/rs/railway/DB/category/passenger-cars"))),
			snippet(li(a("train-sets").href("/trenako-web", "/rs/railway/DB/category/train-sets"))),
			snippet(li(a("starter-sets").href("/trenako-web", "/rs/railway/DB/category/starter-sets")))
				);
		
		String output = renderTag();
		assertEquals(html.toString(), output);
	}
	
	@Test
	public void shouldRenderNavigationListWhenEraAlreadySelected() throws JspException, UnsupportedEncodingException {
		SearchCriteria sc = new SearchCriteria.Builder()
			.category("electric-locomotives")
			.railway("DB")
			.build();
		
		setCriteria(sc);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		HtmlTag html = snippet(
				li("category").cssClass("nav-header"),
				li(a("electric-locomotives").href("#")).cssClass("active"),
				li("").cssClass("divider"),
				li(a("remove").href("/trenako-web/rs/railway/DB"))
			);
		
		String output = renderTag();
		assertEquals(html.toString(), output);
	}
}
