package gui.yc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import bean.Admin;
import bean.Customer;
import bean.Depot;
import bean.Employee;
import bean.PayWay;
import bean.orders.SellOrder;

import dao.CustomDao;
import dao.DepotsDao;
import dao.EmployeesDao;
import dao.GoodsDao;
import dao.OrderDao;
import dao.PayWaysDao;
import dao.SellOrdersDao;

import service.AdminService;
import service.SellService;
import util.CastUtil;
import util.ImportExportHelp;
import util.MyDateChooser;
//商品销售
public class GoodSellModelWindow extends JDialog{
	MyDateChooser dc;
	JTabbedPane tabbed;
	JPanel jp_goodsell;							
	JPanel jp_goodsell_top,jp_goodsell_center,jp_goodsell_bottom;
	JPanel jp_goodsell_top_top,jp_goodsell_top_center;			
	JPanel jp_goodsell_center_top,jp_goodsell_center_center;						
	JPanel jp_goodsell_bottom1,jp_goodsell_bottom2;									
	JLabel lbl_ordernumber,lbl_goodsell;
	JTextField tf_name,tf_selldate;
	DefaultComboBoxModel aModel1;
	JComboBox cobx_depots;
	JTextField tf_wantmoney,tf_paymoney,tf_order,tf_bz;
	Vector aModel1Vector2,aModel1Vector3;
	DefaultComboBoxModel aModel2,aModel3;
	JComboBox cobx_agent,cobx_pay;
	JButton btn_seek,btn_addgoods,btn_inout,btn_ok,btn_exit;
	Vector columnNames;
	Vector<Vector> data;
	DefaultTableModel tablemodel;
	JTable table;
	
	JPanel jp_sellorderscheck;					
	JPanel jp_soc_top,jp_soc_center;			
	JPanel jp_soc_center_p1,jp_soc_center_p2;	
	JPanel jp_p1_top,jp_p1_center;
	JPanel jp_p2_top,jp_p2_center;
	JButton btn_look,btn_delete,btn_check,btn_out,btn_stamp,btn_tui;
	JTextField tf_soc_name,tf_soc_check;
	JButton btn_soc_check1,btn_soc_check2;
	Vector<Vector> date1,date2;
	Vector columnNames1,columnNames2;
	DefaultTableModel table1model,table2model;
	JTable table1,table2;
	JButton btn_add,btn_alter,btn_del;//
	String[] arr1={"客户名称","日期","单号","应付金额","实付金额","优惠金额","欠款金额","经办人","支付方式","操作员","备注  "};
	String[] arr2={"商品编号","商品名称","单位","单价","数量","总金额","规格型号","颜色"};
	//导入导出
	JPopupMenu pop_in_out;
	JMenuItem mitem_in,mitmem_out;
	
