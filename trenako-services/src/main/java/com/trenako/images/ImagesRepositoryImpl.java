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
package com.trenako.images;

import java.util.Map;

import static org.springframework.data.mongodb.core.query.Query.*;
import static org.springframework.data.mongodb.gridfs.GridFsCriteria.*;

import com.mongodb.DBObject;
import com.mongodb.BasicDBObject;
import com.mongodb.gridfs.GridFSDBFile;

import com.trenako.images.UploadFile;

import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * The concrete class for Mongo GridFs repository.
 * @author Carlo Micieli
 *
 */
@Repository("imagesRepository")
public class ImagesRepositoryImpl implements ImagesRepository {

	private final GridFsTemplate gridFsTemplate;

	/**
	 * Creates a new {@code ImagesRepositoryImpl}.
	 * @param gridFsTemplate the GridFs template
	 */
	@Autowired
	public ImagesRepositoryImpl(GridFsTemplate gridFsTemplate) {
		this.gridFsTemplate = gridFsTemplate;
	}

	@Override
	public GridFSDBFile findFileBySlug(String slug) {
		return gridFsTemplate.findOne(query(whereMetaData("slug").is(slug)));
	}

	@Override
	public void store(UploadFile file) {
		DBObject metadata = fillMetadata(file.getMetadata());

		gridFsTemplate.store(file.getContent(),
			file.getFilename(),
			metadata);
	}

	@Override
	public void delete(String slug) {
		gridFsTemplate.delete(query(whereMetaData("slug").is(slug)));
	}

	private DBObject fillMetadata(Map<String, String> metadata) {
		DBObject dbo = new BasicDBObject();
		dbo.putAll(metadata);
		return dbo;
	}
}
