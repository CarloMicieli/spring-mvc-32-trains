package com.trenako.entities;

/**
 * The enumeration of the model categories.
 * @author Carlo Micieli
 *
 */
public enum Category {
	STEAM_LOCOMOTIVES,
	ELECTRIC_LOCOMOTIVES,
	DIESEL_LOCOMOTIVES,
	ELECTRIC_MULTIPLE_UNIT,
	TRAIN_SETS,
	RAILCARS,
	FREIGHT_CARS,
	PASSENGER_CARS,
	STARTER_SETS;
	
	/**
	 * Returns the category description to be stored in the database.
	 * @return the category name.
	 */
	public String getDescription() {
		return name().toLowerCase().replace('_', '-');
	}

	/**
	 * Parses the category value from the database.
	 * @param category the category name.
	 * @return a value from Category enumeration.
	 */
	public static Category parse(String category) {
		String c = category.toUpperCase().replace('-', '_');
		return Category.valueOf(c);
	}
}
