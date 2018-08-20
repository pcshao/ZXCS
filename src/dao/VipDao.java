package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import bean.Vip;
import bean.VipRange;
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
		String sql = "select v.id,v.name,v.range,v.countMoney,v.allPayMoney,v.odate,v.phone,vr.discount from vips v "
				+ "inner join vipRange vr on vr.rid=v.range "
				+ "and phone = ?";
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
				ret.setDiscount(rs.getDouble("discount"));
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
	/**
	 * 获取所有VIP信息
	 * @return
	 */
	public Vector<Vector> getVips(String search) {
		Vector<Vector> ret = new Vector<>();
		String sql = "select * from vips where name like ? or id like ? or phone like ?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, "%"+search+"%");
			pstat.setString(2, "%"+search+"%");
			pstat.setString(3, "%"+search+"%");
			rs = pstat.executeQuery();
			while(rs.next()) {
				Vector v = new Vector<>();
				v.add(rs.getInt("id"));
				v.add(rs.getString("name"));
				v.add(rs.getInt("range"));
				v.add(rs.getDouble("countMoney"));
				v.add(rs.getDouble("allPayMoney"));
				v.add(rs.getString("odate"));
				v.add(rs.getString("phone"));
				ret.add(v);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, stmt, rs);
		}
		return ret;
	}
	/**
	 * 获取VIP消费记录详情
	 * @param id
	 * @return
	 */
	public Vector<Vector> getVipRecordById(int id) {
		Vector<Vector> ret = new Vector<>();
		String sql = "select vr.id,vr.oid,sod.gid,g.name,sod.num from viprecord vr" + 
				"	inner join vips v on v.id=vr.vid" + 
				"		inner join SELLORDERSDETAILS sod on sod.oid=vr.oid" + 
				"			inner join goods g on g.id=sod.gid" + 
				"				and vr.vid=?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			rs = pstat.executeQuery();
			while(rs.next()) {
				Vector v = new Vector<>();
				v.add(rs.getInt("id"));
				v.add(rs.getString("oid"));
				v.add(rs.getInt("gid"));
				v.add(rs.getString("name"));
				v.add(rs.getInt("num"));
				ret.add(v);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, stmt, rs);
		}	
		return ret;
	}
	/**
	 * 新增会员
	 * @param vip
	 * @return
	 */
	public boolean addVip(Vip vip) {
		String sql = "insert into vips (id,name,range,countmoney,allpaymoney,odate,phone)values "
				+ "(sq_vips_id.nextval,?,?,?,0,sysdate,?)";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, vip.getName());
			pstat.setInt(2, vip.getRange());
			pstat.setDouble(3, vip.getCountMoney());
			pstat.setString(4, vip.getPhone());
			pstat.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, stmt, rs);
		}	
		return false;
	}
	/**
	 * 删除会员
	 * @param id
	 * @return
	 */
	public boolean removeVipByid(int id) {
		String sql = "delete from vips where id = ?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			pstat.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, stmt, rs);
		}	
		return false;
	}
	/**
	 * 消费总额增加
	 * @param wantMoney
	 */
	public void plusConsumer(int id, double wantMoney) {
		String sql = "update vips set allPayMoney=allPayMoney+? where id = ?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setDouble(1, wantMoney);
			pstat.setInt(2, id);
			pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, stmt, rs);
		}	
	}
	public Vector<Vector> getVipRangeInfo() {
		Vector<Vector> ret = new Vector<>();
		String sql = "select vr.rid,vr.discount,vr.rangeMoney,vipps "
				+ "from vipRange vr left join ("
				+ "select range,count(id) as vipps from vips group by range) v on v.range=vr.rid";
		conn = db.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				Vector v = new Vector<>();
				v.add(rs.getInt("rid"));
				v.add(rs.getDouble("discount"));
				v.add(rs.getDouble("rangeMoney"));
				v.add(rs.getInt("vipps"));
				ret.add(v);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, stmt, rs);
		}
		return ret;
	}
	/**
	 * 新增会员等级
	 * @param vr
	 * @return
	 */
	public boolean addVipRange(VipRange vr) {
		String sql = "insert into vipRange (rid,discount,rangeMoney)values "
				+ "(?,?,?)";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, vr.getRid());
			pstat.setDouble(2, vr.getDiscount());
			pstat.setDouble(3, vr.getRangeMoney());
			pstat.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat);
		}
		return false;
	}
	/**
	 * 编辑会员等级
	 * @param vr
	 * @return
	 */
	public boolean editVipRangeById(VipRange vr) {
		String sql = "update vipRange set discount=?,rangeMoney=? where rid=?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setDouble(1, vr.getDiscount());
			pstat.setDouble(2, vr.getRangeMoney());
			pstat.setInt(3, vr.getRid());
			pstat.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat);
		}
		return false;
	}
	/**
	 * 删除会员等级（无级联）
	 * @param id
	 * @return
	 */
	public boolean removeVipRangeById(int id) {
		String sql = "delete from vipRange where rid = ?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setDouble(1, id);
			pstat.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat);
		}
		return false;
	}
	
}
