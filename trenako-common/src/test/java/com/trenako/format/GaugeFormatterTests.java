package com.trenako.format;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Locale;

import org.junit.Test;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class GaugeFormatterTests {

	GaugeFormatter formatter = new GaugeFormatter();
	
	@Test
	public void shouldParseValues() throws ParseException {
	
		int a = formatter.parse("100,85", Locale.ITALIAN);
		assertEquals(10085, a);
		
		int b = formatter.parse("100.85", Locale.US);
		assertEquals(10085, b);
	}

	@Test
	public void shouldPrintValues() {
		String a = formatter.print(10085, Locale.ITALIAN);
		assertEquals("100,85", a);
		
		String b = formatter.print(10085, Locale.US);
		assertEquals("100.85", b);		
	}
}
