package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import util.*;
import dao.*;
import service.*;
import bean.*;

/**
 * 管理员、操作员DAO层
 * @author pcshao
 */
public class AdminDao {
	
	private Connection conn = null;
	private PreparedStatement pstat = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private DataBaseUtil db = null;

	public AdminDao(){
		db = new DataBaseUtil();
	}
	
	/**
	 * 登录校验
	 *  检测isUse字段是否可用，否则返回false
	 *  最高级别管理员isUse字段不可修改（程序校验）
	 * @param count
	 * @param password
	 * @return boolean
	 */
	public boolean isLogin(String name,String password) {
		String sql = "select * from admins where name = ? and password = ? ";
		try {
			conn = db.getConnection();
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, name);
			pstat.setString(2, password);
			rs = pstat.executeQuery();
			if(rs.next())
				if("是".equals(rs.getString("isUse").trim()))
					return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			LogMark.LogWrite("-"+name+"尝试登陆-",true);
			db.closeConnection(conn, pstat, rs);
		}
		return false;
	}
	/**
	 * 登录完成获取管理员（操作员）基本信息
	 *  每次后台登录有且只有一位管理员
	 * @param name
	 * @return Admin对象
	 */
	public Admin getAdminInfo(String name) {
		Admin admin = null;
		String sql = "select * from admins where name = '"+name+"'";
		try {
			conn = db.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				admin = new Admin();
				admin.setAid(rs.getInt("aid"));
				admin.setName(rs.getString("name"));
				admin.setPassword(rs.getString("password"));
				admin.setJob(rs.getString("job"));
				admin.setPower(rs.getString("power"));
				admin.setIsGuiZhang(rs.getString("isGuiZhang"));
				admin.setIsPOS(rs.getString("isPOS"));
				admin.setIsUse(rs.getString("isUse"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			LogMark.LogWrite("-登陆成功-",true);
			db.closeConnection(conn, pstat, rs);
		}
		return admin;
	}
	/**
	 * 获取所有管理员（操作员）
	 *  无参
	 *  返回Vector<admin>
	 */
	public Vector<Admin> getAdmins(){
		Vector<Admin> admins = new Vector<Admin>();
		String sql = "select * from admins";
		try{
			conn = db.getConnection();
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			while(rs.next()) {
				Admin admin = new Admin();
				admin.setAid(rs.getInt("aid"));
				admin.setName(rs.getString("name"));
				admin.setPassword(rs.getString("password"));
				admin.setJob(rs.getString("job"));
				admin.setIsGuiZhang(rs.getString("isGuiZhang"));
				admin.setIsPOS(rs.getString("isPos"));
				admin.setIsUse(rs.getString("isUse"));
				admin.setPower(rs.getString("power"));
				admins.add(admin);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return admins;
	}
	/**
	 * 新增管理员（操作员）
	 */
	public boolean addAdmin(Admin admin) {
		String sql = "insert into admins (aid,name,password,job,power,isGuiZhang,isPOS,isUse)values(?,?,?,?,?,?,?,?)";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, admin.getAid());
			pstat.setString(2, admin.getName());
			pstat.setString(3, admin.getPassword());
			pstat.setString(4, admin.getJob());
			pstat.setString(5, admin.getPower());
			pstat.setString(6, admin.getIsGuiZhang());
			pstat.setString(7, admin.getIsPOS());
			pstat.setString(8, admin.getIsUse());
			pstat.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat);
		}
		return false;
	}
	/**
	 * 编辑管理员信息（操作员）
	 */
	public boolean editAdmin(Admin oldAdmin, Admin newAdmin) {
		String sql = "update admins set name=?,password=?,job=?,power=?,isGuiZhang=?,isPOS=?,isUse=? where aid=? and name=?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, newAdmin.getName());
			pstat.setString(2, newAdmin.getPassword());
			pstat.setString(3, newAdmin.getJob());
			pstat.setString(4, newAdmin.getPower());
			pstat.setString(5, newAdmin.getIsGuiZhang());
			pstat.setString(6, newAdmin.getIsPOS());
			pstat.setString(7, newAdmin.getIsUse());

			pstat.setInt(8, newAdmin.getAid());
			pstat.setString(9, newAdmin.getName());
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
	 * 删除管理员（操作员）
	 */
	public boolean removeAdmin(Admin admin) {
		String sql = "delete from admins where aid=? and name=?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, admin.getAid());
			pstat.setString(2, admin.getName());
			return pstat.execute();
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat);
		}
		return false;
	}
	//根据管理员ID获取管理员信息
	public Admin getAdminById(Admin admin) {
		Admin ret = new Admin();
		String sql = "select * from admins where aid=?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, admin.getAid());
			rs = pstat.executeQuery();
			while(rs.next()) {
				ret.setAid(rs.getInt("aid"));
				ret.setName(rs.getString("name"));
				ret.setPassword(rs.getString("password"));
				ret.setJob(rs.getString("job"));
				ret.setIsGuiZhang(rs.getString("isGuiZhang"));
				ret.setIsPOS(rs.getString("isPos"));
				ret.setIsUse(rs.getString("isUse"));
				ret.setPower(rs.getString("power"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return ret;
	}

	public Vector<Admin> searchAdmin(String content) {
		Vector<Admin> ret = new Vector<Admin>();
		String sql = "select * from admins where aid like ? or name like ?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, "%"+content+"%");
			pstat.setString(2, "%"+content+"%");
			rs = pstat.executeQuery();
			while(rs.next()) {
				Admin admin = new Admin();
				admin.setAid(rs.getInt("aid"));
				admin.setName(rs.getString("name"));
				admin.setPassword(rs.getString("password"));
				admin.setJob(rs.getString("job"));
				admin.setIsGuiZhang(rs.getString("isGuiZhang"));
				admin.setIsPOS(rs.getString("isPos"));
				admin.setIsUse(rs.getString("isUse"));
				admin.setPower(rs.getString("power"));
				ret.add(admin);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.closeConnection(conn, pstat, rs);
		}
		return ret;
	}
	
}
