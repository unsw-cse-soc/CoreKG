package au.edu.unsw.cse.data.api.domain.concrete;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.aggregation.AggregationPipeline;
import org.mongodb.morphia.query.Query;

import au.edu.unsw.cse.data.api.domain.abstracts.GlobalRepository;

public class GlobalRepositoryImp<T extends au.edu.unsw.cse.data.api.domain.entity.Entity>
    implements GlobalRepository<T> {

  protected final Datastore datastore;

  @Inject
  public GlobalRepositoryImp(Datastore datastore) {
    this.datastore = datastore;
  }

  private Class<T> getGenericTypeClass() {
    try {
      String className =
          ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]
              .getTypeName();
      Class<?> clazz = Class.forName(className);
      return (Class<T>) clazz;
    } catch (Exception e) {
      throw new IllegalStateException(
          "Class is not parametrized with generic type!!! Please use extends <> ");
    }
  }

  @Override
  public T get(String id) {
    ObjectId objectId = new ObjectId(id);
    T entity = datastore.get(getGenericTypeClass(), objectId);
    return entity;
  }

  @Override
  public List<T> getAll() {
    final Query<T> query = datastore.createQuery(getGenericTypeClass());
    final List<T> entities = query.asList();
    return entities;
  }

  @Override
  public Query<T> getQueryable() {
    return datastore.createQuery(getGenericTypeClass());
  }

  @Override
  public void create(T entity) {
    datastore.save(entity);
  }

  @Override
  public void delete(String id) {
    T entity = get(id);
    datastore.delete(entity);
  }

  @Override
  public AggregationPipeline getAggregation(Query<T> query) {
    return datastore.createAggregation(getGenericTypeClass()).match(query);
  }
}
