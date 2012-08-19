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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Comment;
import com.trenako.mapping.WeakDbRef;
import com.trenako.services.CommentsService;
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
	
	final static ControllerMessage COMMENT_POSTED_MSG = ControllerMessage.success("comment.posted.message");
	
	@Autowired
	public CommentsController(CommentsService service) {
		this.service = service;
	}
	
	// registers the custom property editors
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(WeakDbRef.class, new WeakDbRefPropertyEditor(true));
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postComment(@PathVariable("slug") String slug,
			@Valid @ModelAttribute Comment comment,
			BindingResult bindingResult,
			RedirectAttributes redirectAtts) {
		
		if (bindingResult.hasErrors()) {
			redirectAtts.addAttribute("newComment", comment);
			return "redirect:/rollingstocks/{slug}";
		}
		
		comment.setPostedAt(new Date());
		service.save(comment);
		
		COMMENT_POSTED_MSG.appendToRedirect(redirectAtts);
		return "redirect:/rollingstocks/{slug}";
	}
}
