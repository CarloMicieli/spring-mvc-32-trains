package com.trenako.web.infrastructure;

import java.util.Map;

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
	 * Builds the query parameters for the provided {@code RangeRequest}.
	 * @param rangeRequest the {@code RangeRequest}
	 * @return the Url
	 */
	public static String buildQueryParams(RangeRequest rangeRequest) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("?")
			.append("size=").append(rangeRequest.getSize());
		
		if (rangeRequest.getSort() != RangeRequest.DEFAULT_SORT) {
			
			Order ord = rangeRequest.getFirstOrder();
						
			sb.append("&sort=").append(ord.getProperty())
				.append("&").append(RangeRequest.ORDER_NAME).append("=").append(ord.getDirection());
		}
				
		return sb.toString();
	}
	
	/**
	 * Builds the query parameters for the provided parameters {@code Map}.
	 * @param params the parameters
	 * @return the query parameters
	 */
	public static String buildQueryParams(Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		
		boolean first = true;
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			if (first) {
				sb.append("?");
				first = false;
			}
			else {
				sb.append("&");
			}
			sb.append(entry.getKey()).append("=").append(entry.getValue().toString());
		}
		
		return sb.toString();
		
	}
}
