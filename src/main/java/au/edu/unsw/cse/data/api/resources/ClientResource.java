package au.edu.unsw.cse.data.api.resources;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import au.edu.unsw.cse.data.api.domain.abstracts.ClientRepository;
import au.edu.unsw.cse.data.api.domain.entity.Client;
import au.edu.unsw.cse.data.api.model.CreateClientBindingModel;
import au.edu.unsw.cse.data.api.model.ViewClientBindingModel;

@Path("clients")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClientResource {

  private final ClientRepository clientRepository;

  @Inject
  public ClientResource(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @POST
  public Response create(CreateClientBindingModel model) throws NoSuchAlgorithmException {
    if (clientRepository.getByName(model.getName()) != null) {
      return Response.status(Response.Status.BAD_REQUEST).entity("select another client name.")
          .build();
    } else {
      Client newClient = new Client();
      newClient.setName(model.getName());
      newClient.setRefreshTokenLifeTime(36000);
      // create new key
      SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
      // get base64 encoded version of the key
      String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
      newClient.setSecret(encodedKey);
      clientRepository.create(newClient);
      ViewClientBindingModel viewModel = new ViewClientBindingModel();
      viewModel.setName(newClient.getName());
      viewModel.setSecret(newClient.getSecret());
      return Response.ok(viewModel).build();
    }
  }
}
