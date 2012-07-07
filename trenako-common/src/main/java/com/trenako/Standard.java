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
package com.trenako;

import java.util.Arrays;
import java.util.Collections;

/**
 * It represents the model railway standards enumeration.
 * @author Carlo Micieli
 *
 */
public enum Standard {
	/**
	 * {@code NEM}: these standards are published by the European 
	 * federation of national model railway associations ({@code MOROP}).
	 */
	NEM,
	
	/**
	 * {@code NMRA}: these standards are published by the North
	 * American model railroad association.
	 */
	NMRA,
	
	/**
	 * {@code British}: these standards are widely used by the 
	 * British model railways.
	 */
	BRITISH,

	/**
	 * {@code Japanese}: these standards are widely used by the 
	 * Japanese model railways.
	 */
	JAPANESE;

	public String label() {
		return name().toLowerCase().replace('_', '-');
	}
	
	public static Iterable<String> labels() {
		return Collections.unmodifiableList(
				Arrays.asList(NEM.label(), NMRA.label(), BRITISH.label(), JAPANESE.label()));
	}
}
