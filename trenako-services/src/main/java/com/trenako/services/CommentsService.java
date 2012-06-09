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
import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;

/**
 * The interface for the rolling stock comments service.
 * <p>
 * The concrete classes that implements the {@code CommentsService} interface will provide
 * the following functionalities:
 * <ul>
 * <li>finds a {@link Comment} by id;</li>
 * <li>returns the {@link Comment} list by author;</li>
 * <li>returns the {@link Comment} list by rolling stock;</li>
 * <li>returns the last posted {@link Comment} list;</li>
 * <li>saves/removes a {@link Comment}.</li>
 * </ul>
 * </p>
 *
 * @author Carlo Micieli
 * @see com.trenako.entities.Comment
 */
public interface CommentsService {
	
	/**
	 * Finds the {@link Comment} with the provided id.
	 * @param id the unique id
	 * @return a {@code Comment} if found; {@code null} otherwise
	 */	
	Comment findById(ObjectId id);
	
	/**
	 * Returns the list of {@link Comment} with the same author id.
	 * <p>
	 * This method returns at most a number of items from {@link AppGlobals#MAX_RESULT_SET_SIZE}; 
	 * the results are sort by descending posted date. 
	 * </p>
	 *
	 * @param authorId the comments' author
	 * @return a {@code Comment} list
	 */	 
	Iterable<Comment> findByAuthor(Account author);

	/**
	 * Returns the list of {@link Comment} with the same author name.
	 * <p>
	 * This method is using the {@link Account#getSlug()} value as a search key.
	 * </p>
	 * <p>
	 * This method returns at most a number of items from {@link AppGlobals#MAX_RESULT_SET_SIZE}; 
	 * the results are sort by descending posted date. 
	 * </p>
	 *
	 * @param authorName the comments' author name
	 * @return a {@code Comment} list
	 */
	Iterable<Comment> findByAuthor(String authorName);
	
	/**
	 * Returns the list of {@link Comment} for the same rolling stock id.
	 * <p>
	 * This method returns at most a number of items from {@link AppGlobals#MAX_RESULT_SET_SIZE}; 
	 * the results are sort by descending posted date. 
	 * </p>
	 *
	 * @param rollingStockId the rolling stock
	 * @return the list of comments
	 */	
	Iterable<Comment> findByRollingStock(RollingStock rollingStock);

	/**
	 * Returns the list of {@link Comment} for the same rolling stock slug.
	 * <p>
	 * This method returns at most a number of items from {@link AppGlobals#MAX_RESULT_SET_SIZE}; 
	 * the results are sort by descending posted date. 
	 * </p>
	 *
	 * @param rsSlug the rolling stock slug
	 * @return the list of comments
	 */	
	Iterable<Comment> findByRollingStock(String rsSlug);
	
	/**
	 * Persists the {@link Comment} changes in the data store.
	 * <p>
	 * This method performs a "upsert": if the {@code Comment} is not present in the data store
	 * a new {@code Comment} is created; otherwise the method will update the existing {@code Comment}. 
	 * </p>	 
	 * @param comment the {@code Comment} to be saved
	 */
	void save(Comment comment);
	
	/**
	 * Removes a {@link Comment} from the data store.
	 * @param comment the {@code Comment} to be removed
	 */
	void remove(Comment comment);
}
