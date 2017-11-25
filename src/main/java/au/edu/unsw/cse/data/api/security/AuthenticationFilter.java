package au.edu.unsw.cse.data.api.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SignatureException;
import java.util.Map;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;

import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	@Context
	private HttpServletRequest request;

	private Map<String, Object> claims;

	private boolean secure;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		try {
			OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.HEADER);
			// Get the access token
			String accessToken = oauthRequest.getAccessToken();
			final String secret = "bg)0nn^rh!q0f8)4ku17iz-)dagzm$qjfts$@64cu4#jhb!sx-";
			final JWTVerifier verifier = new JWTVerifier(secret);
			claims = verifier.verify(accessToken);
			secure = true;
		} catch (OAuthSystemException | OAuthProblemException | InvalidKeyException | NoSuchAlgorithmException
				| IllegalStateException | SignatureException | JWTVerifyException e) {
			secure = false;
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}

		requestContext.setSecurityContext(new SecurityContext() {

			@Override
			public boolean isUserInRole(String role) {
				return claims.containsKey(Claims.ROLE)
						&& StringUtils.equalsIgnoreCase(claims.get(Claims.ROLE).toString(), role);
			}

			@Override
			public boolean isSecure() {
				return secure;
			}

			@Override
			public Principal getUserPrincipal() {
				return new UserInfo(claims.get(Claims.USERNAME).toString(), claims.get(Claims.CLIENTID).toString());
			}

			@Override
			public String getAuthenticationScheme() {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}

}
