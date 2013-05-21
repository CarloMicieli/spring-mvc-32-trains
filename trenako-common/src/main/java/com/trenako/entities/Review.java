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

import java.util.Date;
import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * It represents a user review for a rolling stock.
 * <p/>
 * <p>
 * The {@code RollingStock} under review is stored in the reviews container only.
 * </p>
 *
 * @author Carlo Micieli
 * @see com.trenako.entities.RollingStockReviews
 */
public class Review {
    @NotNull(message = "review.author.required")
    private String author;

    @NotBlank(message = "review.title.required")
    private String title;

    @NotBlank(message = "review.content.required")
    private String content;

    private String lang;

    @Range(min = 0, max = 5, message = "review.rating.range.notmet")
    private int rating;

    private Date postedAt;

    /**
     * Creates an empty {@code Review}.
     */
    public Review() {
    }

    /**
     * Creates a new {@code Review} for a rolling stock model using the default Locale.
     *
     * @param author   the review's author
     * @param title    the review title
     * @param content  the review content
     * @param rating   the rating value
     * @param postedAt the posting date
     */
    public Review(Account author, String title, String content, int rating, Date postedAt) {
        this(author, title, content, rating, postedAt, null);
    }

    /**
     * Creates a new {@code Review} for a rolling stock model.
     *
     * @param author   the review's author
     * @param title    the review title
     * @param content  the review content
     * @param rating   the rating value
     * @param postedAt the posting date
     * @param lang     the user's locale
     */
    public Review(Account author, String title, String content, int rating, Date postedAt, Locale lang) {
        this.author = authorSlug(author);
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.postedAt = postedAt;
        this.lang = language(lang);
    }

    /**
     * Returns the language used for the current {@code Review}.
     *
     * @return the language code
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the language used for the current {@code Review}.
     *
     * @param lang the language code
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * Sets the review's author name.
     *
     * @return the author's name
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the {@code Review}'s author name.
     *
     * @param author the author name
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns the {@code Review} title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the {@code Review} title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the {@code Review}'s content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the {@code Review}'s content.
     *
     * @param content the content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns the {@code Review} numeric rating.
     *
     * @return the rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the {@code Review} numeric rating.
     * <p>
     * The rating value is included in the range between 0 and 5.
     * </p>
     *
     * @param rating the rating
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Returns the date when the {@code Review} has been posted.
     *
     * @return the posting date
     */
    public Date getPostedAt() {
        return postedAt;
    }

    /**
     * Sets the date when the {@code Review} has been posted.
     *
     * @param postedAt the posting date
     */
    public void setPostedAt(Date postedAt) {
        this.postedAt = postedAt;
    }

    /**
     * Returns the first character from the {@code Review} content.
     *
     * @return the summary
     */
    public String getSummary() {
        if (getContent() == null) return null;

        int len = getContent().length();
        if (len < 150) {
            return getContent();
        } else {
            return getContent().substring(0, 150).concat("..");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Review)) return false;

        Review other = (Review) obj;
        return new EqualsBuilder()
                .append(this.content, other.content)
                .append(this.author, other.author)
                .append(this.title, other.title)
                .append(this.rating, other.rating)
                .isEquals();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(getAuthor())
                .append(": ")
                .append(getRating())
                .append(" (")
                .append(getTitle())
                .append(")")
                .toString();
    }

    private static String language(Locale locale) {
        if (locale == null) {
            return null;
        }

        return locale.getLanguage();
    }

    private static String authorSlug(Account author) {
        return author.getSlug();
    }

}