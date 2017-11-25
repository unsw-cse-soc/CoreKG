package au.edu.unsw.cse.data.api.domain.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.EntityListeners;
import org.mongodb.morphia.annotations.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import au.edu.unsw.cse.data.api.domain.watcher.EntityWatcher;

@EntityListeners(EntityWatcher.class)
public abstract class Entity {
	@Id
	protected ObjectId id;

	@JsonProperty(access = Access.READ_ONLY)
	protected Date createdAt;

	@JsonProperty(access = Access.READ_ONLY)
	protected Date updatedAt;

	@JsonProperty("id")
	public String getId() {
		return id.toString();
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
