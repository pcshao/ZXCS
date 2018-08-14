package gui.yc;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import bean.Admin;
import bean.Customer;
import bean.Depot;
import bean.Employee;
import bean.PayWay;
import bean.orders.SellOrder;
import bean.orders.SellOrder_tui;

import dao.CustomDao;
import dao.DepotsDao;
import dao.EmployeesDao;
import dao.PayWaysDao;
import dao.SellOrdersDao;

import service.AdminService;
import service.SellService;
import util.CastUtil;
import util.MyDateChooser;
//顾客退货
public class CustomReturnGoodsModelWindow extends JDialog{
	MyDateChooser dc1,dc2,dc3;
	JTabbedPane tabbed;
	JPanel jp_creturn;							//顾客退货面板
	JPanel jp_creturn_top,jp_creturn_center,jp_creturn_bottom;
	JPanel jp_creturn_top_top,jp_creturn_top_center;							//顶部中的面板
	JPanel jp_creturn_center_top,jp_creturn_center_center;						//中部中的面板
	JPanel jp_creturn_bottom1,jp_creturn_bottom2;								//底部中的面板
	JLabel lbl_ordernumber,lbl_returngoods;
	JTextField tf_name,tf_selldate;									//顶部文本
	DefaultComboBoxModel aModel1,aModel3;
	JComboBox cobx_depots,cobx_agent,cobx_pay;
	Vector aModel1Vector2,aModel1Vector3;
	DefaultComboBoxModel aModel2;
	JTextField tf_wantmoney,tf_paymoney,tf_bz;							//底部文本
	JButton btn_seek,btn_addgoods,btn_returnall,btn_ok,btn_exit;
	Vector columnNames;
	Vector<Vector> data;
	DefaultTableModel tablemodel;
	JTable table;
	
