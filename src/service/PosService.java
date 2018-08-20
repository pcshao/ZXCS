package service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;


import bean.Admin;
import bean.Customer;
import bean.Depot;
import bean.Employee;
import bean.Goods;
import bean.PayWay;
import bean.Vip;
import bean.orders.SellOrder;
import dao.CustomDao;
import dao.DepotsDao;
import dao.EmployeesDao;
import dao.GoodsDao;
import dao.OrderDao;
import dao.PayWaysDao;
import dao.VipDao;

/**
 * POS机业务接口
 * @author pcshao
 *
 */
public class PosService {

	/**
	 * 商品编码输入框的模糊查询
	 *  返回goods类型的vector
	 * @return
	 */
	public Vector<Goods> getGoodsLikeID(int id){
		return new GoodsDao().getGoodsLikeID(id);
	}
	/**
	 * 结账
	 * 	获取商品列表
	 * 	销售订单			增加
	 *  销售订单商品详情	增加
	 *  库存				更新
	 * @param goods
	 */
	public void checkOut(HashSet<Goods> sellGoods, Admin operate, 
			Customer c, Employee agent, PayWay payWay, Depot depot, double wantMoney, boolean isVip, Vip vip) {
		SellOrder order = new SellOrder();
		order.setId(new OrderDao().getCurrId(order));	//生成订单ID（序列）
		order.setDepot(depot);
		order.setCustomer(c);
		order.setAgent(agent);
		order.setOperator(operate);
		order.setPayWay(payWay);
		order.setWantMoney(wantMoney);
		order.setPayMoney(wantMoney); 	//默认POS销售实付金额等于应付金额
		//销售服务增加订单、订单商品详情、更新库存
		new SellService().addOrder(order, sellGoods);
		//结算VIP增加vip消费记录，会员消费总额增加
		if(isVip) {
			VipDao vipDap = new VipDao();
			vipDap.addRecord(vip, order.getId());
			vipDap.plusConsumer(vip.getId(), wantMoney);
		}
	}
	/**
	 * 获取会员卡Id
	 * @param vipId
	 * @return
	 */
	public Vip getVipByPhone(String vipPhone) {
		if(vipPhone.equals(""))
			return null;
		return new VipDao().getVipById(vipPhone);
	}
	/**
	 * 获取客户信息列表
	 * @return
	 */
	public Vector<Customer> getCustomers() {
		return new CustomDao().getCustomersThroughVector();
	}
	/**
	 * 获取导购员信息列表（员工）
	 * @return
	 */
	public Vector<Employee> getAgents() {
		return new EmployeesDao().getEmployees();
	}
	/**
	 * 获取支付方式列表
	 * @return
	 */
	public Vector<PayWay> getPayWays() {
		return new PayWaysDao().getPayWaysInfo();
	}
	/**
	 * 获取仓库信息列表
	 * @return
	 */
	public Vector<Depot> getDepots() {
		return new DepotsDao().getDepots();
	}
}
