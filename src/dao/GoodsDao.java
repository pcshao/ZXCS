package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.xml.crypto.Data;

import org.junit.Test;

import bean.Goods;
import bean.Units;
import util.DataBaseUtil;

/**
 * 库存管理DAO
 */
public class GoodsDao {
	
	private Connection conn = null;
	private PreparedStatement pstat = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private DataBaseUtil db = null;

	{
		db = new DataBaseUtil();
	}
	
	/**
	 * 库存报警
	 * 	选择商品 在多个仓库总和数量 小于 商品alterNum字段数量的
	 */
	public ArrayList<Goods> isAlert() {
		ArrayList<Goods> ret = new ArrayList<Goods>();;
		String sql = "select id,name,now from (" + 
				"select gid,sum(count) as now from goodsStore d inner join goods g on g.id = d.gid GROUP BY GID" + 
				")a inner join(" + 
				"	select * from goods" + 
				")b on a.gid = b.id and (now-alertNum)<0";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			while(rs.next()) {
				Goods g = new Goods();
				g.setId(rs.getInt("id"));
				g.setName(rs.getString("name"));
				g.setTempNum(rs.getInt("now"));
				ret.add(g);
				System.out.println(g);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return ret;
	}
	/**
	 * 库存操作（update）
	 * 	传入商品id
	 * 	传入仓库did
	 * 	传入商品数量
	 */
	public void changeGoodsStore(int gid, int did, int num) {
		String sql = "update goodsStore set count=count+? where gid=? and depot=?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, num);
			pstat.setInt(2, gid);
			pstat.setInt(3, did);
			pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
	}
	/**
	 * 库存操作（insert）
	 * 	传入商品id
	 * 	传入仓库did
	 *  传入库存num
	 * 	自增序列sq_goodsStore_id
	 */
	public void insertGoodsStore(int gid, int did, int num) {
		String sql = "insert into goodsStore (id,gid,did,count)values"
				+ "sq_goodsStore_id.nextval,?,?,?";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, gid);
			pstat.setInt(2, did);
			pstat.setInt(3, num);
			pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeConnection(conn, pstat, rs);
		}
	}
	/**
	 * 获取商品编号和名称(导入excel数据时校验用)
	 * 	返回Vector<Vector>
	 */
	public Vector<Vector> getGoodsMainInfoByVector(){
		Vector<Vector> ret = new Vector<Vector>();
		String sql = "select id,name from goods";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			while(rs.next()){
				Vector v = new Vector<>();
				v.add(rs.getInt("id"));
				v.add(rs.getString("name"));
				ret.add(v);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return ret;
	}
	/**
	 * 根据时间段筛选仓库列表 获取库存成本统计
	 *  结算月份 仓库名称 库存数量 库存总成本
	 *  进货数量 进货总金额
	 *  销售数量 销售总金额
	 *  销售利润
	 */
	public Vector<Vector> getDepotsCostByDate(String from, String to){
		Vector<Vector> ret = new Vector<>();
		//查询出from to 时间段、仓库名称 的进货数量
		String sql = "select depot,sum(num) from INORDERS io "
				+ "inner join INORDERSDETAILS ios on ios.oid = io.id and odate between to_date(201808,'yyyyMM')  and to_date(201808,'yyyyMM') and depot = '' group by depot ;";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return ret;
	}
	/**
	 * 根据商品id默认查询获取商品
	 * @param id
	 * @return 
	 */
	public Vector<Goods> getGoodsLikeID(int id) {
		Vector<Goods> ret = new Vector<>();
		String sql = "select g.id,g.name,goodsid,norms,disCount,sellprice,specialPrice,vipdiscount,vipprice,unit,"
				+ "u.name as unitname,unid from goods g inner join units u on g.unit=u.unid and goodsid like ? ";
		conn = db.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, "%"+id+"%");
			rs = pstat.executeQuery();
			while(rs.next()) {
				Goods g = new Goods();
				g.setId(rs.getInt("id"));
				g.setName(rs.getString("name"));
				g.setGoodsid(rs.getString("goodsid"));
				g.setDiscount(rs.getDouble("disCount"));
				g.setSellPrice(rs.getDouble("sellPrice"));
				g.setSpecialPrice(rs.getDouble("specialPrice"));
				g.setVipDiscount(rs.getDouble("vipDiscount"));
				g.setVipPrice(rs.getDouble("vipPrice"));
				g.setNorms(rs.getString("norms"));
				g.setUnit(new Units(rs.getInt("unid"),rs.getString("unitname")));
				ret.add(g);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, pstat, rs);
		}
		return ret;
	}
	/**
	 * 根据商品编号获取商品当时进价
	 * @param id
	 * @return
	 */
	public double getInPriceById(int id) {
		double inPrice = 0;
		String sql = "select inprice from goods where id = "+id;
		conn = db.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				inPrice = rs.getDouble("inPrice");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeConnection(conn, stmt, rs);
		}
		return inPrice;
	}
	/**
	 * yc
	 * ******************************************************
	 */
	//获取所有商品清单信息，包括库存，单位，这是联表查询
		public Vector<Vector> getAllGoods(){
			Vector<Vector> datas=new Vector<Vector>();
				String sql="select g.id,g.name gname,u.name uname,g.norms,g.inprice,s.now,g.sellprice " +
						"from goods g inner join units u on u.unid = g.unit " +
						"inner join( select gid,sum(count) as now from goods g " +
						"inner join goodsStore d on g.id = d.gid GROUP BY GID)s on s.gid = g.id"; 

				conn=db.getConnection();
				try {
					pstat=conn.prepareStatement(sql);
					rs=pstat.executeQuery();
					while(rs.next()){
						Vector data=new Vector();
						data.add(rs.getInt("id"));
						data.add(rs.getString("gname"));
						data.add(rs.getString("uname"));
						data.add(rs.getString("norms"));
						data.add(null);
						data.add(rs.getFloat("sellPrice"));
						data.add(rs.getInt("now"));
						datas.add(data);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					db.closeConnection(conn, pstat, rs);
				}
				return datas;
			
		}
		//获取所有商品清单信息通过id和name
		public Vector<Vector> getAllGoodsByIdName(String str){
			Vector<Vector> datas=new Vector<Vector>();
				String sql="select g.id,g.name gname,u.name uname,g.norms,g.inprice,s.now,g.sellprice" +
						"from goods g inner join units u on u.unid = g.unit" +
						"inner join( select gid,sum(count) as now from goods g " +
						"inner join goodsStore d on g.id = d.gid GROUP BY GID )s on s.gid = g.id"; 

				conn=db.getConnection();
				try {
					pstat=conn.prepareStatement(sql);
					//pstat.setString(1, "%"+str+"%");
					//pstat.setString(2, "%"+str+"%");
					rs=pstat.executeQuery();
					while(rs.next()){
						Vector data=new Vector();
						data.add(rs.getInt("id"));
						data.add(rs.getString("gname"));
						data.add(rs.getString("uname"));
						data.add(rs.getString("norms"));
						data.add(null);
						data.add(rs.getFloat("sellPrice"));
						data.add(rs.getInt("now"));
						datas.add(data);
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
		 * 查询销售详情表中的数量 数量越多的商品排在越前面
		 * @author yc
		 */
		public Vector<Vector> getAllGoodsByCount(){
			Vector<Vector> datas=new Vector<Vector>();
				String sql="select g.id,g.name gname,u.name uname,g.norms,g.inprice,s.now,g.sellprice " +
						"from goods g inner join units u on u.unid = g.unit " +
						"inner join( select gid,sum(count) as now from goods g " +
						"inner join goodsStore d on g.id = d.gid GROUP BY GID)s on s.gid = g.id " +
						"inner join sellordersdetails sell on sell.gid=g.id order by sell.num desc"; 

				conn=db.getConnection();
				try {
					pstat=conn.prepareStatement(sql);
					rs=pstat.executeQuery();
					while(rs.next()){
						Vector data=new Vector();
						data.add(rs.getInt("id"));
						data.add(rs.getString("gname"));
						data.add(rs.getString("uname"));
						data.add(rs.getString("norms"));
						data.add(null);
						data.add(rs.getFloat("sellPrice"));
						data.add(rs.getInt("now"));
						datas.add(data);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					db.closeConnection(conn, pstat, rs);
				}
				return datas;
			
		}
		//获取所有商品类列表信息
		public Vector<Vector> getGoodsLiebiao(){
			Vector<Vector> datas=new Vector<Vector>();
				String sql="select g.id,g.name gname,u.name uname,g.sellprice from goods g inner join units u on u.unid = g.unit";
				conn=db.getConnection();
				try {
					pstat=conn.prepareStatement(sql);
					rs=pstat.executeQuery();
					while(rs.next()){
						Vector data=new Vector();
						data.add(rs.getInt("id"));
						data.add(rs.getString("gname"));
						data.add(rs.getString("uname"));
						data.add(rs.getFloat("sellPrice"));
						datas.add(data);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					db.closeConnection(conn, pstat, rs);
				}
				return datas;
		}
		
		//获取所有商品类列表信息
		public Vector<Vector> getGoodsLiebiaoByType(int goodstypeid){
			Vector<Vector> datas=new Vector<Vector>();
				String sql="select g.id,g.name gname,u.name uname,g.sellprice from goods g inner join units u on u.unid = g.unit where g.type=?";
				conn=db.getConnection();
				try {
					pstat=conn.prepareStatement(sql);
					pstat.setInt(1, goodstypeid);
					rs=pstat.executeQuery();
					while(rs.next()){
						Vector data=new Vector();
						data.add(rs.getInt("id"));
						data.add(rs.getString("gname"));
						data.add(rs.getString("uname"));
						data.add(rs.getFloat("sellPrice"));
						datas.add(data);
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
		 * 获取单据商品信息
		 * 往来账务面板中的表2
		 * 
		 */
		public Vector<Vector> getGoodsInToAccount(String str){
			Vector<Vector> datas=new Vector<Vector>();
				String sql="select g.id,g.name g,u.name u,g.sellprice,sell.num,g.norms from goods g " +
						"join sellordersdetails sell on sell.gid=g.id "+ 
						"join units u on u.unid=g.unit where sell.oid=?";
				conn=db.getConnection();
				try {
					pstat=conn.prepareStatement(sql);
					pstat.setString(1, str);
					rs=pstat.executeQuery();
					while(rs.next()){
						Vector data=new Vector();
						data.add(rs.getInt("id"));
						data.add(rs.getString("g"));
						data.add(rs.getString("u"));
						data.add(rs.getFloat("sellPrice"));
						data.add(rs.getString("num"));
						data.add(Integer.parseInt(rs.getString("num"))*rs.getFloat("sellPrice"));
						data.add(rs.getString("norms"));
						datas.add(data);
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
		 * 查询所有顾客的消费情况
		 * 往来账务
		 * @author admin
		 */
		public Vector<Vector> getGoodsInToAccount(){
			Vector<Vector> datas=new Vector<Vector>();
				String sql="select g.id,g.name g,u.name u,g.sellprice,sell.num,g.norms,g.bz from goods g " +
						"join sellordersdetails sell on sell.gid=g.id "+ 
						"join units u on u.unid=g.unit";
				conn=db.getConnection();
				try {
					pstat=conn.prepareStatement(sql);
					rs=pstat.executeQuery();
					while(rs.next()){
						Vector data=new Vector();
						data.add(rs.getInt("id"));
						data.add(rs.getString("g"));
						data.add(rs.getString("norms"));
						data.add(rs.getString("num"));
						data.add(Integer.parseInt(rs.getString("num"))*rs.getFloat("sellPrice"));
						data.add(rs.getString("u"));
						data.add(rs.getString("bz"));
						datas.add(data);
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
		 ************************************************************************************************************** 
		 */
		//获取所有产品信息是  自己添加的
		public Vector<Vector> getProductInfo(){
			Vector<Vector> datas=new Vector<Vector>();
			Vector data=null;
			
			String sql="select * from goods";
			conn=db.getConnection();
			try{
				pstat=conn.prepareStatement(sql);
				rs=pstat.executeQuery();
				while(rs.next()){
					data=new Vector();
					data.add(rs.getInt("id"));
					data.add(rs.getString("name"));
					data.add(rs.getString("type"));
					data.add(rs.getString("norms"));
					data.add(rs.getInt("inPrice"));
					data.add(rs.getInt("sellPrice"));
					data.add(rs.getString("bz"));
					data.add(rs.getInt("alertNum"));
					datas.add(data);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				db.closeConnection(conn, pstat, rs);
			}
			return datas;
		}
		/**
		 * 库存报警表
		 * 获取商品信息
		 * @return
		 */
		public Vector<Vector> getgoodstoreInfo(){
			Vector<Vector> datas=new Vector<Vector>();
			Vector data=null;
			String sql="select gs.id,gs.gid,gs.count,g.name gname,d.name dname,g.alertNum " +
					"from goodsStore gs " +
					"inner join depots d on d.did = gs.depot " +
					"inner join goods g on g.id = gs.gid";
			Connection conn=db.getConnection();
			PreparedStatement pstat=null;
			ResultSet rs=null;
			try{
				pstat=conn.prepareStatement(sql);
				rs=pstat.executeQuery();
				while(rs.next()){
					data=new Vector();
					data.add(rs.getInt("id"));
					data.add(rs.getString("gid"));
					data.add(rs.getString("gname"));
					data.add(rs.getString("dname"));
					data.add(rs.getString("count"));
					data.add(rs.getInt("alertNum"));
					datas.add(data);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				db.closeConnection(conn, pstat, rs);
			}
			return datas;
		}
		/**
		 * 获取根据指定 或者 单据编号查询
		 * @param info
		 * @return
		 */
		public Vector<Vector> inordersinfoqueery(String info){
			Vector<Vector> datas=new Vector<Vector>();
			Vector data=null;
			String sql="select * from inOrders where  id like ?";
			
			Connection conn=db.getConnection();
			PreparedStatement pstat=null;
			ResultSet rs=null;
			try{
				pstat=conn.prepareStatement(sql);
				pstat.setString(1, "%"+info+"%");				
				rs=pstat.executeQuery();
				while(rs.next()){
					data=new Vector();
					data.add(rs.getString("id"));
					data.add(rs.getString("odate"));
					data.add(rs.getString("supplier"));
					data.add(rs.getInt("depot"));
					data.add(rs.getString("wantMoney"));
					data.add(rs.getString("payMoney"));
					data.add(rs.getInt("agent"));
					data.add(rs.getInt("operator"));
					data.add(rs.getInt("payWay"));
					data.add(rs.getString("bz"));
					datas.add(data);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				db.closeConnection(conn, pstat, rs);
			}
			return datas;
		}
		/**
		 * 获取   盘点单据所有信息   
		 * @return
		 */
		public Vector<Vector> getpdOrders(){
			Vector<Vector> datas=new Vector<Vector>();
			Vector data=null;
			String sql="select * from PdOrders";
			Connection conn=db.getConnection();
			PreparedStatement pstat=null;
			ResultSet rs=null;
			try{
				pstat=conn.prepareStatement(sql);					
				rs=pstat.executeQuery();
				while(rs.next()){
					data=new Vector();
					data.add(rs.getInt("id"));
					data.add(rs.getString("odate"));
					data.add(rs.getString("dname"));
					data.add(rs.getString("operator"));						
					data.add(rs.getString("bz"));
					datas.add(data);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				db.closeConnection(conn, pstat, rs);
			}
			return datas;
		}
		/**
		 * 获取   盘点 商品详情所有信息   
		 * @return
		 */
		public Vector<Vector> getpdOrdersDetails(){
			Vector<Vector> datas=new Vector<Vector>();
			Vector data=null;
			String sql="select * from Pdordersdetails";
			Connection conn=db.getConnection();
			PreparedStatement pstat=null;
			ResultSet rs=null;
			try{
				pstat=conn.prepareStatement(sql);					
				rs=pstat.executeQuery();
				while(rs.next()){
					data=new Vector();
					data.add(rs.getInt("id"));
					data.add(rs.getString("time"));					
					data.add(rs.getString("bz"));
					datas.add(data);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				db.closeConnection(conn, pstat, rs);
			}
			return datas;
		}
		//获取   树那里的所有     商品所有产品信息  是  
		public Vector<Vector> getGoods(){
			Vector<Vector> datas=new Vector<Vector>();
			Vector data=null;
			
			String sql="select * from goods";
			Connection conn=db.getConnection();
			PreparedStatement pstat=null;
			ResultSet rs=null;
			try{
				pstat=conn.prepareStatement(sql);
				rs=pstat.executeQuery();
				while(rs.next()){
					data=new Vector();
					data.add(rs.getInt("id"));
					data.add(rs.getString("name"));
					data.add(rs.getString("type"));
					data.add(rs.getInt("goodsid"));
					data.add(rs.getInt("unit"));
					data.add(rs.getInt("norms"));
					data.add(rs.getString("badDate"));
					data.add(rs.getInt("inPrice"));
					data.add(rs.getInt("sellPrice"));
					data.add(rs.getInt("discount"));
					data.add(rs.getInt("vipDiscount"));
					data.add(rs.getInt("specialPrice"));
					data.add(rs.getInt("vipPrice"));
					data.add(rs.getString("deadDate"));
					data.add(rs.getString("bz"));
					data.add(rs.getInt("alertNum"));
					datas.add(data);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				db.closeConnection(conn, pstat, rs);
			}
			return datas;
		}
		//获取   树那里的所有    编号或者id  查询商品所有产品信息    
		public Vector<Vector> getgoodsthreequeery(String info){
			Vector<Vector> datas=new Vector<Vector>();
			Vector data=null;
			
			String sql="select * from goods where name like ? or id like ?";
			Connection conn=db.getConnection();
			PreparedStatement pstat=null;
			ResultSet rs=null;
			try{
				pstat=conn.prepareStatement(sql);
				pstat.setString(1, "%"+info+"%");
				pstat.setString(2, "%"+info+"%");
				rs=pstat.executeQuery();
				while(rs.next()){
					data=new Vector();
					data.add(rs.getInt("id"));
					data.add(rs.getString("name"));
					data.add(rs.getString("type"));
					data.add(rs.getInt("goodsid"));
					data.add(rs.getInt("unit"));
					data.add(rs.getInt("norms"));
					data.add(rs.getString("badDate"));
					data.add(rs.getInt("inPrice"));
					data.add(rs.getInt("sellPrice"));
					data.add(rs.getInt("discount"));
					data.add(rs.getInt("vipDiscount"));
					data.add(rs.getInt("specialPrice"));
					data.add(rs.getInt("vipPrice"));
					data.add(rs.getString("deadDate"));
					data.add(rs.getString("bz"));
					data.add(rs.getInt("alertNum"));
					datas.add(data);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				db.closeConnection(conn, pstat, rs);
			}
			return datas;
		}
	/**
	 * ******************************************************
	 */
	/**
	 * zw
	 * ******************************************************
	 */
	/**
	 * 库存报警表，根据商品id或者名称查找商品
	 * @param info
	 * @return
	 */
	public Vector<Vector> getgoodstoreInfoByIdOrName(String info) {
		if(info.equals(""))
			return getgoodstoreInfo();
		Vector<Vector> datas=new Vector<Vector>();
		String sql="select gs.id,gs.gid,gs.count,g.name gname,d.name dname,g.alertNum " +
				" from goodsStore gs " +
				" inner join depots d on d.did = gs.depot " +
				" inner join goods g on g.id = gs.gid" +
				" and gs.gid like ? or g.name like ?";
		conn=db.getConnection();
		try{
			pstat=conn.prepareStatement(sql);
			pstat.setString(1, "%"+info+"%");
			pstat.setString(2, "%"+info+"%");
			rs=pstat.executeQuery();
			while(rs.next()){
				Vector data = new Vector();
				data.add(rs.getInt("id"));
				data.add(rs.getString("gid"));
				data.add(rs.getString("gname"));
				data.add(rs.getString("dname"));
				data.add(rs.getString("count"));
				data.add(rs.getInt("alertNum"));
				datas.add(data);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			db.closeConnection(conn, pstat, rs);
		}
		return datas;
	}
		
	/**
	 * ******************************************************
	 */
}
