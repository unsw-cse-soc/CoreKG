package au.edu.unsw.cse.data.api.domain.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Index;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity("users")
@Indexes(@Index(fields = @Field("userName"), options = @IndexOptions(unique = true)))
public class User extends au.edu.unsw.cse.data.api.domain.entity.Entity {
	private String userName;
	@JsonIgnore
	private String password;
	private String role;
	@Reference
	@JsonIgnore
	private Client client;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
