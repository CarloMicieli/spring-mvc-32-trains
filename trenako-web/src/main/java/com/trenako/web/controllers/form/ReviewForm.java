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
package com.trenako.web.controllers.form;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;

import com.trenako.entities.Account;
import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;
import com.trenako.web.security.UserContext;

/**
 * It represents the web form for {@code Review}s.
 *
 * @author Carlo Micieli
 */
public class ReviewForm {
    private final static Map<Integer, String> RATING_LISTS = ratingsList();

    @Valid
    private Review review;
    private RollingStock rs;
    private String rsSlug;
    private String rsLabel;

    public ReviewForm() {
    }

    private ReviewForm(RollingStock rs, Account author) {
        this.rs = rs;
        this.rsLabel = rs.getLabel();
        this.rsSlug = rs.getSlug();
        this.review = new Review(author, "", "", 0, null, null);
    }

    /**
     * Builds a new {@code ReviewForm}.
     *
     * @param rs          TODO
     * @param userContext the security content
     * @return the {@code ReviewForm}
     */
    public static ReviewForm newForm(RollingStock rs, UserContext userContext) {
        Account author = UserContext.authenticatedUser(userContext);
        if (author != null) {
            return new ReviewForm(rs, author);
        }
        return null;
    }

    /**
     * Creates a new {@code Review} from the posted values.
     *
     * @param postingDate the review posting date
     * @param userContext the security content
     * @return a new {@code Review}
     */
    public Review buildReview(Date postingDate, UserContext userContext) {
        if (getReview() == null) {
            return null;
        }

        Account author = UserContext.authenticatedUser(userContext);
        return new Review(author,
                getReview().getTitle(),
                getReview().getContent(),
                getReview().getRating(),
                postingDate
        );
    }

    public RollingStock getRs() {
        return rs;
    }

    public void setRs(RollingStock rs) {
        this.rs = rs;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public String getRsSlug() {
        return rsSlug;
    }

    public void setRsSlug(String rsSlug) {
        this.rsSlug = rsSlug;
    }

    public String getRsLabel() {
        return rsLabel;
    }

    public void setRsLabel(String rsLabel) {
        this.rsLabel = rsLabel;
    }

    public Map<Integer, String> getRatings() {
        return RATING_LISTS;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ReviewForm)) return false;

        ReviewForm other = (ReviewForm) obj;
        return new EqualsBuilder()
                .append(this.rsSlug, other.rsSlug)
                .append(this.rsLabel, other.rsLabel)
                .append(this.review, other.review)
                .isEquals();
    }

    private static Map<Integer, String> ratingsList() {
        Map<Integer, String> ratings = new HashMap<Integer, String>();
        for (int r = 1; r <= 5; r++) {
            ratings.put(r, StringUtils.repeat("*", r));
        }
        return ratings;
    }
}
