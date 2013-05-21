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
package com.trenako.repositories.mongo;

import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;
import com.trenako.entities.RollingStockReviews;
import com.trenako.mapping.WeakDbRef;
import com.trenako.repositories.ReviewsRepository;
import com.trenako.utility.Maps;

import static org.springframework.data.mongodb.core.query.Query.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;

/**
 * A concrete implementation for the user reviews repository for mongodb.
 *
 * @author Carlo Micieli
 */
@Repository("reviewsRepository")
public class ReviewsRepositoryImpl implements ReviewsRepository {

    private final MongoTemplate mongo;

    /**
     * Creates a new reviews repository for mongodb.
     *
     * @param mongo
     */
    @Autowired
    public ReviewsRepositoryImpl(MongoTemplate mongo) {
        this.mongo = mongo;
    }

    @Override
    public RollingStockReviews findById(ObjectId id) {
        return mongo.findById(id, RollingStockReviews.class);
    }

    @Override
    public RollingStockReviews findBySlug(String slug) {
        return mongo.findOne(query(where("slug").is(slug)), RollingStockReviews.class);
    }

    @Override
    public RollingStockReviews findByRollingStock(RollingStock rollingStock) {
        return mongo.findOne(query(where("rollingStock.slug").is(rollingStock.getSlug())),
                RollingStockReviews.class);
    }

    @Override
    public void addReview(RollingStock rs, Review review) {
        Update upd = new Update()
                .set("rollingStock", WeakDbRef.buildRef(rs))
                .push("items", review)
                .inc("numberOfReviews", 1)
                .inc("totalRating", review.getRating());
        mongo.upsert(query(where("slug").is(rs.getSlug())), upd, RollingStockReviews.class);
    }

    @Override
    public void removeReview(RollingStock rs, Review review) {
        Map<String, Object> m = Maps.map("author", (Object) review.getAuthor());

        Update upd = new Update()
                .pull("items", m)
                .inc("numberOfReviews", -1)
                .inc("totalRating", -1 * review.getRating());
        mongo.updateFirst(query(where("slug").is(rs.getSlug())), upd, RollingStockReviews.class);
    }

    @Override
    public void save(RollingStockReviews rsReviews) {
        mongo.save(rsReviews);
    }

    @Override
    public void remove(RollingStockReviews rsReviews) {
        mongo.remove(rsReviews);
    }
}
