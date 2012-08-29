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
import com.trenako.entities.Comment;
import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;

/**
 * It represents the interface for the {@code Activity} stream.
 * @author Carlo Micieli
 *
 */
public interface ActivityStream {

	/**
	 * Saves the provided {@code Activity}.
	 * @param activity the {@code Activity} to be saved
	 */
	void recordActivity(Activity activity);

	void comment(Comment c, RollingStock rs);
	void review(Review c, RollingStock rs);
	void createRollingStock(RollingStock rs);
	void updateRollingStock(RollingStock rs);

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
