package com.trenako.repositories;

import org.bson.types.ObjectId;

import com.trenako.entities.RollingStock;

public interface RollingStocksRepository {
	/**
	 * Returns the rolling stock document by the id.
	 * @param id the document id.
	 * @return the rolling stock document. <em>null</em> if no rolling stock are found.
	 */
	RollingStock findById(ObjectId id);
	
	/**
	 * Returns the rolling stock document by its slug.
	 * @param slug the rolling stock slug.
	 * @return the rolling stock document. <em>null</em> if no rolling stock are found.
	 */
	RollingStock findBySlug(String slug);
	
	/**
	 * Returns the rolling stock documents by brand name.
	 * @param brandName the brand name.
	 * @return the rolling stocks list.
	 */
	Iterable<RollingStock> findByBrandName(String brandName);
	
	/**
	 * Returns the rolling stock documents by era.
	 * @param era the era.
	 * @return the rolling stocks list.
	 */
	Iterable<RollingStock> findByEra(String era);

	/**
	 * Returns the rolling stock documents by scale.
	 * @param scale the scale.
	 * @return the rolling stocks list.
	 */
	Iterable<RollingStock> findByScaleName(String scale);
	
	/**
	 * Returns the rolling stock documents by category.
	 * @param category the category.
	 * @return the rolling stocks list.
	 */
	Iterable<RollingStock> findByCategoryName(String category);
	
	/**
	 * Returns the rolling stock documents by power method.
	 * @param powerMethod the power method.
	 * @return the rolling stocks list.
	 */
	Iterable<RollingStock> findByPowerMethod(String powerMethod);

	/**
	 * Returns the rolling stock documents by railway.
	 * @param railwayName the railway name.
	 * @return the rolling stocks list.
	 */
	Iterable<RollingStock> findByRailwayName(String railwayName);
	
	Iterable<RollingStock> findByBrandAndEra(String brandName, String era);
	Iterable<RollingStock> findByBrandAndScaleName(String brandName, String scale);
	Iterable<RollingStock> findByBrandAndCategoryName(String brandName, String categoryName);
	Iterable<RollingStock> findByBrandAndRailwayName(String brandName, String railwayName);
	
	Iterable<RollingStock> findByTag(String tag);
	
	/**
	 * Saves the rolling stock document in the collection.
	 * @param rs a brand.
	 */
	void save(RollingStock rs);
	
	/**
	 * Remove the rolling stock document from the collection.
	 * @param rs a brand.
	 */
	void remove(RollingStock rs);

}
