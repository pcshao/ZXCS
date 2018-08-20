package bean;

/**
 * 会员等级
 * @author pcshao
 *
 */
public class VipRange {

	private int rid;
	private double discount;
	private double rangeMoney;

	public VipRange() {
		
	}

	public VipRange(int rid, double discount, double rangeMoney) {
		this.rid = rid;
		this.discount = discount;
		this.rangeMoney = rangeMoney;
	}
	
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getRangeMoney() {
		return rangeMoney;
	}
	public void setRangeMoney(double rangeMoney) {
		this.rangeMoney = rangeMoney;
	}
	
}