	//生成订单所需数据
	Customer ret1,ret2;				//客户		
	Vector aModel1Vector;		//仓库
	String iddan;					//订单号		
								//销售清单
	Vector<Vector> customdata;
	public GoodSellModelWindow(){
		dc=MyDateChooser.getInstance("yyyy-MM-dd");
		tabbed=new JTabbedPane();		//选项卡
		jp_goodsell=new JPanel();		//商品销售面板
		jp_sellorderscheck=new JPanel();//销售审核面板
		tabbed.add("商品销售",jp_goodsell);
//		tabbed.add("销售单审核",jp_sellorderscheck);
		jp_sellorderscheck.setLayout(new BorderLayout());	//设置销售面板为边界布局
		jp_goodsell.setLayout(new BorderLayout());			//设置销售面板为边界布局
		
		/**
		 * 商品销售选项卡
		 * @author yc
		 * */		
		//顶部设计
		jp_goodsell_top=new JPanel();
		jp_goodsell_top_top=new JPanel();
		lbl_goodsell=new JLabel("商品销售");
		iddan=new SellOrdersDao().getSellOrderId();
		lbl_ordernumber=new JLabel("单号："+iddan);
		jp_goodsell_top_center=new JPanel();
		tf_name=new JTextField(15);
		btn_seek=new JButton("查找");
		aModel1Vector=new DepotsDao().getDepots();
		aModel1=new DefaultComboBoxModel(aModel1Vector);
		cobx_depots=new JComboBox(aModel1);
		tf_selldate=new JTextField(15);
		jp_goodsell.add(jp_goodsell_top,BorderLayout.NORTH);	//设置顶部面板的位置
		jp_goodsell_top.setLayout(new BorderLayout());			//设置顶部面板的布局
		lbl_goodsell.setFont(new Font("微软雅黑",Font.BOLD,17));//设置商品销售的字体
		jp_goodsell_top_top.setLayout(new GridLayout(1,5));
		jp_goodsell_top_top.add(new JLabel());
		jp_goodsell_top_top.add(new JLabel());
		jp_goodsell_top_top.add(lbl_goodsell);
		lbl_ordernumber.setForeground(Color.red);
		jp_goodsell_top_top.add(lbl_ordernumber);
		jp_goodsell_top_top.add(new JLabel()); 	
		jp_goodsell_top_center.setBorder(BorderFactory.createTitledBorder(""));
		jp_goodsell_top_center.add(new JLabel("客户名称："));
		ret1=new CastUtil().vectorToCustomer(new CastUtil().objectToVector(new CustomDao().getCustomers()).get(0));
		tf_name.setText(ret1.getName());
		tf_name.setEditable(false);
		jp_goodsell_top_center.add(tf_name);
		jp_goodsell_top_center.add(btn_seek);
		jp_goodsell_top_center.add(new JLabel("出货仓库"));
		jp_goodsell_top_center.add(new JScrollPane(cobx_depots));
		jp_goodsell_top_center.add(new JLabel("销售日期"));
		dc.register(tf_selldate);
		tf_selldate.setText(dc.getStrDate());
		jp_goodsell_top_center.add(tf_selldate);
		
		jp_goodsell_top.add(jp_goodsell_top_top,BorderLayout.NORTH);
		jp_goodsell_top.add(jp_goodsell_top_center,BorderLayout.CENTER);
		//中部设计
		jp_goodsell_center=new JPanel();
		btn_addgoods=new JButton("添加商品");
		//搞个左键菜单，放在导入导出里
		pop_in_out=new JPopupMenu();
		mitem_in=new JMenuItem("从excel中导入");
		mitmem_out=new JMenuItem("从excel中导出");
		pop_in_out.add(mitem_in);
		pop_in_out.add(mitmem_out);
		
		btn_inout=new JButton("导入导出");
		jp_goodsell_center_top=new JPanel();
		jp_goodsell_center_center=new JPanel();
		jp_goodsell_center.setLayout(new BorderLayout());
		jp_goodsell_center_top.setLayout(new GridLayout(1,6));
		columnNames=new Vector();
		columnNames.add("商品编号");
		columnNames.add("商品名称");
		columnNames.add("单位");
		columnNames.add("规格型号");
		columnNames.add("单价");
		columnNames.add("数量");
		columnNames.add("总金额");
		data=new Vector<Vector>();
		tablemodel=new DefaultTableModel(data, columnNames);
		table=new JTable(tablemodel)
		{
			public boolean isCellEditable(int row,int clumn){
				return false;
			}
		};
		jp_goodsell_center_top.setBorder(BorderFactory.createTitledBorder(""));
		jp_goodsell_center_top.add(btn_addgoods);
		jp_goodsell_center_top.add(btn_inout);
		jp_goodsell_center_top.add(new JLabel());
		jp_goodsell_center_top.add(new JLabel());
		jp_goodsell_center_top.add(new JLabel());
		jp_goodsell_center_top.add(new JLabel());
		jp_goodsell_center.add(jp_goodsell_center_top,BorderLayout.NORTH);
		jp_goodsell_center_center.setLayout(new GridLayout(1,1));
		jp_goodsell_center_center.add(new JScrollPane(table));
		jp_goodsell_center.add(jp_goodsell_center_center);
		jp_goodsell.add(jp_goodsell_center,BorderLayout.CENTER);
		
		//底部设计----------------------------------------------------
		jp_goodsell_bottom=new JPanel();
		jp_goodsell_bottom.setLayout(new GridLayout(2,1));
		jp_goodsell_bottom1=new JPanel();
		tf_wantmoney=new JTextField(6);
		tf_paymoney=new JTextField(6);
		aModel1Vector2=new EmployeesDao().getEmployees();
		aModel2=new DefaultComboBoxModel(aModel1Vector2);
		cobx_agent=new JComboBox(aModel2);
		aModel1Vector3=new PayWaysDao().getPayWaysInfo();
		aModel3=new DefaultComboBoxModel(aModel1Vector3);
		cobx_pay=new JComboBox(aModel3);
		tf_order=new JTextField(10);
		jp_goodsell_bottom2=new JPanel();
		tf_bz=new JTextField(48);
		btn_ok=new JButton("确定");
		btn_exit=new JButton("退出");
		jp_goodsell_bottom1.add(new JLabel("应收金额："));
		tf_wantmoney.setText("0.00");
		jp_goodsell_bottom1.add(tf_wantmoney);
		jp_goodsell_bottom1.add(new JLabel("实收金额："));
		tf_paymoney.setText("0.00");
		jp_goodsell_bottom1.add(tf_paymoney);
		jp_goodsell_bottom1.add(new JLabel("支付方式："));
		jp_goodsell_bottom1.add(cobx_pay);
		jp_goodsell_bottom1.add(new JLabel("经办人："));
		jp_goodsell_bottom1.add(new JScrollPane(cobx_agent));
		jp_goodsell_bottom1.add(new JLabel("原始单号："));
		
		jp_goodsell_bottom1.add(tf_order);
		jp_goodsell_bottom.add(jp_goodsell_bottom1);
		jp_goodsell_bottom2.add(new JLabel("备注："));
		jp_goodsell_bottom2.add(tf_bz);
		jp_goodsell_bottom2.add(btn_ok);
		jp_goodsell_bottom2.add(btn_exit);
		jp_goodsell_bottom.add(jp_goodsell_bottom2);
		jp_goodsell.add(jp_goodsell_bottom,BorderLayout.SOUTH);
		/**
		 *销售单审核选项卡
		 *@author yc 
		 */
		//顶部设计------------------------------------------------------
		jp_soc_top=new JPanel();
		btn_look=new JButton("查看单据");
		btn_delete=new JButton("删除单据");
		btn_check=new JButton("单据审核");
		btn_out=new JButton("导出");
		btn_stamp=new JButton("打印");
		btn_tui=new JButton("退出");
		jp_soc_top.setLayout(new GridLayout(1,9));
		jp_soc_top.setBorder(BorderFactory.createTitledBorder(""));
		jp_soc_top.add(btn_look);
		jp_soc_top.add(btn_delete);
		jp_soc_top.add(btn_look);
		jp_soc_top.add(btn_check);
		jp_soc_top.add(btn_stamp);
		jp_soc_top.add(btn_tui);
		jp_soc_top.add(new JLabel());
		jp_soc_top.add(new JLabel());
		jp_soc_top.add(new JLabel());
		jp_sellorderscheck.add(jp_soc_top,BorderLayout.NORTH);
		
		//中部设计-----------------------------------------------------
		jp_soc_center=new JPanel();
		jp_soc_center_p1=new JPanel();
		jp_soc_center_p2=new JPanel();
		jp_p1_top=new JPanel();
		jp_p1_center=new JPanel();
		tf_soc_name=new JTextField(15);
		btn_soc_check1=new JButton("搜索");
		tf_soc_check=new JTextField(15);
		btn_soc_check2=new JButton("查询");
		columnNames1=new Vector();
		columnNames2=new Vector();
		for (String str:arr1) {
			columnNames1.add(str);
		}
		for (int i = 0; i < arr2.length; i++) {
			columnNames2.add(arr2[i]);
		}
		table1model=new DefaultTableModel(date1,columnNames1);
		table2model=new DefaultTableModel(date2,columnNames2);
		table1=new JTable(table1model);
		table2=new JTable(table2model);
		jp_p2_top=new JPanel();
		jp_p2_center=new JPanel();
		btn_add=new JButton("增加商品");
		btn_alter=new JButton("修改商品");
		btn_del=new JButton("删除商品");
		jp_soc_center.setLayout(new GridLayout(2,1));
		jp_p1_top.add(new JLabel("客户名称："));
		ret2=new CastUtil().vectorToCustomer(new CastUtil().objectToVector(new CustomDao().getCustomers()).get(0));
		tf_soc_name.setText(ret2.getName());
		jp_p1_top.add(tf_soc_name);
		jp_p1_top.add(btn_soc_check1);
		jp_p1_top.add(new JLabel("    输入单据相关信息进行查询："));
		jp_p1_top.add(tf_soc_check);
		jp_p1_top.add(btn_soc_check2);
		jp_soc_center_p1.setLayout(new BorderLayout());
		jp_soc_center_p1.add(jp_p1_top,BorderLayout.NORTH);
		jp_p1_center.setLayout(new GridLayout(1,1));
		jp_p1_center.add(new JScrollPane(table1));
		jp_soc_center_p1.add(jp_p1_center,BorderLayout.CENTER);
		jp_soc_center.add(jp_soc_center_p1);
		
		jp_p2_top.setLayout(new GridLayout(1,9));
		jp_p2_top.setBorder(BorderFactory.createTitledBorder(""));
		jp_p2_top.add(new JLabel("供货商："));
		jp_p2_top.add(new JLabel("普通供货商"));
		jp_p2_top.add(new JLabel());
		jp_p2_top.add(new JLabel());
		jp_p2_top.add(new JLabel());
		jp_p2_top.add(btn_add);
		jp_p2_top.add(btn_alter);
		jp_p2_top.add(btn_del);
		jp_p2_top.add(new JLabel());
		jp_soc_center_p2.setLayout(new BorderLayout());
		jp_soc_center_p2.add(jp_p2_top,BorderLayout.NORTH);
		jp_p2_center.setLayout(new GridLayout(1,1));
		jp_p2_center.add(new JScrollPane(table2));
		jp_soc_center_p2.add(jp_p2_center,BorderLayout.CENTER);
		jp_soc_center.add(jp_soc_center_p2);
		jp_sellorderscheck.add(jp_soc_center,BorderLayout.CENTER);
		/**
		 * 监听方法
		 * @author ycd
		 */
		//查找seek
		btn_seek.addActionListener(new ActionListener() {
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
		//添加商品
		btn_addgoods.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddGoodsModelWindow	agmw=new AddGoodsModelWindow("增加商品(商品销售)");
				Vector<Vector> data3=agmw.data3;
				double wantMoney=0;
				for( Vector data_1:data3){
					Vector data_1_1=new Vector();
					data_1_1.add(0, data_1.get(0));
					data_1_1.add(1, data_1.get(1));
					data_1_1.add(2, data_1.get(2));
					data_1_1.add(3, "");//规格
					data_1_1.add(4, data_1.get(3));
					data_1_1.add(5, data_1.get(6));
					data_1_1.add(6, data_1.get(7));
					data.add(data_1_1);
					wantMoney += Double.parseDouble(data_1.get(7).toString());
				}
				tf_wantmoney.setText(wantMoney+"");
				tf_paymoney.setText(wantMoney+"");
				tablemodel=new DefaultTableModel(data,columnNames);
				table.setModel(tablemodel);
				table.updateUI();
				
			}
		});

