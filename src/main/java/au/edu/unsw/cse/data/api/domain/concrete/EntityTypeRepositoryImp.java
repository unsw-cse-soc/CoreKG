package au.edu.unsw.cse.data.api.domain.concrete;

import javax.inject.Inject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.mongodb.MongoClient;

import au.edu.unsw.cse.data.api.domain.abstracts.EntityTypeRepository;
import au.edu.unsw.cse.data.api.domain.entity.Client;
import au.edu.unsw.cse.data.api.domain.entity.EntityType;

public class EntityTypeRepositoryImp extends RepositoryImp<EntityType>
    implements EntityTypeRepository {

  @Inject
  public EntityTypeRepositoryImp(Morphia morphia, MongoClient mongoClient) {
    super(morphia, mongoClient);
  }

  @Override
  public EntityType getByName(String name, String clientId) {
    Query<EntityType> query =
        getDataStore("dataApi").find(getGenericTypeClass()).disableValidation().field("client")
            .equal(new Key<>(Client.class, "clients", new ObjectId(clientId)));
    query.and(query.criteria("name").equalIgnoreCase(name));
    EntityType entityType = query.get();
    return entityType;
  }
}
