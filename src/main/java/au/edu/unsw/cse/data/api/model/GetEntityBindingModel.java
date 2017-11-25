package au.edu.unsw.cse.data.api.model;

public class GetEntityBindingModel {
	private String id;
	private String[] includes;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String[] getIncludes() {
		return includes;
	}
	public void setIncludes(String[] includes) {
		this.includes = includes;
	}
	
}
