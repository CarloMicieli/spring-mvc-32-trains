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
package com.trenako.entities;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trenako.format.annotations.IntegerFormat;
import com.trenako.format.annotations.IntegerFormat.Style;
import com.trenako.mapping.DbReferenceable;
import com.trenako.mapping.LocalizedField;
import com.trenako.utility.Slug;
import com.trenako.validation.constraints.ContainsDefault;
import com.trenako.values.Standard;

/**
 * It represents a {@code Scale} of model railway.
 * <p>
 * In order to avoid the {@code double} rounding problems this
 * class is using integer values internally.
 * </p>
 * <p>
 * The class provides methods with the correct values for both
 * {@code ratio} and {@code gauge}.
 * </p>
 *
 * @author Carlo Micieli
 */
@Document(collection = "scales")
public class Scale implements DbReferenceable {

    public final static BigDecimal GAUGE_FACTOR = BigDecimal.valueOf(100);
    public final static BigDecimal RATIO_FACTOR = BigDecimal.valueOf(10);

    @Id
    private ObjectId id;

    @NotBlank(message = "scale.name.required")
    @Size(max = 10, message = "scale.name.size.notmet")
    @Indexed(unique = true)
    private String name;

    @NotNull(message = "scale.description.required")
    @ContainsDefault(message = "scale.description.default.required")
    private LocalizedField<String> description;

    @Indexed(unique = true)
    private String slug;

    @Range(min = 80, max = 2200, message = "scale.ratio.range.notmet")
    @IntegerFormat(style = Style.SCALE_RATIO)
    private int ratio;

    @Range(min = 0, max = 20000, message = "scale.gauge.range.notmet")
    @IntegerFormat(style = Style.SCALE_GAUGE)
    private int gauge;

    @Indexed
    private Set<String> standards;

    private Set<String> powerMethods;

    private boolean narrow;
    private Date lastModified;

    /**
     * Creates a new empty {@code Scale}.
     */
    public Scale() {
    }

    /**
     * Creates a new {@code Scale}.
     *
     * @param name the name
     */
    public Scale(String name) {
        this.name = name;
    }

    /**
     * Creates a new {@code Scale} with the provided id.
     *
     * @param name the name
     */
    public Scale(ObjectId id) {
        this.id = id;
    }

    private Scale(Builder b) {
        this.name = b.name;
        this.description = b.description;
        this.ratio = b.ratio;
        this.gauge = b.gauge;
        this.narrow = b.narrow;
        this.slug = b.slug;
        this.powerMethods = b.powerMethods;
    }

    /**
     * It represents a {@code Scale} builder class.
     *
     * @author Carlo Micieli
     */
    public static class Builder {
        // required fields
        private final String name;

        // optional fields
        private LocalizedField<String> description = null;
        private Set<String> powerMethods = null;
        private String slug = null;
        private int ratio = 0;
        private int gauge = 0;
        private boolean narrow = false;

        public Builder(String name) {
            this.name = name;
        }

        public Builder slug(String slug) {
            this.slug = slug;
            return this;
        }

        public Builder description(String desc) {
            if (this.description == null) {
                this.description = new LocalizedField<String>(desc);
            } else {
                this.description.setDefault(desc);
            }
            return this;
        }

        public Builder description(Locale lang, String desc) {
            if (this.description == null) {
                this.description = new LocalizedField<String>();
            }
            this.description.put(lang, desc);
            return this;
        }

        public Builder ratio(int ratio) {
            this.ratio = ratio;
            return this;
        }

        public Builder ratio(BigDecimal ratio) {
            this.ratio = ratio
                    .multiply(RATIO_FACTOR).intValue();
            return this;
        }

        public Builder gauge(int gauge) {
            this.gauge = gauge;
            return this;
        }

        public Builder gauge(BigDecimal gauge) {
            this.gauge = gauge
                    .multiply(GAUGE_FACTOR).intValue();
            return this;
        }

        public Builder powerMethods(String... powerMethods) {
            this.powerMethods = new HashSet<String>(Arrays.asList(powerMethods));
            return this;
        }

        public Builder narrow(boolean n) {
            narrow = n;
            return this;
        }

        public Scale build() {
            return new Scale(this);
        }
    }

    /**
     * Returns the unique id.
     *
     * @return the unique id
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Sets the {@code Scale} id.
     *
     * @param id the unique id
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * Returns the scale name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the scale name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a label for the current {@code Scale}.
     * <p>
     * The application will show this value as string representation
     * for the current {@code Scale} instead of the usual {@code Scale#toString()}.
     * </p>
     *
     * @return the label string
     */
    @Override
    public String getLabel() {
        return new StringBuffer()
                .append(getName())
                .append(" (")
                .append(getRatioText())
                .append(")")
                .toString();
    }

