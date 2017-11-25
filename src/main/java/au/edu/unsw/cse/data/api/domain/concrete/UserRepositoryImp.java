package au.edu.unsw.cse.data.api.domain.concrete;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import au.edu.unsw.cse.data.api.domain.abstracts.ClientRepository;
import au.edu.unsw.cse.data.api.domain.abstracts.UserRepository;
import au.edu.unsw.cse.data.api.domain.entity.Client;
import au.edu.unsw.cse.data.api.domain.entity.User;

public class UserRepositoryImp extends GlobalRepositoryImp<User> implements UserRepository {

  private final ClientRepository clientRepository;

  @Inject
  public UserRepositoryImp(Datastore datastore, ClientRepository clientRepository) {
    super(datastore);
    this.clientRepository = clientRepository;
  }

  @Override
  public User getByUserNamePasword(String userName, String password, String clientName,
      String clientSecret) {
    Client client = clientRepository.get(clientName, clientSecret);
    if (client == null) {
      return null;
    }
    final Query<User> query = datastore.createQuery(User.class);
    query.and(query.criteria("userName").equalIgnoreCase(userName),
        query.criteria("password").equal(password), query.criteria("client").equal(client));
    User user = query.get();
    return user;
  }

  @Override
  public User getByUserNameClientId(String userName, String clientId) {
    Query<User> query = datastore.find(User.class).disableValidation().field("client")
        .equal(new Key<>(Client.class, "clients", new ObjectId(clientId))).field("userName").equal(userName);
    return query.get();
  }
}
