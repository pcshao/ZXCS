package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import bean.Customer;

import dao.CustomDao;
import dao.GoodsDao;
import dao.SellOrdersDao;

import util.CastUtil;
import util.MyDateChooser;

//往来账务（客户）
public class CurrentAccountsModelWindow extends JDialog{
	MyDateChooser dc1,dc2,dc3,dc4,dc5,dc6,dc7,dc8;
	JTabbedPane tabbed;
	JPanel jp_p1,jp_p2,jp_p3,jp_p4;
	JPanel jp_p1_top,jp_p1_center;
	JPanel jp_p1_top_1,jp_p1_top_2;
	JPanel jp_p1_center_p1,jp_p1_center_p2;
	JPanel jp_p1_center_p2_top,jp_p1_center_p2_center;
	JPanel jp_tabbed_center_p1,jp_tabbed_center_p2;
	JButton btn_p1_1,btn_p1_2,btn_p1_3,btn_p1_4,btn_p1_5,btn_p1_6,btn_p1_7,btn_p1_8;
	JTextField jf_date1,jf_date2,tf_name;
	JButton btn_look,btn_check;
	JRadioButton rbtn_date,rbtn_type;
	ButtonGroup bg;
	Vector columnNames1,columnNames2,columnNames3;
	Vector<Vector> data1,data2,data3;
	DefaultTableModel table1model,table2model,table3model;
	JTable table1,table2,table3;
	JTabbedPane tabbed_center;
	String[] arr1={"客户名称","日期","单号","应收金额","实收金额","欠款金额","经办人","操作员","备注"};
	String[] arr2={"商品编号","商品名称","单位","单价","数量","总金额","规格型号"};
	String[] arr3={"客户名称","日期","单号","类型","收款金额","经办人","操作员","备注"};
	
	JPanel jp_p2_top,jp_p2_center;
	JPanel jp_p2_top_p1,jp_p2_top_p2;
	JPanel jp_p2_center_p1,jp_p2_center_p2;
	JButton btn_p2_1,btn_p2_2,btn_p2_3,btn_p2_4; 	
	JTextField tf_p2_1,tf_p2_2,tf_p2_3;
	JButton btn_p2_5,btn_p2_6;
	Vector columnNames4,columnNames5;
	Vector<Vector> data4,data5;
	DefaultTableModel table4model,table5model;
	JTable table4,table5;
	String[] arr4={"商品编号","商品名称","单位","销售数量","销售总金额","规格型号","备注"};
	String[] arr5={"客户名称","单据号","开单日期","单位","单价","数量","总金额","规格型号","颜色"};
	
	JPanel jp_p3_top,jp_p3_center;
	JButton btn_p3_1,btn_p3_2,btn_p3_3,btn_p3_4,btn_p3_5;
	JPanel jp_p3_top_p1,jp_p3_top_p2;
	JTextField tf_p3_1,tf_p3_2,tf_p3_3;
	Vector columnNames6;
	Vector<Vector> data6;
	DefaultTableModel table6model;
	JTable table6;
	String[] arr6={"客户名称","商品销售额（商品退货额）","合计金额","我方应收金额","我方实收金额","未收金额"};
	
	JPanel jp_p4_top,jp_p4_center;
	JButton btn_p4_1,btn_p4_2,btn_p4_3,btn_p4_4,btn_p4_5,btn_p4_6;
	JPanel jp_p4_top_p1,jp_p4_top_p2;
	JTextField tf_p4_1,tf_p4_2,tf_p4_3;
	Vector columnNames7;
	Vector<Vector> data7;
	DefaultTableModel table7model;
	JTable table7;
	String[] arr7={"单据编号","单据日期","收款方式","客户名称","经办人","操作员","备注"};
	

	 Customer ret1,ret2,ret3,ret4;		//返回的客户客户
	
