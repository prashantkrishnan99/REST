package bean;

import java.util.Date;

public class Job {
     int id = 0;
     String type = "";
     Date start;
     Date finish;
     String status = "";
	public Job(int id, String type, Date start, Date finish, String status) {
		super();
		this.id = id;
		this.type = type;
		this.start = start;
		this.finish = finish;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getFinish() {
		return finish;
	}
	public void setFinish(Date finish) {
		this.finish = finish;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
