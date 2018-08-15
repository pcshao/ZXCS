package service;

import java.sql.SQLException;
import java.util.Vector;

import util.*;
import dao.*;
import service.*;
import bean.*;
import bean.orders.*;;

/**
 * ����Ա����
 * @author pcshao
 */
public class AdminService {

	public static Admin admin;		//���еľ�̬ȫ�ֲ���Ա��Ϣ
	
	private AdminDao admindao;

	/**
	 * �˵�¼����У���û�������
	 *  У��ʧ�ܷ���false
	 *  У��ɹ����洢ȫ�ֹ���Ա������Ϣ
	 */
	public boolean Login(String name,String password) {
		admindao = new AdminDao();
		//��¼У��
		if(!admindao.isLogin(name, password))
			return false;
		//У����ɣ���ȡ����Ա��Ϣ
		admin = admindao.getAdminInfo(name);
		return true;
	}
	/**
	 * �����¹���Ա
	 * 	�������Ա����
	 * 	boolean
	 */
	public boolean addAdmin(Admin admin) {
		return new AdminDao().addAdmin(admin);
	}
	/**
	 * �޸Ĺ���Ա
	 * 	����ɹ���Ա����did��name
	 *  �����¹���Ա��Ϣ������ȫ����
	 * 	boolean
	 */
	public boolean editAdmin(Admin oldAdmin, Admin newAdmin) {
		return new AdminDao().editAdmin(oldAdmin, newAdmin);
	}
	/**
	 * ����ID��ȡ����Ա
	 * 	�������ԱID
	 * 	����Admin
	 */
	public Admin getAdminById(Admin admin) {
		return new AdminDao().getAdminById(admin);
	}
	/**
	 * ɾ������Ա
	 * 	����ֿ�ID
	 * 	����boolean
	 */
	public boolean removeAdmin(Admin admin) {
		return new AdminDao().removeAdmin(admin);
	}
	/**
	 * ���Ҳֿ����content
	 * 	����Vector<Depot>
	 */
	public Vector<Admin> searchAdmin(String content){
		return new AdminDao().searchAdmin(content);
	}
}
