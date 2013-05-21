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

import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;
import com.trenako.entities.RollingStockComments;

/**
 * The interface for the rolling stock comments service.
 *
 * @author Carlo Micieli
 */
public interface CommentsService {

    /**
     * Returns the list of {@link Comment} for the same rolling stock id.
     *
     * @param rollingStock the rolling stock
     * @return the list of comments
     */
    RollingStockComments findByRollingStock(RollingStock rollingStock);

    /**
     * Posts a new {@code Comment} for the provided rolling stock.
     *
     * @param rs      the {@code RollingStock} to be commented
     * @param comment the {@code Comment} to be saved
     */
    void postComment(RollingStock rs, Comment comment);

    /**
     * Post an answer to a rolling stock {@code Comment}.
     *
     * @param rs     the {@code RollingStock} to be commented
     * @param parent the parent {@code Comment}
     * @param answer the answer to be saved
     */
    void postAnswer(RollingStock rs, Comment parent, Comment answer);

    /**
     * Posts a new {@code Comment} for the provided rolling stock.
     *
     * @param rs      the {@code RollingStock} to be commented
     * @param comment the {@code Comment} to be saved
     */
    void deleteComment(RollingStock rs, Comment comment);

    /**
     * Post an answer to a rolling stock {@code Comment}.
     *
     * @param rs     the {@code RollingStock} to be commented
     * @param parent the parent {@code Comment}
     * @param answer the answer to be saved
     */
    void deleteAnswer(RollingStock rs, Comment parent, Comment answer);
}
