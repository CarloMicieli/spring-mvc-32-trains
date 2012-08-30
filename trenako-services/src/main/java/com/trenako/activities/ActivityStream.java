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
package com.trenako.activities;

import com.trenako.entities.Account;
import com.trenako.entities.Collection;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.Comment;
import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;
import com.trenako.entities.WishList;
import com.trenako.entities.WishListItem;

/**
 * The interface for the {@code Activity} stream.
 * @author Carlo Micieli
 *
 */
public interface ActivityStream {

	/**
	 * Records a new {@code Activity} for a user comment.
	 * @param comment the comment
	 * @param rs the rolling stock
	 */
	void comment(Comment comment, RollingStock rs);

	/**
	 * Records a new {@code Activity} for a user review.
	 * @param review the review
	 * @param rs the rolling stock
	 */
	void review(Review review, RollingStock rs);
	
	/**
	 * Records a new {@code Activity} for the rolling stock creation.
	 * @param rs the rolling stock
	 */
	void createRollingStock(RollingStock rs);
	
	/**
	 * Records a new {@code Activity} for the rolling stock modification.
	 * @param rs the rolling stock
	 */	
	void changeRollingStock(RollingStock rs);
	
	/**
	 * Records a new {@code Activity} for the rolling stock added to wish lists.
	 * @param wishList the wish list
	 * @param item the wish list item
	 */
	void wishList(WishList wishList, WishListItem item);
	
	/**
	 * Records a new {@code Activity} for the rolling stock added to collections.
	 * @param collection the collection
	 * @param item the collection item
	 */	
	void collection(Collection collection, CollectionItem item);

	/**
	 * Returns at most {@code numberOfItems} elements from the activity stream.
	 * @param numberOfItems the max number of returned items
	 * @return the {@code Activity} list
	 */
	Iterable<Activity> recentActivity(int numberOfItems);

	/**
	 * Returns at most {@code numberOfItems} elements from the activity stream
	 * for the provided user.
	 * @param user the user
	 * @param numberOfItems the max number of returned items
	 * @return the {@code Activity} list
	 */
	Iterable<Activity> userActivity(Account user, int numberOfItems);
}
