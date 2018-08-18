package gui.lxh;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import bean.Admin;
import bean.Depot;
import bean.Employee;
import bean.PayWay;
import bean.Supplier;
import bean.orders.InOrder;
import bean.orders.InOrder_tui;

import dao.DepotsDao;
import dao.EmployeesDao;
import dao.InOrderDao;
import dao.OrderDao;
import dao.PayWaysDao;
import dao.SuppliersDao;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import service.AdminService;
import service.InService;
import util.CastUtil;
import util.ImportExportHelp;
import util.MyDateChooser;

//顾客退货
public class BuyReturnGoodsModelWindow extends JDialog{	
	int i=1;
	InOrderDao inorder_dao=null;												//订单dao
	SuppliersDao sdao=null;	
	OrderDao order_dao=null;													//订单dao
	String dh=null;																//订单单号
	DepotsDao  depots_dao=null;													//仓库dao
	EmployeesDao employees_dao=null;											//经手人（员工dao）	
	PayWaysDao pay_ways_dao=null;												//支付方式的dao
		
	//日期
	MyDateChooser dc1,dc2,dc3;													//定义两个小日历
	
	JTabbedPane tabbed;
	JPanel jp_creturn;															//顾客退货面板
	JPanel jp_creturn_top,jp_creturn_center,jp_creturn_bottom;
	JPanel jp_creturn_top_top,jp_creturn_top_center;							//顶部中的面板
	JPanel jp_creturn_center_top,jp_creturn_center_center;						//中部中的面板
	JPanel jp_creturn_bottom1,jp_creturn_bottom2;								//底部中的面板
	JLabel lbl_ordernumber,lbl_returngoods;
	/**
	 * 这是用来装仓库的下拉框
	 */
	JComboBox cbox_depot;	
	DefaultComboBoxModel cbox_depot_model;
	/**
	 * 经手人下拉框
	 */
	JComboBox cbox_employees;
	DefaultComboBoxModel combox_employees_model;
																				//经手人//
	
	JTextField tf_name,tf_selldate;												//顶部文本
	JTextField tf_wantmoney,tf_paymoney,tf_bz;									//底部文本
	JButton btn_seek,btn_addgoods,btn_returnall,btn_ok,btn_exit;
	Vector columnNames;
	Vector data;
	DefaultTableModel tablemodel;
	JTable table;
	
	JPanel jp_returngdcheck;														//退货查询面板
	JPanel jp_rgc_top,jp_rgc_center;												//分为顶部和中部
	JPanel jp_tab_p1,jp_tab_p2,jp_tab_p3;
	JPanel jp_tab_p1_1,jp_tab_p1_2,jp_tab_p2_1,jp_tab_p2_2;							//中部放了两个面板
	JTabbedPane tabbed_center;
	JButton btn_look,btn_check,btn_out,btn_tui;
	JTextField tf_rgc_name,tf_rgc_check,tf_rgc_order;
	JButton btn_soc_check1,btn_soc_check2;
	Vector<Vector> date1,date2,date3,date4,date5;
	Vector<Vector> data1;
	Vector columnNames1,columnNames2,columnNames3,columnNames4,columnNames5;
	DefaultTableModel table1model,table2model,table3model,table4model,table5model;
	JTable table1,table2,table3,table4,table5;
	String[] arr1={"单据号","开单日期","供应商名称","仓库名称","应付金额","实付金额","单据类型","经办人","操作员","备注"};		
	String[] arr2={"商品编号","商品名称","单位","单价","数量","总金额","规格型号"};
	String[] arr3={"商品编号","商品名称","单位","单价","数量","总金额","规格型号"};
	String[] arr4={"供货商名称","单据号","开单日期","商品编号","商品名称","单位","单价","数量","总金额","规格型号","经办人","仓库"};
	String[] arr5={"供货商名称","单据号","开单日期","商品编号","商品名称","单位","单价","数量","总金额","规格型号","经办人","仓库"};
	/**
	 * Vector v 定义一个维克托供货商
	 */
	/**
	 * 支付方式，下拉框
	 */
																				//支付方式
		JComboBox cbox_pay;
		DefaultComboBoxModel combox_model;
		Vector v=null;
		
