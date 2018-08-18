package gui.yc;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import bean.Admin;
import bean.Customer;
import bean.Depot;
import bean.Employee;
import bean.Goods;
import bean.PayWay;
import bean.orders.SellOrder_tui;
import dao.GoodsDao;
import dao.OrderDao;
import dao.SellOrdersDao;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import service.SellService;
import util.CastUtil;
import util.ImportExportHelp;
import util.MyDateChooser;
//销售单据查询
public class SalesDocumentsCheckModelWindow extends JDialog{
	MyDateChooser dc1,dc2;
	JPanel jp_top,jp_center;			//分为顶部和中部
	JPanel jp_top_p1,jp_top_p2;
	JPanel jp_tab_p1,jp_tab_p2,jp_tab_p3;
	JPanel jp_tab_p1_1,jp_tab_p1_2,jp_tab_p2_1,jp_tab_p2_2;	//中部放了两个面板
	JTabbedPane tabbed_center;
	JButton btn_top1,btn_top2,btn_top3,btn_top4,btn_top5,btn_top6,btn_top7;
	JTextField tf_name,tf_check,tf_order;
	Vector<Vector> date1,date2,date3,date4,date5;
	Vector columnNames1,columnNames2,columnNames3,columnNames4,columnNames5;
	DefaultTableModel table1model,table2model,table3model,table4model,table5model;
	JTable table1,table2,table3,table4,table5;
	String[] arr1={"单号","开单日期","客户名称","仓库名称","应收金额","实收金额","欠款金额","经办人","操作员"};		
	String[] arr2={"商品编号","商品名称","单位","单价","数量","总金额","规格型号"};
	String[] arr3={"商品编号","商品名称","单位","销售数量","销售总金额","库存数量","规格型号"};
	String[] arr4={"客户名称","单据号","开单日期","单位","单价","数量","总金额","规格型号"};
	String[] arr5={"销售日期","单据号","商品编号","商品名称","单价","数量","总金额","单位","规格型号","仓库","经办人","客户名称"};
	
	
	public SalesDocumentsCheckModelWindow(){
		dc1=MyDateChooser.getInstance("yyyy-MM-dd");
		dc2=MyDateChooser.getInstance("yyyy-MM-dd");
		jp_top=new JPanel();
		jp_top_p1=new JPanel();
		jp_top_p2=new JPanel();
		btn_top1=new JButton("详细查找");
		btn_top2=new JButton("查看单据");
		btn_top3=new JButton("整单退货");
		btn_top4=new JButton("导出");
		btn_top5=new JButton("打印");
		btn_top6=new JButton("退出");
		btn_top7=new JButton("查询");
		tf_name=new JTextField(10);
		tf_check=new JTextField(10);
		tf_order=new JTextField(10);
		/**
		 * 顶部
		 * @author yc
		 */
		jp_top.setLayout(new GridLayout(2,1));
		jp_top_p1.setLayout(new GridLayout(1,8));
		jp_top_p1.setBorder(BorderFactory.createTitledBorder(""));
		jp_top_p1.add(btn_top1);
		jp_top_p1.add(btn_top2);
		jp_top_p1.add(btn_top3);
		jp_top_p1.add(btn_top4);
		jp_top_p1.add(btn_top5);
		jp_top_p1.add(btn_top6);
		jp_top_p1.add(new JLabel());
		jp_top_p1.add(new JLabel());
		jp_top.add(jp_top_p1);
		jp_top_p2.add(new JLabel("查询日期："));
		tf_name.setText(dc1.getStrDate());
		dc1.register(tf_name);
		jp_top_p2.add(tf_name);
		jp_top_p2.add(new JLabel("至"));
		tf_check.setText(dc2.getStrDate());
		dc2.register(tf_check);
		jp_top_p2.add(tf_check);
		jp_top_p2.add(new JLabel("按客户/单据号查询："));
		jp_top_p2.add(tf_order);
		jp_top_p2.add(btn_top7);
		jp_top.add(jp_top_p2);
		/**
		 * 中部
		 * @author yc
		 */
		jp_center=new JPanel();
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
		date1=new SellOrdersDao().getSellOrdersInfo(tf_name.getText(),tf_check.getText());
		table1model=new DefaultTableModel(date1,columnNames1);
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
		};
		table2model=new DefaultTableModel(date2,columnNames2);
		table2=new JTable(table2model){
			public boolean isCellEditable(int row,int clumn){
				return false;
			}
		};
		table3model=new DefaultTableModel(date3,columnNames3);
		table3=new JTable(table3model){
			public boolean isCellEditable(int row,int clumn){
				return false;
			}
		};
		table4model=new DefaultTableModel(date4,columnNames4);
		table4=new JTable(table4model){
			public boolean isCellEditable(int row,int clumn){
				return false;
			}
		};
		table5model=new DefaultTableModel(date5,columnNames5);
		table5=new JTable(table5model){
			public boolean isCellEditable(int row,int clumn){
				return false;
			}
		};
		jp_center.setLayout(new GridLayout(1,1));
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
		jp_center.add(tabbed_center);
		this.setLayout(new BorderLayout());
		this.add(jp_top,BorderLayout.NORTH);
		this.add(jp_center,BorderLayout.CENTER);
		/**
		 * 监听方法
		 * @author yc
		 */
		/**
		 * 详细查找
		 * @author yc
		 */
		btn_top1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "请选择条件点击查询！");
			}
		});
		/**
		 *打印
		 *@author yc
		 */
		btn_top5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "未检测到打印驱动，请检查打印设备！");
			}
		});
		/**
		 *导出
		 * @author yc
		 */
		btn_top4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser fchooser2=new JFileChooser();
					fchooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fchooser2.showSaveDialog(null);
					String str=fchooser2.getSelectedFile().getAbsolutePath();
					Vector<Vector> da=new Vector<Vector>();
					da.add(columnNames1);
					da.addAll(date1);
					ImportExportHelp.ExportData(da, str);
					JOptionPane.showMessageDialog(null, "导出成功！");
				} catch (RowsExceededException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (WriteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		/**
		 * 查看单据
		 * @author yc
		 */
		btn_top2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table1.isShowing()) {
					date1=new SellOrdersDao().getSellOrdersInfo();
					table1model=new DefaultTableModel(date1,columnNames1);
					table1.setModel(table1model);
					table1.updateUI();
				}else if(table3.isShowing()) {
					date3=new SellOrdersDao().getHuiZhongIngo();
					table3model=new DefaultTableModel(date3,columnNames3);
					table3.setModel(table3model);
					table3.updateUI();
				}else if(table5.isShowing()) {
					date5=new SellOrdersDao().getMingXiInfo();
					table5model=new DefaultTableModel(date5,columnNames5);
					table5.setModel(table5model);
					table5.updateUI();
				}
				
			}
		});
		btn_top6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				SalesDocumentsCheckModelWindow.this.setVisible(false);
			}
		});
		/**
		 *查询
		 *查单据表的相关信息
		 *@author yc 
		 */
		btn_top7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table1.isShowing()) {
					String str1=tf_name.getText();
					String str2=tf_check.getText();
					date1=new SellOrdersDao().getSellOrdersInfo(str1, str2);
					table1model=new DefaultTableModel(date1,columnNames1);
					table1.setModel(table1model);
					table1.updateUI();
				}else if(table3.isShowing()){
					String str1=tf_name.getText();
					String str2=tf_check.getText();
					date3=new SellOrdersDao().getHuiZhongIngo(str1, str2);
					table3model=new DefaultTableModel(date3,columnNames3);
					table3.setModel(table3model);
					table3.updateUI();
				}else if(table5.isShowing()){
					String str1=tf_name.getText();
					String str2=tf_check.getText();
					date5=new SellOrdersDao().getMingXiInfo(str1, str2);
					table5model=new DefaultTableModel(date5,columnNames5);
					table5.setModel(table5model);
					table5.updateUI();
				}
			}
		});
		/**
		 * 表格鼠标监听
		 * @author yc
		 * 数据转移
		 */
		table1.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {} 
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==1&&e.getClickCount()==2) {
					String str=date1.get(table1.getSelectedRow()).get(2).toString();
					date2=new GoodsDao().getGoodsInToAccount(str);
					table2model=new DefaultTableModel(date2, columnNames2);
					table2.setModel(table2model);
					table2.updateUI();
			}
			}
		});
		table3.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==1&&e.getClickCount()==2) {
					String str=date3.get(table3.getSelectedRow()).get(0).toString();
					date4=new SellOrdersDao().getHuiZhongInfo(str);
					table4model=new DefaultTableModel(date4, columnNames4);
					table4.setModel(table4model);
					table4.updateUI();
			}
			}
		});
		/**
		 * 整单退货监听
		 * @author yc
		 * 
		 */
		btn_top3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SalesDocumentsCheckModelWindow.this.setVisible(false);
			}
		});
		this.setTitle("销售单据查询");
		this.setLocationRelativeTo(null);
		this.setBounds(300, 100, 880, 580);
		this.setModal(true);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new SalesDocumentsCheckModelWindow();
	}
	
}
