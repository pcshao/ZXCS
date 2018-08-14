package gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bean.Admin;
import bean.Customer;
import bean.Depot;
import bean.Employee;
import bean.Supplier;
import service.AdminService;
import service.DepotService;
import service.SystemService;
import util.CastUtil;

import javax.swing.JScrollPane;

/**
 * 多实体的详细信息管理父组件
 * 	多构造实现多重用
 * @author pcshao
 *
 */
public class Manage_ParentUI extends JFrame{
	
	private JTable table;
	private DefaultTableModel model;
	public Vector items;
	
	private SystemService systemService;
	private DepotService depotService;
	private AdminService adminService;
	
	public Manage_ParentUI(String title) {
			
		systemService = new SystemService();
		depotService = new DepotService();
		adminService = new AdminService();
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel jp_top = new JPanel();
		getContentPane().add(jp_top, BorderLayout.NORTH);
		jp_top.setLayout(new GridLayout(0, 8, 0, 0));
		
		JButton btn_add = new JButton("增加");
		jp_top.add(btn_add);
		
		JButton btn_edit = new JButton("修改");
		jp_top.add(btn_edit);
		
		JButton btn_delete = new JButton("删除");
		jp_top.add(btn_delete);
		
		JButton btn_search = new JButton("查找");
		jp_top.add(btn_search);
		
		JButton btn_all = new JButton("全部");
		jp_top.add(btn_all);
		
		JButton btn_export = new JButton("导出");
		jp_top.add(btn_export);
		
		JButton btn_exit = new JButton("退出");
		jp_top.add(btn_exit);
		
		JPanel jp_center = new JPanel();
		getContentPane().add(jp_center, BorderLayout.CENTER);
		jp_center.setLayout(new BorderLayout(0, 0));
		
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setBounds(0,0,770,400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		//判断窗口
		if("员工管理".equals(title)) {
			Manage_employees(title);
		}else if("仓库管理".equals(title)) {
			Manage_depots(title);
		}else if("操作员管理".equals(title)) {
			Manage_admins(title);
		}else if("客户管理".equals(title)) {
			Manage_customers(title);
		}else if("供应商管理".equals(title)) {
			Manage_appliers(title);
		}
		
		JScrollPane scrollPane = new JScrollPane(table);
		jp_center.add(scrollPane);
		
		//按键
		btn_exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Manage_ParentUI.this.setVisible(false);
			}
		});
		/**
		 * 主功能add区，通过判断下一个窗口的标题名来对应到不同的功能窗体
		 * 	传入当前窗体选择table数据对象
		 * 	下一个窗口操作完成，刷新本窗口
		 */
		btn_add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//增加
				if("员工管理".equals(title)) {
					new Manage_addUI("增加员工",items);
					updateTable(systemService.getEmployees());
				}else if("仓库管理".equals(title)) {
					new Manage_addUI("增加仓库",items);
					updateTable(systemService.getDepots());
				}else if("操作员管理".equals(title)) {
					new Manage_addUI("增加操作员",items);
					updateTable(systemService.getAdmins());
				}else if("客户管理".equals(title)) {
					new Manage_addUI("增加客户",items);
					updateTable(systemService.getCustomers());
				}else if("供应商管理".equals(title)) {
					new Manage_addUI("增加供应商",items);
					updateTable(systemService.getSuppliers());
				}
			}
		});
		/**
		 * 主功能edit区，通过判断下一个窗口的标题名来对应到不同的功能窗体
		 * 	传入当前窗体选择table数据对象
		 * 	下一个窗口操作完成，刷新本窗口
		 */
		btn_edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//修改
				if("员工管理".equals(title)) {
					Vector selected = systemService.getEmployees().get(table.getSelectedRow());
					if(selected==null)
						return;
					new Manage_editUI("修改员工",items,selected);
					updateTable(systemService.getEmployees());
				}else if("仓库管理".equals(title)) {
					Vector selected = systemService.getDepots().get(table.getSelectedRow());
					if(selected==null)
						return;
					new Manage_editUI("修改仓库",items,selected);
					updateTable(systemService.getDepots());
				}else if("操作员管理".equals(title)) {
					Vector selected = systemService.getAdmins().get(table.getSelectedRow());
					if(selected==null)
						return;
					new Manage_editUI("修改操作员",items,selected);
					updateTable(systemService.getAdmins());
				}else if("客户管理".equals(title)) {
					Vector selected = systemService.getCustomers().get(table.getSelectedRow());
					if(selected==null)
						return;
					new Manage_editUI("修改客户",items,selected);
					updateTable(systemService.getCustomers());
				}else if("供应商管理".equals(title)) {
					Vector selected = systemService.getSuppliers().get(table.getSelectedRow());
					if(selected==null)
						return;
					new Manage_editUI("修改供应商",items,selected);
					updateTable(systemService.getSuppliers());
				}
			}
		});
		btn_delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//删除
				if("员工管理".equals(title)) {
					Vector selected = systemService.getEmployees().get(table.getSelectedRow());
					if(selected==null)
						return;
					if(confirmRemove(selected.get(1).toString()))
						systemService.removeEmployee(Integer.parseInt(selected.get(0).toString()));
					updateTable(systemService.getEmployees());	
				}else if("仓库管理".equals(title)) {
					Vector selected = systemService.getDepots().get(table.getSelectedRow());
					if(selected==null)
						return;
					Depot depot = new Depot(Integer.parseInt(selected.get(0).toString()), selected.get(1).toString());
					if(confirmRemove(depot.getName()))
						depotService.removeDepotById(depot);
					updateTable(systemService.getDepots());
				}else if("操作员管理".equals(title)) {
					Vector selected = systemService.getAdmins().get(table.getSelectedRow());
					if(selected==null)
						return;
					Admin admin = new Admin(Integer.parseInt(selected.get(0).toString()), selected.get(1).toString());
					if(confirmRemove(admin.getName()))
						adminService.removeAdmin(admin);
					updateTable(systemService.getAdmins());
				}else if("客户管理".equals(title)) {
					Vector selected = systemService.getCustomers().get(table.getSelectedRow());
					if(selected==null)
						return;
					if(confirmRemove(selected.get(1).toString()))
						systemService.removeCustomer(Integer.parseInt(selected.get(0).toString()));
					updateTable(systemService.getCustomers());	
				}else if("供应商管理".equals(title)) {
					Vector selected = systemService.getSuppliers().get(table.getSelectedRow());
					if(selected==null)
						return;
					if(confirmRemove(selected.get(1).toString()))
						systemService.removeSupplier(Integer.parseInt(selected.get(0).toString()));
					updateTable(systemService.getSuppliers());	
				}
			}
		});
		btn_search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String content = new JOptionPane().showInputDialog("请输入查找信息");
				if("员工管理".equals(title)) {
					updateTable(new CastUtil().objectToVector(systemService.searchEmployee(content), new Employee()));
				}else if("仓库管理".equals(title)) {
					updateTable(new CastUtil().objectToVector(depotService.searchDepot(content), new Depot()));
				}else if("操作员管理".equals(title)) {
					updateTable(new CastUtil().objectToVector(adminService.searchAdmin(content), new Admin()));
				}else if("客户管理".equals(title)) {
					updateTable(new CastUtil().objectToVector(systemService.searchCustomer(content), new Customer()));
				}else if("供应商管理".equals(title)) {
					updateTable(new CastUtil().objectToVector(systemService.searchSupplier(content), new Supplier()));	
				}
			}
		});
		btn_all.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if("员工管理".equals(title)) {
					updateTable(systemService.getEmployees());	
				}else if("仓库管理".equals(title)) {
					updateTable(systemService.getDepots());
				}else if("操作员管理".equals(title)) {
					updateTable(systemService.getAdmins());
				}else if("客户管理".equals(title)) {
					updateTable(systemService.getCustomers());	
				}else if("供应商管理".equals(title)) {
					updateTable(systemService.getSuppliers());	
				}
			}
		});
		btn_export.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//导出
			}
		});
	}
	private boolean confirmRemove(String name) {
		return new JOptionPane().showConfirmDialog(Manage_ParentUI.this, "是否确认删除  "+name, "", JOptionPane.YES_NO_OPTION)==0;
	}
	private void updateTable(Vector<Vector> tableData) {
		//刷新数据
		model = new DefaultTableModel(tableData, items);
		table.setModel(model);
		table.updateUI();
	}
	public void Manage_depots(String title) {
		Manage_ParentUI.this.setTitle(title);
		table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		items = new Vector();
		items.add("编号");
		items.add("仓库名称");
		items.add("负责人");
		items.add("联系电话");
		items.add("仓库地址");
		items.add("备注");
		//仓库管理
		model = new DefaultTableModel(systemService.getDepots(), items);
		table.setModel(model);
		table.updateUI();
		
	}
	public void Manage_employees(String title) {
		Manage_ParentUI.this.setTitle(title);
		//员工管理
		table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		items = new Vector();
		items.add("编号");
		items.add("员工姓名");
		items.add("职务");
		items.add("联系电话");
		items.add("联系地址");
		items.add("备注");
		model = new DefaultTableModel(systemService.getEmployees(), items);
		table.setModel(model);
		table.updateUI();
	}
	public void Manage_admins(String title) {
		Manage_ParentUI.this.setTitle(title);
		//操作员管理
		table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		items = new Vector();
		items.add("编号");
		items.add("用户名称");
		items.add("所任职务");
		items.add("是否POS操作员");
		items.add("是否柜长");
		items.add("可用");
		model = new DefaultTableModel(systemService.getAdmins(), items);
		table.setModel(model);
		table.updateUI();
	}
	public void Manage_customers(String title) {
		Manage_ParentUI.this.setTitle(title);
		//客户管理
		table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		items = new Vector();
		items.add("编号");
		items.add("客户名称");
		items.add("联系人");
		items.add("联系电话");
		items.add("联系地址");
		items.add("备注");
		model = new DefaultTableModel(systemService.getCustomers(), items);
		table.setModel(model);
		table.updateUI();
	}
	public void Manage_appliers(String title) {
		Manage_ParentUI.this.setTitle(title);
		//供应商管理
		table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		items = new Vector();
		items.add("编号");
		items.add("供货商名称");
		items.add("联系人");
		items.add("联系电话");
		items.add("联系地址");
		items.add("备注");
		model = new DefaultTableModel(systemService.getSuppliers(), items);
		table.setModel(model);
		table.updateUI();
	}
	
}
