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
package com.trenako.utility;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class UtilsTests {
	
	@Test
	public void shouldReturnIteratorInReverseOrder() {
		Iterable<String> reversedList = Utils.reverseIterable(values(5));
		List<String> list = Utils.newList(reversedList);
		assertEquals("[item5, item4, item3, item2, item1]", list.toString());
	}
	
	@Test
	public void shouldProduceSublists() {
		Iterable<String> values =  values(10);
		
		List<String> sublist = Utils.newSublist(values, 5);
		
		assertEquals("[item1, item2, item3, item4, item5]", sublist.toString());
	}
	
	private List<String> values(int numberOfItems) {
		List<String> list = new ArrayList<String>(numberOfItems);
		for (int i = 1; i <= numberOfItems; i++) {
			list.add("item" + i);
		}
		return Collections.unmodifiableList(list);
	}
}
