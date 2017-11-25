package au.edu.unsw.cse.data.api.resources;

import au.edu.unsw.cse.data.api.domain.abstracts.ClientRepository;
import au.edu.unsw.cse.data.api.domain.abstracts.DatabaseRepository;
import au.edu.unsw.cse.data.api.domain.entity.Client;
import au.edu.unsw.cse.data.api.domain.entity.Database;
import au.edu.unsw.cse.data.api.model.CreateDatabaseBindingModel;
import au.edu.unsw.cse.data.api.security.AppUser;
import au.edu.unsw.cse.data.api.security.Secured;
import au.edu.unsw.cse.data.api.security.UserInfo;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("databases")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DatabaseResources {

  private final DatabaseRepository databaseRepository;
  private final ClientRepository clientRepository;

  @Inject
  public DatabaseResources(DatabaseRepository databaseRepository,
      ClientRepository clientRepository) {
    this.databaseRepository = databaseRepository;
    this.clientRepository = clientRepository;
  }

  @POST
  @Secured
  public Response create(@AppUser UserInfo userInfo, CreateDatabaseBindingModel model) {
    Client client = clientRepository.get(userInfo.getClientId());
    Database database = new Database();
    database.setClient(client);
    database.setName(model.getName());
    database.setType(model.getType());
    databaseRepository.create(database);
    return Response.ok().build();
  }

}
