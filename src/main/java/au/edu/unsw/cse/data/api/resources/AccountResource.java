package au.edu.unsw.cse.data.api.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import au.edu.unsw.cse.data.api.domain.abstracts.ClientRepository;
import au.edu.unsw.cse.data.api.domain.abstracts.UserRepository;
import au.edu.unsw.cse.data.api.domain.entity.Client;
import au.edu.unsw.cse.data.api.domain.entity.User;
import au.edu.unsw.cse.data.api.model.CreateUserBindingModel;
import au.edu.unsw.cse.data.api.model.ViewUserBindingModel;

import org.apache.commons.codec.digest.DigestUtils;

@Path("account")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

  private final UserRepository userRepo;
  private final ClientRepository clientRepo;

  @Inject
  public AccountResource(UserRepository userRepo, ClientRepository clientRepo) {
    this.userRepo = userRepo;
    this.clientRepo = clientRepo;
  }

  @POST
  public Response register(CreateUserBindingModel userInfo) {
    Client client = clientRepo.get(userInfo.getClientName(), userInfo.getClientSecret());
    if (client == null) {
      return Response.status(Response.Status.UNAUTHORIZED).type(MediaType.APPLICATION_JSON).build();
    }
    if (userRepo.getByUserNameClientId(userInfo.getUserName(), client.getId()) != null) {
      return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON)
          .entity("Username's already taken.").build();
    }
    User user = new User();
    user.setClient(client);
    user.setPassword(DigestUtils.sha1Hex(userInfo.getPassword()));
    user.setUserName(userInfo.getUserName());
    user.setRole(userInfo.getRole());
    userRepo.create(user);
    return Response.ok(new ViewUserBindingModel(user)).build();
  }
}
