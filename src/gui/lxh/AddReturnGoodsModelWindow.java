package gui.lxh;

import gui.lxh.OldGoodsADDModelWindow.MyMouse;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import bean.GoodsType;

import util.TypeWindow;

import dao.GetAllGoodsDao;

//添加商品
public class AddReturnGoodsModelWindow extends JDialog{
	BuyReturnGoodsModelWindow brg=null;							//定义一个父窗口对象
	GetAllGoodsDao get_goods_dao=null;							//定义商品dao
	JPanel jp_left,jp_right;									//设置左右两个面板
	JPanel jp_left_top,jp_left_center;
	JPanel jp_left_top_p1,jp_left_top_p2;
	JPanel jp_right_center,jp_right_bottom;
	JTextField tf_seekgoods;
	JButton btn_addgood;
	JTabbedPane tabbed;
	JPanel jp_tabbed1,jp_tabbed2,jp_tabbed2_center,jp_tabbed2_left;
	JTable table1,table2,table3;
	DefaultTableModel table1model,table2model,table3model;
	Vector columnNames1,columnNames2,columnNames3;
	String[] arr1={"商品编号","商品名称","单位","规格","参考进价","库存量"};
	String[] arr2={"商品编号","商品名称","单位","规格","参考进价","库存量"};
	String[] arr3={"商品编号","商品名称","单位","规格","进价","数量","总金额"};
	JButton btn_alter,btn_del,btn_ok,btn_exit;
	
