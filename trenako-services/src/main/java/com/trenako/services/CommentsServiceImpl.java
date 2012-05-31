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

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenako.entities.Account;
import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;
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
	public Comment findById(ObjectId id) {
		return repo.findById(id);
	}

	@Override
	public Iterable<Comment> findByAuthor(Account author) {
		return repo.findByAuthor(author);
	}

	@Override
	public Iterable<Comment> findByAuthor(String authorName) {
		return repo.findByAuthor(authorName);
	}

	@Override
	public Iterable<Comment> findByRollingStock(RollingStock rollingStock) {
		return repo.findByRollingStock(rollingStock);
	}

	@Override
	public Iterable<Comment> findByRollingStock(String rsSlug) {
		return repo.findByRollingStock(rsSlug);
	}

	@Override
	public void save(Comment comment) {
		repo.save(comment);
	}

	@Override
	public void remove(Comment comment) {
		repo.remove(comment);
	}

}
