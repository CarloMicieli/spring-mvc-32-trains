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
package com.trenako.web.controllers.form;

import static com.trenako.web.security.UserContext.*;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.trenako.entities.Account;
import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;
import com.trenako.mapping.WeakDbRef;
import com.trenako.services.FormValuesService;
import com.trenako.utility.Slug;
import com.trenako.values.Category;
import com.trenako.values.DeliveryDate;
import com.trenako.values.Era;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.PowerMethod;
import com.trenako.web.images.validation.Image;
import com.trenako.web.security.UserContext;

/**
 * It represent a creation/editing form for {@code RollingStock}.
 * @author Carlo Micieli
 *
 */
public class RollingStockForm {
	
	@Valid
	private RollingStock rs;
	
	private String tags;
	
	@Image(message = "rollingStock.image.notValid")
	private MultipartFile file;
	
	private FormValuesService valuesService;
	
	/**
	 * Creates a new empty {@code RollingStockForm}.
	 */
	public RollingStockForm() {
	}

	/**
	 * Creates a new multipart form for the provided {@code RollingStock}. 
	 * @param rs the {@code RollingStock}
	 * @param valuesService the service to fill the drop down lists
	 * @param file the {@code RollingStock} image file
	 */
	public RollingStockForm(RollingStock rs, 
			FormValuesService valuesService, 
			MultipartFile file) {
		this.rs = rs;
		this.valuesService = valuesService;
		this.file = file;
		
		if (rs.getTags() != null) {
			this.tags = StringUtils.join(rs.getTags(), ",");
		}
	}
	
	/**
	 * Creates a new form for the provided {@code RollingStock}. 
	 * @param rs the {@code RollingStock}
	 * @param valuesService the service to fill the drop down lists
	 */
	public static RollingStockForm newForm(RollingStock rs, FormValuesService valuesService) {
		return new RollingStockForm(rs, valuesService, null);
	}	
	
	public static RollingStockForm rejectForm(RollingStockForm form, FormValuesService valuesService) {
		form.valuesService = valuesService;
		return form;
	}
	
	/**
	 * Builds a new {@code RollingStock} using the values posted with the form.
	 * @param valuesService the values service
	 * @param userContext the security context
	 * @param modifiedAt the timestamp when the {@code RollingStock} was modified
	 * @return a new {@code RollingStock}
	 */
	public RollingStock buildRollingStock(FormValuesService valuesService, UserContext userContext, Date modifiedAt) {
		
		Account user = authenticatedUser(userContext);
		if (user == null) {
			return null;
		}
		
		Brand brand = lookupBrand(valuesService, getRs().getBrand());
		Railway railway = lookupRailway(valuesService, getRs().getRailway());
		Scale scale = lookupScale(valuesService, getRs().getScale());
		
		String country = railway != null ? railway.getCountry() : null;

		RollingStock rs = new RollingStock.Builder(brand, getRs().getItemNumber())
			.id(getRs().getId())
			.railway(railway)
			.scale(scale)
			.description(getRs().getDescription())
			.details(getRs().getDetails())
			.country(country)
			.era(getRs().getEra())
			.category(getRs().getCategory())
			.tags(getTagsSet())
			.totalLength(getRs().getTotalLength())
			.powerMethod(getRs().getPowerMethod())
			.upcCode(getRs().getUpcCode())
			.modifiedBy(user)
			.lastModified(modifiedAt)
			.build();
		
		return rs;
	}

	/**
	 * Returns the {@code RollingStock} managed by this form.
	 * @return the {@code RollingStock}
	 */
	public RollingStock getRs() {
		return rs;
	}

	/**
	 * Sets the {@code RollingStock} managed by this form.
	 * @param rs the {@code RollingStock}
	 */
	public void setRs(RollingStock rs) {
		this.rs = rs;
	}

	/**
	 * Returns the {@code Multipart} file.
	 * @return the file
	 */
	public MultipartFile getFile() {
		return file;
	}

	/**
	 * Sets the {@code Multipart} file.
	 * @param file the file
	 */
	public void setFile(MultipartFile file) {
		this.file = file;
	}

	/**
	 * Returns the list of tags.
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * Sets the list of tags.
	 * @param tags the tags
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	public Iterable<Brand> getBrandsList() {
		return valuesService.brands();
	}
	
	public Iterable<Railway> getRailwaysList() {
		return valuesService.railways();
	}
	
	public Iterable<Scale> getScalesList() {
		return valuesService.scales();
	}
	
	public Iterable<LocalizedEnum<Category>> getCategoriesList() {
		return valuesService.categories();
	}
	
	public Iterable<LocalizedEnum<Era>> getErasList() {
		return valuesService.eras();
	}
	
	public Iterable<LocalizedEnum<PowerMethod>> getPowerMethodsList() {
		return valuesService.powerMethods();
	}
	
	public Iterable<DeliveryDate> getDeliveryDates() {
		return valuesService.deliveryDates();
	}

	/**
	 * Returns the tags set built parsing a comma separated string.
	 * @return the tags
	 */
	public SortedSet<String> getTagsSet() {
		if (StringUtils.isBlank(getTags())) {
			return null;
		}
		
		String[] tags = getTags().split(",");
		SortedSet<String> tagsList = new TreeSet<String>();
		for (String tag : tags) {
			tagsList.add(Slug.encode(tag.trim()));
		}
		return tagsList;
	}
	
	private Brand lookupBrand(FormValuesService valuesService, WeakDbRef<Brand> ref) {
		if (!withValue(ref)) {
			return null;
		}
		return valuesService.getBrand(ref.getSlug());
	}
	
	private Scale lookupScale(FormValuesService valuesService, WeakDbRef<Scale> ref) {
		if (!withValue(ref)) {
			return null;
		}
		return valuesService.getScale(ref.getSlug());
	}
	
	private Railway lookupRailway(FormValuesService valuesService, WeakDbRef<Railway> ref) {
		if (!withValue(ref)) {
			return null;
		}
		return valuesService.getRailway(ref.getSlug());
	}

	private boolean withValue(WeakDbRef<?> ref) {
		return (ref != null && ref.getSlug() != null);
	}
}
