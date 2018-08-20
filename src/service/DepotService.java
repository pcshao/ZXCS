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
 * 仓库服务
 *  业务逻辑
 */
public class DepotService {
	
	/**
	 * 当所有仓库的同一种商品
	 * 的库存总和>商品设置的最小库存时 返回null
	 *  否则返回ArrayList<Goods> 需要补货
	 */
	public ArrayList<Goods> isAlert() {
		ArrayList<Goods> alertGoods = new ArrayList<Goods>();
		if((alertGoods=new GoodsDao().isAlert())!=null)
			return alertGoods;
		return null;
	}
	/**
	 * 库存调拨功能
	 * 	被调仓库
	 * 	调入仓库
	 * 	筛选出可调入的商品
	 */
	public void transDepot(DbOrder dbOrder, HashSet<Goods> transGoods) {
		GoodsDao goodsdao = new GoodsDao();
		int fromDepotId = dbOrder.getFromDepot().getDid();
		int toDepotId = dbOrder.getToDepot().getDid();
		//生成调拨记录单
		OrderDao orderDao = new OrderDao();
		orderDao.addDbOrder(dbOrder);
		//生成调拨详情单
		orderDao.addDbOrdersDetails(dbOrder.getId(), transGoods);
		//被调仓库减库存
		for(Goods g : transGoods)
			goodsdao.changeGoodsStore(g.getId(), fromDepotId, -g.getTempNum());
		//调入仓库加库存
		for(Goods g : transGoods)
			goodsdao.changeGoodsStore(g.getId(), toDepotId, g.getTempNum());
	}
	/**
	 * 增加新仓库(捕获异常)
	 * 	传入仓库对象
	 * 	boolean
	 */
	public boolean addDepot(Depot depot) {
		return new DepotsDao().addDepot(depot);
	}
	/**
	 * 修改仓库
	 * 	传入旧仓库对象did，name
	 *  传入新仓库信息对象，完全构造
	 * 	boolean
	 */
	public boolean editDepot(Depot oldDepot, Depot newDepot) {
		return new DepotsDao().editDepot(oldDepot, newDepot);
	}
	/**
	 * 根据content获取仓库信息
	 * 	传入仓库ID
	 * 	返回Depot
	 */
	public Vector<Depot> searchDepot(String content) {
		return new DepotsDao().searchDepot(content);
	}
	/**
	 * 删除仓库
	 * 	传入仓库ID
	 * 	返回boolean
	 */
	public Boolean removeDepotById(Depot depot) {
		return new DepotsDao().removeDepotById(depot);
	}
	/**
	 * 查询按月份结算的库存成本统计(时间分片)
	 * @param from	起始月份
	 * @param to	终止月份
	 * @param did	仓库id
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
		for(int j=fromY, tar=toY-fromY, tar2=1;j<=toY;j++) {	//tar辅助计数当年的月份
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
			v.add(a+"-"+b);	//添加时间段标记
			v.add(depotName);
			v.addAll(depotsDao.getDepotCostAll(b, InOrder.ORDERNAME, depotName));	//库存总成本 三个字段
			v.addAll(depotsDao.getDepotCostThroughMonth(a, b, depotName, new InOrder()));	//进货单
			v.addAll(depotsDao.getDepotCostThroughMonth(a, b, depotName, new SellOrder()));	//销售单
			v.add(depotsDao.getDepotProfitThroughMonth(a, b, depotName).get(0));//销售利润
			ret.add(v);
		}
		return ret;
	}
	/**
	 * 当前库存查询界面接口
	 * 	库存变动情况、商品变动情况、商品信息查询
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
