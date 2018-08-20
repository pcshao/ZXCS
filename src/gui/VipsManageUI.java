package gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import service.VipService;
import util.ImportExportHelp;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VipsManageUI extends JFrame{
	private JTable table,table2;
	private DefaultTableModel model,model2;
	private Vector columnName,columnName2,columname_add,columnCharge;
	private Vector<Vector> data;
	private Vector<Vector> data2;
	private String[] col = {"���","����","�ȼ�","�˻����","���ѽ��","��������","�ֻ���"};
	private String[] col2 = {"���","���ݺ�","��Ʒ���","��Ʒ����","��Ӧ��Ʒ����"};
	private String[] col3 = {"����","�ȼ�","�˻����","�ֻ���"};
	private String[] col4 = {"����","�ȼ�","�˻����","�ֻ���"};
	VipService vipService;
	
	public VipsManageUI() {
		this.setTitle("VIP����");
		vipService = new VipService();
		
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setBounds(0,0,800,600);
		this.setLocationRelativeTo(null);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		getContentPane().add(panel_2, BorderLayout.CENTER);

		//����
		panel_1.setLayout(new GridLayout(0, 9, 0, 0));
		JButton btn_add = new JButton("\u6CE8\u518C\u4F1A\u5458");
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Manage_addUI("������Ա", columname_add);
				refreshVipTable("");
			}
		});
		panel_1.add(btn_add);
		
		//��Ա��ֵ
		JButton btn_pay = new JButton("\u5145\u503C(\u4FEE\u6539)");
		btn_pay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector selected = new Vector<>();
				selected.add(table.getValueAt(table.getSelectedRow(), 1));
				selected.add(table.getValueAt(table.getSelectedRow(), 2));
				selected.add(table.getValueAt(table.getSelectedRow(), 3));
				selected.add(table.getValueAt(table.getSelectedRow(), 6));
				new Manage_editUI("��Ա��ֵ", columnCharge, selected);
				refreshVipTable("");
			}
		});
		panel_1.add(btn_pay);
		
		JButton btn_range = new JButton("\u4F1A\u5458\u7B49\u7EA7\u8BBE\u7F6E");
		btn_range.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VipRangeUI();
			}
		});
		panel_1.add(btn_range);
		
		//��Աע��
		JButton btn_logout = new JButton("\u6CE8\u9500\u4F1A\u5458");
		btn_logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(1==new JOptionPane().showConfirmDialog(VipsManageUI.this, "ȷ��ע�����ò���ֻ��ע�������Ѽ�¼�Ļ�Ա������ϵ����Ա", "", JOptionPane.YES_NO_OPTION))
					return;
				int id = (int) table.getValueAt(table.getSelectedRow(), 0);
				if(vipService.removeVipByid(id))
					new JOptionPane().showMessageDialog(VipsManageUI.this, "ע���ɹ�");
				else
					new JOptionPane().showMessageDialog(VipsManageUI.this, "�û�Ա�����Ѽ�¼����ֹɾ��");
				refreshVipTable("");
			}
		});
		panel_1.add(btn_logout);
		
		//��Ա����
		JButton btn_search = new JButton("\u67E5\u627E");
		btn_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String search = new JOptionPane().showInputDialog("������Ҫ���һ�Ա�ı�� ������ ���ֻ���");
				refreshVipTable(search);
			}
		});
		panel_1.add(btn_search);
		
		JButton btn_searchAll = new JButton("\u5168\u90E8");
		btn_searchAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshVipTable("");
			}
		});
		panel_1.add(btn_searchAll);
		
		JButton btn_export = new JButton("\u5BFC\u51FA");
		btn_export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showSaveDialog(VipsManageUI.this);
				try {
					String exportPath = chooser.getSelectedFile().getAbsolutePath();
					Vector<Vector> exportData = new Vector<>();
					exportData.add(columnName);
					exportData.addAll(data);
					ImportExportHelp.ExportData(exportData, exportPath);
					new JOptionPane().showMessageDialog(VipsManageUI.this, "�����ɹ�\n"+exportPath);
				} catch(Exception ee) {
					new JOptionPane().showMessageDialog(VipsManageUI.this, "δѡ��");
				}
			}
		});
		panel_1.add(btn_export);
		
		JButton btn_exit = new JButton("\u9000\u51FA");
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VipsManageUI.this.setVisible(false);
			}
		});
		panel_1.add(btn_exit);
		panel_2.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panel_2.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		table = new JTable(model);
		panel.add(new JScrollPane(table));
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblNewLabel = new JLabel("\u4F1A\u5458\u6D88\u8D39\u8BB0\u5F55");
		panel_4.add(lblNewLabel);
		
		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new GridLayout(0, 1, 0, 0));

		columnName = new Vector<>();
		columnName2 = new Vector<>();
		columname_add = new Vector<>();
		columnCharge = new Vector<>();
		for(String s:col)
			columnName.add(s);
		for(String s:col2)
			columnName2.add(s);
		for(String s:col3)
			columname_add.add(s);
		for(String s:col4)
			columnCharge.add(s);
		
		refreshVipTable("");
		
		table2 = new JTable();
		panel_5.add(new JScrollPane(table2));
		
		//���vip������������
		table.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
				int id = (Integer)table.getValueAt(table.getSelectedRow(), 0);
				data2 = vipService.getVipRecordById(id);
				model2 = new DefaultTableModel(data2, columnName2);
				table2.setModel(model2);
				table2.updateUI();
			}
		});
		
		this.setVisible(true);
	}
	private void refreshVipTable(String search) {
		data = vipService.getVips(search);
		model = new DefaultTableModel(data, columnName);
		table.setModel(model);
		table.updateUI();
	}

	public static void main(String[] args) {
		new VipsManageUI();
	}
}
