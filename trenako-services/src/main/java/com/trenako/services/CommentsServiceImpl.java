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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;
import com.trenako.entities.RollingStockComments;
import com.trenako.repositories.CommentsRepository;

/**
 * The interface for the comments service.
 * @author Carlo Micieli
 *
 */
@Service("commentsService")
public class CommentsServiceImpl implements CommentsService {

	private final CommentsRepository repo;
	
	@Autowired
	public CommentsServiceImpl(CommentsRepository repo) {
		this.repo = repo;
	}

	@Override
	public RollingStockComments findByRollingStock(RollingStock rollingStock) {
		RollingStockComments rsc = repo.findByRollingStock(rollingStock);
		if (rsc == null) {
			return RollingStockComments.defaultRollingStockComments();
		}
		
		return rsc;
	}

	@Override
	public void postComment(RollingStock rs, Comment comment) {
		repo.createNew(rs, comment);
	}

	@Override
	public void postAnswer(RollingStock rs, Comment parent, Comment answer) {
		repo.createAnswer(rs, parent, answer);
	}

	@Override
	public void deleteComment(RollingStock rs, Comment comment) {
		repo.remove(rs, comment);		
	}

	@Override
	public void deleteAnswer(RollingStock rs, Comment parent, Comment answer) {
		repo.removeAnswer(rs, parent, answer);		
	}

}
