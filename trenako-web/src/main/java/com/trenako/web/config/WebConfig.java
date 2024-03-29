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
package com.trenako.web.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.trenako.AppGlobals;
import com.trenako.format.IntegerAnnotationFormatterFactory;
import com.trenako.format.WeakDbRefFormatter;
import com.trenako.mapping.WeakDbRef;
import com.trenako.web.converters.ObjectIdConverter;
import com.trenako.web.infrastructure.RangeRequestArgumentResolver;
import com.trenako.web.infrastructure.SearchRequestArgumentResolver;

/**
 * The configuration class for the Spring MVC application.
 *
 * @author Carlo Micieli
 */
@Configuration
@EnableWebMvc
@Import(ApplicationConfig.class)
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //Handles HTTP GET requests for resources by efficiently serving
        //up static resources in the ${webappRoot}/resources directory
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ObjectIdConverter());
        registry.addFormatterForFieldType(WeakDbRef.class, new WeakDbRefFormatter());
        registry.addFormatterForFieldAnnotation(new IntegerAnnotationFormatterFactory());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableArgumentResolver resolver = new PageableArgumentResolver();
        resolver.setFallbackPagable(new PageRequest(1, 10));

        argumentResolvers.add(new ServletWebArgumentResolverAdapter(resolver));
        argumentResolvers.add(new RangeRequestArgumentResolver());
        argumentResolvers.add(new SearchRequestArgumentResolver());
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    // bean for files upload.
    // commons-fileupload is required in the classpath
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1024 * 512); // 512Kb
        return multipartResolver;
    }

    /**
     * The {@code bean} that resolves the locale by using a session attribute.
     *
     * @return a bean
     */
    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(AppGlobals.DEFAULT_LOCALE);
        return resolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }
}
