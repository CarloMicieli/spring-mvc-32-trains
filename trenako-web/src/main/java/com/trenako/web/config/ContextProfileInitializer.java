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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.ConfigurableWebApplicationContext;

/**
 * It implements the interface to define the Spring profile in use.
 *
 * @author Carlo Micieli
 */
public class ContextProfileInitializer
        implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {

    private static final Logger log = LoggerFactory.getLogger("com.trenako.web");

    private CloudEnvironment cloudFoundryEnvironment;

    public ContextProfileInitializer() {
        this.cloudFoundryEnvironment = new CloudEnvironment();
    }

    @Override
    public void initialize(ConfigurableWebApplicationContext context) {
        ConfigurableEnvironment environment = context.getEnvironment();

        if (cloudFoundryEnvironment.isOpenShift()) {
            environment.setActiveProfiles("cloud");
        } else {
            log.info("Not running on Cloud Foundry, using 'default' profile");
            environment.setActiveProfiles("default");
        }
    }
}
