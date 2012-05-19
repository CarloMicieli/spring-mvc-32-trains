package com.trenako.repositories.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.trenako.entities.RollingStock;
import com.trenako.repositories.RollingStocksRepository;

@Repository("rollingStocksRepository")
public class RollingStocksRepositoryImpl extends MongoRepository<RollingStock> implements RollingStocksRepository {

	@Autowired
	public RollingStocksRepositoryImpl(MongoTemplate mongoOps) {
		super(mongoOps, RollingStock.class);
	}
	
	@Override
	public RollingStock findBySlug(String slug) {
		return findOne("slug", slug);
	}

	@Override
	public Iterable<RollingStock> findByBrand(String brandName) {
		return findAll("brandName", brandName);
	}

	@Override
	public Iterable<RollingStock> findByEra(String era) {
		return findAll("era", era);
	}

	@Override
	public Iterable<RollingStock> findByScale(String scaleName) {
		return findAll("scaleName", scaleName);
	}

	@Override
	public Iterable<RollingStock> findByCategory(String category) {
		return findAll("category", category);
	}

	@Override
	public Iterable<RollingStock> findByPowerMethod(String powerMethod) {
		return findAll("powerMethod", powerMethod);
	}

	@Override
	public Iterable<RollingStock> findByRailwayName(String railwayName) {
		return findAll("railwayName", railwayName);
	}

	@Override
	public Iterable<RollingStock> findByBrandAndEra(String brandName, String era) {
		return findAll("brandName", brandName, "era", era);
	}

	@Override
	public Iterable<RollingStock> findByBrandAndScale(String brandName,
			String scale) {
		return findAll("brandName", brandName, "scaleName", scale);
	}

	@Override
	public Iterable<RollingStock> findByBrandAndCategory(String brandName,
			String categoryName) {
		return findAll("brandName", brandName, "categoryName", categoryName);
	}

	@Override
	public Iterable<RollingStock> findByBrandAndRailway(String brandName,
			String railwayName) {
		return findAll("brandName", brandName, "railwayName", railwayName);
	}

	@Override
	public Iterable<RollingStock> findByTag(String tag) {
		return findAll("tag", tag);
	}
}
