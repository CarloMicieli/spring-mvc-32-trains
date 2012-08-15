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
package com.trenako.web.controllers;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Map;

import org.junit.Test;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ControllerMessageTests {

	@Test
	public void shouldCreateMessagesWithArguments() {
		ControllerMessage info = ControllerMessage.info("info message", 1, "arg2");
		assertEquals("info message", info.getMessage());
		assertEquals("[1, arg2]", Arrays.toString(info.getArgs()));
	}
	
	@Test
	public void shouldCreateMessageUsingTheBootstrapThemeNames() {
		ControllerMessage info = ControllerMessage.info("message");
		assertEquals("info", info.getType());
		
		ControllerMessage success = ControllerMessage.success("message");
		assertEquals("success", success.getType());
		
		ControllerMessage error = ControllerMessage.error("message");
		assertEquals("error", error.getType());
	}
	
	@Test
	public void shouldCheckWheterTwoMessagesAreEquals() {
		ControllerMessage x = ControllerMessage.info("info message", 1, "arg2");
		ControllerMessage y = ControllerMessage.info("info message", 1, "arg2");
		assertTrue("Messages are different", x.equals(x));
		assertTrue("Messages are different", x.equals(y));
	}
	
	@Test
	public void shouldCheckWheterTwoMessagesAreDifferent() {
		ControllerMessage x = ControllerMessage.info("info message", 1, "arg2");
		ControllerMessage y = ControllerMessage.info("info message");
		assertFalse("Messages are equals", x.equals(y));

		ControllerMessage z = ControllerMessage.info("info message", "arg2", 1);
		assertFalse("Messages are equals", x.equals(z));
	}
	
	@Test
	public void shouldProduceStringFromControllerMessages() {
		ControllerMessage x = ControllerMessage.info("info message", 1, "arg2");
		assertEquals("message{type: info, message: info message, args: [1, arg2]}", x.toString());
		
		ControllerMessage y = ControllerMessage.info("info message");
		assertEquals("message{type: info, message: info message, args: []}", y.toString());
	}
	
	@Test
	public void shouldAppendItselfToRedirectAttributes() {
		RedirectAttributes redirectAtts = new RedirectAttributesModelMap();
		ControllerMessage msg = ControllerMessage.info("info message", 1, "arg2");
		
		msg.appendToRedirect(redirectAtts);
		
		Map<String, ?> flashMap = redirectAtts.getFlashAttributes();
		assertTrue("Map don't contain the message", flashMap.containsKey("message"));
		assertEquals("message{type: info, message: info message, args: [1, arg2]}", 
				flashMap.get("message").toString());
	}
}
