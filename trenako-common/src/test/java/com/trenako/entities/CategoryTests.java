package com.trenako.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTests {

	@Test
	public void shouldProduceDescription() {
		Category el = Category.ELECTRIC_LOCOMOTIVES;
		assertEquals("electric-locomotives", el.getDescription());
		
		Category emu = Category.ELECTRIC_MULTIPLE_UNIT;
		assertEquals("electric-multiple-unit", emu.getDescription());
	}
	
	@Test
	public void shouldParseAStringValue() {
		Category el = Category.parse("electric-locomotives");
		assertEquals(Category.ELECTRIC_LOCOMOTIVES, el);
	}
}
