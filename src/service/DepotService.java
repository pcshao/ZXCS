package service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Vector;

import bean.Depot;
import bean.Goods;
import bean.GoodsType;
import bean.Supplier;
import bean.orders.DbOrder;
import bean.orders.InOrder;
import bean.orders.InOrder_tui;
import bean.orders.SellOrder;
import bean.orders.SellOrder_tui;
import dao.DepotsDao;
import dao.GoodsDao;
import dao.OrderDao;

/**
 * �ֿ����
 *  ҵ���߼�
 */
public class DepotService {
	
	/**
	 * �����вֿ��ͬһ����Ʒ
	 * �Ŀ���ܺ�>��Ʒ���õ���С���ʱ ����null
	 *  ���򷵻�ArrayList<Goods> ��Ҫ����
	 */
	public ArrayList<Goods> isAlert() {
		ArrayList<Goods> alertGoods = new ArrayList<Goods>();
		if((alertGoods=new GoodsDao().isAlert())!=null)
			return alertGoods;
		return null;
	}
	/**
	 * ����������
	 * 	�����ֿ�
	 * 	����ֿ�
	 * 	ɸѡ���ɵ������Ʒ
	 */
	public void transDepot(DbOrder dbOrder, HashSet<Goods> transGoods) {
		GoodsDao goodsdao = new GoodsDao();
		int fromDepotId = dbOrder.getFromDepot().getDid();
		int toDepotId = dbOrder.getToDepot().getDid();
		//���ɵ�����¼��
		OrderDao orderDao = new OrderDao();
		orderDao.addDbOrder(dbOrder);
		//���ɵ������鵥
		orderDao.addDbOrdersDetails(dbOrder.getId(), transGoods);
		//�����ֿ�����
		for(Goods g : transGoods)
			goodsdao.changeGoodsStore(g.getId(), fromDepotId, -g.getTempNum());
		//����ֿ�ӿ��
		for(Goods g : transGoods)
			goodsdao.changeGoodsStore(g.getId(), toDepotId, g.getTempNum());
	}
	/**
	 * �����²ֿ�(�����쳣)
	 * 	����ֿ����
	 * 	boolean
	 */
	public boolean addDepot(Depot depot) {
		return new DepotsDao().addDepot(depot);
	}
	/**
	 * �޸Ĳֿ�
	 * 	����ɲֿ����did��name
	 *  �����²ֿ���Ϣ������ȫ����
	 * 	boolean
	 */
	public boolean editDepot(Depot oldDepot, Depot newDepot) {
		return new DepotsDao().editDepot(oldDepot, newDepot);
	}
	/**
	 * ����content��ȡ�ֿ���Ϣ
	 * 	����ֿ�ID
	 * 	����Depot
	 */
	public Vector<Depot> searchDepot(String content) {
		return new DepotsDao().searchDepot(content);
	}
	/**
	 * ɾ���ֿ�
	 * 	����ֿ�ID
	 * 	����boolean
	 */
	public Boolean removeDepotById(Depot depot) {
		return new DepotsDao().removeDepotById(depot);
	}
	/**
	 * ��ѯ���·ݽ���Ŀ��ɱ�ͳ��(ʱ���Ƭ)
	 * @param from	��ʼ�·�
	 * @param to	��ֹ�·�
	 * @param did	�ֿ�id
	 * @return
	 * @author pcshao
	 */
	public Vector<Vector> getDepotCostThroughMonths(String from, String to, String depotName) {
		Vector<Vector> ret = new Vector<Vector>();
		DepotsDao depotsDao = new DepotsDao();
		int fromY = Integer.parseInt(from.substring(0,4));
		int toY = Integer.parseInt(to.substring(0,4));
		int fromM = Integer.parseInt(from.substring(4, 6));
		int toM = Integer.parseInt(to.substring(4, 6));
		ArrayList<String> months = new ArrayList<String>();
		for(int j=fromY, tar=toY-fromY, tar2=1;j<=toY;j++) {	//tar��������������·�
			if(tar<=0) {
				if(tar2==0) {
					for(int i=1;i<=fromM;i++) {
						months.add(String.valueOf(j)
								+((String.valueOf(i).length()==2)?String.valueOf(i):"0"+String.valueOf(i)));
					}
				}
				for(int i=fromM;i<=toM;i++) {
					months.add(String.valueOf(j)
							+((String.valueOf(i).length()==2)?String.valueOf(i):"0"+String.valueOf(i)));
				}
			}
			else {
				for(int i=fromM;i<=12;i++) {
					months.add(String.valueOf(j)
							+((String.valueOf(i).length()==2)?String.valueOf(i):"0"+String.valueOf(i)));
				}
				--tar;
				tar2=0;
			}
		}
		for(int i=0;i<months.size()-1;i++) {
			String a = months.get(i);
			String b = months.get(i+1);
			Vector v = new Vector<>();
			v.add(a+"-"+b);	//���ʱ��α��
			v.add(depotName);
			v.addAll(depotsDao.getDepotCostAll(b, InOrder.ORDERNAME, depotName));	//����ܳɱ� �����ֶ�
			v.addAll(depotsDao.getDepotCostThroughMonth(a, b, depotName, new InOrder()));	//������
			v.addAll(depotsDao.getDepotCostThroughMonth(a, b, depotName, new SellOrder()));	//���۵�
			v.add(depotsDao.getDepotProfitThroughMonth(a, b, depotName).get(0));//��������
			ret.add(v);
		}
		return ret;
	}
	/**
	 * ��ǰ����ѯ����ӿ�
	 * 	���䶯�������Ʒ�䶯�������Ʒ��Ϣ��ѯ
	 */
	public Vector<Vector> getDepotsChangeInfo() {
		return new DepotsDao().getDepotsChangeInfo();
	}
	public Vector<Vector> getDepotsChangeInfoByCondition(Depot depot, GoodsType goodsType, String search, Boolean useGoodsid) {
		if(useGoodsid)
			return new DepotsDao().getDepotsChangeInfoByCondition(depot, goodsType, search, useGoodsid);
		return new DepotsDao().getDepotsChangeInfoByCondition(depot, goodsType, search);
	}
	
}
