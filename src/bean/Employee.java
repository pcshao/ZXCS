package bean;

/**
 * 员工
 */
public class Employee {

	private int eid;
	private String name;
	private String job;
	private String phone;
	private String address;
	private String bz;
	private String power;
	
	/*
	 * 提供无参构造器
	 */
	public Employee(){
		
	}
	/*
	 * 最小构造器
	 */
	public Employee(int eid, String name, String power) {
		super();
		this.eid = eid;
		this.name = name;
		this.power = power;
	}
	/*
	 * 全构造器
	 */
	public Employee(int eid, String name, String job, String phone, String address, String bz, String power) {
		this.eid = eid;
		this.name = name;
		this.job = job;
		this.phone = phone;
		this.address = address;
		this.bz = bz;
		this.power = power;
	}
	
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	@Override
	public String toString() {
		return name;
	}
	
}
