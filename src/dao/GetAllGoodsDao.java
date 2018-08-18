package dao;

import gui.lxh.OldGoodsADDModelWindow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.text.DefaultEditorKit.PasteAction;

import util.DataBaseUtil;
/**
 * lxhר��
 */
public class GetAllGoodsDao {
	private Connection conn = null;
	private PreparedStatement pstat = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private DataBaseUtil db = null;
	private GoodsUtilDao  unid_dao=null;
	DepotsDao depots_dao=null;
	public GetAllGoodsDao(){
		db = new DataBaseUtil();
		unid_dao=new GoodsUtilDao();
		depots_dao=new DepotsDao();
	}
	
	//��ȡ������Ʒ��Ϣ��������棬��λ�����������ѯ
		public Vector getAllGoods1(String id_or_name){
				Vector datas=new Vector();
				String sql="select g.id,g.name gname,u.name uname,g.norms,g.inprice,s.now,g.sellprice " +
						"from goods g inner join units u on u.unid = g.unit " +
						"inner join( select gid,sum(count) as now from goods g " +
						"inner join goodsStore d on g.id = d.gid GROUP BY GID)s on s.gid = g.id where g.id like ? or g.name like ?"; 

				conn=db.getConnection();
				try {
					pstat=conn.prepareStatement(sql);
					pstat.setString(1, "%"+id_or_name+"%");
					pstat.setString(2, "%"+id_or_name+"%");
					rs=pstat.executeQuery();
					while(rs.next()){
						Vector data=new Vector();
						data.add(rs.getInt("id"));
						data.add(rs.getString("gname"));
						data.add(rs.getString("uname"));
						data.add(rs.getString("norms"));
						data.add(rs.getFloat("inPrice"));
						data.add(rs.getInt("now"));
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
		//��ȡ���У��˻���Ҫ�ã���Ʒ��Ϣ��������棬��λ�����������ѯ
		
		//��ȡ������Ʒ��Ϣ��������棬��λ�����������ѯ
			public Vector getAllGoods2(int id){
					Vector data=new Vector();
					String sql="select g.type,g.id,g.name gname,u.name uname,g.norms,g.inprice,s.now,g.sellprice," +
							"g.goodsid,g.bz,g.alertNum from goods g inner join units u on u.unid = g.unit " +
							"inner join( select gid,sum(count) as now from goods g " +
							"inner join goodsStore d on g.id = d.gid GROUP BY GID)s on s.gid = g.id where id=?"; 

					conn=db.getConnection();
					try {
						pstat=conn.prepareStatement(sql);
						pstat.setInt(1, id);
						rs=pstat.executeQuery();
						if(rs.next()){
							data.add(rs.getInt("type"));
							data.add(rs.getString("gname"));
							data.add(rs.getString("goodsid"));
							data.add(rs.getString("norms"));
							data.add(rs.getString("uname"));
							data.add(rs.getInt("alertNum"));
							data.add(rs.getFloat("inprice"));
							data.add(rs.getFloat("sellprice"));
							data.add(rs.getString("bz"));
						}
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						db.closeConnection(conn, pstat, rs);
					}
					return data;
				
			}
		//������Ʒ����ȡ������Ʒ��Ϣ��������棬��λ�����������ѯ
			public Vector getAllGoods(int type_id){
					Vector datas=new Vector();
					String sql="select g.id,g.name gname,u.name uname,g.norms,g.inprice,s.now,g.sellprice " +
							"from goods g inner join units u on u.unid = g.unit " +
							"inner join( select gid,sum(count) as now from goods g " +
							"inner join goodsStore d on g.id = d.gid GROUP BY GID)s on s.gid = g.id  where g.type=?"; 

					conn=db.getConnection();
					try {
						pstat=conn.prepareStatement(sql);
						pstat.setInt(1, type_id);
						rs=pstat.executeQuery();
						while(rs.next()){
							Vector data=new Vector();
							data.add(rs.getInt("id"));
							data.add(rs.getString("gname"));
							data.add(rs.getString("uname"));
							data.add(rs.getString("norms"));
							data.add(rs.getFloat("inPrice"));
							data.add(rs.getInt("now"));
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
			 * ��ȡ���ڽ�������Ʒ
			 * @
			 */
			public Vector getRecentlyGoods(String id_or_name){
				Vector datas=new Vector();
				String sql="select g.id,g.name gname,i.num,g.inprice,u.name uname,g.norms,ii.odate,g.sellPrice " +
						"from goods g inner join units  u on g.unit=u.unid inner j" +
						"oin inordersdetails i on g.id=i.gid inner join inOrders  ii on i.oid=ii.id " +
						"where g.id in ( select gid from  inordersdetails where oid in(  " +
						"select id from inOrders where Sysdate-odate<7 )) and( g.id like ? or g.name like ?)";
				conn=db.getConnection();
				try {
					pstat=conn.prepareStatement(sql);
					pstat.setString(1, "%"+id_or_name+"%");
					pstat.setString(2, "%"+id_or_name+"%");
					rs=pstat.executeQuery();
					while(rs.next()){
						Vector data=new Vector();
						data.add(rs.getInt("id"));
						data.add(rs.getString("gname"));
						data.add(rs.getInt("num"));
						data.add(rs.getFloat("inPrice"));
						data.add(rs.getString("uname"));
						data.add(rs.getString("norms"));
						data.add(" ");
						data.add(rs.getDate("odate"));
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
		 * ֻ��ʾ��ѡ�����̵���Ʒ��Ϣ
		 */
			public Vector getNowSupplierGoods(String name){
				Vector datas=new Vector();
				String sql="select g.id,g.name gname,u.name uname,g.norms,g.inprice,s.now,g.sellprice "+
			            "from goods g inner join units u on u.unid = g.unit  "+
			            "inner join( select gid,sum(count) as now from goods g  "+
			            "inner join goodsStore d on g.id = d.gid GROUP BY GID)s on s.gid = g.id  where g.id in "+
			           "( select gid from inordersdetails where oid in (select id from inOrders where supplier in "+
			             "(select sid from suppliers where name=?)) )"; 
				conn=db.getConnection();
				try {
					pstat=conn.prepareStatement(sql);
					pstat.setString(1, name);
					rs=pstat.executeQuery();
					while(rs.next()){
						Vector data=new Vector();
						data.add(rs.getInt("id"));
						data.add(rs.getString("gname"));
						data.add(rs.getString("uname"));
						data.add(rs.getString("norms"));
						data.add(rs.getFloat("inPrice"));
						data.add(rs.getInt("now"));
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
		 * ֻ��ʾ��ǰ�ֿ����Ʒ��Ϣ
		 */
			public Vector getNowDepotGoods(String name){
				Vector datas=new Vector();
				String sql="select g.id,g.name gname,u.name uname,g.norms,g.inprice,s.now,g.sellprice "+
		                "from goods g inner join units u on u.unid = g.unit  "+
		                "inner join( select gid,sum(count) as now from goods g  "+
		                "inner join goodsStore d on g.id = d.gid GROUP BY GID)s on s.gid = g.id  where g.id in "+
		               "( select gid from goodsstore where depot in "+
		                "(select did from depots where name=?) )"; 
				conn=db.getConnection();
				try {
					pstat=conn.prepareStatement(sql);
					pstat.setString(1, name);
					rs=pstat.executeQuery();
					while(rs.next()){
						Vector data=new Vector();
						data.add(rs.getInt("id"));
						data.add(rs.getString("gname"));
						data.add(rs.getString("uname"));
						data.add(rs.getString("norms"));
						data.add(rs.getFloat("inPrice"));
						data.add(rs.getInt("now"));
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
			 * ֻ��ʾ��ѡ�����̵���Ʒ��Ϣ
			 * 
			 * �Լ�ֻ��ʾ��ǰ�ֿ����Ʒ��Ϣ
			 */
				public Vector getTwoOnlyGoods(String dname,String sname){
					Vector datas=new Vector();
					String sql="select g.id,g.name gname,u.name uname,g.norms,g.inprice,s.now,g.sellprice "+
				            "from goods g inner join units u on u.unid = g.unit  "+
				            "inner join( select gid,sum(count) as now from goods g  "+
				            "inner join goodsStore d on g.id = d.gid GROUP BY GID)s on s.gid = g.id  where g.id in "+
				           "( select gid from inordersdetails where oid in ( select id from inOrders a join suppliers b on a.supplier=b.sid where depot in "+
	                         "(select did from depots where name=?) and b.name=? ))"; 
					conn=db.getConnection();
					try {
						pstat=conn.prepareStatement(sql);
						pstat.setString(1, dname);
						pstat.setString(2, sname);
						rs=pstat.executeQuery();
						while(rs.next()){
							Vector data=new Vector();
							data.add(rs.getInt("id"));
							data.add(rs.getString("gname"));
							data.add(rs.getString("uname"));
							data.add(rs.getString("norms"));
							data.add(rs.getFloat("inPrice"));
							data.add(rs.getInt("now"));
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
			 * �������Ʒ
			 */
			public void insertNewGoods(int type,String gname,String goodsid,String norms,
				String unit,int alertNum,float in_price,float sell_price,String bz,String dname){
				String sql="insert into goods(type,id,name,goodsid,norms,unit,alertNum,inprice,sellprice,bz) values"+
						"(?,?,?,?,?,?,?,?,?,?)";
				//�ж������λ��û�У�û�о��¼ӵ�������
				int id=getNowGoodId();
				int goods_unit;
				if(unit==null||unit.equals("")){
					goods_unit=0;
				}else {
					goods_unit=unid_dao.getUtilId(unit);
					if(goods_unit==-1000){
						unid_dao.insertUtil(unid_dao.getMaxId()+1,unit,null);
						goods_unit=unid_dao.getUtilId(unit);
					}
				}
				conn=db.getConnection();
				try {
					pstat=conn.prepareStatement(sql);
					pstat.setInt(1,type);
					pstat.setInt(2, id);
					pstat.setString(3,gname);
					pstat.setString(4,goodsid);
					pstat.setString(5,norms);
					pstat.setInt(6,goods_unit);
					pstat.setInt(7,alertNum);
					pstat.setFloat(8,in_price);
					pstat.setFloat(9,sell_price);
					pstat.setString(10,bz);
					pstat.execute();
					int did=depots_dao.getDepotsId(dname);
					if(did!=-1000){
						AddGoodsStore(id,alertNum, did);
						System.out.println(1212);
					}
					JOptionPane.showMessageDialog(null, "��ӳɹ���");
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "����Ĵ���");
					e.printStackTrace();
				}finally{
					db.closeConnection(conn, pstat);
				}
				
			}
			/*
			 * 
			 * ���뵽���
			 */
			public void AddGoodsStore(int gid,int count,int depot_id){
					String sql="insert into goodsstore values(sq_goodsStore_id.nextval,?,?,?)";
					conn=db.getConnection();
					try {
						pstat=conn.prepareStatement(sql);
						pstat.setInt(1,gid);
						pstat.setInt(2,count);
						pstat.setInt(3,depot_id);
						pstat.execute();
					} catch (SQLException e) {
						e.printStackTrace();
					}finally{
						db.closeConnection(conn, pstat);
					}	
				}
			
			/**
			 * �鵱ǰ��Ʒ��id
			 */
			public int  getNowGoodId(){
				int id=-1000;
				String sql="select sq_goods_id.nextval id from dual";
				//�ж������λ��û�У�û�о��¼ӵ�������
				conn=db.getConnection();
				try {
					pstat=conn.prepareStatement(sql);
					rs=pstat.executeQuery();
					if(rs.next()){
						id=rs.getInt("id");
						return id;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					db.closeConnection(conn, pstat);
				}
				return id;
			}
			/**
			 * �޸���Ʒ��Ϣ
			 * 
			 */
			public void AlterGoods(int id,int type,String gname,String goodsid,String norms,
					String unit,int alertNum,float in_price,float sell_price,String bz){
				String sql="update goods set type=?,name=?,goodsid=?,norms=?,unit=?,alertNum=?,inprice=?,sellprice=?,bz=? where id=?";
				//�ж������λ��û�У�û�о��¼ӵ�������;
				int goods_unit;
				if(unit==null||unit.equals("")){
					goods_unit=0;
				}else {
					goods_unit=unid_dao.getUtilId(unit);
					if(goods_unit==-1000){
						unid_dao.insertUtil(unid_dao.getMaxId()+1,unit,null);
						goods_unit=unid_dao.getUtilId(unit);
					}
				}
				conn=db.getConnection();
				try {
					pstat=conn.prepareStatement(sql);
					pstat.setInt(1,type);
					pstat.setString(2,gname);
					pstat.setString(3,goodsid);
					pstat.setString(4,norms);
					pstat.setInt(5,goods_unit);
					pstat.setInt(6,alertNum);
					pstat.setFloat(7,in_price);
					pstat.setFloat(8,sell_price);
					pstat.setString(9,bz);
					pstat.setInt(10, id);
					pstat.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					db.closeConnection(conn, pstat);
				}
				
			}
		/**
		 * �ֿ�����ȡ��Ʒ��������Ϣ���޸�
		 * @author pcshao
		 */
		public Vector getAllGoodsForUpdate(int type_id){
			Vector datas=new Vector();
			String sql="select g.id,g.name gname,u.name uname,g.norms,g.inprice,s.now,g.sellprice,g.bz,g.alertNum " +
					"from goods g inner join units u on u.unid = g.unit " +
					"inner join( select gid,sum(count) as now from goods g " +
					"inner join goodsStore d on g.id = d.gid GROUP BY GID)s on s.gid = g.id  where g.type=?"; 

			conn=db.getConnection();
			try {
				pstat=conn.prepareStatement(sql);
				pstat.setInt(1, type_id);
				rs=pstat.executeQuery();
				while(rs.next()){
					Vector data=new Vector();
					data.add(rs.getInt("id"));
					data.add(rs.getString("gname"));
					data.add(rs.getString("uname"));
					data.add(rs.getString("norms"));
					data.add(rs.getFloat("inPrice"));
					data.add(rs.getFloat("sellPrice"));
					data.add(rs.getInt("now"));
					data.add(rs.getString("bz"));
					data.add(rs.getInt("alertNum"));
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
}
