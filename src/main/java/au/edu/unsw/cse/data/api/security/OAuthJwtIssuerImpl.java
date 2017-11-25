package au.edu.unsw.cse.data.api.security;

import java.util.HashMap;
import java.util.Map;

import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.issuer.ValueGenerator;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import com.auth0.jwt.JWTSigner;

import au.edu.unsw.cse.data.api.domain.entity.User;

public class OAuthJwtIssuerImpl extends OAuthIssuerImpl {

	private final Map<String, Object> claims;

	public OAuthJwtIssuerImpl(ValueGenerator vg) {
		super(vg);
		claims = new HashMap<String, Object>();
	}

	@Override
	public String accessToken() throws OAuthSystemException {
		final String issuer = "data.api";
		final String secret = "bg)0nn^rh!q0f8)4ku17iz-)dagzm$qjfts$@64cu4#jhb!sx-";

		final long iat = System.currentTimeMillis() / 1000l; // issued at claim
		final long exp = iat + 60L; // expires claim. In this case the token
									// expires in 60 seconds

		final JWTSigner signer = new JWTSigner(secret);
		claims.put("iss", issuer);
//		claims.put("exp", exp);
//		claims.put("iat", iat);

		final String jwt = signer.sign(claims);

		return jwt;
	}

	public void setClaims(User user) {
		claims.put(Claims.USERNAME, user.getUserName());
	}

	public void setClaim(String claim, String value) {
		claims.put(claim, value);
	}
}
