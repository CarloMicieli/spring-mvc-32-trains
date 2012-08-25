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

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException

import org.springframework.data.domain.PageRequest 

import com.trenako.entities.Address
import com.trenako.entities.Brand
import com.trenako.mapping.LocalizedField

import com.trenako.services.BrandsService

/**
 * 
 * @author Carlo Micieli
 *
 */
class BrandsServiceSpecification extends MongoSpecification {

	@Autowired BrandsService service
	
	def setup() {
		cleanup()
		
		db.brands << [
			[name: 'ACME', 
				slug: 'acme', 
				companyName: 'Anonima Costruzioni Modellistiche Esatte', 
				description: [en: 'Acme description'],
				address: [streetAddress: 'Viale Lombardia, 27', postalCode: '20131', city: 'Milano', country: 'Italy'],
				scales: ['h0'], 
				industrial: true, 
				emailAddress: 'mail@acme.com', 
				website: 'http://www.acmetreni.com',
				lastModified: new Date()],
			[name: 'Roco', 
				slug: 'roco', 
				companyName: 'Modelleisenbahn GmbH', 
				description: [en: 'Roco description'],
				address: [streetAddress: 'Plainbachstraße 4', postalCode: 'A-5101', city: 'Bergheim', country: 'Austria'],
				scales: ['h0', 'tt', 'n'], 
				emailAddress: 'roco@roco.cc', 
				industrial: true, 
				website: 'http://www.roco.cc',
				lastModified: new Date()],
			[name: 'Märklin', 
				slug: 'marklin', 
				companyName: 'Gebr. Märklin & Cie. GmbH', 
				description: [en: 'Märklin description'],
				address: [streetAddress: 'Stuttgarter Straße 55-57', postalCode: 'D-73033', city: 'Göppingen', country: 'Germany'],
				scales: ['1', 'h0', 'n', 'z'],
				emailAddress: 'service@maerklin.de', 
				industrial: true, 
				website: 'http://www.maerklin.de',
				lastModified: new Date()],
			[name: 'LS Models', 
				slug: 'ls-models', 
				companyName: 'L.S. Models Exclusive S.A.', 
				description: [en: 'Ls Models description'], 
				address: [streetAddress: 'Rue des Maximins 1', postalCode: 'L 8247', city: 'Mamer', country: 'Luxembourg'],
				scales: ['h0', 'n'], 
				emailAddress: 'mail@lsmodels.com', 
				industrial: true, 
				website: 'http://www.lsmodels.com',
				lastModified: new Date()]]
	}
	
	def cleanup() {
		db.brands.remove([:])
	}
	
	def "should return null if no brand is found for the provided name"() {
		when:
		def brand = service.findByName 'AAAA'

		then:
		brand == null
	}
	
	def "should find a brand for the provided name"() {
		when:
		def brand = service.findByName 'Roco'
		
		then:
		brand != null
		brand.name == 'Roco'
		brand.description.getDefault() == 'Roco description'
		
		brand.address != null
		brand.address.toString() == 'Plainbachstraße 4, A-5101 Bergheim, (Austria)'
		brand.scales.sort() == ['h0', 'n', 'tt']
	}
	
	def "should return null if no brand is found for the provided slug value"() {
		when:
		def brand = service.findBySlug 'AAAA'

		then:
		brand == null
	}
	
	def "should find a brand for the provided slug value"() {
		when:
		def brand = service.findBySlug 'ls-models'

		then:
		brand != null
		brand.name == 'LS Models'
		brand.description.getDefault() == 'Ls Models description'
	}
	
	def "should return null if no brand is found for the provided id"() {
		given:
		def brandId = new ObjectId('47cc67093475061e3d95369d')
	
		when:
		def brand = service.findById brandId

		then:
		brand == null
	}
	
	def "should find a brand for the provided id"() {
		given:
		def b = db.brands.findOne(slug: 'acme')
		def brandId = b._id
		assert brandId != null
	
		when:
		def brand = service.findById brandId
		
		then:
		brand != null
		brand.id == brandId
		brand.name == 'ACME'
	}
	
	def "should find all brands"() {
		when:
		def brands = service.findAll()
		
		then:
		brands != null
		brands.size == 4
	}

	def "should find brands with paginated results"() {
		given:
		def paging = new PageRequest(1, 2) 

		when:
		def brands = service.findAll(paging)
		
		then:
		brands != null
		brands.number == 1
		brands.numberOfElements == 2
		brands.totalElements == 4
		brands.content != null
	}
	
	def "should throw an exception if the brand name is already used"() {
		given: "the brand name is already in use"
		def newBrand = new Brand(
			name: 'Roco',
			slug: 'brawa',
			description: LocalizedField.localize('Description'),
			emailAddress: 'mail@brawa.de',
			industrial: true,
			website: 'http://www.brawa.de')
		
		when:
		service.save newBrand

		then:
		thrown(DuplicateKeyException)
		newBrand.id == null
	}
		
	def "should throw an exception if the brand slug is already used"() {
		given: "the brand slug is already in use"
		def newBrand = new Brand(
			name: 'Brawa',
			slug: 'roco',
			description: LocalizedField.localize('Description'),
			emailAddress: 'mail@brawa.de',
			industrial: true,
			website: 'http://www.brawa.de')
		
		when:
		service.save newBrand

		then:
		thrown(DuplicateKeyException)
		newBrand.id == null
	}
	
	def "should create new brands"() {
		given:
		def newBrand = new Brand(
			name: 'Brawa',
			companyName: 'Brawa AG',
			address: new Address(streetAddress: 'Uferstraße 26-28', postalCode: '73625', city: 'Remshalden', country: 'Germany'),
			scales: ['h0', 'n'],
			description: LocalizedField.localize([en: 'Brawa description']),
			emailAddress: 'mail@brawa.de',
			industrial: true,
			website: 'http://www.brawa.de')
			
		when:
		service.save newBrand
		
		then:
		newBrand.id != null
		
		and:
		def brand = db.brands.findOne(_id: newBrand.id)
		brand.name == 'Brawa'
		brand.companyName == 'Brawa AG'
		brand.emailAddress == 'mail@brawa.de'
		brand.website == 'http://www.brawa.de'
		brand.address == [streetAddress: 'Uferstraße 26-28', postalCode: '73625', city: 'Remshalden', country: 'Germany']
		brand.scales == ['n', 'h0']
		brand.description == [en: 'Brawa description'] 
		brand.industrial == true
		
		// added automatically 
		brand.slug == 'brawa'
		brand.lastModified != null
	}
	
	def "should modify brands"() {
		given:
		def brand = service.findBySlug('roco')
		assert brand != null
		assert brand.description.getDefault() != null
		
		when:
		brand.branches = [it: new Address(streetAddress: 'Via Manzoni 144', postalCode: '20811', city: 'Cesano Maderno', country: 'Italy')]
		service.save brand

		then:
		def doc = db.brands.findOne(slug: 'roco')
		doc.branches == [it: [streetAddress: 'Via Manzoni 144', postalCode: '20811', city: 'Cesano Maderno', country: 'Italy']]
	}
	
	def "should remove brands"() {
		given:
		def doc = db.brands.findOne(slug: 'acme')
		def brand = new Brand(id: doc._id)
		
		when:
		service.remove brand
		
		then:
		def notFound = db.brands.findOne(slug: 'acme')
		notFound == null
	}
}
