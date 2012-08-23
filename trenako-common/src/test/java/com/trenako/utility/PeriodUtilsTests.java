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

import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.junit.Test;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class PeriodUtilsTests {
	static final String YEARS_LABEL = "interval.years.more.label";
	static final String YEAR_LABEL = "interval.years.one.label";
	static final String MONTHS_LABEL = "interval.months.more.label";
	static final String MONTH_LABEL = "interval.months.one.label";
	static final String WEEKS_LABEL = "interval.weeks.more.label";
	static final String WEEK_LABEL = "interval.weeks.one.label";
	static final String DAYS_LABEL = "interval.days.more.label";
	static final String DAY_LABEL = "interval.days.one.label";
	static final String HOURS_LABEL = "interval.hours.more.label";
	static final String HOUR_LABEL = "interval.hours.one.label";
	static final String MINUTES_LABEL = "interval.minutes.more.label";
	static final String MINUTE_LABEL = "interval.minutes.one.label";

	@Test
	public void shouldCalculatePeriodForYears() {
		int numberOfYears = 3;
		DateTime start = new DateTime(2010, 1, 1, 10, 30);
		DateTime end = start.plusYears(numberOfYears);

		Pair<String, Integer> yearsPair = PeriodUtils.period(start, end);
		
		assertEquals(YEARS_LABEL, yearsPair.getKey());
		assertEquals(numberOfYears, (int) yearsPair.getValue());
	}

	@Test
	public void shouldCalculatePeriodForOneYear() {
		int numberOfYears = 1;
		DateTime start = new DateTime(2010, 1, 1, 10, 30);
		DateTime end = start.plusYears(numberOfYears);
		
		Pair<String, Integer> yearsPair = PeriodUtils.period(start, end);
		
		assertEquals(YEAR_LABEL, yearsPair.getKey());
		assertEquals(numberOfYears, (int) yearsPair.getValue());
	}

	@Test
	public void shouldCalculatePeriodForMonths() {
		int numberOfMonths = 11;
		DateTime start = new DateTime(2010, 1, 1, 10, 30);
		DateTime end = start.plusMonths(numberOfMonths);

		Pair<String, Integer> monthPair = PeriodUtils.period(start, end);
		
		assertEquals(MONTHS_LABEL, monthPair.getKey());
		assertEquals(numberOfMonths, (int) monthPair.getValue());
	}

	@Test
	public void shouldCalculatePeriodForOneMonth() {
		int numberOfMonths = 1;
		DateTime start = new DateTime(2010, 1, 1, 10, 30);
		DateTime end = start.plusMonths(numberOfMonths);

		Pair<String, Integer> monthPair = PeriodUtils.period(start, end);
		
		assertEquals(MONTH_LABEL, monthPair.getKey());
		assertEquals(numberOfMonths, (int) monthPair.getValue());
	}

	@Test
	public void shouldCalculatePeriodForWeeks() {
		int numberOfWeeks = 3;
		DateTime start = new DateTime(2010, 1, 1, 10, 30);
		DateTime end = start.plusWeeks(numberOfWeeks);

		Pair<String, Integer> weekPair = PeriodUtils.period(start, end);
		
		assertEquals(WEEKS_LABEL, weekPair.getKey());
		assertEquals(numberOfWeeks, (int) weekPair.getValue());
	}

	@Test
	public void shouldCalculatePeriodForOneWeek() {
		int numberOfWeeks = 1;
		DateTime start = new DateTime(2010, 1, 1, 10, 30);
		DateTime end = start.plusWeeks(numberOfWeeks);

		Pair<String, Integer> weekPair = PeriodUtils.period(start, end);
		
		assertEquals(WEEK_LABEL, weekPair.getKey());
		assertEquals(numberOfWeeks, (int) weekPair.getValue());
	}

	@Test
	public void shouldCalculatePeriodForDays() {
		int numberOfDays = 3;
		DateTime start = new DateTime(2010, 1, 1, 10, 30);
		DateTime end = start.plusDays(numberOfDays);

		Pair<String, Integer> dayPair = PeriodUtils.period(start, end);
		
		assertEquals(DAYS_LABEL, dayPair.getKey());
		assertEquals(numberOfDays, (int) dayPair.getValue());
	}

	@Test
	public void shouldCalculatePeriodForOneDay() {
		int numberOfDays = 1;
		DateTime start = new DateTime(2010, 1, 1, 10, 30);
		DateTime end = start.plusDays(numberOfDays);

		Pair<String, Integer> dayPair = PeriodUtils.period(start, end);
		
		assertEquals(DAY_LABEL, dayPair.getKey());
		assertEquals(numberOfDays, (int) dayPair.getValue());
	}
	
	@Test
	public void shouldCalculatePeriodForHours() {
		int numberOfHours = 23;
		DateTime start = new DateTime(2010, 1, 1, 10, 30);
		DateTime end = start.plusHours(numberOfHours);

		Pair<String, Integer> hourPair = PeriodUtils.period(start, end);
		
		assertEquals(HOURS_LABEL, hourPair.getKey());
		assertEquals(numberOfHours, (int) hourPair.getValue());
	}

	@Test
	public void shouldCalculatePeriodForOneHour() {
		int numberOfHours = 1;
		DateTime start = new DateTime(2010, 1, 1, 10, 30);
		DateTime end = start.plusHours(numberOfHours);

		Pair<String, Integer> hourPair = PeriodUtils.period(start, end);

		assertEquals(HOUR_LABEL, hourPair.getKey());
		assertEquals(numberOfHours, (int) hourPair.getValue());
	}	

	@Test
	public void shouldCalculatePeriodForMinutes() {
		int numberOfMinutes = 53;
		DateTime start = new DateTime(2010, 1, 1, 10, 30);
		DateTime end = start.plusMinutes(numberOfMinutes);

		Pair<String, Integer> minutePair = PeriodUtils.period(start, end);

		assertEquals(MINUTES_LABEL, minutePair.getKey());
		assertEquals(numberOfMinutes, (int) minutePair.getValue());
	}

	@Test
	public void shouldCalculatePeriodForOneMinute() {
		int numberOfMinutes = 1;
		DateTime start = new DateTime(2010, 1, 1, 10, 30);
		DateTime end = start.plusMinutes(numberOfMinutes);

		Pair<String, Integer> minutePair = PeriodUtils.period(start, end);

		assertEquals(MINUTE_LABEL, minutePair.getKey());
		assertEquals(numberOfMinutes, (int) minutePair.getValue());
	}
}
