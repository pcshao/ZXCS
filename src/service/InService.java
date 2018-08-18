package service;

import java.util.HashSet;

import dao.*;
import bean.*;
import bean.orders.*;;

/**
 * ��������
 * 	����ĳ�ʼ�����������
 */
public class InService {

	private OrderDao orderdao;
	private GoodsDao goodsdao;
	
	/**
	 * ��������������Ʒ��
	 * 	����					���Ӽ�¼
	 * 	������Ʒ����			���Ӽ�¼
	 * 	�������				���Ӽ�¼
	 */
	public void addOrder(InOrder order, HashSet<Goods> sellGoods) {
		orderdao = new OrderDao();
		orderdao.addOrders(order);
		orderdao.addOrdersDetails(order.getId(), sellGoods);
		int depotId = order.getDepot().getDid();
		//ѭ������ÿ����Ʒ�Ŀ�� ��Դ�ֿⲻ�䣬�ֿ����Ϊ��
		for(Goods g:sellGoods) {
			new GoodsDao().changeGoodsStore(g.getId(), depotId, g.getTempNum());
		}
	}
	/**
	 * ��������������Ʒ��
	 * 	����					���Ӽ�¼
	 * 	������Ʒ����			���Ӽ�¼
	 * 	�������				���Ӽ�¼
	 *  ��Ա�Ļ���Ա���Ѽ�¼��	���Ӽ�¼
	 */
	public void addOrder(InOrder order, HashSet<Goods> sellGoods, Boolean isNew) {
		if(!isNew)
			addOrder(order,sellGoods);
		orderdao = new OrderDao();
		orderdao.addOrders(order);
		orderdao.addOrderDetails(order.getId(), sellGoods);
		int depotId = order.getDepot().getDid();
		//ѭ������ÿ����Ʒ�Ŀ�� ��Դ�ֿⲻ�䣬�ֿ����Ϊ��
		for(Goods g:sellGoods) {
			new GoodsDao().insertGoodsStore(g.getId(),depotId,g.getTempNum());
		}
	}
	/**
	 * �����˻�����
	 * 	�����˻�ֱ�Ӳ鵽���۵�����Ʒ����ȫ����ArrayList<Goods>����
	 * 	�����˻���ǰ̨ѡ���˻��ֿ��ѡ�������Ʒ������ArrayList<Goods>�󴫹���
	 */
	public void addOrder(InOrder_tui order, HashSet<Goods> sellGoods) {
		orderdao = new OrderDao();
		orderdao.addOrders(order);
		//Ϊ�˷��� ��������˵Ļ�����ѭ�����Ӷ�����Ʒ���飬���ᷢ����������
		orderdao.addOrdersDetails(order.getId(), sellGoods);
		int depotId = order.getDepot().getDid();
		//ѭ������ÿ����Ʒ�Ŀ�� ��Դ�ֿⲻ�䣬�ֿ����Ϊ��
		for(Goods g:sellGoods) {
			new GoodsDao().changeGoodsStore(g.getId(), depotId, -g.getTempNum());
		}
	}
}
