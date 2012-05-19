package com.trenako.services;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.trenako.entities.RollingStock;
import com.trenako.repositories.RollingStocksRepository;

@Service("rollingStocksService")
public class RollingStocksService {
	
	private RollingStocksRepository repo;
	
	public RollingStocksService(RollingStocksRepository repo) {
		this.repo = repo;
	}
	
	/**
	 * Finds the rolling stock document in the collection by id.
	 * @param id the rolling stock id.
	 * @return the rolling stock document if found; <em>null</em> otherwise.
	 */
	public RollingStock findById(ObjectId id) {
		return repo.findById(id);
	}
	
	/**
	 Finds the rolling stock document in the collection by slug.
	 * @param slug the rolling stock slug.
	 * @return the rolling stock document if found; <em>null</em> otherwise.
	 */
	public RollingStock findBySlug(String slug) {
		return repo.findBySlug(slug);
	}
	
	/**
	 * Returns the rolling stock documents by brand name.
	 * @param brandName the brand name.
	 * @return the rolling stocks list.
	 */
	public Iterable<RollingStock> findByBrand(String brandName) {
		return repo.findByBrand(brandName);
	}
	
	/**
	 * Returns the rolling stock documents by era.
	 * @param era the era.
	 * @return the rolling stocks list.
	 */
	public Iterable<RollingStock> findByEra(String era) {
		return repo.findByEra(era);
	}

	/**
	 * Returns the rolling stock documents by scale.
	 * @param scale the scale.
	 * @return the rolling stocks list.
	 */
	public Iterable<RollingStock> findByScale(String scale) {
		return repo.findByScale(scale);
	}
	
	/**
	 * Returns the rolling stock documents by category.
	 * @param category the category.
	 * @return the rolling stocks list.
	 */
	public Iterable<RollingStock> findByCategory(String category) {
		return repo.findByCategory(category);
	}
	
	/**
	 * Returns the rolling stock documents by power method.
	 * @param powerMethod the power method.
	 * @return the rolling stocks list.
	 */
	public Iterable<RollingStock> findByPowerMethod(String powerMethod) {
		return repo.findByPowerMethod(powerMethod);
	}

	/**
	 * Returns the rolling stock documents by railway.
	 * @param railwayName the railway name.
	 * @return the rolling stocks list.
	 */
	public Iterable<RollingStock> findByRailwayName(String railwayName) {
		return repo.findByRailwayName(railwayName);
	}
	
	public Iterable<RollingStock> findByBrandAndEra(String brandName, String era) {
		return repo.findByBrandAndEra(brandName, era);
	}
	
	public Iterable<RollingStock> findByBrandAndScale(String brandName, String scale) {
		return repo.findByBrandAndScale(brandName, scale);
	}
	
	public Iterable<RollingStock> findByBrandAndCategory(String brandName, String categoryName) {
		return repo.findByBrandAndCategory(brandName, categoryName);
	}
	
	public Iterable<RollingStock> findByBrandAndRailway(String brandName, String railwayName) {
		return repo.findByBrandAndRailway(brandName, railwayName);
	}
	
	public Iterable<RollingStock> findByTag(String tag) {
		return repo.findByTag(tag);
	}
	
	/**
	 * Saves the rolling stock document in the collection.
	 * @param rs a brand.
	 */
	public void save(RollingStock rs) {
		repo.save(rs);
	}
	
	/**
	 * Remove the rolling stock document from the collection.
	 * @param rs a brand.
	 */
	public void remove(RollingStock rs) {
		repo.remove(rs);
	}

}
