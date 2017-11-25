package au.edu.unsw.cse.data.api.domain.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

@Entity("entityTypes")
public class EntityType extends au.edu.unsw.cse.data.api.domain.entity.Entity {

	private String name;
	@Reference
	private Client client;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	private String collectionName;
}
