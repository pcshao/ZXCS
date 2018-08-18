package gui.lxh;

import gui.lxh.OldGoodsADDModelWindow.MyMouse;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Collection;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import dao.InOrderDao;

import bean.orders.InOrder;
import util.ImportExportHelp;
import util.MyDateChooser;

//���۵��ݲ�ѯ
public class BuyDocumentsCheckModelWindow extends JDialog{
	int i=1;
	/*
	 * ����BuyReturnGoodsModelWindow brg����
	 */
	BuyReturnGoodsModelWindow brg=null;
	/**
	 * ����inorder  dao
	 */
	InOrderDao in_order_dao=null;
	JPanel jp_top,jp_center;			//��Ϊ�������в�
	JPanel jp_top_p1,jp_top_p2;
	JPanel jp_tab_p1,jp_tab_p2,jp_tab_p3;
	JPanel jp_tab_p1_1,jp_tab_p1_2,jp_tab_p2_1,jp_tab_p2_2;	//�в������������
	JTabbedPane tabbed_center;
	JButton btn_big_select,btn_look_order,btn_return,btn_output,btn_stamp,btn_exit,btn_select,btn_look;
	JTextField tf_name,tf_check,tf_order;
	Vector columnNames1,columnNames2,columnNames3,columnNames4,columnNames5;
	DefaultTableModel table1model,table2model,table3model,table4model,table5model;
	JTable table1,table2,table3,table4,table5;
	String[] arr1={"���ݺ�","��������","����������","�ֿ�����","Ӧ�����","ʵ�����","������","����Ա","֧����ʽ","��ע"};		
	String[] arr2={"��Ʒ���","��Ʒ����","����","��λ","����","�ܽ��","�������","����ͺ�","��ע","������"};
	String[] arr3={"��Ʒ���","��Ʒ����","����","��λ","����","�ܽ��","�������","����ͺ�","��ע"};
	String[] arr4={"����������","���ݺ�","��������","��Ʒ���","��Ʒ����","��λ","����ͺ�","����","����","�ܽ��","�ֿ�","������"};
	String[] arr5={"����������","���ݺ�","��������","��Ʒ���","��Ʒ����","��λ","����ͺ�","����","����","�ܽ��","�ֿ�","������"};
	
	MyDateChooser date1,date2;
	
	/**
	 * 
	 * ����װtable2����ά���У����ڴ���������
	 */
	Vector<Vector> vector_boss=null;
	
