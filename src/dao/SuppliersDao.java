package dao;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import bean.Customer;
import bean.Supplier;
import util.DataBaseUtil;

/**
 * 供货商DAO
 */
public class SuppliersDao {
	
	private DataBaseUtil db;
	private Connection conn = null;
	private PreparedStatement pstat = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	{
		db = new DataBaseUtil();
	}
	
	/**
	 * 获取所有供应商信息
	 * 	返回Vector<Supplier>
	 * @return
	 */
	public Vector<Supplier> getSuppliers(){
		Vector<Supplier> ret = new Vector<Supplier>();
		String sql = "select * from suppliers";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			while(rs.next()) {
				Supplier s = new Supplier();
				s.setSid(rs.getInt("sid"));
				s.setName(rs.getString("name"));
				s.setContact(rs.getString("contact"));
				s.setPhone(rs.getString("phone"));
				s.setAddress(rs.getString("address"));
				s.setBz(rs.getString("bz"));
				ret.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * 得到供货商所有信息
	 * @return
	 */
	public ArrayList<Supplier> getSupplier() {
		ArrayList<Supplier> ret = new ArrayList<Supplier>();
		String sql = "select * from suppliers";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			while(rs.next()){
				Supplier s = new Supplier();
				s.setSid(rs.getInt("sid"));
				s.setName(rs.getString("name"));
				s.setContact(rs.getString("contact"));
				s.setPhone(rs.getString("phone"));
				s.setAddress(rs.getString("address"));
				ret.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return ret;
	}
	/**
	 * 通过编号，名称查找供货商所有信息
	 * @param str
	 * @return
	 */
	public ArrayList<Supplier> getSuppliersInfoByContactorName(String str) {
		ArrayList<Supplier> ret = new ArrayList<Supplier>();
		String sql = "select * from suppliers where sid like ? or name like ? or contact like ?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1,"%"+str+"%");  
			pstat.setString(2, "%"+str+"%");  
			pstat.setString(3, "%"+str+"%"); 
			rs = pstat.executeQuery();
			
			while(rs.next()){
				Supplier s = new Supplier();
				s.setSid(rs.getInt("sid"));
				s.setName(rs.getString("name"));
				s.setContact(rs.getString("contact"));
				s.setPhone(rs.getString("phone"));
				s.setAddress(rs.getString("address"));
				ret.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return ret;
	}
	/**
	 * 添加供应商
	 * @param supplier
	 * @return
	 */
	public Boolean addSupplier(Supplier supplier) {
		String sql = "insert into suppliers (sid,name,contact,phone,address,bz)values(?,?,?,?,?,?)";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, supplier.getSid());
			pstat.setString(2, supplier.getName());
			pstat.setString(3, supplier.getContact());
			pstat.setString(4, supplier.getPhone());
			pstat.setString(5, supplier.getAddress());
			pstat.setString(6, supplier.getBz());
			pstat.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.closeConnection(conn, pstat, rs);
		}
		return false;
	}

	public Boolean editSupplier(Supplier supplierOld, Supplier supplierNew) {
		String sql = "update suppliers set name=?,contact=?,phone=?,address=?,bz=? where sid=?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, supplierNew.getName());
			pstat.setString(2, supplierNew.getContact());
			pstat.setString(3, supplierNew.getPhone());
			pstat.setString(4, supplierNew.getAddress());
			pstat.setString(5, supplierNew.getBz());
			pstat.setInt(6, supplierOld.getSid());
			pstat.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.closeConnection(conn, pstat, rs);
		}
		return false;
	}

	public Boolean removeSuppliers(int id) {
		String sql = "delete suppliers where sid=?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			pstat.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.closeConnection(conn, pstat, rs);
		}
		return false;
	}

	public Vector<Supplier> searchSupplier(String content) {
		Vector<Supplier> ret = new Vector<Supplier>();
		String sql = "select * from suppliers where sid like ? or name like ?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, "%"+content+"%");
			pstat.setString(2, "%"+content+"%");
			rs = pstat.executeQuery();
			while(rs.next()) {
				Supplier s = new Supplier();
				s.setSid(rs.getInt("sid"));
				s.setName(rs.getString("name"));
				s.setContact(rs.getString("contact"));
				s.setPhone(rs.getString("phone"));
				s.setAddress(rs.getString("address"));
				s.setBz(rs.getString("bz"));
				ret.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.closeConnection(conn, pstat, rs);
		}
		return ret;
	}
	/**
	 * 通过名称查找供货商对象所有信息
	 * @param str
	 * @return
	 */
	public Supplier getSupplierInfoByContactorName(String str) {
		Supplier s = new Supplier();
		String sql = "select * from suppliers where name=? ";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1,str);   
			rs = pstat.executeQuery();
			if(rs.next()){
				s.setSid(rs.getInt("sid"));
				s.setName(rs.getString("name"));
				s.setContact(rs.getString("contact"));
				s.setPhone(rs.getString("phone"));
				s.setAddress(rs.getString("address"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return s;
	}

}
