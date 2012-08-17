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

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException

import com.trenako.entities.Brand
import com.trenako.entities.Railway
import com.trenako.entities.RollingStock
import com.trenako.entities.Scale
import com.trenako.mapping.LocalizedField
import com.trenako.values.Category
import com.trenako.values.DeliveryDate;
import com.trenako.values.Era
import com.trenako.values.PowerMethod

import com.trenako.services.RollingStocksService

/**
 * 
 * @author Carlo Micieli
 *
 */
class FormValuesServiceSpecification  extends MongoSpecification {

	@Autowired FormValuesService service
	
	def setup() {
		
		db.brands << [[name: 'ACME', slug: 'acme'], 
			[name: 'LS Models', slug: 'ls-models'], 
			[name: 'Roco', slug: 'roco']]
		db.scales << [[name: 'H0', slug: 'h0', ratio: 870],
			[name: '1', slug: '1', ratio: 325],
			[name: 'N', slug: 'n', ratio: 1600]]
		db.railways << [[name: 'DB', slug: 'db', country: 'de'],
			[name: 'FS', slug: 'fs', country: 'it'],
			[name: 'Sncf', slug: 'sncf', country: 'fr']]
	}
	
	def cleanup() {
		db.scales.remove([:])
		db.railways.remove([:])
		db.brands.remove([:])
	}
	
	def "should fill the list of brands for rolling stock forms"() {
		when:
		def results = service.brands()
		
		then:
		results != null
		results.size == 3
		results.collect { it.slug }.sort() == ['acme', 'ls-models', 'roco']
	}
		
	def "should fill the list of railways for rolling stock forms"() {
		when:
		def results = service.railways()
		
		then:
		results != null
		results.size == 3 
		results.collect { it.slug }.sort() == ['db', 'fs', 'sncf']
	}
	
	def "should fill the list of scales for rolling stock forms"() {
		when:
		def results = service.scales()
		
		then:
		results != null
		results.size == 3
		results.collect { it.slug }.sort() == ['1', 'h0', 'n']
	}
}
