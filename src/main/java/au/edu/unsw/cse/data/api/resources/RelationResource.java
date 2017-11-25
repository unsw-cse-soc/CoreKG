package au.edu.unsw.cse.data.api.resources;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections4.IteratorUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;

import au.edu.unsw.cse.data.api.domain.abstracts.DatabaseRepository;
import au.edu.unsw.cse.data.api.domain.abstracts.RelationRepository;
import au.edu.unsw.cse.data.api.domain.entity.Database;
import au.edu.unsw.cse.data.api.domain.entity.EntityRelation;
import au.edu.unsw.cse.data.api.index.abstracts.EntityIndex;
import au.edu.unsw.cse.data.api.model.CreateRelationBindingModel;
import au.edu.unsw.cse.data.api.security.AppUser;
import au.edu.unsw.cse.data.api.security.Secured;
import au.edu.unsw.cse.data.api.security.UserInfo;

@Path("relations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RelationResource {

  private final RelationRepository relationRepository;
  private final DatabaseRepository databaseRepository;
  private final EntityIndex entityIndex;

  @Inject
  public RelationResource(RelationRepository relationRepository,
      DatabaseRepository databaseRepository, EntityIndex entityIndex) {
    this.relationRepository = relationRepository;
    this.databaseRepository = databaseRepository;
    this.entityIndex = entityIndex;
  }

  @POST
  @Path("/{sourceType}/{destinationType}")
  @Secured
  public Response create(@AppUser UserInfo userInfo, @PathParam("sourceType") String sourceType,
      @PathParam("destinationType") String destinationType, CreateRelationBindingModel model) {
    EntityRelation newRelation = new EntityRelation();
    Database sourceDatabase = databaseRepository.getByClientId(userInfo.getClientId(),
        model.getSourceDataset(), model.getSourceDatabase());
    Database destinationDatabase = databaseRepository.getByClientId(userInfo.getClientId(),
        model.getDestinationDataset(), model.getDestinationDatabase());
    newRelation.setSourceDatabase(sourceDatabase);
    newRelation.setPath(new String[] {model.getDestination()});
    newRelation.setSource(model.getSource());
    newRelation.setEntityTypes(new String[] {sourceType, destinationType});
    newRelation.setRelationNames(new String[] {model.getName()});
    // newRelation.setEntityTypes(new String[] {model.getType()});
    newRelation.setDestinationsDatabases(new Database[] {destinationDatabase});
    relationRepository.create(newRelation);
    List<EntityRelation> query = relationRepository.getQueryable().field("path")
        .endsWithIgnoreCase(model.getDestination()).asList();
    List<EntityRelation> mainPaths = new LinkedList<EntityRelation>();
    int max = -1;
    for (int i = 0; i < query.size(); i++) {
      if (query.get(i).getPath().length > max) {
        max = query.get(i).getPath().length;
      }
    }
    for (int i = 0; i < query.size(); i++) {
      if (query.get(i).getPath().length == max) {
        mainPaths.add(query.get(i));
      }
    }
    // List<ObjectId> mainPathIds = IteratorUtils
    // .toList(relationRepository.getAggregation(query).unwind("path")
    // .group("_id",
    // org.mongodb.morphia.aggregation.Group.grouping("len",
    // org.mongodb.morphia.aggregation.Accumulator.accumulator("$sum", 1)))
    // .sort(org.mongodb.morphia.query.Sort.descending("len"))
    // .aggregate(ObjectId.class));
    // List<EntityRelation> mainPaths = relationRepository.getByIds(mainPathIds);
    entityIndex.indexRelations(mainPaths);
    return Response.ok().build();
  }
}
