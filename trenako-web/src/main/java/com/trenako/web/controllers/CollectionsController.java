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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Account;
import com.trenako.entities.Collection;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.RollingStock;
import com.trenako.services.AccountsService;
import com.trenako.services.CollectionsService;
import com.trenako.services.RollingStocksService;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.Visibility;
import com.trenako.web.controllers.form.CollectionItemForm;
import com.trenako.web.errors.NotFoundException;
import com.trenako.web.infrastructure.LogUtils;
import com.trenako.web.security.UserContext;

/**
 * @author Carlo Micieli
 */
@Controller
@RequestMapping("/collections")
public class CollectionsController {

    private final static Logger log = LoggerFactory.getLogger("com.trenako.web");

    static final ControllerMessage COLLECTION_SAVED_MSG = ControllerMessage.success("collection.saved.message");
    static final ControllerMessage COLLECTION_DELETED_MSG = ControllerMessage.success("collection.deleted.message");

    private
    @Autowired(required = false)
    MessageSource messageSource;

    private final UserContext userContext;
    private final CollectionsService service;
    private final AccountsService usersService;
    private final RollingStocksService rsService;

    @Autowired
    public CollectionsController(CollectionsService service,
                                 RollingStocksService rsService,
                                 AccountsService usersService,
                                 UserContext userContext) {
        this.service = service;
        this.rsService = rsService;
        this.userContext = userContext;
        this.usersService = usersService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
    }

    @RequestMapping(value = "/{slug}", method = RequestMethod.GET)
    public String show(@PathVariable("slug") String slug, ModelMap model) {
        Collection collection = service.findBySlug(slug);
        if (collection == null) {
            throw new NotFoundException();
        }

        Account owner = usersService.findBySlug(collection.getOwner());

        model.addAttribute("collection", collection);
        model.addAttribute("owner", owner);
        return "collection/show";
    }

    @RequestMapping(value = "/{slug}/edit", method = RequestMethod.GET)
    public String editForm(@PathVariable("slug") String slug, ModelMap model) {
        Account owner = UserContext.authenticatedUser(userContext);
        Collection collection = service.findBySlug(slug);
        model.addAttribute("collection", collection);
        model.addAttribute("owner", owner);
        model.addAttribute("visibilities", getVisibilities(collection));
        return "collection/edit";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid @ModelAttribute Collection collection,
                         BindingResult bindingResult,
                         ModelMap model,
                         RedirectAttributes redirectAtts) {

        if (bindingResult.hasErrors()) {
            LogUtils.logValidationErrors(log, bindingResult);
            Account owner = UserContext.authenticatedUser(userContext);
            model.addAttribute("owner", owner);
            model.addAttribute("collection", collection);
            model.addAttribute("visibilities", getVisibilities(collection));
            return "collection/edit";
        }

        service.saveChanges(collection);
        COLLECTION_SAVED_MSG.appendToRedirect(redirectAtts);
        return "redirect:/you/collection";
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public String delete(@ModelAttribute Collection collection, RedirectAttributes redirectAtts) {
        service.remove(collection);
        COLLECTION_DELETED_MSG.appendToRedirect(redirectAtts);
        return "redirect:/you";
    }

    @RequestMapping(value = "/add/{slug}", method = RequestMethod.GET)
    public String addItemForm(@PathVariable("slug") String slug, ModelMap model) {

        RollingStock rs = rsService.findBySlug(slug);

        CollectionItemForm newForm = CollectionItemForm.newForm(rs, messageSource);
        model.addAttribute("itemForm", newForm);
        model.addAttribute("rs", rs);
        return "collection/add";
    }

    @RequestMapping(value = "/items", method = RequestMethod.POST)
    public String addItem(@Valid @ModelAttribute CollectionItemForm form,
                          BindingResult bindingResult,
                          ModelMap model,
                          RedirectAttributes redirectAtts) {

        RollingStock rs = rsService.findBySlug(form.getRsSlug());

        if (bindingResult.hasErrors()) {
            LogUtils.logValidationErrors(log, bindingResult);
            CollectionItemForm newForm = CollectionItemForm.newForm(rs, messageSource);
            model.addAttribute("itemForm", newForm);
            model.addAttribute("rs", rs);
            return "collection/add";
        }

        Account owner = userContext.getCurrentUser().getAccount();
        CollectionItem newItem = form.newItem(rs, owner);
        service.addRollingStock(owner, newItem);

        return "redirect:/you/collection";
    }

    @RequestMapping(value = "/items", method = RequestMethod.PUT)
    public String editItem(@Valid @ModelAttribute CollectionItemForm form,
                           BindingResult bindingResult,
                           ModelMap model,
                           RedirectAttributes redirectAtts) {

        Account owner = userContext.getCurrentUser().getAccount();

        if (bindingResult.hasErrors()) {
            LogUtils.logValidationErrors(log, bindingResult);
            return "redirect:/you/collection";
        }

        RollingStock rs = rsService.findBySlug(form.getRsSlug());
        CollectionItem item = form.editItem(rs, owner);
        service.updateItem(owner, item);

        return "redirect:/you/collection";
    }

    @RequestMapping(value = "/items", method = RequestMethod.DELETE)
    public String removeItem(@Valid @ModelAttribute CollectionItemForm form,
                             BindingResult bindingResult,
                             ModelMap model,
                             RedirectAttributes redirectAtts) {

        Account owner = userContext.getCurrentUser().getAccount();

        if (bindingResult.hasErrors()) {
            LogUtils.logValidationErrors(log, bindingResult);
            return "redirect:/you/collection";
        }

        RollingStock rs = rsService.findBySlug(form.getRsSlug());
        CollectionItem item = form.deletedItem(rs, owner);
        service.removeRollingStock(owner, item);

        return "redirect:/you/collection";
    }

    private HashMap<LocalizedEnum<Visibility>, String> getVisibilities(Collection c) {
        Iterable<LocalizedEnum<Visibility>> visibilities = LocalizedEnum.list(Visibility.class, messageSource, null);
        HashMap<LocalizedEnum<Visibility>, String> map =
                new LinkedHashMap<LocalizedEnum<Visibility>, String>(Visibility.values().length);

        for (LocalizedEnum<Visibility> val : visibilities) {
            String checked = val.getValue().equals(c.getVisibilityValue()) ? "checked" : "";
            map.put(val, checked);
        }

        return map;
    }

}
