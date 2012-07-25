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

import org.springframework.util.Assert;

import com.trenako.entities.Brand;
import com.trenako.web.tags.html.HtmlTag;
import static com.trenako.web.tags.html.HtmlBuilder.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CategoriesListTags extends SpringTagSupport {
	private static final long serialVersionUID = 1L;

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
	
	private String spanClass() {
		int i = 12 / getColumns();
		return "span" + i;
	}
	
	@Override
	protected int writeTagContent(JspWriter jspWriter, String contextPath)
			throws JspException {
		
		Assert.notNull(getBrand(), "Brand is required");
		
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
						String scale = scales[n];
						cols[col] = div(h2(scale)).cssClass(spanClass());
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

}
