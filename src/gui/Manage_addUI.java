package gui;

import javax.swing.JDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;

import bean.Admin;
import bean.Customer;
import bean.Depot;
import bean.Employee;
import bean.Supplier;
import service.AdminService;
import service.DepotService;
import service.SystemService;

public class Manage_addUI extends JDialog{
	
	private JTextField tf_1,tf_3,tf_4,tf_5,tf_6,tf_2;
	private JLabel lb_1,lb_2,lb_3,lb_4,lb_5,lb_6;
	DepotService depotService;
	AdminService adminService;
	SystemService systemService;
	
	public Manage_addUI(String title, Vector items) {
		setTitle(title);

		depotService = new DepotService();
		adminService = new AdminService();
		systemService = new SystemService();
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel jp_north = new JPanel();
		getContentPane().add(jp_north, BorderLayout.NORTH);
		
		JPanel jp_title = new JPanel();
		jp_north.add(jp_title);
		
		JPanel jp_center = new JPanel();
		getContentPane().add(jp_center, BorderLayout.CENTER);
		jp_center.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel jp_content_l = new JPanel();
		jp_content_l.setBorder(new EmptyBorder(15, 5, 15, 0));
		jp_center.add(jp_content_l);
		jp_content_l.setLayout(new GridLayout(6, 1, 0, 0));
		
		JPanel panel = new JPanel();
		jp_content_l.add(panel);
		
		lb_1 = new JLabel("New label");
		panel.add(lb_1);
		
		JPanel panel_1 = new JPanel();
		jp_content_l.add(panel_1);
		
		lb_2 = new JLabel("New label");
		panel_1.add(lb_2);
		
		JPanel panel_2 = new JPanel();
		jp_content_l.add(panel_2);
		
		lb_3 = new JLabel("New label");
		panel_2.add(lb_3);
		
		JPanel panel_3 = new JPanel();
		jp_content_l.add(panel_3);
		
		lb_4 = new JLabel("New label");
		panel_3.add(lb_4);
		
		JPanel panel_4 = new JPanel();
		jp_content_l.add(panel_4);

		JPanel panel_11 = new JPanel();
		jp_content_l.add(panel_11);
		
		lb_5 = new JLabel("New label");
		panel_4.add(lb_5);
		
		lb_6 = new JLabel("New label");
		panel_11.add(lb_6);
		
		JPanel jp_content_r = new JPanel();
		jp_content_r.setBorder(new EmptyBorder(15, 0, 15, 10));
		jp_center.add(jp_content_r);
		jp_content_r.setLayout(new GridLayout(6, 1, 0, 0));
		
		JPanel panel_5 = new JPanel();
		jp_content_r.add(panel_5);
		panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panel_12 = new JPanel();
		jp_content_r.add(panel_12);
		
		tf_1 = new JTextField();
		panel_5.add(tf_1);
		tf_1.setColumns(10);
		
		JPanel panel_7 = new JPanel();
		jp_content_r.add(panel_7);
		
		tf_3 = new JTextField();
		panel_7.add(tf_3);
		tf_3.setColumns(10);
		
		JPanel panel_8 = new JPanel();
		jp_content_r.add(panel_8);
		
		tf_4 = new JTextField();
		panel_8.add(tf_4);
		tf_4.setColumns(10);
		
		JPanel panel_9 = new JPanel();
		jp_content_r.add(panel_9);
		
		tf_5 = new JTextField();
		panel_9.add(tf_5);
		tf_5.setColumns(10);
		
		JPanel panel_10 = new JPanel();
		jp_content_r.add(panel_10);
		
		tf_6 = new JTextField();
		tf_6.setText(" ");
		panel_10.add(tf_6);
		tf_6.setColumns(10);
		
		tf_2 = new JTextField();
		panel_12.add(tf_2);
		tf_2.setColumns(10);

		//label
		lb_1.setText(items.get(0).toString());
		lb_2.setText(items.get(1).toString());
		lb_3.setText(items.get(2).toString());
		lb_4.setText(items.get(3).toString());
		try {
			lb_5.setText(items.get(4).toString());
			lb_6.setText(items.get(5).toString());
		}catch (Exception e){
			lb_5.setVisible(false);
			tf_6.setVisible(false);
			lb_6.setVisible(false);
			tf_2.setVisible(false);
		}
		
		JPanel jp_south = new JPanel();
		getContentPane().add(jp_south, BorderLayout.SOUTH);
		
		JPanel panel_6 = new JPanel();
		jp_south.add(panel_6);
		panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btn_confirm = new JButton("\u786E\u5B9A");
		btn_confirm.setHorizontalAlignment(SwingConstants.LEFT);
		panel_6.add(btn_confirm);
		
		JButton btn_cancel = new JButton("\u53D6\u6D88");
		btn_cancel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_6.add(btn_cancel);
		
		//按钮
		btn_confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if("增加仓库".equals(title)) {
					Depot depot = new Depot(Integer.parseInt(tf_1.getText().trim()), tf_2.getText().trim(), tf_3.getText().trim(),
							tf_4.getText().trim(), tf_5.getText().trim(), tf_6.getText().trim());
					if(depotService.addDepot(depot))
						showStatus(0);
					else {
						showStatus(-1);
						return;
					}
					Manage_addUI.this.setVisible(false);
				}else if("增加员工".equals(title)) {
					Employee employee = new Employee();
					employee.setEid(Integer.parseInt(tf_1.getText().trim()));
					employee.setName(tf_2.getText().trim());
					employee.setJob(tf_3.getText().trim());
					employee.setPhone(tf_4.getText().trim());
					employee.setAddress(tf_5.getText().trim());
					employee.setBz(tf_6.getText().trim());
					employee.setPower("普通");
					if(systemService.addEmployee(employee))
						showStatus(0);
					else {
						showStatus(-1);
						return;
					}
					Manage_addUI.this.setVisible(false);
				}else if("增加操作员".equals(title)) {
					//默认密码123
					Admin admin = new Admin(Integer.parseInt(tf_1.getText().trim()), tf_2.getText().trim(), "123", tf_3.getText().trim(),
							"普通", tf_5.getText().trim(), tf_4.getText().trim(), tf_6.getText().trim());
					if(adminService.addAdmin(admin))
						showStatus(0);
					else {
						showStatus(-1);
						return;
					}
					Manage_addUI.this.setVisible(false);
				}else if("增加客户".equals(title)) {
					Customer cus = new Customer();
					cus.setCid(Integer.parseInt(tf_1.getText().trim()));
					cus.setName(tf_2.getText().trim());
					cus.setContact(tf_3.getText().trim());
					cus.setPhone(tf_4.getText().trim());
					cus.setAddress(tf_5.getText().trim());
					cus.setBz(tf_6.getText().trim());
					if(systemService.addCustomer(cus))
						showStatus(0);
					else {
						showStatus(-1);
						return;
					}
					Manage_addUI.this.setVisible(false);
				}else if("增加供应商".equals(title)) {
					Supplier supplier = new Supplier();
					supplier.setSid(Integer.parseInt(tf_1.getText().trim()));
					supplier.setName(tf_2.getText().trim());
					supplier.setContact(tf_3.getText().trim());
					supplier.setPhone(tf_4.getText().trim());
					supplier.setAddress(tf_5.getText().trim());
					supplier.setBz(tf_6.getText().trim());
					if(systemService.addSupplier(supplier))
						showStatus(0);
					else {
						showStatus(-1);
						return;
					}
					Manage_addUI.this.setVisible(false);
				}
			}
		});
		btn_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Manage_addUI.this.setVisible(false);
			}
		});
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setBounds(0, 0, 300, 350);
		this.setLocationRelativeTo(null);
		
		this.setModal(true);
		this.setVisible(true);
	}
	/**
	 * 状态显示
	 * @param a
	 */
	private void showStatus(int a) {
		if(a==-1)
			new JOptionPane().showMessageDialog(Manage_addUI.this, "操作失败");
		if(a==0)
			new JOptionPane().showMessageDialog(Manage_addUI.this, "添加成功");
		else if(a==1)
			new JOptionPane().showMessageDialog(Manage_addUI.this, "编辑成功");
		else if(a==2)
			new JOptionPane().showMessageDialog(Manage_addUI.this, "删除成功");
	}
	
}
