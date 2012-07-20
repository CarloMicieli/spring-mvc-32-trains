package com.trenako.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.trenako.Category;

/**
 * 
 * @author Carlo Micieli
 *
 */
@SuppressWarnings("serial")
public class CategoriesSearchTags extends SearchBarItemTags {

	@Override
	protected int writeTagContent(JspWriter jspWriter, String contextPath)
			throws JspException {
		try {
			Iterable<String> categories = null;
			if (!getCriteria().has("category")) {
				categories = Category.list();
			}
			
			jspWriter.append(render(categories, "category", contextPath).toString());
			
		} catch (IOException e) {
			throw new JspException(e);
		}
		
		return SKIP_BODY;
	}

}
