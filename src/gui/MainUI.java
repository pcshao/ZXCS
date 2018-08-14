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
import service.AdminService;
import service.DepotService;
import util.MyDateChooser;
import javax.swing.JDesktopPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
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
		setTitle("超市管理系统");
		jp_content = new JPanel(new BorderLayout());
		
		//服务注册
		depotService = new DepotService();
		adminService = new AdminService();
		//顶部按钮
		jp_north = new JPanel();
		jp_north.add(btn_today = new JButton("今日提醒"));
		jp_north.add(btn_depots_search = new JButton("库存查询"));
		jp_north.add(btn_date = new JButton("日期查询"));
		jp_content.add(jp_north,BorderLayout.NORTH);
		//左部功能按钮
//		jp_west = new JPanel(new GridLayout(6, 1));
//		jp_west.add(btn_in = new JButton("进货管理"));
//		jp_west.add(btn_sell = new JButton("销售管理"));
//		jp_west.add(btn_depots = new JButton("库存管理"));
//		jp_west.add(btn_reports = new JButton("统计报表"));
//		jp_west.add(btn_system = new JButton("系统管理"));
//		jp_west.add(new JLabel("超市进销管理软件"));
//		jp_content.add(jp_west,BorderLayout.WEST);

		//选项卡按钮
		jp_center = new JPanel(new GridLayout(1, 1));
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBorder(null);
		tabbedPane.setBackground(Color.LIGHT_GRAY);
		tabbedPane.setForeground(Color.WHITE);
		tabbedPane.setFont(new Font("黑体", Font.PLAIN, 20));
		tabbedPane.add("进货管理", new Index_InManagerUI());
		tabbedPane.add("销售管理", new Index_SellManager());
		tabbedPane.add("库存管理", new Index_DepotsManagerUI());
		tabbedPane.add("统计报表", new Index_ReportsUI());
		tabbedPane.add("系统管理", new Index_SystemManageUI());
		jp_center.add(tabbedPane);
		jp_content.add(jp_center,BorderLayout.CENTER);
		//南部内容
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
		tf_adminName.setText("操作员："+adminService.admin.getName());
		this.setBounds(200,100,800,480);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		/*
		 * 库存查询
		 */
		btn_depots_search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * 需要数据：
				 * 	所有商品
				 * 	...
				 */

				/*
				 * 跳转窗口：
				 * 	增添商品窗口
				 * 	...
				 */
			}
		});
		/*
		 * 今日提醒
		 */
		btn_today.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * 需要数据：
				 * 	是否要补货
				 * 		是	调取需要补货的货物清单、各仓库该货物的库存
				 * 		否	今日提醒
				 */
				ArrayList<Goods> alertGoods;
				StringBuilder text = new StringBuilder();				//测试用字符串，后期改界面
				if((alertGoods=depotService.isAlert())!=null) {
					new JOptionPane().showMessageDialog(MainUI.this, "库存警报！");
					for(Goods g : alertGoods)
						text.append(g.getId()+"，商品名："+g.getName()+"，库存剩余："+g.getTempNum()+"，需要补货！\n");
					new JOptionPane().showMessageDialog(MainUI.this, text.toString());
				}
				else
					new JOptionPane().showMessageDialog(MainUI.this, "没啥东西！");
				
			}
		});
		/*
		 * 日期控件测试 
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
	
	//测试
	public static void main(String[] args) {
		new AdminService().Login("admin", "123");
		new MainUI();
	}

}
