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

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trenako.validation.constraints.SupportedImage;

/**
 * It represents an image as stored in the database.
 * @author Carlo Micieli
 *
 */
@Document(collection = "images")
public class Image {
	@Id
	private ObjectId _id;
	
	@Indexed
	@NotNull(message = "image.parentId.required")
	private ObjectId parentId;
	
	private String filename;
	
	@NotNull(message = "image.is.empty")
	@SupportedImage(message = "image.invalid.file")
	private UploadFile image;
	
	@SupportedImage(message = "image.invalid.file")
	private UploadFile thumbnail;
	
	/**
	 * Creates an empty {@code Image}.
	 */
	public Image() {
	}
	
	/**
	 * Creates a new {@code Image}.
	 * @param parentId 
	 * @param image 
	 */
	public Image(ObjectId parentId, UploadFile image) {
		this.parentId = parentId;
		this.image = image;
	}
	
	public Image(Builder b) {
		this.parentId = b.parentId;
		this.image = b.image;
		this.filename = b.filename;
		this.thumbnail = b.thumbnail;
	}
	
	public static class Builder {
		private final ObjectId parentId;
		private final UploadFile image;
		
		private String filename = "";
		private UploadFile thumbnail = null;
		
		public Builder(ObjectId parentId, UploadFile image) {
			this.image = image;
			this.parentId = parentId;
		}
		
		public Builder(ObjectId parentId, byte[] content, String mediaType) {
			this.image = new UploadFile(content, mediaType);
			this.parentId = parentId;
		}
		
		public Builder thumbnail(byte[] content, String mediaType) {
			this.thumbnail  = new UploadFile(content, mediaType);
			return this;
		}
		
		public Builder thumbnail(UploadFile thumbnail) {
			this.thumbnail  = thumbnail;
			return this;
		}
		
		public Builder filename(String filename) {
			this.filename = filename;
			return this;
		}
		
		public Image build() {
			return new Image(this);
		}
	}
	
	public ObjectId getId() {
		return _id;
	}

	public void setId(ObjectId id) {
		this._id = id;
	}

	public ObjectId getParentId() {
		return parentId;
	}

	public void setParentId(ObjectId parentId) {
		this.parentId = parentId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public UploadFile getImage() {
		return image;
	}

	public void setImage(UploadFile image) {
		this.image = image;
	}

	public UploadFile getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(UploadFile thumbnail) {
		this.thumbnail = thumbnail;
	}
}
