package bean;

public class Profile {
	
	int skill_level = 0;
	Job j;
	Resource r;
	
	public int getSkill_level() {
		return skill_level;
	}
	public void setSkill_level(int skill_level) {
		this.skill_level = skill_level;
	}
	public Job getJ() {
		return j;
	}
	public void setJ(Job j) {
		this.j = j;
	}
	public Resource getR() {
		return r;
	}
	public void setR(Resource r) {
		this.r = r;
	}
	public Profile(int skill_level, Job j, Resource r) {
		super();
		this.skill_level = skill_level;
		this.j = j;
		this.r = r;
	}

}