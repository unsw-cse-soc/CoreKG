package au.edu.unsw.cse.data.api.domain.abstracts;

import au.edu.unsw.cse.data.api.domain.entity.Database;

public interface DatabaseRepository extends GlobalRepository<Database> {

  Database getByClientId(String clientId, String databaseName, String databaseType);
}
