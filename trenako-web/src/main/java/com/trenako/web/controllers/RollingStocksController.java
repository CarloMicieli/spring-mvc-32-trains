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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;
import com.trenako.services.RollingStocksService;
import com.trenako.services.SelectOptionsService;
import com.trenako.web.errors.NotFoundException;
import com.trenako.web.images.WebImageService;
import com.trenako.web.infrastructure.BrandPropertyEditor;
import com.trenako.web.infrastructure.RailwayPropertyEditor;
import com.trenako.web.infrastructure.ScalePropertyEditor;

/**
 * It represents the rolling stocks management controller.
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/rollingstocks")
public class RollingStocksController {

	private final RollingStocksService service;
	private final SelectOptionsService soService;
	private final WebImageService imgService;
	
	/**
	 * Creates a new {@code RollingStocksController}.
	 * @param service 
	 * @param soService the service used to fill the drop down lists
	 */
	@Autowired
	public RollingStocksController(RollingStocksService service, 
			SelectOptionsService soService,
			WebImageService imgService) {
		this.service = service;
		this.soService = soService;
		this.imgService = imgService;
	}
	
	// registers the custom property editors
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Brand.class, new BrandPropertyEditor(true));
		binder.registerCustomEditor(Railway.class, new RailwayPropertyEditor(true));
		binder.registerCustomEditor(Scale.class, new ScalePropertyEditor(true));
	}
	
	@RequestMapping(value = "/{slug}", method = RequestMethod.GET)
	public String show(@PathVariable("slug") String slug, ModelMap model) {
		RollingStock rs = service.findBySlug(slug);
		if ( rs==null ) {
			throw new NotFoundException();
		}
		
		model.addAttribute("rollingStock", rs);
		return "rollingstock/show";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView createNew() {
		ModelAndView mav = new ModelAndView("rollingstock/new");
		mav.addObject(new RollingStock());
		
		// fills the data for the drop down lists.
		fillDropdownLists(mav);
		
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)	
	public String create(@ModelAttribute("rollingStock") @Valid RollingStock rs, 
			BindingResult bindingResult,
			@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAtts) {
		
		if (bindingResult.hasErrors()) {
			redirectAtts.addAttribute(rs);
			return "rollingstocks/new";
		}
		
		rs.setBrand(soService.findBrand(rs.getBrand().getId()));
		
		service.save(rs);
		if (!file.isEmpty()) {
			imgService.saveImage(rs.getId(), file);
		}

		redirectAtts.addFlashAttribute("message", "rolling.stock.created");
		redirectAtts.addAttribute("slug", rs.getSlug());
		return "redirect:/rollingstocks/{slug}";
	}

	@RequestMapping(value = "/{slug}/edit", method = RequestMethod.GET)
	public ModelAndView editForm(@PathVariable("slug") String slug) {
		ModelAndView mav = new ModelAndView("rollingstock/edit");
		RollingStock rs = service.findBySlug(slug);
		if ( rs==null ) {
			throw new NotFoundException();
		}
		
		mav.addObject("rollingStock", rs);
		
		// fills the data for the drop down lists.
		fillDropdownLists(mav);
		
		return mav;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String save(@ModelAttribute("rollingStock") @Valid RollingStock rs, 
			BindingResult bindingResult,
			RedirectAttributes redirectAtts) {
		
		if (bindingResult.hasErrors()) {
			redirectAtts.addAttribute("rollingStock", rs);
			return "rollingstock/edit";
		}
		
		redirectAtts.addFlashAttribute("message", "rolling.stock.saved");
		return "redirect:/rollingstocks/{slug}";
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public String delete(@ModelAttribute("rollingStock") RollingStock rs, 
			RedirectAttributes redirectAtts) {
		
		service.remove(rs);
		return "redirect:/rollingstocks";
	}
	
	private void fillDropdownLists(ModelAndView mav) {
		mav.addObject("brands", soService.brands());
		mav.addObject("scales", soService.scales());
		mav.addObject("railways", soService.railways());
		mav.addObject("categories", soService.categories());
		mav.addObject("eras", soService.eras());
		mav.addObject("powerMethods", soService.powerMethods());
	}
}
