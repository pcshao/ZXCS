package gui.lxh;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultTreeModel;

import bean.Admin;
import bean.Depot;
import bean.Employee;
import bean.PayWay;
import bean.Supplier;
import bean.orders.InOrder;

import service.AdminService;
import service.InService;
import util.CastUtil;
import util.ImportExportHelp;
import util.MyDateChooser;

import dao.DepotsDao;
import dao.EmployeesDao;
import dao.OrderDao;
import dao.PayWaysDao;


public class InputGoodsModelWindow extends JDialog{
	OrderDao order_dao=null;
	String dh=null;															//订单单号
	int money=0;
	MyDateChooser date1=null;	
	DepotsDao  depots_dao=null;												//仓库的dao对象				
	PayWaysDao pay_ways_dao=null;											//支付方式的dao包对象
	EmployeesDao employees_dao=null;										//经手人（员工dao）				
	//1.选项卡第一个选项用到的东西
	JTabbedPane tabbed;   													//选项卡的名字
	JPanel p_input,p_auditing;												//选项卡里的连个选项面板
	
	JPanel p_input_up,p_input_down;											//第一个面板里分为两个up——down面板
	JPanel p_up1,p_up2,p_down1,p_down2,p_down3;								//up又包含了两个面板，down包括三个
	JPanel p_down31,p_down32;
	
	JTextField tf_supplier,tf_date,tf_should_pay,tf_reality_pay,tf_number,tf_bz;  
	
	
	/**
	 * commboBox 获取仓库名称，支付方式，经手人																					//定义下拉框
	 */	
	ButtonGroup bg;																		//获取仓库
	JComboBox cbox_depot;
	DefaultComboBoxModel cbox_depot_model;
																			//支付方式
	JComboBox cbox_pay;
	DefaultComboBoxModel combox_model;
																			//经手人
	JComboBox cbox_employees;
	DefaultComboBoxModel combox_employees_model;
	
	JButton btn_select,btn_old_add,btn_new_add,btn_in_out,btn_ok,btn_exit;
	JLabel lable_d;
	String[] strs={"商品编号","商品名称","单位","规格","单价","数量","总金额"};			//表格的列名
	
	JTable table;																			//定义表格，维克托，以及model容器
	Vector vectorboss,vector;											
	DefaultTableModel model;
	
	JPopupMenu pop_in_out;
	JMenuItem mitem_in,mitmem_out;
	
	//2.选项卡第二个选项用到的东西
	JPanel p_north,p_center,p_n1,p_c1,p_c11,p_c2,p_c21;	
	
	//北pane包含-->2个pane,中pane包含--->3个pane
	JButton btn_n1_look,btn_n1_delete,btn_n1_auditing,btn_n1_output,btn_n1_stamp,btn_n1_exit,	//里面的按钮
	btn_n2_look,btn_n2_select,btn_c2_add,btn_c2_alter,btn_c2_delete;
	
	JTextField tf_supplier2,tf_select;
	
	JLabel label_no;
	//两个表格以及它们的列
	JTable table_s_number,table_g_number;
	String[] s_column={"供货商名称","日期","单号","应付金额","实付金额","欠款金额","优惠金额","经办人","操作员"};
	String[] g_column={"商品编号","商品名称","单位","单价","数量","总金额","生产日期","保质期（天）","规格型号"};
	
	//定义两个装列的vector,model,；
	Vector vector_s, vector_g;
	DefaultTableModel medol_s,medol_g;
	
