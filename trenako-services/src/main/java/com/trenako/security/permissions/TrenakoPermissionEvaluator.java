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
package com.trenako.security.permissions;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.trenako.entities.Account;
import com.trenako.security.AccountDetails;

/**
 * The custom permissions evaluator.
 *
 * @author Carlo Micieli
 */
@Component
public class TrenakoPermissionEvaluator implements PermissionEvaluator {

    private final PermissionsHolder holder;

    /**
     * Creates a new {@code TrenakoPermissionEvaluator}.
     */
    public TrenakoPermissionEvaluator() {
        this(new PermissionsHolder());
    }

    /**
     * Creates a new {@code TrenakoPermissionEvaluator}.
     *
     * @param holder the permissions holder
     */
    public TrenakoPermissionEvaluator(PermissionsHolder holder) {
        this.holder = holder;
    }

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Object targetDomainObject, Object permission) {

        Account user = user(authentication);
        if (user == null) {
            return false;
        }

        return holder.permission(targetDomainObject)
                .evaluate(user, targetDomainObject, permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Serializable targetId, String targetType, Object permission) {
        throw new UnsupportedOperationException();
    }

    private Account user(Authentication authentication) {
        Object user = authentication.getPrincipal();
        if (user instanceof Account) {
            return (Account) user;
        }

        if (user instanceof AccountDetails) {
            return ((AccountDetails) user).getAccount();
        }

        return null;
    }

}