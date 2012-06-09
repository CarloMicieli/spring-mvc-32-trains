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

import com.trenako.entities.Account;
import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;

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
	 * Finds the {@link Review} with the provided id.
	 * @param id the unique id
	 * @return a {@code Review} if found; {@code null} otherwise
	 */
	Review findById(ObjectId id);
	
	/**
	 * Returns the list of {@link Review} with the same author id.
	 * <p>
	 * This method returns at most a number of items from {@link AppGlobals#MAX_RESULT_SET_SIZE}; 
	 * the results are sort by descending posted date. 
	 * </p>
	 *
	 * @param authorId the reviews' author
	 * @return a {@code Review} list
	 */	
	Iterable<Review> findByAuthor(Account author);

	/**
	 * Returns the list of {@link Review} with the same author name.
	 * <p>
	 * This method is using the {@link Account#getSlug()} value as a search key.
	 * </p>
	 * <p>
	 * This method returns at most a number of items from {@link AppGlobals#MAX_RESULT_SET_SIZE}; 
	 * the results are sort by descending posted date. 
	 * </p>
	 *
	 * @param authorName the reviews' author name
	 * @return a {@code Review} list
	 */
	Iterable<Review> findByAuthor(String authorName);
	
	/**
	 * Returns a list of {@link Review} for the same rolling stock.
	 * <p>
	 * This method returns at most a number of items from {@link AppGlobals#MAX_RESULT_SET_SIZE}; 
	 * the results are sort by descending posted date. 
	 * </p>
	 *
	 * @param rollingStock the rolling stock
	 * @return a {@code Review} list
	 */	
	Iterable<Review> findByRollingStock(RollingStock rollingStock);

	/**
	 * Returns a list of {@link Review} for the same rolling stock slug.
	 * <p>
	 * This method returns at most a number of items from {@link AppGlobals#MAX_RESULT_SET_SIZE}; 
	 * the results are sort by descending posted date. 
	 * </p>
	 *
	 * @param rsSlug the rolling stock slug
	 * @return a {@code Review} list
	 */	
	Iterable<Review> findByRollingStock(String rsSlug);
	
	/**
	 * Persists the {@link Review} changes in the data store.
	 * <p>
	 * This method performs a "upsert": if the {@code Review} is not present in the data store
	 * a new {@code Review} is created; otherwise the method will update the existing {@code Review}. 
	 * </p>	 
	 * @param comment the {@code Review} to be saved
	 */
	void save(Review review);
	
	/**
	 * Removes a {@link Review} from the data store.
	 * @param comment the {@code Review} to be removed
	 */
	void remove(Review review);	
}
