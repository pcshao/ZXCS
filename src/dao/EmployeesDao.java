package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import bean.Customer;
import bean.Employee;

import util.DataBaseUtil;

public class EmployeesDao {
	/**
	 *获取 经手人
	 */
	private Connection conn = null;
	private PreparedStatement pstat = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private DataBaseUtil db = null;

	public EmployeesDao(){
		db = new DataBaseUtil();
	}
	public Vector<Employee> getEmployees(){
		Vector<Employee> datas=new Vector<Employee>();
		String sql="select * from employees";
		conn=db.getConnection();
		try {
			pstat=conn.prepareStatement(sql);
			rs=pstat.executeQuery();
			while(rs.next()){
				Employee e=new Employee();
				e.setEid(rs.getInt("eid"));
				e.setName(rs.getString("name"));
				e.setJob(rs.getString("job"));
				e.setPhone(rs.getString("phone"));
				e.setAddress(rs.getString("address"));
				e.setBz(rs.getString("bz"));
				e.setPower(rs.getString("power"));
				datas.add(e);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
	/**
	 * 新增员工
	 * @param emp
	 * @return
	 */
	public Boolean addEmployee(Employee emp) {
		String sql = "insert into employees (eid,name,job,phone,address,bz,power)values(?,?,?,?,?,?,?)";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, emp.getEid());
			pstat.setString(2, emp.getName());
			pstat.setString(3, emp.getJob());
			pstat.setString(4, emp.getPhone());
			pstat.setString(5, emp.getAddress());
			pstat.setString(6, emp.getBz());
			pstat.setString(7, emp.getPower());
			pstat.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.closeConnection(conn, pstat, rs);
		}
		return false;
	}
	public Boolean editEmployee(Employee empOld, Employee empNew) {
		String sql = "update employees set name=?,job=?,phone=?,address=?,bz=?,power=? where eid=?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, empNew.getName());
			pstat.setString(2, empNew.getJob());
			pstat.setString(3, empNew.getPhone());
			pstat.setString(4, empNew.getAddress());
			pstat.setString(5, empNew.getBz());
			pstat.setString(6, empNew.getPower());
			pstat.setInt(7, empOld.getEid());
			pstat.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.closeConnection(conn, pstat, rs);
		}
		return false;
	}
	public Boolean removeEmployee(int id) {
		String sql = "delete from employees where eid=?";
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
	public Vector<Employee> searchEmployee(String content) {
		Vector<Employee> ret = new Vector<Employee>();
		String sql = "select * from employees where eid like ? or name like ?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, "%"+content+"%");
			pstat.setString(2, "%"+content+"%");
			rs = pstat.executeQuery();
			while(rs.next()) {
				Employee e = new Employee();
				e.setEid(rs.getInt("eid"));
				e.setName(rs.getString("name"));
				e.setJob(rs.getString("job"));
				e.setPhone(rs.getString("phone"));
				e.setAddress(rs.getString("address"));
				e.setBz(rs.getString("bz"));
				e.setPower(rs.getString("power"));
				ret.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.closeConnection(conn, pstat, rs);
		}
		return ret;
	}
	

}
