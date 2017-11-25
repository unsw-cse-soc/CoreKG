package au.edu.unsw.cse.data.api.domain.entity;

import java.util.Date;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

@Entity("refreshtokens")
public class RefreshToken extends au.edu.unsw.cse.data.api.domain.entity.Entity {
	private String subject;
	// refresh token
	private String token;
	@Reference
	private Client client;
	private Date issuedOn;
	private Date expires;
	// access token
	private String protectedTicket;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Date getIssuedOn() {
		return issuedOn;
	}

	public void setIssuedOn(Date issuedOn) {
		this.issuedOn = issuedOn;
	}

	public Date getExpires() {
		return expires;
	}

	public void setExpires(Date expires) {
		this.expires = expires;
	}

	public String getProtectedTicket() {
		return protectedTicket;
	}

	public void setProtectedTicket(String protectedTicket) {
		this.protectedTicket = protectedTicket;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