	public BuyReturnGoodsModelWindow(){
		inorder_dao=new InOrderDao();											//订单dao
		sdao=new SuppliersDao();												//初始化供货商dao
		order_dao=new OrderDao();												//初始化订单dao
		employees_dao=new EmployeesDao();										//初始化员工dao
		depots_dao=new DepotsDao();												//初始化仓库dao
		pay_ways_dao=new PayWaysDao();											//支付方式的dao
		dc1=MyDateChooser.getInstance("yyyy-MM-dd");
		dc2=MyDateChooser.getInstance("yyyy-MM-dd");
		dc3=MyDateChooser.getInstance("yyyy-MM-dd");							//小日历初始化
		
		
		tabbed=new JTabbedPane();												//选项卡
		jp_creturn=new JPanel();												//顾客退货面板
		jp_returngdcheck=new JPanel();											//退货查询面板
		
		tabbed.add("采购退货",jp_creturn);
		tabbed.add("退货查询",jp_returngdcheck);
		jp_returngdcheck.setLayout(new BorderLayout());							//设置边界布局
		jp_creturn.setLayout(new BorderLayout());
		/**
		 * 初始化仓库下拉框
		 */
		cbox_depot_model=new DefaultComboBoxModel(depots_dao.getDepots());							//定义combobox用于存取仓库名称
		cbox_depot=new JComboBox(cbox_depot_model);										//仓库下拉框
		
/*-----------------------------------------------顾客退货面板------------------------------------------------------*/		
		//顶部设计
		jp_creturn_top=new JPanel();
		jp_creturn_top_top=new JPanel();
		lbl_returngoods=new JLabel("采购退货");
		dh=order_dao.getInOrderTuiId();													//定义那个红色显示的单号用全局dh装着
		lbl_ordernumber=new JLabel("单号："+dh);
		lbl_ordernumber.setForeground(Color.red);
		jp_creturn_top_center=new JPanel();
		tf_name=new JTextField(15);
		btn_seek=new JButton("查找");
		tf_selldate=new JTextField(15);
		jp_creturn.add(jp_creturn_top,BorderLayout.NORTH);			//设置顶部面板的位置
		jp_creturn_top.setLayout(new BorderLayout());				//设置顶部面板的布局
		lbl_returngoods.setFont(new Font("微软雅黑",Font.BOLD,17));	//设置的字体
		jp_creturn_top_top.setLayout(new GridLayout(1,5));
		jp_creturn_top_top.add(new JLabel());
		jp_creturn_top_top.add(new JLabel());
		jp_creturn_top_top.add(lbl_returngoods);
		lbl_ordernumber.setForeground(Color.red);
		jp_creturn_top_top.add(lbl_ordernumber);
		jp_creturn_top_top.add(new JLabel()); 	
		jp_creturn_top_center.setBorder(BorderFactory.createTitledBorder(""));
		jp_creturn_top_center.add(new JLabel("供货商："));
		jp_creturn_top_center.add(tf_name);
		tf_name.setEditable(false);
		jp_creturn_top_center.add(btn_seek);
		jp_creturn_top_center.add(new JLabel("出货仓库："));
		jp_creturn_top_center.add(cbox_depot);
		tf_selldate.setText(dc1.getStrDate());
		dc1.register(tf_selldate);													//添加一个日历
		jp_creturn_top_center.add(new JLabel("退货日期："));
		jp_creturn_top_center.add(tf_selldate);
		jp_creturn_top.add(jp_creturn_top_top,BorderLayout.NORTH);
		jp_creturn_top.add(jp_creturn_top_center,BorderLayout.CENTER);
		
		//中部设计------------------------------------------------------
		jp_creturn_center=new JPanel();
		btn_addgoods=new JButton("添加退货商品");
		btn_returnall=new JButton("整单退货");
		jp_creturn_center_top=new JPanel();
		jp_creturn_center_center=new JPanel();
		
		jp_creturn_center.setLayout(new BorderLayout());
		jp_creturn_center_top.setLayout(new GridLayout(1,6));
		jp_creturn_center_top.setBorder(BorderFactory.createTitledBorder(""));
		jp_creturn_center_top.add(btn_addgoods);
		jp_creturn_center_top.add(btn_returnall);
		jp_creturn_center_top.add(new JLabel());
		jp_creturn_center_top.add(new JLabel());
		jp_creturn_center_top.add(new JLabel());
		jp_creturn_center_top.add(new JLabel());
		jp_creturn_center.add(jp_creturn_center_top,BorderLayout.NORTH);
		jp_creturn_center_center.setLayout(new GridLayout(1,1));
		columnNames=new Vector();
		columnNames.add("商品编号");
		columnNames.add("商品名称");
		columnNames.add("单位");
		columnNames.add("规格型号");
		columnNames.add("单价");
		columnNames.add("数量");
		columnNames.add("总金额");
		data=new Vector();
		
		
		/**
		 * 初始化经办人下拉框
		 */
		combox_employees_model=new DefaultComboBoxModel(employees_dao.getEmployees());
		cbox_employees=new JComboBox(combox_employees_model);
		
		/**
		 * 初始化支付方式下拉框
		 */
																					//定义combobox用于存取支付方式
		combox_model=new DefaultComboBoxModel(pay_ways_dao.getPayWaysInfo());
		cbox_pay=new JComboBox(combox_model);
		/**
		 * 主界面的表
		 */
		tablemodel=new DefaultTableModel(data, columnNames);
		table=new JTable(tablemodel);
		jp_creturn_center_center.add(new JScrollPane(table));
		jp_creturn_center.add(jp_creturn_center_center);
		jp_creturn.add(jp_creturn_center,BorderLayout.CENTER);
		
		//底部设计----------------------------------------------------
		jp_creturn_bottom=new JPanel();
		jp_creturn.add(jp_creturn_bottom,BorderLayout.SOUTH);	
		jp_creturn_bottom.setLayout(new GridLayout(2,1));
		jp_creturn_bottom1=new JPanel();
		jp_creturn_bottom.add(jp_creturn_bottom1);
		jp_creturn_bottom1.add(new JLabel("应收金额："));
		tf_wantmoney=new JTextField(15);
		jp_creturn_bottom1.add(tf_wantmoney);
		jp_creturn_bottom1.add(new JLabel("实收金额："));
		tf_paymoney=new JTextField(15);
		jp_creturn_bottom1.add(tf_paymoney);
		
		jp_creturn_bottom1.add(new JLabel("支付方式："));
		jp_creturn_bottom1.add(cbox_pay);
		jp_creturn_bottom1.add(new JLabel("经办人："));
		jp_creturn_bottom1.add(cbox_employees);
		jp_creturn_bottom.add(jp_creturn_bottom1);
		
		jp_creturn_bottom2=new JPanel();
		jp_creturn_bottom2.add(new JLabel("备注"));
		tf_bz=new JTextField(53);
		jp_creturn_bottom2.add(tf_bz);
		btn_ok=new JButton("确定");
		jp_creturn_bottom2.add(btn_ok);
		btn_exit=new JButton("退出");
		jp_creturn_bottom2.add(btn_exit);
		jp_creturn_bottom.add(jp_creturn_bottom2	);
		
/*----------------------------------------------------退货查询面板------------------------------------------------------*/
		
		//顶部设计------------------------------------------------------
		jp_rgc_top=new JPanel();
		btn_look=new JButton("高级查询");
		btn_check=new JButton("查看单据");
		btn_out=new JButton("导出");
		btn_tui=new JButton("退出");
		tf_rgc_name=new JTextField(8);
		tf_rgc_check=new JTextField(8);
		tf_rgc_order=new JTextField(8);
		btn_soc_check1=new JButton("查询");
		btn_soc_check2=new JButton("查询");
		jp_rgc_top.setBorder(BorderFactory.createTitledBorder(""));
		jp_rgc_top.add(btn_look);
		jp_rgc_top.add(btn_check);
		jp_rgc_top.add(btn_out);
		jp_rgc_top.add(btn_tui);
		jp_rgc_top.add(new JLabel("开单日期"));
		tf_rgc_name.setText(dc2.getStrDate());
		dc2.register(tf_rgc_name);											//添加一个日历
		jp_rgc_top.add(tf_rgc_name);
		jp_rgc_top.add(new JLabel("至"));
		tf_rgc_check.setText(dc3.getStrDate());	
		dc3.register(tf_rgc_check);											//添加一个日历
		jp_rgc_top.add(tf_rgc_check);
		jp_rgc_top.add(btn_soc_check1);
		jp_rgc_top.add(new JLabel("供货商/单据号"));
		jp_rgc_top.add(tf_rgc_order);
		jp_rgc_top.add(btn_soc_check2);
		jp_returngdcheck.add(jp_rgc_top,BorderLayout.NORTH);
		//中部设计-----------------------------------------------------
		jp_rgc_center=new JPanel();
		tabbed_center=new JTabbedPane();
		jp_tab_p1=new JPanel();
		jp_tab_p2=new JPanel();
		jp_tab_p3=new JPanel();
		jp_tab_p1_1=new JPanel();
		jp_tab_p1_2=new JPanel();
		jp_tab_p2_1=new JPanel();
		jp_tab_p2_2=new JPanel();
		columnNames1=new Vector();
		columnNames2=new Vector();
		columnNames3=new Vector();
		columnNames4=new Vector();
		columnNames5=new Vector();
		for (String str:arr1) {
			columnNames1.add(str);
		}
		for (String str:arr2) {
			columnNames2.add(str);
		}
		for (String str:arr3) {
			columnNames3.add(str);
		}
		for (String str:arr4) {
			columnNames4.add(str);
		}
		for (String str:arr5) {
			columnNames5.add(str);
		}
		table1model=new DefaultTableModel(data1 = inorder_dao.getTuiInorder(),columnNames1);
		table1=new JTable(table1model) {
			public boolean isCellEditable(int row, int column) {
				return false;
		}};
		table2model=new DefaultTableModel(date2,columnNames2);
		table2=new JTable(table2model);
		table3model=new DefaultTableModel(inorder_dao.getTuiGoods(),columnNames3);
		table3=new JTable(table3model) {
			public boolean isCellEditable(int row, int column) {
				return false;
		}};
		table4model=new DefaultTableModel(date4,columnNames4);
		table4=new JTable(table4model);
		table5model=new DefaultTableModel(inorder_dao.getTuiGoodsInfo(""),columnNames5);
		table5=new JTable(table5model);
		jp_rgc_center.setLayout(new GridLayout(1,1));
		//单据选项卡
		tabbed_center.add("单据表",jp_tab_p1);
		jp_tab_p1.setLayout(new GridLayout(2,1));
		jp_tab_p1.add(jp_tab_p1_1);
		jp_tab_p1_1.setLayout(new GridLayout(1,1));
		jp_tab_p1_1.add(new JScrollPane(table1));
		jp_tab_p1.add(jp_tab_p1_2);
		jp_tab_p1_2.setLayout(new GridLayout(1,1));
		jp_tab_p1_2.add(new JScrollPane(table2));
		//汇总选项卡
		tabbed_center.add("汇总表",jp_tab_p2);
		jp_tab_p2.setLayout(new GridLayout(2,1));
		jp_tab_p2.add(jp_tab_p2_1);
		jp_tab_p2_1.setLayout(new GridLayout(1,1));
		jp_tab_p2_1.add(new JScrollPane(table3));
		jp_tab_p2.add(jp_tab_p2_2);
		jp_tab_p2_2.setLayout(new GridLayout(1,1));
		jp_tab_p2_2.add(new JScrollPane(table4));
		//明细选项卡
		tabbed_center.add("明细表",jp_tab_p3);
		jp_tab_p3.setLayout(new GridLayout(1,1));
		jp_tab_p3.add(new JScrollPane(table5));
		jp_rgc_center.add(tabbed_center);
		jp_returngdcheck.add(jp_rgc_center,BorderLayout.CENTER);
		
		
		
		 	
	/**
	 * 监听方法
	 * @author lxh
	 * 		 
	 */
		/**
		 * 查找seek 供货商
		 */
		btn_seek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SupplierInfoModelWindow sp=new SupplierInfoModelWindow();
				 v=sp.retData;
				try {
					tf_name.setText(v.get(1).toString());
				} catch (Exception e2) {
					
				}
			}
		});
		/**
		 * 添加退货商品
		 */
		btn_addgoods.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddReturnGoodsModelWindow(BuyReturnGoodsModelWindow.this);
			}
		});
		/**
		 * 整单退货
		 */
		btn_returnall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new BuyDocumentsCheckModelWindow(BuyReturnGoodsModelWindow.this);
			}
		});
		/*
		 * 确定事件
		 * 生成退货单
		 * 
		 */
		btn_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String date=tf_selldate.getText().trim();
					Depot depot=(Depot) cbox_depot.getSelectedItem();
					Double wantMoney=Double.parseDouble(tf_wantmoney.getText().trim());
					Double payMoney=Double.parseDouble(tf_paymoney.getText().trim());
					Employee agent=(Employee) cbox_employees.getSelectedItem();
					Admin operator=AdminService.admin;
					String bz=tf_bz.getText().trim();
					PayWay payWay=(PayWay) cbox_pay.getSelectedItem();
					Supplier supplier=new Supplier();
					 supplier=sdao.getSupplierInfoByContactorName(tf_name.getText()+"");
					InOrder_tui in_order_tui=new InOrder_tui(dh, date, depot, wantMoney, payMoney, agent, operator, bz, payWay, supplier);
					new InService().addOrder(in_order_tui,new CastUtil().VerctorToHashSet(data));
					JOptionPane.showMessageDialog(null, "提交成功!");
					BuyReturnGoodsModelWindow.this.setVisible(false);
					new BuyReturnGoodsModelWindow();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "提交失败!");
				}
				
				
				
			}
		});
		/**
		 * 导出
		 */
		btn_out.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFileChooser out_file=new JFileChooser();
					out_file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					out_file.showSaveDialog(null);
					String str=out_file.getSelectedFile().getAbsolutePath();
					Vector<Vector> da=new Vector<Vector>();
					da.add(columnNames1);
					da.addAll(data1);
					
						ImportExportHelp.ExportData(da, str);
						JOptionPane.showMessageDialog(null, "导出成功");
					} catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}catch (NullPointerException e) {
						JOptionPane.showMessageDialog(null, "无数据警告！");
					}
					
				
			}
		});
		/**
		 * btn_look 高级查询
		 */
		btn_look.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "右边有按日期和供货商以及单据号查询1");
			}
		});
		/**
		 * btn_check 查看单据
		 */
		btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "单击表一，选中数据，下面的表就会显示数据");
			}
		});
		/**
		 * btn_exit退出
		 */
		btn_tui.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
			BuyReturnGoodsModelWindow.this.setVisible(false);
				
			}
		});
		btn_exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
			BuyReturnGoodsModelWindow.this.setVisible(false);
				
			}
		});
		/**
		 * 退货明细的日期查询
		 */
		btn_soc_check1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String date1=tf_rgc_name.getText();
				String date2=tf_rgc_check.getText();
				
			if(table1.isShowing()){
				i=2;
				table1model=new DefaultTableModel(inorder_dao.getTuiInorder(date1,date2),columnNames1);
				table1.setModel(table1model);
				table1.updateUI();
			}else if(table3.isShowing()) {
				i=2;
				table3model=new DefaultTableModel(inorder_dao.getTuiGoods(date1,date2),columnNames3);
				table3.setModel(table3model);
				table3.updateUI();
			}else if(table5.isShowing()) {
				table5model=new DefaultTableModel(inorder_dao.getTuiGoodsInfo(date1,date2),columnNames5);
				table5.setModel(table5model);
				table5.updateUI();
			}
			
				
			}
		});
		/**
		 * 按供货商名称和订单编号查询
		 */
		btn_soc_check2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String id_or_name=tf_rgc_order.getText();
				if(table1.isShowing()){
					i=3;
					table1model=new DefaultTableModel(inorder_dao.getTuiInorder(id_or_name),columnNames1);
					table1.setModel(table1model);
					table1.updateUI();
				}else if(table3.isShowing()) {
					i=3;
					table3model=new DefaultTableModel(inorder_dao.getTuiGoods1(id_or_name),columnNames3);
					table3.setModel(table3model);
					table3.updateUI();
				}else if(table5.isShowing()) {
					table5model=new DefaultTableModel(inorder_dao.getTuiGoodsInfo1(id_or_name),columnNames5);
					table5.setModel(table5model);
					table5.updateUI();
				}
				
			}
		});
		/**
		 * table1 的选中事件
		 */
		table1.addMouseListener(new TableMouse(table1));
		table3.addMouseListener(new Table3Mouse(table3));
		this.setTitle("采购退货");
		this.add(tabbed);
		this.setBounds(300, 100, 900, 550);
		this.setModal(true);
		this.setVisible(true);
	}
	/***
	 * 
	 * 表格1的点击事件
	 * 会使表格2发生变化
	 *
	 */
	
	public class TableMouse extends MouseAdapter{
		JTable table;
		public TableMouse(	JTable table){
			this.table=table;
		}
		public void mouseClicked(MouseEvent e) {
			if(e.getButton()==1&&isSelectTable1()!=null){
				Vector vector_boss=inorder_dao.getTuiGoods(isSelectTable1().get(0)+"");
				table2model=new DefaultTableModel(vector_boss,columnNames2);
				table2.setModel(table2model);
				table2.updateUI();
			}
		}
	}
	/**
	 * 
	 * 返回表格1选中的行
	 */
	public Vector isSelectTable1(){
		Vector vector=null;
		if(table1.isShowing()){
			try{
				 if(i==1) {								//如果为一调用下面函数
					 vector=(Vector)inorder_dao.getTuiInorder().get(table1.getSelectedRow());	
				}else if(i==2){							//如果为二调用查询指定日期内商品的函数
					vector=(Vector)inorder_dao.getTuiInorder(tf_rgc_name.getText(),tf_rgc_check.getText()).get(table1.getSelectedRow());	
					System.out.println(2222);
				}else if(i==3){
					vector=(Vector)inorder_dao.getTuiInorder(tf_rgc_order.getText()).get(table3.getSelectedRow());	

				}
			}catch (Exception e2) {
				
			}
		}
		return vector;
	}	/***
	 * 
	 * 表格3的点击事件
	 * 会使表格4发生变化
	 *
	 */
	
	public class Table3Mouse extends MouseAdapter{
		JTable table;
		
		public Table3Mouse(	JTable table){
			this.table=table;
		}
		public void mouseClicked(MouseEvent e) {
			if(e.getButton()==1&&isSelectTable3()!=null){
				jp_tab_p2_2.setBorder(BorderFactory.createTitledBorder("商品退货明细："+isSelectTable3().get(1)));
				int gid=Integer.parseInt(isSelectTable3().get(0)+"");
				table4model=new DefaultTableModel(inorder_dao.getTuiGoodsInfo(gid),columnNames4);
				table4.setModel(table4model);
				table4.updateUI();
			}
	}}
	/**
	 * 
	 * 返回表格3选中的行
	 */
	public Vector isSelectTable3(){
		Vector vector=null;
		if(table3.isShowing()){
			try{
				if(i==1) {								//如果为一调用下面函数
					 vector=(Vector)inorder_dao.getTuiGoods().get(table3.getSelectedRow());	
				}else if(i==2){							//如果为二调用查询指定日期内商品的函数
					vector=(Vector)inorder_dao.getTuiGoods(tf_rgc_name.getText(),tf_rgc_check.getText()).get(table3.getSelectedRow());	
				}else if(i==3){							//如果为二调用查询指定日期内商品的函数
					vector=(Vector)inorder_dao.getTuiGoods1(tf_rgc_order.getText()).get(table3.getSelectedRow());	

				}
					 
			}catch (Exception e2) {
				
			}
		}
		return vector;
	}

	
	public static void main(String[] args) {
		new AdminService().Login("admin", "123");
		new BuyReturnGoodsModelWindow();
	}
}
