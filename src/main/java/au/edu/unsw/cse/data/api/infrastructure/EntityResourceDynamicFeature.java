package au.edu.unsw.cse.data.api.infrastructure;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityResourceDynamicFeature implements DynamicFeature {

	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		if ("EntityResource".equals(resourceInfo.getResourceClass().getSimpleName())) {
			context.register(EntityTypeFilter.class);
		}
	}
}
