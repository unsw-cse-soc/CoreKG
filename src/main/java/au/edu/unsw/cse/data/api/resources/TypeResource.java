package au.edu.unsw.cse.data.api.resources;

import au.edu.unsw.cse.data.api.security.Secured;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

@Path("type")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TypeResource {

  @POST
  @Path("/{name}")
  @Secured
  public Response create(@PathParam("name") String name, ObjectNode type) {

    return Response.ok().build();
  }

}