	JPanel jp_returngdcheck;					//退货查询面板
	JPanel jp_rgc_top,jp_rgc_center;			//分为顶部和中部
	JPanel jp_tab_p1,jp_tab_p2,jp_tab_p3;
	JPanel jp_tab_p1_1,jp_tab_p1_2,jp_tab_p2_1,jp_tab_p2_2;	//中部放了两个面板
	JTabbedPane tabbed_center;
	JButton btn_look,btn_check,btn_out,btn_tui;
	JTextField tf_rgc_name,tf_rgc_check,tf_rgc_order;
	JButton btn_soc_check1,btn_soc_check2;
	Vector<Vector> date1,date2,date3,date4,date5;
	Vector columnNames1,columnNames2,columnNames3,columnNames4,columnNames5;
	DefaultTableModel table1model,table2model,table3model,table4model,table5model;
	JTable table1,table2,table3,table4,table5;
	String[] arr1={"单据号","开单日期","客户名称","仓库名称","应收金额","实收金额","欠款金额","单据类型","经办人","操作员"};		
	String[] arr2={"商品编号","商品名称","单位","单价","数量","总金额","规格型号","颜色"};
	String[] arr3={"商品编号","商品名称","单位","销售数量","销售总金额","库存数量","规格型号","颜色","生产厂商"};
	String[] arr4={"客户名称","单据号","开单日期","单位","单价","数量","总金额","规格型号","颜色"};
	String[] arr5={"销售日期","单据号","商品编号","商品名称","单价","数量","总金额","利润","利润率","单位","规格型号","仓库","经办人","客户名称" };
	
	
	//生成订单所需数据
	Customer ret;			//客户
	Vector aModel1Vector;	//仓库
	String iddan;				//订单号
							
	
	public CustomReturnGoodsModelWindow(){
		dc1=MyDateChooser.getInstance("yyyy-MM-dd");
		dc2=MyDateChooser.getInstance("yyyy-MM-dd");
		dc3=MyDateChooser.getInstance("yyyy-MM-dd");
		tabbed=new JTabbedPane();		//选项卡
		jp_creturn=new JPanel();		//顾客退货面板
		jp_returngdcheck=new JPanel();//退货查询面板
		
		tabbed.add("顾客退货",jp_creturn);
		tabbed.add("退货查询",jp_returngdcheck);
		jp_returngdcheck.setLayout(new BorderLayout());	//设置边界布局
		jp_creturn.setLayout(new BorderLayout());	
		
/*-----------------------------------------------顾客退货面板------------------------------------------------------*/		
		//顶部设计
		jp_creturn_top=new JPanel();
		jp_creturn_top_top=new JPanel();
		lbl_returngoods=new JLabel("顾客退货");
		iddan=new SellOrdersDao().getSellTuiOrderId();
		lbl_ordernumber=new JLabel("单号:"+iddan);
		jp_creturn_top_center=new JPanel();
		tf_name=new JTextField(15);
		btn_seek=new JButton("查找");
		aModel1Vector=new DepotsDao().getDepots();
		aModel1=new DefaultComboBoxModel(aModel1Vector);
		cobx_depots=new JComboBox(aModel1);
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
		jp_creturn_top_center.add(new JLabel("客户名称："));
		ret=new CastUtil().vectorToCustomer(new CastUtil().objectToVector(new CustomDao().getCustomers()).get(0));
		tf_name.setText(ret.getName());
		jp_creturn_top_center.add(tf_name);
		jp_creturn_top_center.add(btn_seek);
		jp_creturn_top_center.add(new JLabel("出货仓库"));
		jp_creturn_top_center.add(new JScrollPane(cobx_depots));
		jp_creturn_top_center.add(new JLabel("退货日期"));
		dc1.register(tf_selldate);
		tf_selldate.setText(dc1.getStrDate());
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
		columnNames.add("颜色");
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
		jp_creturn_center_center.add(new JScrollPane(table));
		jp_creturn_center.add(jp_creturn_center_center);
		jp_creturn.add(jp_creturn_center,BorderLayout.CENTER);
		
		//底部设计----------------------------------------------------
		jp_creturn_bottom=new JPanel();
		jp_creturn.add(jp_creturn_bottom,BorderLayout.SOUTH);	
		jp_creturn_bottom.setLayout(new GridLayout(2,1));
		jp_creturn_bottom1=new JPanel();
		jp_creturn_bottom.add(jp_creturn_bottom1);
		jp_creturn_bottom1.add(new JLabel("应退金额："));
		aModel1Vector3=new PayWaysDao().getPayWaysInfo();
		aModel3=new DefaultComboBoxModel(aModel1Vector3);
		cobx_pay=new JComboBox(aModel3);
		tf_wantmoney=new JTextField(15);
		tf_wantmoney.setText("0.00");
		jp_creturn_bottom1.add(tf_wantmoney);
		jp_creturn_bottom1.add(new JLabel("实退	金额"));
		tf_paymoney=new JTextField(15);
		tf_paymoney.setText("0.00");
		jp_creturn_bottom1.add(tf_paymoney);
		jp_creturn_bottom1.add(new JLabel("支付方式："));
		jp_creturn_bottom1.add(cobx_pay);
		jp_creturn_bottom1.add(new JLabel("经办人"));
		aModel1Vector2=new EmployeesDao().getEmployees();
		aModel1=new DefaultComboBoxModel(aModel1Vector2);
		cobx_agent=new JComboBox(aModel1);
		jp_creturn_bottom1.add(new JScrollPane(cobx_agent));
		jp_creturn_bottom.add(jp_creturn_bottom1);
		
		jp_creturn_bottom2=new JPanel();
		jp_creturn_bottom2.add(new JLabel("备注"));
		tf_bz=new JTextField(48);
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
		jp_rgc_top.setBorder(BorderFactory.createTitledBorder(""));
		jp_rgc_top.add(btn_look);
		jp_rgc_top.add(btn_check);
		jp_rgc_top.add(btn_out);
		jp_rgc_top.add(btn_tui);
		jp_rgc_top.add(new JLabel("开单日期"));
		dc2.register(tf_rgc_name);
		tf_rgc_name.setText(dc2.getStrDate());
		jp_rgc_top.add(tf_rgc_name);
		jp_rgc_top.add(new JLabel("至"));
		dc3.register(tf_rgc_check);
		tf_rgc_check.setText(dc3.getStrDate());
		jp_rgc_top.add(tf_rgc_check);
		jp_rgc_top.add(new JLabel("客户/单据号"));
		jp_rgc_top.add(tf_rgc_order);
		jp_rgc_top.add(btn_soc_check1);
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
		table1model=new DefaultTableModel(date1,columnNames1);
		table1=new JTable(table1model);
		table2model=new DefaultTableModel(date2,columnNames2);
		table2=new JTable(table2model);
		table3model=new DefaultTableModel(date3,columnNames3);
		table3=new JTable(table3model);
		table4model=new DefaultTableModel(date4,columnNames4);
		table4=new JTable(table4model);
		table5model=new DefaultTableModel(date5,columnNames5);
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
	 * @author yc
	 * 		 
	 */
		//查找seek
		btn_seek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomInfoModelWindow cimw = new CustomInfoModelWindow();
				ret=cimw.ret;
				try {
					if(cimw.table.isRowSelected(cimw.table.getSelectedRow())){
						ret = new CastUtil().vectorToCustomer(cimw.retData);
						tf_name.setText(ret.getName());
					}else{
						tf_name.setText(cimw.ret.getName());
					}
					
				} catch (java.lang.NullPointerException e2) {
					//JOptionPane.showMessageDialog(null, "请选择数据");
				}
			}
		});
		//添加退货商品
		btn_addgoods.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddGoodsModelWindow agmw=new AddGoodsModelWindow("增加商品(销售退货)");
				Vector<Vector> data3=agmw.data3;
				for( Vector data_1:data3){
					Vector data_1_1=new Vector();
					data_1_1.add(0, data_1.get(0));
					data_1_1.add(1, data_1.get(1));
					data_1_1.add(2, data_1.get(2));
					data_1_1.add(3, "");//规格
					data_1_1.add(4, data_1.get(3));
					data_1_1.add(5, data_1.get(4));
					data_1_1.add(6, data_1.get(5));
					data_1_1.add(7, data_1.get(6));
					data_1_1.add(8, data_1.get(7));
					data.add(data_1_1);
					tf_wantmoney.setText(data_1.get(7).toString());
					tf_paymoney.setText(data_1.get(7).toString());
					}
				
				tablemodel=new DefaultTableModel(data,columnNames);
				table.setModel(tablemodel);
				table.updateUI();
			}
		});
		//整单退货
		btn_returnall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SalesDocumentsCheckModelWindow();
			}
		});
		
		
		//退出
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomReturnGoodsModelWindow.this.setVisible(false);
			}
		});
		btn_tui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomReturnGoodsModelWindow.this.setVisible(false);
			}
		});
		//确定形成单据
		btn_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String id=iddan;
					String odate=CustomReturnGoodsModelWindow.this.tf_selldate.getText().trim();
					Double wantMoney=-Double.parseDouble(CustomReturnGoodsModelWindow.this.tf_wantmoney.getText().trim());
					Double payMoney=-Double.parseDouble(CustomReturnGoodsModelWindow.this.tf_paymoney.getText().trim());
					Depot depot=(Depot) CustomReturnGoodsModelWindow.this.cobx_depots.getSelectedItem();
					Employee agent=(Employee) CustomReturnGoodsModelWindow.this.cobx_agent.getSelectedItem();
					Admin operator=AdminService.admin;
					String bz=CustomReturnGoodsModelWindow.this.tf_bz.getText().trim();
					PayWay payWay=(PayWay) CustomReturnGoodsModelWindow.this.cobx_pay.getSelectedItem();
					Customer customer=CustomReturnGoodsModelWindow.this.ret;
					SellOrder_tui sellorder=new SellOrder_tui(id, odate, depot, wantMoney, payMoney, agent, operator, bz, payWay, customer);
					new  SellService().addOrder(sellorder, new CastUtil().vectorToGoods(data));
					JOptionPane.showMessageDialog(null, "添加退货单据成功!!!");
					CustomReturnGoodsModelWindow.this.setVisible(false);
				} catch (java.lang.NullPointerException e1) {
					JOptionPane.showMessageDialog(null, "单据中没有业务不能保存");
				}
				
			}
		});
		this.setTitle("顾客退货");
		this.add(tabbed);
		this.setLocationRelativeTo(null);
		this.setBounds(300, 100, 850, 550);
		this.setModal(true);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new AdminService().Login("admin", "123");
		new CustomReturnGoodsModelWindow();
	}
}
