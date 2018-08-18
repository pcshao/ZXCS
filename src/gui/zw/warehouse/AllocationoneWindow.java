package gui.zw.warehouse;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
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
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

import bean.Depot;
import bean.Employee;
import bean.orders.DbOrder;
import dao.AdminDao;
import dao.DbOrdersDao;
import dao.DepotsDao;
import dao.EmployeesDao;
import dao.GoodsDao;
import dao.OrderDao;
import gui.zw.modewindow.AInorderDiagWindow;
import gui.zw.modewindow.AddGoodsModelWindow;
import service.AdminService;
import service.DepotService;
import util.CastUtil;
import util.MyDateChooser;
import java.awt.Color;

/*库存调拨单
 * 库存调查
 * 
 * 
 * */
public class AllocationoneWindow extends JFrame {
	JTabbedPane tp_1,tp2,tp3,tp4;//选项卡
	JPanel p1,p2,p3,p4,p5,jp_o,jp_t,jp_center,jp_top,jp_low;
	JPanel jp_center_top,jp_center_low,jp_low_top,jp_low_low,jp_top_top,jp_top_low,jp_top_center;
	JPanel jp_t_top_top,jp_t_top_low,low,jpt_low_top,jp_t_low_low,jp_t_low_top,jp_t_low_center;
	JPanel jp_t_top,jp_t_low;
	JButton sure;
	JButton btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8;
	 JCheckBox cb1,cb2,cb3,cb4;//多选
	 JRadioButton rb5,rb6;//单选
	Icon c1,c2,c3,c4,c5,c6;
	JTextField tf_1,tf_2,tf_3,tf_4,tf_5,tf_6,tf_7,tf1_1,tf1_2;
	JComboBox c_add,c_add1,c_add2;//下拉框
	Vector items=new Vector();
	JLabel l1,l2,l3;
	JFrame jf1;
	JButton b1,b2,b3,b4,b5,b6,b7,b8,b9;
	//表格
	JTable table;
	Vector<Vector> rowData=new Vector<Vector>();
	Vector<Vector> nowVector;
	Vector columnNames=new Vector();
	Vector rd1=new Vector();
	Vector rd2=new Vector();
	Vector rd3=new Vector();
	DefaultTableModel model;
	GoodsDao goodsDao;
	DbOrdersDao dborderdao;
	JTable table1;
	Vector<Vector> rowData1=new Vector<Vector>();	
	Vector columnNames1=new Vector();
	
	DefaultTableModel model1;
	//第二个表格
	JTable table2;
	Vector<Vector> rowData2;
	Vector columnNames2=new Vector();
	DefaultTableModel model2;
	MyDateChooser dc1,dc2_1,dc2_2;
	DefaultComboBoxModel modelc ,modelc1,modelc2;
	
	//注册服务
	DepotService depotService;
	
