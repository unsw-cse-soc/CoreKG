package au.edu.unsw.cse.data.api.infrastructure;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.glassfish.hk2.api.Factory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class MongoDatastoreFactory implements Factory<Datastore> {

  private Datastore datastore;

  public MongoDatastoreFactory() {
    final Morphia morphia = new Morphia();
    morphia.mapPackage("au.edu.unsw.cse.domain.entity");
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    InputStream input = classLoader.getResourceAsStream("mongo.properties");
    Properties properties = new Properties();
    try {
      properties.load(input);
      datastore = morphia.createDatastore(new MongoClient(properties.getProperty("mongohost"),
          Integer.parseInt(properties.getProperty("mongoport"))), properties.getProperty("db"));
      datastore.ensureIndexes();
    } catch (IOException e) {
    }
  }

  @Override
  public Datastore provide() {
    return datastore;
  }

  @Override
  public void dispose(Datastore instance) {
    instance.getMongo().close();
  }

}
