package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import bean.Depot;
import bean.Units;

import util.DataBaseUtil;
import util.LogMark;

/**
 * lxh专用
 */
public class GoodsUtilDao {

	private Connection conn = null;
	private PreparedStatement pstat = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private DataBaseUtil db = null;

	public GoodsUtilDao(){
		db = new DataBaseUtil();
	}
	/**
	 * 获取所有单位
	 * @return
	 */
	public Vector<Units> getAllUtil() {
		Vector<Units> ret=new Vector<Units>();
		String sql = "select * from units";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			while(rs.next()){
				Units u = new Units();
				u.setUnid(rs.getInt("unid"));
				u.setName(rs.getString("name"));
				u.setFz(rs.getString("fz"));
				ret.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return ret;
	}
	/**
	 * 根据名称查询对应单位的编号
	 * 
	 */
	public int  getUtilId(String name) {
		String sql = "select unid from units where name=?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1,name);
			rs = pstat.executeQuery();
			if(rs.next()){
				return rs.getInt("unid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return -1000;
	}
	/**
	 * 新增一个单位
	 * @param name
	 * @return
	 */
	public Boolean  insertUtil(int unid,String name,String fz) {
		String sql = "insert into units values(?,?,?)";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1,unid);
			pstat.setString(2,name);
			pstat.setString(3,fz);
			pstat.execute();
			return true;
		} catch (SQLException e) {
			return false;
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		
	}
	/**
	 * 获取最大的编号
	 * 
	 */
	public int getMaxId() {
		String sql = "select max(unid) unid from units";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			rs=pstat.executeQuery();
			if(rs.next()){
				return rs.getInt("unid");
			}
		} catch (SQLException e) {
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return -1000;
	}
}
