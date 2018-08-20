package gui;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bean.VipRange;
import service.VipService;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VipRangeUI extends JDialog{
	private JTable table;
	private DefaultTableModel model;
	private String[] col = {"等级","折扣力度","升级门槛金额","该等级对应会员数"};
	private String[] col2 = {"等级","折扣力度","升级门槛金额","预留（不填写）"};
	private Vector columnames,columnames_add;
	private Vector<Vector> data;
	//注册服务
	VipService vipService;
	
	public VipRangeUI() {
		this.setTitle("会员等级管理");
		
		//初始化列名数组
		columnames_add = new  Vector<>();
		columnames = new Vector();
		for(String s:col)
			columnames.add(s);
		for(String s:col2)
			columnames_add.add(s);
		
		vipService = new VipService();
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		
		JButton btn_add = new JButton("\u65B0\u589E\u7B49\u7EA7");
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Manage_addUI a = new Manage_addUI("会员等级添加", columnames_add);
				Vector v = a.getRetData();
				VipRange vr = new VipRange();
				vr.setRid(Integer.parseInt(v.get(0).toString()));
				vr.setDiscount(Double.parseDouble(v.get(1).toString()));
				vr.setRangeMoney(Double.parseDouble(v.get(2).toString()));
				if(vipService.addVipRange(vr))
					new JOptionPane().showMessageDialog(VipRangeUI.this, "添加成功");
				else {
					new JOptionPane().showMessageDialog(VipRangeUI.this, "操作失败");
					btn_add.doClick();
				}
				refreshTable();
			}
		});
		panel_1.add(btn_add);
		
		JButton btn_edit = new JButton("\u7F16\u8F91");
		btn_edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector selected = data.get(table.getSelectedRow());
				Manage_editUI a = new Manage_editUI("会员等级编辑", columnames_add, selected);
				Vector v = a.getRetData();
				VipRange vr = new VipRange();
				vr.setRid(Integer.parseInt(v.get(0).toString()));
				vr.setDiscount(Double.parseDouble(v.get(1).toString()));
				vr.setRangeMoney(Double.parseDouble(v.get(2).toString()));
				if(vipService.editVipRangeById(vr))
					new JOptionPane().showMessageDialog(VipRangeUI.this, "编辑成功，折扣将立即生效,升级门槛将在下次开启后台管理系统后生效");
				else {
					new JOptionPane().showMessageDialog(VipRangeUI.this, "操作失败");
					btn_add.doClick();
				}
				refreshTable();
			}
		});
		panel_1.add(btn_edit);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		table = new JTable();
		panel.add(new JScrollPane(table));
		
		//表格加载
		refreshTable();

		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setBounds(0,0,400,300);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setVisible(true);
	}
	
	private void refreshTable() {
		data = vipService.getVipRangeInfo(); 
		model = new DefaultTableModel(data, columnames);
		table.setModel(model);
		table.updateUI();
	}

}
