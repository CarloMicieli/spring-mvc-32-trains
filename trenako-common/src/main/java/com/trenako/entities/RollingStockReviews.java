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
package com.trenako.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.bson.types.ObjectId;

import java.util.Collections;
import java.util.List;
import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import com.trenako.mapping.WeakDbRef;

/**
 * It represents a list of user reviews for a rolling stock.
 *
 * @author Carlo Micieli
 */
@Document(collection = "reviews")
public class RollingStockReviews {

    private final static RollingStockReviews DEFAULT = new RollingStockReviews();

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String slug;

    @NotNull(message = "reviews.rollingStock.required")
    private WeakDbRef<RollingStock> rollingStock;

    @Valid
    private List<Review> items;

    @Range(min = 1, message = "reviews.numberOfReviews.range.notmet")
    private int numberOfReviews;

    @Range(min = 0, message = "reviews.totalRating.range.notmet")
    private int totalRating;

    /**
     * Creates a new empty {@code RollingStockReviews}.
     */
    public RollingStockReviews() {
    }

    /**
     * Creates a {@code RollingStockReviews} for the
     * provided rolling stock.
     *
     * @param rollingStock the rolling stock under review
     */
    public RollingStockReviews(RollingStock rollingStock) {
        this(rollingStock, 0, 0);
    }

    /**
     * Creates a {@code RollingStockReviews} for the
     * provided rolling stock.
     *
     * @param rollingStock    the rolling stock under review
     * @param numberOfReviews the number of reviews
     * @param totalRating     the total rating
     */
    public RollingStockReviews(RollingStock rollingStock, int numberOfReviews, int totalRating) {
        this.setRollingStock(rollingStock);
        this.slug = reviewsSlug(getRollingStock());
        this.numberOfReviews = numberOfReviews;
        this.totalRating = totalRating;
    }

    /**
     * Returns the default instance of {@code RollingStockReviews}.
     *
     * @return the default {@code RollingStockReviews}
     */
    public static RollingStockReviews defaultRollingStockReviews() {
        return DEFAULT;
    }

    /**
     * Returns the {@code RollingStockReviews} id.
     *
     * @return the unique id
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Sets the {@code RollingStockReviews} id.
     *
     * @param id the unique id
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * Returns the current {@code RollingStockReviews} slug.
     *
     * @return the slug
     */
    public String getSlug() {
        if (slug == null) {
            slug = reviewsSlug(getRollingStock());
        }
        return slug;
    }

    /**
     * Sets the current {@code RollingStockReviews} slug.
     *
     * @param slug the slug
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     * Returns the {@code RollingStock} currently under review.
     *
     * @return the rolling stock
     */
    public WeakDbRef<RollingStock> getRollingStock() {
        return rollingStock;
    }

    /**
     * Sets the {@code RollingStock} currently under review.
     *
     * @param rollingStock the rolling stock
     */
    public void setRollingStock(WeakDbRef<RollingStock> rollingStock) {
        this.rollingStock = rollingStock;
    }

    /**
     * Sets the {@code RollingStock} currently under review.
     *
     * @param rollingStock the rolling stock
     */
    public void setRollingStock(RollingStock rollingStock) {
        this.rollingStock = WeakDbRef.buildRef(rollingStock);
    }

    /**
     * Returns the list of rolling stock reviews.
     * <p>
     * The list of items is immutable.
     * </p>
     *
     * @return the reviews list
     */
    public List<Review> getItems() {
        if (items == null) {
            return Collections.emptyList();
        }
        return items;
    }

    /**
     * Sets the list of rolling stock reviews.
     *
     * @param items the reviews list
     */
    public void setItems(List<Review> items) {
        this.items = items;
    }

    /**
     * Returns the number of reviews for the {@code RollingStock}.
     *
     * @return the number of reviews
     */
    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    /**
     * Sets the number of reviews for the {@code RollingStock}.
     *
     * @param numberOfReviews the number of reviews
     */
    public void setNumberOfReviews(int numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    /**
     * Returns the total rating for the {@code RollingStock}.
     * <p>
     * This field contains the sum of all user review ratings for the
     * current {@code RollingStock}.
     * </p>
     *
     * @return the total rating
     */
    public int getTotalRating() {
        return totalRating;
    }

    /**
     * Sets the total rating for the {@code RollingStock}.
     *
     * @param totalRating the total rating
     */
    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    /**
     * Returns the average rating for the {@code RollingStock} currently under review.
     * <p>
     * The rating is {@code null} when no review exists for the current {@code RollingStock}.
     * </p>
     *
     * @return the average rating
     */
    public BigDecimal getRating() {
        if (getNumberOfReviews() <= 0) {
            return null;
        }
        return (new BigDecimal(getTotalRating()))
                .divide(new BigDecimal(getNumberOfReviews()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof RollingStockReviews)) return false;

        RollingStockReviews other = (RollingStockReviews) obj;
        return new EqualsBuilder()
                .append(this.rollingStock, other.rollingStock)
                .append(this.numberOfReviews, other.numberOfReviews)
                .append(this.totalRating, other.totalRating)
                .isEquals();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("review{rs: ")
                .append(getRollingStock().toString())
                .append(", no of review(s): ")
                .append(getNumberOfReviews())
                .append(", total rating: ")
                .append(getTotalRating())
                .append("}")
                .toString();
    }

    private static String reviewsSlug(WeakDbRef<RollingStock> rs) {
        return rs.getSlug();
    }
}