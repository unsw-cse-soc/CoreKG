package au.edu.unsw.cse.data.api.model;

public class CreateRelationBindingModel {
  private String sourceDatabase;
  private String sourceDataset;
  private String source;
  private String destinationDatabase;
  private String destinationDataset;
  private String destination;
  private String name;
  private String type;

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

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

  public String getSourceDatabase() {
    return sourceDatabase;
  }

  public void setSourceDatabase(String sourceDatabase) {
    this.sourceDatabase = sourceDatabase;
  }

  public String getSourceDataset() {
    return sourceDataset;
  }

  public void setSourceDataset(String sourceDataset) {
    this.sourceDataset = sourceDataset;
  }

  public String getDestinationDatabase() {
    return destinationDatabase;
  }

  public void setDestinationDatabase(String destinationDatabase) {
    this.destinationDatabase = destinationDatabase;
  }

  public String getDestinationDataset() {
    return destinationDataset;
  }

  public void setDestinationDataset(String destinationDataset) {
    this.destinationDataset = destinationDataset;
  }

}
