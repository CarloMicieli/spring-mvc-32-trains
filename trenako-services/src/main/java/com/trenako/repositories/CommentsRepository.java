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
package com.trenako.repositories;

import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;
import com.trenako.entities.RollingStockComments;

/**
 * The interface for the rolling stock comments repository.
 *
 * @author Carlo Micieli
 */
public interface CommentsRepository {

    /**
     * Finds the list of comments for a rolling stock.
     * <p/>
     * This method returns the comments ordered by
     * descending posted date.
     *
     * @param rollingStock the rolling stock
     * @return the list of comments
     */
    RollingStockComments findByRollingStock(RollingStock rollingStock);

    /**
     * Creates a new {@code Comment}.
     *
     * @param rs      the rolling stock under review
     * @param comment the comment to be saved
     */
    void createNew(RollingStock rs, Comment comment);

    /**
     * Creates a new {@code Comment}'s answer.
     *
     * @param rs      the rolling stock under review
     * @param comment the original {@code Comment}
     * @param answer  the answer
     */
    void createAnswer(RollingStock rs, Comment comment, Comment answer);


    /**
     * Removes the {@code Comment}.
     *
     * @param rs      the rolling stock under review
     * @param comment the comment to be removed
     */
    void remove(RollingStock rs, Comment comment);

    /**
     * Removes the {@code Comment}'s answer.
     *
     * @param rs      the rolling stock under review
     * @param comment the comment
     * @param answer  the answer to be removed
     */
    void removeAnswer(RollingStock rs, Comment comment, Comment answer);
}
