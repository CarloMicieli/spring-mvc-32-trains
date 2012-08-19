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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.util.Assert;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class Utils {

	private Utils() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Creates an immutable {@code ArrayList} filled with 
	 * the {@code Iterable} elements.
	 * 
	 * @param elements the elements to be added
	 * @return the {@code ArrayList}
	 */
	public static <E> List<E> newList(final Iterable<E> elements) {
		List<E> list = new ArrayList<E>();
		for (E el : elements) {
			list.add(el);
		}
		return Collections.unmodifiableList(list);
	}
	
	/**
	 * Returns a reverse version for the provided {@code List}. 
	 * @param list the original list
	 * @return the reverse iterable
	 */
	public static <E> Iterable<E> reverseIterable(final List<E> list) {
		return new Iterable<E>() {
			@Override
			public Iterator<E> iterator() {
				return new Iterator<E>() {

					private int pos = list.size() - 1;
					
					@Override
					public boolean hasNext() {
						return pos >= 0;
					}

					@Override
					public E next() {
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

	/**
	 * Creates an immutable {@code ArrayList} with the first elements.
	 * @param elements the elements
	 * @param size the size of the new sublist
	 * @return the {@code ArrayList}
	 */
	public static <E> List<E> newSublist(Iterable<E> elements, int size) {
		Assert.isTrue(size > 0, "Size must be > 0");
		
		int i = 1;
		List<E> list = new ArrayList<E>();
		for (E el : elements) {
			list.add(el);
			if (i == size) {
				break;
			}
			i++;
		}
		return Collections.unmodifiableList(list);
	}
}
