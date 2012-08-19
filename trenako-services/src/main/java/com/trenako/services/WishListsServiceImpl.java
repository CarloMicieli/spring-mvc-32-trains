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
package com.trenako.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.trenako.entities.Account;
import com.trenako.entities.RollingStock;
import com.trenako.entities.WishList;
import com.trenako.entities.WishListItem;
import com.trenako.values.Visibility;

import com.trenako.repositories.WishListsRepository;

/**
 * The concrete implementation of the {@code WishList} service.
 * @author Carlo Micieli
 *
 */
@Service("wishListsService")
public class WishListsServiceImpl implements WishListsService {

	private final WishListsRepository repo;
	
	/**
	 * Creates a new {@code WishListsServiceImpl}.
	 * @param repo the {@code WishList} repository
	 */
	@Autowired
	public WishListsServiceImpl(WishListsRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public Iterable<WishList> findByOwner(Account owner) {
		return repo.findByOwner(owner, false);
	}
		
	@Override
	public WishList findBySlug(String slug) {
		return repo.findBySlug(slug);
	}
	
	@Override
	public WishList findDefaultListByOwner(Account owner) {
		return repo.findDefaultListByOwner(owner);
	}
	
	@Override
	public boolean containsRollingStock(WishList wishList, RollingStock rs) {
		return repo.containsRollingStock(wishList, rs);
	}
	
	@Override
	public void addItem(WishList wishList, WishListItem newItem) {
		if (!StringUtils.hasText(newItem.getItemId())) {
			newItem.setItemId(newItem.getItemId());
		}
		
		repo.addItem(wishList, newItem);
	}
	
	@Override
	public void updateItem(WishList wishList, WishListItem item) {
		repo.updateItem(wishList, item);
	}
	
	@Override
	public void removeItem(WishList wishList, WishListItem item) {
		repo.removeItem(wishList, item);
	}
	
	@Override
	public void moveItem(WishList source, WishList target, WishListItem item) {
		if (source.equals(target)) {
			// the two lists are equals. No further actions.
			return;
		}
		
		if (!source.getOwner().equals(target.getOwner())) {
			throw new IllegalArgumentException("The lists have different owners");
		}
		
		repo.addItem(target, item);
		repo.removeItem(source, item);
	}
	
	@Override
	public void changeVisibility(WishList wishList, Visibility visibility) {
		repo.changeVisibility(wishList, visibility);
	}
	
	@Override
	public void setAsDefault(Account owner, WishList wishList) {
		repo.resetDefault(owner);
		repo.changeDefault(wishList, true);
	}
	
	@Override
	public void createNew(Account owner, String name) {
		WishList newList = new WishList(owner, name, Visibility.PUBLIC);
		repo.save(newList);
	}
	
	@Override
	public void remove(WishList wishList) {
		repo.remove(wishList);
	}
}