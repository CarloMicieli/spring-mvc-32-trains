package com.trenako.web.images;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.trenako.entities.Image;
import com.trenako.entities.UploadFile;
import com.trenako.services.ImagesService;
import com.trenako.web.errors.NotFoundException;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class WebImageServiceTests {

	@Mock MultipartFile mockFile;
	@Mock ImageConverter mockConverter;
	@Mock ImagesService mockDb;
	WebImageService service;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new WebImageServiceImpl(mockDb, mockConverter);
	}

	@Test
	public void shouldSaveImages() throws IOException {
		final ObjectId parentId = new ObjectId();
		String filename = "image.jpg";
		String mediaType = "image/jpg";
		byte[] content = "content".getBytes();
		
		when(mockFile.getOriginalFilename()).thenReturn(filename);
		UploadFile file = new UploadFile(content, mediaType);

		ArgumentCaptor<Image> arg = ArgumentCaptor.forClass(Image.class);
		when(mockConverter.createImage(mockFile)).thenReturn(file);

		service.saveImage(parentId, mockFile);
		
		verify(mockConverter, times(1)).createImage(eq(mockFile));
		verify(mockDb, times(1)).saveImage(arg.capture());
		Image img = arg.getValue();
		assertEquals(filename, img.getFilename());
		assertNotNull(img.getImage());
		assertEquals(content, img.getImage().getContent());
		assertEquals(mediaType, img.getImage().getMediaType());
	}

	@Test
	public void shouldSaveImagesAndCreateThumb() throws IOException {
		final ObjectId parentId = new ObjectId();
		String filename = "image.jpg";
		String mediaType = "image/jpg";
		byte[] content = "content".getBytes();
		
		when(mockFile.getOriginalFilename()).thenReturn(filename);
		UploadFile file = new UploadFile(content, mediaType);
		UploadFile thumb = new UploadFile(content, mediaType);
		
		ArgumentCaptor<Image> arg = ArgumentCaptor.forClass(Image.class);
		when(mockConverter.createImage(mockFile)).thenReturn(file);
		when(mockConverter.createThumbnail(mockFile, 100)).thenReturn(thumb);

		service.saveImageWithThumb(parentId, mockFile, 100);
		
		verify(mockConverter, times(1)).createImage(eq(mockFile));
		verify(mockConverter, times(1)).createThumbnail(eq(mockFile), eq(100));
		
		verify(mockDb, times(1)).saveImage(arg.capture());
		Image img = arg.getValue();
		assertEquals(filename, img.getFilename());
		assertNotNull(img.getImage());
		assertEquals(content, img.getImage().getContent());
		assertEquals(mediaType, img.getImage().getMediaType());
		assertNotNull(img.getThumbnail());
		assertEquals(content, img.getThumbnail().getContent());
		assertEquals(mediaType, img.getThumbnail().getMediaType());
	}
	
	@Test
	public void shouldRenderImages() throws IOException {
		final ObjectId parentId = new ObjectId();
		final UploadFile uploadFile = new UploadFile();
		final Image img = new Image(parentId, uploadFile);
		final ResponseEntity<byte[]> value = new ResponseEntity<byte[]>(HttpStatus.CREATED);
		
		when(mockDb.getImage(eq(parentId))).thenReturn(img);
		when(mockConverter.renderImage(eq(uploadFile))).thenReturn(value);
		
		ResponseEntity<byte[]> resp = service.renderImageFor(parentId);
		
		verify(mockDb, times(1)).getImage(eq(parentId));
		assertNotNull(resp);
	}
	
	@Test(expected = NotFoundException.class)
	public void shouldThrowsExceptionWhenRenderingNotFoundImage() throws IOException {
		final ObjectId parentId = new ObjectId();
		when(mockDb.getImage(eq(parentId))).thenReturn(null);
		service.renderImageFor(parentId);
	}
	
	@Test
	public void shouldRenderThumbnails() throws IOException {
		final ObjectId parentId = new ObjectId();
		final UploadFile uploadFile = new UploadFile();
		final Image img = new Image.Builder(parentId, new UploadFile())
			.thumbnail(uploadFile)
			.build();
		final ResponseEntity<byte[]> value = new ResponseEntity<byte[]>(HttpStatus.CREATED);
		
		when(mockDb.getImage(eq(parentId))).thenReturn(img);
		when(mockConverter.renderImage(eq(uploadFile))).thenReturn(value);
		
		ResponseEntity<byte[]> resp = service.renderThumbnailFor(parentId);
		
		verify(mockDb, times(1)).getImage(eq(parentId));
		assertNotNull(resp);
	}
	
	@Test(expected = NotFoundException.class)
	public void shouldThrowsExceptionWhenRenderingNotFoundThumbnail() throws IOException {
		final ObjectId parentId = new ObjectId();
		when(mockDb.getImage(eq(parentId))).thenReturn(null);
		service.renderImageFor(parentId);
	}
}
