package au.edu.unsw.cse.data.api.index.abstracts;

import au.edu.unsw.cse.data.api.domain.entity.EntityRelation;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.elasticsearch.action.index.IndexResponse;

public interface EntityIndex {

  IndexResponse createIndex(Map<String, Object> entity, String databaseId, String entityType,
      String entityId);

  void update(String index, String type, String id, String fieldName, Map<String, Object> fields)
      throws ExecutionException, InterruptedException, IOException;

  Map<String, Object> get(String index, String type, String id);

  void indexRelations(List<EntityRelation> relations);
}
