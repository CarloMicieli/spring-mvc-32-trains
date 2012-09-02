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
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trenako.entities.Account;
import com.trenako.entities.Collection;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.Comment;
import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;
import com.trenako.entities.WishList;
import com.trenako.entities.WishListItem;
import com.trenako.mapping.WeakDbRef;

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
	private final static Map<String, String> verbColors = initColors();
	
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
		this(actorId(actor), verb, object, context, new Date());
	}
	
	private Activity(String actor, ActivityVerb verb, ActivityObject object, ActivityContext context, Date recordAt) {
		this.actor = actor;
		this.verb = activityVerb(verb);
		this.object = object;
		this.context = context;
		this.recorded = recordAt;
	}
	
	/**
	 * Builds a new {@code Activity} for provided user comments.
	 * @param comment the comment
	 * @param rsRef the rolling stock ref
	 * @return a new {@code Activity}
	 */
	public static Activity buildForComment(Comment comment, WeakDbRef<RollingStock> rsRef) {
		return new Activity(comment.getAuthor(), 
				ActivityVerb.COMMENT, 
				ActivityObject.rsObject(rsRef), 
				null, 
				comment.getPostedAt());
	}
	
	/**
	 * Builds a new {@code Activity} for provided user reviews.
	 * @param review the review
	 * @param rsRef the rolling stock ref
	 * @return a new {@code Activity}
	 */
	public static Activity buildForReview(Review review, WeakDbRef<RollingStock> rsRef) {
		return new Activity(review.getAuthor(), 
				ActivityVerb.REVIEW, 
				ActivityObject.rsObject(rsRef), 
				null, 
				review.getPostedAt());
	}
	
	public static Activity buildForRsCreate(RollingStock rollingStock) {
		return new Activity(rollingStock.getModifiedBy(), 
				ActivityVerb.RS_INSERT, 
				ActivityObject.rsObject(WeakDbRef.buildRef(rollingStock)), 
				null, 
				rollingStock.getLastModified());
	}
	
	public static Activity buildForRsChange(RollingStock rollingStock) {
		return new Activity(rollingStock.getModifiedBy(), 
				ActivityVerb.RS_UPDATE, 
				ActivityObject.rsObject(WeakDbRef.buildRef(rollingStock)), 
				null, 
				rollingStock.getLastModified());
	}
	
	public static Activity buildForWishList(WishList wishList, WishListItem item) {
		return new Activity(wishList.getOwner(), 
				ActivityVerb.ADD_WISH_LIST, 
				ActivityObject.rsObject(item.getRollingStock()), 
				ActivityContext.wishListContext(wishList), 
				item.getAddedAt());
	}
	
	public static Activity buildForCollection(Collection collection, CollectionItem item) {
		return new Activity(collection.getOwner(), 
				ActivityVerb.ADD_COLLECTION, 
				ActivityObject.rsObject(item.getRollingStock()), 
				ActivityContext.collectionContext(collection), 
				item.getAddedAt());
	}
	
	/**
	 * Returns the {@code Activity} id.
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * Sets the {@code Activity} id.
	 * @param id the id
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	/**
	 * Returns the {@code Activity} color.
	 * @return the color
	 */
	public String getColor() {
		String color = verbColors.get(getVerb());
		if (StringUtils.isBlank(color)) {
			return "White";
		}
		
		return color;
	}

	/**
	 * Returns the {@code Activity} actor.
	 * @return the actor
	 */
	public String getActor() {
		return actor;
	}

	/**
	 * Sets the {@code Activity} actor.
	 * @param actor the actor
	 */
	public void setActor(String actor) {
		this.actor = actor;
	}

	/**
	 * Returns the {@code Activity} verb.
	 * @return the verb
	 */
	public String getVerb() {
		return verb;
	}

	/**
	 * Sets the {@code Activity} verb.
	 * @param verb the verb
	 */
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

	/**
	 * Returns the {@code Activity} context.
	 * @return the context
	 */
	public ActivityContext getContext() {
		return context;
	}

	/**
	 * Sets the {@code Activity} context.
	 * @param context the context
	 */
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

	private static Map<String, String> initColors() {
		Map<String, String> colors = new HashMap<String, String>();
		
		colors.put(ActivityVerb.COMMENT.label(), "Darkorange");
		colors.put(ActivityVerb.REVIEW.label(), "LimeGreen");
		colors.put(ActivityVerb.RS_INSERT.label(), "SkyBlue");
		colors.put(ActivityVerb.RS_UPDATE.label(), "StateBlue");
		colors.put(ActivityVerb.ADD_COLLECTION.label(), "MediumTurquoise");
		colors.put(ActivityVerb.ADD_WISH_LIST.label(), "DarkOrchid");
		
		return colors;
	}
}
