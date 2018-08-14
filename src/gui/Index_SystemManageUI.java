package gui;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Index_SystemManageUI extends JPanel {
	
	JButton btn_admins,btn_customers,btn_employees,btn_depots,btn_suppliers;
	JPanel jp_content;
	private JPanel jp_1;
	private JPanel jp_2;
	
	public Index_SystemManageUI() {
		setLayout(new BorderLayout(0, 0));
		jp_content = new JPanel();
		jp_content.setBorder(new EmptyBorder(100, 100, 100, 100));
		
		this.add(jp_content);
		
		jp_1 = new JPanel();
		jp_content.add(jp_1);
		jp_1.setLayout(new GridLayout(2, 1, 0, 0));
		
		jp_content.add(btn_admins = new JButton("操作员管理"));
		jp_1.add(btn_admins);
		jp_content.add(btn_employees = new JButton("员工管理"));
		jp_1.add(btn_employees);
		
		jp_2 = new JPanel();
		jp_content.add(jp_2);
		jp_2.setLayout(new GridLayout(3, 1, 0, 0));
		jp_content.add(btn_customers = new JButton("客户管理"));
		jp_2.add(btn_customers);
		jp_content.add(btn_depots = new JButton("仓库管理"));
		jp_2.add(btn_depots);
		jp_content.add(btn_suppliers = new JButton("供货商管理"));
		jp_2.add(btn_suppliers);
		
		//监听
		//操作员管理
		btn_admins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Manage_ParentUI("操作员管理");
			}
		});
		btn_employees.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Manage_ParentUI("员工管理");
			}
		});
		btn_customers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Manage_ParentUI("客户管理");
			}
		});
		btn_depots.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Manage_ParentUI("仓库管理");
			}
		});
		btn_suppliers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Manage_ParentUI("供应商管理");
			}
		});
	}
}
