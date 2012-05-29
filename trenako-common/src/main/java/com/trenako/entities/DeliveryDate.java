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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * It represents a delivery date for a rolling stock.
 * 
 * The delivery date has two parts: the <em>year</em> (required) and 
 * the <em>quarter</em> (optional).
 * 
 * @author Carlo Micieli
 *
 */
public class DeliveryDate {

	private static List<Integer> QUARTERS =
			Collections.unmodifiableList(Arrays.asList(1, 2, 3, 4));
	
	private int year;
	private int quarter;
	
	// required by the persistence layer
	DeliveryDate() {
	}
	
	/**
	 * Creates a new {@link DeliveryDate} without the quarter.
	 * @param year the year
	 */
	public DeliveryDate(int year) {
		this.year = year;
	}
	
	/**
	 * Creates a new {@link DeliveryDate} with year and quarter.
	 * @param year the year
	 * @param quarter quarter (1-4)
	 * 
	 * @throws IllegalArgumentException if the quarter is invalid.
	 */	
	public DeliveryDate(int year, int quarter) {
		
		if( QUARTERS.contains(quarter)==false )
			throw new IllegalArgumentException("quarter must be >=1 and <=4");
		
		this.year = year;
		this.quarter = quarter;
	}
	
	void setYear(int year) {
		this.year = year;
	}

	/**
	 * Returns the delivery date year.
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	
	void setQuarter(int quarter) {
		this.quarter = quarter;
	}

	/**
	 * Returns the delivery date quarter.
	 * @return the quarter
	 */
	public int getQuarter() {
		return quarter;
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * @param obj the reference object with which to compare.
	 * @return <em>true</em> if this object is the same as the obj argument; <em>false</em> otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if( this==obj ) return true;
		if( !(obj instanceof DeliveryDate) ) return false;
		
		DeliveryDate other = (DeliveryDate)obj;
		return this.quarter==other.quarter &&
				this.year==other.year;
	}
	
	/**
	 * Returns a string representation of the object.
	 * @return a string representation of the object.
	 */
	@Override
	public String toString() {
		if( QUARTERS.contains(quarter) ) {
			return String.format("%d/Q%d", year, quarter);
		}
		
		return String.format("%d", year);
	}
}
