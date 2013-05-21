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
import com.trenako.entities.Collection;
import com.trenako.values.Visibility;

/**
 * @author Carlo Micieli
 */
public class CollectionPermission extends Permission {

    @Override
    public boolean evaluate(Account user, Object targetObj, Object permissionType) {

        Collection collection = collection(targetObj);

        if (collection.isOwnedBy(user)) {
            return true;
        } else {
            return isReading(permissionType) && isPublic(collection);
        }
    }

    private boolean isPublic(Collection collection) {
        return collection.getVisibilityValue().equals(Visibility.PUBLIC);
    }

    private Collection collection(Object targetObj) {
        if (!(targetObj instanceof Collection)) {
            throw new IllegalArgumentException("Only Collection objects are valid");
        }

        return (Collection) targetObj;
    }

}
