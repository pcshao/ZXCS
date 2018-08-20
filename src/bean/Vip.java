package bean;

public class Vip {
	
	private int id;
	private String name;
	private int range;
	private double countMoney;
	private double allPayMoney;
	private String odate;
	private String phone;
	
	private double discount;	//会员等级表的折扣力度
	
	public Vip() {
	}
	public Vip(int id, String name, int range, double countMoney, double allPayMoney, String odate, String phone) {
		this.id = id;
		this.name = name;
		this.range = range;
		this.countMoney = countMoney;
		this.allPayMoney = allPayMoney;
		this.odate = odate;
		this.phone = phone;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getId() {
		return id;
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
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public double getCountMoney() {
		return countMoney;
	}
	public void setCountMoney(double countMoney) {
		this.countMoney = countMoney;
	}
	public double getAllPayMoney() {
		return allPayMoney;
	}
	public void setAllPayMoney(double allPayMoney) {
		this.allPayMoney = allPayMoney;
	}
	public String getOdate() {
		return odate;
	}
	public void setOdate(String odate) {
		this.odate = odate;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	@Override
	public String toString() {
		return "Vip [id=" + id + ", name=" + name + ", range=" + range + ", countMoney=" + countMoney + ", allPayMoney="
				+ allPayMoney + ", odate=" + odate + ", phone=" + phone + ", discount=" + discount + "]";
	}
	
}
