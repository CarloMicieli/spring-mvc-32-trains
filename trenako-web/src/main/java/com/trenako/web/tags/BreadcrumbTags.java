package com.trenako.web.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.trenako.SearchCriteria;
import com.trenako.web.tags.html.HtmlTag;

import static com.trenako.web.tags.html.HtmlBuilder.*;

/**
 * It represent a "breadcrumb" tag.
 * <p>
 * Example usage:
 * <blockquote>
 * <pre>
 * <tk:breadcrumb criteria="${searchCriteria} />
 * </pre>
 * </blockquote>
 * </p>
 * 
 * @author Carlo Micieli
 *
 */
public class BreadcrumbTags extends TagSupport {
	
	private static final long serialVersionUID = 1L;

	private SearchCriteria criteria;
	
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		StringBuilder url = new StringBuilder();
		url.append(request.getContextPath());
		
		WebApplicationContext springContext = getSpringContext();
		JspWriter out = pageContext.getOut();
		
		try {
			
			List<HtmlTag> items = new ArrayList<HtmlTag>();
			if (criteria.hasBrand()) {
				items.add(listItem(request, springContext, "brand", criteria.getBrand()));
			}
			if (criteria.hasScale()) {
				items.add(listItem(request, springContext, "scale", criteria.getScale()));
			}
			if (criteria.hasCat()) {
				items.add(listItem(request, springContext, "cat", criteria.getCat().toString()));
			}
			if (criteria.hasPowerMethod()) {
				items.add(listItem(request, springContext, "powerMethod", criteria.getPowerMethod()));
			}
			if (criteria.hasCategory()) {
				items.add(listItem(request, springContext, "category", criteria.getCategory()));
			}
			if (criteria.hasRailway()) {
				items.add(listItem(request, springContext, "railway", criteria.getRailway()));
			}
			if (criteria.hasEra()) {
				items.add(listItem(request, springContext, "era", criteria.getEra()));
			}
			
			HtmlTag list = ul(tags(items))
					.cssClass("breadcrumb");
			out.println(list.build());
			
		} catch (IOException e) {
			throw new JspException(e);
		}
		
		return Tag.SKIP_BODY;
	}
	
	private HtmlTag listItem(HttpServletRequest request, WebApplicationContext springContext, String key, String value) {
		String path = new StringBuilder()
			.append("/rs/")
			.append(key)
			.append("/")
			.append(value).toString();

		String label = springContext.getMessage("breadcrumb."+key+".label", null, key, null);
		String title = springContext.getMessage("breadcrumb."+key+".title.label", null, key, null);
		
		 return snippet(
			li(
				plain(label + " "),
				span("/").cssClass("divider")
					).cssClass("active"),
			
			li(
				a(value).href(request, path).title(title),
				plain(" "),
				span("/").cssClass("divider")
					));
	}
		
	private WebApplicationContext getSpringContext() {
		WebApplicationContext springContext = 
			    WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
		return springContext;
	}
}
