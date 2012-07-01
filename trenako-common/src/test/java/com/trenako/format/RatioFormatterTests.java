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
public class RatioFormatterTests {

	RatioFormatter formatter = new RatioFormatter();
	
	@Test
	public void shouldParseValues() throws ParseException {
	
		int a = formatter.parse("100,5", Locale.ITALIAN);
		assertEquals(1005, a);
		
		int b = formatter.parse("100.5", Locale.US);
		assertEquals(1005, b);
	}

	@Test
	public void shouldPrintValues() {
		String a = formatter.print(1005, Locale.ITALIAN);
		assertEquals("100,5", a);
		
		String b = formatter.print(1005, Locale.US);
		assertEquals("100.5", b);		
	}
}
