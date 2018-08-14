package service;

import java.util.Vector;

import bean.Admin;
import bean.Customer;
import bean.Depot;
import bean.Employee;
import bean.Supplier;
import dao.AdminDao;
import dao.CustomDao;
import dao.DepotsDao;
import dao.EmployeesDao;
import dao.SuppliersDao;
import util.CastUtil;

/**
 * 系统服务
 * 	各种基础信息修改
 * 	某些与其他服务中的获取信息重合
 * @author pcshao
 *
 */
public class SystemService {

	/**
	 * 获取仓库所有信息
	 * @return
	 */
	public Vector<Vector> getDepots() {
		return new CastUtil().objectToVector(new DepotsDao().getDepots(), new Depot());
	}
	/**
	 * 获取员工所有信息
	 * @return
	 */
	public Vector<Vector> getEmployees() {
		return new CastUtil().objectToVector(new EmployeesDao().getEmployees(), new Employee());
	}
	/**
	 * 获取管理员所有信息
	 * @return
	 */
	public Vector<Vector> getAdmins() {
		return new CastUtil().objectToVector(new AdminDao().getAdmins(), new Admin());
	}
	/**
	 * 获取客户所有信息
	 * @return
	 */
	public Vector<Vector> getCustomers() {
		return new CastUtil().objectToVector(new CustomDao().getCustomers());
	}
	/**
	 * 获取供货商所有信息
	 * @return
	 */
	public Vector<Vector> getSuppliers() {
		return new CastUtil().objectToVector(new SuppliersDao().getSuppliers(), new Supplier());
	}
	/**
	 * 新增员工
	 */
	public Boolean addEmployee(Employee emp) {
		return new EmployeesDao().addEmployee(emp);
	}
	/**
	 * 新增客户
	 */
	public Boolean addCustomer(Customer cus) {
		return new CustomDao().addCustomer(cus);
	}
	/**
	 * 新增供应商
	 */
	public Boolean addSupplier(Supplier supplier) {
		return new SuppliersDao().addSupplier(supplier);
	}
	/**
	 * 编辑员工
	 * @param old
	 * @param new
	 * @return
	 */
	public Boolean editEmployee(Employee empOld, Employee empNew) {
		return new EmployeesDao().editEmployee(empOld, empNew);
	}
	/**
	 * 编辑客户
	 * @param old
	 * @param new
	 * @return
	 */
	public Boolean editCustomer(Customer cusOld, Customer cusNew) {
		return new CustomDao().editCustomer(cusOld, cusNew);
	}
	/**
	 * 编辑供应商
	 * @param old
	 * @param new
	 * @return
	 */
	public Boolean editSupplier(Supplier supplierOld, Supplier supplierNew) {
		return new SuppliersDao().editSupplier(supplierOld, supplierNew);
	}
	/**
	 * 查找员工根据content
	 */
	public Vector<Employee> searchEmployee(String content) {
		return new EmployeesDao().searchEmployee(content);
	}
	/**
	 * 查找客户根据content
	 */
	public Vector<Customer> searchCustomer(String content) {
		return new CustomDao().searchCustomer(content);
	}
	/**
	 * 查找供应商根据content
	 */
	public Vector<Supplier> searchSupplier(String content) {
		return new SuppliersDao().searchSupplier(content);
	}
	/**
	 * 删除员工 根据ID
	 */
	public Boolean removeEmployee(int id) {
		return new EmployeesDao().removeEmployee(id);
	}
	/**
	 * 删除客户 根据ID
	 */
	public Boolean removeCustomer(int id) {
		return new CustomDao().removeCustomer(id);
	}
	/**
	 * 删除供应商 根据ID
	 */
	public Boolean removeSupplier(int id) {
		return new SuppliersDao().removeSuppliers(id);
	}
}
