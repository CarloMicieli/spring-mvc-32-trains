package com.trenako.entities;

import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * It represents a rolling stocks collection item.
 * 
 * @author Carlo Micieli
 *
 */
public class CollectionItem {
    
	@DBRef
	private RollingStock rollingStock;
	
	@Indexed
    private String rsSlug;

    private Date addedAt;

    private int price;

    private String condition;

    private String notes;

	public RollingStock getRollingStock() {
		return rollingStock;
	}

	public void setRollingStock(RollingStock rollingStock) {
		this.rollingStock = rollingStock;
	}
	
	public String getRsSlug() {
		return rsSlug;
	}

	public void setRsSlug(String rsSlug) {
		this.rsSlug = rsSlug;
	}

	public Date getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(Date addedAt) {
		this.addedAt = addedAt;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
