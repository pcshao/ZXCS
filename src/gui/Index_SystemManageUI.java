package gui;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;

import com.lzw.BackgroundPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Index_SystemManageUI extends JPanel {
	
	JButton btn_admins,btn_adminsPasswd,btn_customers,btn_employees,btn_depots,btn_suppliers,btn_vips;
	JPanel jp_content;
	private JPanel jp_1;
	private JPanel jp_2;
	
	public Index_SystemManageUI() {
		setLayout(new BorderLayout(0, 0));

		//����ͼ
		BackgroundPanel bk = new BackgroundPanel();
		add(bk, BorderLayout.CENTER);
		bk.setImage(Toolkit.getDefaultToolkit().getImage(Index_InManagerUI.class.getResource(LoginUI.BKPATH)));
		bk.setLayout(new BorderLayout(0, 0));
		jp_content = new JPanel();
		jp_content.setOpaque(false);
		bk.add(jp_content, BorderLayout.CENTER);
		jp_content.setBorder(new EmptyBorder(100, 100, 100, 100));
		
		jp_1 = new JPanel();
		jp_content.add(jp_1);
		jp_1.setLayout(new GridLayout(4, 1, 0, 0));
		
		jp_content.add(btn_admins = new JButton("����Ա����"));
		jp_1.add(btn_admins);
		jp_content.add(btn_employees = new JButton("Ա������"));
		jp_1.add(btn_employees);
		jp_content.add(btn_vips = new JButton("VIP����"));
		jp_1.add(btn_vips);
		jp_1.add(btn_adminsPasswd = new JButton("�������"));
		
		jp_2 = new JPanel();
		jp_content.add(jp_2);
		jp_2.setLayout(new GridLayout(3, 1, 0, 0));
		jp_content.add(btn_customers = new JButton("�ͻ�����"));
		jp_2.add(btn_customers);
		jp_content.add(btn_depots = new JButton("�ֿ����"));
		jp_2.add(btn_depots);
		jp_content.add(btn_suppliers = new JButton("�����̹���"));
		jp_2.add(btn_suppliers);
		Index_bottom_SomeButtonUI index_bottom_SomeButtonUI = new Index_bottom_SomeButtonUI();
		bk.add(index_bottom_SomeButtonUI, BorderLayout.SOUTH);
		
		//����
		//����Ա����
		btn_admins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Manage_ParentUI("����Ա����");
			}
		});
		btn_employees.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Manage_ParentUI("Ա������");
			}
		});
		btn_adminsPasswd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PassWordManage();
			}
		});
		btn_vips.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VipsManageUI();
			}
		});
		btn_customers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Manage_ParentUI("�ͻ�����");
			}
		});
		btn_depots.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Manage_ParentUI("�ֿ����");
			}
		});
		btn_suppliers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Manage_ParentUI("��Ӧ�̹���");
			}
		});
	}
}
