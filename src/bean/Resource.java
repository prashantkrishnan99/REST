package bean;

public class Resource {	
	Job j;
	int id = 0;
    String name = "";
    
	public Resource(Job j, int id, String name) {
		super();
		this.j = j;
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public Job getJ() {
		return j;
	}
	public void setJ(Job j) {
		this.j = j;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}   
}
