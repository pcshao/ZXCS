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
	public Vector<Vector> getAllInorder() {
		Vector<Vector> datas=new Vector<Vector>();
		String sql = "select i.id,i.odate,s.name sname,d.name dname,i.wantmoney,i.paymoney," +
				"e.name ename,a.name aname,p.name pname,i.bz from Inorders i "+
                "join depots d on i.depot=d.did  "+
                "join suppliers s  on i.supplier=s.sid "+
                "join employees e on i.agent=e.eid  "+
                "join admins a on i.operator=a.aid  "+
                "join Payways p on i.payway=p.pid";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
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
	public Vector<Vector> getAlwaysInorder() {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select g.id,g.name gname,g.inprice,u.name uname,i.num,ino.wantmoney," +
				"gs.count,g.norms,g.bz,ino.id inoid from goods g "+ 
		          "join  inordersdetails i on g.id=i.gid  "+ 
		          "join units u on g.unit=u.unid "+ 
		          "join goodsstore gs on g.id=gs.gid  "+ 
		          "join inOrders ino on i.oid=ino.id ";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
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
		String sql ="select g.id,g.name gname,g.inprice,u.name uname,i.num,ino.wantmoney," +
				"gs.count,g.norms,g.bz from goods g "+ 
		          "join  inordersdetails i on g.id=i.gid  "+ 
		          "join units u on g.unit=u.unid "+ 
		          "join goodsstore gs on g.id=gs.gid  "+ 
		          "join inOrders ino on i.oid=ino.id where ino.id=?";
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
	public Vector<Vector> getGoodsInorderInfo() {
		Vector<Vector> datas=new Vector<Vector>();
		String sql ="select s.name sname,io.id ioid,io.odate,g.id gid,g.name gname,u.name uname," +
				"g.inprice,iod.num,io.wantmoney,g.norms,d.name dname,e.name ename from goods g  "+ 
                "join units u on g.unit=u.unid  "+ 
                "join inordersdetails iod on iod.gid=g.id "+ 
                "join Inorders io on iod.oid=io.id "+ 
                "join depots d on d.did=io.depot "+ 
                "join suppliers s on s.sid=io.supplier "+ 
                "join employees e on e.eid=io.agent  ";
                ;
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
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
	
}
