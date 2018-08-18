package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import util.DataBaseUtil;

/**
 * 调拨单DAO
 * @author pcshao
 */
public class DbOrdersDao {
	
	private Connection conn = null;
	private PreparedStatement pstat = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private DataBaseUtil db = null;

	public DbOrdersDao(){
		db = new DataBaseUtil();
	}
	/**
	 * 获取   盘点单据所有信息   
	 * @return
	 */
	public Vector<Vector> getpdOrders(){
		Vector<Vector> datas=new Vector<Vector>();
		Vector data=null;
		String sql="select * from PdOrders";
		Connection conn=db.getConnection();
		PreparedStatement pstat=null;
		ResultSet rs=null;
		try{
			pstat=conn.prepareStatement(sql);					
			rs=pstat.executeQuery();
			while(rs.next()){
				data=new Vector();
				data.add(rs.getInt("id"));
				data.add(rs.getString("odate"));
				data.add(rs.getString("dname"));
				data.add(rs.getString("operator"));						
				data.add(rs.getString("bz"));
				datas.add(data);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 * 获取   盘点 商品详情所有信息   
	 * @return
	 */
	public Vector<Vector> getpdOrdersDetails(){
		Vector<Vector> datas=new Vector<Vector>();
		Vector data=null;
		String sql="select * from Pdordersdetails";
		Connection conn=db.getConnection();
		PreparedStatement pstat=null;
		ResultSet rs=null;
		try{
			pstat=conn.prepareStatement(sql);					
			rs=pstat.executeQuery();
			while(rs.next()){
				data=new Vector();
				data.add(rs.getInt("id"));
				data.add(rs.getString("time"));					
				data.add(rs.getString("bz"));
				datas.add(data);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 * 根据调拨单号查找调拨单详细信息
	 * @param orderId
	 * @return
	 */
	public Vector<Vector> getDbOrderDetails(String orderId) {
		Vector<Vector> ret = new Vector<>();
		String sql = "select g.id,g.name gname,dod.num,u.name unit,g.norms"
				+ " from dbOrdersDetails dod "
				+ "inner join goods g on g.id=dod.gid"
				+ " inner join units u on u.unid=g.unit"
				+ " and dod.oid = ?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, orderId);
			rs = pstat.executeQuery();
			while(rs.next()) {
				Vector v = new Vector<>();
				v.add(rs.getInt("id"));
				v.add(rs.getString("gname"));
				v.add(rs.getString("num"));
				v.add(rs.getString("unit"));
				v.add(rs.getString("norms"));
				ret.add(v);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
