package com.trenako.web.infrastructure;

import java.beans.PropertyEditorSupport;

import org.bson.types.ObjectId;
import org.springframework.util.StringUtils;

import com.trenako.entities.Scale;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ScalePropertyEditor extends PropertyEditorSupport {
	private final boolean allowEmpty;
	
	/**
	 * Creates a new {@code ScalePropertyEditor}.
	 * @param allowEmpty if empty strings should be allowed
	 */
	public ScalePropertyEditor(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (allowEmpty && !StringUtils.hasText(text)) {
			setValue(null);
			return;
		}
		
		if (!ObjectId.isValid(text)) {
			throw new IllegalArgumentException(text + " is not a valid ObjecId");
		}
		
		ObjectId id = new ObjectId(text);
		setValue(new Scale(id));
	}

}
