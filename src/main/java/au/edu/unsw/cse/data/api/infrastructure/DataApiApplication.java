package au.edu.unsw.cse.data.api.infrastructure;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import au.edu.unsw.cse.data.api.security.AuthenticationFilter;

@ApplicationPath("/api")
public class DataApiApplication extends ResourceConfig {
	public DataApiApplication() {
		property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
		JacksonJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		register(jacksonJsonProvider);
		register(AuthenticationFilter.class);
		register(EntityResourceDynamicFeature.class);
		register(new DataApiBinder());
		packages(true, "au.edu.unsw.cse.data.api.resources");
	}
}
