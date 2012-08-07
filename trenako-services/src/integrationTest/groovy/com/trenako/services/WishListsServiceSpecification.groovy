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

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException

import com.trenako.entities.Account
import com.trenako.entities.Brand
import com.trenako.entities.RollingStock
import com.trenako.entities.WishList
import com.trenako.entities.WishListItem
import com.trenako.mapping.WeakDbRef

import com.trenako.values.Visibility

/**
 * 
 * @author Carlo Micieli
 *
 */
class WishListsServiceSpecification extends MongoSpecification {

	@Autowired WishListsService service
	
	def now = new GregorianCalendar(2010, Calendar.JULY, 22, 1, 30, 00).time
	
	def acme() {
		new WeakDbRef(slug: 'acme', label: 'ACME')
	}
	
	def rollingStock(brand, itemNumber) {
		new WeakDbRef(slug: "$brand-$itemNumber", label: "$brand $itemNumber")
	}
	
	def bob() {
		new WeakDbRef(slug: 'bob', label: 'Bob')
	}
	
	def setup() {
		db.wishLists.insert(
			slug: 'bob-my-list',
			name: 'My List',
			owner: [slug: 'bob', label: 'Bob'],
			defaultList: false,
			items: [
				[itemId: 'acme-123455', 
					rollingStock: [slug: 'acme-123455', label: 'ACME 123455'], 
					notes: 'My notes1', 
					priority: 'low'],
				[itemId: 'acme-123456', 
					rollingStock: [slug: 'acme-123456', label: 'ACME 123456'], 
					notes: 'My notes2', 
					priority: 'medium'],
				[itemId: 'acme-123457', 
					rollingStock: [slug: 'acme-123457', label: 'ACME 123457'], 
					notes: 'My notes3', 
					priority: 'high']],
			visibility: 'public',
			lastModified: now
		)
		
		db.wishLists.insert(
			slug: 'bob-my-list-2',
			name: 'My List 2',
			owner: [slug: 'bob', label: 'Bob'],
			defaultList: true,
			items: [
				[itemId: 'acme-123456', 
					rollingStock: [slug: 'acme-123456', label: 'ACME 123456'], 
					notes: 'My notes4', 
					priority: 'medium'],
				[itemId: 'acme-123458', 
					rollingStock: [slug: 'acme-123458', label: 'ACME 123458'], 
					notes: 'My notes5', 
					priority: 'high']],
			visibility: 'public',
			lastModified: now
		)
		
		db.wishLists.insert(
			slug: 'alice-my-list',
			name: 'My List',
			owner: [slug: 'alice', label: 'Alice'],
			items: [
				[itemId: 'acme-123455', 
					rollingStock: [slug: 'acme-123455', label: 'ACME 123455'], 
					notes: 'My notes6', 
					priority: 'medium'],
				[itemId: 'acme-123458', 
					rollingStock: [slug: 'acme-123458', label: 'ACME 123458'], 
					notes: 'My notes7', 
					priority: 'high']],
			visibility: 'public',
			lastModified: now
		)
	}
	
	def cleanup() {
		db.wishLists.remove([:])
	}
	
	def "should find wish lists for the provided user without loading the items"() {
		given:
		def owner = new Account(slug: 'bob')
		
		when:
		def results = service.findByOwner(owner)
		
		then:
		results != null
		results.size() == 2
		results.collect { it.slug }.sort() == ['bob-my-list', 'bob-my-list-2']
		
		and: "the items are not loaded"
		results.collect { it.items } == [null, null]
	}

	def "should return empty results when the provided user has no wish list yet"() {
		given:
		def owner = new Account(slug: 'george')
		
		when:
		def results = service.findByOwner(owner)
		
		then:
		results.size() == 0
	}

	def "should find wish lists for the provided slug"() {
		when:
		def result = service.findBySlug('alice-my-list')
		
		then:
		result != null
		result.slug == 'alice-my-list'
		result.items != null
		result.items.size() == 2
		result.items.collect { it.itemId } == ['acme-123455', 'acme-123458']
	}

	def "should return null if the wish list with the provided slug was not found"() {
		when:
		def result = service.findBySlug('not-found')
		
		then:
		result == null
	}
	
	def "should find the default wish list for the provided user"() {
		given:
		def owner = new Account(slug: 'bob')
	
		when:
		def result = service.findDefaultListByOwner(owner)
		
		then:
		result != null
		result.slug == 'bob-my-list-2'
		result.owner.slug == 'bob'
		result.items != null
		result.items.size() == 2
	}

	def "should return null if the user has no default wish list yet"() {
		given:
		def owner = new Account(slug: 'alice')
	
		when:
		def results = service.findDefaultListByOwner(owner)
		
		then:
		results == null
	}
	
	def "should check whether the wish list contains the provided rolling stock"() {
		given:
		def wishList = new WishList(slug: 'bob-my-list-2')
		
		and:
		def rs = new RollingStock(brand: acme(), itemNumber: '123456')
		
		when:
		def ret = service.containsRollingStock(wishList, rs)
		
		then:
		ret == true	
	}
	
	def "should check whether the wish list doesn't contain the provided rolling stock"() {
		given:
		def wishList = new WishList(slug: 'bob-my-list-2')
		
		and:
		def rs = new RollingStock(brand: acme(), itemNumber: '99999')
		
		when:
		def ret = service.containsRollingStock(wishList, rs)
		
		then:
		ret == false	
	}

