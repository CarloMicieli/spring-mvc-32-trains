package com.trenako.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.trenako.Era;

/**
 * 
 * @author Carlo Micieli
 *
 */
@SuppressWarnings("serial")
public class ErasSearchTags extends SearchBarItemTags {

	@Override
	protected int writeTagContent(JspWriter jspWriter, String contextPath)
			throws JspException {
		try {
			Iterable<String> eras = null;
			if (!getCriteria().has("era")) {
				eras = Era.list();
			}
			
			jspWriter.append(render(eras, "era", contextPath).toString());
			
		} catch (IOException e) {
			throw new JspException(e);
		}
		
		return SKIP_BODY;
	}

}
