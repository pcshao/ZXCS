package gui.zw.warehouse;

import gui.MainUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import bean.Goods;

import dao.DepotsDao;
import dao.GoodsDao;


import service.DepotService;
import util.CastUtil;
import util.MyDateChooser;

public class WarningWindow extends JFrame{
	JTabbedPane tp1,tp2,tp3,tp4;//选项卡
	JPanel jp_center,jp_top,jp_low;
	JPanel jp_center_top,jp_center_low,
	jp_top_center_top,jp_low_low,jp_low_center,jp_top_top,jp_top_low,jp_top_center;		
	JTextField tf_3,tf_4,tf_5,tf_6,tf2_3;
	Vector items=new Vector();
	JButton btn_1,btn_4,btn_5,btn_6,btn_7,btn_9;
	JButton btn1_1,btn1_2;
	//第一个表格
	JTable table;
	Vector<Vector> rowData=new Vector<Vector>();	
	Vector columnNames=new Vector();
	/*Vector rd1=new Vector();
	Vector rd2=new Vector();
	Vector rd3=new Vector();*/
	DefaultTableModel model;
	//第二个表格
	JTable table1;
	Vector<Vector> rowData1=new Vector<Vector>();
	Vector columnNames1=new Vector();
	DefaultTableModel model1;
	
	GoodsDao gdao;
	MyDateChooser dc1,dc2_1,dc2_2;
	//注册服务
	DepotService depotService;
	DepotsDao ddao;
	
	public WarningWindow (){
		
		depotService = new DepotService();
		gdao=new GoodsDao();
		ddao=new DepotsDao();
		
		
		dc1=MyDateChooser.getInstance("yyyy-MM-dd");
		dc2_1=MyDateChooser.getInstance("yyyy-MM-dd");
		dc2_2=MyDateChooser.getInstance("yyyy-MM-dd");
		
		btn_1=new JButton("发出警报");
		btn_4=new JButton("导出");
		btn_5=new JButton("打印 ");
		btn_6=new JButton("退出 ");btn_7=new JButton("查询 ");
		btn1_1=new JButton("确定");
		btn1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn1_2.doClick();
			}
		});
		btn1_2=new JButton("退出");
		btn1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WarningWindow.this.setVisible(false);
			}
		});tf_3=new JTextField(10);
		tf_4=new JTextField(8);tf_5=new JTextField(50);tf_6=new JTextField(20);
		tf2_3=new JTextField(10);
		jp_center=new JPanel();jp_top=new JPanel();jp_low=new JPanel();
		jp_center_top=new JPanel();jp_center_low=new JPanel();jp_low_low=new JPanel();jp_low_center=new JPanel();
		jp_top_top=new JPanel();jp_top_low=new JPanel();jp_top_center=new JPanel();
		jp_top_center_top=new JPanel();
		
		//top添加按钮
		//btn_1.setBackground(Color.red);
		jp_top_top.add(btn_1);
		jp_top_top.add(btn_6);
		//第一个表格
		columnNames.add("编号");
		columnNames.add("商品编号");	
		columnNames.add("商品名称");		
		columnNames.add("所在仓库");
		columnNames.add("库存量");		
		columnNames.add("最低库存");//goods  中alertnum
		model=new DefaultTableModel(gdao.getgoodstoreInfo(), columnNames);
		table=new JTable(model);
		//第二个表格
	
		columnNames1.add("商品名称");
		columnNames1.add("单位");
		columnNames1.add("类别");
		columnNames1.add("规格型号");
		columnNames1.add("进价");
		columnNames1.add("售价");
		columnNames1.add("备注");
		columnNames1.add("报警库存");
		//这里是gdao.getProductInfo()
		model1=new DefaultTableModel(gdao.getProductInfo(), columnNames1);
		table1=new JTable(model1);
	
		//top面板再分
		jp_top.setLayout(new BorderLayout());
		jp_top.add(jp_top_top,BorderLayout.NORTH);
		
		jp_top.add(jp_top_center, BorderLayout.CENTER);
		jp_top_center.setBorder(BorderFactory.createTitledBorder("查看库存数量"));
		jp_top_center.setLayout(new BorderLayout());
		jp_top_center.add(jp_top_center_top,BorderLayout.NORTH);
		jp_top_center_top.add(new JLabel("查询商品信息"));
		jp_top_center_top.add(tf2_3);jp_top_center_top.add(btn_7);
		
		//jp_center.add(jp_center_top,BorderLayout.NORTH);
		jp_top_center.add(new JScrollPane(table),BorderLayout.CENTER);
		
		jp_low.setBorder(BorderFactory.createTitledBorder("看下进了神马货"));
		jp_low.setLayout(new BorderLayout());
		jp_low.add(new JScrollPane(table1),BorderLayout.CENTER);
		jp_low.add(jp_low_low,BorderLayout.SOUTH);
		jp_low_low.add(btn1_1);jp_low_low.add(btn1_2);
		
		
		//添加事件
		btn_1.addActionListener(new ActionListener() {
			//警报事件
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Goods> alertGoods = new ArrayList<Goods>();
				StringBuilder text = new StringBuilder();
				if((alertGoods=depotService.isAlert())!=null) {
					new JOptionPane().showMessageDialog(WarningWindow.this, "库存警报！");
					for(Goods g : alertGoods)
						text.append(g.getId()+"，商品名："+g.getName()+"，库存剩余："+g.getTempNum()+"，需要补货！\n");
					new JOptionPane().showMessageDialog(WarningWindow.this, text.toString());
				}
				else
					new JOptionPane().showMessageDialog(WarningWindow.this, "没啥东西！");
			}
		});
		btn_1.addMouseListener(new MouseListener() {			
		//警报颜色事件
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
				btn_1.setBackground(Color.RED);
			}
			public void mouseExited(MouseEvent e) {
				btn_1.setBackground(Color.white);				
			}
			public void mouseEntered(MouseEvent e) {				
			}
			public void mouseClicked(MouseEvent e) {					
			}
		});
		
		btn_4.addActionListener(new ActionListener() {
			//事件   导入到文件夹
			@Override
			public void actionPerformed(ActionEvent e) {
								
			}
		});
		btn_6.addActionListener(new ActionListener() {
			//退出事件
			@Override
			public void actionPerformed(ActionEvent e) {
				WarningWindow.this.setVisible(false);
			}
		});
		btn_7.addActionListener(new ActionListener() {
			//查询 库存
			@Override
			public void actionPerformed(ActionEvent e) {
				String info=tf2_3.getText().trim();
				model=new DefaultTableModel(gdao.getgoodstoreInfoByIdOrName(info), columnNames);
				table.setModel(model);
				table.updateUI();
			}
		});
		
		
		
		//整块面板网格布局分界
		getContentPane().add(jp_top);getContentPane().add(jp_low);
		getContentPane().setLayout(new GridLayout(2,1));
		this.setTitle("库存警报");			
		this.setBounds(100, 100, 800, 600);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new WarningWindow();
	}
	

}
