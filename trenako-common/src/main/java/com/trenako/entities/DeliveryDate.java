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
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}

	public int getQuarter() {
		return quarter;
	}

	@Override
	public boolean equals(Object obj) {
		if( this==obj ) return true;
		if( !(obj instanceof DeliveryDate) ) return false;
		
		DeliveryDate other = (DeliveryDate)obj;
		return this.quarter==other.quarter &&
				this.year==other.year;
	}
	
	@Override
	public String toString() {
		if( quarter>=1 && quarter<=4 ) {
			return String.format("%d/Q%d", year, quarter);
		}
		
		return String.format("%d", year);
	}
}
