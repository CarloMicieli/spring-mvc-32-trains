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
package com.trenako.listeners;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.stereotype.Component;

import com.mongodb.DBObject;
import com.trenako.entities.RollingStock;

/**
 * The listener to change the document just before the rolling stocks are saved.
 * @author Carlo Micieli
 *
 */
@Component
public class RollingStocksEventListener extends AbstractMongoEventListener<RollingStock> {

	@Override
	public void onBeforeSave(RollingStock rs, DBObject dbo) {
		dbo.put("lastModified", new Date());
		dbo.put("slug", rs.getSlug());
	}
}
