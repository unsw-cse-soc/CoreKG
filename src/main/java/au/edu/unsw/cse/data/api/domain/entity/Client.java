package au.edu.unsw.cse.data.api.domain.entity;

import org.mongodb.morphia.annotations.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity("clients")
public class Client extends au.edu.unsw.cse.data.api.domain.entity.Entity {
	@JsonProperty(access = Access.READ_ONLY)
	private String secret;
	private String name;
	private int refreshTokenLifeTime;

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRefreshTokenLifeTime() {
		return refreshTokenLifeTime;
	}

	public void setRefreshTokenLifeTime(int refreshTokenLifeTime) {
		this.refreshTokenLifeTime = refreshTokenLifeTime;
	}
}
