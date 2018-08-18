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
	GetAllGoodsDao get_goods_dao=null;																		//�����ȡ��Ʒ��dao����
	JPanel p_left,p_right,p_l_up,p_l_down,p_r_up,p_r_down,p_l_up_c,p_l_up_d,p_l_up_c1,p_l_up_c2,p_l_up_c3;	//��ģ����˵����Ϊ��������panel
	
	//ѡ��������������������
	JTabbedPane tabbed_goods;
	JPanel p_tab_c,p_tree;
	
	//����������
	JLabel label_one,label_two;
	JTextField tf_Info;
	JButton btn_add,btn_addto;

	JCheckBox  rbtn_depot,rbtn_supplier;
	
	//�ұ��������
	JButton btn_alter,btn_delete,btn_ok,btn_cancel;
	
	//�ĸ���ѡ������������ұ�һ��
	public JTable table_l,table_c,table_r,table_right;
	public String[] l={"��Ʒ���","��Ʒ����","��λ","���","�ο�����","�����","�ο��ۼ�"};
	public String[] r={"��Ʒ���","��Ʒ����","����","����","��λ","���","��ɫ","��������","�ο��ۼ�"};
	public String[] right={"��Ʒ���","��Ʒ����","��λ","���","����","����","�ܽ��"};
	public Vector vector_l,vector_c,vector_r,vector_right;
	public Vector<Vector> vector_boss;            //vector_boss��ȫ��ά���У����ڴ��ұ߱�������
	public DefaultTableModel model_l,model_c,model_r,model_right;
	
	
	TypeWindow p_tree_type=null; 					//��������һ����
	InputGoodsModelWindow igm=null; 		//���ڶ���
	
	public OldGoodsADDModelWindow(InputGoodsModelWindow igm){
	
		this.igm=igm;
		//------------------��ʼ��------------------------
		get_goods_dao=new GetAllGoodsDao();			
		
		p_left=new JPanel();
		p_right=new JPanel();
		p_left.setBorder(BorderFactory.createTitledBorder(""));
		p_right.setBorder(BorderFactory.createTitledBorder("��ѡ��Ʒ"));
		
		p_l_up=new JPanel();																//��--//���ұ߱ߵ�����Ϊ����panel
		p_l_down=new JPanel();
																							//�ұ�
		p_r_up=new JPanel();
		p_r_down=new JPanel();
		
		p_l_up_c=new JPanel();																//���Ϸ�����panel��һ�����У�һ������
		p_l_up_c1=new JPanel();
		p_l_up_c2=new JPanel();
		p_l_up_c3=new JPanel();
		p_l_up_d=new JPanel();
		
		p_tab_c=new JPanel();
		p_tree=new JPanel();
		tabbed_goods=new JTabbedPane();														//ѡ�

		
		label_one=new JLabel("����һ��");													//���������ɫ�ı�ǩ
		label_two=new JLabel("�������");
		label_one.setForeground(Color.red);
		label_two.setForeground(Color.red);
																							//������������
		tf_Info=new JTextField(15);
		btn_add=new JButton("  �������Ʒ  ");
		btn_addto=new JButton("������ѡ��Ʒ");
		rbtn_depot=new JCheckBox("ֻ��ʾ�ֿ����Ʒ��Ϣ");
		rbtn_supplier=new JCheckBox("ֻ��ʾ��ǰ�����̵���Ʒ��Ϣ");
	
		add(rbtn_depot);
		add(rbtn_supplier);
		
		//�ұߵ��������
		btn_alter=new JButton("�޸�");
		btn_delete=new JButton("ɾ��");
		btn_ok=new JButton("ȷ��");
		btn_cancel=new JButton("ȡ��");
		
		//�ĸ����Լ�model
		vector_l=new Vector();
		vector_c=new Vector();
		vector_r=new Vector();
		vector_right=new Vector();
		vector_boss=new Vector();						//ȫ��ά���У����ڴ��ұ߱�������
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
				
		//-------------���--------------
		
																								//���Ϸ�����panel��һ�����У�һ������,���ڿ�ʼ�ӼӶ���
		p_l_up_c.setLayout(new GridLayout(3,1));
		p_l_up_c1.add(label_one);	
		p_l_up_c1.add(new JLabel("��������Ʒ��Ż����Ʋ�ѯ��Ʒ����ѯ����Ʒ����ӵ��ұ���ѡ��Ʒ"));
		p_l_up_c1.add(new JLabel("  ��"));
		p_l_up_c2.add(label_two);	
		p_l_up_c2.add(new JLabel("������µ���Ʒ��Ŀ������Ʒ���Ʋ����б�������"));
		p_l_up_c2.add(btn_add);
		p_l_up_c3.add(new JLabel("������Ʒ��Ż������Ʋ�ѯ��Ʒ��"));
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
		p_l_up.add(p_l_up_d,BorderLayout.SOUTH);															//���ϸ㶨
		
		
		/**
		 * ��ʼ���µ�ѡ�������
		 */
		p_tree_type=new TypeWindow();	
		p_tree.setLayout(new GridLayout(1,1));									//��ʼ������һ����
		p_tree.add(new JScrollPane(p_tree_type));
		p_tab_c.setLayout(new BorderLayout());
		p_tab_c.add(p_tree,BorderLayout.WEST);
		p_tab_c.add(new JScrollPane(table_c),BorderLayout.CENTER);
		tabbed_goods.add("��Ʒ�嵥",new JScrollPane(table_l));
		tabbed_goods.add("��Ʒ�б�",p_tab_c);
		tabbed_goods.add("�������",new JScrollPane(table_r));
		
		p_l_down.setLayout(new GridLayout(1,1));
		p_l_down.add(tabbed_goods);																			//���¸㶨
		
		
		/**
		 *��ʼ�������������
		 */
		p_left.setLayout(new BorderLayout());
		p_left.add(p_l_up,BorderLayout.NORTH);
		p_left.add(tabbed_goods,BorderLayout.CENTER);
		
		/**
		 * ��߸㶨	
		 */
		
		/**
		 * ------��ʼ�ұ�--------------------
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
		this.setTitle("��Ʒ���(�ɹ�����)");
		this.setLocationRelativeTo(null);
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddGoodsModleWindow(OldGoodsADDModelWindow.this.igm);
			}
		});
		/**
		 * ֻ��ʾ��ѡ�ֿ����Ʒ��Ϣ
		 * or ֻ��ʾ��ѡ�����̵���Ʒ��Ϣ
		 * or ֻ��ʾ��ѡ�ֿ����Ʒ��Ϣ&&ֻ��ʾ��ѡ�����̵���Ʒ��Ϣ
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
		 * ��ߵ����ݼ��뵽�ұ�
		 */
		table_l.addMouseListener(new MyMouse());
		table_c.addMouseListener(new MyMouse());
		table_r.addMouseListener(new MyMouse());
		
		/**
		 * ������ѡ��Ʒ
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
		 * �޸��¼�
		 * @author lxh
		 *
		 */
		btn_alter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Vector vector=(Vector) vector_boss.get(table_right.getSelectedRow());
					int num=Integer.parseInt(JOptionPane.showInputDialog("������Ҫ�޸ĵ�������"));
					vector.set(3, num);
					model_right=new DefaultTableModel(vector_boss,vector_right);
					table_right.setModel(model_right);
					table_right.updateUI();
				} catch (Exception e2) {
					
				}
				
			}
		});
		/**
		 * ɾ���ұ�ѡ�����
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
		 * typeWindow������ĵ���¼�
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
		 * �����Ż����ֲ�ѯ��Ʒ�б�
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
		 * ȷ���¼�
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
		 * ȡ���¼�
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
	 * ���б��ӵ��ұ�
	 * @param table_l
	 */
	public void setTableL_C(JTable table){
		Vector date=new Vector();
		Vector vector=(Vector) get_goods_dao.getAllGoods1("").get(table.getSelectedRow());
		try{
			int sum=Integer.parseInt(JOptionPane.showInputDialog("��������:"));
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
	//��������ӵ��ұ�
	public void setTableR(JTable table){
		Vector date=new Vector();
		Vector vector=(Vector) get_goods_dao.getAllGoods1("").get(table.getSelectedRow());
		try{
			int sum=Integer.parseInt(JOptionPane.showInputDialog("��������:"));
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
	 * ���˫���¼����Ҽ��¼�
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
	 * �Ƿ�ѡ���˱�����ȡѡ��ı����
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
	 * �����Ż������ֲ�ѯ����
	 */
	public void updateTable(DefaultTableModel medel,JTable table,Vector vector,String txt){
		medel=new DefaultTableModel(get_goods_dao.getAllGoods1(txt),vector);
		table.setModel(medel);
		table.updateUI();
	}
	
	
}
