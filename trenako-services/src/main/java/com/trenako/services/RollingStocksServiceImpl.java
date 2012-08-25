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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;
import com.trenako.entities.RollingStockReviews;
import com.trenako.repositories.CommentsRepository;
import com.trenako.repositories.ReviewsRepository;
import com.trenako.repositories.RollingStocksRepository;
import com.trenako.services.view.RollingStockView;

/**
 * A concrete implementation for the {@code RollingStocks} service.
 * @author Carlo Micieli
 *
 */
@Service("rollingStocksService")
public class RollingStocksServiceImpl implements RollingStocksService {
	
	private final RollingStocksRepository rollingStocks;
	private final CommentsRepository comments;
	private final ReviewsRepository reviews;
	
	/**
	 * Creates a {@code RollingStocksServiceImpl}
	 * @param rollingStocks the {@code RollingStock}s repository
	 * @param comments the {@code Comment}s repository
	 */
	@Autowired
	public RollingStocksServiceImpl(RollingStocksRepository rollingStocks,
			CommentsRepository comments,
			ReviewsRepository reviews) {
		
		this.rollingStocks = rollingStocks;
		this.comments = comments;
		this.reviews = reviews;
	}
	
	@Override
	public RollingStock findById(ObjectId id) {
		return rollingStocks.findOne(id);
	}
	
	@Override
	public RollingStock findBySlug(String slug) {
		return rollingStocks.findBySlug(slug);
	}
	
	@Override
	public RollingStockView findViewBySlug(String slug) {
		RollingStock rs = rollingStocks.findBySlug(slug);
		if (rs == null) {		
			return null;
		}
		
		Iterable<Comment> commentsList = comments.findByRollingStock(rs);
		RollingStockReviews rsReviews = reviews.findByRollingStock(rs);
		
		return new RollingStockView(rs, 
				commentsList,
				rsReviews);		
	}

	@Override
	public void createNew(RollingStock rs) {
		rollingStocks.save(rs);
	}
	
	@Override
	public void save(RollingStock rs) {
		Assert.notNull(rs.getId(), "Rolling stock id is required during update");
		rollingStocks.save(rs);
	}

	@Override
	public void remove(RollingStock rs) {
		rollingStocks.delete(rs);
	}
}
