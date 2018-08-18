package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import bean.Units;

import util.DataBaseUtil;

public class InOrderDao {
	
	private Connection conn = null;
	private PreparedStatement pstat = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private DataBaseUtil db = null;

	public InOrderDao(){
		db = new DataBaseUtil();
	}
	/**
	 * 获取所有订单信息
	 * @return
	 */
	public Vector<Vector> getAllInorder(String oid_or_sname) {
		Vector<Vector> datas=new Vector<Vector>();
		String sql = "select i.id,i.odate,s.name sname,d.name dname,i.wantmoney,i.paymoney," +
				"e.name ename,a.name aname,p.name pname,i.bz from Inorders i "+
                "join depots d on i.depot=d.did  "+
                "join suppliers s  on i.supplier=s.sid "+
                "join employees e on i.agent=e.eid  "+
                "join admins a on i.operator=a.aid  "+
                "join Payways p on i.payway=p.pid where i.id like ? or s.name like ?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1,"%"+oid_or_sname+"%");
			pstat.setString(2,"%"+oid_or_sname+"%");
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getString("id"));
				data.add(rs.getDate("odate"));
				data.add(rs.getString("sname"));
				data.add(rs.getString("dname"));
				data.add(rs.getDouble("wantmoney"));
				data.add(rs.getDouble("paymoney"));
				data.add(rs.getString("ename"));
				data.add(rs.getString("aname"));
				data.add(rs.getString("pname"));
				data.add(rs.getString("bz"));
				datas.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 * 获取整单退货汇总表信息
	 * @return
	 */
	public Vector<Vector> getAlwaysInorder1(String oid_or_sname) {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select g.id,g.name gname,g.inprice,u.name uname,i.num,ino.wantmoney," +
				"gs.count,g.norms,g.bz,ino.id inoid from goods g "+ 
		          "join  inordersdetails i on g.id=i.gid  "+ 
		          "join units u on g.unit=u.unid "+ 
		          "join goodsstore gs on g.id=gs.gid  "+ 
		          "join inOrders ino on i.oid=ino.id  "+
		          "join suppliers s  on ino.supplier=s.sid  where ino.id like ? or s.name like ?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, "%"+oid_or_sname+"%");
			pstat.setString(2, "%"+oid_or_sname+"%");
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getString("id"));
				data.add(rs.getString("gname"));
				data.add(rs.getDouble("inPrice"));
				data.add(rs.getString("uname"));
				data.add(rs.getString("num"));
				data.add(rs.getDouble("wantmoney"));
				data.add(rs.getDouble("count"));
				data.add(rs.getString("norms"));
				data.add(rs.getString("bz"));
				data.add(rs.getString("inoid"));
				datas.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	
	/**
	 * 获取整单退货采购商品的详细信息
	 * @return
	 */
	public Vector<Vector> getAlwaysInorder(String id) {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select g.id,g.name gname,g.inprice,u.name uname,i.num,ino.wantmoney,s.name sname," +
				"gs.count,g.norms,g.bz from goods g "+ 
		          "join  inordersdetails i on g.id=i.gid  "+ 
		          "join units u on g.unit=u.unid "+ 
		          "join goodsstore gs on g.id=gs.gid  "+ 
		          "join inOrders ino on i.oid=ino.id  "+
		          "join suppliers s on s.sid=ino.supplier "+
		          "where ino.id=?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1,id);
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getString("id"));
				data.add(rs.getString("gname"));
				data.add(rs.getDouble("inPrice"));
				data.add(rs.getString("uname"));
				data.add(rs.getString("num"));
				data.add(rs.getDouble("wantmoney"));
				data.add(rs.getDouble("count"));
				data.add(rs.getString("norms"));
				data.add(rs.getString("bz"));
				data.add(rs.getString("sname"));
				datas.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 * 获取整单退货商品采购明细
	 * @return
	 */
	public Vector<Vector> getGoodsInorderInfo(int gid,String oid) {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select s.name sname,io.id ioid,io.odate,g.id gid,g.name gname,u.name uname," +
				"g.inprice,iod.num,io.wantmoney,g.norms,d.name dname,e.name ename from goods g  "+ 
                "join units u on g.unit=u.unid  "+ 
                "join inordersdetails iod on iod.gid=g.id "+ 
                "join Inorders io on iod.oid=io.id "+ 
                "join depots d on d.did=io.depot "+ 
                "join suppliers s on s.sid=io.supplier "+ 
                "join employees e on e.eid=io.agent where g.id=? and io.id=? ";
                ;
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1,gid);
			pstat.setString(2,oid);
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getString("sname"));
				data.add(rs.getString("ioid"));
				data.add(rs.getString("odate"));
				data.add(rs.getInt("gid"));
				data.add(rs.getString("gname"));
				data.add(rs.getString("uname"));
				data.add(rs.getString("norms"));
				data.add(rs.getDouble("inprice"));
				data.add(rs.getInt("num"));
				data.add(rs.getDouble("wantmoney"));
				data.add(rs.getString("dname"));
				data.add(rs.getString("ename"));
				datas.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 * 获取整单退货商品采购明细
	 * @return
	 */
	public Vector<Vector> getGoodsInorderInfo(String oid_or_sname) {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select s.name sname,io.id ioid,io.odate,g.id gid,g.name gname,u.name uname," +
				"g.inprice,iod.num,io.wantmoney,g.norms,d.name dname,e.name ename from goods g  "+ 
                "join units u on g.unit=u.unid  "+ 
                "join inordersdetails iod on iod.gid=g.id "+ 
                "join Inorders io on iod.oid=io.id "+ 
                "join depots d on d.did=io.depot "+ 
                "join suppliers s on s.sid=io.supplier "+ 
                "join employees e on e.eid=io.agent  where io.id like ? or s.name like ?";
                ;
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, "%"+oid_or_sname+"%");
			pstat.setString(2, "%"+oid_or_sname+"%");
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getString("sname"));
				data.add(rs.getString("ioid"));
				data.add(rs.getString("odate"));
				data.add(rs.getInt("gid"));
				data.add(rs.getString("gname"));
				data.add(rs.getString("uname"));
				data.add(rs.getString("norms"));
				data.add(rs.getDouble("inprice"));
				data.add(rs.getInt("num"));
				data.add(rs.getDouble("wantmoney"));
				data.add(rs.getString("dname"));
				data.add(rs.getString("ename"));
				datas.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}/**
	 * table1获取所有订单信息
	 * @return
	 */
	public Vector<Vector> getAllInorder(String date1,String date2) {
		Vector<Vector> datas=new Vector<Vector>();
		String sql = "select i.id,i.odate,s.name sname,d.name dname,i.wantmoney,i.paymoney," +
				"e.name ename,a.name aname,p.name pname,i.bz from Inorders i "+
                "join depots d on i.depot=d.did  "+
                "join suppliers s  on i.supplier=s.sid "+
                "join employees e on i.agent=e.eid  "+
                "join admins a on i.operator=a.aid  "+
                "join Payways p on i.payway=p.pid where  i.odate between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd')";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, date1);
			pstat.setString(2, date2);
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getString("id"));
				data.add(rs.getDate("odate"));
				data.add(rs.getString("sname"));
				data.add(rs.getString("dname"));
				data.add(rs.getDouble("wantmoney"));
				data.add(rs.getDouble("paymoney"));
				data.add(rs.getString("ename"));
				data.add(rs.getString("aname"));
				data.add(rs.getString("pname"));
				data.add(rs.getString("bz"));
				datas.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 * table5根据日期获取整单退货商品采购明细
	 * @return
	 */
	public Vector<Vector> getGoodsInorderInfo(String date1,String date2){
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select s.name sname,io.id ioid,io.odate,g.id gid,g.name gname,u.name uname," +
				"g.inprice,iod.num,io.wantmoney,g.norms,d.name dname,e.name ename from goods g  "+ 
                "join units u on g.unit=u.unid  "+ 
                "join inordersdetails iod on iod.gid=g.id "+ 
                "join Inorders io on iod.oid=io.id "+ 
                "join depots d on d.did=io.depot "+ 
                "join suppliers s on s.sid=io.supplier "+ 
                "join employees e on e.eid=io.agent where  io.odate between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd')";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, date1);
			pstat.setString(2, date2);
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getString("sname"));
				data.add(rs.getString("ioid"));
				data.add(rs.getString("odate"));
				data.add(rs.getInt("gid"));
				data.add(rs.getString("gname"));
				data.add(rs.getString("uname"));
				data.add(rs.getString("norms"));
				data.add(rs.getDouble("inprice"));
				data.add(rs.getInt("num"));
				data.add(rs.getDouble("wantmoney"));
				data.add(rs.getString("dname"));
				data.add(rs.getString("ename"));
				datas.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 * table3根据日期获取整单退货汇总表信息
	 * @return
	 */
	public Vector<Vector> getAlwaysInorder(String date1,String date2) {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select g.id,g.name gname,g.inprice,u.name uname,i.num,ino.wantmoney," +
				"gs.count,g.norms,g.bz,ino.id inoid from goods g "+ 
		          "join  inordersdetails i on g.id=i.gid  "+ 
		          "join units u on g.unit=u.unid "+ 
		          "join goodsstore gs on g.id=gs.gid  "+ 
		          "join inOrders ino on i.oid=ino.id where  ino.odate between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd')";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, date1);
			pstat.setString(2, date2);
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getString("id"));
				data.add(rs.getString("gname"));
				data.add(rs.getDouble("inPrice"));
				data.add(rs.getString("uname"));
				data.add(rs.getString("num"));
				data.add(rs.getDouble("wantmoney"));
				data.add(rs.getDouble("count"));
				data.add(rs.getString("norms"));
				data.add(rs.getString("bz"));
				data.add(rs.getString("inoid"));
				datas.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 *查询所有退货订单
	 * @return
	 */
	public Vector<Vector> getTuiInorder() {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select ino.id,ino.odate,s.name sname,d.name dname,ino.wantmoney,ino.paymoney,"+
				 "e.name ename,a.name aname,ino.bz "+
                "from inOrders ino  "+
                "join suppliers s on ino.supplier=s.sid  "+
                "join depots d on ino.depot=d.did  "+
                "join employees e on ino.agent=e.eid  "+
                "join admins a on ino.operator=a.aid where ino.id like '%CT%'"; 
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getString("id"));
				data.add(rs.getString("odate"));
				data.add(rs.getString("sname"));
				data.add(rs.getString("dname"));
				data.add(-rs.getDouble("wantmoney"));
				data.add(-rs.getDouble("paymoney"));
				data.add("采购退货");
				data.add(rs.getString("ename"));
				data.add(rs.getString("aname"));
				data.add(rs.getString("bz"));
				datas.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 *查询所有退货商品
	 * @return
	 */
	public Vector<Vector> getTuiGoods() {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select g.id gid,g.name gname,u.name uname,g.inprice,inordd.num,"+ 
				"g.norms from Inorders ino "+
                "join inordersdetails inordd on ino.id=inordd.oid "+
                "join goods g on g.id=inordd.gid "+
                "join units u on g.unit=u.unid"; 
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getInt("gid"));
				data.add(rs.getString("gname"));
				data.add(rs.getString("uname"));
				double price=rs.getDouble("inprice");
				data.add(-price);
				int num=rs.getInt("num");
				data.add(-num);
				data.add(-num*price);
				data.add(rs.getString("norms"));
				datas.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 *按照订单id查询所有退货商品
	 * @return
	 */
	public Vector<Vector> getTuiGoods(String id) {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select g.id gid,g.name gname,u.name uname,g.inprice,inordd.num,"+ 
				"g.norms from Inorders ino "+
                "join inordersdetails inordd on ino.id=inordd.oid "+
                "join goods g on g.id=inordd.gid "+
                "join units u on g.unit=u.unid where ino.id=? "; 
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getInt("gid"));
				data.add(rs.getString("gname"));
				data.add(rs.getString("uname"));
				double price=rs.getDouble("inprice");
				data.add(-price);
				int num=rs.getInt("num");
				data.add(-num);
				data.add(-num*price);
				data.add(rs.getString("norms"));
				datas.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 *按照商品id查询商品的详细信息
	 * @return
	 */
	public Vector<Vector> getTuiGoodsInfo(int id) {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select s.name sname,ino.id inoid,ino.odate,g.id gid,g.name gname,u.name uname,g.inprice,"+
				" inordd.num,ino.wantmoney,g.norms,e.name ename,d.name dname "+
                 "from inOrders ino  "+
                " join suppliers s on ino.supplier=s.sid "+
                " join depots d on ino.depot=d.did "+
                " join employees e on ino.agent=e.eid "+
                " join  inordersdetails inordd on ino.id=inordd.oid "+
                " join goods g on g.id=inordd.gid "+
                " join units u on g.unit=u.unid where g.id=?"; 
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getString("sname"));
				data.add(rs.getString("inoid"));
				data.add(rs.getString("odate"));
				data.add(rs.getInt("gid"));
				data.add(rs.getString("gname"));
				data.add(rs.getString("uname"));
				double price=rs.getDouble("inprice");
				data.add(-price);
				int num=rs.getInt("num");
				data.add(-num);
				data.add(-num*price);
				data.add(rs.getString("norms"));
				data.add(rs.getString("ename"));
				data.add(rs.getString("dname"));
				datas.add(data);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 *按照商品id查询商品的详细信息
	 * @return
	 */
	public Vector<Vector> getTuiGoodsInfo(String id) {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select s.name sname,ino.id inoid,ino.odate,g.id gid,g.name gname,u.name uname,g.inprice,"+
				" inordd.num,ino.wantmoney,g.norms,e.name ename,d.name dname "+
                 "from inOrders ino  "+
                " join suppliers s on ino.supplier=s.sid "+
                " join depots d on ino.depot=d.did "+
                " join employees e on ino.agent=e.eid "+
                " join  inordersdetails inordd on ino.id=inordd.oid "+
                " join goods g on g.id=inordd.gid "+
                " join units u on g.unit=u.unid where gid like ?"; 
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, "%"+id+"%");
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getString("sname"));
				data.add(rs.getString("inoid"));
				data.add(rs.getString("odate"));
				data.add(-rs.getInt("gid"));
				data.add(rs.getString("gname"));
				data.add(rs.getString("uname"));
				double price=rs.getDouble("inprice");
				data.add(-price);
				int num=rs.getInt("num");
				data.add(-num);
				data.add(-num*price);
				data.add(rs.getString("norms"));
				data.add(rs.getString("ename"));
				data.add(rs.getString("dname"));
				datas.add(data);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 *按编号或者名所有退货订单
	 * @return
	 */
	public Vector<Vector> getTuiInorder(String id_or_name) {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select ino.id,ino.odate,s.name sname,d.name dname,ino.wantmoney,ino.paymoney,"+
				 "e.name ename,a.name aname,ino.bz "+
                "from inOrders ino  "+
                "join suppliers s on ino.supplier=s.sid  "+
                "join depots d on ino.depot=d.did  "+
                "join employees e on ino.agent=e.eid  "+
                "join admins a on ino.operator=a.aid where ino.id like '%CT%' and " + 
               "(s.name like ? or ino.id like ?)";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, "%"+id_or_name+"%");
			pstat.setString(2, "%"+id_or_name+"%");
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getString("id"));
				data.add(rs.getString("odate"));
				data.add(rs.getString("sname"));
				data.add(rs.getString("dname"));
				data.add(-rs.getDouble("wantmoney"));
				data.add(-rs.getDouble("paymoney"));
				data.add("采购退货");
				data.add(rs.getString("ename"));
				data.add(rs.getString("aname"));
				data.add(rs.getString("bz"));
				datas.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		
		return datas;
	}
	/**
	 *按编号或者名所有退货订单
	 * @return
	 */
	public Vector<Vector> getTuiGoods1(String id_or_name) {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select g.id gid,g.name gname,u.name uname,g.inprice,inordd.num,"+ 
				"g.norms from Inorders ino "+
				"join suppliers s on ino.supplier=s.sid  "+
                "join inordersdetails inordd on ino.id=inordd.oid "+
                "join goods g on g.id=inordd.gid "+
                "join units u on g.unit=u.unid where "+ 
                "s.name like ? or ino.id like ?"; 
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, "%"+id_or_name+"%");
			pstat.setString(2, "%"+id_or_name+"%");
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getInt("gid"));
				data.add(rs.getString("gname"));
				data.add(rs.getString("uname"));
				double price=rs.getDouble("inprice");
				data.add(-price);
				int num=rs.getInt("num");
				data.add(-num);
				data.add(-num*price);
				data.add(rs.getString("norms"));
				datas.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 *按编号或者名所有退货订单
	 * @return
	 */
	public Vector<Vector> getTuiGoodsInfo1(String id_or_name) {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select s.name sname,ino.id inoid,ino.odate,g.id gid,g.name gname,u.name uname,g.inprice,"+
				" inordd.num,ino.wantmoney,g.norms,e.name ename,d.name dname "+
                 "from inOrders ino  "+
                " join suppliers s on ino.supplier=s.sid "+
                " join depots d on ino.depot=d.did "+
                " join employees e on ino.agent=e.eid "+
                " join  inordersdetails inordd on ino.id=inordd.oid "+
                " join goods g on g.id=inordd.gid "+
                " join units u on g.unit=u.unid where "+
                "s.name like ? or ino.id like ?"; 
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, "%"+id_or_name+"%");
			pstat.setString(2, "%"+id_or_name+"%");
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getString("sname"));
				data.add(rs.getString("inoid"));
				data.add(rs.getString("odate"));
				data.add(-rs.getInt("gid"));
				data.add(rs.getString("gname"));
				data.add(rs.getString("uname"));
				double price=rs.getDouble("inprice");
				data.add(-price);
				int num=rs.getInt("num");
				data.add(-num);
				data.add(-num*price);
				data.add(rs.getString("norms"));
				data.add(rs.getString("ename"));
				data.add(rs.getString("dname"));
				datas.add(data);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	//-----------------------------------------------------------------------------------------------------
	/**
	 *按日期查询所有退货订单
	 * @return
	 */
	public Vector<Vector> getTuiInorder(String date1,String date2) {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select ino.id,ino.odate,s.name sname,d.name dname,ino.wantmoney,ino.paymoney,"+
				 "e.name ename,a.name aname,ino.bz "+
                "from inOrders ino  "+
                "join suppliers s on ino.supplier=s.sid  "+
                "join depots d on ino.depot=d.did  "+
                "join employees e on ino.agent=e.eid  "+
                "join admins a on ino.operator=a.aid where ino.id like '%CT%' and " + 
               "ino.odate between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd')";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, date1);
			pstat.setString(2, date2);
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getString("id"));
				data.add(rs.getString("odate"));
				data.add(rs.getString("sname"));
				data.add(rs.getString("dname"));
				data.add(-rs.getDouble("wantmoney"));
				data.add(-rs.getDouble("paymoney"));
				data.add("采购退货");
				data.add(rs.getString("ename"));
				data.add(rs.getString("aname"));
				data.add(rs.getString("bz"));
				datas.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		
		return datas;
	}
	/**
	 *按照日期查询所有退货商品
	 * @return
	 */
	public Vector<Vector> getTuiGoods(String date1,String date2) {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select g.id gid,g.name gname,u.name uname,g.inprice,inordd.num,"+ 
				"g.norms from Inorders ino "+
                "join inordersdetails inordd on ino.id=inordd.oid "+
                "join goods g on g.id=inordd.gid "+
                "join units u on g.unit=u.unid where "+ 
                "ino.odate between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd')"; 
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, date1);
			pstat.setString(2, date2);
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getInt("gid"));
				data.add(rs.getString("gname"));
				data.add(rs.getString("uname"));
				double price=rs.getDouble("inprice");
				data.add(-price);
				int num=rs.getInt("num");
				data.add(-num);
				data.add(-num*price);
				data.add(rs.getString("norms"));
				datas.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 *按照时间查询商品的详细信息
	 * @return
	 */
	public Vector<Vector> getTuiGoodsInfo(String date1,String date2) {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select s.name sname,ino.id inoid,ino.odate,g.id gid,g.name gname,u.name uname,g.inprice,"+
				" inordd.num,ino.wantmoney,g.norms,e.name ename,d.name dname "+
                 "from inOrders ino  "+
                " join suppliers s on ino.supplier=s.sid "+
                " join depots d on ino.depot=d.did "+
                " join employees e on ino.agent=e.eid "+
                " join  inordersdetails inordd on ino.id=inordd.oid "+
                " join goods g on g.id=inordd.gid "+
                " join units u on g.unit=u.unid where "+
                "ino.odate between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd')"; 
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, date1);
			pstat.setString(2, date2);
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getString("sname"));
				data.add(rs.getString("inoid"));
				data.add(rs.getString("odate"));
				data.add(-rs.getInt("gid"));
				data.add(rs.getString("gname"));
				data.add(rs.getString("uname"));
				double price=rs.getDouble("inprice");
				data.add(-price);
				int num=rs.getInt("num");
				data.add(-num);
				data.add(-num*price);
				data.add(rs.getString("norms"));
				data.add(rs.getString("ename"));
				data.add(rs.getString("dname"));
				datas.add(data);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/*-----------------------------账务往来-----------------------------------*/
	/**
	 * 查询跟所有供货商的账务往来
	 * @param date1
	 * @param date2
	 * @return
	 */
	public Vector<Vector> getSOrder() {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select s.name sname,ino.odate,ino.id inoid,ino.bz,"
				+ "ino.wantmoney,ino.paymoney,e.name ename,a.name aname "+
				 "from inorders ino "+
                 "join suppliers s on s.sid=ino.supplier "+
                 "join employees e on e.eid=ino.agent "+
                 "join admins a on a.aid=ino.operator"; 
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getString("sname"));
				data.add(rs.getString("odate"));
				data.add(rs.getString("inoid"));
				data.add(rs.getString("bz"));
				data.add(rs.getDouble("wantmoney"));
				data.add(rs.getDouble("paymoney"));
				data.add(rs.getString("ename"));
				data.add(rs.getString("aname"));
				datas.add(data);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 * 根据订单号获取商品信息
	 */
	public Vector<Vector> getGoodsInfo(String oid) {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select g.id,g.name gname,u.name uname,g.inprice,g.norms,inordd.num,ino.wantmoney "+
                "from inorders ino "+
               "join inordersdetails inordd on ino.id=inordd.oid  "+
               "join goods g on g.id=inordd.gid "+
               "join units u on u.unid=g.unit where ino.id=?"; 
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1,oid);
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getInt("id"));
				data.add(rs.getString("gname"));
				data.add(rs.getString("uname"));
				data.add(rs.getDouble("inprice"));
				data.add(rs.getString("norms"));
				data.add(rs.getInt("num"));
				data.add(rs.getDouble("wantmoney"));
				datas.add(data);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 * 根据订单号获取商品支付信息和供货商
	 */
	public Vector<Vector> getOrderInfo(String oid) {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select s.name sname,ino.odate,ino.id inoid, ino.bz,"
				+ "ino.wantMoney,e.name ename,a.name aname,s.bz sbz " + 
				" from inorders ino  " + 
				" join suppliers s on s.sid=ino.supplier " + 
				" join employees e on e.eid= ino.agent " + 
				"join admins a on a.aid =ino.operator where ino.id like ?"; 
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1,"%"+oid+"%");
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getString("sname"));
				data.add(rs.getString("odate"));
				data.add(rs.getString("inoid"));
				data.add(rs.getString("bz"));
				data.add(rs.getDouble("wantMoney"));
				data.add(rs.getString("ename"));
				data.add(rs.getString("aname"));
				data.add(rs.getString("sbz"));
				datas.add(data);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 * 删除指定订单
	 */
	public Vector<Vector> deleteOrder(String oid) {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="delete  inordersdetails where oid=?"; 
		String sql1 ="delete  inorders where id=?"; 
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1,oid);
			pstat.execute();
			pstat = conn.prepareStatement(sql1);
			pstat.setString(1,oid);
			pstat.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 * 按日期排序--------升序
	 */
	public Vector<Vector> getSOrderOfASC() {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select s.name sname,ino.odate,ino.id inoid,ino.bz,"
				+ "ino.wantmoney,ino.paymoney,e.name ename,a.name aname "+
				 "from inorders ino "+
                 "join suppliers s on s.sid=ino.supplier "+
                 "join employees e on e.eid=ino.agent "+
                 "join admins a on a.aid=ino.operator order by  ino.odate asc"; 
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getString("sname"));
				data.add(rs.getString("odate"));
				data.add(rs.getString("inoid"));
				data.add(rs.getString("bz"));
				data.add(rs.getDouble("wantmoney"));
				data.add(rs.getDouble("paymoney"));
				data.add(rs.getString("ename"));
				data.add(rs.getString("aname"));
				datas.add(data);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 * 按供货商名字查询
	 */
	public Vector<Vector> getSOrder(String sname) {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select s.name sname,ino.odate,ino.id inoid,ino.bz,"
				+ "ino.wantmoney,ino.paymoney,e.name ename,a.name aname "+
				 "from inorders ino "+
                 "join suppliers s on s.sid=ino.supplier "+
                 "join employees e on e.eid=ino.agent "+
                 "join admins a on a.aid=ino.operator where s.name like ?"; 
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, "%"+sname+"%");
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getString("sname"));
				data.add(rs.getString("odate"));
				data.add(rs.getString("inoid"));
				data.add(rs.getString("bz"));
				data.add(rs.getDouble("wantmoney"));
				data.add(rs.getDouble("paymoney"));
				data.add(rs.getString("ename"));
				data.add(rs.getString("aname"));
				datas.add(data);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 * 查询指定时间的订单表
	 */
	public Vector<Vector> getSOrder(String date1,String date2) {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select s.name sname,ino.odate,ino.id inoid,ino.bz,"
				+ "ino.wantmoney,ino.paymoney,e.name ename,a.name aname "+
				 "from inorders ino "+
                 "join suppliers s on s.sid=ino.supplier "+
                 "join employees e on e.eid=ino.agent "+
                 "join admins a on a.aid=ino.operator where ino.odate between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd')"; 
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, date1);
			pstat.setString(2, date2);
			rs = pstat.executeQuery();
			
			while(rs.next()){
				Vector data=new Vector();
				data.add(rs.getString("sname"));
				data.add(rs.getString("odate"));
				data.add(rs.getString("inoid"));
				data.add(rs.getString("bz"));
				data.add(rs.getDouble("wantmoney"));
				data.add(rs.getDouble("paymoney"));
				data.add(rs.getString("ename"));
				data.add(rs.getString("aname"));
				datas.add(data);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
}
