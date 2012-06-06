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

/**
 * It represents a counter for a particular item.
 * <p>
 * This class implements the {@link Comparable} interface, the objects
 * are ordered by number of items and then by label.
 * </p>
 * 
 * @author Carlo Micieli
 *
 */
public class ItemsCount implements Comparable<ItemsCount> {

	private String slug;
	private String label;
	private int items;
	
	// required by spring data
	ItemsCount() {
	}
	
	/**
	 * Creates a new items counter.
	 * @param slug the slug for this element
	 * @param label the label for this element
	 * @param items the number of items
	 */
	public ItemsCount(String slug, String label, int items) {
		this.slug = slug;
		this.label = label;
		this.items = items;
	}
	
	/**
	 * Returns the slug.
	 * @return the slug
	 */
	public String getSlug() {
		return slug;
	}

	/**
	 * Returns the label.
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Returns the number of items.
	 * @return the items count
	 */
	public int getItems() {
		return items;
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * @param obj the reference object with which to compare
	 * @return <em>true</em> if this object is the same as the obj argument; <em>false</em> otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if( obj==this ) return true;
		if( !(obj instanceof ItemsCount) ) return false;
		
		ItemsCount other = (ItemsCount) obj;
		return this.items==other.items &&
				this.label.equals(other.label) &&
				this.slug.equals(other.slug);
	}
	
	/**
	 * Returns a string representation of the object.
	 * <p>
	 * This method returns a string equal to the value of:
	 * <pre>
	 * {@code label + "(" + slug + ", items=" + items + ")" }
	 * </pre>
	 * </p>
	 * 
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		return new StringBuffer()
			.append(label)
			.append(" (")
			.append(slug)
			.append(", items=")
			.append(items)
			.append(")")
			.toString();
	}

	/**
	 * Compares this object with the specified object for order.
	 * @param o the object to be compared
	 * @return a negative integer, zero, or a positive integer as this object is less than, 
	 * equal to, or greater than the specified object
	 */
	@Override
	public int compareTo(ItemsCount o) {
		if( this.equals(o) ) return 0;
		
		int diff = this.items - o.items;
		if( diff!=0 ) return diff;
		
		return o.label.compareTo(this.label);
	}
}
