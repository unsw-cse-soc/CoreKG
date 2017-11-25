package au.edu.unsw.cse.data.api.domain.abstracts;

import java.util.List;

import org.mongodb.morphia.aggregation.AggregationPipeline;
import org.mongodb.morphia.query.Query;

public interface Repository<T extends au.edu.unsw.cse.data.api.domain.entity.Entity> {

  T get(String databaseName, String id);

  List<T> getAll(String databaseName);

  Query<T> getQueryable(String databaseName);

  void create(String databaseName, T entity);

  void delete(String databaseName, String id);

  AggregationPipeline getAggregation(String databaseName, Query<T> query);
}
