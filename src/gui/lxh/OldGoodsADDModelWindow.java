package gui.lxh;

import java.awt.BorderLayout;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import bean.GoodsType;

import util.TypeWindow;

import dao.GetAllGoodsDao;



public class OldGoodsADDModelWindow extends JDialog {
	public Float money=0f;
	GetAllGoodsDao get_goods_dao=null;																		//定义获取商品的dao对象
	JPanel p_left,p_right,p_l_up,p_l_down,p_r_up,p_r_down,p_l_up_c,p_l_up_d,p_l_up_c1,p_l_up_c2,p_l_up_c3;	//大模块来说，分为左右两个panel
	
	//选项卡及其所包含的三个面板
	JTabbedPane tabbed_goods;
	JPanel p_tab_c,p_tree;
	
	//左边其他组件
	JLabel label_one,label_two;
	JTextField tf_Info;
	JButton btn_add,btn_addto;

	JCheckBox  rbtn_depot,rbtn_supplier;
	
	//右边其他组件
	JButton btn_alter,btn_delete,btn_ok,btn_cancel;
	
	//四个表，选项卡里面三个，右边一个
	public JTable table_l,table_c,table_r,table_right;
	public String[] l={"商品编号","商品名称","单位","规格","参考进价","库存数","参考售价"};
	public String[] r={"商品编号","商品名称","数量","单价","单位","规格","颜色","进货日期","参考售价"};
	public String[] right={"商品编号","商品名称","单位","规格","进价","数量","总金额"};
	public Vector vector_l,vector_c,vector_r,vector_right,vector_boss;            //vector_boss是全局维克托，用于存右边表格的数据
	public DefaultTableModel model_l,model_c,model_r,model_right;
	
	
	TypeWindow p_tree_type=null; 					//定义我是一棵树
	InputGoodsModelWindow igm=null; 		//窗口对象
	
