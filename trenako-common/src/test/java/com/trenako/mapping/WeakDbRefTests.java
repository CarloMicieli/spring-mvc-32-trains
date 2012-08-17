package com.trenako.mapping;

import static org.junit.Assert.*;

import org.junit.Test;

import com.trenako.entities.Brand;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class WeakDbRefTests {

	@Test
	public void shouldCreateWeakRefFromSlug() {
		WeakDbRef<Brand> ref = WeakDbRef.buildFromSlug("acme", Brand.class);
		assertNotNull(ref);
		assertEquals("{slug: acme, label: null}", ref.toCompleteString());
	}
	
	@Test
	public void shouldCheckWhetherWeakRefsAreFullyLoaded() {
		WeakDbRef<?> x = WeakDbRef.buildFromSlug("slug", Brand.class);
		assertFalse("Ref was loaded", x.isLoaded());

		WeakDbRef<?> y = WeakDbRef.buildRef(entity("slug", ""));
		assertFalse("Ref was loaded", y.isLoaded());
		
		WeakDbRef<?> z = WeakDbRef.buildRef(entity("slug", "label"));
		assertTrue("Ref was not loaded", z.isLoaded());
	}
	
	@Test
	public void shouldCreateWeakRef() {
		WeakDbRef<?> ref = WeakDbRef.buildRef(entity("slug", "label"));
		assertNotNull(ref);
		assertEquals("{slug: slug, label: label}", ref.toCompleteString());
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
	
	@Test
	public void shouldProduceStringRepresentationForWeakDbRefs() {
		WeakDbRef<?> ref = WeakDbRef.buildRef(entity("slug", "label"));
		assertEquals("slug", ref.toString());
		assertEquals("{slug: slug, label: label}", ref.toCompleteString());
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
