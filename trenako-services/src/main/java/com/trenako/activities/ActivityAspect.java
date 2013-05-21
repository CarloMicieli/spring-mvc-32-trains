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

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trenako.entities.Account;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Comment;
import com.trenako.entities.WishList;
import com.trenako.entities.WishListItem;

/**
 * @author Carlo Micieli
 */
@Aspect
@Component
public class ActivityAspect {

    private final ActivityStream activityStream;

    @Autowired
    public ActivityAspect(ActivityStream activityStream) {
        this.activityStream = activityStream;
    }

    //
    // pointcuts

    @Pointcut("execution(* com.trenako.services.CommentsService.postComment(..)) && args(rs, comment)")
    protected void postComment(RollingStock rs, Comment comment) {
    }

    @Pointcut("execution(* com.trenako.services.ReviewsService.postReview(..)) && args(rs, review)")
    protected void postReview(RollingStock rs, Review review) {
    }

    @Pointcut("execution(* com.trenako.services.RollingStocksService.createNew(..)) && args(rs)")
    protected void createRollingStock(RollingStock rs) {
    }

    @Pointcut("execution(* com.trenako.services.RollingStocksService.save(..)) && args(rs)")
    protected void updateRollingStock(RollingStock rs) {
    }

    @Pointcut("execution(* com.trenako.services.CollectionsService.addRollingStock(..)) && args(owner, item)")
    protected void addCollection(Account owner, CollectionItem item) {
    }

    @Pointcut("execution(* com.trenako.services.WishListsService.addItem(..)) && args(wishList, newItem)")
    protected void addWishlist(WishList wishList, WishListItem newItem) {
    }

    //
    // AOP advises

    @AfterReturning("postComment(rs, comment)")
    public void recordCommentPosting(JoinPoint jp, RollingStock rs, Comment comment) {
        activityStream.comment(rs, comment);
    }

    @AfterReturning("postReview(rs, review)")
    public void recordReviewPosting(JoinPoint jp, RollingStock rs, Review review) {
        activityStream.review(rs, review);
    }

    @AfterReturning("createRollingStock(rs)")
    public void recordRollingStockCreation(JoinPoint jp, RollingStock rs) {
        activityStream.createRollingStock(rs);
    }

    @AfterReturning("updateRollingStock(rs)")
    public void recordRollingStockUpdate(JoinPoint jp, RollingStock rs) {
        activityStream.changeRollingStock(rs);
    }

    @AfterReturning("addCollection(owner, item)")
    public void recordCollectionChange(JoinPoint jp, Account owner, CollectionItem item) {
        activityStream.collection(owner, item);
    }

    @AfterReturning("addWishlist(wishList, newItem)")
    public void recordWishListChange(JoinPoint jp, WishList wishList, WishListItem newItem) {
        activityStream.wishList(wishList, newItem);
    }
}