    /**
     * Returns the {@code Scale} slug.
     *
     * @return the slug
     */
    @Override
    public String getSlug() {
        if (slug == null) {
            slug = Slug.encode(name);
        }
        return slug;
    }

    /**
     * Returns the {@code Scale} description.
     *
     * @return the {@code Scale} description
     */
    public LocalizedField<String> getDescription() {
        return description;
    }

    /**
     * Sets the {@code Scale} description.
     *
     * @param description the {@code Scale} description
     */
    public void setDescription(LocalizedField<String> description) {
        this.description = description;
    }

    /**
     * Returns the {@code Scale} power methods list.
     *
     * @return the power methods list
     */
    public Set<String> getPowerMethods() {
        return powerMethods;
    }

    /**
     * Sets the {@code Scale} power methods list.
     *
     * @param powerMethods the power methods list
     */
    public void setPowerMethods(Set<String> powerMethods) {
        this.powerMethods = powerMethods;
    }

    /**
     * Returns the ratio of a linear dimension of the
     * model to the same dimension of the original.
     *
     * @return the scale ratio
     */
    public int getRatio() {
        return ratio;
    }

    /**
     * Returns the {@code Scale} ratio in millimeters,
     *
     * @return the ratio
     */
    public BigDecimal ratio() {
        return (new BigDecimal(getRatio()))
                .divide(RATIO_FACTOR);
    }

    /**
     * Sets the ratio of a linear dimension of the
     * model to the same dimension of the original.
     *
     * @param ratio the scale ratio
     */
    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    /**
     * Sets the {@code Scale} ratio from a {@link java.math.BigDecimal}
     * value.
     *
     * @param ratio the {@code Scale} ratio
     */
    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio
                .multiply(RATIO_FACTOR).intValue();
    }

    /**
     * Returns the the distance between the two rails forming a railroad track.
     *
     * @return the gauge.
     */
    public int getGauge() {
        return gauge;
    }

    /**
     * Returns the {@code Scale} gauge in millimeters.
     *
     * @return the gauge
     */
    public BigDecimal gauge() {
        return (new BigDecimal(getGauge()))
                .divide(GAUGE_FACTOR);
    }

    /**
     * Sets the distance between the two rails forming a railroad track
     *
     * @param gauge the gauge
     */
    public void setGauge(int gauge) {
        this.gauge = gauge;
    }

    /**
     * Indicates whether has track gauge narrower than the standard
     * gauge railways or not.
     *
     * @return {@code true} if the scale is narrow; {@code false} otherwise
     */
    public boolean isNarrow() {
        return narrow;
    }

    /**
     * Indicates whether has track gauge narrower than the standard
     * gauge railways or not.
     *
     * @param isNarrow {@code true} if the scale is narrow; {@code false} otherwise
     */
    public void setNarrow(boolean isNarrow) {
        this.narrow = isNarrow;
    }

    /**
     * Sets the list of {@link Standard} that include this
     * {@code Scale}.
     *
     * @param standards the list of standards
     */
    public void setStandards(Set<String> standards) {
        this.standards = standards;
    }

    /**
     * Returns the list of {@link Standard} that include this
     * {@code Scale}.
     *
     * @return the list of standards
     */
    public Set<String> getStandards() {
        return standards;
    }

    /**
     * Adds a new standard to this scale.
     *
     * @param standard a standard
     */
    public void addStandard(Standard standard) {
        if (standards == null) {
            standards = new HashSet<String>();
        }
        standards.add(standard.toString());
    }

    /**
     * Returns a string with the standards list.
     *
     * @return the the standards list
     */
    public String getStandardsList() {
        if (standards == null || standards.size() == 0) {
            return "";
        }
        return standards.toString();
    }

    /**
     * Returns the string representation for this scale ratio.
     *
     * @return the scale ratio as string.
     */
    public String getRatioText() {
        return new StringBuilder()
                .append("1:")
                .append(this.ratio().toString())
                .toString();
    }

    /**
     * Returns the last modification timestamp.
     *
     * @return the timestamp
     */
    public Date getLastModified() {
        return lastModified;
    }

    /**
     * Sets the last modification timestamp.
     *
     * @param lastModified the timestamp
     */
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("scale{")
                .append("name: ")
                .append(getName())
                .append(", ratio: ")
                .append(getRatioText())
                .append("}")
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Scale)) return false;

        Scale other = (Scale) obj;
        return new EqualsBuilder()
                .append(name, other.name)
                .append(ratio, other.ratio)
                .append(narrow, other.narrow)
                .append(gauge, other.gauge)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(15, 37)
                .append(name)
                .append(ratio)
                .append(narrow)
                .append(gauge)
                .hashCode();
    }
}