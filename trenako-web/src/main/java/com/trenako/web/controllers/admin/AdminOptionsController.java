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
package com.trenako.web.controllers.admin;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Option;
import com.trenako.services.OptionsService;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.OptionFamily;
import com.trenako.web.controllers.ControllerMessage;
import com.trenako.web.controllers.form.OptionForm;

/**
 * @author Carlo Micieli
 */
@Controller
@RequestMapping("/admin/options")
public class AdminOptionsController {

    private final static Logger log = LoggerFactory.getLogger("trenako.web");

    static final ControllerMessage OPTION_CREATED_MSG = ControllerMessage.success("option.created.message");

    private final OptionsService service;
    private
    @Autowired
    MessageSource messageSource;

    @Autowired
    public AdminOptionsController(OptionsService service) {
        this.service = service;
    }

    @ModelAttribute("familiesList")
    public Iterable<LocalizedEnum<OptionFamily>> families() {
        return LocalizedEnum.list(OptionFamily.class, messageSource, null);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(ModelMap model) {
        model.addAttribute("couplerOptions", service.findByFamily(OptionFamily.COUPLER));
        model.addAttribute("headlightsOptions", service.findByFamily(OptionFamily.HEADLIGHTS));
        model.addAttribute("transmissionOptions", service.findByFamily(OptionFamily.TRANSMISSION));
        model.addAttribute("dccOptions", service.findByFamily(OptionFamily.DCC_INTERFACE));

        return "option/list";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newOption(ModelMap model) {
        model.addAttribute("newForm", new OptionForm());
        return "option/new";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(OptionForm form, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAtts) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("newForm", form);
            return "option/new";
        }

        Option option;
        try {
            option = form.buildOption();
            service.save(option);
            OPTION_CREATED_MSG.appendToRedirect(redirectAtts);
            return "redirect:/admin/options";

        } catch (IOException e) {
            log.error(e.toString());
        }

        model.addAttribute("newForm", form);
        return "option/new";
    }

}
