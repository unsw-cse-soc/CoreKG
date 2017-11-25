package au.edu.unsw.cse.data.api.resources;

import au.edu.unsw.cse.data.api.domain.abstracts.DatabaseRepository;
import au.edu.unsw.cse.data.api.domain.entity.Database;
import au.edu.unsw.cse.data.api.index.abstracts.EntityIndex;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.bson.Document;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import au.edu.unsw.cse.data.api.domain.abstracts.EntityRepository;
import au.edu.unsw.cse.data.api.security.AppUser;
import au.edu.unsw.cse.data.api.security.Secured;
import au.edu.unsw.cse.data.api.security.UserInfo;

@Path("entities")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EntityResource {

  private final EntityRepository entityRepository;
  private final DatabaseRepository databaseRepository;
  private final EntityIndex entityIndex;
  private final MongoClient mongoClient;

  @Inject
  public EntityResource(EntityRepository entityRepository, EntityIndex entityIndex,
      DatabaseRepository databaseRepository, MongoClient mongoClient) {
    this.entityRepository = entityRepository;
    this.entityIndex = entityIndex;
    this.databaseRepository = databaseRepository;
    this.mongoClient = mongoClient;
  }

  @POST
  @Path("/schema/{name}")
  @Secured
  public Response createSchema(@AppUser UserInfo userInfo, @PathParam("name") String name,
      ObjectNode schema) {
    Document newSchema = new Document();
    newSchema.append("schema_name", name);
    schema.fields().forEachRemaining(field -> {
      newSchema.append(field.getKey(), field.getValue().asText());
    });
    newSchema.append("createdBy", userInfo.getName());
    newSchema.append("updatedBy", userInfo.getName());
    newSchema.append("clientId", userInfo.getClientId());
    // entityRepository.create(newSchema, "schemas");
    return Response.ok().build();
  }

  @GET
  @Path("/{databasename}/{datasetname}/{entitytype}")
  public Response query(@PathParam("databasename") String databasename,
      @PathParam("datasetname") String datasetname, @PathParam("entitytype") String entitytype,
      @QueryParam("query") String query) {
    List<Document> result = new LinkedList<Document>();
    if (query.startsWith("{")) {
      MongoDatabase db = mongoClient.getDatabase("tweetDb");
      MongoCursor<Document> s = db.getCollection("tweets")
          .find(com.mongodb.client.model.Filters.regex("body", ".*health.*", "i")).iterator();
      while (s.hasNext()) {
        Document obj = s.next();
        result.add(obj);
      }
    } else {
      MongoDatabase db = mongoClient.getDatabase("unsw");
      MongoCursor<Document> s = db.getCollection("health")
          .find(com.mongodb.client.model.Filters.regex("position", ".*Nurse.*", "i")).iterator();
      while (s.hasNext()) {
        Document obj = s.next();
        if (obj.containsKey("_id")) {
          String id = obj.getObjectId("_id").toString();
          obj.append("id", id);
          obj.remove("_id");
        }
        result.add(obj);
      }
    }
    return Response.ok(result).build();
  }

  @POST
  @Path("/{databasename}/{datasetname}/{entitytype}")
  @Secured
  public Response create(@AppUser UserInfo userInfo, @PathParam("databasename") String databasename,
      @PathParam("datasetname") String datasetname, @PathParam("entitytype") String entitytype,
      ObjectNode entity) {
    Database database =
        databaseRepository.getByClientId(userInfo.getClientId(), datasetname, databasename);
    if (database == null) {
      // return error
    }
    Document document = new Document();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> mapObject = mapper.convertValue(entity, Map.class);
    // create mongo object
    document.putAll(mapObject);
    document.append("type", entitytype);
    document.append("createdBy", userInfo.getName());
    document.append("updatedBy", userInfo.getName());
    document.append("clientId", userInfo.getClientId());
    document.append("databaseId", database.getId());
    // store mongo object
    entityRepository.create(document, datasetname, entitytype);
    String insertedId = document.getString("id");
    entityIndex.createIndex(mapObject, database.getId(), entitytype, insertedId);
    return Response.ok(document).build();
  }


  // @GET
  // @Path("/get/{type}/{id}")
  // @Secured
  // public Response get(@AppUser UserInfo userInfo, @PathParam("type") String type,
  // @PathParam("id") String id, @QueryParam("include") List<String> includes) {
  // List<Document> documents = entityRepository.get(id, includes, userInfo.getClientId(),
  // String.format("%s_%s", userInfo.getClientId(), type));
  // return Response.ok(documents).build();
  // }
  //
  // @GET
  // @Path("/list/{type}")
  // @Secured
  // public Response getAll(@AppUser UserInfo userInfo, @PathParam("type") String type) {
  // List<Document> documents = entityRepository.getAll(userInfo.getClientId(),
  // String.format("%s_%s", userInfo.getClientId(), type));
  // return Response.ok(documents).build();
  // }
}
