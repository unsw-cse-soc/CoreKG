package au.edu.unsw.cse.data.api.infrastructure;

import java.security.Principal;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.internal.inject.AbstractContainerRequestValueFactory;
import org.glassfish.jersey.server.internal.inject.AbstractValueFactoryProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.internal.inject.ParamInjectionResolver;
import org.glassfish.jersey.server.model.Parameter;

import au.edu.unsw.cse.data.api.security.AppUser;
import au.edu.unsw.cse.data.api.security.UserInfo;

public class UserInfoFactoryProvider extends AbstractValueFactoryProvider {

	@Inject
	public UserInfoFactoryProvider(MultivaluedParameterExtractorProvider mpep, ServiceLocator locator) {
		super(mpep, locator, Parameter.Source.UNKNOWN);
	}

	@Singleton
	public static final class InjectionResolver extends ParamInjectionResolver<AppUser> {
		public InjectionResolver() {
			super(UserInfoFactoryProvider.class);
		}
	}

	private static final class UserInfoFactory extends AbstractContainerRequestValueFactory<UserInfo> {

		@Override
		public UserInfo provide() {
			final Principal principal = getContainerRequest().getSecurityContext().getUserPrincipal();
			return (UserInfo) principal;
		}
	}

	@Override
	protected Factory<?> createValueFactory(Parameter arg0) {
		return new UserInfoFactory();
	}

}
