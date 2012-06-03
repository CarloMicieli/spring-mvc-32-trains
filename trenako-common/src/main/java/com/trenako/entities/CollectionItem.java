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
    
    CollectionItem() {
	}    

    private CollectionItem(Builder b) {
    	this.rollingStock = b.rs;
    	this.addedAt = b.addedAt;
    	this.price = b.price;
    	this.condition = b.condition;
    	this.notes = b.notes;
    }
    
    public static class Builder {
    	// required fields
    	private final RollingStock rs;
    	
    	// optional fields
    	private Date addedAt = null;
    	private int price = 0;
    	private String condition = null;
    	private String notes = null;
    	
    	public Builder(RollingStock rs) {
    		this.rs = rs;
    	}

		public Builder addedAt(Date date) {
			addedAt = date;
			return this;
		}

		public Builder condition(Condition cond) {
			condition = "new";
			return this;
		}

		public Builder notes(String n) {
			notes = n;
			return this;
		}

		public Builder price(int p) {
			price = p;
			return this;
		}

		public CollectionItem build() {
			return new CollectionItem(this);
		}   	
    }
    
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
