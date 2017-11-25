package au.edu.unsw.cse.data.api.security;

import java.security.Principal;

public class UserInfo implements Principal {

	private String username;
	private String clientId;

	public UserInfo() {

	}

	public UserInfo(String username, String clientId) {
		this.username = username;
		this.clientId = clientId;
	}

	@Override
	public String getName() {
		return username;
	}

	public String getClientId() {
		return clientId;
	}

}
