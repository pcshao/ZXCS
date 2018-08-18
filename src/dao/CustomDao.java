package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import bean.Customer;
import bean.Employee;
import util.CastUtil;
import util.DataBaseUtil;

public class CustomDao {
	
	DataBaseUtil du;
	CastUtil cu;
	Vector item=null;
	Connection conn=null;
	PreparedStatement pstat=null;
	ResultSet rs=null;
	
	
	public CustomDao(){
		du=new DataBaseUtil();
	}
	/**
	 * 得到客户所有信息
	 * @return
	 */
	public ArrayList<Customer> getCustomers() {
		ArrayList<Customer> ret = new ArrayList<Customer>();
		String sql = "select * from customers";
		conn = du.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			while(rs.next()){
				Customer c = new Customer();
				c.setCid(rs.getInt("cid"));
				c.setName(rs.getString("name"));
				c.setContact(rs.getString("contact"));
				c.setPhone(rs.getString("phone"));
				c.setAddress(rs.getString("address"));
				ret.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			du.closeConnection(conn, pstat, rs);
		}
		return ret;
	}
	//得到客户所有信息 Vector
	public Vector<Customer> getCustomersThroughVector() {
		Vector<Customer> ret = new Vector<Customer>();
		String sql = "select * from customers";
		conn = du.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			while(rs.next()){
				Customer c = new Customer();
				c.setCid(rs.getInt("cid"));
				c.setName(rs.getString("name"));
				c.setContact(rs.getString("contact"));
				c.setPhone(rs.getString("phone"));
				c.setAddress(rs.getString("address"));
				ret.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			du.closeConnection(conn, pstat, rs);
		}
		return ret;
	}
	//判断字符串能否转整
	public static boolean isStrtoNum(String str){
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	public Boolean addCustomer(Customer cus) {
		String sql = "insert into customers (cid,name,contact,phone,address,bz)values(?,?,?,?,?,?)";
		conn = du.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, cus.getCid());
			pstat.setString(2, cus.getName());
			pstat.setString(3, cus.getContact());
			pstat.setString(4, cus.getPhone());
			pstat.setString(5, cus.getAddress());
			pstat.setString(6, cus.getBz());
			pstat.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			du.closeConnection(conn, pstat, rs);
		}
		return false;
	}
	public Boolean editCustomer(Customer cusOld, Customer cusNew) {
		String sql = "update customers set name=?,contact=?,phone=?,address=?,bz=? where cid=?";
		conn = du.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, cusNew.getName());
			pstat.setString(2, cusNew.getContact());
			pstat.setString(3, cusNew.getPhone());
			pstat.setString(4, cusNew.getAddress());
			pstat.setString(5, cusNew.getBz());
			pstat.setInt(6, cusOld.getCid());
			pstat.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			du.closeConnection(conn, pstat, rs);
		}
		return false;
	}
	public Boolean removeCustomer(int id) {
		String sql = "delete customers where cid=?";
		conn = du.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			pstat.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			du.closeConnection(conn, pstat, rs);
		}
		return false;
	}
	public Vector<Customer> searchCustomer(String content) {
		Vector<Customer> ret = new Vector<Customer>();
		String sql = "select * from customers where cid like ? or name like ?";
		conn = du.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, "%"+content+"%");
			pstat.setString(2, "%"+content+"%");
			rs = pstat.executeQuery();
			while(rs.next()) {
				Customer c = new Customer();
				c.setCid(rs.getInt("cid"));
				c.setName(rs.getString("name"));
				c.setContact(rs.getString("contact"));
				c.setPhone(rs.getString("phone"));
				c.setAddress(rs.getString("address"));
				c.setBz(rs.getString("bz"));
				ret.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			du.closeConnection(conn, pstat, rs);
		}
		return ret;
	}
	/**
	 * yc 
	 **************************************************
	 */
	//通过编号，名称查找客户所有信息
		public ArrayList<Customer> getCustomersInfoByContactorName(String str) {
			ArrayList<Customer> ret = new ArrayList<Customer>();
			String sql = "select * from customers where contact like ? or name like ? or cid like ?";
			conn = du.getConnection();
			try {
				pstat = conn.prepareStatement(sql);
				pstat.setString(1,"%"+str+"%");  
				pstat.setString(2, "%"+str+"%");  
				pstat.setString(3, "%"+str+"%"); 
				rs = pstat.executeQuery();
				while(rs.next()){
					Customer c = new Customer();
					c.setCid(rs.getInt("cid"));
					c.setName(rs.getString("name"));
					c.setContact(rs.getString("contact"));
					c.setPhone(rs.getString("phone"));
					c.setAddress(rs.getString("address"));
					ret.add(c);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				du.closeConnection(conn, pstat, rs);
			}
			return ret;
		}
		//添加新客户
		public boolean addCustomersInfo(Customer c) {
			String sql1 = "insert into customers values(?,?,?,?,?,?)";
			conn = du.getConnection();
			try {
				pstat = conn.prepareStatement(sql1);
				pstat.setInt(1,c.getCid());
				pstat.setString(2,c.getName()); 
				pstat.setString(3,c.getContact()); 
				pstat.setString(4,c.getPhone()); 
				pstat.setString(5,c.getAddress()); 
				pstat.setString(6,c.getBz());
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				du.closeConnection(conn, pstat, rs);
			}
			return false;
		}
		/**
		 * 查询所有顾客账户信息
		 * 记录客户销售和退货
		 * 往来账务
		 * @author admin
		 */
		public Vector<Vector> getCustomAccount(){
			Vector<Vector> datas=new Vector<Vector>();
				String sql="select c.name,s.paymoney ,s.wantmoney from sellordersdetails sell " +
						"join sellorders s on sell.oid=s.id "+
						"join customers c on c.cid=s.customer";
				conn=du.getConnection();
				try {
					pstat=conn.prepareStatement(sql);
					rs=pstat.executeQuery();
					while(rs.next()){
						Vector data=new Vector();
						data.add(rs.getString("name"));
						data.add(rs.getString("paymoney"));
						data.add(rs.getString("paymoney"));
						data.add(rs.getString("wantmoney"));
						data.add(rs.getString("paymoney"));
						data.add(Double.parseDouble(rs.getString("paymoney"))-Double.parseDouble(rs.getString("wantmoney")));
						datas.add(data);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					du.closeConnection(conn, pstat, rs);
				}
				return datas;
		}
		/**
		 * 查询所有客户付款明细
		 * 往来账务
		 * @author y'c
		 */
		public Vector<Vector> getCustomPayInfo(String str1,String str2){
			Vector<Vector> datas=new Vector<Vector>();
				String sql="select s.id,s.odate,p.name p,c.name c,e.name e,a.name a,s.bz from sellorders s "+ 
							"join customers c on s.customer=c.cid "+
							"join employees e on e.eid=s.agent "+
							"join admins a on a.aid=s.operator "+
							"join payways p on p.pid=s.payway where s.odate between to_date('"+str1+"','yyyy-mm-dd') and to_date('"+str2+"','yyyy-mm-dd')";
				conn=du.getConnection();
				try {
					pstat=conn.prepareStatement(sql);
					rs=pstat.executeQuery();
					while(rs.next()){
						Vector data=new Vector();
						data.add(rs.getString("id"));
						data.add(rs.getString("odate"));
						data.add(rs.getString("p"));
						data.add(rs.getString("c"));
						data.add(rs.getString("e"));
						data.add(rs.getString("a"));
						data.add(rs.getString("bz"));
						datas.add(data);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					du.closeConnection(conn, pstat, rs);
				}
				return datas;
		}
		/**
		 * *************************************************
		 */
}
