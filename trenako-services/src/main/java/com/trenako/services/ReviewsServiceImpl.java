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

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;
import com.trenako.entities.RollingStockReviews;
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
	public RollingStockReviews findBySlug(String slug) {
		return repo.findBySlug(slug);
	}

	@Override
	public RollingStockReviews findByRollingStock(RollingStock rollingStock) {
		return repo.findByRollingStock(rollingStock);
	}

	@Override
	public void postReview(RollingStock rs, Review review) {
		Assert.notNull(rs.getLabel(), "Rolling stock label required");
		Assert.notNull(rs.getSlug(), "Rolling stock slug required");
		
		if (review.getPostedAt() == null) {
			review.setPostedAt(new Date());
		}
		
		repo.addReview(rs, review);
	}

	@Override
	public void deleteReview(RollingStock rs, Review review) {
		repo.removeReview(rs, review);
	}
}
