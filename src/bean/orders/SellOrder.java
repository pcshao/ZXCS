package bean.orders;

import bean.AOrder;
import bean.Admin;
import bean.Customer;
import bean.Depot;
import bean.Employee;
import bean.PayWay;

/**
 * 销售订单表		XS
 * 	新增字段		客户
 */
public class SellOrder extends AOrder {

	public static String ORDERNAME = "XS";

	private Customer customer;

	/*
	 * 提供无参构造器
	 */
	public SellOrder() {
		super();
	}
	/*
	 * 提供最小构造器
	 */
	public SellOrder(String id, String odate, Admin operator) {
		super(id, odate, operator);
	}
	/*
	 * 全构造器
	 */
	public SellOrder(String id, String odate, Depot depot, double wantMoney, double payMoney, Employee agent,
			Admin operator, String bz, PayWay payWay, Customer customer) {
		super(id, odate, depot, wantMoney, payMoney, agent, operator, bz, payWay);
		this.customer = customer;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
