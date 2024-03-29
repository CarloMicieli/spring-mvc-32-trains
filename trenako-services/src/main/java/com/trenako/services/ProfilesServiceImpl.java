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

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenako.activities.Activity;
import com.trenako.activities.ActivityStream;
import com.trenako.entities.Account;
import com.trenako.entities.Collection;
import com.trenako.entities.WishList;
import com.trenako.repositories.CollectionsRepository;
import com.trenako.repositories.WishListsRepository;
import com.trenako.services.view.ProfileOptions;
import com.trenako.services.view.ProfileView;

/**
 * It represents the concrete implementation for the {@code ProfileService} interface.
 *
 * @author Carlo Micieli
 */
@Service("profilesService")
public class ProfilesServiceImpl implements ProfilesService {

    private final CollectionsRepository collections;
    private final WishListsRepository wishLists;
    private final ActivityStream activityStream;

    @Autowired
    public ProfilesServiceImpl(CollectionsRepository collections,
                               WishListsRepository wishLists,
                               ActivityStream activityStream) {

        this.collections = collections;
        this.wishLists = wishLists;
        this.activityStream = activityStream;
    }

    @Override
    public ProfileView findProfileView(Account owner) {

        Iterable<Activity> userActivity = activityStream.userActivity(owner, 10);

        Collection collection = collections.findByOwner(owner);
        if (collection == null) {
            collection = new Collection(owner);
        }

        List<WishList> lists = (List<WishList>) wishLists.findAllByOwner(owner, 10);
        if (lists == null) {
            lists = Collections.emptyList();
        }

        return new ProfileView(userActivity, collection, lists, ProfileOptions.DEFAULT);
    }
}
