package au.edu.unsw.cse.data.api.domain.abstracts;

import java.util.List;

import org.bson.types.ObjectId;

import au.edu.unsw.cse.data.api.domain.entity.EntityRelation;

public interface RelationRepository extends GlobalRepository<EntityRelation> {
  List<EntityRelation> get(String source, List<String> types);

  List<EntityRelation> getByIds(List<ObjectId> ids);
}
