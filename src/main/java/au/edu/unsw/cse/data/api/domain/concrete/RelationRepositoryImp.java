package au.edu.unsw.cse.data.api.domain.concrete;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import au.edu.unsw.cse.data.api.domain.abstracts.RelationRepository;
import au.edu.unsw.cse.data.api.domain.entity.EntityRelation;

public class RelationRepositoryImp extends GlobalRepositoryImp<EntityRelation>
    implements RelationRepository {

  @Inject
  public RelationRepositoryImp(Datastore datastore) {
    super(datastore);
  }

  @Override
  public void create(EntityRelation entity) {
    List<EntityRelation> paths = datastore.find(EntityRelation.class).field("path")
        .endsWithIgnoreCase(entity.getSource()).asList();
    List<EntityRelation> updatedPaths = new LinkedList<>();
    paths.forEach(path -> {
      EntityRelation newPath = new EntityRelation();
      newPath.setSource(path.getSource());
      newPath.setPath(ArrayUtils.add(Arrays.copyOf(path.getPath(), path.getPath().length),
          entity.getPath()[0]));
      newPath.setEntityTypes(
          ArrayUtils.add(Arrays.copyOf(path.getEntityTypes(), path.getEntityTypes().length),
              entity.getEntityTypes()[1]));
      newPath.setRelationNames(
          ArrayUtils.add(Arrays.copyOf(path.getRelationNames(), path.getRelationNames().length),
              path.getRelationNames()[0]));
      newPath.setRelationTypes(
          ArrayUtils.add(Arrays.copyOf(path.getRelationTypes(), path.getRelationTypes().length),
              path.getRelationTypes()[0]));
      newPath.setDestinationsDatabases(ArrayUtils.add(
          Arrays.copyOf(path.getDestinationsDatabases(), path.getDestinationsDatabases().length),
          entity.getDestinationsDatabases()[0]));
      updatedPaths.add(newPath);
    });
    updatedPaths.add(entity);
    datastore.save(updatedPaths);
  }

  @Override
  public List<EntityRelation> get(String source, List<String> types) {
    String[] typesArray = new String[types.size()];
    typesArray = types.toArray(typesArray);
    final Query<EntityRelation> query = datastore.createQuery(EntityRelation.class).field("source")
        .equalIgnoreCase(source).filter("types in", typesArray);
    List<EntityRelation> relations = query.asList();
    return relations;
  }

  @Override
  public List<EntityRelation> getByIds(List<ObjectId> ids) {
    ObjectId[] idsArray = new ObjectId[ids.size()];
    idsArray = ids.toArray(idsArray);
    final Query<EntityRelation> query =
        datastore.createQuery(EntityRelation.class).field("_id").hasAnyOf(ids);
    List<EntityRelation> relations = query.asList();
    return relations;
  }
}
