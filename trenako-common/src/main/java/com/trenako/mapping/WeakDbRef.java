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
package com.trenako.mapping;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.util.Assert;

/**
 * It represents a wrapper for {@code "weak"} database references.
 * <p>
 * As stated in the official Mongodb documentation, {@code manual reference}
 * are the preferred way to link two collections.
 * </p>
 * <p>
 * Typically the entities wrapped in a {@code WeakDbRef} contains all
 * the information needed to fully describe the entity. This produces a
 * denormalized schemas, but it requires less database queries to populate
 * the field values.
 * </p>
 *
 * @author Carlo Micieli
 */
public class WeakDbRef<E extends DbReferenceable> {

    @Indexed
    private String slug;
    private String label;

    /**
     * Creates an empty {@code WeakDbRef}.
     */
    public WeakDbRef() {
    }

    /**
     * Creates a new {@code WeakDbRef}.
     *
     * @param slug  the slug
     * @param label the label
     */
    public WeakDbRef(String slug, String label) {
        this.slug = slug;
        this.label = label;
    }

    private WeakDbRef(E entity) {
        this(entity.getSlug(), entity.getLabel());
    }

    /**
     * Builds a new {@code WeakDbRef} for the provided entity.
     *
     * @param entity the entity object
     * @return the new {@code WeakDbRef}
     */
    public static <E extends DbReferenceable> WeakDbRef<E> buildRef(E entity) {
        Assert.notNull(entity, "Weak ref: entity obj is required.");
        return new WeakDbRef<E>(entity);
    }

    /**
     * Builds a new {@code WeakDbRef} for the provided slug string.
     *
     * @param slug   the slug
     * @param entity the entity type
     * @return the new {@code WeakDbRef}
     */
    public static <E extends DbReferenceable> WeakDbRef<E> buildFromSlug(String slug, Class<E> entity) {
        Assert.notNull(slug, "Weak ref: slug text is required.");
        return new WeakDbRef<E>().setSlug(slug);
    }

    /**
     * Checks whether the current {@code WeakDbRef} was fully loaded.
     * <p>
     * A reference is loaded when it contains values for both {@code slug} and {@code label}.
     * </p>
     *
     * @return {@code true} the reference was loaded; {@code false} otherwise.
     */
    public boolean isLoaded() {
        return (!StringUtils.isBlank(slug) && !StringUtils.isBlank(label));
    }

    /**
     * Returns the {@code WeakDbRef} slug.
     *
     * @return the slug
     */
    public String getSlug() {
        return slug;
    }

    /**
     * Sets the {@code WeakDbRef} slug.
     *
     * @param slug the slug
     */
    public WeakDbRef<E> setSlug(String slug) {
        this.slug = slug;
        return this;
    }

    /**
     * Returns the {@code WeakDbRef} label.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the {@code WeakDbRef} label.
     *
     * @param label the label
     */
    public WeakDbRef<E> setLabel(String label) {
        this.label = label;
        return this;
    }

    @Override
    public String toString() {
        return this.getSlug();
    }

    /**
     * Returns the complete (both slug and label) representation for the
     * current reference.
     *
     * @return the complete string representation
     */
    public String toCompleteString() {
        return new StringBuilder()
                .append("{slug: ").append(getSlug())
                .append(", label: ").append(getLabel())
                .append("}")
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof WeakDbRef)) return false;

        WeakDbRef<?> other = (WeakDbRef<?>) obj;
        return new EqualsBuilder()
                .append(this.slug, other.slug)
                .append(this.label, other.label)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return slug.hashCode();
    }
}
