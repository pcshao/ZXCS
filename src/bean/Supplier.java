package bean;

/**
 * 供货商
 */
public class Supplier {
	
	private int sid;
	private String name;
	private String contact;
	private String phone;
	private String address;
	private String bz;
	
	/*
	 * 提供无参构造器
	 */
	public Supplier(){
		
	}
	/*
	 * 提供最小构造器
	 */
	public Supplier(int sid, String name) {
		this.sid = sid;
		this.name = name;
	}
	/*
	 * 全构造器
	 */
	public Supplier(int sid, String name, String contact, String phone, String address, String bz) {
		this.sid = sid;
		this.name = name;
		this.contact = contact;
		this.phone = phone;
		this.address = address;
		this.bz = bz;
	}
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
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
	
}