	public CurrentAccountsModelWindow(){
		dc1=MyDateChooser.getInstance("yyyy-MM-dd");
		dc2=MyDateChooser.getInstance("yyyy-MM-dd");
		dc3=MyDateChooser.getInstance("yyyy-MM-dd");
		dc4=MyDateChooser.getInstance("yyyy-MM-dd");
		dc5=MyDateChooser.getInstance("yyyy-MM-dd");
		dc6=MyDateChooser.getInstance("yyyy-MM-dd");
		dc7=MyDateChooser.getInstance("yyyy-MM-dd");
		dc8=MyDateChooser.getInstance("yyyy-MM-dd");
		tabbed=new JTabbedPane();
		jp_p1=new JPanel();//客户所有单据
		jp_p2=new JPanel();//客户消费情况
		jp_p3=new JPanel();//客户账户信息
		jp_p4=new JPanel();//客户付款明细
		jp_p1_top=new JPanel();
		jp_p1_center=new JPanel();
		btn_p1_1=new JButton("我方收款");
		btn_p1_2=new JButton("查看单据");
		btn_p1_3=new JButton("删除单据");
		btn_p1_4=new JButton("整单退货");
		btn_p1_5=new JButton("单据过滤");
		btn_p1_6=new JButton("导出");
		btn_p1_7=new JButton("打印");
		btn_p1_8=new JButton("退出");
		jp_p1_top_1=new JPanel();
		jp_p1_top_2=new JPanel();
		jf_date1=new JTextField(10);
		jf_date2=new JTextField(10);
		tf_name=new JTextField(8);
		btn_look=new JButton("查客户");
		btn_check=new JButton("查询");
		bg=new ButtonGroup();
		rbtn_date=new JRadioButton("按日期排序");
		rbtn_type=new JRadioButton("按类型排序");
		jp_p1_center_p1=new JPanel();
		jp_p1_center_p2=new JPanel();
		//表1
		columnNames1=new Vector();
		data1=new Vector<Vector>();
		for (String str:arr1) {
			columnNames1.add(str);
		}
		table1model=new DefaultTableModel(data1,columnNames1);
		/**
		 * 把表格设置为不可编辑的
		 * 重写isCellEditable方法
		 * @author yc
		 */
		table1=new JTable(table1model)
		{
			public boolean isCellEditable(int row,int clumn){
				return false;
			}
		};;
		//表2
		columnNames2=new Vector();
		data2=new Vector<Vector>();
		for (String str:arr2) {
			columnNames2.add(str);
		}
		table2model=new DefaultTableModel(data2,columnNames2);
		table2=new JTable(table2model);
		//表3
		table3=new JTable(table3model);
		columnNames3=new Vector();
		data3=new Vector<Vector>();
		for (String str:arr3) {
			columnNames3.add(str);
		}
		table3model=new DefaultTableModel(data3,columnNames3);
		table3=new JTable(table3model);
		jp_p1_center_p2_top=new JPanel();
		jp_p1_center_p2_center=new JPanel();
		tabbed_center=new JTabbedPane();
		jp_tabbed_center_p1=new JPanel();
		jp_tabbed_center_p2=new JPanel();
		
		jp_p2_top=new JPanel();
		jp_p2_center=new JPanel();
		jp_p2_top_p1=new JPanel();
		jp_p2_top_p2=new JPanel();
		btn_p2_1=new JButton("高级查询");
		btn_p2_2=new JButton("导出");
		btn_p2_3=new JButton("打印");
		btn_p2_4=new JButton("退出");
		tf_p2_1=new JTextField(10);
		tf_p2_2=new JTextField(10);
		tf_p2_3=new JTextField(10);
		btn_p2_5=new JButton("查名称");
		btn_p2_6=new JButton("查询");
		jp_p2_center_p1=new JPanel();
		jp_p2_center_p2=new JPanel();
		//表4
		table4=new JTable(table4model);
		columnNames4=new Vector();
		data4=new Vector<Vector>();
		for (String str:arr4) {
			columnNames4.add(str);
		}
		table4model=new DefaultTableModel(data4,columnNames4);
		table4=new JTable(table4model);
		//表5
		table5=new JTable(table5model);
		columnNames5=new Vector();
		data5=new Vector<Vector>();
		for (String str:arr5) {
			columnNames5.add(str);
		}
		table5model=new DefaultTableModel(data5,columnNames5);
		table5=new JTable(table5model);
		
		jp_p3_top=new JPanel();
		jp_p3_center=new JPanel();
		jp_p3_top_p1=new JPanel();
		jp_p3_top_p2=new JPanel();
		btn_p3_1=new JButton("导出");
		btn_p3_2=new JButton("打印");
		btn_p3_3=new JButton("退出");
		tf_p3_1=new JTextField(10);
		tf_p3_2=new JTextField(10);
		tf_p3_3=new JTextField(10);
		btn_p3_4=new JButton("查名称");
		btn_p3_5=new JButton("查询");
		//表6
		table6=new JTable(table6model);
		columnNames6=new Vector();
		data6=new Vector<Vector>();
		for (String str:arr6) {
			columnNames6.add(str);
		}
		table6model=new DefaultTableModel(data6,columnNames6);
		table6=new JTable(table6model);
		
		jp_p4_top=new JPanel();
		jp_p4_center=new JPanel();
		jp_p4_top_p1=new JPanel();
		jp_p4_top_p2=new JPanel();
		btn_p4_1=new JButton("查看单据");
		btn_p4_2=new JButton("导出");
		btn_p4_3=new JButton("打印");
		btn_p4_4=new JButton("退出");
		tf_p4_1=new JTextField(10);
		tf_p4_2=new JTextField(10);
		tf_p4_3=new JTextField(10);
		btn_p4_5=new JButton("查名称");
		btn_p4_6=new JButton("查询");
		//表7
		table7=new JTable(table7model);
		columnNames7=new Vector();
		data7=new Vector<Vector>();
		for (String str:arr7) {
			columnNames7.add(str);
		}
		table7model=new DefaultTableModel(data7,columnNames7);
		table7=new JTable(table7model);
		/**
		 * 选项卡1
		 * @author yc
		 */
		tabbed.add("客户所有单据",jp_p1);
		jp_p1.setLayout(new BorderLayout());
		jp_p1_top.setLayout(new GridLayout(2,1));
		jp_p1_top_1.setLayout(new GridLayout(1,9));
		jp_p1_top_1.setBorder(BorderFactory.createTitledBorder(""));
		jp_p1_top_1.add(btn_p1_1);
		jp_p1_top_1.add(btn_p1_2);
		jp_p1_top_1.add(btn_p1_3);
		jp_p1_top_1.add(btn_p1_4);
		jp_p1_top_1.add(btn_p1_5);
		jp_p1_top_1.add(btn_p1_6);
		jp_p1_top_1.add(btn_p1_7);
		jp_p1_top_1.add(btn_p1_8);
		jp_p1_top_1.add(new JLabel());
		jp_p1_top.add(jp_p1_top_1);
		jp_p1_top_2.add(new JLabel("查询时间："));
		jf_date1.setText(dc1.getStrDate());
		dc1.register(jf_date1);
		jp_p1_top_2.add(jf_date1);
		jp_p1_top_2.add(new JLabel("至"));
		jf_date2.setText(dc2.getStrDate());
		dc2.register(jf_date2);
		jp_p1_top_2.add(jf_date2);
		jp_p1_top_2.add(new JLabel("客户名称："));
		ret1=new CastUtil().vectorToCustomer(new CastUtil().objectToVector(new CustomDao().getCustomers()).get(0));
		tf_name.setText(ret1.getName());
		tf_name.setEditable(false);
		jp_p1_top_2.add(tf_name);
		jp_p1_top_2.add(btn_look);
		jp_p1_top_2.add(btn_check);
		bg.add(rbtn_date);
		bg.add(rbtn_type);
		jp_p1_top_2.add(rbtn_date);
		jp_p1_top_2.add(rbtn_type);
		jp_p1_top.add(jp_p1_top_2);
		jp_p1.add(jp_p1_top,BorderLayout.NORTH);
		jp_p1_center.setLayout(new GridLayout(2,1));
		jp_p1_center.add(jp_p1_center_p1);
		jp_p1_center_p1.setLayout(new GridLayout(1,1));
		jp_p1_center_p1.add(new JScrollPane(table1));
		jp_p1_center.add(jp_p1_center_p2);
		jp_p1_center_p2.setLayout(new BorderLayout());
		jp_p1_center_p2_top.setLayout(new GridLayout(1,7));
		jp_p1_center_p2_top.add(new JLabel("单据的详细信息："));
		jp_p1_center_p2_top.add(new JLabel());
		jp_p1_center_p2_top.add(new JLabel());
		jp_p1_center_p2_top.add(new JLabel());
		jp_p1_center_p2_top.add(new JLabel());
		jp_p1_center_p2_top.add(new JLabel());
		jp_p1_center_p2_top.add(new JLabel());
		jp_p1_center_p2.add(jp_p1_center_p2_top,BorderLayout.NORTH);
		jp_tabbed_center_p1.setLayout(new GridLayout(1,1));
		jp_tabbed_center_p1.add(new JScrollPane(table2));
		tabbed_center.add("单据商品信息",jp_tabbed_center_p1);
		jp_tabbed_center_p2.setLayout(new GridLayout(1,1));
		jp_tabbed_center_p2.add(new JScrollPane(table3));
		tabbed_center.add("单据付款信息",jp_tabbed_center_p2);
		jp_p1_center_p2_center.setLayout(new GridLayout(1,1));
		jp_p1_center_p2_center.add(tabbed_center);
		jp_p1_center_p2.add(jp_p1_center_p2_center,BorderLayout.CENTER);
		jp_p1_center.setBorder(BorderFactory.createTitledBorder("往来帐务列表"));
		jp_p1.add(jp_p1_center,BorderLayout.CENTER);
		/**
		 * 选项卡2
		 * @author yc
		 */
		tabbed.add("客户消费情况",jp_p2);
		jp_p2.setLayout(new BorderLayout());
		jp_p2_top.setLayout(new GridLayout(2,1));
		jp_p2_top_p1.setLayout(new GridLayout(1,8));
		jp_p2_top_p1.setBorder(BorderFactory.createTitledBorder(""));
		jp_p2_top_p1.add(btn_p2_1);
		jp_p2_top_p1.add(btn_p2_2);
		jp_p2_top_p1.add(btn_p2_3);
		jp_p2_top_p1.add(btn_p2_4);
		jp_p2_top_p1.add(new JLabel());
		jp_p2_top_p1.add(new JLabel());
		jp_p2_top_p1.add(new JLabel());
		jp_p2_top_p1.add(new JLabel());
		jp_p2_top.add(jp_p2_top_p1);
		jp_p2_top_p2.add(new JLabel("查询时间："));
		tf_p2_1.setText(dc3.getStrDate());
		dc3.register(tf_p2_1);
		jp_p2_top_p2.add(tf_p2_1);
		jp_p2_top_p2.add(new JLabel("至"));
		tf_p2_2.setText(dc4.getStrDate());
		dc4.register(tf_p2_2);
		jp_p2_top_p2.add(tf_p2_2);
		jp_p2_top_p2.add(new JLabel("客户名称"));
		ret2=new CastUtil().vectorToCustomer(new CastUtil().objectToVector(new CustomDao().getCustomers()).get(0));
		tf_p2_3.setText(ret2.getName());
		tf_p2_3.setEditable(false);
		jp_p2_top_p2.add(tf_p2_3);
		jp_p2_top_p2.add(btn_p2_5);
		jp_p2_top_p2.add(btn_p2_6);
		jp_p2_top.add(jp_p2_top_p2);
		jp_p2.add(jp_p2_top,BorderLayout.NORTH);
		jp_p2_center.setBorder(BorderFactory.createTitledBorder("商品销售总汇"));
		jp_p2_center.setLayout(new GridLayout(2,1));
		jp_p2_center_p1.setLayout(new GridLayout(1,1));
		jp_p2_center_p1.add(new JScrollPane(table4));
		jp_p2_center.add(jp_p2_center_p1);
		jp_p2_center_p2.setLayout(new GridLayout(1,1));
		jp_p2_center_p2.add(new JScrollPane(table5));
		jp_p2_center.add(jp_p2_center_p2);
		jp_p2.add(jp_p2_center,BorderLayout.CENTER);
		/**
		 * 选项卡3
		 * @author yc
		 */
		tabbed.add("客户账户信息",jp_p3);
		jp_p3.setLayout(new BorderLayout());
		jp_p3_top.setLayout(new GridLayout(2,1));
		jp_p3_top_p1.setLayout(new GridLayout(1,8));
		jp_p3_top_p1.setBorder(BorderFactory.createTitledBorder(""));
		jp_p3_top_p1.add(btn_p3_1);
		jp_p3_top_p1.add(btn_p3_2);
		jp_p3_top_p1.add(btn_p3_3);
		jp_p3_top_p1.add(new JLabel());
		jp_p3_top_p1.add(new JLabel());
		jp_p3_top_p1.add(new JLabel());
		jp_p3_top_p1.add(new JLabel());
		jp_p3_top_p1.add(new JLabel());
		jp_p3_top.add(jp_p3_top_p1);
		jp_p3_top_p2.add(new JLabel("统计时间："));
		tf_p3_1.setText(dc5.getStrDate());
		dc5.register(tf_p3_1);
		jp_p3_top_p2.add(tf_p3_1);
		jp_p3_top_p2.add(new JLabel("至"));
		tf_p3_2.setText(dc6.getStrDate());
		dc6.register(tf_p3_2);
		jp_p3_top_p2.add(tf_p3_2);
		jp_p3_top_p2.add(new JLabel("客户名称"));
		ret3=new CastUtil().vectorToCustomer(new CastUtil().objectToVector(new CustomDao().getCustomers()).get(0));
		tf_p3_3.setText(ret3.getName());
		tf_p3_3.setEditable(false);
		jp_p3_top_p2.add(tf_p3_3);
		jp_p3_top_p2.add(btn_p3_4);
		jp_p3_top_p2.add(btn_p3_5);
		jp_p3_top.add(jp_p3_top_p2);
		jp_p3.add(jp_p3_top,BorderLayout.NORTH);
		jp_p3_center.setLayout(new GridLayout(1,1));
		jp_p3_center.add(new JScrollPane(table6));
		jp_p3.add(jp_p3_center,BorderLayout.CENTER);
		/**
		 * 选项卡4
		 * @author yc
		 */
		tabbed.add("客户付款明细",jp_p4);
		jp_p4.setLayout(new BorderLayout());
		jp_p4_top.setLayout(new GridLayout(2,1));
		jp_p4_top_p1.setLayout(new GridLayout(1,8));
		jp_p4_top_p1.setBorder(BorderFactory.createTitledBorder(""));
		jp_p4_top_p1.add(btn_p4_1);
		jp_p4_top_p1.add(btn_p4_2);
		jp_p4_top_p1.add(btn_p4_3);
		jp_p4_top_p1.add(btn_p4_4);
		jp_p4_top_p1.add(new JLabel());
		jp_p4_top_p1.add(new JLabel());
		jp_p4_top_p1.add(new JLabel());
		jp_p4_top_p1.add(new JLabel());
		jp_p4_top_p1.add(new JLabel());
		jp_p4_top.add(jp_p4_top_p1);
		jp_p4_top_p2.add(new JLabel("查询时间："));
		tf_p4_1.setText(dc7.getStrDate());
		dc7.register(tf_p4_1);
		jp_p4_top_p2.add(tf_p4_1);
		jp_p4_top_p2.add(new JLabel("至"));
		tf_p4_2.setText(dc8.getStrDate());
		dc8.register(tf_p4_2);
		jp_p4_top_p2.add(tf_p4_2);
		jp_p4_top_p2.add(new JLabel("客户名称"));
		ret4=new CastUtil().vectorToCustomer(new CastUtil().objectToVector(new CustomDao().getCustomers()).get(0));
		tf_p4_3.setText(ret4.getName());
		tf_p4_3.setEditable(false);
		jp_p4_top_p2.add(tf_p4_3);
		jp_p4_top_p2.add(btn_p4_5);
		jp_p4_top_p2.add(btn_p4_6);
		jp_p4_top.add(jp_p4_top_p2);
		jp_p4.add(jp_p4_top,BorderLayout.NORTH);
		jp_p4_center.setLayout(new GridLayout(1,1));
		jp_p4_center.add(new JScrollPane(table7));
		jp_p4.add(jp_p4_center,BorderLayout.CENTER);
		/**
		 * 监听方法
		 * @author yc
		 */
		/**
		 * 4个选项卡查客户名称的点击事件
		 * 得到客户的相关信息
		 * @author yc
		 * 
		 */
			btn_look.addActionListener(new ActionListener() {//选项卡一
				public void actionPerformed(ActionEvent e) {
					CustomInfoModelWindow cimw = new CustomInfoModelWindow();
					ret1=cimw.ret;
				try {
					if(cimw.table.isRowSelected(cimw.table.getSelectedRow())){
						ret1 = new CastUtil().vectorToCustomer(cimw.retData);
						tf_name.setText(ret1.getName());
					}else{
						tf_name.setText(cimw.ret.getName());
					}
					
				} catch (java.lang.NullPointerException e2) {
					//JOptionPane.showMessageDialog(null, "请选择数据");
				}
				}
			});
			btn_p2_5.addActionListener(new ActionListener() {//选项卡2
				public void actionPerformed(ActionEvent e) {
					CustomInfoModelWindow cimw = new CustomInfoModelWindow();
					ret2=cimw.ret;
				try {
					if(cimw.table.isRowSelected(cimw.table.getSelectedRow())){
						ret2 = new CastUtil().vectorToCustomer(cimw.retData);
						tf_p2_3.setText(ret2.getName());
					}else{
						tf_p2_3.setText(cimw.ret.getName());
					}
					
				} catch (java.lang.NullPointerException e2) {
					//JOptionPane.showMessageDialog(null, "请选择数据");
				}
				}
			});
			btn_p3_4.addActionListener(new ActionListener() {//选项卡3
				public void actionPerformed(ActionEvent e) {
					CustomInfoModelWindow cimw = new CustomInfoModelWindow();
					ret3=cimw.ret;
				try {
					if(cimw.table.isRowSelected(cimw.table.getSelectedRow())){
						ret3 = new CastUtil().vectorToCustomer(cimw.retData);
						tf_p3_3.setText(ret3.getName());
					}else{
						tf_p3_3.setText(cimw.ret.getName());
					}
					
				} catch (java.lang.NullPointerException e2) {;
					//JOptionPane.showMessageDialog(null, "请选择数据");
				}
				}
			});
			btn_p4_5.addActionListener(new ActionListener() {//选项卡4
				public void actionPerformed(ActionEvent e) {
					CustomInfoModelWindow cimw = new CustomInfoModelWindow();
					ret4=cimw.ret;
				try {
					if(cimw.table.isRowSelected(cimw.table.getSelectedRow())){
						ret4 = new CastUtil().vectorToCustomer(cimw.retData);
						tf_p4_3.setText(ret4.getName());
					}else{
						tf_p4_3.setText(cimw.ret.getName());
					}
					
				} catch (java.lang.NullPointerException e2) {;
					//JOptionPane.showMessageDialog(null, "请选择数据");
				}
				}
			});
			
			/**
			 * 查询监听事件
			 * btn_check
			 * 查询全部的客户所有单据
			 * 一共为4个选项卡的查询点击事件
			 * 获取数据库中的相关信息
			 * @author yc
			 */
			btn_check.addActionListener(new ActionListener() {//选项卡1
				public void actionPerformed(ActionEvent e) {
					String str1=jf_date1.getText().trim();
					String str2=jf_date2.getText().trim();
					data1=new SellOrdersDao().getSellOrdersInfo(str1, str2);
					table1model=new DefaultTableModel(data1,columnNames1);
					table1.setModel(table1model);
					table1.updateUI();
				}
			});
			btn_p2_6.addActionListener(new ActionListener() {//选项卡2
				public void actionPerformed(ActionEvent e) {
					data4=new GoodsDao().getGoodsInToAccount();
					table4model=new DefaultTableModel(data4,columnNames4);
					table4.setModel(table4model);
					table4.updateUI();
				}
			});
			btn_p3_5.addActionListener(new ActionListener() {//选项卡3
				public void actionPerformed(ActionEvent e) {
					data6=new CustomDao().getCustomAccount();
					table6model=new DefaultTableModel(data6,columnNames6);
					table6.setModel(table6model);
					table6.updateUI();
				}
			});
			btn_p4_6.addActionListener(new ActionListener() {//选项卡4
				public void actionPerformed(ActionEvent e) {
					data7=new CustomDao().getCustomPayInfo();
					table7model=new DefaultTableModel(data7,columnNames7);
					table7.setModel(table7model);
					table7.updateUI();
				}
			});
			
			/**
			 * 监听文本框内容变化
			 * 根据文本框的内容查找相关信息
			 * @author yc
			 */
			tf_name.getDocument().addDocumentListener(new DocumentListener() {
				public void removeUpdate(DocumentEvent e) {
					data1=new SellOrdersDao().getSellOrdersAccountAllByCustom(ret1);
					table1model=new DefaultTableModel(data1,columnNames1);
					table1.setModel(table1model);
					table1.updateUI();
				}
				public void insertUpdate(DocumentEvent e) {}
				public void changedUpdate(DocumentEvent e) {	
				}
			});
			tf_p2_3.getDocument().addDocumentListener(new DocumentListener() {
				public void removeUpdate(DocumentEvent e) {
					data6=new CustomDao().getCustomAccount();
					table6model=new DefaultTableModel(data6,columnNames6);
					table6.setModel(table1model);
					table6.updateUI();
				}
				public void insertUpdate(DocumentEvent e) {}
				public void changedUpdate(DocumentEvent e) {	
				}
			});
			
			/**
			 * 对表进行鼠标监听
			 * 点击表中所选的内容
			 * 在下表展示相关信息
			 * @author yc
			 */
			table1.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {}
				public void mousePressed(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {
					if(e.getButton()==1&&e.getClickCount()==2){
						String str =data1.get(table1.getSelectedRow()).get(2).toString();
						data2=new GoodsDao().getGoodsInToAccount(str);
						table2model=new DefaultTableModel(data2,columnNames2);
						table2.setModel(table2model);
						table2.updateUI();
					}
					
				}
			});
			//退出
			btn_p1_8.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				CurrentAccountsModelWindow.this.setVisible(false);
				}
			});
			btn_p2_4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				CurrentAccountsModelWindow.this.setVisible(false);
				}
			});
			btn_p3_3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				CurrentAccountsModelWindow.this.setVisible(false);
				}
			});
			btn_p4_4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				CurrentAccountsModelWindow.this.setVisible(false);
				}
			});
		this.add(tabbed);
		this.setTitle("往来账务（客户）");	
		this.setBounds(300, 100, 850, 550);
		this.setModal(true);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new CurrentAccountsModelWindow();
	}
}
