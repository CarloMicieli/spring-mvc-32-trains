package com.trenako.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PowerMethodTests {

	@Test
	public void shouldProduceDescription() {
		PowerMethod ac = PowerMethod.AC;
		assertEquals("ac", ac.getDescription());
		
		PowerMethod ads = PowerMethod.AC_DCC_SOUND;
		assertEquals("ac-dcc-sound", ads.getDescription());
	}
	
	@Test
	public void shouldParseAStringValue() {
		PowerMethod ads = PowerMethod.parse("ac-dcc-sound");
		assertEquals(PowerMethod.AC_DCC_SOUND, ads);
	}
}
