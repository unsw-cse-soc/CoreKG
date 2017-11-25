package au.edu.unsw.cse.data.api.domain.concrete;

import javax.inject.Inject;

import org.mongodb.morphia.Datastore;

import au.edu.unsw.cse.data.api.domain.abstracts.TokenRepository;
import au.edu.unsw.cse.data.api.domain.entity.RefreshToken;

public class TokenRepositoryImp extends GlobalRepositoryImp<RefreshToken>
    implements TokenRepository {

  @Inject
  public TokenRepositoryImp(Datastore datastore) {
    super(datastore);
  }
}
