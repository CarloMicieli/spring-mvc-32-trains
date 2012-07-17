package com.trenako.web.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.trenako.SearchCriteria;

/**
 * 
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
		
		JspWriter out = pageContext.getOut();
		
		try {
			out.println("<ul class=\"breadcrumb\">");

			if (criteria.hasBrand()) {
				appendCriteria(out, "brand", criteria.getBrand());
			}
			if (criteria.hasRailway()) {
				appendCriteria(out, "railway", criteria.getRailway());
			}
			if (criteria.hasEra()) {
				appendCriteria(out, "era", criteria.getEra());
			}
			if (criteria.hasScale()) {
				appendCriteria(out, "scale", criteria.getScale());
			}
			
			out.println("</ul>");
			
		} catch (IOException e) {
			throw new JspException(e);
		}
		
		return Tag.SKIP_BODY;
	}
	
	private void appendCriteria(JspWriter out, String key, String value) throws IOException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		StringBuilder url = new StringBuilder()
			.append(request.getContextPath())
			.append("/rs/")
			.append(key)
			.append("/")
			.append(value);
		
		String label = getMessage("rollingStock.brand.label", null, key);
		
		out.println("<li class=\"active\">" + label + " <span class=\"divider\">/</span></li>");
		out.println("<li>");
		out.println("<a title=\"Click here\" href=\"" + url.toString() + "\">" + value + "</a>");
		out.println(" <span class=\"divider\">/</span></li>");
	}
	
	protected String getMessage(String code, Object[] args, String defaultMessage) {
		WebApplicationContext springContext = 
			    WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
		return springContext.getMessage(code, args, defaultMessage, null);
	}
}