	public BuyDocumentsCheckModelWindow(BuyReturnGoodsModelWindow brg){
		/**
		 * ��ʼ�������ڶ���
		 */
		this.brg=brg;
		/**
		 * ��ʼ�� ����װtable2����ά����
		 */
		vector_boss=new Vector<Vector>();
		/**
		 * ��ʼ��inoreder  dao
		 */
		in_order_dao=new InOrderDao();
		date1=MyDateChooser.getInstance("yyyy-MM-dd");
		date2=MyDateChooser.getInstance("yyyy-MM-dd");
		
		jp_top=new JPanel();
		jp_top_p1=new JPanel();
		jp_top_p2=new JPanel();
		btn_big_select=new JButton("��ϸ����");
		btn_look_order=new JButton("�鿴����");
		btn_return=new JButton("�����˻�(�޵��ݱ�)");
		btn_output=new JButton(" ��    �� ");
		btn_stamp=new JButton(" ��    ӡ ");
		btn_exit=new JButton(" ��    �� ");
		btn_select=new JButton(" ��    ѯ ");
		btn_look=new JButton(" ��     �� ");
		tf_name=new JTextField(10);
		tf_check=new JTextField(10);
		tf_order=new JTextField(15);
		/**
		 * ����
		 * @author lxh
		 */
		jp_top.setLayout(new GridLayout(2,1));
		jp_top_p1.setBorder(BorderFactory.createTitledBorder(""));
		jp_top_p1.add(btn_big_select);
		jp_top_p1.add(btn_look_order);
		jp_top_p1.add(btn_return);
		jp_top_p1.add(btn_output);
		jp_top_p1.add(btn_stamp);
		jp_top_p1.add(btn_exit);
		jp_top_p1.add(new JLabel());
		jp_top_p1.add(new JLabel());
		jp_top.add(jp_top_p1);
		
		jp_top_p2.add(new JLabel("��ѯ���ڣ�"));
		tf_name.setText(date1.getStrDate());
		date1.register(tf_name);
		jp_top_p2.add(tf_name);
		jp_top_p2.add(new JLabel("��"));
		tf_check.setText(date2.getStrDate());
		date2.register(tf_check);
		jp_top_p2.add(tf_check);
		jp_top_p2.add(btn_select);
		jp_top_p2.add(new JLabel("��������/���ݺŲ�ѯ��"));
		jp_top_p2.add(tf_order);
		jp_top_p2.add(btn_look);
		jp_top.add(jp_top_p2);
		/**
		 * �в�
		 * @author lxh
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
		table1model=new DefaultTableModel(in_order_dao.getAllInorder(""),columnNames1);
		table1=new JTable(table1model){														//���ñ�񲻿��Ա༭
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table2model=new DefaultTableModel(null,columnNames2);
		table2=new JTable(table2model){														//���ñ�񲻿��Ա༭
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table3model=new DefaultTableModel(in_order_dao.getAlwaysInorder1(""),columnNames3);
		table3=new JTable(table3model){														//���ñ�񲻿��Ա༭
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table4model=new DefaultTableModel(null,columnNames4);
		table4=new JTable(table4model){														//���ñ�񲻿��Ա༭
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table5model=new DefaultTableModel(in_order_dao.getGoodsInorderInfo(""),columnNames5);
		table5=new JTable(table5model){														//���ñ�񲻿��Ա༭
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jp_center.setLayout(new GridLayout(1,1));
		//����ѡ�
		tabbed_center.add("���ݱ�",jp_tab_p1);
		jp_tab_p1.setLayout(new GridLayout(2,1));
		jp_tab_p1.add(jp_tab_p1_1);
		jp_tab_p1_1.setLayout(new GridLayout(1,1));
		jp_tab_p1_1.add(new JScrollPane(table1));
		jp_tab_p1.add(jp_tab_p1_2);
		jp_tab_p1_2.setBorder(BorderFactory.createTitledBorder("���ݵ���ϸ��Ϣ��"));
		jp_tab_p1_2.setLayout(new GridLayout(1,1));
		jp_tab_p1_2.add(new JScrollPane(table2));
		//����ѡ�
		tabbed_center.add("���ܱ�",jp_tab_p2);
		jp_tab_p2.setLayout(new GridLayout(2,1));
		jp_tab_p2.add(jp_tab_p2_1);
		jp_tab_p2_1.setLayout(new GridLayout(1,1));
		jp_tab_p2_1.add(new JScrollPane(table3));
		jp_tab_p2.add(jp_tab_p2_2);
		jp_tab_p2_2.setBorder(BorderFactory.createTitledBorder("��Ʒ�ɹ���ϸ��"));
		jp_tab_p2_2.setLayout(new GridLayout(1,1));
		jp_tab_p2_2.add(new JScrollPane(table4));
		//��ϸѡ�
		tabbed_center.add("��ϸ��",jp_tab_p3);
		jp_tab_p3.setLayout(new GridLayout(1,1));
		jp_tab_p3.add(new JScrollPane(table5));
		jp_center.add(tabbed_center);
		this.setLayout(new BorderLayout());
		this.add(jp_top,BorderLayout.NORTH);
		this.add(jp_center,BorderLayout.CENTER);
		this.setTitle("�ɹ����ݲ�ѯ");	
		this.setBounds(300, 100, 850, 550);
		this.setLocationRelativeTo(null);
		
		/**
		 * table1 �ĵ���¼�
		 */
		table1.addMouseListener(new TableMouse(table1));
		table3.addMouseListener(new Table3Mouse(table3));
		
		/**
		 * ���ڲ�ѯ�¼�
		 */
		btn_select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			String date1=tf_name.getText()+"";
			String date2=tf_check.getText()+"";
			if(table1.isShowing()) {
				i=2;
				table1model=new DefaultTableModel(in_order_dao.getAllInorder(date1,date2),columnNames1);
				table1.setModel(table1model);
				table1.updateUI();
			}else if(table3.isShowing()) {
				i=2;
				table3model=new DefaultTableModel(in_order_dao.getAlwaysInorder(date1,date2),columnNames3);
				table3.setModel(table3model);
				table3.updateUI();
			}else if(table5.isShowing()) {
				i=2;
				table5model=new DefaultTableModel(in_order_dao.getGoodsInorderInfo(date1,date2),columnNames5);
				table5.setModel(table5model);
				table5.updateUI();
			}
			}
		});
		/**
		 * �����������ƺͶ���id����
		 
		 */
		btn_look.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table1.isShowing()) {
					i=3;
					table1model=new DefaultTableModel(in_order_dao.getAllInorder(tf_order.getText()),columnNames1);
					table1.setModel(table1model);
					table1.updateUI();
				}else if(table3.isShowing()) {
					i=3;
					table3model=new DefaultTableModel(in_order_dao.getAlwaysInorder1(tf_order.getText()),columnNames3);
					table3.setModel(table3model);
					table3.updateUI();
				}else if(table5.isShowing()) {
					i=3;
					table5model=new DefaultTableModel(in_order_dao.getGoodsInorderInfo(tf_order.getText()),columnNames5);
					table5.setModel(table5model);
					table5.updateUI();
				}
				
			}
		});
		/**
		 * ��ϸ����
		 * 
		 */
		btn_big_select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "�����и��ݵ��ź͹����̲��ң���");
			}
		});
		/**
		 * �鿴����
		 */
		btn_look_order.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "ѡ�񵥾ݱ��е�һ�����ݣ������������ϸ��Ϣ�ͻ�����ڶ�Ӧ�����·���");
				
			}
		});
		/**
		 *�����˻��¼�
		 */
		btn_return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Vector<Vector> vector_super=new Vector();
					Vector vector_son=new Vector();
					for(Vector v:vector_boss) {
						vector_son.add(v.get(0));
						vector_son.add(v.get(1));
						vector_son.add(v.get(3));
						vector_son.add(v.get(7));
						vector_son.add(v.get(2));
						vector_son.add(v.get(4));
						vector_son.add(v.get(5));
						vector_super.add(vector_son);	
						BuyDocumentsCheckModelWindow.this.brg.tf_name.setText(v.get(9)+"");
					}
					BuyDocumentsCheckModelWindow.this.brg.data=vector_super;
					BuyDocumentsCheckModelWindow.this.brg.tablemodel=new DefaultTableModel(BuyDocumentsCheckModelWindow.this.brg.data,BuyDocumentsCheckModelWindow.this.brg.columnNames);
					BuyDocumentsCheckModelWindow.this.brg.table.setModel(BuyDocumentsCheckModelWindow.this.brg.tablemodel);
					BuyDocumentsCheckModelWindow.this.brg.table.updateUI();
					float money=0;
					for(Object str:vector_super){
						Vector v_money=(Vector) str;
						money+=Float.parseFloat((v_money.get(6)+""));
					}
					BuyDocumentsCheckModelWindow.this.brg.tf_wantmoney.setText(money+"");
					BuyDocumentsCheckModelWindow.this.brg.tf_paymoney.setText(money+"");
				} catch (Exception e2) {
					
				}
				if(vector_boss.isEmpty()){
				}else{	
					BuyDocumentsCheckModelWindow.this.setVisible(false);
				}
			}
		});
		/**
		 * ��ӡ�¼�
		 */
		btn_stamp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "�˹�����Ҫ��ͨ��Ա����ʹ�ã���������ϵlxh��ͨ");
			}
		});
		/**
		 * �����¼�
		 */
		btn_output.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser out_file=new JFileChooser();
					out_file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					out_file.showSaveDialog(null);
					String str=out_file.getSelectedFile().getAbsolutePath();
					Vector<Vector> da=new Vector<Vector>();
					da.add(columnNames1);
					da.addAll(vector_boss);
					
						ImportExportHelp.ExportData(da, str);
						JOptionPane.showMessageDialog(null, "�����ɹ�");
					} catch (RowsExceededException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					} catch (WriteException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					} catch (IOException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}catch (NullPointerException ex) {
						JOptionPane.showMessageDialog(null, "�����ݾ��棡");
					}
					
			}
		});
		
		/**
		 * �˳��¼�
		 */
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuyDocumentsCheckModelWindow.this.setVisible(false);
			}
		});
		
		this.setModal(true);
		this.setVisible(true);
	}
	/***
	 * 
	 * ���1�ĵ���¼�
	 * ��ʹ���2�����仯
	 *
	 */
	
	public class TableMouse extends MouseAdapter{
		JTable table;
		public TableMouse(	JTable table){
			this.table=table;
		}
		public void mouseClicked(MouseEvent e) {
			if(e.getButton()==1&&isSelectTable1()!=null){
				vector_boss=in_order_dao.getAlwaysInorder(isSelectTable1().get(0)+"");
				table2model=new DefaultTableModel(vector_boss,columnNames2);
				table2.setModel(table2model);
				table2.updateUI();
			}
		}
	}
	/***
	 * 
	 * ���3�ĵ���¼�
	 * ��ʹ���4�����仯
	 *
	 */
	
	public class Table3Mouse extends MouseAdapter{
		JTable table;
		public Table3Mouse(	JTable table){
			this.table=table;
		}
		public void mouseClicked(MouseEvent e) {
			if(e.getButton()==1&&isSelectTable3()!=null){
				jp_tab_p2_2.setBorder(BorderFactory.createTitledBorder("��Ʒ�ɹ���ϸ��"+isSelectTable3().get(1)));
				int gid=Integer.parseInt(isSelectTable3().get(0)+"");
				String oid=isSelectTable3().get(9)+"";
				table4model=new DefaultTableModel(in_order_dao.getGoodsInorderInfo(gid,oid),columnNames4);
				table4.setModel(table4model);
				table4.updateUI();
			}
		}
	}
	/**
	 * 
	 * ���ر��1ѡ�е���
	 */
	public Vector isSelectTable1(){
		Vector vector=null;
		if(table1.isShowing()){
			try{
				if(i==1) {
					vector=(Vector)in_order_dao.getAllInorder("").get(table1.getSelectedRow());	
				}else if(i==2){
					vector=(Vector)in_order_dao.getAllInorder(tf_name.getText(),tf_check.getText()).get(table1.getSelectedRow());	
				}else if(i==3) {
					vector=(Vector)in_order_dao.getAllInorder(tf_order.getText()).get(table1.getSelectedRow());
				}
				 	 
			}catch (Exception e2) {
				
			}
		}
		return vector;
	}
	/**
	 * 
	 * ���ر��3ѡ�е���
	 */
	public Vector isSelectTable3(){
		Vector vector=null;
		if(table3.isShowing()){
			try{		 
				 if(i==1) {
					 vector=(Vector)in_order_dao.getAlwaysInorder1("").get(table3.getSelectedRow());	
					}else if(i==2){
						vector=(Vector)in_order_dao.getAlwaysInorder(tf_name.getText(),tf_check.getText()).get(table3.getSelectedRow());	
					}else if(i==3) {
						vector=(Vector)in_order_dao.getAlwaysInorder1(tf_order.getText()).get(table3.getSelectedRow());	
					}
			}catch (Exception e2) {
				
			}
		}
		return vector;
	}
	/**
	 * ���±�ĺ���
	 * @param args
	 */
	public void UpdateTable (JTable table,DefaultTableModel model){
		table.setModel(model);
		table.updateUI();
	}
	
}
