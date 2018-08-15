package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import bean.Customer;
import bean.orders.SellOrder;
import bean.orders.SellOrder_tui;

import util.CastUtil;
import util.DataBaseUtil;

/**
 * yc坏胡是
 */
public class SellOrdersDao {
	DataBaseUtil du;
	CastUtil cu;
	Vector item=null;
	Connection conn=null;
	PreparedStatement pstat=null;
	ResultSet rs=null;
	
	
	public SellOrdersDao(){
		du=new DataBaseUtil();
	}
	
	
	/**
	 * 得到商品销售订单号
	 * 从一个临时表中获取相应的序列
	 * @author yc
	 */
	public String getSellOrderId(){
		conn=du.getConnection();
		String str="";
		String sql="select ('"+SellOrder.ORDERNAME+"'||TO_CHAR(SYSDATE,'YYYYMMDD')||TRIM(TO_CHAR(sq_orders_id.nextval,'00000000'))) id from dual";
		try {
			pstat=conn.prepareStatement(sql);
			rs=pstat.executeQuery();
			while(rs.next()){
				str=rs.getString("id");
				return str;
			}
			} catch (SQLException e) {
			e.printStackTrace();
			}finally{
			du.closeConnection(conn, pstat, rs);
			}
			return str;
	}
	/**
	 * 得到顾客退货订单号
	 * 从一个临时表中获取相应的序列
	 * @author yc
	 */
	public String getSellTuiOrderId(){
		conn=du.getConnection();
		String str="";
		String sql="select ('"+SellOrder_tui.ORDERNAME+"'||TO_CHAR(SYSDATE,'YYYYMMDD')||TRIM(TO_CHAR(sq_orders_id.nextval,'00000000'))) id from dual";
		try {
			pstat=conn.prepareStatement(sql);
			rs=pstat.executeQuery();
			while(rs.next()){
				str=rs.getString("id");
				return str;
			}
			} catch (SQLException e) {
			e.printStackTrace();
			}finally{
			du.closeConnection(conn, pstat, rs);
			}
			return str;
	}
	/**
	 * 销售单据查询
	 * 单据表
	 * @author yc
	 */
	public Vector<Vector> getSellOrdersInfo(String str1,String str2){
		Vector<Vector> datas=new Vector<Vector>();
			String sql="select s.id,s.odate,s.wantmoney,s.paymoney,c.name c,e.name e,a.name a,d.name d from sellorders s " +
					"join customers c on s.customer=c.cid "+
					"join employees e on e.eid=s.agent "+
					"join admins a on a.aid=s.operator "+
					"join depots d on d.did=s.depot where s.odate between to_date('"+str1+"','yyyy-mm-dd') and to_date('"+str2+"','yyyy-mm-dd')";

			conn=du.getConnection();
			try {
				pstat=conn.prepareStatement(sql);
//				pstat.setString(1, "'"+str1+"'");
//				pstat.setString(2, "'"+str2+"'");
				rs=pstat.executeQuery();
				while(rs.next()){
					Vector data=new Vector();
					data.add(rs.getString("id"));
					data.add(rs.getString("odate"));
					data.add(rs.getString("c"));
					data.add(rs.getString("d"));
					data.add(rs.getString("wantmoney"));
					data.add(rs.getString("paymoney"));
					data.add(Double.parseDouble(rs.getString("wantmoney"))-Double.parseDouble(rs.getString("paymoney")));
					data.add(rs.getString("e"));
					data.add(rs.getString("a"));
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
	 * 往来账务
	 * 客户所有单据信息
	 * 单据表
	 * @author yc
	 */
	public Vector<Vector> getSellOrdersAccountAll(){
		Vector<Vector> datas=new Vector<Vector>();
			String sql="select s.id,s.odate,s.wantmoney,s.paymoney,s.bz,c.name c,e.name e,a.name a,d.name d from sellorders s " +
					"join customers c on s.customer=c.cid "+
					"join employees e on e.eid=s.agent "+
					"join admins a on a.aid=s.operator "+
					"join depots d on d.did=s.depot";

			conn=du.getConnection();
			try {
				pstat=conn.prepareStatement(sql);
				rs=pstat.executeQuery();
				while(rs.next()){
					Vector data=new Vector();
					data.add(rs.getString("c"));
					data.add(rs.getString("odate"));
					data.add(rs.getString("id"));
					data.add(rs.getString("wantmoney"));
					data.add(rs.getString("paymoney"));
					data.add(Double.parseDouble(rs.getString("wantmoney"))-Double.parseDouble(rs.getString("paymoney")));
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
	 * 往来账务
	 * 通过客名称查询客户所有单据信息
	 * 单据表
	 * @author yc
	 */
	public Vector<Vector> getSellOrdersAccountAllByCustom(Customer customer){
		Vector<Vector> datas=new Vector<Vector>();
			String sql="select s.id,s.odate,s.wantmoney,s.paymoney,s.bz,c.name c,e.name e,a.name a,d.name d from sellorders s " +
					"join customers c on s.customer=c.cid "+
					"join employees e on e.eid=s.agent "+
					"join admins a on a.aid=s.operator "+
					"join depots d on d.did=s.depot where c.name like ?";

			conn=du.getConnection();
			try {
				pstat=conn.prepareStatement(sql);
				pstat.setString(1, ""+customer.getName()+"");
				rs=pstat.executeQuery();
				while(rs.next()){
					Vector data=new Vector();
					data.add(rs.getString("c"));
					data.add(rs.getString("odate"));
					data.add(rs.getString("id"));
					data.add(rs.getString("wantmoney"));
					data.add(rs.getString("paymoney"));
					data.add(Double.parseDouble(rs.getString("wantmoney"))-Double.parseDouble(rs.getString("paymoney")));
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
	
}
