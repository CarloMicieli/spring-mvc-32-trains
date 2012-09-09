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
import org.springframework.util.StringUtils;

import com.trenako.values.Category;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CategoriesCount {

	public final static CategoriesCount EMPTY = new CategoriesCount();

	private int steamLocomotives;
	private int dieselLocomotives;
	private int electricLocomotives;
	private int railcars;
	private int electricMultipleUnit;
	private int freightCars;
	private int passengerCars;
	private int trainSets;
	private int starterSets;
	
	/**
	 * Creates an empty {@code CategoriesCount}.
	 */
	public CategoriesCount() {
	}
		
	protected CategoriesCount(int steamLocomotives, 
			int dieselLocomotives,
			int electricLocomotives, 
			int railcars, 
			int electricMultipleUnit,
			int freightCars, 
			int passengerCars, 
			int trainSets, 
			int starterSets) {
		this.steamLocomotives = steamLocomotives;
		this.dieselLocomotives = dieselLocomotives;
		this.electricLocomotives = electricLocomotives;
		this.railcars = railcars;
		this.electricMultipleUnit = electricMultipleUnit;
		this.freightCars = freightCars;
		this.passengerCars = passengerCars;
		this.trainSets = trainSets;
		this.starterSets = starterSets;
	}

	public int getSteamLocomotives() {
		return steamLocomotives;
	}

	public void setSteamLocomotives(int steamLocomotives) {
		this.steamLocomotives = steamLocomotives;
	}

	public int getDieselLocomotives() {
		return dieselLocomotives;
	}

	public void setDieselLocomotives(int dieselLocomotives) {
		this.dieselLocomotives = dieselLocomotives;
	}

	public int getElectricLocomotives() {
		return electricLocomotives;
	}

	public void setElectricLocomotives(int electricLocomotives) {
		this.electricLocomotives = electricLocomotives;
	}

	public int getRailcars() {
		return railcars;
	}

	public void setRailcars(int railcars) {
		this.railcars = railcars;
	}

	public int getElectricMultipleUnit() {
		return electricMultipleUnit;
	}

	public void setElectricMultipleUnit(int electricMultipleUnit) {
		this.electricMultipleUnit = electricMultipleUnit;
	}

	public int getFreightCars() {
		return freightCars;
	}

	public void setFreightCars(int freightCars) {
		this.freightCars = freightCars;
	}

	public int getPassengerCars() {
		return passengerCars;
	}

	public void setPassengerCars(int passengerCars) {
		this.passengerCars = passengerCars;
	}

	public int getTrainSets() {
		return trainSets;
	}

	public void setTrainSets(int trainSets) {
		this.trainSets = trainSets;
	}

	public int getStarterSets() {
		return starterSets;
	}

	public void setStarterSets(int starterSets) {
		this.starterSets = starterSets;
	}
	
	/**
	 * Returns the total count for the {@code CategoriesCount}.
	 * @return the total
	 */
	public int getTotalCount() {
		return 	this.steamLocomotives +
			this.dieselLocomotives +
			this.electricLocomotives +
			this.railcars +
			this.electricMultipleUnit +
			this.freightCars +
			this.passengerCars +
			this.trainSets +
			this.starterSets;
	}

	/**
	 * Returns the field name from the {@code Category} label. 
	 * @param cat the {@code Category} label
	 * @return the field name
	 */
	public static String getKey(String cat) {
		StringBuilder sb = new StringBuilder();
		if (cat.indexOf("-") > 0) {
			String[] tokens = cat.split("-");
			sb.append(tokens[0]);
			for (int i = 1; i < tokens.length; i++) {
				sb.append(StringUtils.capitalize(tokens[i]));
			}
		}
		else {
			sb.append(cat);
		}
		
		return sb.toString();
	}
	
	/**
	 * Returns the field name from the {@code Category} value. 
	 * @param category the {@code Category} value
	 * @return the field name
	 */
	public static String getKey(Category category) {
		return getKey(category.label());
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append("categories{")
			.append(this.steamLocomotives).append(", ")
			.append(this.dieselLocomotives).append(", ")
			.append(this.electricLocomotives).append(", ")
			.append(this.railcars).append(", ")
			.append(this.electricMultipleUnit).append(", ")
			.append(this.freightCars).append(", ")
			.append(this.passengerCars).append(", ")
			.append(this.trainSets).append(", ")
			.append(this.starterSets).append("}")
			.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof CategoriesCount)) return false;
		
		CategoriesCount other = (CategoriesCount) obj;
		return new EqualsBuilder()
			.append(this.steamLocomotives, other.steamLocomotives)
			.append(this.dieselLocomotives, other.dieselLocomotives)
			.append(this.electricLocomotives, other.electricLocomotives)
			.append(this.railcars, other.railcars)
			.append(this.electricMultipleUnit, other.electricMultipleUnit)
			.append(this.freightCars, other.freightCars)
			.append(this.passengerCars, other.passengerCars)
			.append(this.trainSets, other.trainSets)
			.append(this.starterSets, other.starterSets)
			.isEquals();
	}

}