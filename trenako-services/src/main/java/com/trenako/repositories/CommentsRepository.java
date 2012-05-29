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
import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;

/**
 * The interface for the comments repository.
 * @author Carlo Micieli
 *
 */
public interface CommentsRepository {
	/**
	 * Finds the comment by its unique id.
	 * @param id the comment id
	 * @return the comment if found; <em>null</em> otherwise
	 */
	Comment findById(ObjectId id);
	
	/**
	 * Finds the list of comments with the same author.
	 * @param author the comments' author
	 * @return the list of comments
	 */
	Iterable<Comment> findByAuthor(Account author);

	/**
	 * Finds the list of comments with the same author name.
	 * @param authorName the comments' author name
	 * @return the list of comments
	 */
	Iterable<Comment> findByAuthor(String authorName);
	
	/**
	 * Finds the list of comments for a rolling stock.
	 * 
	 * This method returns the comments ordered by
	 * descending posted date. 
	 * 
	 * @param rollingStock the rolling stock
	 * @return the list of comments
	 */	
	Iterable<Comment> findByRollingStock(RollingStock rollingStock);

	/**
	 * Finds the list of comments for the provided rolling stock slug.
	 * 
	 * This method returns the comments ordered by
	 * descending posted date. 
	 * 
	 * @param rsSlug the rolling stock slug
	 * @return the list of comments
	 */	
	Iterable<Comment> findByRollingStock(String rsSlug);
	
	/**
	 * Saves the comment.
	 * @param comment the comment to be saved
	 */
	void save(Comment comment);
	
	/**
	 * Removes the comment.
	 * @param comment the comment to be removed
	 */
	void remove(Comment comment);
}
