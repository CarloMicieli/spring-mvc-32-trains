package com.trenako.web.infrastructure;

import org.springframework.data.domain.Sort.Order;

import com.trenako.results.RangeRequest;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RangeRequestQueryParamsBuilder {

	private RangeRequestQueryParamsBuilder() {
	}
	
	/**
	 * Builds the Url for the provided {@code RangeRequest}.
	 * @param rangeRequest the {@code RangeRequest}
	 * @return the Url
	 */
	public static String buildQueryParams(RangeRequest rangeRequest) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("?")
			.append("count=").append(rangeRequest.getCount());
		
		if (rangeRequest.getSort() != RangeRequest.DEFAULT_SORT) {
			
			Order ord = rangeRequest.getFirstOrder();
						
			sb.append("&sort=").append(ord.getProperty())
				.append("&").append(RangeRequest.ORDER_NAME).append("=").append(ord.getDirection());
		}
				
		return sb.toString();
	}
}
