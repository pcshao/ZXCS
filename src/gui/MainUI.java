package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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
		setTitle("���й���ϵͳ");
		jp_content = new JPanel(new BorderLayout());
		
		//����ע��
		depotService = new DepotService();
		adminService = new AdminService();
		//������ť
		jp_north = new JPanel();
		jp_north.add(btn_today = new JButton("��������"));
		jp_north.add(btn_depots_search = new JButton("\u5E93\u5B58\u67E5\u8BE2"));
		jp_north.add(btn_date = new JButton("\u5F80\u6765\u8D26\u52A1"));
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
		tabbedPane.setFont(new Font("����", Font.PLAIN, 20));
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
				
//				JFrame jf = new JFrame();
//				JTextField tf = new JTextField();
//		        tf.setBounds(10, 10, 200, 30);
//				
//				MyDateChooser date = MyDateChooser.getInstance();
//				date.register(tf);
//				
//				jf.getContentPane().add(tf);
//				jf.setBounds(200,100,300,200);
//				jf.setVisible(true);
			}
		});
		
	}
	
	//����
	public static void main(String[] args) {
		new AdminService().Login("admin", "123");
		new MainUI();
	}

}
