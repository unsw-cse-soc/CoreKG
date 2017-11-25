package au.edu.unsw.cse.data.api.domain.abstracts;

import au.edu.unsw.cse.data.api.domain.entity.EntityType;

public interface EntityTypeRepository extends Repository<EntityType> {
	EntityType getByName(String name, String clientId);
}
