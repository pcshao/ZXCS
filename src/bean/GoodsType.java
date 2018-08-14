package bean;

public class GoodsType {
	
	private int self_id;
	private String name;
	private int super_id;
	
	public GoodsType(int self_id, String name, int super_id) {
		this.self_id = self_id;
		this.name = name;
		this.super_id = super_id;
	}
	public int getSelf_id() {
		return self_id;
	}
	public void setSelf_id(int self_id) {
		this.self_id = self_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSuper_id() {
		return super_id;
	}
	public void setSuper_id(int super_id) {
		this.super_id = super_id;
	}
	public String toString() {
		return name ;
	}
	public String getInfo() {
		return "Type [self_id=" + self_id + ", name=" + name + ", super_id="
				+ super_id + "]";
	}
}
