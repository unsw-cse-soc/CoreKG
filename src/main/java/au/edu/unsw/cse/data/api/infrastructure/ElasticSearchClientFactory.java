package au.edu.unsw.cse.data.api.infrastructure;

import java.net.UnknownHostException;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.client.transport.TransportClient;
import org.glassfish.hk2.api.Factory;
import java.net.InetAddress;

public class ElasticSearchClientFactory implements Factory<TransportClient> {


  @Override
  public TransportClient provide() {
    TransportClient client = null;
    try {
      client = TransportClient.builder().build()
          .addTransportAddress(
              new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return client;
  }

  @Override
  public void dispose(TransportClient instance) {
    instance.close();
  }
}
