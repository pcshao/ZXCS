package service;

import java.util.HashSet;

import dao.*;
import bean.*;
import bean.orders.*;;

/**
 * 进货服务
 * 	库存表的初始化在这里完成
 */
public class InService {

	private OrderDao orderdao;
	private GoodsDao goodsdao;
	
	/**
	 * 进货发生（旧商品）
	 * 	订单					增加记录
	 * 	订单商品详情			增加记录
	 * 	库存增加				增加记录
	 */
	public void addOrder(InOrder order, HashSet<Goods> sellGoods) {
		orderdao = new OrderDao();
		orderdao.addOrders(order);
		orderdao.addOrdersDetails(order.getId(), sellGoods);
		int depotId = order.getDepot().getDid();
		//循环增加每件商品的库存 来源仓库不变，仓库库存可为负
		for(Goods g:sellGoods) {
			new GoodsDao().changeGoodsStore(g.getId(), depotId, g.getTempNum());
		}
	}
	/**
	 * 进货发生（新商品）
	 * 	订单					增加记录
	 * 	订单商品详情			增加记录
	 * 	库存增加				增加记录
	 *  会员的话会员消费记录表	增加记录
	 */
	public void addOrder(InOrder order, HashSet<Goods> sellGoods, Boolean isNew) {
		if(!isNew)
			addOrder(order,sellGoods);
		orderdao = new OrderDao();
		orderdao.addOrders(order);
		orderdao.addOrderDetails(order.getId(), sellGoods);
		int depotId = order.getDepot().getDid();
		//循环增加每件商品的库存 来源仓库不变，仓库库存可为负
		for(Goods g:sellGoods) {
			new GoodsDao().insertGoodsStore(g.getId(),depotId,g.getTempNum());
		}
	}
	/**
	 * 进货退货发生
	 * 	整单退货直接查到销售单的商品详情全部传ArrayList<Goods>过来
	 * 	单个退货在前台选择退货仓库和选择具体商品后生成ArrayList<Goods>后传过来
	 */
	public void addOrder(InOrder_tui order, HashSet<Goods> sellGoods) {
		orderdao = new OrderDao();
		orderdao.addOrders(order);
		//为了方便 如果整单退的话还是循环增加订单商品详情，但会发生数据冗余
		orderdao.addOrdersDetails(order.getId(), sellGoods);
		int depotId = order.getDepot().getDid();
		//循环减少每件商品的库存 来源仓库不变，仓库库存可为负
		for(Goods g:sellGoods) {
			new GoodsDao().changeGoodsStore(g.getId(), depotId, -g.getTempNum());
		}
	}
}
