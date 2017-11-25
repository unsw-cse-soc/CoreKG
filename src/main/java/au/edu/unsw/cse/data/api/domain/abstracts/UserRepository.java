package au.edu.unsw.cse.data.api.domain.abstracts;

import au.edu.unsw.cse.data.api.domain.entity.User;

public interface UserRepository extends GlobalRepository<User> {
  User getByUserNamePasword(String userName, String password, String clientName,
      String clientSecret);

  User getByUserNameClientId(String userName, String clientId);
}
