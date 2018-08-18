package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * 数据库工具类 提供两个方法：
 * 	1、getConnection() 		返回连接对象
 * 	2、closeConnection(...) 	关闭数据库连接对象
 */
public class DataBaseUtil {
	//String url="jdbc:oracle:thin:@localhost:1521:orcl";
	//String url="jdbc:oracle:thin:@192.168.123.207:1521:orcl";
	String url="jdbc:oracle:thin:@172.16.4.100:1521:orcl";
	String urlMysql="jdbc:mysql://pcshao.cn:9910/zxcs?useSSL=false&autoReconnect=true&characterEncoding=UTF-8";
	private static String user = "zxcsadmin";
	private static String password = "123";
	private static String mysqlDriver = "com.mysql.jdbc.Driver";
	private static String oracleDriver = "oracle.jdbc.driver.OracleDriver";
	
	static{
		try{
			Class.forName(oracleDriver);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		System.out.println(new DataBaseUtil().getConnection());
	}
	//获得连接对象
	public Connection getConnection(){
		try{
			return DriverManager.getConnection(url, user, password);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	//关闭相关对象
	public void closeConnection(Connection conn, PreparedStatement pstat){
		closeDBObject(conn);
		closeDBObject(pstat);
	}
	public void closeConnection(Connection conn, Statement stat){
		closeDBObject(conn);
		closeDBObject(stat);
	}
	public void closeConnection(Connection conn, PreparedStatement pstat, ResultSet rs){
		closeDBObject(conn);
		closeDBObject(pstat);
		closeDBObject(rs);
	}
	public void closeConnection(Connection conn, Statement stat, ResultSet rs){
		closeDBObject(conn);
		closeDBObject(stat);
		closeDBObject(rs);
	}
	public void closeDBObject(Connection conn){
		if(conn!=null){
			try{
				conn.close();
			}catch(SQLException e){}
		}
	}
	public void closeDBObject(PreparedStatement pstat){
		if(pstat!=null){
			try{
				pstat.close();
			}catch(SQLException e){}
		}
	}
	public void closeDBObject(Statement stat){
		if(stat!=null){
			try{
				stat.close();
			}catch(SQLException e){}
		}
	}
	public void closeDBObject(ResultSet rs){
		if(rs!=null){
			try{
				rs.close();
			}catch(SQLException e){}
		}
	}
}
