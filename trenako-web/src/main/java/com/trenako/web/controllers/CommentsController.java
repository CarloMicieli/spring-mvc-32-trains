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

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.RollingStock;
import com.trenako.mapping.WeakDbRef;
import com.trenako.services.CommentsService;
import com.trenako.services.RollingStocksService;
import com.trenako.web.controllers.form.CommentForm;
import com.trenako.web.editors.WeakDbRefPropertyEditor;

/**
 * It represents the controller for rolling stock comments.
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/rollingstocks/{slug}/comments")
public class CommentsController {

	private final CommentsService service;
	private final RollingStocksService rsService;
	
	final static ControllerMessage COMMENT_POSTED_MSG = ControllerMessage.success("comment.posted.message");
	
	// for testing
	private Date now;
	protected void setTimestamp(Date now) {
		this.now = now;
	}

	private Date now() {
		if (now == null) {
			return new Date();
		}
		return now;
	}
	
	@Autowired
	public CommentsController(CommentsService service, RollingStocksService rsService) {
		this.service = service;
		this.rsService = rsService;
	}
	
	// registers the custom property editors
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(WeakDbRef.class, new WeakDbRefPropertyEditor(true));
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postComment(@ModelAttribute @Valid CommentForm commentForm,
			BindingResult bindingResult,
			ModelMap model,
			RedirectAttributes redirectAtts) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute(commentForm);
			return "redirect:/rollingstocks/{slug}";
		}
		
		RollingStock rs = rsService.findBySlug(commentForm.getRsSlug());
		service.postComment(rs, commentForm.buildComment(now()));
		
		COMMENT_POSTED_MSG.appendToRedirect(redirectAtts);
		return "redirect:/rollingstocks/{slug}";
	}
}
