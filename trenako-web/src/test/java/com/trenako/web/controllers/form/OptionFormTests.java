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

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.trenako.entities.Option;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class OptionFormTests {
	
	@Test
	public void shouldCreateNewForms() {
		
		OptionForm form = OptionForm.newForm();
		
		assertNotNull(form.getOption());
		assertEquals(new Option(), form.getOption());
	}
	
	@Test
	public void shouldReturnTheOptionObjectFilledWithFormValues() throws IOException {
		Option opt = new Option();
		opt.setName("optionName");
		opt.setFamily("optionFamily");
		
		OptionForm form = new OptionForm();
		form.setOption(opt);
		form.setFile(new MockMultipartFile("file.png", "content".getBytes()));
		
		Option option = form.buildOption();
		
		assertEquals("optionName", option.getName());
		assertEquals("optionFamily", option.getFamily());
		assertNotNull(option.getImage());
	}
}
