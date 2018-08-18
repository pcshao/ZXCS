package bean;
/**
 * 仓库
 */
public class Depot {
	
	private int did;
	private String name;
	private String contact;
	private String phone;
	private String address;
	private String bz;
	
	/*
	 * 提供无参构造器
	 */
	public Depot(){
		
	}
	/*
	 * 提供最小构造器
	 */
	public Depot(int did, String name) {
		this.did = did;
		this.name = name;
	}
	/*
	 * 全构造器
	 */
	public Depot(int did, String name, String contact, String phone, String address, String bz) {
		this.did = did;
		this.name = name;
		this.contact = contact;
		this.phone = phone;
		this.address = address;
		this.bz = bz;
	}
	
	public int getDid() {
		return did;
	}
	public void setDid(int did) {
		this.did = did;
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
	@Override
	public String toString() {
		return name;
	}
	public String getInfo() {
		return "Depot [did=" + did + ", name=" + name + ", contact=" + contact
				+ ", phone=" + phone + ", address=" + address + ", bz=" + bz
				+ "]";
	}
	
}
