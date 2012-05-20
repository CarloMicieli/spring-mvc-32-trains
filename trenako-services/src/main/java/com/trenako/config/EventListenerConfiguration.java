package com.trenako.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.trenako.listeners.BrandsEventListener;
import com.trenako.listeners.RailwaysEventListener;
import com.trenako.listeners.RollingStocksEventListener;
import com.trenako.listeners.ScalesEventListener;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Configuration
public class EventListenerConfiguration {

	public @Bean BrandsEventListener brandsEventListener() {
		return new BrandsEventListener();
	}
	
	public @Bean RailwaysEventListener railwaysEventListener() {
		return new RailwaysEventListener();
	}

	public @Bean RollingStocksEventListener rollingStocksEventListener() {
		return new RollingStocksEventListener();
	}
	
	public @Bean ScalesEventListener scalesEventListener() {
		return new ScalesEventListener();
	}
	
}
