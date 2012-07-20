package com.trenako.results;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ImmutableRangeRequest implements RangeRequest {

	private final ObjectId sinceId;
	private final ObjectId maxId;
	private final Sort sort;
	private final int count;
	
	public ImmutableRangeRequest(int count, ObjectId sinceId, ObjectId maxId, Sort sort) {
		this.count = count;
		this.sinceId = sinceId;
		this.maxId = maxId;
		this.sort = sort;
	}
	
	@Override
	public ObjectId getSinceId() {
		return sinceId;
	}

	@Override
	public ObjectId getMaxId() {
		return maxId;
	}

	@Override
	public Order getFirstOrder() {
		return null;
	}

	@Override
	public Sort getSort() {
		return sort;
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public RangeRequest immutableRange() {
		return this;
	}

}
