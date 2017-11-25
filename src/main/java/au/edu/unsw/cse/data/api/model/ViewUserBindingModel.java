package au.edu.unsw.cse.data.api.model;

import au.edu.unsw.cse.data.api.domain.entity.User;

public class ViewUserBindingModel {
  private String id;
  private String userName;
  private String role;

  public ViewUserBindingModel(User user) {
    id = user.getId();
    userName = user.getUserName();
    role = user.getRole();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
