package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import bean.Admin;
import service.AdminService;

import java.awt.GridLayout;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PassWordManage extends JFrame{
	private JTable table;
	private DefaultTableModel model;
	private Vector<Vector> data;
	private Vector columname;
	private String[] col = {"���","����","����","ְ��"};
	AdminService adminService;
	
	public PassWordManage() {
		this.setTitle("�������");
		
		adminService = new AdminService();
		
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setBounds(0,0,800,600);
		this.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JButton btn_change = new JButton("\u5BC6\u7801\u4FEE\u6539 ");
		btn_change.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector selected = new Vector<>();
				int id = (int) table.getValueAt(table.getSelectedRow(), 0);
				selected.add(id);
				selected.add(table.getValueAt(table.getSelectedRow(), 1));
				selected.add(table.getValueAt(table.getSelectedRow(), 2));
				selected.add(table.getValueAt(table.getSelectedRow(), 3));
				PassWordManageEditDialog a = new PassWordManageEditDialog(selected);
				String password = a.getPassword();
				if(adminService.changeAdminPassword(id, password))
					new JOptionPane().showMessageDialog(PassWordManage.this, "�޸ĳɹ�");
				else {
					new JOptionPane().showMessageDialog(PassWordManage.this, "����ʧ��");
					return;
				}
				refreshTable();
			}
		});
		panel.add(btn_change);
		
		JButton btn_resetpwd = new JButton("\u5168\u90E8\u6062\u590D\u4E3A\u5BC6\u7801123");
		btn_resetpwd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.YES_OPTION==new JOptionPane().showConfirmDialog(PassWordManage.this, "�˲������Ὣ�����û���������Ϊ123"))
					if(adminService.resetAdminPassword())
						new JOptionPane().showMessageDialog(PassWordManage.this, "���óɹ�");
					else
						new JOptionPane().showMessageDialog(PassWordManage.this, "����ʧ��");
				refreshTable();
			}
		});
		panel.add(btn_resetpwd);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		table = new JTable();
		panel_1.add(new JScrollPane(table));
		
		//��ʼ���������
		columname = new Vector<>();
		for(String s:col)
			columname.add(s);
		
		refreshTable();
		
		this.setVisible(true);
	}
	private void refreshTable() {
		data = adminService.getAdminsPassword();
		model = new DefaultTableModel(data, columname);
		table.setModel(model);
		table.updateUI();
	}
	public static void main(String[] args) {
		new PassWordManage();
	}
}