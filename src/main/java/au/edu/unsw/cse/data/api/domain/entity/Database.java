package au.edu.unsw.cse.data.api.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.mongodb.morphia.annotations.Reference;

public class Database extends Entity {

  private String name;

  private String type;

  @Reference
  @JsonIgnore
  private Client client;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

}
