package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.Vip;
import util.DataBaseUtil;

public class VipDao {

	private Connection conn = null;
	private PreparedStatement pstat = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private DataBaseUtil db = null;

	public VipDao(){
		db = new DataBaseUtil();
	}
	/**
	 * 获取会员信息
	 * @param vipPhone
	 * @return 无则返回null
	 */
	public Vip getVipById(String vipPhone) {
		Vip ret = null;
		String sql = "select * from vips where phone = ?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, vipPhone);
			rs = pstat.executeQuery();
			while(rs.next()) {
				ret = new Vip();
				ret.setId(rs.getInt("id"));
				ret.setName(rs.getString("name"));
				ret.setRange(rs.getInt("range"));
				ret.setCountMoney(rs.getDouble("countMoney"));
				ret.setAllPayMoney(rs.getDouble("allPayMoney"));
				ret.setOdate(rs.getString("odate"));
				ret.setPhone(rs.getString("phone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return ret;
	}
	/**
	 * 增加vip会员消费记录
	 * @param vipPhone
	 * @param id
	 */
	public void addRecord(Vip vip, String orderId) {
		String sql = "insert into vipRecord (id,vid,oid)values("
				+ "sq_vipRecord_id.nextval,?,?)";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, vip.getId());
			pstat.setString(2, orderId);
			rs = pstat.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
	}
	
}
