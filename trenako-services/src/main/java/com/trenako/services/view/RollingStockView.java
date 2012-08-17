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
package com.trenako.services.view;

import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;
import com.trenako.entities.RollingStockReviews;

/**
 * It represents a {@code RollingStock} view, that includes comments and reviews too.
 * @author Carlo Micieli
 *
 */
public class RollingStockView {

	private final RollingStock rs;
	private final Iterable<Comment> comments;
	private final RollingStockReviews reviews;
	
	public RollingStockView(RollingStock rs, 
			Iterable<Comment> comments, 
			RollingStockReviews reviews) {
		this.rs = rs;
		this.comments = comments;
		this.reviews = reviews;
	}

	/**
	 * Returns the {@code RollingStock}.
	 * @return the {@code RollingStock}
	 */
	public RollingStock getRs() {
		return rs;
	}

	/**
	 * Returns the comments for the current {@code RollingStock}.
	 * @return the comments list
	 */
	public Iterable<Comment> getComments() {
		return comments;
	}

	/**
	 * Returns the reviews for the current {@code RollingStock}.
	 * @return the reviews list
	 */
	public RollingStockReviews getReviews() {
		return reviews;
	}
}
