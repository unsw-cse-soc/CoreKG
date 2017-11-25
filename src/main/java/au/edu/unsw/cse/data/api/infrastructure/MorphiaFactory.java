package au.edu.unsw.cse.data.api.infrastructure;

import org.glassfish.hk2.api.Factory;
import org.mongodb.morphia.Morphia;

public class MorphiaFactory implements Factory<Morphia> {
  // private Datastore datastore;
  //
  // public DatastoreFactory() {
  // final Morphia morphia = new Morphia();
  // morphia.mapPackage("au.edu.unsw.cse.domain.entity");
  // ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
  // InputStream input = classLoader.getResourceAsStream("mongo.properties");
  // Properties properties = new Properties();
  // try {
  // properties.load(input);
  // datastore = morphia.createDatastore(new MongoClient(properties.getProperty("mongohost"),
  // Integer.parseInt(properties.getProperty("mongoport"))), properties.getProperty("db"));
  // datastore.ensureIndexes();
  // } catch (IOException e) {
  // }
  // }

  @Override
  public void dispose(Morphia morphia) {

  }

  @Override
  public Morphia provide() {
    final Morphia morphia = new Morphia();
    morphia.mapPackage("au.edu.unsw.cse.domain.entity");
    return morphia;
  }
}
