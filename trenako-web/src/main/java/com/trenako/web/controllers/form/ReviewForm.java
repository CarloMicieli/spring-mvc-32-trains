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
package com.trenako.web.controllers.form;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;

import com.trenako.entities.Account;
import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ReviewForm {
	private final static Map<Integer, String> RATING_LISTS = ratingsList();

	@Valid
	private Review review;
	private RollingStock rs;
	private Account author;
	
	public ReviewForm() {
	}
	
	public RollingStock getRs() {
		return rs;
	}

	public void setRs(RollingStock rs) {
		this.rs = rs;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public Account getAuthor() {
		return author;
	}

	public void setAuthor(Account author) {
		this.author = author;
	}

	public Map<Integer, String> getRatings() {
		return RATING_LISTS;
	}
	
	private static Map<Integer, String> ratingsList() {
		Map<Integer, String> ratings = new HashMap<Integer, String>();
		for (int r = 1; r <= 5; r++) {
			ratings.put(r, StringUtils.repeat("*", r));
		}
		return ratings;
	}
	
	
}
