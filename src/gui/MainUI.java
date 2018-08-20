package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bean.Goods;
import gui.yc.StockCheckModelWindow;
import service.AdminService;
import service.DepotService;
import javax.swing.JTabbedPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;


public class MainUI extends JFrame{
	
	JPanel jp_content;
	JPanel jp_north,jp_west,jp_center,jp_bottom;
	JPanel jp_tabb;
	JButton btn_today,btn_depots_search,btn_date;
	JButton btn_in,btn_sell,btn_depots,btn_reports,btn_system;
	JTextField tf_adminName;
	JTabbedPane tabbedPane;
	
	private DepotService depotService;
	private AdminService adminService;
	private JPanel jp_operator;
	private JPanel jp_mess;
	private JLabel lblNewLabel;
	
	
	public MainUI() {
		setTitle("\u8D85\u5E02\u7BA1\u7406\u7CFB\u7EDFV1.0");
		jp_content = new JPanel(new BorderLayout());
		
		//����ע��
		depotService = new DepotService();
		adminService = new AdminService();
		//������ť
		jp_north = new JPanel();
		jp_north.add(btn_today = new JButton("��������"));
		//ͼ��
		ImageIcon ic_alert = new ImageIcon(MainUI.class.getResource("/icon/alert.png"));
		ImageIcon ic_depot = new ImageIcon(MainUI.class.getResource("/icon/depot.png"));
		ImageIcon ic_vip = new ImageIcon(MainUI.class.getResource("/icon/vip2.png"));
		ic_alert.setImage(ic_alert.getImage().getScaledInstance(20, 20,Image.SCALE_DEFAULT ));
		ic_depot.setImage(ic_depot.getImage().getScaledInstance(20, 20,Image.SCALE_DEFAULT ));
		ic_vip.setImage(ic_vip.getImage().getScaledInstance(20, 20,Image.SCALE_DEFAULT ));
		//
		btn_today.setIcon(ic_alert);
		jp_north.add(btn_depots_search = new JButton("\u5E93\u5B58\u67E5\u8BE2"));
		btn_depots_search.setIcon(ic_depot);
		jp_north.add(btn_date = new JButton("\u4F1A\u5458\u7BA1\u7406"));
		btn_date.setIcon(ic_vip);
		jp_content.add(jp_north,BorderLayout.NORTH);
		//�󲿹��ܰ�ť
//		jp_west = new JPanel(new GridLayout(6, 1));
//		jp_west.add(btn_in = new JButton("��������"));
//		jp_west.add(btn_sell = new JButton("���۹���"));
//		jp_west.add(btn_depots = new JButton("������"));
//		jp_west.add(btn_reports = new JButton("ͳ�Ʊ���"));
//		jp_west.add(btn_system = new JButton("ϵͳ����"));
//		jp_west.add(new JLabel("���н����������"));
//		jp_content.add(jp_west,BorderLayout.WEST);

		//ѡ���ť
		jp_center = new JPanel(new GridLayout(1, 1));
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBorder(null);
		tabbedPane.setBackground(Color.LIGHT_GRAY);
		tabbedPane.setForeground(Color.DARK_GRAY);
		tabbedPane.setFont(new Font("����", Font.PLAIN, 17));
		tabbedPane.add("��������", new Index_InManagerUI());
		tabbedPane.add("���۹���", new Index_SellManager());
		tabbedPane.add("������", new Index_DepotsManagerUI());
		tabbedPane.add("ͳ�Ʊ���", new Index_ReportsUI());
		tabbedPane.add("ϵͳ����", new Index_SystemManageUI());
		jp_center.add(tabbedPane);
		jp_content.add(jp_center,BorderLayout.CENTER);
		//�ϲ�����
		jp_bottom = new JPanel();
		jp_content.add(jp_bottom,BorderLayout.SOUTH);
		
		getContentPane().add(jp_content);
		jp_bottom.setLayout(new GridLayout(0, 2, 0, 0));
		
		jp_mess = new JPanel();
		jp_bottom.add(jp_mess);
		
		lblNewLabel = new JLabel("\u8D85\u5E02\u7BA1\u7406\u7CFB\u7EDF \u540E\u53F0");
		jp_mess.add(lblNewLabel);
		
		jp_operator = new JPanel();
		jp_bottom.add(jp_operator);
		jp_bottom.add(tf_adminName = new JTextField(20));
		jp_operator.add(tf_adminName);
		tf_adminName.setEditable(false);
		tf_adminName.setText("����Ա��"+adminService.admin.getName());
		this.setBounds(200,100,800,480);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		/*
		 * ����ѯ
		 */
		btn_depots_search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new StockCheckModelWindow();
			}
		});
		/*
		 * ��������
		 */
		btn_today.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * ��Ҫ���ݣ�
				 * 	�Ƿ�Ҫ����
				 * 		��	��ȡ��Ҫ�����Ļ����嵥�����ֿ�û���Ŀ��
				 * 		��	��������
				 */
				ArrayList<Goods> alertGoods;
				StringBuilder text = new StringBuilder();				//�������ַ��������ڸĽ���
				if((alertGoods=depotService.isAlert())!=null) {
					new JOptionPane().showMessageDialog(MainUI.this, "��澯����");
					for(Goods g : alertGoods)
						text.append(g.getId()+"����Ʒ����"+g.getName()+"�����ʣ�ࣺ"+g.getTempNum()+"����Ҫ������\n");
					new JOptionPane().showMessageDialog(MainUI.this, text.toString());
				}
				else
					new JOptionPane().showMessageDialog(MainUI.this, "ûɶ������");
				
			}
		});
		/*
		 * ���ڿؼ����� 
		 */
		btn_date.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VipsManageUI();
			}
		});
		
	}
	
	//����
	public static void main(String[] args) {
		new AdminService().Login("admin", "123");
		new MainUI();
	}

}
