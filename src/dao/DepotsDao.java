package dao;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import util.CastUtil;
import util.DataBaseUtil;

import bean.Customer;
import bean.Depot;
import bean.GoodsType;
import bean.Supplier;
import bean.orders.InOrder;
import bean.orders.SellOrder;

public class DepotsDao {
	
	DataBaseUtil du;
	CastUtil cu;
	Vector item=null;
	Connection conn=null;
	PreparedStatement pstat=null;
	ResultSet rs=null;
	
	public DepotsDao(){
		du=new DataBaseUtil();
	}
	
	/**
	 * 获取仓库
	 */
	public Vector<Depot> getDepots(){
		Vector<Depot> ret = new Vector<Depot>();
		String sql = "select * from depots";
		conn = du.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			while(rs.next()){
				Depot c = new Depot();
				c.setDid(rs.getInt("did"));
				c.setName(rs.getString("name"));
				c.setContact(rs.getString("contact"));
				c.setPhone(rs.getString("phone"));
				c.setAddress(rs.getString("address"));
				c.setBz(rs.getString("bz"));
				ret.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			du.closeConnection(conn, pstat, rs);
		}
		return ret;
	}
	/**
	 * 增加新仓库
	 */
	public boolean addDepot(Depot depot) {
		String sql = "insert into depots (did,name,contact,phone,address,bz)values(?,?,?,?,?,?)";
		conn = du.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, depot.getDid());
			pstat.setString(2, depot.getName());
			pstat.setString(3, depot.getContact());
			pstat.setString(4, depot.getPhone());
			pstat.setString(5, depot.getAddress());
			pstat.setString(6, depot.getBz());
			pstat.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			du.closeConnection(conn, pstat);
		}
		return false;
	}
	/**
	 * 编辑仓库
	 */
	public boolean editDepot(Depot oldDepot, Depot newDepot) {
		String sql = "update depots set name=?,contact=?,phone=?,address=?,bz=? where did=? and name=?";
		conn = du.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, newDepot.getName());
			pstat.setString(2, newDepot.getContact());
			pstat.setString(3, newDepot.getPhone());
			pstat.setString(4, newDepot.getAddress());
			pstat.setString(5, newDepot.getBz());
			pstat.setInt(6, oldDepot.getDid());
			pstat.setString(7, oldDepot.getName());
			pstat.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			du.closeConnection(conn, pstat);
		}
		return false;
	}
	/**
	 * 根据仓库ID获取仓库信息
	 * @param depot
	 * @return
	 */
	public Depot getDepotById(Depot depot) {
		Depot ret = new Depot();
		String sql = "select * from depots where did=?";
		conn = du.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, depot.getDid());
			rs = pstat.executeQuery();
			while(rs.next()) {
				ret.setDid(rs.getInt("did"));
				ret.setName(rs.getString("name"));
				ret.setAddress(rs.getString("address"));
				ret.setContact(rs.getString("contact"));
				ret.setPhone(rs.getString("phone"));
				ret.setBz(rs.getString("bz"));
				return ret;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			du.closeConnection(conn, pstat, rs);
		}
		return ret;
	}

	public boolean removeDepotById(Depot depot) {
		String sql = "delete from depots where did=? and name=?";
		conn = du.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, depot.getDid());
			pstat.setString(2, depot.getName());
			return pstat.execute();
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			du.closeConnection(conn, pstat);
		}
		return false;
	}

	public Vector<Depot> searchDepot(String content) {
		Vector<Depot> ret = new Vector<Depot>();
		String sql = "select * from depots where did like ? or name like ?";
		conn = du.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, "%"+content+"%");
			pstat.setString(2, "%"+content+"%");
			rs = pstat.executeQuery();
			while(rs.next()) {
				Depot c = new Depot();
				c.setDid(rs.getInt("did"));
				c.setName(rs.getString("name"));
				c.setContact(rs.getString("contact"));
				c.setPhone(rs.getString("phone"));
				c.setAddress(rs.getString("address"));
				c.setBz(rs.getString("bz"));
				ret.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			du.closeConnection(conn, pstat, rs);
		}
		return ret;
	}
	/**
	 * 根据月份区间获得每一个月的库存成本统计
	 * 	包含结算月份，库存名称，库存数量，库存总成本，进货数量，进货总金额，销售数量，销售总金额，销售利润
	 * 	通过订单表获得数据（日期）
	 * @param from
	 * @param to
	 * @return
	 */
	public Vector getDepotCostThroughMonth(String from, String to, String did, InOrder type) {
		Vector ret = new Vector();
		String sql = "select sum(num) as allCount,sum(wantMoney) as allWantMoney "
				+ " from inOrdersDetails iod " 
				+ " inner join inOrders io on iod.oid=io.id"
				+ "	inner join depots d on d.did=io.depot"
				+ " and d.name = ?"
				+ " and SUBSTR(iod.oid, 0, 2)=? and odate between to_date(?,'yyyy-mm') and to_date(?,'yyyy-mm')";
		conn = du.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, did);
			pstat.setString(2, type.ORDERNAME);
			pstat.setString(3, from);
			pstat.setString(4, to);
			rs = pstat.executeQuery();
			while(rs.next()) {
				ret.add(rs.getString("allCount"));
				ret.add(rs.getDouble("allWantMoney"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			du.closeConnection(conn, pstat, rs);
		}
		return ret;
	}
	/**
	 * 根据月份区间获得每一个月的库存成本统计
	 * 	包含结算月份，库存名称，库存数量，库存总成本，进货数量，进货总金额，销售数量，销售总金额，销售利润
	 * 	通过订单表获得数据（日期）
	 * @param from
	 * @param to
	 * @return
	 */
	public Vector getDepotCostThroughMonth(String from, String to, String did, SellOrder type) {
		Vector ret = new Vector();
		String sql = "select sum(num) as allCount,sum(wantMoney) as allWantMoney "
				+ " from sellOrdersDetails iod " 
				+ " inner join sellOrders io on iod.oid=io.id"
				+ "	inner join depots d on d.did=io.depot"
				+ " and d.name = ?"
				+ " and SUBSTR(iod.oid, 0, 2)=? and odate between to_date(?,'yyyy-mm') and to_date(?,'yyyy-mm')";
		conn = du.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, did);
			pstat.setString(2, type.ORDERNAME);
			pstat.setString(3, from);
			pstat.setString(4, to);
			rs = pstat.executeQuery();
			while(rs.next()) {
				ret.add(rs.getString("allCount"));
				ret.add(rs.getDouble("allWantMoney"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			du.closeConnection(conn, pstat, rs);
		}
		return ret;
	}
	/**
	 * 库存总成本
	 * @param to
	 * @param ordername
	 */
	public Vector getDepotCostAll(String to, String ordername, String did) {
		Vector ret = new Vector();
		String sql = "select sum(num) as allCount,sum(wantMoney) as allWantMoney "
				+ " from inOrdersDetails iod " 
				+ " inner join inOrders io on iod.oid=io.id"
				+ "	inner join depots d on d.did=io.depot"
				+ " and d.name = ?"
				+ " and SUBSTR(iod.oid, 0, 2)=? and odate < to_date(?,'yyyy-mm')";
		conn = du.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, did);
			pstat.setString(2, ordername);
			pstat.setString(3, to);
			rs = pstat.executeQuery();
			while(rs.next()) {
				ret.add(rs.getString("allCount"));
				ret.add(rs.getDouble("allWantMoney"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			du.closeConnection(conn, pstat, rs);
		}
		return ret;
	}
	/**
	 * 获取选择的仓库的id
	 */
	public int getDepotsId(String name){
		int did=-1000;
		String sql = "select did from depots where name=?";
		conn = du.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, name);
			rs = pstat.executeQuery();
			if(rs.next()){
				did=rs.getInt("did");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			du.closeConnection(conn, pstat, rs);
		}
		return did;
	}
	/**
	 * 查询库存变动情况
	 * 	聚合查询，不做Cast转换，直接返回结果
	 */
	public Vector<Vector> getDepotsChangeInfo() {
		Vector<Vector> ret = new Vector<>();
		String sql = "select g.id,g.name,g.goodsid,g.unit,gs.count,goodsSellNum,pi,ps,g.sellprice,pi*gs.count depotAll,g.norms,g.bz from goods g inner join goodsStore gs on g.id=gs.gid inner join (select gid,avg(preInprice) pi,avg(preSellPrice) ps,sum(num)as goodsSellNum from sellOrdersDetails GROUP BY gid) sod on sod.gid=g.id";
		conn = du.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			while(rs.next()) {
				Vector v = new Vector<>();
				v.add(rs.getInt("id"));
				v.add(rs.getString("name"));
				v.add(rs.getString("goodSid"));
				v.add(rs.getInt("unit"));
				v.add(rs.getInt("count"));
				v.add(rs.getInt("goodsSellNum"));
				v.add(rs.getDouble("pi"));
				v.add(rs.getDouble("ps"));
				v.add(rs.getDouble("sellPrice"));
				v.add(rs.getDouble("depotAll"));
				v.add(rs.getString("norms"));
				v.add(rs.getString("bz"));
				ret.add(v);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * 库存变动查询
	 * 	根据仓库名、商品类别、商品编号或名称模糊
	 */
	public Vector<Vector> getDepotsChangeInfoByCondition(Depot depot, GoodsType goodsType, String search) {
		Vector<Vector> ret = new Vector<>();
		String sql = "select g.id,g.name,g.goodsid,g.unit,gs.count,goodsSellNum,pi,ps,g.sellprice,pi*gs.count depotAll,g.norms,g.bz from goods g "
				+ "inner join goodsStore gs on g.id=gs.gid "
				+ "inner join (select gid,avg(preInprice) pi,avg(preSellPrice) ps,sum(num)as goodsSellNum from sellOrdersDetails GROUP BY gid) "
				+ "sod on sod.gid=g.id and gs.depot=? and (g.name like ? or g.id like ?)";
		if(goodsType!=null)
			sql = "select g.id,g.name,g.goodsid,g.unit,gs.count,goodsSellNum,pi,ps,g.sellprice,pi*gs.count depotAll,g.norms,g.bz from goods g "
					+ "inner join goodsStore gs on g.id=gs.gid "
					+ "inner join (select gid,avg(preInprice) pi,avg(preSellPrice) ps,sum(num)as goodsSellNum from sellOrdersDetails GROUP BY gid) "
					+ "sod on sod.gid=g.id and gs.depot=? and (g.name like ? or g.id like ?) and g.type = ?";
		conn = du.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, depot.getDid());
			pstat.setString(2, "%"+search+"%");
			pstat.setString(3, "%"+search+"%");
			if(goodsType!=null)
				pstat.setInt(4, goodsType.getSelf_id());
			rs = pstat.executeQuery();
			while(rs.next()) {
				Vector v = new Vector<>();
				v.add(rs.getInt("id"));
				v.add(rs.getString("name"));
				v.add(rs.getString("goodSid"));
				v.add(rs.getInt("unit"));
				v.add(rs.getInt("count"));
				v.add(rs.getInt("goodsSellNum"));
				v.add(rs.getDouble("pi"));
				v.add(rs.getDouble("ps"));
				v.add(rs.getDouble("sellPrice"));
				v.add(rs.getDouble("depotAll"));
				v.add(rs.getString("norms"));
				v.add(rs.getString("bz"));
				ret.add(v);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			du.closeConnection(conn, pstat,rs);
		}
		return ret;
	}
	/**
	 * 库存变动查询
	 * 	根据仓库名、商品类别、商品辅助条码或名称模糊
	 */
	public Vector<Vector> getDepotsChangeInfoByCondition(Depot depot, GoodsType goodsType, String search,
			Boolean useGoodsid) {
		Vector<Vector> ret = new Vector<>();
		String sql = "select g.id,g.name,g.goodsid,g.unit,gs.count,goodsSellNum,pi,ps,g.sellprice,pi*gs.count depotAll,g.norms,g.bz from goods g "
				+ "inner join goodsStore gs on g.id=gs.gid "
				+ "inner join (select gid,avg(preInprice) pi,avg(preSellPrice) ps,sum(num)as goodsSellNum from sellOrdersDetails GROUP BY gid) "
				+ "sod on sod.gid=g.id and gs.depot=? and (g.name like ? or g.goodsid like ?)";
		if(goodsType!=null)
			sql = "select g.id,g.name,g.goodsid,g.unit,gs.count,goodsSellNum,pi,ps,g.sellprice,pi*gs.count depotAll,g.norms,g.bz from goods g "
					+ "inner join goodsStore gs on g.id=gs.gid "
					+ "inner join (select gid,avg(preInprice) pi,avg(preSellPrice) ps,sum(num)as goodsSellNum from sellOrdersDetails GROUP BY gid) "
					+ "sod on sod.gid=g.id and gs.depot=? and (g.name like ? or g.goodsid like ?) and g.type = ?";
		conn = du.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, depot.getDid());
			pstat.setString(2, "%"+search+"%");
			pstat.setString(3, "%"+search+"%");
			if(goodsType!=null)
				pstat.setInt(4, goodsType.getSelf_id());
			rs = pstat.executeQuery();
			while(rs.next()) {
				Vector v = new Vector<>();
				v.add(rs.getInt("id"));
				v.add(rs.getString("name"));
				v.add(rs.getString("goodSid"));
				v.add(rs.getInt("unit"));
				v.add(rs.getInt("count"));
				v.add(rs.getInt("goodsSellNum"));
				v.add(rs.getDouble("pi"));
				v.add(rs.getDouble("ps"));
				v.add(rs.getDouble("sellPrice"));
				v.add(rs.getDouble("depotAll"));
				v.add(rs.getString("norms"));
				v.add(rs.getString("bz"));
				ret.add(v);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			du.closeConnection(conn, pstat,rs);
		}
		return ret;
	}
	/**
	 * 获取时间片内的销售利润
	 * 	指定仓库
	 * @param a
	 * @param b
	 * @param depotName
	 * @return
	 */
	public Vector getDepotProfitThroughMonth(String a, String b, String depotName) {
		Vector ret = new Vector();
		String sql = "select sum(sum(preSellPrice*num-preInprice*num) )as profit "
				+ "from sellOrdersDetails sod inner join ( " + 
				"select so.id,so.odate from sellOrders so "
				+ "inner join sellordersdetails sod on sod.oid=so.id and SUBSTR(so.id, 0, 2)='XS' " + 
				" inner join depots d on so.depot=d.did and d.name=? "
				+ "and so.odate between to_date(?,'yyyy-mm') and to_date(?,'yyyy-mm')" + 
				")so on sod.oid=so.id GROUP BY gid";
		conn = du.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, depotName);
			pstat.setString(2, a);
			pstat.setString(3, b);
			rs = pstat.executeQuery();
			while(rs.next()) {
				ret.add(rs.getDouble("profit"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			du.closeConnection(conn, pstat, rs);
		}
		return ret;
	}

}
