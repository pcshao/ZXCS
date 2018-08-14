package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import bean.PayWay;

import util.DataBaseUtil;

public class PayWaysDao {

	/**
	 * Ö§¸¶·½Ê½
	 */
	private Connection conn = null;
	private PreparedStatement pstat = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private DataBaseUtil db = null;

	public PayWaysDao(){
		db = new DataBaseUtil();
	}
	public Vector<PayWay> getPayWaysInfo(){
		 Vector<PayWay> datas=new  Vector<PayWay>();
		String sql="select * from payWays";
		conn=db.getConnection();
		try {
			pstat=conn.prepareStatement(sql);
			rs=pstat.executeQuery();
			while(rs.next()){
				PayWay p=new PayWay();
				p.setPid(rs.getInt("pid"));
				p.setName(rs.getString("name"));
				datas.add(p);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
		
	}
}
