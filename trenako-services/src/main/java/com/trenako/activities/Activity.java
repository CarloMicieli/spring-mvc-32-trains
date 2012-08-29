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
package com.trenako.activities;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trenako.entities.Account;

/**
 * It represents an element in the user activity stream.
 * <p>
 * User activities have the following form:
 * <pre>
 * <blockquote>
 * Actor |verb| object [context]
 * </blockquote>
 * </pre>
 * Usually {@code Action}, {@code Verb} and {@code object} components
 * are mandatory. The {@code context} value is optional.
 * </p>
 *
 * @author Carlo Micieli
 *
 */
@Document(collection = "activityStream")
public class Activity {
	@Id
	private ObjectId id;

	@NotNull(message = "activity.actor.required")
	@Indexed
	private String actor;

	@NotNull(message = "activity.verb.required")
	private String verb;

	@NotNull(message = "activity.object.required")
	private ActivityObject object;

	private ActivityContext context;

	@Indexed
	private Date recorded;

	/**
	 * Creates an empty {@code Activity}.
	 */
	public Activity() {
	}

	/**
	 * Creates a new user {@code Activity} without a context.
	 * @param actor the {@code Activity} actor
	 * @param verb the {@code Activity} verb
	 * @param object the {@code Activity} object
	 */
	public Activity(Account actor, ActivityVerb verb, ActivityObject object) {
		this(actor, verb, object, null);
	}

	/**
	 * Creates a new user {@code Activity} with a context.
	 * @param actor the {@code Activity} actor
	 * @param verb the {@code Activity} verb
	 * @param object the {@code Activity} object
	 * @param context the {@code Activity} target
	 */
	public Activity(Account actor, ActivityVerb verb, ActivityObject object, ActivityContext context) {
		this.actor = actorId(actor);
		this.verb = activityVerb(verb);
		this.object = object;
		this.context = context;

		this.recorded = new Date();
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	/**
	 * Returns the value that identifies the primary object of the activity.
	 * @return the activity object
	 */
	public ActivityObject getObject() {
		return object;
	}

	/**
	 * Sets the value that identifies the primary object of the activity.
	 * @param object the activity object
	 */
	public void setObject(ActivityObject object) {
		this.object = object;
	}

	public ActivityContext getContext() {
		return context;
	}

	public void setContext(ActivityContext context) {
		this.context = context;
	}

	/**
	 * Returns the value that identifies the time at which the activity occurred.
	 * @return the time at which the activity occurred
	 */
	public Date getRecorded() {
		return recorded;
	}

	/**
	 * Sets the value that identifies the time at which the activity occurred.
	 * @param recorded the time at which the activity occurred
	 */
	public void setRecorded(Date recorded) {
		this.recorded = recorded;
	}

	@Override
	public String toString() {
		return new StringBuilder()
			.append("activity{actor: ")
			.append(getActor())
			.append(", verb: ")
			.append(getVerb())
			.append(", object: ")
			.append(getObject())
			.append(", context: ")
			.append(getContext())
			.append("}")
			.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof Activity)) return false;
		
		Activity other = (Activity) obj;
		return new EqualsBuilder()
			.append(this.actor, other.actor)
			.append(this.verb, other.verb)
			.append(this.object, other.object)
			.append(this.context, other.context)
			.isEquals();
	}	
	
	private static String actorId(Account actor) {
		return actor.getSlug();
	}

	private static String activityVerb(ActivityVerb verb) {
		return verb.label();
	}
}
