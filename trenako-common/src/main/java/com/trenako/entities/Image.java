package com.trenako.entities;

/**
 * It represents an image as stored in the database.
 * @author Carlo Micieli
 *
 */
public class Image {
	private byte[] image;
	private String mediaType;
	
	Image() {
	}
	
	/**
	 * Creates a new {@code Image}.
	 * @param mediaType the media type
	 * @param image the image binary content
	 */
	public Image(String mediaType, byte[] image) {
		this.mediaType = mediaType;
		this.image = image;
	}

	/**
	 * Returns the {@code Image} binary content.
	 * @return the binary content
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * Sets the {@code Image} binary content.
	 * @param image the binary content
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}

	/**
	 * Returns the {@code Image} media type.
	 * @return the media type
	 */
	public String getMediaType() {
		return mediaType;
	}

	/**
	 * Sets the {@code Image} media type.
	 * @param mediaType the media type
	 */
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
}
