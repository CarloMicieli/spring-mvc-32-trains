package com.trenako.repositories.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.trenako.entities.Option;
import com.trenako.entities.OptionFamily;
import com.trenako.repositories.OptionsRepository;

@Repository("optionsRepository")
public class OptionsRepositoryImpl extends MongoRepository<Option> implements OptionsRepository {

	@Autowired
	public OptionsRepositoryImpl(MongoTemplate mongoOps) {
		super(mongoOps, Option.class);
	}
	
	@Override
	public Option findByName(String name) {
		return findOne("name", name);
	}

	@Override
	public Iterable<Option> findByFamily(OptionFamily family) {
		return findAll("family", family);
	}
}
