package gui.zw.modewindow;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import bean.GoodsType;

import dao.GoodsDao;
import util.TypeWindow;

//添加商品
public class AddGoodsModelWindow extends JDialog{
	JPanel jp_left,jp_right;			//设置左右两个面板
	JPanel jp_left_top,jp_left_center;
	JPanel jp_left_top_p1,jp_left_top_p2,jp_left_top_p3;
	JPanel jp_right_center,jp_right_bottom;
	JTextField tf_seekgoods;
	JButton btn_addgood;
	JRadioButton rbtn;
	JTabbedPane tabbed;
	JPanel jp_tabbed1,jp_tabbed2,jp_tabbed2_center,jp_tabbed2_left;
	JTable table1,table2,table3;
	DefaultTableModel table1model,table2model,table3model;
	public Vector<Vector> data1,data2,data3;
	Vector columnNames1,columnNames2,columnNames3;
	String[] arr1={"商品编号","商品名称","单位","规格","颜色","预设售价","库存量"};
	String[] arr2={"商品编号","商品名称","单位","预设售价"};
	String[] arr3={"商品编号","商品名称","单位","单价","打折率","打折后","数量","总金额"};
	JButton btn_alter,btn_del,btn_ok,btn_exit;
	TypeWindow type;
	public GoodsType goodstype;
	int goodscount; //输入商品的数量
	Vector goods1,goods2;
	public AddGoodsModelWindow(String title){
		type=new TypeWindow();
		jp_left=new JPanel();
		jp_right=new JPanel();
		
		jp_left.setBorder(BorderFactory.createTitledBorder("查询商品列表"));
		jp_right.setBorder(BorderFactory.createTitledBorder("所选商品"));
		
/*--------------------------------------左边面板--------------------------------------------------*/		
		jp_left_top=new JPanel();			//左边顶部
		jp_left_top_p1=new JPanel();
		jp_left_top_p2=new JPanel();
		jp_left_top_p3=new JPanel();
		tf_seekgoods=new JTextField(12);
		btn_addgood=new JButton("加入所选商品");
		rbtn=new JRadioButton("按商品销售频率排序");
		jp_left.setLayout(new BorderLayout());
		jp_left_top.setLayout(new GridLayout(3,1));
		jp_left_top_p1.add(new JLabel("请输入商品编号或名称查询商品,查询到商品后添加到右边所选商品"));
		jp_left_top_p2.add(new JLabel("输入商品编号或名称查询商品："));
		jp_left_top_p2.add(tf_seekgoods);
		jp_left_top_p2.add(btn_addgood);
		jp_left_top_p3.setLayout(new GridLayout(1,2));
		jp_left_top_p3.add(rbtn);
		jp_left_top_p3.add(new JLabel());
		jp_left_top.add(jp_left_top_p1);
		jp_left_top.add(jp_left_top_p2);
		jp_left_top.add(jp_left_top_p3);
		jp_left.add(jp_left_top,BorderLayout.NORTH);
		
		jp_left_center=new JPanel();		//左边中间
		tabbed=new JTabbedPane();
		jp_tabbed1=new JPanel();
		jp_tabbed2=new JPanel();
		jp_tabbed2_center=new JPanel();
		jp_tabbed2_left=new JPanel();
		columnNames1=new Vector();
		for(String str:arr1){
			columnNames1.add(str);
		}
		data1=new GoodsDao().getAllGoods();
		table1model=new DefaultTableModel(data1,columnNames1);
		table1=new JTable(table1model){
			public boolean isCellEditable(int row,int clumn){
				return false;
			}
		};//设置表不可编辑
		
		columnNames2=new Vector();
		for(String str:arr2){
			columnNames2.add(str);
		}
		//data2=new GoodsDao().getGoodsLiebiao();
		table2model=new DefaultTableModel(data2,columnNames2);
		table2=new JTable(table2model){
			public boolean isCellEditable(int row,int clumn){
				return false;
			}
		};//设置表不可编辑
		tabbed.add("商品清单",jp_tabbed1);
		tabbed.add("商品列表",jp_tabbed2);
		jp_tabbed1.add(new JScrollPane(table1));
		jp_tabbed2.setLayout(new BorderLayout());
		jp_tabbed2_center.setLayout(new GridLayout(1,1));
		jp_tabbed2_center.add(new JScrollPane(table2));
		jp_tabbed2_left.add(type);
		

		jp_tabbed2.add(jp_tabbed2_left,BorderLayout.WEST);
		jp_tabbed2.add(jp_tabbed2_center,BorderLayout.CENTER);
		jp_left_center.setLayout(new GridLayout(1,1));
		jp_left_center.add(tabbed);
		jp_left.add(jp_left_center,BorderLayout.CENTER);
		
/*--------------------------------------右边面板--------------------------------------------------*/		
		jp_right_center=new JPanel();
		columnNames3=new Vector();
		for(String str:arr3){
			columnNames3.add(str);
		}
		data3=new Vector<Vector>();
		table3model=new DefaultTableModel(data3,columnNames3);
		table3=new JTable(table3model){
			public boolean isCellEditable(int row,int clumn){
				return false;
			}
		};
		
		//设置表不可编辑;
		jp_right_bottom=new JPanel();
		btn_alter=new JButton("修改");
		btn_del=new JButton("删除");
		btn_ok=new JButton("确定");
		btn_exit=new JButton("取消");
		jp_right_center.setLayout(new GridLayout(1,1));
		jp_right_center.add(new JScrollPane(table3));
		jp_right.setLayout(new BorderLayout());
		jp_right.add(jp_right_center,BorderLayout.CENTER);
		jp_right_bottom.setLayout(new GridLayout(1,8));
		jp_right_bottom.add(btn_alter);
		jp_right_bottom.add(btn_del);
		jp_right_bottom.add(btn_ok);
		jp_right_bottom.add(btn_exit);
		jp_right_bottom.add(new JLabel());
		jp_right_bottom.add(new JLabel());
		jp_right_bottom.add(new JLabel());
		jp_right_bottom.add(new JLabel());
		jp_right.add(jp_right_bottom,BorderLayout.SOUTH);
		/**
		 * 监听方法设置
		 * @author yc
		 */
		//加入所选商品
		btn_addgood.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goodscount=Integer.parseInt(new JOptionPane().showInputDialog("请输入商品数量：").trim()) ;
				try {
					if(jp_tabbed1.isShowing()){
						Vector data2_1=new Vector();
						data2_1.add(goods1.get(0));
						data2_1.add(goods1.get(1));
						data2_1.add(goods1.get(2));
						data2_1.add(goods1.get(5));
						data2_1.add("1.00");
						data2_1.add(goods1.get(5));
						data2_1.add(goodscount);
						data2_1.add(Float.parseFloat(goods1.get(5)+"")*goodscount);
						data3.add(data2_1);
						table3model=new DefaultTableModel(data3,columnNames3);
						table3.setModel(table3model);
						table3.updateUI();
					}else{
						Vector data2_1=new Vector();
						data2_1.add(goods2.get(0));
						data2_1.add(goods2.get(1));
						data2_1.add(goods2.get(2));
						data2_1.add(goods2.get(3));
						data2_1.add("1.00");
						data2_1.add(goods2.get(3));
						data2_1.add(goodscount);
						data2_1.add(goods2.get(3));
						data2_1.add(Float.parseFloat(goods2.get(3)+"")*goodscount);
						data3.add(data2_1);
						table3model=new DefaultTableModel(data3,columnNames3);
						table3.setModel(table3model);
						table3.updateUI();
					}
					
				} catch (java.lang.NullPointerException e1) {
					JOptionPane.showMessageDialog(null, "请选中商品在操作！");
				}
				
				
				
			}
		});
		//文档查询商品
		tf_seekgoods.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				String str=tf_seekgoods.getText();
				table1model=new DefaultTableModel(new GoodsDao().getAllGoodsByIdName(str),columnNames1);
				table1.setModel(table1model);
				table1.updateUI();
			}
			public void insertUpdate(DocumentEvent e) {}
			public void changedUpdate(DocumentEvent e) {	
				String str=tf_seekgoods.getText();
				table1model=new DefaultTableModel(new GoodsDao().getAllGoodsByIdName(str),columnNames1);
				table1.setModel(table1model);
				table1.updateUI();
			}
		});
		
		//table1中的点击事件
		table1.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {} 	
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				goods1=data1.get(table1.getSelectedRow());
				if(e.getButton()==1&&e.getClickCount()==2){
					try {
						goods1=data1.get(table1.getSelectedRow());
						goodscount=Integer.parseInt(new JOptionPane().showInputDialog("请输入商品数量：").trim()) ;
						Vector data2_1=new Vector();
						data2_1.add(goods1.get(0));
						data2_1.add(goods1.get(1));
						data2_1.add(goods1.get(2));
						data2_1.add(goods1.get(5));
						data2_1.add("1.00");
						data2_1.add(goods1.get(5));
						data2_1.add(goodscount);
						data2_1.add(Float.parseFloat(goods1.get(5)+"")*goodscount);
						data3.add(data2_1);
						table3model=new DefaultTableModel(data3,columnNames3);
						table3.setModel(table3model);
						table3.updateUI();
					} catch (java.lang.NullPointerException e1) {
						JOptionPane.showMessageDialog(null, "请选中商品在操作！");
					}
				}
			}
		});
		//table2中的点击事件
		table2.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {} 	
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				goods2=data2.get(table2.getSelectedRow());
				if(e.getButton()==1&&e.getClickCount()==2){
					try {
						goods2=data2.get(table2.getSelectedRow());
						goodscount=Integer.parseInt(new JOptionPane().showInputDialog("请输入商品数量：").trim()) ;
						Vector data2_1=new Vector();
						data2_1.add(goods2.get(0));
						data2_1.add(goods2.get(1));
						data2_1.add(goods2.get(2));
						data2_1.add(goods2.get(3));
						data2_1.add("1.00");
						data2_1.add(goods2.get(3));
						data2_1.add(goodscount);
						data2_1.add(goods2.get(3));
						data2_1.add(Float.parseFloat(goods2.get(3)+"")*goodscount);
						data3.add(data2_1);
						table3model=new DefaultTableModel(data3,columnNames3);
						table3.setModel(table3model);
						table3.updateUI();
					} catch (java.lang.NullPointerException e1) {
						JOptionPane.showMessageDialog(null, "请选中商品在操作！");
					}
				}
			}
		});
		//tree点击出现商品信息
		type.tree_type.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
			DefaultMutableTreeNode node=(DefaultMutableTreeNode) AddGoodsModelWindow.this.type.tree_type.getLastSelectedPathComponent();
			goodstype=(GoodsType) node.getUserObject();
			data2=new GoodsDao().getGoodsLiebiaoByType(AddGoodsModelWindow.this.goodstype.getSelf_id());
			table2model=new DefaultTableModel(data2,columnNames2);
			table2.setModel(table2model);
			table2.updateUI();
			}
		});
		//点击修改事件
		btn_alter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Vector goods3=data3.get(table3.getSelectedRow());
					goodscount=Integer.parseInt(new JOptionPane().showInputDialog("请输入商品修改数量：").trim()) ;
					goods3.set(5, goodscount);
					table3model=new DefaultTableModel(data3,columnNames3);
					table3.setModel(table3model);
					table3.updateUI();
				} catch (java.lang.NullPointerException e1) {
					JOptionPane.showMessageDialog(null, "请选中商品在操作！");
				}
			}
		});
		//点击删除事件
		btn_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					data3.remove(table3.getSelectedRow());
					table3model=new DefaultTableModel(data3,columnNames3);
					table3.setModel(table3model);
					table3.updateUI();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "请选中商品在操作！");
				}
				
			}
		});
		//点击确定事件
		btn_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(data3.isEmpty()){
					
				}else{
					AddGoodsModelWindow.this.setVisible(false);
				}
			}
		});
		//取消点击事件
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			AddGoodsModelWindow.this.setVisible(true);
			}
		});
		this.setLayout(new GridLayout(1,2));
		this.add(jp_left);
		this.add(jp_right);
		this.setLocationRelativeTo(null);
		this.setTitle(title);
		this.setBounds(200,50,1000, 650);
		this.setModal(true);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new AddGoodsModelWindow("增加商品(商品调拨)");
	}
}
