package com.trenako.web.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

import com.trenako.DeliveryDateFormatException;
import com.trenako.values.DeliveryDate;

/**
 * It represents a {@code DeliveryDate} property editor.
 * @author Carlo Micieli
 *
 */
public class DeliveryDatePropertyEditor extends PropertyEditorSupport {
	
	private final boolean allowEmpty;
	
	/**
	 * Creates a new {@code DeliveryDatePropertyEditor}.
	 * @param allowEmpty if empty strings should be allowed
	 */
	public DeliveryDatePropertyEditor(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (allowEmpty && !StringUtils.hasText(text)) {
			setValue(null);
			return;
		}
	
		try {
			DeliveryDate dd = DeliveryDate.parseString(text);
			setValue(dd);
		}
		catch (DeliveryDateFormatException e) {
			throw new IllegalArgumentException("'" + text + "' is not a valid delivery date");
		}
	}
	
}
