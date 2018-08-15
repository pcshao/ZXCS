package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import util.*;
import dao.*;
import service.*;
import bean.*;
import bean.orders.*;;

/**
 * 单据DAO
 * 	采购单据、采购退货单据、销售单据、销售退货单据重载参数类型名
 *  传入单据对象
 *  持久化数据
 */
public class OrderDao {
	
	//	select 'XS'||to_char(sysdate,'yyyymmdd')||to_char(orders_id.nextval,'00000000') from dual;
	
	private Connection conn = null;
	private PreparedStatement pstat = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private DataBaseUtil db = null;

	{
		db = new DataBaseUtil();
	}
	/*@Test
	public void test() {
		new AdminService().Login("admin", "123");
		InOrder o = new InOrder ();
		o.setSupplier(new Supplier(201,"高桥百货"));
		o.setBz("测试成功");
		o.setDepot(new Depot(101,"主仓库"));
		o.setWantMoney(2000.55);
		o.setPayMoney(2000.55);
		o.setAgent(new Employee(1001,"张三丰","仓储"));
		o.setOperator(new Admin(11,"admin","123","是"));
		o.setPayWay(new PayWay(201,"现金"));
		addOrders(o);
	}*/
	/**
	 * 采购单
	 */
	public void addOrders(InOrder order) {
		String sql = "insert into inOrders values("
				+ "'"+order.getId()+"'"
				+ ",to_date('"+order.getOdate()+"','yyyy-mm-dd')"
				+ ",?,?,?,?,?,?,?,?)";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, order.getSupplier().getSid());
			pstat.setInt(2, order.getDepot().getDid());
			pstat.setDouble(3, order.getWantMoney());
			pstat.setDouble(4, order.getPayMoney());
			pstat.setInt(5, order.getAgent().getEid());
			pstat.setInt(6, order.getOperator().getAid());
			pstat.setInt(7, order.getPayWay().getPid());
			pstat.setString(8, order.getBz());
			pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			LogMark.LogWrite("-采购进货订单操作-");
			db.closeConnection(conn, pstat);
		}
	}
	/**
	 * 采购订单——商品详情表
	 */
	public void addOrdersDetails(String orderId, HashSet<Goods> inGoods) {
		String sql = "insert into inOrdersDetails (id,oid,gid,num)values(sq_inordersdetails_id.nextval,?,?,?)";
		conn = db.getConnection();
		try {
			for(Goods g : inGoods) {
				pstat = conn.prepareStatement(sql);
				pstat.setString(1,orderId);
				pstat.setInt(2, g.getId());
				pstat.setInt(3, g.getTempNum());
				pstat.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat);
		}
	}
	/**
	 * 销售订单-商品详情
	 */
	public void addOrderDetails(String orderId, HashSet<Goods> sellGoods) {
		String sql = "insert into sellOrdersDetails (id,oid,gid,num,preInprice,preSellPrice)values(sq_sellOrdersdetails_id.nextval,?,?,?,?,?)";
		conn = db.getConnection();
		try {
			for(Goods g : sellGoods) {
				pstat = conn.prepareStatement(sql);
				pstat.setString(1,orderId);
				pstat.setInt(2, g.getId());
				pstat.setInt(3, g.getTempNum());
				pstat.setDouble(4, g.getInPrice());
				pstat.setDouble(5, g.getSellPrice());
				pstat.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat);
		}
	}
	/**
	 * 采购退货单
	 */
	public void addOrders(InOrder_tui order) {
		String sql = "insert into inOrders values("
				+ "'"+order.getId()+"'"
				+ ",to_date('"+order.getOdate()+"','yyyy-mm-dd')"
				+ ",?,?,?,?,?,?,?,?)";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, order.getSupplier().getSid());
			pstat.setInt(2, order.getDepot().getDid());
			pstat.setDouble(3, order.getWantMoney());
			pstat.setDouble(4, order.getPayMoney());
			pstat.setInt(5, order.getAgent().getEid());
			pstat.setInt(6, order.getOperator().getAid());
			pstat.setInt(7, order.getPayWay().getPid());
			pstat.setString(8, order.getBz());
			pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			LogMark.LogWrite("-采购退货订单操作-");
			db.closeConnection(conn, pstat, rs);
		}
	}
	/**
	 * 销售单
	 * 	单据日期即数据库当前操作时间
	 */
	public void addOrders(SellOrder order) {
		String sql = "insert into sellOrders (id,odate,"
				+ "customer,depot,wantMoney,payMoney,agent,operator,bz,payWay)values("
				+ "?"
				+ ",sysdate"
				+ ",?,?,?,?,?,?,?,?)";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, order.getId());
			pstat.setInt(2, order.getCustomer().getCid());
			pstat.setInt(3, order.getDepot().getDid());
			pstat.setDouble(4, order.getWantMoney());
			pstat.setDouble(5, order.getPayMoney());
			pstat.setInt(6, order.getAgent().getEid());
			pstat.setInt(7, order.getOperator().getAid());
			pstat.setString(8, order.getBz());
			pstat.setInt(9, order.getPayWay().getPid());
			pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			LogMark.LogWrite("-销售订单操作-");
			db.closeConnection(conn, pstat, rs);
		}
	}
	/**
	 * 销售单（手动日期）
	 * 	单据日期即数据库当前操作时间
	 */
	public void addOrders(SellOrder order, boolean manalDate) {
		String sql = "insert into sellOrders (id,odate,"
				+ "customer,depot,wantMoney,payMoney,agent,operator,bz,payWay)values("
				+"?,"
				+ "to_date('"+order.getOdate()+"','yyyy-MM-dd')"
				+ ",?,?,?,?,?,?,?,?)";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, order.getId());
			pstat.setInt(2, order.getCustomer().getCid());
			pstat.setInt(3, order.getDepot().getDid());
			pstat.setDouble(4, order.getWantMoney());
			pstat.setDouble(5, order.getPayMoney());
			pstat.setInt(6, order.getAgent().getEid());
			pstat.setInt(7, order.getOperator().getAid());
			pstat.setString(8, order.getBz());
			pstat.setInt(9, order.getPayWay().getPid());
			pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			LogMark.LogWrite("-销售订单操作-");
			db.closeConnection(conn, pstat, rs);
		}
	}
	/**
	 * 调拨记录单
	 */
	public void addDbOrder(DbOrder dbOrder) {
		String sql = "insert into dbOrders (id,odate,"
				+ "fromDepot,toDepot,agent,operator,bz)values("
				+ "?"
				+ ",to_date(?,'yyyy-MM-dd')"
				+ ",?,?,?,?,?)";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dbOrder.getId());
			pstat.setString(2, dbOrder.getOdate());
			pstat.setInt(3, dbOrder.getFromDepot().getDid());
			pstat.setInt(4, dbOrder.getToDepot().getDid());
			pstat.setInt(5, dbOrder.getAgent().getEid());
			pstat.setInt(6, dbOrder.getOperator().getAid());
			pstat.setString(7, dbOrder.getBz());
			pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			LogMark.LogWrite("-调拨订单操作-");
			db.closeConnection(conn, pstat);
		}
	}
	/**
	 * 销售退货单
	 */
	public void addOrders(SellOrder_tui order) {
		String sql = "insert into sellOrders (id,odate,"
			+ "customer,depot,wantMoney,payMoney,agent,operator,bz,payWay)values("
			+"?,"
			+ "to_date('"+order.getOdate()+"','yyyy-MM-dd')"
			+ ",?,?,?,?,?,?,?,?)";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, order.getId());
			pstat.setInt(2, order.getCustomer().getCid());
			pstat.setInt(3, order.getDepot().getDid());
			pstat.setDouble(4, order.getWantMoney());
			pstat.setDouble(5, order.getPayMoney());
			pstat.setInt(6, order.getAgent().getEid());
			pstat.setInt(7, order.getOperator().getAid());
			pstat.setString(8, order.getBz());
			pstat.setInt(9, order.getPayWay().getPid());
			pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			LogMark.LogWrite("-销售退货订单操作-");
			db.closeConnection(conn, pstat, rs);
		}
	}
	/**
	 * 获取采购订单号
	 */
	public String getInOrderId() {
		String currId = "";
		String sql = "select '"+InOrder.ORDERNAME+"'||TO_CHAR(SYSDATE,'YYYYMMDD')||TRIM(TO_CHAR(sq_orders_id.nextval,'00000000')) as id from dual";
		conn = db.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next())
				currId = rs.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return currId;
	}
	/**
	* 获取采购退货订单号
	*/
	public String getInOrderTuiId() {
		String currId = "";
		String sql = "select '"+InOrder_tui.ORDERNAME+"'||TO_CHAR(SYSDATE,'YYYYMMDD')||TRIM(TO_CHAR(sq_orders_id.nextval,'00000000')) as id from dual";
		conn = db.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next())
				currId = rs.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return currId;
	}
	/**
	 * 取得当前销售订单序列ID
	 * @return
	 */
	public String getCurrId(SellOrder type) {
		String currId = "";
		String sql = "select '"+type.ORDERNAME+"'||TO_CHAR(SYSDATE,'YYYYMMDD')||TRIM(TO_CHAR(sq_orders_id.nextval,'00000000')) as id from dual";
		conn = db.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next())
				currId = rs.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return currId;
	}
	/**
	 * 取得当前销售退货订单序列ID
	 * @return
	 */
	public String getCurrId(SellOrder_tui type) {
		String currId = "";
		String sql = "select '"+type.ORDERNAME+"'||TO_CHAR(SYSDATE,'YYYYMMDD')||TRIM(TO_CHAR(sq_orders_id.nextval,'00000000')) as id from dual";
		conn = db.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next())
				currId = rs.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return currId;
	}
	/**
	 * 获取调拨单的ID
	 * @return
	 */
	public String getDbOrderId() {
		String currId = "";
		String sql = "select '"+DbOrder.ORDERNAME+"'||TO_CHAR(SYSDATE,'YYYYMMDD')||TRIM(TO_CHAR(sq_orders_id.nextval,'00000000')) as id from dual";
		conn = db.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next())
				currId = rs.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return currId;
	}
	/**
	 * 生成调拨详情单
	 * @param id
	 * @param transGoods
	 */
	public void addDbOrdersDetails(String id, HashSet<Goods> transGoods) {
		String sql = "insert into dbOrdersDetails (id,oid,gid,num)"
				+ "values(sq_dbOrdersdetails_id.nextval,?,?,?)";
		conn = db.getConnection();
		try {
			for(Goods g : transGoods) {
				pstat = conn.prepareStatement(sql);
				pstat.setString(1,id);
				pstat.setInt(2, g.getId());
				pstat.setInt(3, g.getTempNum());
				pstat.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat);
		}
	}
}