		//退出
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				GoodSellModelWindow.this.setVisible(false);
			}
		});
		btn_tui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GoodSellModelWindow.this.setVisible(false);
			}
		});
		/**
		 * 导入导出菜单
		 */
		btn_inout.addMouseListener(new mouseClicked());
		//导入
		mitem_in.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImportExportHelp a = new ImportExportHelp();
				//取a对象的data数据
				Vector<Vector> corectData = new Vector<>();
				for(int i=0;i<a.data.size();i++)
					if(a.data.get(i).lastElement().toString().trim().equals("可导入"))
						corectData.add(a.data.get(i));
					table.setModel(new DefaultTableModel(corectData, columnNames));
				//计算应付金额
				double wantMoney = 0;
				for(int i=0;i<corectData.size();i++) {
					wantMoney += Double.parseDouble(corectData.get(i).lastElement().toString());
				}	
				tf_wantmoney.setText(wantMoney+"");
				tf_paymoney.setText(wantMoney+"");
			}
		});
		//导出
		mitmem_out.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ImportExportHelp();
			}
		});
		/**
		 *搜索监听
		 *@author yc 
		 */
		btn_soc_check1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomInfoModelWindow cimw = new CustomInfoModelWindow();
					ret2=cimw.ret;
				try {
					if(cimw.table.isRowSelected(cimw.table.getSelectedRow())){
						ret2 = new CastUtil().vectorToCustomer(cimw.retData);
						tf_soc_name.setText(ret2.getName());
					}else{
						tf_soc_name.setText(cimw.ret.getName());
					}
					
				} catch (java.lang.NullPointerException e2) {
					//JOptionPane.showMessageDialog(null, "请选择数据");
				}
				
			
			}
		});
		
		//确定形成单据
		btn_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {	
					data.get(0);
					String id=iddan;
					String odate=GoodSellModelWindow.this.tf_selldate.getText().trim();
					Double wantMoney=Double.parseDouble(GoodSellModelWindow.this.tf_wantmoney.getText().trim());
					Double payMoney=Double.parseDouble(GoodSellModelWindow.this.tf_paymoney.getText().trim());
					Depot depot=(Depot) GoodSellModelWindow.this.cobx_depots.getSelectedItem();
					Employee agent=(Employee) GoodSellModelWindow.this.cobx_agent.getSelectedItem();
					Admin operator=AdminService.admin;
					String bz=GoodSellModelWindow.this.tf_bz.getText().trim();
					PayWay payWay=(PayWay) GoodSellModelWindow.this.cobx_pay.getSelectedItem();
					Customer customer=GoodSellModelWindow.this.ret1;
					SellOrder sellorder=new SellOrder(id, odate, depot, wantMoney, payMoney, agent, operator, bz, payWay, customer);
					new  SellService().addOrder(sellorder, new CastUtil().vectorToGoods_sell(data), true);
					JOptionPane.showMessageDialog(null, "添加销售单据成功!!!");
					GoodSellModelWindow.this.setVisible(false);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "单据中没有业务不能保存");
				}
				
			}
		});
		
		this.setTitle("商品销售");
		getContentPane().add(tabbed);
		this.setBounds(300, 100, 900, 550);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setVisible(true);	
	}
		
	
	//导入导出菜单
	public class mouseClicked extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			if(e.getButton()==1){
				pop_in_out.show(btn_inout, e.getX(), e.getY());
			}
			
		}
	}
	public static void main(String[] args) {
		new AdminService().Login("admin", "123");
		new GoodSellModelWindow();
	}
}
