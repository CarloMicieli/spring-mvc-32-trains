package com.trenako.entities;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * It represents a user collection of rolling stocks.
 * 
 * <p>
 * The collection keeps track for all rolling stocks purchased by the owner. It allows the insertion of the 
 * same rolling stock more than once.
 * </p>
 * 
 * @author Carlo Micieli
 * 
 */
@Document(collection = "collections")
public class Collection {

	@Id
	private ObjectId id;
	
    @Indexed(unique = true)
    private String ownerName;

    @DBRef
    @NotNull(message = "collection.owner.required")
    private Account owner;

    private List<CollectionItem> items;

    // category -> no of rolling stocks
    private Map<String, Integer> counters;

    private boolean visible;
  
    // required by spring data
    Collection() {
	}

    /**
     * Creates a new rolling stocks collection for the 
     * provided user.
     * @param owner the user who owns this collection
     */
	public Collection(Account owner) {
		this.owner = owner;
	}

	public ObjectId getId() {
		return id;
	}
    
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Account getOwner() {
		return owner;
	}

	public void setOwner(Account owner) {
		this.owner = owner;
	}

	public List<CollectionItem> getItems() {
		return items;
	}

	public void setItems(List<CollectionItem> items) {
		this.items = items;
	}

	public Map<String, Integer> getCounters() {
		return counters;
	}

	public void setCounters(Map<String, Integer> counters) {
		this.counters = counters;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
