package au.edu.unsw.cse.data.api.domain.concrete;

import au.edu.unsw.cse.data.api.domain.abstracts.DatabaseRepository;
import au.edu.unsw.cse.data.api.domain.entity.Client;
import au.edu.unsw.cse.data.api.domain.entity.Database;
import javax.inject.Inject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

public class DatabaseRepositoryImp extends GlobalRepositoryImp<Database>
    implements DatabaseRepository {

  @Inject
  public DatabaseRepositoryImp(Datastore datastore) {
    super(datastore);
  }

  @Override
  public Database getByClientId(String clientId, String databaseName, String databaseType) {
    final Query<Database> query = datastore.createQuery(Database.class);
    query.and(query.criteria("name").equalIgnoreCase(databaseName),
        query.criteria("type").equalIgnoreCase(databaseType),
        query.criteria("client").equal(new Key<>(Client.class, "clients", new ObjectId(clientId))));
    Database database = query.get();
    return database;
  }
}
