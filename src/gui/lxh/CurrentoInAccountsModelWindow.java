package gui.lxh;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import bean.orders.InOrder;
import dao.InOrderDao;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import util.ImportExportHelp;
import util.MyDateChooser;

//往来账务（客户）
public class CurrentoInAccountsModelWindow extends JDialog{
	int i=1;
	InOrderDao inorder_dao=null;
	JTabbedPane tabbed;
	JPanel jp_p1,jp_p2,jp_p3,jp_p4,jp_p5;
	JPanel jp_p1_top,jp_p1_center;
	JPanel jp_p1_top_1,jp_p1_top_2;
	JPanel jp_p1_center_p1,jp_p1_center_p2;
	JPanel jp_p1_center_p2_top,jp_p1_center_p2_center;
	JPanel jp_tabbed_center_p1,jp_tabbed_center_p2;
	JButton btn_p1_1,btn_p1_2,btn_delete,btn_p1_4,btn_p1_5,btn_output,btn_p1_7,btn_p1_8;
	
	
	
	JTextField jf_date1,jf_date2,tf_name;
	JButton btn_look,btn_check;
	JButton rbtn_date,rbtn_type;
	ButtonGroup bg;
	Vector columnNames1,columnNames2,columnNames3;
	DefaultTableModel table1model,table2model,table3model;
	JTable table1,table2,table3;
	JTabbedPane tabbed_center;
	String[] arr1={"供货商名称","日期","单号","类型","应付金额","实付金额","经办人","操作员"};
	String[] arr2={"商品编号","商品名称","单位","单价","规格","数量","总金额"};
	String[] arr3={"供货商名称","日期","单号","类型","付款金额","经办人","操作员","备注"};
	
	
	JPanel jp_p2_top,jp_p2_center;
	JPanel jp_p2_top_p1,jp_p2_top_p2;
	JPanel jp_p2_center_p1,jp_p2_center_p2;
	JButton btn_p2_1,btn_p2_2,btn_p2_3,btn_p2_4; 	
	JTextField tf_p2_1,tf_p2_2,tf_p2_3,tf_shop1,tf_shop2;
	JButton btn_p2_5,btn_p2_6;
	Vector columnNames4,columnNames5;
	Vector<Vector> data4,data5;
	DefaultTableModel table4model,table5model;
	JTable table4,table5;
	//String[] arr4={"商品编号","商品名称","单位","数量","总金额","规格型号","颜色","生产厂商","备注"};
	//String[] arr5={"单据号","开单日期","商品编号","商品名称","单价","数量","总金额","单位","规格型号","颜色","仓库","经办人","供销商名称"};
	
	JPanel jp_p3_top,jp_p3_center;
	JButton btn_p3_1,btn_p3_2,btn_p3_3,btn_p3_4,btn_p3_5;
	JPanel jp_p3_top_p1,jp_p3_top_p2;
	JTextField tf_p3_1,tf_p3_2,tf_p3_3;
	Vector columnNames6;
	Vector<Vector> data6;
	DefaultTableModel table6model;
	JTable table6;
	//String[] arr6={"商品编号","商品名称","单位","销售数量","成本价","销售总金额","利润","毛利率（%）","规格型号","颜色","生产厂商","备注"};
	
	JPanel jp_p4_top,jp_p4_center;
	JButton btn_p4_2,btn_p4_3,btn_p4_4,btn_p4_5,btn_p4_6;
	JPanel jp_p4_top_p1,jp_p4_top_p2;
	JTextField tf_p4_1,tf_p4_2,tf_p4_3;
	Vector columnNames7;
	Vector<Vector> data1,data7;
	DefaultTableModel table7model;
	JTable table7;
	String[] arr7={"供货商名称","商品销售额","商品退货额","我方应付金额","我方实付金额","未付金额"};
	
	//jp_p5 里的东西
		JPanel p5_1,p51_11,p51_12,p5_2;
		JButton btn_select,btn_in,btn_out,btn_exit,btn_fdj,btn_looks;
		JTextField tf_date1,tf_date2,tf_supplier;
		String[] arr8={"单据编号","单据日期","付款方式","付款金额","供货商名称","经办人","操作员","备注"};
		Vector columnNames8;
		Vector<Vector> data8;
		DefaultTableModel table8model;
		JTable table8;
		
