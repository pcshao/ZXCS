package bean;

/**
 * 订单父类
 * 	派生多种单据
 * @author pcshao
 */
public abstract class AOrder {
	
	private String id;				//单据号
	private String odate;			//开单日期
	private Depot depot;			//仓库
	private double wantMoney;		//应付金额
	private double payMoney;		//实付金额
	private Employee agent;			//经办人
	private Admin operator;			//操作员
	private String bz;				//备注
	private PayWay payWay;			//支付方式
	
	/*
	 * 不提供无参构造器
	 */
	public AOrder() {
		
	}
	/*
	 * 提供最小化构造器
	 */
	public AOrder(String id, String odate, Admin operator) {
		this.id = id;
		this.odate = odate;
		this.operator = operator;
	}
	/*
	 * 提供全构造器给派生类
	 */
	public AOrder(String id, String odate, Depot depot, double wantMoney, double payMoney, Employee agent,
			Admin operator, String bz, PayWay payWay) {
		this.id = id;
		this.odate = odate;
		this.depot = depot;
		this.wantMoney = wantMoney;
		this.payMoney = payMoney;
		this.agent = agent;
		this.operator = operator;
		this.bz = bz;
		this.payWay = payWay;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOdate() {
		return odate;
	}
	public void setOdate(String odate) {
		this.odate = odate;
	}
	public Depot getDepot() {
		return depot;
	}
	public void setDepot(Depot depot) {
		this.depot = depot;
	}
	public double getWantMoney() {
		return wantMoney;
	}
	public void setWantMoney(double wantMoney) {
		this.wantMoney = wantMoney;
	}
	public double getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(double payMoney) {
		this.payMoney = payMoney;
	}
	public Employee getAgent() {
		return agent;
	}
	public void setAgent(Employee agent) {
		this.agent = agent;
	}
	public Admin getOperator() {
		return operator;
	}
	public void setOperator(Admin operator) {
		this.operator = operator;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public PayWay getPayWay() {
		return payWay;
	}
	public void setPayWay(PayWay payWay) {
		this.payWay = payWay;
	}

}