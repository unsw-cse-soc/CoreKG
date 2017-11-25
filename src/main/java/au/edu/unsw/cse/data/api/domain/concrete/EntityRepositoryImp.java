package au.edu.unsw.cse.data.api.domain.concrete;

import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import au.edu.unsw.cse.data.api.domain.abstracts.EntityRepository;
import au.edu.unsw.cse.data.api.domain.abstracts.RelationRepository;
import au.edu.unsw.cse.data.api.domain.entity.EntityRelation;

public class EntityRepositoryImp implements EntityRepository {

  private final MongoClient mongoClient;

  private final RelationRepository relationRepository;

  @Inject
  public EntityRepositoryImp(MongoClient mongoClient, RelationRepository relationRepository) {
    this.mongoClient = mongoClient;
    this.relationRepository = relationRepository;
  }

  private MongoDatabase getMongoDatabase(String database) {
    return mongoClient.getDatabase(database);
  }

  @Override
  public void create(Document entity, String database, String collection) {
    MongoCollection<Document> col = getMongoDatabase(database).getCollection(collection);
    col.insertOne(entity);
    if (entity.containsKey("clientId")) {
      entity.remove("clientId");
    }
    if (entity.containsKey("_id")) {
      String id = entity.getObjectId("_id").toString();
      entity.append("id", id);
      entity.remove("_id");
    }
  }

  @Override
  public Document get(String id, String clientId, String database, String collection) {
    MongoCollection<Document> col = getMongoDatabase(database).getCollection(collection);
    BasicDBObject query = new BasicDBObject();
    query.put("_id", new ObjectId(id));
    query.put("clientId", clientId);
    Document dbObj = col.find(query).first();
    if (dbObj.containsKey("clientId")) {
      dbObj.remove("clientId");
    }
    if (dbObj.containsKey("_id")) {
      dbObj.append("id", id);
      dbObj.remove("_id");
    }
    return dbObj;
  }

  @Override
  public List<Document> filter(String clientId, String database, String collection,
      Map<String, String> fields) {
    MongoCollection<Document> col = getMongoDatabase(database).getCollection(collection);
    BasicDBObject query = new BasicDBObject();
    List<Document> documents = new LinkedList<Document>();
    query.put("clientId", clientId);
    fields.forEach((key, value) -> {
      query.put(key, value);
    });
    MongoCursor<Document> cursor = col.find(query).iterator();
    try {
      while (cursor.hasNext()) {
        documents.add(cursor.next());
      }
    } finally {
      cursor.close();
    }
    return documents;
  }

  @Override
  public List<Document> getAll(String clientId, String database, String collection) {
    MongoCollection<Document> col = getMongoDatabase(database).getCollection(collection);
    BasicDBObject query = new BasicDBObject();
    query.put("clientId", clientId);
    List<Document> objects = col.find(query).into(new ArrayList<Document>());
    objects.forEach(object -> {
      if (object.containsKey("clientId")) {
        object.remove("clientId");
      }
      if (object.containsKey("_id")) {
        String id = object.getObjectId("_id").toString();
        object.append("id", id);
        object.remove("_id");
      }
    });
    return objects;
  }

  @Override
  public List<Document> get(String id, List<String> includes, String clientId, String database,
      String collection) {
    return null;
//    List<EntityRelation> relations = relationRepository.get(database, id, includes);
//    ConcurrentHashMap<String, Set<ObjectId>> requiredEntities =
//        new ConcurrentHashMap<String, Set<ObjectId>>();
//    relations.forEach(relation -> {
//      for (int i = 0; i < relation.getPath().length; i++) {
//        String itemType = relation.getEntityTypes()[i + 1];
//        String entityId = relation.getPath()[i];
//        if (requiredEntities.putIfAbsent(itemType, new HashSet<ObjectId>() {
//          {
//            add(new ObjectId(entityId));
//          }
//        }) == null) {
//          requiredEntities.get(itemType).add(new ObjectId(entityId));
//        }
//      }
//    });
//    // add source
//    requiredEntities.put(relations.get(0).getEntityTypes()[0], new HashSet<ObjectId>() {
//      {
//        add(new ObjectId(id));
//      }
//    });
//    List<Document> entities = new LinkedList<Document>();
//    // get all entities
//    requiredEntities.forEach(10, (key, value) -> {
//      MongoCollection<Document> col =
//          getMongoDatabase(database).getCollection(String.format("%s_%s", clientId, key));
//      BasicDBObject inQuery = new BasicDBObject("$in", value.toArray());
//      BasicDBObject query = new BasicDBObject("_id", inQuery);
//      entities.addAll(col.find(query).into(new ArrayList<Document>()));
//    });
//    List<Document> results = new LinkedList<Document>();
//    relations.forEach(relation -> {
//      if (relation.getPath().length == 1) {
//        Document source = results.stream()
//            .filter(doc -> doc.getObjectId("_id").toString().equalsIgnoreCase(id)).findAny()
//            .orElse(entities.stream()
//                .filter(entity -> entity.getObjectId("_id").toString().equalsIgnoreCase(id))
//                .findFirst().get());
//        int indexOfSourceType =
//            ArrayUtils.indexOf(relation.getEntityTypes(), source.getString("type"));
//        String destinationType = relation.getEntityTypes()[indexOfSourceType + 1];
//        Document destination = entities.stream().filter(entity -> entity.getObjectId("_id")
//            .toString().equalsIgnoreCase(relation.getPath()[indexOfSourceType])).findFirst().get();
//        if (source.containsKey(destinationType)) {
//          Document[] docArray = source.get(destinationType, Document[].class);
//          source.replace(destinationType, ArrayUtils.add(docArray, destination));
//        } else {
//          source.append(destinationType, new Document[] {destination});
//        }
//        if (!results.stream().filter(doc -> doc.getObjectId("_id").toString().equalsIgnoreCase(id))
//            .findAny().isPresent()) {
//          results.add(source);
//        }
//      } else {
//        for (int i = 0; i < relation.getPath().length - 1; i++) {
//          if (includes.contains(relation.getEntityTypes()[i + 1])
//              && includes.contains(relation.getEntityTypes()[i + 2])) {
//            String sourceId = relation.getPath()[i];
//            String destinationId = relation.getPath()[i + 1];
//
//            Document source =
//                results.stream()
//                    .filter(doc -> doc.getObjectId("_id").toString().equalsIgnoreCase(sourceId))
//                    .findAny()
//                    .orElse(entities.stream().filter(
//                        entity -> entity.getObjectId("_id").toString().equalsIgnoreCase(sourceId))
//                        .findFirst().get());
//            String destinationType = relation.getEntityTypes()[i + 2];
//            if (!source.containsKey(destinationType)
//                || !Arrays.asList(source.get(destinationType, Document[].class)).stream()
//                    .filter(doc -> doc.get("_id").toString().equalsIgnoreCase(destinationId))
//                    .findAny().isPresent()) {
//              Document detDoc = entities.stream()
//                  .filter(doc -> doc.getObjectId("_id").toString().equalsIgnoreCase(destinationId))
//                  .findFirst().get();
//              if (!source.containsKey(destinationType)) {
//                source.append(destinationType, new Document[] {detDoc});
//              } else {
//                Document[] docArray = source.get(destinationType, Document[].class);
//                source.replace(destinationType, ArrayUtils.add(docArray, detDoc));
//              }
//            }
//          }
//        }
//      }
//    });
//    return results;
  }
}
