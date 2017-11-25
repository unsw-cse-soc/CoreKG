package au.edu.unsw.cse.data.api.infrastructure;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.glassfish.hk2.api.Factory;
import com.mongodb.MongoClient;

public class MongoFactory implements Factory<MongoClient> {

  private String mongoHost;
  private int mongoPort;

  public MongoFactory() {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    InputStream input = classLoader.getResourceAsStream("mongo.properties");
    Properties properties = new Properties();
    try {
      properties.load(input);
      mongoHost = properties.getProperty("mongohost");
      mongoPort = Integer.parseInt(properties.getProperty("mongoport"));
    } catch (IOException e) {

    }
  }

  @Override
  public void dispose(MongoClient mongoClient) {
    mongoClient.close();
  }

  @Override
  public MongoClient provide() {
    return new MongoClient(mongoHost, mongoPort);
  }
}
