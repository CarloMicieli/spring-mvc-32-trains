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

import com.trenako.entities.Account;
import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;

/**
 * The interface for the reviews repository.
 * 
 * @author Carlo Micieli
 *
 */
public interface ReviewsRepository {

	/**
	 * Finds the review by its unique id.
	 * @param id the review id
	 * @return the review if found; <em>null</em> otherwise
	 */
	Review findById(ObjectId id);
	
	/**
	 * Finds the list of reviews with the same author.
	 * @param author the reviews' author
	 * @return the list of reviews
	 */
	Iterable<Review> findByAuthor(Account author);

	/**
	 * Finds the list of reviews with the same author name.
	 * @param authorName the reviews' author name
	 * @return the list of reviews
	 */
	Iterable<Review> findByAuthor(String authorName);
	
	/**
	 * Finds the list of reviews for a rolling stock.
	 * 
	 * This method returns the last 10 reviews ordered by
	 * descending posted date. 
	 * 
	 * @param rollingStock the rolling stock
	 * @return the list of reviews
	 */	
	Iterable<Review> findByRollingStock(RollingStock rollingStock);

	/**
	 * Finds the list of reviews for the provided rolling stock slug.
	 * 
	 * This method returns the last 10 reviews ordered by
	 * descending posted date. 
	 * 
	 * @param rsSlug the rolling stock slug
	 * @return the list of reviews
	 */	
	Iterable<Review> findByRollingStock(String rsSlug);
	
	/**
	 * Saves the review.
	 * @param review the review to be saved
	 */
	void save(Review review);
	
	/**
	 * Removes the review.
	 * @param review the review to be removed
	 */
	void remove(Review review);	
}
