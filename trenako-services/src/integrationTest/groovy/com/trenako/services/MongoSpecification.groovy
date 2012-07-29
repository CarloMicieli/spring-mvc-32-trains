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
package com.trenako.services

import com.trenako.mapping.LocalizedField
import com.trenako.mapping.LocalizedFieldWriteConverter

import com.gmongo.GMongo
import org.springframework.test.context.ContextConfiguration
import org.springframework.beans.factory.annotation.Autowired

import spock.lang.Specification;

/**
 * 
 * @author Carlo Micieli
 *
 */
@ContextConfiguration(locations = "classpath:META-INF/spring-context.xml")
class MongoSpecification extends Specification {
	def db = new GMongo("localhost:27017").getDB("trenako-testdb")
	
	@Autowired LocalizedFieldWriteConverter writeConverter
	
	def localize(String v) {
		LocalizedField<String> field = new LocalizedField<String>(v)
		writeConverter.convert(field)
	}
	
	LocalizedField<String> localizedDesc(String v) {
		return new LocalizedField<String>(v)
	}
}
