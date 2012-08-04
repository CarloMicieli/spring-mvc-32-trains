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
package com.trenako.repositories;

import org.bson.types.ObjectId;

import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;
import com.trenako.entities.RollingStockReviews;

/**
 * The interface for the {@code RollingStock} reviews repository.
 * 
 * @author Carlo Micieli
 *
 */
public interface ReviewsRepository {

	/**
	 * Finds the {@code RollingStockReviews} with the provided unique id.
	 *
	 * @param id the review id
	 * @return the {@code RollingStockReviews} if found; {@code null} otherwise
	 */
	RollingStockReviews findById(ObjectId id);
	
	/**
	 * Finds the {@code RollingStockReviews} with the provided slug.
	 *
	 * @param slug the review slug
	 * @return the {@code RollingStockReviews} if found; {@code null} otherwise
	 */
	RollingStockReviews findBySlug(String slug);
	
	/**
	 * Finds the {@code RollingStockReviews} for the provided {@code RollingStock}.
	 *
	 * @param rollingStock the rolling stock
	 * @return the list of reviews
	 */	
	RollingStockReviews findByRollingStock(RollingStock rollingStock);
		
	/**
	 * Adds a new user review to the provided {@code RollingStock}.
	 *
	 * @param rs the {@code RollingStock} under review
	 * @param review the review to be saved	 
	 */
	void addReview(RollingStock rs, Review review);
	
	/**
	 * Removes the review from the provided {@code RollingStock}.
	 *
	 * @param rs the {@code RollingStock} under review
	 * @param review the review to be removed	 
	 */
	void removeReview(RollingStock rs, Review review);
	
	/**
	 * Saves all the reviews for the {@code RollingStock}.
	 * @param rsReview the rolling stock reviews
	 */
	void save(RollingStockReviews rsReviews);
	
	/**
	 * Removes all the reviews for the {@code RollingStock}.
	 * @param rsReview the rolling stock reviews
	 */
	void remove(RollingStockReviews rsReviews);
}