	/**
	 * Vector v 供货商
	 */
	Vector v=null;
	public InputGoodsModelWindow() {
		order_dao=new OrderDao();
		date1=MyDateChooser.getInstance("yyyy-MM-dd");
		
		/**
		 * 初始化dao对象
		 */
		depots_dao=new DepotsDao();
		pay_ways_dao=new PayWaysDao();
		employees_dao=new EmployeesDao();
		//1.第一选项卡用到的东西
		
		//初始化表格，把表格放在一个面板里面
		vector=new Vector();
		for(String str:strs){												//把strs全部放入维克托里面，然后把维克托放入model容器里
			vector.add(str);
		}
		vectorboss=new Vector();
		model=new DefaultTableModel(vectorboss,vector);
		table=new JTable(model);
		
		//定义选项卡
		tabbed=new JTabbedPane();
		p_input=new JPanel();
		p_auditing=new JPanel();
		
		tabbed.add("采购进货",p_input);										//为控制面板加入两个选项（面板）
		tabbed.add("单据审核",p_auditing);
		
		//定义面板
		p_input_up=new JPanel();
		p_input_down=new JPanel();
		p_up1=new JPanel();
		p_up2=new JPanel();
		p_down1=new JPanel();
		p_down2=new JPanel();
		p_down3=new JPanel();
		p_down31=new JPanel();
		p_down32=new JPanel();
		p_down3.setBorder(BorderFactory.createTitledBorder(""));
		
		
		//定义文本框
		tf_supplier=new JTextField(12);
		tf_supplier.setText("从右边选择供货商--→");
		tf_supplier.setEditable(false);
		tf_date=new JTextField(8);
		tf_should_pay=new JTextField(8);
		tf_reality_pay=new JTextField(8);
		tf_number=new JTextField(10);
		tf_bz=new JTextField(27);
		
		
		cbox_depot_model=new DefaultComboBoxModel(depots_dao.getDepots());					//定义combobox用于存取仓库名称
		cbox_depot=new JComboBox(cbox_depot_model);
																							//定义combobox用于存取支付方式
		combox_model=new DefaultComboBoxModel(pay_ways_dao.getPayWaysInfo());
		cbox_pay=new JComboBox(combox_model);
																							//定义combobox用于存取经手人
		combox_employees_model=new DefaultComboBoxModel(employees_dao.getEmployees());
		cbox_employees=new JComboBox(combox_employees_model);
		
		//定义按钮
		btn_select=new JButton("查找");
		btn_new_add=new JButton("新商品添加");
		btn_old_add=new JButton("老商品添加");
		btn_in_out=new JButton("导入导出");
		btn_ok=new JButton("确定");
		btn_exit=new JButton("退出");
		
		//定义那个红色显示的单号
	    dh=order_dao.getInOrderId();													//定义那个红色显示的单号用全局dh装着
		lable_d=new JLabel("单号："+dh);
		lable_d.setForeground(Color.red);
		
		//p_input_up面板添加各种东西
		p_up1.add(new JLabel("------------------------------------------------采购进货--------------------------------------------------------------------------"));
		p_up1.add(new JLabel("           "));
		p_up1.add(lable_d);
		
		p_up2.add(new JLabel("供应商："));	
		p_up2.add(tf_supplier);
		p_up2.add(btn_select);
		p_up2.add(new JLabel("     "));
		p_up2.add(new JLabel("收货仓库："));
		p_up2.add(cbox_depot);
		p_up2.add(new JLabel("进货日期："));
		tf_date.setText(date1.getStrDate());
		date1.register(tf_date);
		p_up2.add(tf_date);
		p_up2.setBorder(BorderFactory.createTitledBorder(""));
		
		p_input_up.setLayout(new BorderLayout());
		p_input_up.add(p_up1,BorderLayout.NORTH);
		p_input_up.add(p_up2,BorderLayout.CENTER);
		
		
		//p_input_down面板添加各种东西
		p_down1.setLayout(new GridLayout(1,3));
		p_down1.add(btn_old_add);
		p_down1.add(btn_new_add);
		p_down1.add(btn_in_out);
		p_down1.setBorder(BorderFactory.createTitledBorder(""));
		
		p_down2.setLayout(new GridLayout());
		p_down2.add(new JScrollPane(table));
		p_down31.add(new JLabel("应付金额："));
		p_down31.add(tf_should_pay);
		p_down31.add(new JLabel("实付金额："));
		p_down31.add(tf_reality_pay);
		p_down31.add(new JLabel("付款方式："));
		p_down31.add(cbox_pay);
		p_down31.add(new JLabel("原始单号："));
		tf_number.setText("需要开通会员才能用！");
		tf_number.setEditable(false);
		p_down31.add(tf_number);
		
		p_down32.add(new JLabel("经手人："));
		p_down32.add(cbox_employees);
		p_down32.add(new JLabel("你的备注："));
		p_down32.add(tf_bz);
		p_down32.add(new JLabel("      "));
		p_down32.add(btn_ok);
		p_down32.add(new JLabel("      "));
		p_down32.add(btn_exit);
		
		p_down3.setLayout(new GridLayout(2,1));
		p_down3.add(p_down31);
		p_down3.add(p_down32);
		
		//为p_input_down 添加p_down1,p_down2,p_down3 个面板
		p_input_down.setLayout(new BorderLayout());
		p_input_down.add(p_down1,BorderLayout.NORTH);
		p_input_down.add(p_down2,BorderLayout.CENTER);
		p_input_down.add(p_down3,BorderLayout.SOUTH);
		
		//再把大面板加到p_input选项卡中
		p_input.setLayout(new BorderLayout());
		p_input.add(p_input_up,BorderLayout.NORTH);
		p_input.add(p_input_down,BorderLayout.CENTER);
		p_input.setBorder(BorderFactory.createTitledBorder(""));
		
		//搞个左键菜单，放在导入导出里
		pop_in_out=new JPopupMenu();
		mitem_in=new JMenuItem("从excel中导入");
		mitmem_out=new JMenuItem("从excel中导出");
		pop_in_out.add(mitem_in);
		pop_in_out.add(mitmem_out);
		
		
		//2.选项卡第二个选项用到的东西
		//定义,把选项分为两个面板，第一个里面包含2个，在北方，第二个包含三个在中间
		p_north=new JPanel();
		p_center=new JPanel();
		p_n1=new JPanel();
		p_c1=new JPanel();
		p_c11=new JPanel();
		p_c2=new JPanel();
		p_c21=new JPanel();
		p_north.setBorder(BorderFactory.createTitledBorder(""));
		p_center.setBorder(BorderFactory.createTitledBorder(""));
		p_n1.setBorder(BorderFactory.createTitledBorder(""));
		p_c11.setBorder(BorderFactory.createTitledBorder(""));
		p_c2.setBorder(BorderFactory.createTitledBorder(""));
		p_c21.setBorder(BorderFactory.createTitledBorder(""));
		
		//里面的按钮哦
		btn_n1_look=new JButton("查看单据");
		btn_n1_delete=new JButton("删除单据");
		btn_n1_auditing=new JButton("单据审核");
		btn_n1_output=new JButton("导   出");
		btn_n1_stamp=new JButton("打    印");
		btn_n1_exit=new JButton("退    出");
		
		btn_n2_look=new JButton("查看");
		btn_n2_select=new JButton("查询");
		
		btn_c2_add=new JButton("增加商品");
		btn_c2_alter=new JButton("修改商品");
		btn_c2_delete=new JButton("删除商品");
		
		//初始化文本框
		tf_supplier2=new JTextField(8);
		tf_select=new JTextField(12);
		
		label_no=new JLabel("无");
		label_no.setForeground(Color.blue);
		
		//初始化表格
		vector_s=new Vector();
		vector_g=new Vector();
		for(String str:s_column){
			vector_s.add(str);
		}
		for(String str:g_column){
			vector_g.add(str);
		}
		medol_s=new DefaultTableModel(null,vector_s);
		medol_g=new DefaultTableModel(null,vector_g);
		table_s_number=new JTable(medol_s);
		table_g_number=new JTable(medol_g);
		
		//添加控件到选项卡里,从内到外，从上到下法添加
		p_n1.add(btn_n1_look);
		p_n1.add(btn_n1_delete);
		p_n1.add(btn_n1_auditing);
		p_n1.add(btn_n1_output);
		p_n1.add(btn_n1_stamp);
		p_n1.add(btn_n1_exit);
		
		p_north.setLayout(new BorderLayout());									//把包含拿6个按钮的面板放在p_north里，靠左；
		p_north.add(p_n1,BorderLayout.WEST);
		//这个选项的上部分已经搞定
		
		//p_c11.setLayout(new GridLayout(1, 7));
		p_c11.add(new JLabel("------------"));//往p_c11里加该有的东西
		p_c11.add(new JLabel("供货商："));
		p_c11.add(tf_supplier2);
		p_c11.add(btn_n2_look);
		p_c11.add(new JLabel("-------------------------"));
		p_c11.add(new JLabel("输入单据相关信息进行查询："));
		p_c11.add(tf_select);
		p_c11.add(btn_n2_select);
														
		
		p_c21.setLayout(new GridLayout(1,8));								//往p_c2里面加东西
		p_c21.add(new JLabel("供货商："));
		p_c21.add(label_no);
		p_c21.add(new JLabel("  "));
		p_c21.add(new JLabel("  "));
		p_c21.add(new JLabel("  "));
		p_c21.add(btn_c2_add);
		p_c21.add(btn_c2_alter);
		p_c21.add(btn_c2_delete);
		
		p_c1.setLayout(new BorderLayout());									//中间面板的上部分
		p_c1.add(p_c11,BorderLayout.NORTH);
		p_c1.add(new JScrollPane(table_s_number),BorderLayout.CENTER);
		
		p_c2.setLayout(new BorderLayout());									//中间面板的下部分
		p_c2.add(p_c21,BorderLayout.NORTH);
		p_c2.add(new JScrollPane(table_g_number),BorderLayout.CENTER);
		
		//开始往p_center 里加东西
		p_center.setLayout(new GridLayout(2,1));
		p_center.add(p_c1);
		p_center.add(p_c2);
		
		//在把北中两个面板加到选项卡中
		p_auditing.setLayout(new BorderLayout());
		p_auditing.add(p_north,BorderLayout.NORTH);
		p_auditing.add(p_center,BorderLayout.CENTER);
		
		
		this.add(tabbed);
		this.setBounds(0, 0, 800, 600);
		this.setLocationRelativeTo(null);									//居中显示
		
		//老商品添加
		btn_old_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new OldGoodsADDModelWindow(InputGoodsModelWindow.this);
			}
		});
		//新商品添加
		btn_new_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddGoodsModleWindow(InputGoodsModelWindow.this);
			}
		});
		//查找监听
		btn_select.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				SupplierInfoModelWindow sp=new SupplierInfoModelWindow();
				 v=sp.retData;
				try {
					tf_supplier.setText(v.get(1).toString());
				} catch (Exception e2) {
					
				}
				
				
			}
		});
		/**
		 * 导入导出
		 */
		mitem_in.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ImportExportHelp();
			}
		});
		mitmem_out.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ImportExportHelp();
			}
		});
		//导入导出菜单
		btn_in_out.addMouseListener(new mouseClicked());
		/*
		 * 确定事件
		 * 生成订单
		 */
		btn_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String date=tf_date.getText().trim();
					Depot depot=(Depot) cbox_depot.getSelectedItem();
					Double wantMoney=Double.parseDouble(tf_should_pay.getText().trim());
					Double payMoney=Double.parseDouble(tf_reality_pay.getText().trim());
					
					Employee agent=(Employee) cbox_employees.getSelectedItem();
					Admin operator=AdminService.admin;
					
					String bz=tf_bz.getText().trim();
					PayWay payWay=(PayWay) cbox_pay.getSelectedItem();
					Supplier supplier=new CastUtil().VectorToSupplier(v);
					InOrder in_order=new InOrder(dh, date, depot, wantMoney, payMoney, agent, operator, bz, payWay, supplier);
					new InService().addOrder(in_order,new CastUtil().VerctorToHashSet(vectorboss));
					JOptionPane.showMessageDialog(null, "提交成功!");
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "提交失败!");
				}
			}
		});
		/**
		 * btn_exit退出
		 */
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InputGoodsModelWindow.this.setVisible(false);
			}
		});
		this.setModal(true);												//设置为模式窗口
		this.setVisible(true);
	}
	
	//导入导出菜单
	public class mouseClicked extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			if(e.getButton()==1){
				pop_in_out.show(btn_in_out, e.getX(), e.getY());
			}
			
		}
	}
	public static void main(String[] args) {
		new AdminService().Login("admin", "123");
		new InputGoodsModelWindow();
	}
}
