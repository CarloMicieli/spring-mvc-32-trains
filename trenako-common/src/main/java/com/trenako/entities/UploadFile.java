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

/**
 * This class represents a container for an upload file container.
 * <p>
 * The system store the file as arrays of bytes, this class will
 * store the content type as well. 
 * </p>
 * 
 * @author Carlo Micieli
 *
 */
public class UploadFile {
	private byte[] content;
	private String mediaType;
	
	/**
	 * Creates an empty {@code UploadFile}.
	 */
	public UploadFile() {
	}
	
	/**
	 * Creates a new {@code UploadFile}.
	 * @param content the file content
	 * @param mediaType the media type
	 */
	public UploadFile(byte[] content, String mediaType) {
		this.content = content;
		this.mediaType = mediaType;
	}
	
	/**
	 * Returns the {@code UploadFile} binary content.
	 * @return the binary content
	 */
	public byte[] getContent() {
		return content;
	}
	
	/**
	 * Sets the {@code UploadFile} binary content.
	 * @param content the binary content
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	/**
	 * Returns the {@code UploadFile} media type.
	 * @return the media type
	 */
	public String getMediaType() {
		return mediaType;
	}
	
	/**
	 * Sets the {@code UploadFile} media type.
	 * @param mediaType the media type
	 */
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
}
