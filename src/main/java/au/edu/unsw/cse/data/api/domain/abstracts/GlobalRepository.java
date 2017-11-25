package au.edu.unsw.cse.data.api.domain.abstracts;

import java.util.List;

import org.mongodb.morphia.aggregation.AggregationPipeline;
import org.mongodb.morphia.query.Query;

public interface GlobalRepository<T extends au.edu.unsw.cse.data.api.domain.entity.Entity> {

  T get(String id);

  List<T> getAll();

  Query<T> getQueryable();

  void create(T entity);

  void delete(String id);

  AggregationPipeline getAggregation(Query<T> query);
}