	public OldGoodsADDModelWindow(InputGoodsModelWindow igm){
	
		this.igm=igm;
		//------------------初始化------------------------
		get_goods_dao=new GetAllGoodsDao();			
		
		p_left=new JPanel();
		p_right=new JPanel();
		p_left.setBorder(BorderFactory.createTitledBorder(""));
		p_right.setBorder(BorderFactory.createTitledBorder("所选商品"));
		
		p_l_up=new JPanel();																//左《--//左右边边的面板分为两个panel
		p_l_down=new JPanel();
																							//右边
		p_r_up=new JPanel();
		p_r_down=new JPanel();
		
		p_l_up_c=new JPanel();																//左上分两个panel，一个在中，一个在下
		p_l_up_c1=new JPanel();
		p_l_up_c2=new JPanel();
		p_l_up_c3=new JPanel();
		p_l_up_d=new JPanel();
		
		p_tab_c=new JPanel();
		p_tree=new JPanel();
		tabbed_goods=new JTabbedPane();														//选项卡

		
		label_one=new JLabel("步骤一：");													//左边两个红色的标签
		label_two=new JLabel("步骤二：");
		label_one.setForeground(Color.red);
		label_two.setForeground(Color.red);
																							//左边其他的组件
		tf_Info=new JTextField(15);
		btn_add=new JButton("  添加新商品  ");
		btn_addto=new JButton("加入所选商品");
		rbtn_depot=new JCheckBox("只显示仓库的商品信息");
		rbtn_supplier=new JCheckBox("只显示当前供货商的商品信息");
	
		add(rbtn_depot);
		add(rbtn_supplier);
		
		//右边的其他组件
		btn_alter=new JButton("修改");
		btn_delete=new JButton("删除");
		btn_ok=new JButton("确定");
		btn_cancel=new JButton("取消");
		
		//四个表以及model
		vector_l=new Vector();
		vector_c=new Vector();
		vector_r=new Vector();
		vector_right=new Vector();
		vector_boss=new Vector();						//全局维克托，用于存右边表格的数据
		for(String str:l){
			vector_l.add(str);
		}
		for(String str:l){
			vector_c.add(str);
		}
		for(String str:r){
			vector_r.add(str);
		}
		for(String str:right){
			vector_right.add(str);
		}
		model_l=new DefaultTableModel(get_goods_dao.getAllGoods1(""),vector_l);
		model_c=new DefaultTableModel(get_goods_dao.getAllGoods1(""),vector_c);
		model_r=new DefaultTableModel(get_goods_dao.getRecentlyGoods(""),vector_r);
		model_right=new DefaultTableModel(null,vector_right);
		
		table_l=new JTable(model_l){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table_c=new JTable(model_c){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table_r=new JTable(model_r){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table_right=new JTable(model_right){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
				
		//-------------添加--------------
		
																								//左上分两个panel，一个在中，一个在下,现在开始加加东西
		p_l_up_c.setLayout(new GridLayout(3,1));
		p_l_up_c1.add(label_one);	
		p_l_up_c1.add(new JLabel("请输入商品编号或名称查询商品，查询到商品后添加到右边所选商品"));
		p_l_up_c1.add(new JLabel("  →"));
		p_l_up_c2.add(label_two);	
		p_l_up_c2.add(new JLabel("如果是新的商品项目，即商品名称不在列表中请点→"));
		p_l_up_c2.add(btn_add);
		p_l_up_c3.add(new JLabel("输入商品编号或者名称查询商品："));
		p_l_up_c3.add(tf_Info);
		p_l_up_c3.add(btn_addto);
		p_l_up_c.add(p_l_up_c1);
		p_l_up_c.add(p_l_up_c2);
		p_l_up_c.add(p_l_up_c3);
		
		p_l_up_d.setBorder(BorderFactory.createTitledBorder(""));	
		p_l_up_d.add(rbtn_depot);
		p_l_up_d.add(rbtn_supplier);
		
		p_l_up.setBorder(BorderFactory.createTitledBorder(""));	
		p_l_up.setLayout(new BorderLayout());
		p_l_up.add(p_l_up_c,BorderLayout.CENTER);
		p_l_up.add(p_l_up_d,BorderLayout.SOUTH);															//左上搞定
		
		
		/**
		 * 开始左下的选项卡搞事情
		 */
		p_tree_type=new TypeWindow();	
		p_tree.setLayout(new GridLayout(1,1));									//初始化我是一棵树
		p_tree.add(new JScrollPane(p_tree_type));
		p_tab_c.setLayout(new BorderLayout());
		p_tab_c.add(p_tree,BorderLayout.WEST);
		p_tab_c.add(new JScrollPane(table_c),BorderLayout.CENTER);
		tabbed_goods.add("商品清单",new JScrollPane(table_l));
		tabbed_goods.add("商品列表",p_tab_c);
		tabbed_goods.add("最近进货",new JScrollPane(table_r));
		
		p_l_down.setLayout(new GridLayout(1,1));
		p_l_down.add(tabbed_goods);																			//左下搞定
		
		
		/**
		 *开始左边面板两个面板
		 */
		p_left.setLayout(new BorderLayout());
		p_left.add(p_l_up,BorderLayout.NORTH);
		p_left.add(tabbed_goods,BorderLayout.CENTER);
		
		/**
		 * 左边搞定	
		 */
		
		/**
		 * ------开始右边--------------------
		 */
		p_r_down.setBorder(BorderFactory.createTitledBorder(""));	
		p_r_up.setLayout(new GridLayout(1,1));
		p_r_up.add(new JScrollPane(table_right));
		p_r_down.add(btn_alter);
		p_r_down.add(btn_delete);
		p_r_down.add(btn_ok);
		p_r_down.add(btn_cancel);
		p_right.setLayout(new BorderLayout());
		p_right.add(p_r_down,BorderLayout.SOUTH);
		p_right.add(p_r_up,BorderLayout.CENTER);
		
		
		this.setLayout(new GridLayout(1,2));
		this.add(p_left);
		this.add(p_right);
		this.setBounds(0, 0, 1100,700);
		this.setTitle("商品添加(采购进货)");
		this.setLocationRelativeTo(null);
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddGoodsModleWindow(OldGoodsADDModelWindow.this.igm);
			}
		});
		/**
		 * 只显示所选仓库的商品信息
		 * or 只显示所选供货商的商品信息
		 * or 只显示所选仓库的商品信息&&只显示所选供货商的商品信息
		 */
		rbtn_depot.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				if(rbtn_depot.isSelected()&&rbtn_supplier.isSelected()){
					String dname=OldGoodsADDModelWindow.this.igm.cbox_depot.getSelectedItem().toString();
					String sname=OldGoodsADDModelWindow.this.igm.tf_supplier.getText();
					model_l=new DefaultTableModel(get_goods_dao.getTwoOnlyGoods(dname, sname),vector_l);
					table_l.setModel(model_l);
					table_l.updateUI();
				}else if(rbtn_depot.isSelected()&&!rbtn_supplier.isSelected()){
					String dname=OldGoodsADDModelWindow.this.igm.cbox_depot.getSelectedItem().toString();
					model_l=new DefaultTableModel(get_goods_dao.getNowDepotGoods(dname),vector_l);
					table_l.setModel(model_l);
					table_l.updateUI();
				}else if(!rbtn_depot.isSelected()&&!rbtn_supplier.isSelected()){
					model_l=new DefaultTableModel(get_goods_dao.getAllGoods1(""),vector_l);
					table_l.setModel(model_l);
					table_l.updateUI();
				}else{
					String sname=OldGoodsADDModelWindow.this.igm.tf_supplier.getText();
					model_l=new DefaultTableModel(get_goods_dao.getNowSupplierGoods(sname),vector_l);
					table_l.setModel(model_l);
					table_l.updateUI();
				}
			}
		});
		rbtn_supplier.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(rbtn_depot.isSelected()&&rbtn_supplier.isSelected()){
					String dname=OldGoodsADDModelWindow.this.igm.cbox_depot.getSelectedItem().toString();
					String sname=OldGoodsADDModelWindow.this.igm.tf_supplier.getText();
					model_l=new DefaultTableModel(get_goods_dao.getTwoOnlyGoods(dname, sname),vector_l);
					table_l.setModel(model_l);
					table_l.updateUI();
				}else if(rbtn_supplier.isSelected()&&!rbtn_depot.isSelected()){
					String sname=OldGoodsADDModelWindow.this.igm.tf_supplier.getText();
					model_l=new DefaultTableModel(get_goods_dao.getNowSupplierGoods(sname),vector_l);
					table_l.setModel(model_l);
					table_l.updateUI();
				}else if(!rbtn_depot.isSelected()&&!rbtn_supplier.isSelected()){
					model_l=new DefaultTableModel(get_goods_dao.getAllGoods1(""),vector_l);
					table_l.setModel(model_l);
					table_l.updateUI();
				}else{
					String dname=OldGoodsADDModelWindow.this.igm.cbox_depot.getSelectedItem().toString();
					model_l=new DefaultTableModel(get_goods_dao.getNowDepotGoods(dname),vector_l);
					table_l.setModel(model_l);
					table_l.updateUI();
				}
			}
		});
		
		/**
		 * 左边的数据加入到右边
		 */
		table_l.addMouseListener(new MyMouse());
		table_c.addMouseListener(new MyMouse());
		table_r.addMouseListener(new MyMouse());
		
		/**
		 * 加入所选商品
		 */
		btn_addto.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {	
					if(table_l.isShowing()){
						try{
							setTableL_C(table_l);
						}catch (Exception e2) {
							
						}
					}else if(table_c.isShowing()){
						try{
							setTableL_C(table_c);
						}catch (Exception e2) {
							
						}
					}else if(table_r.isShowing()){
						try{
							setTableR(table_r);
						}catch (Exception e2) {
							
						}
					}		
			}
		});
		/**
		 * 修改事件
		 * @author lxh
		 *
		 */
		btn_alter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Vector vector=(Vector) vector_boss.get(table_right.getSelectedRow());
					int num=Integer.parseInt(JOptionPane.showInputDialog("请输入要修改的数量："));
					vector.set(3, num);
					model_right=new DefaultTableModel(vector_boss,vector_right);
					table_right.setModel(model_right);
					table_right.updateUI();
				} catch (Exception e2) {
					
				}
				
			}
		});
		/**
		 * 删除右边选择的行
		 * 
		 */
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Vector vector=(Vector) vector_boss.remove(table_right.getSelectedRow());
					model_right=new DefaultTableModel(vector_boss,vector_right);
					table_right.setModel(model_right);
					table_right.updateUI();
				} catch (Exception e2) {
					
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
					model_l=new DefaultTableModel(get_goods_dao.getAllGoods(id),vector_l);
					table_c.setModel(model_l);
					table_c.updateUI();
				}
				
			}
		});
		/**
		 * 输入编号或名字查询商品列表
		 */
		tf_Info.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				
				updateTable(model_l, table_l, vector_l,tf_Info.getText());
				updateTable(model_c, table_c, vector_c,tf_Info.getText());
				updateTable(model_r, table_r, vector_r,tf_Info.getText());
			}
			public void insertUpdate(DocumentEvent e) {
				updateTable(model_l, table_l, vector_l,tf_Info.getText());
				updateTable(model_c, table_c, vector_c,tf_Info.getText());
				updateTable(model_r, table_r, vector_r,tf_Info.getText());
			}
			public void changedUpdate(DocumentEvent e) {
				updateTable(model_l, table_l, vector_l,tf_Info.getText());
				updateTable(model_c, table_c, vector_c,tf_Info.getText());
				updateTable(model_r, table_r, vector_r,tf_Info.getText());
			}
		});
		/**
		 * 确定事件
		 */
		btn_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					OldGoodsADDModelWindow.this.igm.vectorboss=vector_boss;
					OldGoodsADDModelWindow.this.igm.model=new DefaultTableModel(OldGoodsADDModelWindow.this.igm.vectorboss,OldGoodsADDModelWindow.this.igm.vector);
					OldGoodsADDModelWindow.this.igm.table.setModel(OldGoodsADDModelWindow.this.igm.model);
					OldGoodsADDModelWindow.this.igm.table.updateUI();
					float money=0;
					for(Object str:vector_boss){
						Vector v_money=(Vector) str;
						money+=Float.parseFloat((v_money.get(6)+""));
					}
					OldGoodsADDModelWindow.this.igm.tf_should_pay.setText(money+"");
					OldGoodsADDModelWindow.this.igm.tf_reality_pay.setText(money+"");
				} catch (Exception e2) {
					
				}
				if(vector_boss.isEmpty()){
				}else{	
					OldGoodsADDModelWindow.this.setVisible(false);
				}
			}
		});
		/**
		 * 取消事件
		 */
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OldGoodsADDModelWindow.this.setVisible(false);
			}
		});
		this.setModal(true);
		this.setVisible(true);
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
			model_right=new DefaultTableModel(vector_boss,vector_right);
			table_right.setModel(model_right);
			table_right.updateUI();
		}catch (Exception e2) {
			
		}
	}
	//最近进货加到右边
	public void setTableR(JTable table){
		Vector date=new Vector();
		Vector vector=(Vector) get_goods_dao.getAllGoods1("").get(table.getSelectedRow());
		try{
			int sum=Integer.parseInt(JOptionPane.showInputDialog("输入数量:"));
			date.add(vector.get(0));
			date.add(vector.get(1));
			date.add(vector.get(4));
			date.add(vector.get(5));
			date.add(vector.get(3));
			date.add(sum);
			date.add(sum*Float.parseFloat(vector.get(4)+""));
			vector_boss.add(date);
			model_right=new DefaultTableModel(vector_boss,vector_right);
			table_right.setModel(model_right);
			table_right.updateUI();
		}catch (Exception e2) {
			
		}
	}
	
	
	/**
	 * 表的双击事件和右键事件
	 * @author lxh
	 *
	 */
	public class MyMouse extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			if(e.getButton()==1&&e.getClickCount()==2){
				if(table_l.isShowing()){
					try{
						setTableL_C(table_l);
					}catch (Exception e2) {
						
					}
				}else if(table_c.isShowing()){
					try{
						setTableL_C(table_c);
					}catch (Exception e2) {
						
					}
				}else if(table_r.isShowing()){
					try{
						setTableR(table_r);
					}catch (Exception e2) {
						
					}
				}
				
			}else if(e.getButton()==3&&isSelectTable()!=null){
				new AlterGoodsModleWindow(OldGoodsADDModelWindow.this);
			}
			
		}
	}
	/**
	 * 是否选择了表，并获取选择的表的行
	 * @return
	 */
	public Vector isSelectTable(){
		Vector vector=null;
		if(table_l.isShowing()){
			try{
				 vector=(Vector) get_goods_dao.getAllGoods1("").get(table_l.getSelectedRow());
				 
			}catch (Exception e2) {
				
			}
		}else if(table_c.isShowing()){
			try{
				 vector=(Vector) get_goods_dao.getAllGoods1("").get(table_c.getSelectedRow());
			}catch (Exception e2) {
				
			}
		}else if(table_r.isShowing()){
			try{
				vector=(Vector) get_goods_dao.getAllGoods1("").get(table_r.getSelectedRow());
			}catch (Exception e2) {
				
			}
		}
		return vector;
	}
	/**
	 * 输入编号或者名字查询数据
	 */
	public void updateTable(DefaultTableModel medel,JTable table,Vector vector,String txt){
		medel=new DefaultTableModel(get_goods_dao.getAllGoods1(txt),vector);
		table.setModel(medel);
		table.updateUI();
	}
	
	
}
