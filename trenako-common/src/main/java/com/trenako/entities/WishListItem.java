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

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.index.Indexed;

import com.trenako.mapping.WeakDbRef;
import com.trenako.utility.Slug;
import com.trenako.values.Priority;

/**
 * It represents a {@code WishList} item.
 *
 * @author Carlo Micieli
 */
public class WishListItem implements Comparable<WishListItem> {

    @Indexed
    private String itemId;

    @NotNull(message = "wishList.rollingStock.required")
    private WeakDbRef<RollingStock> rollingStock;

    private String notes;
    private String priority;

    private Money price;
    private Date addedAt;

    /**
     * Creates an empty {@code WishListItem}.
     */
    public WishListItem() {
    }

    /**
     * Creates a new {@code WishListItem}.
     *
     * @param itemId the item id
     * @param price  the item price
     */
    public WishListItem(String itemId, Money price) {
        this.itemId = itemId;
        this.price = price;
    }

    /**
     * Creates a new {@code WishListItem} for the provided rolling stock.
     *
     * @param rollingStock the rolling stock
     */
    public WishListItem(RollingStock rollingStock) {
        this.setRollingStock(rollingStock);
        this.itemId = itemId(itemId, rollingStock, new Date());
    }

    /**
     * Creates a new {@code WishListItem}.
     *
     * @param rollingStock the rolling stock
     * @param notes        the item notes
     * @param priority     the item priority
     * @param addedAt      the timestamp
     */
    public WishListItem(RollingStock rollingStock, String notes, Priority priority, Date addedAt) {
        this(null, WeakDbRef.buildRef(rollingStock), notes, priority, addedAt, null);
    }

    /**
     * Creates a new {@code WishListItem}.
     *
     * @param itemId       the item id
     * @param rollingStock the rolling stock
     * @param notes        the item notes
     * @param priority     the item priority
     * @param addedAt      the timestamp
     * @param price        the item estimated price
     */
    public WishListItem(String itemId,
                        WeakDbRef<RollingStock> rollingStock,
                        String notes,
                        Priority priority,
                        Date addedAt,
                        Money price) {

        this.rollingStock = rollingStock;
        this.notes = notes;
        this.priority = priority.label();
        this.addedAt = addedAt;
        this.price = price;

        this.itemId = itemId(itemId, rollingStock, addedAt);
    }

    /**
     * Returns the item id.
     *
     * @return the item id
     */
    public String getItemId() {
        return this.itemId;
    }

    /**
     * Sets the item id.
     *
     * @param itemId the item id
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * Returns the rolling stock.
     *
     * @return the rolling stock
     */
    public WeakDbRef<RollingStock> getRollingStock() {
        return rollingStock;
    }

    /**
     * Sets the rolling stock.
     *
     * @param rollingStock the rolling stock
     */
    public void setRollingStock(WeakDbRef<RollingStock> rollingStock) {
        this.rollingStock = rollingStock;
    }

    /**
     * Sets the rolling stock.
     *
     * @param rollingStock the rolling stock
     */
    public void setRollingStock(RollingStock rollingStock) {
        this.rollingStock = WeakDbRef.buildRef(rollingStock);
    }

    /**
     * Returns this item notes.
     *
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets this item notes.
     *
     * @param notes the notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Returns the item priority as {@code Priority} value.
     *
     * @return the item priority
     */
    public Priority priority() {
        return Priority.parse(getPriority());
    }

    /**
     * Returns the item priority in the wish list.
     *
     * @return the item priority
     */
    public String getPriority() {
        if (StringUtils.isBlank(priority)) {
            priority = defaultPriority().label();
        }
        return priority;
    }

    /**
     * Sets the item priority in the wish list.
     *
     * @param priority the item priority
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * Returns the estimated price for the current {@code WishListItem}.
     *
     * @return the estimated price
     */
    public Money getPrice() {
        if (price == null) {
            return Money.nullMoney();
        }
        return price;
    }

    /**
     * Sets the estimated price for the current {@code WishListItem}.
     *
     * @param price the estimated price
     */
    public void setPrice(Money price) {
        this.price = price;
    }

    /**
     * Returns the timestamp when this rolling stock was added to the list.
     *
     * @return the timestamp
     */
    public Date getAddedAt() {
        return addedAt;
    }

    /**
     * Sets the timestamp when this rolling stock was added to the list.
     *
     * @param addedAt the timestamp
     */
    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    /**
     * Returns the default {@code WishListItem} priority.
     *
     * @return the default priority.
     */
    public static Priority defaultPriority() {
        return Priority.NORMAL;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof WishListItem)) return false;

        WishListItem other = (WishListItem) obj;
        return this.itemId.equals(other.itemId);
    }

    @Override
    public int compareTo(WishListItem o) {
        if (this.equals(o)) return 0;

        int c1 = this.priority().compareTo(o.priority());
        if (c1 != 0) return c1;

        return o.getAddedAt().compareTo(this.getAddedAt());
    }

    private String itemId(String id, WeakDbRef<RollingStock> rs, Date addedAt) {
        return itemId(itemId, rs.getSlug(), addedAt);
    }

    private String itemId(String itemId, RollingStock rs, Date addedAt) {
        return itemId(itemId, rs.getSlug(), addedAt);
    }

    private String itemId(String id, String rsSlug, Date addedAt) {
        if (StringUtils.isBlank(id)) {
            return new StringBuilder()
                    .append(rsSlug)
                    .append("_")
                    .append(Slug.encodeFull(addedAt))
                    .toString();
        } else {
            return id;
        }
    }
}