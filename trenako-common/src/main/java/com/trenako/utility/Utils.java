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

import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class Utils {

	private Utils() {
	}
	
	/**
	 * Returns a reverse version for the provided {@code List}. 
	 * @param list the original list
	 * @return the reverse iterable
	 */
	public static <T> Iterable<T> reverseIterable(final List<T> list) {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return new Iterator<T>() {

					private int pos = list.size() - 1;
					
					@Override
					public boolean hasNext() {
						return pos >= 0;
					}

					@Override
					public T next() {
						return list.get(pos--);
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
					
				};
			}
		};
	}
}
