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

import spock.lang.*

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.domain.PageRequest

import com.trenako.entities.Scale
import com.trenako.mapping.LocalizedField
import com.trenako.services.ScalesService

/**
 * 
 * @author Carlo Micieli
 *
 */
class ScalesServiceSpecification extends MongoSpecification {

	@Autowired ScalesService service
	
	def setup() {
		db.scales << [
				[name: '1', slug: '1', description: [en: 'Scale 1'], standards: ['NEM'], powerMethods: ['dc'], ratio: 320, gauge: 4445, narrow: false],
				[name: 'H0', slug: 'h0', description: [en: 'Scale H0'], standards: ['NEM'], powerMethods: ['ac', 'dc'], ratio: 870, gauge: 1650, narrow: false],
				[name: '0', slug: '0', description: [en: 'Scale 0'], standards: ['NEM'], powerMethods: ['dc'], ratio: 435, gauge: 3200, narrow: false],
				[name: 'N', slug: 'n', description: [en: 'Scale N'], standards: ['NEM'], powerMethods: ['dc'], ratio: 160, gauge: 900, narrow: false]
			]
	}
	
	def cleanup() {
		db.scales.remove([:])
	}
	
	def "should find all scaleswith paginated results"() {
		given:
		def paging = new PageRequest(1, 2) 
		
		when:
		def results = service.findAll(paging)
		
		then:
		results != null
		results.number == 1
		results.numberOfElements == 2
		results.totalElements == 4
		results.content != null
	}

	def "should return null if no scale has the provided id"() {
		given:
		def id = new ObjectId('47cc67093475061e3d95369d')
		
		when:
		def scale = service.findById id

		then:
		scale == null
	}
	
	def "should find the scale for the provided id"() {
		given:
		def doc = db.scales.findOne(slug: 'h0')
		def id = doc._id
		
		when:
		def scale = service.findById id
		
		then:
		scale != null
		scale.id == id
		scale.name == 'H0'
		scale.description.getDefault() == 'Scale H0'
	}
	
	def "should return null if no scale has the provided name"() {
		when:
		def scale = service.findByName 'Not found'

		then:
		scale == null
	}
	
	def "should find the scale with the provided name"() {
		when:
		def scale = service.findByName 'H0'

		then:
		scale != null
		scale.name == 'H0'
	}
	
	def "should return null if no scale has the provided slug"() {
		when:
		def scale = service.findBySlug 'not-found'

		then:
		scale == null
	}
	
	def "should find the scale with the provided slug"() {
		when:
		def scale = service.findBySlug 'h0'

		then:
		scale != null
		scale.slug == 'h0'
		scale.name == 'H0'
	}
	
	def "should throw an exception if the scale name is already used"() {
		given: "the scale name is already in use"
		def newScale = new Scale(
			name: 'H0',
			description: LocalizedField.localize([en: 'H0 description']),
			gauge: 1650,
			ratio: 870)
		
		when:
		service.save newScale

		then:
		thrown(DuplicateKeyException)
		newScale.id == null
	}
	
	def "should throw an exception if the scale slug is already used"() {
		given: "the scale slug is already in use"
		def newScale = new Scale(
			name: 'H0-2',
			slug: 'h0',
			description: LocalizedField.localize([en: 'H0 description']),
			gauge: 1650,
			ratio: 870)
		
		when:
		service.save newScale

		then:
		thrown(DuplicateKeyException)
		newScale.id == null
	}
		
	def "should create new scales"() {
		given:
		def newScale = new Scale(name: 'H0m', 
			ratio: 870, 
			gauge: 900, 
			powerMethods: ['ac', 'dc'],
			description: LocalizedField.localize([en: 'H0m description']),
			standards: ['NEM'],
			narrow: false)

		when:
		service.save newScale
		
		then:
		newScale.id != null
		
		and:
		def savedDoc = db.scales.findOne(slug: 'h0m')
		assert savedDoc != null
		
		savedDoc.name == 'H0m'
		savedDoc.ratio == 870
		savedDoc.gauge == 900
		savedDoc.narrow == false
		savedDoc.slug == 'h0m'
		savedDoc.powerMethods == ['dc', 'ac']
		savedDoc.standards == ['NEM']
		savedDoc.description.en == 'H0m description'
	}
	
	def "should remove scales"() {
		given:
		def doc = db.scales.findOne(slug: 'h0')
		def scale = new Scale(id: doc._id)
		
		when:
		service.remove scale 
		
		then:
		def dbDoc = db.scales.findOne(slug: 'h0')
		dbDoc == null
	}
}
