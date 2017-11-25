package au.edu.unsw.cse.data.api.resources;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import au.edu.unsw.cse.data.api.domain.abstracts.ClientRepository;
import au.edu.unsw.cse.data.api.domain.abstracts.TokenRepository;
import au.edu.unsw.cse.data.api.domain.abstracts.UserRepository;
import au.edu.unsw.cse.data.api.domain.entity.Client;
import au.edu.unsw.cse.data.api.domain.entity.RefreshToken;
import au.edu.unsw.cse.data.api.domain.entity.User;
import au.edu.unsw.cse.data.api.model.ViewUserBindingModel;
import au.edu.unsw.cse.data.api.security.Claims;
import au.edu.unsw.cse.data.api.security.OAuthJwtIssuerImpl;
import au.edu.unsw.cse.data.api.security.OAuthRequestWrapper;

@Path("oauth")
public class TokenEndpoint {
  private final ClientRepository clientRepo;
  private final UserRepository userRepo;
  private final TokenRepository tokenRepo;

  @Inject
  public TokenEndpoint(UserRepository userRepo, TokenRepository tokenRepo,
      ClientRepository clientRepo) {
    this.userRepo = userRepo;
    this.tokenRepo = tokenRepo;
    this.clientRepo = clientRepo;
  }

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/token")
  public Response authorize(@Context HttpServletRequest request,
      MultivaluedMap<String, String> form) throws OAuthSystemException {
    try {
      OAuthTokenRequest oauthRequest =
          new OAuthTokenRequest(new OAuthRequestWrapper(request, form));

      Client client = clientRepo.get(oauthRequest.getClientId(), oauthRequest.getClientSecret());

      if (client == null) {
        return buildInvalidClientIdResponse();
      }

      User user = null;
      // do checking for different grant types
      if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.PASSWORD.toString())) {
        user = userRepo.getByUserNamePasword(oauthRequest.getUsername(),
            DigestUtils.sha1Hex(oauthRequest.getPassword()), oauthRequest.getClientId(),
            oauthRequest.getClientSecret());
        if (user == null) {
          return buildInvalidUserPassResponse();
        }
      } else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE)
          .equals(GrantType.REFRESH_TOKEN.toString())) {
        // refresh token is not supported in this implementation
        buildInvalidUserPassResponse();
      }

      OAuthIssuer oauthIssuerImpl = new OAuthJwtIssuerImpl(new MD5Generator());
      ((OAuthJwtIssuerImpl) oauthIssuerImpl).setClaims(user);
      ((OAuthJwtIssuerImpl) oauthIssuerImpl).setClaim(Claims.CLIENTID, client.getId());

      final String accessToken = oauthIssuerImpl.accessToken();
      final String refreshToken = oauthIssuerImpl.refreshToken();

      Date now = new Date();
      RefreshToken rToken = new RefreshToken();
      rToken.setToken(refreshToken);
      rToken.setClient(client);
      rToken.setIssuedOn(now);
      rToken.setExpires(DateUtils.addMonths(now, client.getRefreshTokenLifeTime()));
      rToken.setProtectedTicket(accessToken);
      rToken.setSubject(user.getUserName());
      tokenRepo.create(rToken);

      ObjectMapper mapper = new ObjectMapper();
      String userInString = mapper.writeValueAsString(new ViewUserBindingModel(user));

      OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
          .setAccessToken(accessToken).setRefreshToken(refreshToken).setExpiresIn("3600")
          .setParam("user", userInString).buildJSONMessage();
      return Response.status(response.getResponseStatus()).entity(response.getBody()).build();

    } catch (OAuthProblemException e) {
      OAuthResponse res = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e)
          .buildJSONMessage();
      return Response.status(res.getResponseStatus()).entity(res.getBody()).build();
    } catch (JsonProcessingException e) {
      return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  private Response buildInvalidClientIdResponse() throws OAuthSystemException {
    OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
        .setError(OAuthError.TokenResponse.INVALID_CLIENT)
        .setErrorDescription("Client is not registered in the system.").buildJSONMessage();
    return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
  }

  private Response buildInvalidUserPassResponse() throws OAuthSystemException {
    OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
        .setError(OAuthError.TokenResponse.INVALID_GRANT)
        .setErrorDescription("invalid username or password").buildJSONMessage();
    return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
  }
}