	/**
	 * 
	 * 定义装table1和table2表格的维克托
	 */
	Vector vector_boss=null;
	/**
	 * 这个树是一个面板
	 */
	TypeWindow p_tree_type=null; 									//定义我是一棵树
	public AddReturnGoodsModelWindow(BuyReturnGoodsModelWindow brg){
		this.brg=brg;
		p_tree_type=new TypeWindow();								//初始化我是一棵树
		get_goods_dao=new GetAllGoodsDao();							//初始化商品dao
		jp_left=new JPanel();
		jp_right=new JPanel();
		
		jp_left.setBorder(BorderFactory.createTitledBorder("查询商品列表"));
		jp_right.setBorder(BorderFactory.createTitledBorder("所选商品"));
		
/*--------------------------------------左边面板--------------------------------------------------*/		
		jp_left_top=new JPanel();			//左边顶部
		jp_left_top_p1=new JPanel();
		jp_left_top_p2=new JPanel();
		tf_seekgoods=new JTextField(12);
		btn_addgood=new JButton("加入所选商品");
		jp_left.setLayout(new BorderLayout());
		jp_left_top.setLayout(new GridLayout(2,1));
		jp_left_top_p1.add(new JLabel("请输入商品编号或名称查询商品,查询到商品后添加到右边所选商品"));
		jp_left_top_p2.add(new JLabel("输入商品编号或名称查询商品："));
		jp_left_top_p2.add(tf_seekgoods);
		jp_left_top_p2.add(btn_addgood);
		jp_left_top_p2.setBorder(BorderFactory.createTitledBorder(""));
		jp_left_top.add(jp_left_top_p1);
		jp_left_top.add(jp_left_top_p2);
		jp_left.add(jp_left_top,BorderLayout.NORTH);
		
		jp_left_center=new JPanel();		//左边中间
		tabbed=new JTabbedPane();
		jp_tabbed1=new JPanel();
		jp_tabbed2=new JPanel();
		jp_tabbed2_center=new JPanel();
		jp_tabbed2_left=new JPanel();
		/**
		 * 初始化装表格的维克托
		 */
		vector_boss=new Vector();												//装表格的维克托在这里
		columnNames1=new Vector();
		for(String str:arr1){
			columnNames1.add(str);
		}
		table1model=new DefaultTableModel(get_goods_dao.getAllGoods1(""),columnNames1);
		/**
		 * 设置表格不可编辑
		 */
		table1=new JTable(table1model){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		columnNames2=new Vector();
		for(String str:arr2){
			columnNames2.add(str);
		}
		table2model=new DefaultTableModel(get_goods_dao.getAllGoods1(""),columnNames2);
		
		/**
		 * 设置表格不可编辑
		 */
		table2=new JTable(table2model){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabbed.add("商品清单",jp_tabbed1);
		tabbed.add("商品列表",jp_tabbed2);
		jp_tabbed1.add(new JScrollPane(table1));
		jp_tabbed2.setLayout(new BorderLayout());
		jp_tabbed2_center.setLayout(new GridLayout(1,1));
		jp_tabbed2_center.add(new JScrollPane(table2));
		jp_tabbed2_left.setLayout(new GridLayout(1,1));									//加入我是一棵树
		jp_tabbed2_left.add(new JScrollPane(p_tree_type));
		jp_tabbed2.add(jp_tabbed2_left,BorderLayout.WEST);
		jp_tabbed2.add(jp_tabbed2_center,BorderLayout.CENTER);
		jp_left_center.setLayout(new GridLayout(1,1));
		jp_left_center.add(tabbed);
		jp_left.add(jp_left_center,BorderLayout.CENTER);
		
/*--------------------------------------右边面板--------------------------------------------------*/		
		jp_right_center=new JPanel();
		columnNames3=new Vector();
		/**
		 * 设置表格不可编辑
		 */
		for(String str:arr3){
			columnNames3.add(str);
		}
		table3model=new DefaultTableModel(null,columnNames3);
		table3=new JTable(table3model){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jp_right_bottom=new JPanel();
		btn_alter=new JButton(" 修   改 ");
		btn_del=new JButton(" 删    除 ");
		btn_ok=new JButton(" 确    定 ");
		btn_exit=new JButton(" 取    消 ");
		jp_right_center.setLayout(new GridLayout(1,1));
		jp_right_center.add(new JScrollPane(table3));
		jp_right.setLayout(new BorderLayout());
		jp_right.add(jp_right_center,BorderLayout.CENTER);
		jp_right_bottom.setBorder(BorderFactory.createTitledBorder(""));
		jp_right_bottom.add(btn_alter);
		jp_right_bottom.add(btn_del);
		jp_right_bottom.add(btn_ok);
		jp_right_bottom.add(btn_exit);
		jp_right_bottom.add(new JLabel());
		jp_right_bottom.add(new JLabel());
		jp_right_bottom.add(new JLabel());
		jp_right_bottom.add(new JLabel());
		jp_right.add(jp_right_bottom,BorderLayout.SOUTH);
		
		
		this.setTitle("增加商品(商品退货)");
		this.setLayout(new GridLayout(1,2));
		this.add(jp_left);
		this.add(jp_right);
		this.setBounds(200,50,1000, 650);
		/**
		 * 修改事件
		 * @author lxh
		 *
		 */
		btn_alter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Vector vector=(Vector) vector_boss.get(table3.getSelectedRow());
					int num=Integer.parseInt(JOptionPane.showInputDialog("请输入要修改的数量："));
					vector.set(5, num);
					table3model=new DefaultTableModel(vector_boss,columnNames3);
					table3.setModel(table3model);
					table3.updateUI();
				} catch (Exception e2) {	
				}
			}
		});
		/**
		 * 删除右边选择的行
		 * 
		 */
		btn_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Vector vector=(Vector) vector_boss.remove(table3.getSelectedRow());
					table3model=new DefaultTableModel(vector_boss,columnNames3);
					table3.setModel(table3model);
					table3.updateUI();
				} catch (Exception e2) {
				}
				
			}
		});
		/**
		 * 加入所选商品
		 */	
		btn_addgood.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {	
					if(table1.isShowing()){
						try{
							setTableL_C(table1);
						}catch (Exception e2) {
							
						}
					}else if(table2.isShowing()){
						try{
							setTableL_C(table2);
						}catch (Exception e2) {
							
						}
					}		
			}
		});
		/*
		 * typeWindow里的树的点击事件
		 * 
		 */
		p_tree_type.tree_type.addTreeSelectionListener(new TreeSelectionListener() {	
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node =(DefaultMutableTreeNode) p_tree_type.tree_type.getLastSelectedPathComponent();
				if(node!=null){
					GoodsType type=(GoodsType) node.getUserObject();
					int id=type.getSelf_id();
					table2model=new DefaultTableModel(get_goods_dao.getAllGoods(id),columnNames2);
					table2.setModel(table2model);
					table2.updateUI();
				}
				
			}
		});
		/**
		 * 输入编号或名字查询商品列表
		 */
		tf_seekgoods.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				
				updaateTable(table1model, table1, columnNames1,tf_seekgoods.getText());
				updaateTable(table2model, table2, columnNames2,tf_seekgoods.getText());
				
			}
			public void insertUpdate(DocumentEvent e) {
				updaateTable(table1model, table1, columnNames1,tf_seekgoods.getText());
				updaateTable(table2model, table2, columnNames2,tf_seekgoods.getText());
				
			}
			public void changedUpdate(DocumentEvent e) {
				updaateTable(table1model, table1, columnNames1,tf_seekgoods.getText());
				updaateTable(table2model, table2, columnNames2,tf_seekgoods.getText());
				
			}
		});
		/**
		 * 确定事件
		 */
		btn_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AddReturnGoodsModelWindow.this.brg.data=vector_boss;
					AddReturnGoodsModelWindow.this.brg.tablemodel=new DefaultTableModel(AddReturnGoodsModelWindow.this.brg.data,AddReturnGoodsModelWindow.this.brg.columnNames);
					AddReturnGoodsModelWindow.this.brg.table.setModel(AddReturnGoodsModelWindow.this.brg.tablemodel);
					AddReturnGoodsModelWindow.this.brg.table.updateUI();
					float money=0;
					for(Object str:vector_boss){
						Vector v_money=(Vector) str;
						money+=Float.parseFloat((v_money.get(6)+""));
					}
					AddReturnGoodsModelWindow.this.brg.tf_wantmoney.setText(money+"");
					AddReturnGoodsModelWindow.this.brg.tf_paymoney.setText(money+"");
				} catch (Exception e2) {
					
				}
				if(vector_boss.isEmpty()){
				}else{	
					AddReturnGoodsModelWindow.this.setVisible(false);
				}
			}
		});
		/**
		 * 左边的数据加入到右边
		 */
		table1.addMouseListener(new MyMouse());
		table2.addMouseListener(new MyMouse());
		this.setModal(true);
		this.setVisible(true);
	}
	/**
	 * 表的双击事件和右键事件
	 * @author lxh
	 *
	 */
	public class MyMouse extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			if(e.getButton()==1&&e.getClickCount()==2){
				if(table1.isShowing()){
					try{
						setTableL_C(table1);
					}catch (Exception e2) {
						
					}
				}else if(table2.isShowing()){
					try{
						setTableL_C(table2);
					}catch (Exception e2) {
						
					}
				}
			}	
		}
	}
	/**
	 * 左中表格加到右边
	 * @param table_l
	 */
	public void setTableL_C(JTable table){
		Vector date=new Vector();
		Vector vector=(Vector) get_goods_dao.getAllGoods1("").get(table.getSelectedRow());
		try{
			int sum=Integer.parseInt(JOptionPane.showInputDialog("输入数量:"));
			date.add(vector.get(0));
			date.add(vector.get(1));
			date.add(vector.get(2));
			date.add(vector.get(3));
			date.add(vector.get(4));
			date.add(sum);
			date.add(sum*Float.parseFloat(vector.get(4)+""));
			vector_boss.add(date);
			table3model=new DefaultTableModel(vector_boss,columnNames3);
			table3.setModel(table3model);
			table3.updateUI();
		}catch (Exception e2) {
			
		}
	}/**
	 * 是否选择了表，并获取选择的表的行
	 * @return
	 */
	public Vector isSelectTable(){
		Vector vector=null;
		if(table1.isShowing()){
			try{
				 vector=(Vector) get_goods_dao.getAllGoods1("").get(table1.getSelectedRow());
				 
			}catch (Exception e2) {
				
			}
		}else if(table2.isShowing()){
			try{
				 vector=(Vector) get_goods_dao.getAllGoods1("").get(table2.getSelectedRow());
			}catch (Exception e2) {
				
			}
		}
		return vector;
		}
	/**
	 * 输入编号或者名字查询数据
	 */
	public void updaateTable(DefaultTableModel medel,JTable table,Vector vector,String txt){
		medel=new DefaultTableModel(get_goods_dao.getAllGoods1(txt),vector);
		table.setModel(medel);
		table.updateUI();
	}
}
