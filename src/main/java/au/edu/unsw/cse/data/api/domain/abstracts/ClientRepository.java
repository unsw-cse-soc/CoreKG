package au.edu.unsw.cse.data.api.domain.abstracts;

import au.edu.unsw.cse.data.api.domain.entity.Client;

public interface ClientRepository extends GlobalRepository<Client> {
	Client get(String name, String secret);

	Client getByName(String name);
}
