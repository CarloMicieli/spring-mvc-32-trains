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
import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;
import com.trenako.repositories.ReviewsRepository;

/**
 * The interface for the reviews repository.
 * @author Carlo Micieli
 *
 */
@Service("reviewsService")
public class ReviewsServiceImpl implements ReviewsService {

	private final ReviewsRepository repo;
	
	@Autowired
	public ReviewsServiceImpl(ReviewsRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public Review findById(ObjectId id) {
		return repo.findById(id);
	}

	@Override
	public Iterable<Review> findByAuthor(Account author) {
		return repo.findByAuthor(author);
	}

	@Override
	public Iterable<Review> findByAuthor(String authorName) {
		return repo.findByAuthor(authorName);
	}

	@Override
	public Iterable<Review> findByRollingStock(RollingStock rollingStock) {
		return repo.findByRollingStock(rollingStock);
	}

	@Override
	public Iterable<Review> findByRollingStock(String rsSlug) {
		return repo.findByRollingStock(rsSlug);
	}

	@Override
	public void save(Review review) {
		repo.save(review);
	}

	@Override
	public void remove(Review review) {
		repo.remove(review);
	}

}
