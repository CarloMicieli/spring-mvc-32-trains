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

import static com.trenako.test.TestDataBuilder.*;
import static org.mockito.Mockito.*;

import org.aspectj.lang.JoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.entities.Account;
import com.trenako.entities.Collection;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.Comment;
import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;
import com.trenako.entities.WishList;
import com.trenako.entities.WishListItem;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ActivityAspectTests {
	
	@Mock JoinPoint joinPoint;
	@Mock ActivityStream activityStream;
	private ActivityAspect aspect;
	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		aspect = new ActivityAspect(activityStream);
	}
	
	@Test
	public void shouldRecordCommentsPosting() {
		aspect.recordCommentPosting(joinPoint, rollingStock(), comment());
		verify(activityStream, times(1)).comment(eq(rollingStock()), eq(comment()));
	}

	@Test
	public void shouldRecordReviewsPosting() {
		aspect.recordReviewPosting(joinPoint, rollingStock(), review());
		verify(activityStream, times(1)).review(eq(rollingStock()), eq(review()));
	}
	
	@Test
	public void shouldRecordRollingStockCreation() {
		aspect.recordRollingStockCreation(joinPoint, rollingStock());
		verify(activityStream, times(1)).createRollingStock(eq(rollingStock()));
	}
	
	@Test
	public void shouldRecordRollingStockUpdate() {
		aspect.recordRollingStockUpdate(joinPoint, rollingStock());
		verify(activityStream, times(1)).changeRollingStock(eq(rollingStock()));
	}
	
	@Test
	public void shouldRecordCollectionChanges() {
		aspect.recordCollectionChange(joinPoint, user(), collectionItem());
		verify(activityStream, times(1)).collection(eq(user()), eq(collectionItem()));
	}
	
	@Test
	public void shouldRecordWishListChanges() {
		aspect.recordWishListChange(joinPoint, wishList(), wishListItem());
		verify(activityStream, times(1)).wishList(eq(wishList()), eq(wishListItem()));
	}
	
	RollingStock rollingStock() {
		 RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.scale(scaleH0())
			.railway(fs())
			.build();

		 rs.setModifiedBy("bob");
		 return rs;
	}

	Account user() {
		return new Account.Builder("mail@mail.com")
			.displayName("Bob")
			.build();
	}

	Comment comment() {
		return new Comment("bob", "my comment", date("2012/02/02"));
	}

	Review review() {
		Review r = new Review(user(), "title", "content", 4, null);
		r.setPostedAt(date("2012/02/02"));
		return r;
	}

	WishList wishList() {
		return new WishList(user(), "my list");
	}

	WishListItem wishListItem() {
		return new WishListItem(rollingStock());
	}

	Collection collection() {
		return new Collection(user());
	}

	CollectionItem collectionItem() {
		return new CollectionItem(rollingStock(), date("2012/02/02"));
	}
}
