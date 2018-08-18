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
 * yc
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
				rs=pstat.executeQuery();
				while(rs.next()){
					Vector data=new Vector();
					data.add(rs.getString("c"));
					data.add(rs.getString("odate"));
					data.add(rs.getString("id"));
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
	public Vector<Vector> getSellOrdersInfo(){
		Vector<Vector> datas=new Vector<Vector>();
			String sql="select s.id,s.odate,s.wantmoney,s.paymoney,c.name c,e.name e,a.name a,d.name d from sellorders s " +
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
	/**
	 * 往来账务
	 * 通过客名称查询客户所有商品销售总汇
	 * 客户消费情况
	 * @author yc
	 */
	public Vector<Vector> getGoodsInToAccount(Customer customer){
		Vector<Vector> datas=new Vector<Vector>();
			String sql="select g.id,g.name g,u.name u,g.sellprice,sell.num,g.norms,g.bz from goods g "+
		            "join sellordersdetails sell on sell.gid=g.id "+ 
		            "join sellorders s on s.id=sell.oid "+
		            "join customers c on c.cid=s.customer "+
		            "join units u on u.unid=g.unit where c.name=?";
			conn=du.getConnection();
			try {
				pstat=conn.prepareStatement(sql);
				pstat.setString(1, customer.getName());
				rs=pstat.executeQuery();
				while(rs.next()){
					Vector data=new Vector();
					data.add(rs.getInt("id"));
					data.add(rs.getString("g"));
					data.add(rs.getString("u"));
					data.add(rs.getDouble("sellPrice"));
					data.add(rs.getString("num"));
					data.add(Integer.parseInt(rs.getString("num"))*rs.getDouble("sellPrice"));
					data.add(rs.getString("norms"));
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
	 * 通过顾客名称查询所有顾客账户信息
	 * 记录客户销售和退货
	 * 往来账务
	 * @author yc
	 */
	public Vector<Vector> getCustomAccount(Customer customer ){
		Vector<Vector> datas=new Vector<Vector>();
			String sql="select c.name,s.paymoney ,s.wantmoney from sellordersdetails sell " +
					"join sellorders s on sell.oid=s.id "+
					"join customers c on c.cid=s.customer  where c.name=?";
			conn=du.getConnection();
			try {
				pstat=conn.prepareStatement(sql);
				pstat.setString(1, customer.getName());
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
	 * 通过商品id得到客户名称和出货仓库
	 * 添加退货商品
	 * @author yc
	 */
	public Vector<Object> getCustomnameAndDepots(int goodsid ){
		Vector<Object> datas=new Vector<Object>();
			String sql="select c.name c,d.name d from goods g "+
		            "join sellordersdetails sell on sell.gid=g.id "+
		            "join sellorders s on s.id=sell.oid "+
		            "join customers c on c.cid=s.customer "+
		            "join depots d on d.did=s.depot where g.id= ?"; 
		          
			conn=du.getConnection();
			try {
				pstat=conn.prepareStatement(sql);
				pstat.setInt(1, goodsid);
				rs=pstat.executeQuery();
				while(rs.next()){
					datas.add(rs.getString("c"));
					datas.add(rs.getString("d"));
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
	 * 退货查询
	 * 总汇表
	 * @author yc
	 * @param str
	 * @return
	 */
	public Vector<Vector> getHuiZhongIngo(String str, String str1){
		Vector<Vector> datas=new Vector<Vector>();
			String sql="select g.id,g.name g,u.name u,g.sellprice,sell.num,g.norms,gs.count from goods g " +
					"join sellordersdetails sell on sell.gid=g.id "+ 
					 "join sellorders s on s.id=sell.oid "+
					 "join goodsstore gs on gs.gid=g.id "+
					 "join units u on u.unid=g.unit where s.odate between to_date('"+str+"','yyyy-mm-dd') and to_date('"+str1+"','yyyy-mm-dd')";
			conn=du.getConnection();
			try {
				pstat=conn.prepareStatement(sql);
				rs=pstat.executeQuery();
				while(rs.next()){
					Vector data=new Vector();
					data.add(rs.getInt("id"));
					data.add(rs.getString("g"));
					data.add(rs.getString("u"));
					data.add(rs.getString("num"));
					data.add(Integer.parseInt(rs.getString("num"))*rs.getDouble("sellPrice"));
					data.add(rs.getString("count"));
					data.add(rs.getString("norms"));
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
	public Vector<Vector> getHuiZhongIngo(){
		Vector<Vector> datas=new Vector<Vector>();
			String sql="select g.id,g.name g,u.name u,g.sellprice,sell.num,g.norms,gs.count from goods g " +
					"join sellordersdetails sell on sell.gid=g.id "+ 
					 "join sellorders s on s.id=sell.oid "+
					 "join goodsstore gs on gs.gid=g.id "+
					 "join units u on u.unid=g.unit";
			conn=du.getConnection();
			try {
				pstat=conn.prepareStatement(sql);
				rs=pstat.executeQuery();
				while(rs.next()){
					Vector data=new Vector();
					data.add(rs.getInt("id"));
					data.add(rs.getString("g"));
					data.add(rs.getString("u"));
					data.add(rs.getString("num"));
					data.add(Integer.parseInt(rs.getString("num"))*rs.getDouble("sellPrice"));
					data.add(rs.getString("count"));
					data.add(rs.getString("norms"));
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
	 * 退货查询
	 * 总汇表
	 * 点击事件后展示信息
	 * @author yc
	 * @param str
	 * @return
	 */
	public Vector<Vector> getHuiZhongInfo(String str){
		Vector<Vector> datas=new Vector<Vector>();
			String sql="select c.name c,s.id,s.odate,u.name u,g.sellprice,sell.num,g.norms from goods g "+
		            "join sellordersdetails sell on sell.gid=g.id "+
		            "join sellorders s on s.id=sell.oid "+
		            "join goodsstore gs on gs.gid=g.id "+
		            "join units u on u.unid=g.unit "+
		            "join customers c on c.cid=s.customer where g.id=?";
			conn=du.getConnection();
			try {
				pstat=conn.prepareStatement(sql);
				pstat.setInt(1, Integer.parseInt(str));
				rs=pstat.executeQuery();
				while(rs.next()){
					Vector data=new Vector();
					data.add(rs.getString("c"));
					data.add(rs.getString("id"));
					data.add(rs.getString("odate"));
					data.add(rs.getString("u"));
					data.add(rs.getString("sellprice"));
					data.add(rs.getString("num"));
					data.add(Integer.parseInt(rs.getString("num"))*rs.getDouble("sellPrice"));
					data.add(rs.getString("norms"));
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
	 * 退货查询
	 * 明细表
	 * 
	 * @author yc
	 * @param str
	 * @return
	 */
	public Vector<Vector> getMingXiInfo(String str1,String str2){
		Vector<Vector> datas=new Vector<Vector>();
			String sql="select c.name c,s.id s,s.odate,u.name u,g.sellprice,sell.num,g.norms"
					+ ",g.id g,d.name d,g.name gn,em.name em"
					+ " from goods g "+
		            "join sellordersdetails sell on sell.gid=g.id "+
		            "join sellorders s on s.id=sell.oid "+
		            "join goodsstore gs on gs.gid=g.id "+
		            "join units u on u.unid=g.unit "+
		            "join customers c on c.cid=s.customer "+
		            "join depots d on d.did= s.depot "
		            + "join employees em on em.eid=s.agent where s.odate between to_date('"+str1+"','yyyy-mm-dd') and to_date('"+str2+"','yyyy-mm-dd')";
			conn=du.getConnection();
			try {
				pstat=conn.prepareStatement(sql);
				rs=pstat.executeQuery();
				while(rs.next()){
					Vector data=new Vector();
					data.add(rs.getString("odate"));
					data.add(rs.getString("s"));
					data.add(rs.getString("g"));
					data.add(rs.getString("gn"));
					data.add(rs.getString("sellprice"));
					data.add(rs.getString("num"));
					data.add(Integer.parseInt(rs.getString("num"))*rs.getDouble("sellPrice"));
					data.add(rs.getString("u"));
					data.add(rs.getString("norms"));
					data.add(rs.getString("d"));
					data.add(rs.getString("em"));
					data.add(rs.getString("c"));
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
	public Vector<Vector> getMingXiInfo(){
		Vector<Vector> datas=new Vector<Vector>();
			String sql="select c.name c,s.id s,s.odate,u.name u,g.sellprice,sell.num,g.norms"
					+ ",g.id g,d.name d,g.name gn,em.name em"
					+ " from goods g "+
		            "join sellordersdetails sell on sell.gid=g.id "+
		            "join sellorders s on s.id=sell.oid "+
		            "join goodsstore gs on gs.gid=g.id "+
		            "join units u on u.unid=g.unit "+
		            "join customers c on c.cid=s.customer "+
		            "join depots d on d.did= s.depot "
		            + "join employees em on em.eid=s.agent";
			conn=du.getConnection();
			try {
				pstat=conn.prepareStatement(sql);
				rs=pstat.executeQuery();
				while(rs.next()){
					Vector data=new Vector();
					data.add(rs.getString("odate"));
					data.add(rs.getString("s"));
					data.add(rs.getString("g"));
					data.add(rs.getString("gn"));
					data.add(rs.getString("sellprice"));
					data.add(rs.getString("num"));
					data.add(Integer.parseInt(rs.getString("num"))*rs.getDouble("sellPrice"));
					data.add(rs.getString("u"));
					data.add(rs.getString("norms"));
					data.add(rs.getString("d"));
					data.add(rs.getString("em"));
					data.add(rs.getString("c"));
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
	 * 删除单据
	 * @author yc
	 */
	public boolean deleteOrder(String str){
			String sq11="delete sellordersdetails where oid=?";
			String sql2="delete sellorders where id=?";
			conn=du.getConnection();
			try {
				pstat=conn.prepareStatement(sq11);
				pstat.setString(1, str);
				pstat.execute();
				pstat=conn.prepareStatement(sql2);
				pstat.setString(1, str);
				pstat.execute();
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				du.closeConnection(conn, pstat );
			}
			return false;
	}
	/**
	 * 往来账务
	 * 客户付款明细
	 * 查看单据
	 * @return
	 */
	public Vector<Vector> getCustomPayInfo(){
		Vector<Vector> datas=new Vector<Vector>();
			String sql="select s.id,s.odate,p.name p,c.name c,e.name e,a.name a,s.bz from sellorders s "+ 
						"join customers c on s.customer=c.cid "+
						"join employees e on e.eid=s.agent "+
						"join admins a on a.aid=s.operator "+
						"join payways p on p.pid=s.payway";
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
	/**往来账务
	 * 客户查询
	 * @param str1
	 * @param str2
	 * @param str3
	 * @return
	 */
	public Vector<Vector> getGoodsInToAccount(String str1,String str2,String str3){
		Vector<Vector> datas=new Vector<Vector>();
			String sql="select g.id,g.name g,u.name u,g.sellprice,sell.num,g.norms,g.bz from goods g " +
					"join sellordersdetails sell on sell.gid=g.id "+ 
					"join sellorders s on s.id=sell.oid "
					+ "join customers c on s.customer=c.cid"+ 
					"join units u on u.unid=g.unit where s.odate between to_date('"+str1+"','yyyy-mm-dd') and to_date('"+str2+"','yyyy-mm-dd')"
							+ "and c.name=?";
			conn=du.getConnection();
			try {
				pstat=conn.prepareStatement(sql);
				pstat.setString(1, str3);
				rs=pstat.executeQuery();
				while(rs.next()){
					Vector data=new Vector();
					data.add(rs.getInt("id"));
					data.add(rs.getString("g"));
					data.add(rs.getString("u"));
					data.add(rs.getString("num"));
					data.add(Integer.parseInt(rs.getString("num"))*rs.getFloat("sellPrice"));
					data.add(rs.getString("norms"));
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
