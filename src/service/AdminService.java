package service;

import java.sql.SQLException;
import java.util.Vector;

import org.junit.Test;
import util.*;
import dao.*;
import service.*;
import bean.*;
import bean.orders.*;;

/**
 * 管理员服务
 * @author pcshao
 */
public class AdminService {

	public static Admin admin;		//公有的静态全局操作员信息
	
	private AdminDao admindao;

	/**
	 * 此登录方法校验用户名密码
	 *  校验失败返回false
	 *  校验成功并存储全局管理员基本信息
	 */
	public boolean Login(String name,String password) {
		admindao = new AdminDao();
		//登录校验
		if(!admindao.isLogin(name, password))
			return false;
		//校验完成，获取管理员信息
		admin = admindao.getAdminInfo(name);
		return true;
	}/**
	 * 增加新管理员
	 * 	传入管理员对象
	 * 	boolean
	 */
	public boolean addAdmin(Admin admin) {
		return new AdminDao().addAdmin(admin);
	}
	/**
	 * 修改管理员
	 * 	传入旧管理员对象did，name
	 *  传入新管理员信息对象，完全构造
	 * 	boolean
	 */
	public boolean editAdmin(Admin oldAdmin, Admin newAdmin) {
		return new AdminDao().editAdmin(oldAdmin, newAdmin);
	}
	/**
	 * 根据ID获取管理员
	 * 	传入管理员ID
	 * 	返回Admin
	 */
	public Admin getAdminById(Admin admin) {
		return new AdminDao().getAdminById(admin);
	}
	/**
	 * 删除管理员
	 * 	传入仓库ID
	 * 	返回boolean
	 */
	public boolean removeAdmin(Admin admin) {
		return new AdminDao().removeAdmin(admin);
	}
	/**
	 * 查找仓库根据content
	 * 	返回Vector<Depot>
	 */
	public Vector<Admin> searchAdmin(String content){
		return new AdminDao().searchAdmin(content);
	}
}
