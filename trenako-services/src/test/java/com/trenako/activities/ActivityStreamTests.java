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

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

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
	
	private @Mock Page<Activity> results;
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
		activityStream.comment(rollingStock(), comment());
		
		verify(repo, times(1)).save(arg.capture());
		Activity act = (Activity) arg.getValue();
		assertEquals(Activity.buildForComment(comment(), rsRef()), act);
	}
	
	@Test
	public void shouldRecordActivityForReviewsPosting() {
		activityStream.review(rollingStock(), review());
		
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
	public void shouldRecordRollingStocksAddedToCollections() {
		activityStream.collection(user(), collectionItem());
		
		verify(repo, times(1)).save(arg.capture());
		Activity act = (Activity) arg.getValue();
		assertEquals(Activity.buildForCollection(collection(), collectionItem()), act);
	}
	
	@Test
	public void shouldReturnTheLastRecordedActivities() {
		int numberOfItems = 10;
		Pageable paging = new PageRequest(0, numberOfItems, Direction.DESC, "recorded");
		List<Activity> items = Collections.emptyList();
		
		when(results.hasContent()).thenReturn(true);
		when(results.getContent()).thenReturn(items);
		when(repo.findAll(eq(paging))).thenReturn(results);
		
		Iterable<Activity> stream = activityStream.recentActivity(numberOfItems);
		
		assertNotNull("Activity result is empty", stream);
		ArgumentCaptor<Pageable> arg = ArgumentCaptor.forClass(Pageable.class);
		verify(repo, times(1)).findAll(arg.capture());
		
		assertEquals(0, arg.getValue().getPageNumber());
		assertEquals(10, arg.getValue().getPageSize());
		assertEquals("recorded: DESC", arg.getValue().getSort().toString());
	}
	
	@Test
	public void shouldReturnTheLastRecordedActivitiesForTheProvidedActor() {
		int numberOfItems = 10;
		Pageable paging = new PageRequest(0, numberOfItems, Direction.DESC, "recorded");
		List<Activity> items = Collections.emptyList();
		
		when(repo.findByActor(eq(user().getSlug()), eq(paging))).thenReturn(items);
		
		Iterable<Activity> stream = activityStream.userActivity(user(), numberOfItems);
		
		assertNotNull("Activity result is empty", stream);
		ArgumentCaptor<Pageable> arg = ArgumentCaptor.forClass(Pageable.class);
		verify(repo, times(1)).findByActor(eq(user().getSlug()), arg.capture());
		
		assertEquals(0, arg.getValue().getPageNumber());
		assertEquals(10, arg.getValue().getPageSize());
		assertEquals("recorded: DESC", arg.getValue().getSort().toString());
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
