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
 * POS��ҵ��ӿ�
 * @author pcshao
 *
 */
public class PosService {

	/**
	 * ��Ʒ����������ģ����ѯ
	 *  ����goods���͵�vector
	 * @return
	 */
	public Vector<Goods> getGoodsLikeID(int id){
		return new GoodsDao().getGoodsLikeID(id);
	}
	/**
	 * ����
	 * 	��ȡ��Ʒ�б�
	 * 	���۶���			����
	 *  ���۶�����Ʒ����	����
	 *  ���				����
	 * @param goods
	 */
	public void checkOut(HashSet<Goods> sellGoods, Admin operate, 
			Customer c, Employee agent, PayWay payWay, Depot depot, double wantMoney, boolean isVip, Vip vip) {
		SellOrder order = new SellOrder();
		order.setId(new OrderDao().getCurrId(order));	//���ɶ���ID�����У�
		order.setDepot(depot);
		order.setCustomer(c);
		order.setAgent(agent);
		order.setOperator(operate);
		order.setPayWay(payWay);
		order.setWantMoney(wantMoney);
		order.setPayMoney(wantMoney); 	//Ĭ��POS����ʵ��������Ӧ�����
		//���۷������Ӷ�����������Ʒ���顢���¿��
		new SellService().addOrder(order, sellGoods);
		//����VIP����vip���Ѽ�¼����Ա�����ܶ�����
		if(isVip) {
			VipDao vipDap = new VipDao();
			vipDap.addRecord(vip, order.getId());
			vipDap.plusConsumer(vip.getId(), wantMoney);
		}
	}
	/**
	 * ��ȡ��Ա��Id
	 * @param vipId
	 * @return
	 */
	public Vip getVipByPhone(String vipPhone) {
		if(vipPhone.equals(""))
			return null;
		return new VipDao().getVipById(vipPhone);
	}
	/**
	 * ��ȡ�ͻ���Ϣ�б�
	 * @return
	 */
	public Vector<Customer> getCustomers() {
		return new CustomDao().getCustomersThroughVector();
	}
	/**
	 * ��ȡ����Ա��Ϣ�б�Ա����
	 * @return
	 */
	public Vector<Employee> getAgents() {
		return new EmployeesDao().getEmployees();
	}
	/**
	 * ��ȡ֧����ʽ�б�
	 * @return
	 */
	public Vector<PayWay> getPayWays() {
		return new PayWaysDao().getPayWaysInfo();
	}
	/**
	 * ��ȡ�ֿ���Ϣ�б�
	 * @return
	 */
	public Vector<Depot> getDepots() {
		return new DepotsDao().getDepots();
	}
}
