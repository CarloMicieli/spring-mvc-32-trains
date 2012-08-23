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

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.base.BaseSingleFieldPeriod;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.ImmutablePair;

import static org.joda.time.Years.yearsBetween;
import static org.joda.time.Months.monthsBetween;
import static org.joda.time.Weeks.weeksBetween;
import static org.joda.time.Days.daysBetween;
import static org.joda.time.Hours.hoursBetween;
import static org.joda.time.Minutes.minutesBetween;

/**
 * An utility class to calculate periods between a date and the current
 * timestamp.
 * 
 * @author Carlo Micieli
 *
 */
public class PeriodUtils {
	
	/**
	 * Returns a pair of label and value with the period duration between
	 * the provided date and the current timestamp.
	 * 
	 * @param startDate the starting date and time
	 * @return a pair of label and value
	 */
	public static Pair<String, Integer> periodUntilNow(Date startDate) {
		DateTime start = new DateTime(startDate);
		DateTime now = DateTime.now();
		return period(start, now);
	}

	static Pair<String, Integer> period(DateTime start, DateTime now) {
		Pair<String, Integer> p = null;

		p = periodValue(yearsBetween(start, now));
		if (p != null) {
			return p;
		}

		p = periodValue(monthsBetween(start, now));
		if (p != null) {
			return p;
		}

		p = periodValue(weeksBetween(start, now));
		if (p != null) {
			return p;
		}

		p = periodValue(daysBetween(start, now));
		if (p != null) {
			return p;
		}

		p = periodValue(hoursBetween(start, now));
		if (p != null) {
			return p;
		}

		p = periodValue(minutesBetween(start, now));
		if (p != null) {
			return p;
		}

		return new ImmutablePair<String, Integer>("interval.minutes.one.label", 1);	
	}

	private static Pair<String, Integer> periodValue(BaseSingleFieldPeriod field) {
		String name = field.getPeriodType().getName().toLowerCase();
		int value = field.get(field.getFieldType());
		if (value > 1) {
			return new ImmutablePair<String, Integer>("interval." + name + ".more.label",  value);
		}
		else if (value > 0) {
			return new ImmutablePair<String, Integer>("interval." + name + ".one.label",  1);
		}

		return null;
	}
}
