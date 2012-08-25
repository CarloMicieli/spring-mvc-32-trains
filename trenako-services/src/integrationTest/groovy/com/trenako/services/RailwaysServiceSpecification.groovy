/*
* Copyright 2012 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the 'License');
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*   http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an 'AS IS' BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.trenako.services

import spock.lang.*

import org.bson.types.ObjectId
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.dao.DuplicateKeyException
import org.springframework.data.domain.PageRequest

import com.trenako.mapping.LocalizedField
import com.trenako.entities.Railway
import com.trenako.services.RailwaysService

/**
 * 
 * @author Carlo Micieli
 *
 */
class RailwaysServiceSpecification extends MongoSpecification {

	@Autowired RailwaysService service
	
	def setup() {
		cleanup()
		
		def year1994 = new GregorianCalendar(1994, Calendar.JANUARY, 1).time
		def year1905 = new GregorianCalendar(1905, Calendar.JANUARY, 1).time
		def year1938 = new GregorianCalendar(1938, Calendar.JANUARY, 1).time
		def year1956 = new GregorianCalendar(1956, Calendar.JANUARY, 1).time
		
		db.railways << [
				[name: 'Die bahn', 
					slug: 'die-bahn', 
					description: [en: 'The German railways'], 
					companyName: 'Die bahn', 
					country: 'de', 
					operatingSince: year1994, 
					lastModified: new Date()],
				[name: 'DB', 
					slug: 'db', 
					description: [en: 'The German railways'], 
					companyName: 'Deutshe bundesbahn', 
					country: 'de', 
					operatingSince: year1956, 
					operatingUntil: year1994,
					lastModified: new Date()],
				[name: 'Sncf', 
					slug: 'sncf', 
					description: [en: 'The French railways'], 
					companyName: 'Société Nationale des Chemins de fer Français', 
					country: 'fr', 
					operatingSince: year1938, 
					lastModified: new Date()],
				[name: 'FS',
					slug: 'fs', 
					description: [en: 'The Italian railways'], 
					companyName: 'Ferrovie dello stato', 
					country: 'it', 
					operatingSince: year1905, 
					lastModified: new Date()],					
			]
	}
	
	def cleanup() {
		db.railways.remove([:])
	}
	
	def "should find railways for the provided unique id"() {
		given:
		def doc = db.railways.findOne(slug: 'db')
		def id = doc?._id
		assert id != null
		
		when:
		def railway = service.findById id
		
		then:
		railway != null
		railway.id == id
		railway.name == 'DB'
	}
	
	def "should return null if no railway is found for the provided id"() {
		given:
		def id = new ObjectId('47cc67093475061e3d95369d')
		
		when:
		def railway = service.findById id
		
		then:
		railway == null
	}
	
	def "should find a railway for the provided slug value"() {
		when:
		def railway = service.findBySlug 'sncf'
		
		then:
		railway != null
		railway.slug == 'sncf'
		railway.name == 'Sncf'
	}
	
	def "should return null if no railway is found for the provided slug value"() {
		when:
		def railway = service.findBySlug 'not-found'
		
		then:
		railway == null
	}
	
	def "should find railways for the provided name"() {
		when:
		def railway = service.findByName 'Sncf'

		then:
		railway != null
		railway.name == 'Sncf'
	}
	
	def "should return null if no railway exists for the provided name"() {
		when:
		def railway = service.findByName 'Not found'

		then:
		railway == null
	}
	
	def "should return null if no railway exists for the provided country"() {
		when:
		def result = service.findByCountry 'dk'
		
		then:
		result.empty
	}
	
	def "should find railways by country"() {
		when:
		def result = service.findByCountry 'de'
		
		then:
		result != null
		result.size == 2
		result.collect {it.name} == ['DB', 'Die bahn']
	}
		
	def "should find railways returning paginated results"() {
		given:
		def paging = new PageRequest(0, 10)
		
		when:
		def result = service.findAll(paging)
		
		then:
		result != null
		result.content != null
		result.content.size() == 4
	}
	
	def "should throw an exception if the railway name is already used"() {
		given:
		def newRailway = new Railway(
			name: 'DB', 
			companyName: 'German railways',
			description: LocalizedField.localize([en: 'DB description']),
			country: 'de')
			
		when:
		service.save newRailway
		
		then:
		thrown(DuplicateKeyException)
		newRailway.id == null
	}
	
	def "should throw an exception if the railway slug is already used"() {
		given:
		def newRailway = new Railway(
			name: 'D?B',
			slug: 'db',
			companyName: 'German railways',
			description: LocalizedField.localize([en: 'DB description']),
			country: 'de')
			
		when:
		service.save newRailway
		
		then:
		thrown(DuplicateKeyException)
		newRailway.id == null
	}
	
	def "should create new railways"() {
		given:
		def year1903 = new GregorianCalendar(1903, Calendar.JANUARY, 1).time
		def newRailway = new Railway(
			name: 'S.N.C.B.', 
			companyName: 'Société Nationale des Chemins de fer Belges',
			description: LocalizedField.localize([en: 'National railway company of Belgium']),
			country: 'be', 
			operatingSince: year1903)
		
		when:
		service.save newRailway

		then:
		newRailway.id != null
		
		and:
		def railway = db.railways.findOne(_id: newRailway.id)
		railway.name == 'S.N.C.B.'
		railway.companyName == 'Société Nationale des Chemins de fer Belges'
		railway.description == [en: 'National railway company of Belgium']
		railway.country == 'be'
		String.format('%tF', railway.operatingSince) == '1903-01-01'
		railway.operatingUntil == null
		
		// added automatically before saving
		railway.slug == 'sncb'
		railway.lastModified != null
	}
		
	def "should remove railways"() {
		given:
		def doc = db.railways.findOne(slug: 'db')
		def railway = new Railway(id: doc._id)
		assert railway != null
		
		when:
		service.remove railway
		
		then:
		def docDb = db.railways.findOne(_id: railway.id)
		docDb == null
	}
}
