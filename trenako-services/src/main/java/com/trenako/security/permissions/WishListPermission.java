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

import com.trenako.entities.Account;
import com.trenako.entities.WishList;
import com.trenako.values.Visibility;

/**
 * @author Carlo Micieli
 */
public class WishListPermission extends Permission {

    @Override
    public boolean evaluate(Account user, Object targetObj, Object permissionType) {

        WishList list = wishList(targetObj);

        if (list.isOwnedBy(user)) {
            return true;
        } else {
            return isReading(permissionType) && isPublic(list);
        }
    }

    private boolean isPublic(WishList list) {
        return list.getVisibilityValue().equals(Visibility.PUBLIC);
    }

    private WishList wishList(Object targetObj) {
        if (!(targetObj instanceof WishList)) {
            throw new IllegalArgumentException("Only WishList objects are valid");
        }

        return (WishList) targetObj;
    }
}
