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

import static com.trenako.web.controllers.ControllerMessage.*;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Pageable;
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

import com.trenako.entities.Scale;
import com.trenako.services.ScalesService;
import com.trenako.values.Standard;
import com.trenako.web.controllers.ControllerMessage;
import com.trenako.web.infrastructure.LogUtils;

/**
 * The administration controller for {@code Scale}s.
 *
 * @author Carlo Micieli
 */
@Controller
@RequestMapping("/admin/scales")
public class AdminScalesController {

    private static final Logger log = LoggerFactory.getLogger("com.trenako.web");

    private final ScalesService service;

    final static ControllerMessage SCALE_CREATED_MSG = success("scale.created.message");
    final static ControllerMessage SCALE_SAVED_MSG = success("scale.saved.message");
    final static ControllerMessage SCALE_DELETED_MSG = success("scale.deleted.message");
    final static ControllerMessage SCALE_DB_ERROR_MSG = error("scale.db.error.message");
    final static ControllerMessage SCALE_NOT_FOUND_MSG = error("scale.not.found.message");

    /**
     * Creates a new {@code AdminScalesController} controller.
     *
     * @param service the scales service
     */
    @Autowired
    public AdminScalesController(ScalesService scaleService) {
        this.service = scaleService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // allow empty string
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
    }

    /**
     * Returns the list of {@code Scale} standards.
     *
     * @return the standards list
     */
    @ModelAttribute("standards")
    public Iterable<String> standards() {
        return Standard.labels();
    }

    /**
     * It shows the {@code Scale}s list.
     * <p/>
     * <p>
     * <pre><blockquote>
     * {@code GET /admin/scales}
     * </blockquote></pre>
     * </p>
     *
     * @param paging the paging information
     * @param model  the model
     * @return the view name
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(Pageable paging, ModelMap model) {
        model.addAttribute("scales", service.findAll(paging));
        return "scale/list";
    }

    /**
     * It shows a {@code Scale}.
     * <p/>
     * <p>
     * <pre><blockquote>
     * {@code GET /admin/scales/:slug}
     * </blockquote></pre>
     * </p>
     *
     * @param slug         the {@code Scale} slug
     * @param model        the model
     * @param redirectAtts the redirect attributes
     * @return the view name
     */
    @RequestMapping(value = "/{slug}", method = RequestMethod.GET)
    public String show(@PathVariable("slug") String slug, ModelMap model, RedirectAttributes redirectAtts) {
        Scale scale = service.findBySlug(slug);
        if (scale == null) {
            SCALE_NOT_FOUND_MSG.appendToRedirect(redirectAtts);
            return "redirect:/admin/scales";
        }

        model.addAttribute(scale);
        return "scale/show";
    }

    /**
     * It shows the web form for {@code Scale} creation.
     * <p/>
     * <p>
     * <pre><blockquote>
     * {@code GET /admin/scales/new}
     * </blockquote></pre>
     * </p>
     *
     * @param model the model
     * @return the view name
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newForm(ModelMap model) {
        model.addAttribute("scale", new Scale());
        return "scale/new";
    }

    /**
     * It creates a new {@code Scale} using the posted form values.
     * <p/>
     * <p>
     * <pre><blockquote>
     * {@code POST /admin/scales}
     * </blockquote></pre>
     * </p>
     *
     * @param scale         the {@code Scale} to be added
     * @param bindingResult the validation results
     * @param model         the model
     * @param redirectAtts  the redirect attributes
     * @return the view name
     */
    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute Scale scale,
                         BindingResult bindingResult,
                         ModelMap model,
                         RedirectAttributes redirectAtts) {

        if (bindingResult.hasErrors()) {
            LogUtils.logValidationErrors(log, bindingResult);
            model.addAttribute(scale);
            return "scale/new";
        }

        try {
            service.save(scale);
            SCALE_CREATED_MSG.appendToRedirect(redirectAtts);
            return "redirect:/admin/scales";
        } catch (DuplicateKeyException dke) {
            LogUtils.logException(log, dke);
            bindingResult.rejectValue("name", "scale.name.already.used");
        } catch (DataAccessException dae) {
            LogUtils.logException(log, dae);
            SCALE_DB_ERROR_MSG.appendToModel(model);
        }

        model.addAttribute(scale);
        return "scale/new";
    }

    /**
     * This action shows the form to edit a {@code Scale}.
     * <p/>
     * <p>
     * <pre><blockquote>
     * {@code GET /admin/scales/:slug/edit}.
     * </blockquote></pre>
     * </p>
     *
     * @param slug         the {@code Scale} slug
     * @param model        the model
     * @param redirectAtts the redirect attributes
     * @return the view name
     */
    @RequestMapping(value = "/{slug}/edit", method = RequestMethod.GET)
    public String editForm(@PathVariable("slug") String slug, ModelMap model, RedirectAttributes redirectAtts) {
        Scale scale = service.findBySlug(slug);
        if (scale == null) {
            SCALE_NOT_FOUND_MSG.appendToRedirect(redirectAtts);
            return "redirect:/admin/scales";
        }

        model.addAttribute(scale);
        return "scale/edit";
    }

    /**
     * It updates the {@code Scale} using the posted form values.
     * <p/>
     * <p>
     * <pre><blockquote>
     * {@code POST /admin/scales}
     * </blockquote></pre>
     * </p>
     *
     * @param scale         the {@code Scale} to be updated
     * @param bindingResult the validation results
     * @param model         the model
     * @param redirectAtts  the redirect attributes
     * @return the view name
     */
    @RequestMapping(method = RequestMethod.PUT)
    public String save(@Valid @ModelAttribute Scale scale,
                       BindingResult bindingResult,
                       ModelMap model,
                       RedirectAttributes redirectAtts) {

        if (bindingResult.hasErrors()) {
            LogUtils.logValidationErrors(log, bindingResult);
            model.addAttribute(scale);
            return "scale/edit";
        }

        try {
            service.save(scale);

            redirectAtts.addFlashAttribute("message", SCALE_SAVED_MSG);
            return "redirect:/admin/scales";
        } catch (DataAccessException dae) {
            LogUtils.logException(log, dae);
            model.addAttribute("scale", scale);
            SCALE_DB_ERROR_MSG.appendToModel(model);
            return "scale/edit";
        }
    }

    /**
     * It deletes a {@code Scale}.
     * <p/>
     * <p>
     * <pre><blockquote>
     * {@code DELETE /admin/scales/:slug}
     * </blockquote></pre>
     * </p>
     *
     * @param slug         the {@code Scale} slug
     * @param redirectAtts the redirect attributes
     * @return the view name
     */
    @RequestMapping(value = "/{slug}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("slug") String slug, RedirectAttributes redirectAtts) {
        Scale scale = service.findBySlug(slug);
        if (scale == null) {
            SCALE_NOT_FOUND_MSG.appendToRedirect(redirectAtts);
            return "redirect:/admin/scales";
        }

        try {
            service.remove(scale);
            SCALE_DELETED_MSG.appendToRedirect(redirectAtts);
        } catch (DataAccessException dae) {
            LogUtils.logException(log, dae);
            SCALE_DB_ERROR_MSG.appendToRedirect(redirectAtts);
        }
        return "redirect:/admin/scales";
    }
}