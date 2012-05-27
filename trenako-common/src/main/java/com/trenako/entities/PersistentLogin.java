package com.trenako.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Document(collection = "persistentLogins")
public class PersistentLogin {
	@Id
	private String series;
	private Date date;
	private String tokenValue;
	private String username;

	public PersistentLogin() {
	}
	
	/**
	 * Creates a new persistent login token.
	 * @param username
	 * @param series
	 * @param date
	 * @param tokenValue
	 */
	public PersistentLogin(String username, String series, Date date, String tokenValue) {
		this.username = username;
		this.series = series;
		this.date = date;
		this.tokenValue = tokenValue;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
