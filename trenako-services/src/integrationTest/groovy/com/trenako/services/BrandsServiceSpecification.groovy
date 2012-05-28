package com.trenako.services

import java.util.List;

import spock.lang.*

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query

import static org.springframework.data.mongodb.core.query.Query.*
import static org.springframework.data.mongodb.core.query.Criteria.*

import com.trenako.entities.Brand;
import com.trenako.services.BrandsServiceImpl;

/**
 * 
 * @author Carlo Micieli
 *
 */
@ContextConfiguration(locations = "classpath:META-INF/spring-context.xml")
class BrandsServiceSpecification extends Specification {
	
	@Autowired MongoTemplate mongoTemplate
	@Autowired BrandsServiceImpl service
		
	def brands = Brand.class
	
	def setup() {
		def collection = [
			new Brand(name: "ACME", description: "Brand descritpion",
				emailAddress: "mail@acme.com", industrial: true, 
				website: "http://www.acme.com"),
			new Brand(name: "Roco", description: "Brand descritpion",
				emailAddress: "mail@roco.cc", industrial: true,
				website: "http://www.roco.cc"),
			new Brand(name: "LS Models", description: "Brand descritpion",
				emailAddress: "mail@lsmodels.com", industrial: true,
				website: "http://www.lsmodels.com"),
			new Brand(name: "Maerklin", description: "Brand descritpion",
				emailAddress: "mail@maerklin.de", industrial: true,
				website: "http://www.maerklin.de")]

		mongoTemplate.insert collection, brands
	}
	
	def cleanup() {
		def all = new Query()
		mongoTemplate.remove all, brands
	}
	
	def "should return Null if no brand is found"() {
		when:
		def brand = service.findByName "AAAA"

		then:
		brand == null
	}
	
	def "should find a brand by name"() {
		when:
		def brand = service.findByName "ACME"
		
		then:
		brand != null
		brand.name == "ACME"
	}
	
	def "should find a brand by slug"() {
		when:
		def brand = service.findBySlug "ls-models"

		then:
		brand != null
		brand.name == "LS Models"
	}
	
	def "should find all the brands"() {
		when:
		def brands = service.findAll()
		
		then:
		brands != null
		brands.size == 4
	}
	
	def "should create new brands"() {
		given:
		def newBrand = new Brand(name: "Brawa",
			description: "Brand description",
			emailAddress: "mail@brawa.de",
			industrial: true,
			website: "http://www.brawa.de")
			
		when:
		service.save newBrand
		
		then:
		newBrand.id != null
		
		def brand = mongoTemplate.findById newBrand.id, brands
		brand.name == "Brawa"
		brand.description == "Brand description"
		brand.emailAddress == "mail@brawa.de"
		brand.website == "http://www.brawa.de"

		// added automatically 
		brand.slug == "brawa"
		brand.lastModified != null
	}
	
	def "should find brands by id"() {
		given:
		def id = mongoTemplate.findOne(query(where("slug").is("acme")), brands).id
		
		when:
		def brand = service.findById id
		
		then:
		brand != null
		brand.id == id
		brand.name == "ACME"
	}
	
	def "should remove brand"() {
		given:
		def brand = mongoTemplate.findOne query(where("slug").is("acme")), brands

		when:
		service.remove brand
		
		then:
		def b = mongoTemplate.findById brand.id, brands
		b == null
	}
}

