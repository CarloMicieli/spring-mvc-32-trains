package com.trenako.repositories.mongo;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.trenako.entities.Scale;
import com.trenako.repositories.ScalesRepository;

@Repository("scalesRepository")
public class ScalesRepositoryImpl extends MongoRepository<Scale> implements ScalesRepository {

	public ScalesRepositoryImpl(MongoTemplate mongoOps, Class<Scale> clazz) {
		super(mongoOps, clazz);
	}

	@Override
	public Scale findByName(String name) {
		return findOne("name", name);
	}
}
