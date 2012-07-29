package com.trenako.mapping;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class WeakDbRefTests {

	@Test
	public void shouldCreateWeakRef() {
		WeakDbRef<?> ref = WeakDbRef.buildRef(entity("slug", "label"));
		assertNotNull(ref);
		assertEquals("{label=label, slug=slug}", ref.toString());
	}
	
	@Test
	public void shouldReadValues() {
		WeakDbRef<?> ref = WeakDbRef.buildRef(entity("slug", "label"));
		assertEquals("slug", ref.getSlug());
		assertEquals("label", ref.getLabel());
	}
	
	@Test
	public void shouldChecksIfTwoRefAreEquals() {
		WeakDbRef<?> a = WeakDbRef.buildRef(entity("slug", "label"));
		WeakDbRef<?> b = WeakDbRef.buildRef(entity("slug", "label"));
		assertTrue("Refs are different", a.equals(b));
		
		WeakDbRef<?> c = WeakDbRef.buildRef(entity("slug", "label"));
		WeakDbRef<?> d = WeakDbRef.buildRef(entity("slag", "labul"));
		assertFalse("Refs are equals", c.equals(d));
	}
		
	private DbReferenceable entity(final String slug, final String label)  {
		return new DbReferenceable() {
			@Override
			public String getSlug() {
				return slug;
			}

			@Override
			public String getLabel() {
				return label;
			}
		};
	}
}
