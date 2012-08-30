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

import static org.junit.Assert.*;
import static com.trenako.test.TestDataBuilder.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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
import com.trenako.mapping.WeakDbRef;
import com.trenako.repositories.ActivityRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ActivityStreamTests {
	
	private @Mock ActivityRepository repo;
	private ActivityStream activityStream;
	
	private ArgumentCaptor<Activity> arg = ArgumentCaptor.forClass(Activity.class);
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		activityStream = new ActivityStreamImpl(repo);
	}
	
	@Test
	public void shouldRecordActivityForCommentsPosting() {
		activityStream.comment(comment(), rollingStock());
		
		verify(repo, times(1)).save(arg.capture());
		Activity act = (Activity) arg.getValue();
		assertEquals(Activity.buildForComment(comment(), rsRef()), act);
	}
	
	@Test
	public void shouldRecordActivityForReviewsPosting() {
		activityStream.review(review(), rollingStock());
		
		verify(repo, times(1)).save(arg.capture());
		Activity act = (Activity) arg.getValue();
		assertEquals(Activity.buildForReview(review(), rsRef()), act);
	}
	
	@Test
	public void shouldRecordRollingStockCreation() {
		activityStream.createRollingStock(rollingStock());
		
		verify(repo, times(1)).save(arg.capture());
		Activity act = (Activity) arg.getValue();
		assertEquals(Activity.buildForRsCreate(rollingStock()), act);
	}
	
	@Test
	public void shouldRecordRollingStockChanges() {
		activityStream.changeRollingStock(rollingStock());
		
		verify(repo, times(1)).save(arg.capture());
		Activity act = (Activity) arg.getValue();
		assertEquals(Activity.buildForRsChange(rollingStock()), act);
	}
	
	@Test
	public void shouldRecordWishListChanges() {
		activityStream.wishList(wishList(), wishListItem());
		
		verify(repo, times(1)).save(arg.capture());
		Activity act = (Activity) arg.getValue();
		assertEquals(Activity.buildForWishList(wishList(), wishListItem()), act);
	}
	
	@Test
	public void shouldRecordCollectionChanges() {
		activityStream.collection(collection(), collectionItem());
		
		verify(repo, times(1)).save(arg.capture());
		Activity act = (Activity) arg.getValue();
		assertEquals(Activity.buildForCollection(collection(), collectionItem()), act);
	}
	
	WeakDbRef<RollingStock> rsRef() {
		return WeakDbRef.buildRef(rollingStock());
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
		Review r = new Review(user(), "title", "content", 4);
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
