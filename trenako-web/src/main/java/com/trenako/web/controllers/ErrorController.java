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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.trenako.web.infrastructure.LogUtils;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

	private static final Logger log = LoggerFactory.getLogger("com.trenako.web");
	
	@RequestMapping(value = "/denied")
	public String denied() {
		return "error/denied";
	}
	
	@RequestMapping(value = "/notfound")
	public String notFound() {
		return "error/notfound";
	}
	
	@RequestMapping(value = "/server-error")
	public ModelAndView resolveException(HttpServletRequest request) {
		Exception ex = (Exception) request.getAttribute("javax.servlet.error.exception");
		LogUtils.logException(log, ex);
		
		if (isLocalhost(request)) {
			String error = ExceptionUtils.getStackTrace(ex);
			ModelAndView debugView = new ModelAndView("error/debug");
			debugView.addObject("error", error);
			debugView.addObject("request", request);
			return debugView;
		}
		else {
			return new ModelAndView("error/error");
		}
	}

	private final static boolean isLocalhost(HttpServletRequest request) {
		if (request == null) return false;
	
		return request.getRemoteAddr().equals("localhost") || 
			request.getRemoteAddr().equals("127.0.0.1");
	}
}
