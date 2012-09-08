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

import static org.junit.Assert.*;
import static com.trenako.test.TestDataBuilder.*;

import java.util.Arrays;

import org.junit.Test;

import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;
import com.trenako.entities.RollingStockComments;
import com.trenako.entities.RollingStockReviews;
import com.trenako.entities.WishList;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RollingStockViewTests {

	@Test
	public void shouldCreateNewRollingStockViews() {
		RollingStockView rsView = new RollingStockView(rollingStock(), comments(), reviews(), wishLists());
		
		assertEquals(rollingStock(), rsView.getRs());
		assertEquals(comments().getItems(), rsView.getComments());
		assertEquals(reviews(), rsView.getReviews());
		assertEquals(wishLists(), rsView.getWishLists());
	}
	
	Iterable<WishList> wishLists() {
		return Arrays.asList(new WishList(), new WishList());
	}
	
	RollingStock rollingStock() { 
		return new RollingStock.Builder(acme(), "123456")
			.railway(fs())
			.scale(scaleH0())
			.build();
	}
	
	RollingStockComments comments() {
		RollingStockComments comments = new RollingStockComments(rollingStock());
		comments.setItems(Arrays.asList(new Comment(), new Comment()));
		return comments;
	}
	
	RollingStockReviews reviews() {
		RollingStockReviews reviews = new RollingStockReviews(rollingStock());
		return reviews;
	}
}
