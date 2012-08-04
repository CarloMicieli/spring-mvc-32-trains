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
 * <p>
 * The concrete classes that implements the {@code ReviewsService} interface will provide
 * the following functionalities:
 * <ul>
 * <li>finds a {@code Review} by id;</li>
 * <li>returns the {@code Review} list by author;</li>
 * <li>returns the {@code Review} list by rolling stock;</li>
 * <li>returns the {@code Review} list;</li>
 * <li>saves/removes a {@code Review}.</li>
 * </ul>
 * </p>
 *
 * @author Carlo Micieli
 * @see com.trenako.entities.Review
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
	 * Deletes the review from the provided {@code RollingStock}.
	 *
	 * @param rs the {@code RollingStock} under review
	 * @param review the review to be removed	 
	 */
	void deleteReview(RollingStock rs, Review review);	
}
