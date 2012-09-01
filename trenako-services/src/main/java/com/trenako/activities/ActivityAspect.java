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
package com.trenako.activities;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trenako.entities.RollingStock;
import com.trenako.entities.Comment;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Aspect
@Component
public class ActivityAspect {
	
	private final ActivityStream activityStream;
	
	@Autowired
	public ActivityAspect(ActivityStream activityStream) {
		this.activityStream = activityStream;
	}
	
	//
	// pointcuts
	
	@Pointcut(
			"execution(* com.trenako.services.CommentsService.postComment(..)) && args(rs, comment)")
	protected void postComment(RollingStock rs, Comment comment) {}
	
	//
	// AOP advises
	
	@AfterReturning("postComment(rs, comment)")
	public void recordCommentPosting(JoinPoint jp, RollingStock rs, Comment comment) {
		activityStream.comment(rs, comment);
	}
}
