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
package com.trenako.test;

import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.Scale;
import com.trenako.utility.Cat;
import com.trenako.values.Category;
import com.trenako.values.Era;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.PowerMethod;

/**
 * Builder for test data. Probably an anti-pattern....
 * 
 * @author Carlo Micieli
 *
 */
public class TestDataBuilder {
	
	private final static LocalizedEnum<Era> eraIII = new LocalizedEnum<Era>(Era.III);
	private final static LocalizedEnum<Category> steamLocomotives = new LocalizedEnum<Category>(Category.STEAM_LOCOMOTIVES);
	private final static LocalizedEnum<Category> electricLocomotives = new LocalizedEnum<Category>(Category.ELECTRIC_LOCOMOTIVES);
	
	private final static LocalizedEnum<PowerMethod> ac = new LocalizedEnum<PowerMethod>(PowerMethod.AC);
	private final static LocalizedEnum<PowerMethod> dc = new LocalizedEnum<PowerMethod>(PowerMethod.DC);
	
	private final static Cat dcElectricLocomotives = new Cat(PowerMethod.DC, Category.ELECTRIC_LOCOMOTIVES);
	private final static Cat acElectricLocomotives = new Cat(PowerMethod.AC, Category.ELECTRIC_LOCOMOTIVES);
	
	private final static Brand acme = new Brand.Builder("ACME").slug("acme").build();
	private final static Brand roco = new Brand.Builder("Roco").slug("roco").build();
	private final static Brand marklin = new Brand.Builder("Märklin").slug("marklin").build();
	
	private final static Railway fs = new Railway.Builder("FS").companyName("Ferrovie dello stato").country("it").build();
	private final static Railway db = new Railway.Builder("DB").companyName("Die bahn").country("de").build();
	private final static Scale scaleH0 = new Scale.Builder("H0").ratio(870).build();
	private final static Scale scaleN = new Scale.Builder("N").ratio(1600).build();
	
	public static LocalizedEnum<Era> eraIII() { return eraIII; }
	public static LocalizedEnum<Category> steamLocomotives() { return steamLocomotives; }
	public static LocalizedEnum<Category> electricLocomotives() { return electricLocomotives; }
	public static LocalizedEnum<PowerMethod> ac() { return ac; } 
	public static LocalizedEnum<PowerMethod> dc() { return dc; }
	public static Cat dcElectricLocomotives() { return dcElectricLocomotives; }
	public static Cat acElectricLocomotives() { return acElectricLocomotives; }
	
	public static Brand acme() { return acme; }
	public static Brand roco() { return roco; }
	public static Brand marklin() { return marklin; }
	
	public static Railway fs() { return fs; }
	public static Railway db() { return db; }
	public static Scale scaleH0() { return scaleH0; }
	public static Scale scaleN() { return scaleN; }
}
