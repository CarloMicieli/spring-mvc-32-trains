package com.trenako.repositories.mongo;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.trenako.entities.Railway;
import com.trenako.repositories.RailwaysRepository;

@Repository("railwaysRepository")
public class RailwaysRepositoryImpl extends MongoRepository<Railway> implements RailwaysRepository {

	public RailwaysRepositoryImpl(MongoTemplate mongoOps, Class<Railway> clazz) {
		super(mongoOps, clazz);
	}

	@Override
	public Railway findByName(String name) {
		return findOne("name", name);
	}	
}
