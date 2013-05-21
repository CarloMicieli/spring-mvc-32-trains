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

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * It represents a persistent login token.
 *
 * @author Carlo Micieli
 */
@Document(collection = "persistentLogins")
public class PersistentLogin {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String series;
    private Date date;
    private String tokenValue;
    private String username;

    /**
     * Creates a new empty {@code PersistentLogin} token.
     */
    public PersistentLogin() {
    }

    /**
     * Creates a new {@code PersistentLogin} token.
     *
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

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    @Override
    public String toString() {
        return new StringBuilder()
                .append(getUsername() + " ")
                .append(getSeries())
                .toString();
    }
}