	def "should add new items to wish lists"() {
		given:
		def wishList = new WishList(slug: 'bob-my-list-2')
		
		and:
		def newItem = new WishListItem(rollingStock: rollingStock('acme', '123459'), 
			notes: 'My new notes',
			priority: 'high')
		
		when:
		service.addItem(wishList, newItem)
		
		then:
		def doc = db.wishLists.findOne(slug: 'bob-my-list-2')
		
		and: "the last modified timestamp was updated"
		doc.lastModified != now
		
		and: "the new item was added to the list"
		doc.items.size() == 3
		doc.items.collect { it.itemId } == ['acme-123456', 'acme-123458', 'acme-123459']
		doc.items[2].itemId == 'acme-123459'
		doc.items[2].rollingStock == [slug: 'acme-123459', label: 'acme 123459']
		doc.items[2].notes == 'My new notes'
		doc.items[2].priority == 'high'
	}

	def "should remove items from wish lists"() {
		given:
		def wishList = new WishList(slug: 'bob-my-list-2')
		
		and:
		def item = new WishListItem(itemId: 'acme-123458')
		
		when:
		service.removeItem(wishList, item)
		
		then:
		def doc = db.wishLists.findOne(slug: 'bob-my-list-2')
		
		and: "the last modified timestamp was updated"
		doc.lastModified != now
		
		and: "the item was removed from the list"
		doc.items.size() == 1
		doc.items.collect { it.itemId } == ['acme-123456']
	}
	
	def "should move items between wish lists of the same user"() {
		given: "two wish lists"
		def sourceList = new WishList(slug: 'bob-my-list', owner: bob())
		def targetList = new WishList(slug: 'bob-my-list-2', owner: bob())
		
		and: "an item available in the source list"
		def item = new WishListItem(itemId: 'acme-123457')
		
		when:
		service.moveItem(sourceList, targetList, item)
		
		then: "the item is removed from source list"
		def src = db.wishLists.findOne(slug: 'bob-my-list')
		src.items.size() == 2
		src.items.collect { it.itemId }.sort() == ['acme-123455', 'acme-123456']
		
		and: "the last modified timestamp was updated"
		src.lastModified != now
		
		and: "the item is added to target list"
		def target = db.wishLists.findOne(slug: 'bob-my-list-2')
		target.items.size() == 3
		target.items.collect { it.itemId }.sort() == ['acme-123456', 'acme-123457', 'acme-123458']
		
		and: "the last modified timestamp was updated"
		target.lastModified != now
	}

	def "should update wish list items"() {
		given:
		def wishList = new WishList(slug: 'bob-my-list-2')
		
		and:
		def rs = new WeakDbRef<RollingStock>(slug: 'acme-123458', label: 'ACME 123458')
		def item = new WishListItem(itemId: 'acme-123458', 
			rollingStock: rs,
			notes: 'My updated notes', 
			priority: 'low')
		
		when:
		service.updateItem(wishList, item)
		
		then:
		def doc = db.wishLists.findOne(slug: 'bob-my-list-2')
		
		and: "the last modified timestamp was updated"
		doc.lastModified != now
		
		and:
		doc.items.size() == 2
		doc.items[1].itemId == 'acme-123458'
		doc.items[1].rollingStock == [slug: 'acme-123458', label: 'ACME 123458']
		doc.items[1].notes == 'My updated notes'
		doc.items[1].priority == 'low'
	}

	def "should change wish list visibility"() {
		given:
		def wishList = new WishList(slug: 'bob-my-list-2')
		
		when:
		service.changeVisibility(wishList, Visibility.PRIVATE)
		
		then:
		def doc = db.wishLists.findOne(slug: 'bob-my-list-2')
		doc.visibility == 'private'
		
		and: "the last modified timestamp was updated"
		doc.lastModified != now
	}

	def "should set a wish list as the default one"() {
		given:
		def owner = new Account(slug: 'bob')
	
		and:
		def wishList = new WishList(slug: 'bob-my-list', owner: bob())
		
		when:
		service.setAsDefault(owner, wishList)
		
		then:
		def defList = db.wishLists.findOne(slug: 'bob-my-list')
		defList.defaultList == true
		
		and: "the last modified timestamp was updated"
		defList.lastModified != now
		
		def otherList = db.wishLists.findOne(slug: 'bob-my-list-2')
		otherList.defaultList == false
		
		and: "the last modified timestamp was updated"
		otherList.lastModified != now
	}

	def "should create a new public wish list for the user"() {
		given:
		def owner = new Account(slug: 'bob', displayName: 'Bob')
		
		when:
		service.createNew(owner, 'My list 3')
		
		then:
		def doc = db.wishLists.findOne(slug: 'bob-my-list-3')
		doc != null
		doc.slug == 'bob-my-list-3'
		doc.name == 'My list 3'
		doc.owner == [slug: 'bob', label: 'Bob']
		doc.visibility == 'public'
		doc.defaultList == false
		
		and: "the last modified timestamp was set"
		doc.lastModified != null
	}
	
	def "should throw exception creating a new wish list with a name already used"() {
		given:
		def owner = new Account(slug: 'bob', displayName: 'Bob')
		
		when:
		service.createNew(owner, 'My list')
		
		then:
		thrown(DuplicateKeyException)
	}
	
	def "should remove a wish list"() {
		given:
		def wl = db.wishLists.findOne(slug: 'bob-my-list')
		def wishList = new WishList(id: wl._id)
		
		when:
		service.remove(wishList)
		
		then:
		def doc = db.wishLists.findOne(slug: 'bob-my-list')
		doc == null
	}
}