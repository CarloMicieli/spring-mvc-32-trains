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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service("activityStream")
public class ActivityStreamImpl implements ActivityStream {

	private final ActivityRepository repo;
	
	@Autowired
	public ActivityStreamImpl(ActivityRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public void comment(Comment comment, RollingStock rs) {
		Activity act = Activity.buildForComment(comment, WeakDbRef.buildRef(rs));
		repo.save(act);
	}

	@Override
	public void review(Review review, RollingStock rs) {
		Activity act = Activity.buildForReview(review, WeakDbRef.buildRef(rs));
		repo.save(act);
	}

	@Override
	public void createRollingStock(RollingStock rs) {
		Activity act = Activity.buildForRsCreate(rs);
		repo.save(act);
	}

	@Override
	public void changeRollingStock(RollingStock rs) {
		Activity act = Activity.buildForRsChange(rs);
		repo.save(act);
	}

	@Override
	public void wishList(WishList wishList, WishListItem item) {
		Activity act = Activity.buildForWishList(wishList, item);
		repo.save(act);
	}

	@Override
	public void collection(Collection collection, CollectionItem item) {
		Activity act = Activity.buildForCollection(collection, item);
		repo.save(act);
	}
	
	@Override
	public Iterable<Activity> recentActivity(int numberOfItems) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Activity> userActivity(Account user, int numberOfItems) {
		// TODO Auto-generated method stub
		return null;
	}


}
