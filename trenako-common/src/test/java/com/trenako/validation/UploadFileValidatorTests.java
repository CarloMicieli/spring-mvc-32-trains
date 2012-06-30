package com.trenako.validation;

import static org.junit.Assert.*;

import javax.validation.ConstraintValidatorContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.entities.UploadFile;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class UploadFileValidatorTests {

	@Mock ConstraintValidatorContext mockContext;
	UploadFileValidator validator = new UploadFileValidator();
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldValidateEmptyUploads() {
		boolean rv = validator.isValid(null, mockContext);
		assertTrue(rv);
	}

	@Test
	public void shouldFailValidationIfMediaTypeNotAllowed() {
		UploadFile file = new UploadFile(null, "application/exe");
		boolean rv = validator.isValid(file, mockContext);
		assertFalse(rv);
	}
	
	@Test
	public void shouldValidateCorrectUploads() {
		UploadFile file = new UploadFile("content".getBytes(), "images/jpg");
		boolean rv = validator.isValid(file, mockContext);
		assertTrue(rv);
	}
}
