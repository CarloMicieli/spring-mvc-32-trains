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

import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;
import com.trenako.entities.RollingStockReviews;

/**
 * The interface for the rolling stock reviews service.
 *
 * @author Carlo Micieli
 */ 
public interface ReviewsService {
	
	/**
	 * Finds the {@code RollingStockReviews} with the provided slug.
	 * @param slug the slug
	 * @return a {@code RollingStockReviews} if found; {@code null} otherwise
	 */
	RollingStockReviews findBySlug(String slug);
		
	/**
	 * Returns the {@code RollingStockReviews} for the provided rolling stock.
	 *
	 * @param rollingStock the rolling stock
	 * @return a {@code RollingStockReviews} if found; {@code null} otherwise
	 */	
	RollingStockReviews findByRollingStock(RollingStock rollingStock);

	/**
	 * Posts a new user review for the provided {@code RollingStock}.
	 *
	 * @param rs the {@code RollingStock} under review
	 * @param review the review to be saved	 
	 */
	void postReview(RollingStock rs, Review review);
	
	/**
	 * Deletes a review for the provided {@code RollingStock}.
	 *
	 * @param rs the {@code RollingStock} under review
	 * @param review the review to be deleted	 
	 */
	void deleteReview(RollingStock rs, Review review);	
}
