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
package com.trenako.web.controllers.form;

import javax.validation.Valid;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.web.multipart.MultipartFile;

import com.trenako.entities.Railway;
import com.trenako.web.images.validation.Image;

/**
 * It represents the web form for railways. 
 * @author Carlo Micieli
 *
 */
public class RailwayForm {

	@Valid
	private Railway railway;
	
	@Image(message = "railway.logo.notValid")
	private MultipartFile file;	

	private RailwayForm(Railway railway) {
		this.railway = railway;
	}
	
	public RailwayForm(Railway railway, MultipartFile file) {
		this.railway = railway;
		this.file = file;
	}

	public static RailwayForm newForm(Railway railway) {
		return new RailwayForm(railway);
	}
	
	public Railway getRailway() {
		return railway;
	}

	public void setRailway(Railway railway) {
		this.railway = railway;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof RailwayForm)) return false;
		
		RailwayForm other = (RailwayForm) obj;
		return new EqualsBuilder()
			.append(this.railway, other.railway)
			.isEquals();
	}
}