		MyDateChooser date1,date2,date3,date4,date5,date6,date7,date8,date9,date10;			//定义日历选择器
	public CurrentoInAccountsModelWindow(){
		inorder_dao=new InOrderDao();
		date1=MyDateChooser.getInstance("yyyy-MM-dd");
		date2=MyDateChooser.getInstance("yyyy-MM-dd");
		date3=MyDateChooser.getInstance("yyyy-MM-dd");
		date4=MyDateChooser.getInstance("yyyy-MM-dd");
		date5=MyDateChooser.getInstance("yyyy-MM-dd");
		date6=MyDateChooser.getInstance("yyyy-MM-dd");
		date7=MyDateChooser.getInstance("yyyy-MM-dd");
		date8=MyDateChooser.getInstance("yyyy-MM-dd");
		date9=MyDateChooser.getInstance("yyyy-MM-dd");
		date10=MyDateChooser.getInstance("yyyy-MM-dd");
		
		
		tabbed=new JTabbedPane();
		jp_p1=new JPanel();//供货商所有单据
		/*jp_p2=new JPanel();//供货商供货情况
		jp_p3=new JPanel();//供货商销售情况
		jp_p4=new JPanel();//供货商账务
		jp_p5=new JPanel();//我方付款明细
*/		jp_p1_top=new JPanel();
		jp_p1_center=new JPanel();
		//btn_p1_1=new JButton("我方付款");
		//btn_p1_2=new JButton("查看单据");
		btn_delete=new JButton("删除单据");
		//btn_p1_4=new JButton("整单退货");
		//btn_p1_5=new JButton("单据过滤");
		btn_output=new JButton(" 导    出 ");
		btn_p1_7=new JButton(" 打    印 ");
		btn_p1_8=new JButton(" 退    出 ");
		jp_p1_top_1=new JPanel();
		jp_p1_top_2=new JPanel();
		jf_date1=new JTextField(10);
		jf_date2=new JTextField(10);
		tf_name=new JTextField(8);
		btn_look=new JButton("查   看");
		btn_check=new JButton("查 询");
		bg=new ButtonGroup();
		rbtn_date=new JButton("按日期排序(升序)");
		//rbtn_type=new JRadioButton("按类型排序");
		jp_p1_center_p1=new JPanel();
		jp_p1_center_p2=new JPanel();
		//表1
		columnNames1=new Vector();
		for (String str:arr1) {
			columnNames1.add(str);
		}
		table1model=new DefaultTableModel(inorder_dao.getSOrder(),columnNames1);
		table1=new JTable(table1model) {
			public boolean isCellEditable(int row,int column) {
				return false;
			}
		};
		//表2
		columnNames2=new Vector();
		for (String str:arr2) {
			columnNames2.add(str);
		}
		table2model=new DefaultTableModel(null,columnNames2);
		table2=new JTable(table2model);
		//表3
		table3=new JTable(table3model);
		columnNames3=new Vector();
		
		for (String str:arr3) {
			columnNames3.add(str);
		}
		table3model=new DefaultTableModel(null,columnNames3);
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
		btn_p2_2=new JButton(" 导    出 ");
		btn_p2_3=new JButton(" 打    印 ");
		btn_p2_4=new JButton(" 退    出 ");
		tf_p2_1=new JTextField(10);
		tf_p2_2=new JTextField(10);
		tf_p2_3=new JTextField(10);
		tf_shop1=new JTextField(10);
		btn_p2_5=new JButton("查名称");
		btn_p2_6=new JButton("查  询");
		jp_p2_center_p1=new JPanel();
		jp_p2_center_p2=new JPanel();
		/*//表4
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
		table5=new JTable(table5model);*/
		
		jp_p3_top=new JPanel();
		jp_p3_center=new JPanel();
		jp_p3_top_p1=new JPanel();
		jp_p3_top_p2=new JPanel();
		btn_p3_1=new JButton(" 导    出 ");
		btn_p3_2=new JButton(" 打    印 ");
		btn_p3_3=new JButton(" 退    出 ");
		tf_p3_1=new JTextField(10);
		tf_p3_2=new JTextField(10);
		tf_p3_3=new JTextField(10);
		tf_shop2=new JTextField(10);
		btn_p3_4=new JButton("查名称");
		btn_p3_5=new JButton("查询");
		/*//表6
		table6=new JTable(table6model);
		columnNames6=new Vector();
		data6=new Vector<Vector>();
		for (String str:arr6) {
			columnNames6.add(str);
		}
		table6model=new DefaultTableModel(data6,columnNames6);
		table6=new JTable(table6model);*/
		
		jp_p4_top=new JPanel();
		jp_p4_center=new JPanel();
		jp_p4_top_p1=new JPanel();
		jp_p4_top_p2=new JPanel();
		btn_p4_2=new JButton(" 导    出 ");
		btn_p4_3=new JButton(" 打    印 ");
		btn_p4_4=new JButton(" 退    出 ");
		tf_p4_1=new JTextField(10);
		tf_p4_2=new JTextField(10);
		tf_p4_3=new JTextField(10);
		btn_p4_5=new JButton("查名称");
		btn_p4_6=new JButton("查 询");
		/*//表7
		table7=new JTable(table7model);
		columnNames7=new Vector();
		data7=new Vector<Vector>();
		for (String str:arr7) {
			columnNames7.add(str);
		}
		table7model=new DefaultTableModel(data7,columnNames7);
		table7=new JTable(table7model);
		
		//表8
		table8=new JTable(table8model);
		columnNames8=new Vector();
		data7=new Vector<Vector>();
		for (String str:arr8) {
			columnNames8.add(str);
		}
		table8model=new DefaultTableModel(data8,columnNames8);
		table8=new JTable(table8model);
		*/
		
		/**
		 * 选项卡1
		 * @author lxh
		 */
		tabbed.add("供货商所有单据",jp_p1);
		jp_p1.setLayout(new BorderLayout());
		jp_p1_top.setLayout(new GridLayout(2,1));
		jp_p1_top_1.setBorder(BorderFactory.createTitledBorder(""));
		/*jp_p1_top_1.add(btn_p1_1);
		jp_p1_top_1.add(btn_p1_2);*/
		jp_p1_top_1.add(btn_delete);
		/*jp_p1_top_1.add(btn_p1_4);
		jp_p1_top_1.add(btn_p1_5);*/
		jp_p1_top_1.add(btn_output);
		jp_p1_top_1.add(btn_p1_7);
		jp_p1_top_1.add(btn_p1_8);
		jp_p1_top_1.add(new JLabel());
		jp_p1_top.add(jp_p1_top_1);
		jp_p1_top_2.add(new JLabel("查询时间："));
		jf_date1.setText(date1.getStrDate());
		date1.register(jf_date1);
		jp_p1_top_2.add(jf_date1);
		jp_p1_top_2.add(new JLabel("至"));
		jf_date2.setText(date2.getStrDate());
		date2.register(jf_date2);
		jp_p1_top_2.add(jf_date2);
		jp_p1_top_2.add(btn_look);
		jp_p1_top_2.add(new JLabel("供货商名称："));
		jp_p1_top_2.add(tf_name);
		jp_p1_top_2.add(btn_check);
		bg.add(rbtn_date);
		bg.add(rbtn_type);
		jp_p1_top_2.add(rbtn_date);
		//jp_p1_top_2.add(rbtn_type);
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
		 * @author lxh
		 *//*
		tabbed.add("供货商供货情况",jp_p2);
		jp_p2.setLayout(new BorderLayout());
		jp_p2_top.setLayout(new GridLayout(2,1));
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
		tf_p2_1.setText(date3.getStrDate());
		date3.register(tf_p2_1);
		jp_p2_top_p2.add(tf_p2_1);
		jp_p2_top_p2.add(new JLabel("至"));
		tf_p2_2.setText(date4.getStrDate());
		date4.register(tf_p2_2);
		jp_p2_top_p2.add(tf_p2_2);
		jp_p2_top_p2.add(new JLabel("供货商名称："));
		jp_p2_top_p2.add(tf_shop1);
		jp_p2_top_p2.add(btn_p2_5);
		jp_p2_top_p2.add(btn_p2_6);
		jp_p2_top.add(jp_p2_top_p2);
		jp_p2.add(jp_p2_top,BorderLayout.NORTH);

		jp_p2_center.setLayout(new GridLayout(2,1));
		jp_p2_center_p1.setBorder(BorderFactory.createTitledBorder("总汇表"));
		jp_p2_center_p1.setLayout(new GridLayout(1,1));
		jp_p2_center_p1.add(new JScrollPane(table4));
		jp_p2_center.add(jp_p2_center_p1);
		jp_p2_center_p2.setBorder(BorderFactory.createTitledBorder("流水表"));
		jp_p2_center_p2.setLayout(new GridLayout(1,1));
		jp_p2_center_p2.add(new JScrollPane(table5));
		jp_p2_center.add(jp_p2_center_p2);
		jp_p2.add(jp_p2_center,BorderLayout.CENTER);
		*//**
		 * 选项卡3
		 * @author yc
		 *//*
		tabbed.add("供货商商品销售情况表",jp_p3);
		jp_p3.setLayout(new BorderLayout());
		jp_p3_top.setLayout(new GridLayout(2,1));
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
		tf_p3_1.setText(date5.getStrDate());
		date5.register(tf_p3_1);
		jp_p3_top_p2.add(tf_p3_1);
		jp_p3_top_p2.add(new JLabel("至"));
		tf_p3_2.setText(date6.getStrDate());
		date6.register(tf_p3_2);
		jp_p3_top_p2.add(tf_p3_2);
		jp_p3_top_p2.add(new JLabel("供货商名称："));
		jp_p3_top_p2.add(tf_shop2);
		jp_p3_top_p2.add(btn_p3_4);
		jp_p3_top_p2.add(btn_p3_5);
		jp_p3_top.add(jp_p3_top_p2);
		jp_p3.add(jp_p3_top,BorderLayout.NORTH);
		jp_p3_center.setLayout(new GridLayout(1,1));
		jp_p3_center.add(new JScrollPane(table6));
		jp_p3.add(jp_p3_center,BorderLayout.CENTER);
		*//**
		 * 选项卡4
		 * @author yc
		 *//*
		tabbed.add("供货商账务",jp_p4);
		jp_p4.setLayout(new BorderLayout());
		jp_p4_top.setLayout(new GridLayout(2,1));
		jp_p4_top_p1.setBorder(BorderFactory.createTitledBorder(""));
		jp_p4_top_p1.add(btn_p4_2);
		jp_p4_top_p1.add(btn_p4_3);
		jp_p4_top_p1.add(btn_p4_4);
		jp_p4_top_p1.add(new JLabel());
		jp_p4_top_p1.add(new JLabel());
		jp_p4_top_p1.add(new JLabel());
		jp_p4_top_p1.add(new JLabel());
		jp_p4_top_p1.add(new JLabel());
		jp_p4_top.add(jp_p4_top_p1);
	
		
		jp_p4_top_p2.add(new JLabel("          "));
		jp_p4_top_p2.add(new JLabel("供货商名称："));
		jp_p4_top_p2.add(tf_p4_2);
		jp_p4_top_p2.add(btn_p4_5);
		jp_p4_top_p2.add(btn_p4_6);
		jp_p4_top.add(jp_p4_top_p2);
		jp_p4.add(jp_p4_top,BorderLayout.NORTH);
		jp_p4_center.setLayout(new GridLayout(1,1));
		jp_p4_center.add(new JScrollPane(table7));
		jp_p4.add(jp_p4_center,BorderLayout.CENTER);
		
		
		 * *lxh  
		 * 我方付款明细
		 	
		p5_1=new JPanel();
		p51_11=new JPanel();
		p51_12=new JPanel();
		p5_2=new JPanel();
		btn_select=new JButton("查看单据");
		btn_in=new JButton(" 导     出 ");
		btn_out=new JButton(" 打     印 ");
		btn_exit=new JButton(" 退     出 ");
		btn_fdj=new JButton(" 查     看");
		btn_looks=new JButton("确定查询");
		
		tf_date1=new JTextField(10);
		tf_date2=new JTextField(10);
		tf_supplier=new JTextField(10);
		
		p51_11.setBorder(BorderFactory.createTitledBorder(" "));
		p51_11.add(btn_select);
		p51_11.add(new JLabel("    "));
		p51_11.add(btn_in);
		p51_11.add(new JLabel("    "));
		p51_11.add(btn_out);
		p51_11.add(new JLabel("    "));
		p51_11.add(btn_exit);
		
		p51_12.add(new JLabel("--------------------"));
		p51_12.add(new JLabel("查询时间："));
		tf_date1.setText(date7.getStrDate());
		date7.register(tf_date1);
		p51_12.add(tf_date1);
		p51_12.add(new JLabel("至"));
		tf_date2.setText(date8.getStrDate());
		date8.register(tf_date2);
		p51_12.add(tf_date2);
		p51_12.add(new JLabel("----------------------"));
		p51_12.add(new JLabel("供货商名称："));
		p51_12.add(tf_supplier);
		p51_12.add(btn_fdj);
		p51_12.add(btn_looks);
		
		p5_2.setLayout(new GridLayout(1,1));
		p5_2.add(new JScrollPane(table8));
		
		p5_1.setLayout(new GridLayout(2,1));
		p5_1.add(p51_11);
		p5_1.add(p51_12);
		jp_p5.setLayout(new BorderLayout());
		jp_p5.add(p5_1,BorderLayout.NORTH);
		jp_p5.add(p5_2,BorderLayout.CENTER);
		tabbed.add("我方付款明细",jp_p5);*/

		//监听
		btn_p1_8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CurrentoInAccountsModelWindow.this.setVisible(false);
			}
		});
		/**
		 * 删除订单
		 */
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isSelectTable1()!=null){
					inorder_dao.deleteOrder(isSelectTable1().get(2)+"");
					table1model=new DefaultTableModel(inorder_dao.getSOrder(),columnNames1);
					table1.setModel(table1model);
					table1.updateUI();
				}
				
				
			}
		});
		/**
		 * 导出
		 */
		btn_output.addActionListener(new ActionListener() {
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
		 * 打印
		 */
		btn_p1_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(null, "请检查打印设备是否连接成功！");
				
			}
		});
		/**
		 * 查询指定日期的订单
		 */
		btn_look.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				i=2;
				String date1=jf_date1.getText();
				String date2=jf_date2.getText();
				table1model=new DefaultTableModel(data1 = inorder_dao.getSOrder(date1,date2),columnNames1);
				table1.setModel(table1model);
				table1.updateUI();
			}
		});
		/**
		 * 按供货商名称查询
		 */
		btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				i=3;
				String sname=tf_name.getText().trim();
				table1model=new DefaultTableModel(inorder_dao.getSOrder(sname),columnNames1);
				table1.setModel(table1model);
				table1.updateUI();
			}
		});
		/**
		 * 按日期升序或降序
		 */
		rbtn_date.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				i=4;
				table1model=new DefaultTableModel(inorder_dao.getSOrderOfASC(),columnNames1);
				table1.setModel(table1model);
				table1.updateUI();
			}
		});
		table1.addMouseListener(new TableMouse(table1));
		this.add(tabbed);
		this.setTitle("往来账务（供货商）");	
		this.setBounds(300, 100, 1000, 550);
		this.setModal(true);
		this.setVisible(true);
	}/***
	 * 
	 * 表格1的点击事件
	 * 会使表格2和3发生变化
	 *
	 */
	
	public class TableMouse extends MouseAdapter{
		JTable table;
		public TableMouse(	JTable table){
			this.table=table;
		}
		public void mouseClicked(MouseEvent e) {
			if(e.getButton()==1&&isSelectTable1()!=null){
				/**
				 * 表2变
				 */
				Vector vector_boss=inorder_dao.getGoodsInfo(isSelectTable1().get(2)+"");
				table2model=new DefaultTableModel(vector_boss,columnNames2);
				table2.setModel(table2model);
				table2.updateUI();
				/**
				 * 表三变
				 */
				Vector vector_boss1=inorder_dao.getOrderInfo(isSelectTable1().get(2)+"");
				table3model=new DefaultTableModel(vector_boss1,columnNames3);
				table3.setModel(table3model);
				table3.updateUI();
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
				 if(i==1) {								
					 vector=(Vector)inorder_dao.getSOrder().get(table1.getSelectedRow());	
				}else if(i==2){	
						vector=inorder_dao.getSOrder(jf_date1.getText(),jf_date2.getText()).get(table1.getSelectedRow());
					
					
					
				}else if(i==3){
					vector=(Vector)inorder_dao.getSOrder(tf_name.getText()).get(table1.getSelectedRow());	

				}else if(i==4) {
					vector=(Vector)inorder_dao.getSOrderOfASC().get(table1.getSelectedRow());	
					
				}
				 
			}catch (Exception e2) {
				
			}
		}
		return vector;
	}
	
	public static void main(String[] args) {
		new CurrentoInAccountsModelWindow();
	}
}
