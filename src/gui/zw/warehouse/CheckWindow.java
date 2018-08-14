package gui.zw.warehouse;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.GoodsDao;
import gui.zw.modewindow.*;
import util.MyDateChooser;

public class CheckWindow extends JFrame {
	public static void main(String[] args) {
		new CheckWindow();
	}
	JTabbedPane jtp;//选项卡
	JPanel jp_center,jp_top,jp_low,jtp_2,jtp_3,jtp_1;
	JPanel jp_center_top,jp_center_low,jp_low_top,jp_low_low,
	jp_low_center,jp_top_top,jp_top_low,jp_top_center,jp_top_center_top;		
	JTextField tf_1,tf_2,tf_3,tf_4,tf_5,tf_6;
	//Vector items=new Vector();
	JButton btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9;
	JButton btn1_1,btn1_2;
	//第二个界面
	Vector items=new Vector();
	JComboBox  cmbx;
	JPanel jp2_center,jp2_top,jp2_low,jtp2_2,jtp2_3,jtp2_1;
	JPanel jp2_center_top,jp2_center_low,jp2_low_top,jp2_low_low,
	jp2_low_center,jp2_top_top,jp2_top_low,jp2_top_center,jp2_top_center_top;		
	JTextField tf2_1,tf2_2,tf2_3,tf2_4,tf2_5,tf2_6,tf2_7;
	//Vector items=new Vector();
	JButton btn2_1,btn2_2,btn2_3,btn2_4,btn2_5,btn2_6,btn2_7,btn2_8,btn2_9;
	//第一个表格
	JTable table;
	Vector<Vector> rowData=new Vector<Vector>();	
	Vector columnNames=new Vector();	
	DefaultTableModel model;
	//第二个表格
	JTable table1;
	Vector<Vector> rowData1=new Vector<Vector>();
	Vector columnNames1=new Vector();
	DefaultTableModel model1;
	//第三个表格
	JTable table2;
	Vector<Vector> rowData2=new Vector<Vector>();	
	Vector columnNames2=new Vector();	
	DefaultTableModel model2;
	//第四个表格
	JTable table3;
	Vector<Vector> rowData3=new Vector<Vector>();
	Vector columnNames3=new Vector();
	DefaultTableModel model3;
	MyDateChooser dc1_1,dc1_2,dc2_2;
	GoodsDao gdao=null;
	public CheckWindow(){
		gdao=new GoodsDao();
		
		btn_1=new JButton("开始盘点");
		btn_2=new JButton("查看单据 ");
		btn_3=new JButton("导出excel");
		btn_4=new JButton("退出");
		btn_5=new JButton("新增盘点单");
		btn_6=new JButton("修改盘点单");
		btn_7=new JButton("删除盘点单");
		btn_8=new JButton("高级查询");
		btn_9=new JButton("查询 ");
		
		btn1_1=new JButton("确定");
		btn1_2=new JButton("退出");
		
		btn2_1=new JButton("打印");
		btn2_2=new JButton("查看单据 ");
		btn2_3=new JButton("导出excel");
		btn2_4=new JButton("退出");
		btn2_5=new JButton("查询");	
		btn2_6=new JButton("查找");
		btn2_7=new JButton("确定");
		btn2_8=new JButton("退出");
		
	
		
		dc1_1=MyDateChooser.getInstance("yyyy-MM-dd");
		dc1_2=MyDateChooser.getInstance("yyyy-MM-dd");
		dc2_2=MyDateChooser.getInstance("yyyy-MM-dd");
		
			
		tf_1=new JTextField(10);tf_2=new JTextField(10);tf_3=new JTextField(10);
		tf_4=new JTextField(8);tf_5=new JTextField(50);tf_6=new JTextField(20);
		jp_center=new JPanel();jp_top=new JPanel();jp_low=new JPanel();
		jp_center_top=new JPanel();jp_center_low=new JPanel();
		jp_low_top=new JPanel();jp_low_low=new JPanel();jp_low_center=new JPanel();
		jp_top_top=new JPanel();jp_top_low=new JPanel();jp_top_center=new JPanel();
		jp_top_center_top=new JPanel();jp_low_low=new JPanel();
		
		jtp=new JTabbedPane();
		jtp_1=new JPanel();jtp_2=new JPanel();
		
		//top添加按钮
		jp_top_top.add(btn_1);jp_top_top.add(btn_2);jp_top_top.add(btn_3);
		jp_top_top.add(btn_4);jp_top_center_top.add(btn_5);jp_top_center_top.add(btn_6);
		jp_top_center_top.add(btn_7);jp_top_center_top.add(btn_8);
		//第一个表格
		columnNames.add("盘点单据号");
		columnNames.add("名称");
		columnNames.add("仓库名称");
		columnNames.add("数量");
		//columnNames.add("商品条数");
		//columnNames.add("商品数量");
		columnNames.add("备注");
		//gdao.getpdOrders()rowData
		model=new DefaultTableModel(gdao.getGoods(), columnNames);
		table=new JTable(model);
		//第二个表格
		columnNames1.add("编号");	
		columnNames1.add("盘点单号");
		columnNames1.add("商品编号");	
		//gdao.getpdOrdersDetails()
		model1=new DefaultTableModel(rowData1, columnNames1);
		table1=new JTable(model1);
	
		//top面板再分
		jp_top.setLayout(new BorderLayout());
		jp_top.add(jp_top_top,BorderLayout.NORTH);
		jp_top.add(jp_top_center,BorderLayout.CENTER);
		jp_top_center.setBorder(BorderFactory.createTitledBorder("盘点单据列表"));
		jp_top_center.setLayout(new BorderLayout());
		
		jp_top_center.add(jp_top_center_top,BorderLayout.NORTH);
		jp_top_center.add(new JScrollPane(table),BorderLayout.CENTER);
		//jp_top.add(new Label("合计0"),BorderLayout.SOUTH);
		
		//low面板再分
		jp_low.setBorder(BorderFactory.createTitledBorder("盘点商品明细"));
		jp_low_top.add(new Label("你可以查下盘点编号："));jp_low_top.add(tf_1);
		
		jp_low_top.add(btn_9);
		jp_low.setLayout(new BorderLayout());
		jp_low.add(jp_low_top,BorderLayout.NORTH);
		jp_low.add(new JScrollPane(table1),BorderLayout.CENTER);
		jp_low.add(jp_low_low,BorderLayout.SOUTH);
		jp_low_low.add(btn1_1);jp_low_low.add(btn1_2);
		btn1_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CheckWindow.this.setVisible(false);
			}
		});
		//历史盘点查询
		//top添加按钮
		items.add("所有仓库");
		items.add("主仓库");
		items.add("饮料库");
		items.add("酒库");
		cmbx=new  JComboBox(items);
		
		tf2_1=new JTextField(8);tf2_2=new JTextField(10);tf2_3=new JTextField(10);
		tf2_4=new JTextField(8);tf2_5=new JTextField(8);tf2_6=new JTextField(20);
		tf2_7=new JTextField(8);
		jp2_center=new JPanel();jp2_top=new JPanel();jp2_low=new JPanel();
		jp2_center_top=new JPanel();jp2_center_low=new JPanel();
		jp2_low_top=new JPanel();jp2_low_low=new JPanel();jp2_low_center=new JPanel();
		jp2_top_top=new JPanel();jp2_top_low=new JPanel();jp2_top_center=new JPanel();
		jp2_top_center_top=new JPanel();
		
		
				jp2_top_top.add(btn2_1);jp2_top_top.add(btn2_2);jp2_top_top.add(btn2_3);
				jp2_top_top.add(btn2_4);
				jp2_top_center_top.add(new JLabel("查询日期："));
				//此处添加日期
				dc1_1.register(tf2_4);jp2_top_center_top.add(tf2_4);
				jp2_top_center_top.add(new JLabel("至"));
				dc1_2.register(tf2_5);jp2_top_center_top.add(tf2_5);
				jp2_top_center_top.add(new JLabel("仓库名称:"));jp2_top_center_top.add(cmbx);
				jp2_top_center_top.add(new JLabel("操作员"));jp2_top_center_top.add(tf2_1);
				jp2_top_center_top.add(btn2_5);jp2_top_center_top.add(tf2_7);
				//第三个表格
				columnNames2.add("盘点单号");
				columnNames2.add("日期");
				columnNames2.add("仓库名称");
				columnNames2.add("操作员");
				columnNames2.add("商品条数");
				columnNames2.add("商品数量");
				columnNames2.add("备注");
				model2=new DefaultTableModel(rowData2, columnNames2);
				table2=new JTable(model2);
				//第四个表格从商品 goods里面找
				columnNames3.add("编号");	
				columnNames3.add("名称");
				columnNames3.add("单位");
				columnNames3.add("规格型号");
				//columnNames3.add("");
				//columnNames3.add("生产厂商");
				columnNames3.add("仓库");
				//columnNames3.add("盘点数量");
				model3=new DefaultTableModel(rowData2, columnNames3);
				table3=new JTable(model3);
			
				//top面板再分
				jp2_top.setLayout(new BorderLayout());
				jp2_top.add(jp2_top_top,BorderLayout.NORTH);
				jp2_top.add(jp2_top_center,BorderLayout.CENTER);
				//jp2_top_center.setBorder(BorderFactory.createTitledBorder("盘点单列表"));
				jp2_top_center.setLayout(new BorderLayout());
				
				jp2_top_center.add(jp2_top_center_top,BorderLayout.NORTH);
				jp2_top_center.add(new JScrollPane(table2),BorderLayout.CENTER);
				//jp2_top.add(new Label("合计0"),BorderLayout.SOUTH);
				
				//low面板再分
				//jp2_low_top.add(new Label("商品批次盘点信息："));
				jp2_low.setBorder(BorderFactory.createTitledBorder("看下货啊"));
				jp2_low_top.add(new Label("可以查商品编号或者货物名称："));
				jp2_low_top.add(tf2_3);
				jp2_low_top.add(btn2_6);
				jp2_low.setLayout(new BorderLayout());
				jp2_low.add(jp2_low_top,BorderLayout.NORTH);
				jp2_low.add(new JScrollPane(table3),BorderLayout.CENTER);
				jp2_low.add(jp2_low_low,BorderLayout.SOUTH);
				jp2_low_low.add(btn2_7);jp2_low_low.add(btn2_8);
		
				btn_1.addActionListener(new ActionListener() {	
					//开始盘点
					public void actionPerformed(ActionEvent e) {
						new StartCheck();
						
					}
				});
				//添加事件
				btn_5.addActionListener(new ActionListener() {
					//新增盘点单
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						new  NewCheckWindow();
					}
				});
				btn_6.addActionListener(new ActionListener() {
					//修改盘点单
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						new CheckDiagWindow();
					}
				});
				btn_7.addActionListener(new ActionListener() {
					//移除盘点单
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						new CheckDiagWindow();
					}
				});
				//历史盘点查询
				btn2_5.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						
					}
				});
				btn2_6.addActionListener(new ActionListener() {
					//最后一个表格乱几把查下
				
					public void actionPerformed(ActionEvent e) {
						String info=tf2_3.getText().trim();
						model3=new DefaultTableModel(gdao.getgoodsthreequeery(info), columnNames);
						table3.setModel(model3);
						table3.updateUI();
						
					}
				});
		
				jtp.add("盘点列表",jtp_1);jtp.add("历史盘点查询",jtp_2);
				jtp_1.setLayout(new GridLayout(2,1));
				jtp_1.add(jp_top);jtp_1.add(jp_low);
				jtp_2.setLayout(new GridLayout(2,1));
				jtp_2.add(jp2_top);jtp_2.add(jp2_low);
				//jtp_2.add(jp_top);jtp_1.add(jp_low);
				this.add(jtp);
				this.setTitle("库存盘点");			
				this.setBounds(100, 100, 800, 600);
				this.setDefaultCloseOperation(HIDE_ON_CLOSE);
				this.setVisible(true);
		}

	}
