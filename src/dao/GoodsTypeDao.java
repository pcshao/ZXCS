package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.tree.DefaultMutableTreeNode;

import bean.GoodsType;
import util.DataBaseUtil;

public class GoodsTypeDao{
	DataBaseUtil user_db=null;
	public GoodsTypeDao(){
		user_db=new DataBaseUtil();
	}
	/**
	 * 根据名字获得该节点的父亲id，也就是外键	
	 * @param my_id
	 * @return
	 */
	public int getSuperId(int my_id){
		int id = 0;
		Connection conn=user_db.getConnection();
		PreparedStatement pstat=null;
		ResultSet rs=null;
		String sql="select pid from goodstype where tid=?";
		try {
			pstat=conn.prepareStatement(sql);
			pstat.setInt(1,my_id);
			rs=pstat.executeQuery();
			if(rs.next()){
				id=rs.getInt("pid");	
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			user_db.closeConnection(conn, pstat,rs);
		}
		return id;
	}
	/**
	 * 插入一个节点
	 * @param self_id
	 * @param super_id
	 * @param name
	 */
	public void insertInfo(int self_id,int super_id,String name){
		Connection conn=user_db.getConnection();
		PreparedStatement pstat=null;
		ResultSet rs=null;
		String sql="insert into goodstype values(?,?,?)";
		try {
			pstat=conn.prepareStatement(sql);
			pstat.setInt(1,self_id);
			pstat.setInt(2,super_id);
			pstat.setString(3,name);
			pstat.execute();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			user_db.closeConnection(conn, pstat);
		}
	}
	/**
	 * 获取数据库有多少条记录
	 * @return
	 */
	public int getIdNumber(){
		int number = 0;
		Connection conn=user_db.getConnection();
		PreparedStatement pstat=null;
		ResultSet rs=null;
		String sql="select sq_goodsType_id.nextval as a from dual";
		try {
			pstat=conn.prepareStatement(sql);
			rs=pstat.executeQuery();
			if(rs.next()){
				number=rs.getInt("a");	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			user_db.closeConnection(conn, pstat,rs);
		}
		return number;
	}
	/**
	 * 还原整个树到ui
	 * @param parentId
	 * @param node
	 * @return
	 */
	public DefaultMutableTreeNode getNodeFromDB(int parentId,DefaultMutableTreeNode node) {
		String sql = "select * from goodstype where pid=? and tid!=?";
		Connection conn=user_db.getConnection();
		PreparedStatement pstat=null;
		ResultSet rs=null;
		try {
			pstat=conn.prepareStatement(sql);
			pstat.setInt(1, parentId);
			pstat.setInt(2, parentId);
			rs=pstat.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("tid");
				String name=rs.getString("name");
				int superid=rs.getInt("pid");
				GoodsType type=new GoodsType(id,name,superid);
				DefaultMutableTreeNode node1=new DefaultMutableTreeNode(type);
				node1=getNodeFromDB(id,node1);
				node.add(node1);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			user_db.closeConnection(conn, pstat,rs);
		}
		return node;
		
	}
	/**
	 * 修改类别
	 * @param self_id
	 * @param new_name
	 */
	public void alterNode(int self_id,String new_name){
		Connection conn=user_db.getConnection();
		PreparedStatement pstat=null;
		ResultSet rs=null;
		String sql="update goodstype set name=? where tid =? ";
		try {
			pstat=conn.prepareStatement(sql);
			pstat.setString(1,new_name);
			pstat.setInt(2,self_id);
			pstat.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			user_db.closeConnection(conn, pstat);
		}
	}
	/**
	 * 删除
	 * @param self_id
	 */
	public void deleteNode(int self_id){
		Connection conn=user_db.getConnection();
		PreparedStatement pstat=null;
		ResultSet rs=null;
		String sql="delete goodstype where tid=?";
		try {
			pstat=conn.prepareStatement(sql);
			pstat.setInt(1,self_id);
			pstat.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			user_db.closeConnection(conn, pstat);
		}
	}

}
