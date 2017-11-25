package au.edu.unsw.cse.data.api.infrastructure;

import au.edu.unsw.cse.data.api.domain.abstracts.DatabaseRepository;
import au.edu.unsw.cse.data.api.domain.concrete.DatabaseRepositoryImp;
import au.edu.unsw.cse.data.api.index.abstracts.EntityIndex;
import au.edu.unsw.cse.data.api.index.concrete.ElasticSearchEntityIndex;
import javax.inject.Singleton;

import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

import au.edu.unsw.cse.data.api.domain.abstracts.ClientRepository;
import au.edu.unsw.cse.data.api.domain.abstracts.EntityRepository;
import au.edu.unsw.cse.data.api.domain.abstracts.EntityTypeRepository;
import au.edu.unsw.cse.data.api.domain.abstracts.RelationRepository;
import au.edu.unsw.cse.data.api.domain.abstracts.TokenRepository;
import au.edu.unsw.cse.data.api.domain.abstracts.UserRepository;
import au.edu.unsw.cse.data.api.domain.concrete.ClientRepositoryImp;
import au.edu.unsw.cse.data.api.domain.concrete.EntityRepositoryImp;
import au.edu.unsw.cse.data.api.domain.concrete.EntityTypeRepositoryImp;
import au.edu.unsw.cse.data.api.domain.concrete.RelationRepositoryImp;
import au.edu.unsw.cse.data.api.domain.concrete.TokenRepositoryImp;
import au.edu.unsw.cse.data.api.domain.concrete.UserRepositoryImp;
import au.edu.unsw.cse.data.api.security.AppUser;
import org.elasticsearch.client.transport.TransportClient;

public class DataApiBinder extends AbstractBinder {

  @Override
  protected void configure() {
    bindFactory(MongoFactory.class).to(MongoClient.class).in(Singleton.class);
    bindFactory(MorphiaFactory.class).to(Morphia.class).in(Singleton.class);
    bindFactory(MongoDatastoreFactory.class).to(Datastore.class).in(Singleton.class);
    bindFactory(ElasticSearchClientFactory.class).to(TransportClient.class).in(Singleton.class);
    bind(UserInfoFactoryProvider.class).to(ValueFactoryProvider.class).in(Singleton.class);
    bind(UserInfoFactoryProvider.InjectionResolver.class)
        .to(new TypeLiteral<InjectionResolver<AppUser>>() {}).in(Singleton.class);
    bind(EntityRepositoryImp.class).to(EntityRepository.class);
    bind(EntityTypeRepositoryImp.class).to(EntityTypeRepository.class);
    bind(RelationRepositoryImp.class).to(RelationRepository.class);
    bind(TokenRepositoryImp.class).to(TokenRepository.class);
    bind(UserRepositoryImp.class).to(UserRepository.class);
    bind(ClientRepositoryImp.class).to(ClientRepository.class);
    bind(DatabaseRepositoryImp.class).to(DatabaseRepository.class);
    bind(ElasticSearchEntityIndex.class).to(EntityIndex.class);
  }
}
