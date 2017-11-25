package au.edu.unsw.cse.data.api.infrastructure;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import au.edu.unsw.cse.data.api.domain.abstracts.ClientRepository;
import au.edu.unsw.cse.data.api.domain.abstracts.EntityTypeRepository;
import au.edu.unsw.cse.data.api.domain.entity.Client;
import au.edu.unsw.cse.data.api.domain.entity.EntityType;
import au.edu.unsw.cse.data.api.security.UserInfo;

public class EntityTypeFilter implements ContainerRequestFilter {

	@Inject
	private EntityTypeRepository entityTypeRepo;
	@Inject
	private ClientRepository clientRepo;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// create entity type on post, reject others if type does not exist.
//		MultivaluedMap<String, String> pathParams = requestContext.getUriInfo().getPathParameters();
//		String typeName = pathParams.getFirst("type");
//		UserInfo userInfo = (UserInfo) requestContext.getSecurityContext().getUserPrincipal();
//		if (requestContext.getMethod().equals("POST")) {
//			if (entityTypeRepo.getByName(typeName, userInfo.getClientId()) == null) {
//				Client client = clientRepo.get(userInfo.getClientId());
//				EntityType newType = new EntityType();
//				newType.setClient(client);
//				newType.setCollectionName(String.format("%s_%s", client.getId(), typeName));
//				newType.setName(typeName);
//				entityTypeRepo.create(newType);
//			}
//		} else {
//			if (entityTypeRepo.getByName(typeName, userInfo.getClientId()) == null) {
//				requestContext.abortWith(Response.status(Response.Status.NOT_FOUND).build());
//			}
//		}
	}

}
