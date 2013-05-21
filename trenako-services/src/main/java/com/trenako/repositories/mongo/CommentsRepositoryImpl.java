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

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;
import com.trenako.entities.RollingStockComments;
import com.trenako.repositories.CommentsRepository;

import static org.springframework.data.mongodb.core.query.Query.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;

/**
 * The concrete implementation for the comments mongodb repository.
 *
 * @author Carlo Micieli
 */
@Repository("commentsRepository")
public class CommentsRepositoryImpl implements CommentsRepository {

    private final MongoTemplate mongo;

    /**
     * Creates a new mongodb repository for comments.
     *
     * @param mongo the mongodb template
     */
    @Autowired
    public CommentsRepositoryImpl(MongoTemplate mongo) {
        this.mongo = mongo;
    }

    // for testing
    private Date now = null;

    protected void setCurrentTimestamp(Date now) {
        this.now = now;
    }

    protected Date now() {
        if (now == null) {
            return new Date();
        }
        return now;
    }

    @Override
    public RollingStockComments findByRollingStock(RollingStock rollingStock) {
        Query query = query(where("slug").is(rollingStock.getSlug()));
        return mongo.findOne(query, RollingStockComments.class);
    }

    @Override
    public void createNew(RollingStock rs, Comment comment) {
        RollingStockComments rsc = new RollingStockComments(rs);
        Comment c = new Comment(comment.getAuthor(), comment.getContent(), now());

        Update upd = new Update()
                .set("rollingStock", rsc.getRollingStock())
                .inc("numberOfComments", 1)
                .push("items", c);

        mongo.upsert(query(where("slug").is(rsc.getSlug())), upd, RollingStockComments.class);
    }

    @Override
    public void createAnswer(RollingStock rs, Comment comment, Comment answer) {
        RollingStockComments rsc = new RollingStockComments(rs);
        Comment a = new Comment(answer.getAuthor(), answer.getContent(), now());

        Update upd = new Update()
                .inc("numberOfComments", 1)
                .push("items.$.answers", a);

        Query where = query(where("slug").is(rsc.getSlug())
                .and("items.commentId").is(comment.getCommentId()));

        mongo.updateFirst(where, upd, RollingStockComments.class);
    }

    @Override
    public void remove(RollingStock rs, Comment comment) {
        RollingStockComments rsc = new RollingStockComments(rs);
        Comment c = new Comment(comment.getCommentId());

        Update upd = new Update()
                .inc("numberOfComments", -1)
                .pull("items", c);

        Query where = query(where("slug").is(rsc.getSlug()));

        mongo.updateFirst(where, upd, RollingStockComments.class);
    }

    @Override
    public void removeAnswer(RollingStock rs, Comment comment, Comment answer) {
        RollingStockComments rsc = new RollingStockComments(rs);
        Comment a = new Comment(answer.getCommentId());

        Update upd = new Update()
                .inc("numberOfComments", -1)
                .pull("items.$.answers", a);

        Query where = query(where("slug").is(rsc.getSlug())
                .and("items.commentId").is(comment.getCommentId()));

        mongo.updateFirst(where, upd, RollingStockComments.class);
    }
}