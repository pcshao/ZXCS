package service;

import java.util.ArrayList;
import java.util.HashSet;

import dao.*;
import bean.*;
import bean.orders.*;;

/**
 * ?为保证数据库连接关闭后重连，应该把new对象放在哪个层次比较好 目前dao
 * 销售服务
 * 	业务逻辑
 */
public class SellService {
	
	private OrderDao orderdao;
	private GoodsDao goodsdao;

	/**
	 * ?怎么根据传来的不同类型订单自动调用重载的dao
	 * ?把订单商品详情表的多次insert操作放在dao还是放在service
	 * 商品销售发生
	 * 	订单 					增加记录
	 * 	订单附属商品详情表 		增加记录
	 *  库存减少				变更记录
	 */
	public void addOrder(SellOrder order, HashSet<Goods> sellGoods) {
		orderdao = new OrderDao();
		GoodsDao goodsdao = new GoodsDao();
		//增加商品当时的进价 暂时无视效率问题 冗余售价，售价可以与当前订单的应收、实收金额中查询
		for(Goods g:sellGoods) {
			g.setInPrice(goodsdao.getInPriceById(g.getId()));
		}
		orderdao.addOrders(order);	//订单ID序列生成的，得重新拿到
		orderdao.addOrderDetails(order.getId(), sellGoods);
		int depotId = order.getDepot().getDid();
		//循环减少每件商品的库存 来源仓库不变，仓库库存可为负
		goodsdao = new GoodsDao();
		for(Goods g:sellGoods) {
			goodsdao.changeGoodsStore(g.getId(), depotId, -g.getTempNum());
		}
	}
	/**
	 * 商品销售发生(手动日期）
	 * 	订单 					增加记录
	 * 	订单附属商品详情表 		增加记录
	 *  库存减少				变更记录
	 *  会员的话会员消费记录表	增加记录		 <<----未完成
	 */
	public void addOrder(SellOrder order, HashSet<Goods> sellGoods, boolean manalDate) {
		orderdao = new OrderDao();
		orderdao.addOrders(order, manalDate);
		orderdao.addOrderDetails(order.getId(), sellGoods);
		int depotId = order.getDepot().getDid();
		//循环减少每件商品的库存 来源仓库不变，仓库库存可为负
		goodsdao = new GoodsDao();
		for(Goods g:sellGoods) {
			goodsdao.changeGoodsStore(g.getId(), depotId, -g.getTempNum());
		}
	}
	/**
	 * 商品销售退货发生
	 * 	整单退货直接查到销售单的商品详情全部传ArrayList<Goods>过来
	 * 	单个退货在前台选择商品后生成ArrayList<Goods>后传过来
	 */
	public void addOrder(SellOrder_tui order, HashSet<Goods> sellGoods) {
		orderdao = new OrderDao();
		GoodsDao goodsdao = new GoodsDao();
		//增加商品当时的进价 暂时无视效率问题 冗余售价，售价可以与当前订单的应收、实收金额中查询
		for(Goods g:sellGoods) {
			g.setInPrice(goodsdao.getInPriceById(g.getId()));
		}
		orderdao.addOrders(order);
		//为了方便 如果整单退的话还是循环增加订单商品详情，但会发生数据冗余
		orderdao.addOrderDetails(order.getId(), sellGoods);
		int depotId = order.getDepot().getDid();
		//循环增加每件商品的库存 去向仓库不变，仓库库存可为负
		for(Goods g:sellGoods) {
			new GoodsDao().changeGoodsStore(g.getId(), depotId, g.getTempNum());
		}
	}
}