	Vector<Vector> data3;
	public AllocationoneWindow(){
		
		goodsDao=new GoodsDao();
		depotService = new DepotService();
		dborderdao = new DbOrdersDao();
		
		modelc=new DefaultComboBoxModel(new DepotsDao().getDepots());
		c_add=new  JComboBox();
		c_add.setModel(modelc);
		
		modelc1=new DefaultComboBoxModel(new DepotsDao().getDepots());
		c_add1=new  JComboBox(modelc1);
		
		modelc2=new DefaultComboBoxModel(new EmployeesDao().getEmployees());
		c_add2=new  JComboBox(modelc2);
		
		b1=new JButton("查找商品");
		b2=new JButton("确定 ");
		b3=new JButton("退出 ");
		b4=new JButton("增加商品");
		
		dc1=MyDateChooser.getInstance("yyyy-MM-dd");
		dc2_1=MyDateChooser.getInstance("yyyy-MM-dd");
		dc2_2=MyDateChooser.getInstance("yyyy-MM-dd");
		
		p1=new JPanel();
		p2=new JPanel();
		p3=new JPanel();p4=new JPanel();p5=new JPanel();
		//p6=new JPanel();p7=new JPanel();p8=new JPanel();
		tf1_1=new JTextField(10);
		tf_1=new JTextField(15);
		tf_1.setEditable(false);
		tf_1.setForeground(Color.RED);tf_2=new JTextField(10);tf_3=new JTextField(10);
		tf_4=new JTextField(8);tf_5=new JTextField(50);tf_6=new JTextField(20);
		tf_7=new JTextField(10);
		jp_center=new JPanel();jp_top=new JPanel();jp_low=new JPanel();
		jp_center_top=new JPanel();jp_center_low=new JPanel();
		jp_low_top=new JPanel();jp_low_low=new JPanel();
		jp_top_top=new JPanel();jp_top_low=new JPanel();
		jp_top_center=new JPanel();
		tp_1=new JTabbedPane();
		jp_o=new JPanel();
		tf1_2=new JTextField(10);
		tf1_1=new JTextField(8);
		jp_t=new JPanel();
		tp_1.add("调拨单查询",jp_t);
		
		//pt组件 实例化
		jp_t_top=new JPanel();
		jp_t_top_top=new JPanel();
		jp_t_low=new JPanel();
		jp_t_low_low=new JPanel();
		btn_1=new JButton("\u67E5\u8BE2\u5355\u53F7");
		btn_2=new JButton("\u8BF7\u5728\u5DE6\u4FA7\u6DFB\u52A0\u8C03\u62E8\u5355");
		btn_3=new JButton("修改单据");
		btn_4=new JButton("删除单据");
		btn_6=new JButton("确定");
		btn_7=new JButton("退出");
		
		//top 添加
		jp_t_top_top.add(btn_2);
		jp_t_top_top.add(btn_3);
		jp_t_top_top.add(btn_4);
		//此处应添加时间
		jp_t_top_top.add(new JLabel("调拨时间"));
		tf1_1.setText(dc2_1.getStrDate());
		dc2_1.register(tf1_1); 
		jp_t_top_top.add(tf1_1);	
		jp_t_top_top.add(btn_1);//高级查询
		jp_t_top_top.add(tf1_2);	
		table1=new JTable(model1);
		table2=new JTable(model2);
		//pt界面
		jp_t.setLayout(new GridLayout(2,1));
		//上层
		jp_t.add(jp_t_top);
		jp_t_top.setLayout(new BorderLayout());
		jp_t_top.add(jp_t_top_top,BorderLayout.NORTH);
		jp_t_top.add(new JScrollPane(table1),BorderLayout.CENTER);
		
		//下层
		jp_t.add(jp_t_low);
		jp_t_low.setLayout(new BorderLayout());
		jp_t_low.add(new JLabel("仓库详细信息:"),BorderLayout.NORTH);
		jp_t_low.add(new JScrollPane(table2),BorderLayout.CENTER);
		jp_t_low.add(jp_t_low_low,BorderLayout.SOUTH);
		jp_t_low_low.add(btn_6);
		jp_t_low_low.add(btn_7);
		btn_1.addActionListener(new ActionListener() {
			// 调拨单查询   
			@Override
			public void actionPerformed(ActionEvent e) {
				String info=tf1_2.getText().trim();
				model1=new DefaultTableModel(goodsDao.getDbOrdersById(info), columnNames1);
				table1.setModel(model1);
				table1.updateUI();
			}
		});
		
		btn_2.addActionListener(new ActionListener() {
			//增加单据
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new AInorderDiagWindow();
				
			}
		});
		btn_3.addActionListener(new ActionListener() {
			//修改单据
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new AInorderDiagWindow();
				
			}
		});
		btn_4.addActionListener(new ActionListener() {
			//删除单据
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new AInorderDiagWindow();
				
			}
		});
		
		btn_7.addActionListener(new ActionListener() {
			//退出
			@Override
			public void actionPerformed(ActionEvent e) {
				AllocationoneWindow.this.setVisible(false);
			}
		});
		table1.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
				String orderId = (String) table1.getValueAt(table1.getSelectedRow(), 0);
				model2 = new DefaultTableModel(dborderdao.getDbOrderDetails(orderId), columnNames);
				table2.setModel(model2);
				table2.updateUI();
			}
		});
		l1=new JLabel("库存调拨");
		//po最上层
		tp_1.add("调拨商品",jp_o);
		l1.setFont(new Font("微软雅黑",5,30));	
		jp_top_top.add(l1);
		jp_top_center.add(new Label("调出仓库:"));jp_top_center.add(c_add);
		jp_top_center.add(new Label("调入仓库:"));jp_top_center.add(c_add1);
		jp_top_center.add(new Label("调拨时期"));
		tf_3.setText(dc1.getStrDate());
		dc1.register(tf_3);jp_top_center.add(tf_3);
		jp_top_center.add(new Label("调拨单号"));jp_top_center.add(tf_1);
		//po中间表格

		/*
		 * tf_1 生成调拨订单号
		 */
		tf_1.setText(new OrderDao().getDbOrderId());
		
		columnNames.add("商品编号");
		columnNames.add("商品名称");
		columnNames.add("调拨数量");	
		columnNames.add("单位");
		columnNames.add("规格");	
		//这里gdao.getgoodstoreInfo()
		model=new DefaultTableModel(null, columnNames);
		table=new JTable(model);
		//p_center_low.add(new JScrollPane(table));
		//ImageIcon buttonicon=new 	ImageIcon();
		//po最下层
		jp_low_top.add(new Label("经办人"));jp_low_top.add(c_add2);
		jp_low_top.add(new Label("备注"));jp_low_top.add(tf_5);
		jp_low_low.add(b2);jp_low_low.add(b3);
						
		//库存调拨界面
		//top面板分为上下面板
		jp_o.setLayout(new BorderLayout());
		jp_o.add(jp_top,BorderLayout.NORTH);		
		jp_top.setLayout(new GridLayout(3,1));
		jp_top.add(jp_top_top);
		jp_top.add(	jp_top_center);
		jp_top.add(	jp_top_low);//jp_top.add(tf_7);	
		jp_top_low.setBorder(BorderFactory.createTitledBorder("商品库存查询"));
		jp_top_low.add(b4);
		jp_top_low.add(b1);jp_top_low.add(tf_6);
		jp_top_low.add(new JLabel("（根据商品编号查找）"));
		//中间
		jp_o.add(new JScrollPane(table),BorderLayout.CENTER);
		/*p_center.setLayout(new BorderLayout());
		p_center.add(p_center_top,BorderLayout.NORTH);
		p_center.add(table,BorderLayout.CENTER);
		*/
		//下层
		jp_o.add(jp_low,BorderLayout.SOUTH);
		jp_low.setLayout(new GridLayout(2,1));
		jp_low.add(jp_low_top);
		jp_low.add(jp_low_low);jp_t_top_low=new JPanel();
		jp_t_low_top=new JPanel();jp_t_low_center=new JPanel();
		//jp_t_top_top.add(btn_5);//jp_t_top_top.add(btn_6);
		
		//top第一个表格
				columnNames1.add("调拨单号");
				columnNames1.add("开单日期");
				columnNames1.add("被调仓库");
				columnNames1.add("调入仓库");
				columnNames1.add("经办人");
				columnNames1.add("操作员");
				columnNames1.add("备注");
				model1=new DefaultTableModel(dborderdao.getpdOrders(), columnNames1);
		//下层表格
				columnNames2.add("编号");
				columnNames2.add("名称");
				columnNames2.add("联系人");
				columnNames2.add("联系电话");
				columnNames2.add("联系地址");
				columnNames2.add("备注");
						//	这里应该是gdao.getdepotsInfo()
				model2=new DefaultTableModel(rowData2, columnNames2);
		//添加事件
		b1.addActionListener(
				//库存；量查询 根据编号查找
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String info=tf_6.getText().trim();
						model=new DefaultTableModel(goodsDao.getgoodsthreequeery(info), columnNames);
						table.setModel(model);
						table.updateUI();
					}
				}
			);
		b3.addActionListener(new ActionListener() {
			//退出
			public void actionPerformed(ActionEvent e) {
				AllocationoneWindow.this.setVisible(false);
			}
		});
		b4.addActionListener(new ActionListener() {
			//添加商品
			public void actionPerformed(ActionEvent e) {
				//System.exit(0);
				AddGoodsModelWindow agmw = new AddGoodsModelWindow("库存调拨  （增加商品）");
				 data3=agmw.data3;
				 nowVector = new Vector<>();
				 for(int i=0;i<data3.size();i++) {
					 Vector vv = new Vector<>();
					 vv.add(data3.get(i).get(0));
					 vv.add(data3.get(i).get(1));
					 vv.add(data3.get(i).get(2));
					 vv.add(data3.get(i).get(6));
					 nowVector.add(vv);
				 }
				 model=new DefaultTableModel(nowVector, columnNames);
				 table.setModel(model);
				 table.updateUI();
			}
		});
		/**
		 * 生成调拨单
		 */
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DbOrder dbOrder = new DbOrder();
				dbOrder.setId(tf_1.getText());
				dbOrder.setOdate(tf_3.getText());
				dbOrder.setFromDepot((Depot)c_add.getSelectedItem());
				dbOrder.setToDepot((Depot)c_add1.getSelectedItem());
				dbOrder.setAgent((Employee)c_add2.getSelectedItem());
				dbOrder.setOperator(AdminService.admin);
				dbOrder.setBz(tf_5.getText()+"");
				depotService.transDepot(dbOrder, new CastUtil().vectorToGoods(nowVector, new DbOrder()));
				new JOptionPane().showMessageDialog(AllocationoneWindow.this, "调拨成功！");
			}
		});
		/**
		 * 点击调拨单更新下面表格的详情数据
		 */
		
		this.setTitle("库存调拨");
		getContentPane().add(tp_1);
		
		this.setBounds(100, 100, 800, 600);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setVisible(true);
		
	}
	public static void main(String[] args) {
		new AdminService().Login("admin", "123");
		new AllocationoneWindow();
	}

}
