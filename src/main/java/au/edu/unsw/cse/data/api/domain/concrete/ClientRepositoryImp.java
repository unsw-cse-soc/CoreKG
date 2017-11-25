package au.edu.unsw.cse.data.api.domain.concrete;

import javax.inject.Inject;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import au.edu.unsw.cse.data.api.domain.abstracts.ClientRepository;
import au.edu.unsw.cse.data.api.domain.entity.Client;

public class ClientRepositoryImp extends GlobalRepositoryImp<Client> implements ClientRepository {

  @Inject
  public ClientRepositoryImp(Datastore datastore) {
    super(datastore);
  }

  @Override
  public Client get(String name, String secret) {
    final Query<Client> query =
        datastore.createQuery(Client.class).field("name").equal(name).field("secret").equal(secret);
    Client client = query.get();
    return client;
  }

  @Override
  public Client getByName(String name) {
    final Query<Client> query = datastore.createQuery(Client.class);
    query.and(query.criteria("name").equalIgnoreCase(name));
    Client client = query.get();
    return client;
  }
}
