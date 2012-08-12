///*
// * Copyright 2012 the original author or authors.
// * 
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// * 
// *   http://www.apache.org/licenses/LICENSE-2.0
// * 
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.trenako.web.controllers;
//
//import static org.mockito.Matchers.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;
//
//import org.bson.types.ObjectId;
//import org.junit.After;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.trenako.web.errors.NotFoundException;
//import com.trenako.web.images.WebImageService;
//import com.trenako.web.test.AbstractSpringControllerTests;
//
///**
// * 
// * @author Carlo Micieli
// *
// */
//public class ImagesControllerMappingTests extends AbstractSpringControllerTests {
//	private @Autowired WebImageService mockService;
//	
//	private final static String ID = "47cc67093475061e3d95369d";
//	private final static ObjectId OID = new ObjectId(ID);
//	
//	@After
//	public void cleanUp() {
//		// TODO code smell
//		// waiting 3.2 <https://jira.springsource.org/browse/SPR-9493>
//		reset(mockService);
//	}
//	
//	@Test
//	public void shouldReturn404WhenTheBrandLogoNotFound() throws Exception {
//		when(mockService.renderImageFor(eq(OID))).thenThrow(new NotFoundException());
//		mockMvc().perform(get("/images/brand/{brandId}", ID))
//			.andExpect(status().isNotFound());
//	}
//}
