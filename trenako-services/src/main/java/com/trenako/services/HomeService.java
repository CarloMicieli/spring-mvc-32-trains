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
package com.trenako.services;

import com.trenako.entities.Account;
import com.trenako.services.options.HomepageOptions;
import com.trenako.services.view.HomeView;

/**
 * The interface for the homepage service.
 *
 * @author Carlo Micieli
 */
public interface HomeService {

    /**
     * Returns the content for the homepage.
     * <p>
     * This method will have two different behaviors whether the current user
     * is logged or not (i.e. this method will manage {@code null} logged user}.
     * </p>
     *
     * @param loggedUser the logged user, can be {@code null}
     * @return the homepage content
     */
    HomeView getHomeContent(Account loggedUser);

    /**
     * Returns the content for the homepage.
     * <p>
     * This method will have two different behaviors whether the current user
     * is logged or not (i.e. this method will manage {@code null} logged user}.
     * </p>
     *
     * @param loggedUser the logged user, can be {@code null}
     * @param options    the home content options
     * @return the homepage content
     */
    HomeView getHomeContent(Account loggedUser, HomepageOptions options);

}
