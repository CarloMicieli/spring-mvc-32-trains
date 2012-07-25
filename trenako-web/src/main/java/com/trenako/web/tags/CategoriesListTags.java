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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.Assert;

import com.trenako.criteria.SearchCriteria;
import com.trenako.entities.Brand;
import com.trenako.entities.Scale;
import com.trenako.services.ScalesService;
import com.trenako.utility.Cat;
import com.trenako.values.PowerMethod;
import com.trenako.web.tags.html.HtmlTag;

import static com.trenako.web.infrastructure.SearchCriteriaUrlBuilder.*;
import static com.trenako.web.tags.html.HtmlBuilder.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CategoriesListTags extends SpringTagSupport {
	private static final long serialVersionUID = 1L;

	private @Autowired MessageSource messageSource;
	private @Autowired ScalesService service;
	
	private String contextPath;
	private Brand brand;
	private int columns;
	
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	public Brand getBrand() {
		return brand;
	}
	
	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public int getColumns() {
		if (columns <= 0) return 3;
		return columns;
	}
	
	void setService(ScalesService service) {
		this.service = service;
	}
	
	void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@Override
	protected int writeTagContent(JspWriter jspWriter, String contextPath)
			throws JspException {
		
		Assert.notNull(getBrand(), "Brand is required");
		
		this.contextPath = contextPath;
		try {

			if (getBrand().getScales() == null || getBrand().getScales().size() == 0) {
				jspWriter.write(snippet(div().cssClass("row-fluid")).build());
				return SKIP_BODY;
			}
			
			List<HtmlTag> rows = new ArrayList<HtmlTag>(); 
			
			int numOfScales = getBrand().getScales().size();
			int numOfRows = (int) Math.ceil(numOfScales / (double) getColumns());

			String[] scales = (String[]) getBrand().getScales().toArray(new String[numOfScales]);
			
			for (int row = 0; row < numOfRows; row++) {   
				HtmlTag[] cols = new HtmlTag[getColumns()];
				
				for (int col = 0; col < getColumns(); col++) {
					int n = col + (getColumns() * row);
					if (scales.length > n) {
						String slug = scales[n];
						
						Scale scale = service.findBySlug(slug);
						if (scale == null) {
							continue;
						}
						
						cols[col] = div(buildScale(scale)).cssClass(spanClass());
					}
					// pad the rows with empty columns
					else {
						cols[col] = div().cssClass(spanClass());
					}
				}
			
				rows.add(div(cols).cssClass("row-fluid"));
			}
			
			jspWriter.write(snippet(tags(rows)).build());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return SKIP_BODY;
	}
	
	private HtmlTag buildScale(Scale scale) {
		List<HtmlTag> list = new ArrayList<HtmlTag>();
		
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand(getBrand())
			.scale(scale)
			.build();
		
		list.add(h2(scale.getLabel()));
		
		if (scale.getPowerMethods() == null || scale.getPowerMethods().size() == 0) {
			appendCategoryList(list, sc, PowerMethod.DC);
		}
		else {
			for (PowerMethod pm : PowerMethod.values()) {
				if (scale.getPowerMethods().contains(pm.label())) {
					appendCategoryList(list, sc, pm);
				}
			}
		}
		
		return snippet(tags(list));		
	}
	
	private void appendCategoryList(List<HtmlTag> list, SearchCriteria sc, PowerMethod pm) {
		List<HtmlTag> items = new ArrayList<HtmlTag>();
		Iterable<Cat> categories = Cat.list(pm, messageSource);
		for (Cat cat : categories) {
			items.add(li(a(cat.label()).href(contextPath, buildUrlAdding(sc, "cat", cat))));
		}
		
		list.add(ul(tags(items)).cssClass("unstyled"));
	}
	
	private String spanClass() {
		int i = 12 / getColumns();
		return "span" + i;
	}
}
