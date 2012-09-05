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

/**
 * It represents a user permission in the system.
 * @author Carlo Micieli
 *
 */
public abstract class Permission {

	/**
	 * The user has permission to {@code read} the resource.
	 */
	public final static String READ = "read";

	/**
	 * The user has permission to {@code write} the resource.
	 */
	public final static String WRITE = "write";
	
	/**
	 * The user has permission to {@code delete} the resource.
	 */
	public final static String DELETE = "delete";
		
	/**
	 * Checks whether the user has the appropriate permissions on 
	 * the provided {@code targetObj}.
	 * @param user the user
	 * @param targetObj the target object
	 * @param permissionType the permission type ({@code read}, {@code write}, {@code delete})
	 * @return {@code true} if the user has the permissions; {@code false} otherwise
	 */
	public abstract boolean evaluate(Account user, Object targetObj, Object permissionType);

	protected boolean isReading(Object permissionType) {
		return permissionType.equals(Permission.READ);
	}
	
	protected boolean isWriting(Object permissionType) {
		return permissionType.equals(Permission.WRITE);
	}
	
	protected boolean isDeleting(Object permissionType) {
		return permissionType.equals(Permission.DELETE);
	}
}
