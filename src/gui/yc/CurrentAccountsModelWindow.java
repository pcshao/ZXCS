package gui.yc;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import bean.Customer;

import dao.CustomDao;
import dao.GoodsDao;
import dao.SellOrdersDao;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import util.CastUtil;
import util.ImportExportHelp;
import util.MyDateChooser;

//�������񣨿ͻ���
public class CurrentAccountsModelWindow extends JDialog{
	MyDateChooser dc1,dc2,dc3,dc4,dc5,dc6,dc7,dc8;
	JTabbedPane tabbed;
	JPanel jp_p1,jp_p2,jp_p3,jp_p4;
	JPanel jp_p1_top,jp_p1_center;
	JPanel jp_p1_top_1,jp_p1_top_2;
	JPanel jp_p1_center_p1,jp_p1_center_p2;
	JPanel jp_p1_center_p2_top,jp_p1_center_p2_center;
	JPanel jp_tabbed_center_p1,jp_tabbed_center_p2;
	JButton btn_p1_1,btn_p1_2,btn_p1_3,btn_p1_4,btn_p1_5,btn_p1_6,btn_p1_7,btn_p1_8;
	JTextField jf_date1,jf_date2,tf_name;
	JButton btn_look,btn_check;
	JRadioButton rbtn_date,rbtn_type;
	ButtonGroup bg;
	Vector columnNames1,columnNames2,columnNames3;
	Vector<Vector> data1,data2,data3;
	DefaultTableModel table1model,table2model,table3model;
	JTable table1,table2,table3;
	JTabbedPane tabbed_center;
	String[] arr1={"�ͻ�����","����","����","Ӧ�ս��","ʵ�ս��","Ƿ����","������","����Ա","��ע"};
	String[] arr2={"��Ʒ���","��Ʒ����","��λ","����","����","�ܽ��","����ͺ�"};
	String[] arr3={"�ͻ�����","����","����","����","�տ���","������","����Ա","��ע"};
	
	JPanel jp_p2_top,jp_p2_center;
	JPanel jp_p2_top_p1,jp_p2_top_p2;
	JPanel jp_p2_center_p1,jp_p2_center_p2;
	JButton btn_p2_1,btn_p2_2,btn_p2_3,btn_p2_4; 	
	JTextField tf_p2_1,tf_p2_2,tf_p2_3;
	JButton btn_p2_5,btn_p2_6;
	Vector columnNames4,columnNames5;
	Vector<Vector> data4,data5;
	DefaultTableModel table4model,table5model;
	JTable table4,table5;
	String[] arr4={"��Ʒ���","��Ʒ����","��λ","��������","�����ܽ��","����ͺ�","��ע"};
	String[] arr5={"�ͻ�����","���ݺ�","��������","��λ","����","����","�ܽ��","����ͺ�"};
	
	JPanel jp_p3_top,jp_p3_center;
	JButton btn_p3_1,btn_p3_2,btn_p3_3,btn_p3_4,btn_p3_5;
	JPanel jp_p3_top_p1,jp_p3_top_p2;
	JTextField tf_p3_1,tf_p3_2,tf_p3_3;
	Vector columnNames6;
	Vector<Vector> data6;
	DefaultTableModel table6model;
	JTable table6;
	String[] arr6={"�ͻ�����","��Ʒ���۶��Ʒ�˻��","�ϼƽ��","�ҷ�Ӧ�ս��","�ҷ�ʵ�ս��","δ�ս��"};
	
	JPanel jp_p4_top,jp_p4_center;
	JButton btn_p4_1,btn_p4_2,btn_p4_3,btn_p4_4,btn_p4_5,btn_p4_6;
	JPanel jp_p4_top_p1,jp_p4_top_p2;
	JTextField tf_p4_1,tf_p4_2,tf_p4_3;
	Vector columnNames7;
	Vector<Vector> data7;
	DefaultTableModel table7model;
	JTable table7;
	String[] arr7={"���ݱ��","��������","�տʽ","�ͻ�����","������","����Ա","��ע"};
	

	 Customer ret1,ret2,ret3,ret4;		//���صĿͻ��ͻ�
	
	public CurrentAccountsModelWindow(){
		dc1=MyDateChooser.getInstance("yyyy-MM-dd");
		dc2=MyDateChooser.getInstance("yyyy-MM-dd");
		dc3=MyDateChooser.getInstance("yyyy-MM-dd");
		dc4=MyDateChooser.getInstance("yyyy-MM-dd");
		dc5=MyDateChooser.getInstance("yyyy-MM-dd");
		dc6=MyDateChooser.getInstance("yyyy-MM-dd");
		dc7=MyDateChooser.getInstance("yyyy-MM-dd");
		dc8=MyDateChooser.getInstance("yyyy-MM-dd");
		tabbed=new JTabbedPane();
		jp_p1=new JPanel();//�ͻ����е���
		jp_p2=new JPanel();//�ͻ��������
		jp_p3=new JPanel();//�ͻ��˻���Ϣ
		jp_p4=new JPanel();//�ͻ�������ϸ
		jp_p1_top=new JPanel();
		jp_p1_center=new JPanel();
		btn_p1_1=new JButton("�ҷ��տ�");
		btn_p1_2=new JButton("�鿴����");
		btn_p1_3=new JButton("ɾ������");
		btn_p1_4=new JButton("�����˻�");
		btn_p1_5=new JButton("���ݹ���");
		btn_p1_6=new JButton("����");
		btn_p1_7=new JButton("��ӡ");
		btn_p1_8=new JButton("�˳�");
		jp_p1_top_1=new JPanel();
		jp_p1_top_2=new JPanel();
		jf_date1=new JTextField(10);
		jf_date2=new JTextField(10);
		tf_name=new JTextField(8);
		btn_look=new JButton("��ͻ�");
		btn_check=new JButton("��ѯ");
		bg=new ButtonGroup();
		rbtn_date=new JRadioButton("����������");
		rbtn_type=new JRadioButton("����������");
		jp_p1_center_p1=new JPanel();
		jp_p1_center_p2=new JPanel();
		//��1
		columnNames1=new Vector();
		data1=new Vector<Vector>();
		for (String str:arr1) {
			columnNames1.add(str);
		}
		table1model=new DefaultTableModel(data1,columnNames1);
		/**
		 * �ѱ������Ϊ���ɱ༭��
		 * ��дisCellEditable����
		 * @author yc
		 */
		table1=new JTable(table1model)
		{
			public boolean isCellEditable(int row,int clumn){
				return false;
			}
		};;
		//��2
		columnNames2=new Vector();
		data2=new Vector<Vector>();
		for (String str:arr2) {
			columnNames2.add(str);
		}
		table2model=new DefaultTableModel(data2,columnNames2);
		table2=new JTable(table2model)
		{
			public boolean isCellEditable(int row,int clumn){
				return false;
			}
		};
		//��3
		columnNames3=new Vector();
		data3=new Vector<Vector>();
		for (String str:arr3) {
			columnNames3.add(str);
		}
		table3model=new DefaultTableModel(data3,columnNames3);
		table3=new JTable(table3model)
		{
			public boolean isCellEditable(int row,int clumn){
				return false;
			}
		};
		jp_p1_center_p2_top=new JPanel();
		jp_p1_center_p2_center=new JPanel();
		tabbed_center=new JTabbedPane();
		jp_tabbed_center_p1=new JPanel();
		jp_tabbed_center_p2=new JPanel();
		
		jp_p2_top=new JPanel();
		jp_p2_center=new JPanel();
		jp_p2_top_p1=new JPanel();
		jp_p2_top_p2=new JPanel();
		btn_p2_1=new JButton("�߼���ѯ");
		btn_p2_2=new JButton("����");
		btn_p2_3=new JButton("��ӡ");
		btn_p2_4=new JButton("�˳�");
		tf_p2_1=new JTextField(10);
		tf_p2_2=new JTextField(10);
		tf_p2_3=new JTextField(10);
		btn_p2_5=new JButton("������");
		btn_p2_6=new JButton("��ѯ");
		jp_p2_center_p1=new JPanel();
		jp_p2_center_p2=new JPanel();
		//��4
		columnNames4=new Vector();
		data4=new Vector<Vector>();
		for (String str:arr4) {
			columnNames4.add(str);
		}
		table4model=new DefaultTableModel(data4,columnNames4);
		table4=new JTable(table4model)
		{
			public boolean isCellEditable(int row,int clumn){
				return false;
			}
		};
		//��5
		columnNames5=new Vector();
		data5=new Vector<Vector>();
		for (String str:arr5) {
			columnNames5.add(str);
		}
		table5model=new DefaultTableModel(data5,columnNames5);
		table5=new JTable(table5model)
		{
			public boolean isCellEditable(int row,int clumn){
				return false;
			}
		};
		
		jp_p3_top=new JPanel();
		jp_p3_center=new JPanel();
		jp_p3_top_p1=new JPanel();
		jp_p3_top_p2=new JPanel();
		btn_p3_1=new JButton("����");
		btn_p3_2=new JButton("��ӡ");
		btn_p3_3=new JButton("�˳�");
		tf_p3_1=new JTextField(10);
		tf_p3_2=new JTextField(10);
		tf_p3_3=new JTextField(10);
		btn_p3_4=new JButton("������");
		btn_p3_5=new JButton("��ѯ");
		//��6
		columnNames6=new Vector();
		data6=new Vector<Vector>();
		for (String str:arr6) {
			columnNames6.add(str);
		}
		table6model=new DefaultTableModel(data6,columnNames6);
		table6=new JTable(table6model)
		{
			public boolean isCellEditable(int row,int clumn){
				return false;
			}
		};
		
		jp_p4_top=new JPanel();
		jp_p4_center=new JPanel();
		jp_p4_top_p1=new JPanel();
		jp_p4_top_p2=new JPanel();
		btn_p4_1=new JButton("�鿴����");
		btn_p4_2=new JButton("����");
		btn_p4_3=new JButton("��ӡ");
		btn_p4_4=new JButton("�˳�");
		tf_p4_1=new JTextField(10);
		tf_p4_2=new JTextField(10);
		tf_p4_3=new JTextField(10);
		btn_p4_5=new JButton("������");
		btn_p4_6=new JButton("��ѯ");
		//��7
		columnNames7=new Vector();
		data7=new Vector<Vector>();
		for (String str:arr7) {
			columnNames7.add(str);
		}
		table7model=new DefaultTableModel(data7,columnNames7);
		table7=new JTable(table7model)
		{
			public boolean isCellEditable(int row,int clumn){
				return false;
			}
		};
		/**
		 * ѡ�1
		 * @author yc
		 */
		tabbed.add("�ͻ����е���",jp_p1);
		jp_p1.setLayout(new BorderLayout());
		jp_p1_top.setLayout(new GridLayout(2,1));
		jp_p1_top_1.setLayout(new GridLayout(1,9));
		jp_p1_top_1.setBorder(BorderFactory.createTitledBorder(""));
		jp_p1_top_1.add(btn_p1_1);
		jp_p1_top_1.add(btn_p1_2);
		jp_p1_top_1.add(btn_p1_3);
		//jp_p1_top_1.add(btn_p1_4);
		//jp_p1_top_1.add(btn_p1_5);
		jp_p1_top_1.add(btn_p1_6);
		jp_p1_top_1.add(btn_p1_7);
		jp_p1_top_1.add(btn_p1_8);
		jp_p1_top_1.add(new JLabel());
		jp_p1_top.add(jp_p1_top_1);
		jp_p1_top_2.add(new JLabel("��ѯʱ�䣺"));
		jf_date1.setText(dc1.getStrDate());
		dc1.register(jf_date1);
		jp_p1_top_2.add(jf_date1);
		jp_p1_top_2.add(new JLabel("��"));
		jf_date2.setText(dc2.getStrDate());
		dc2.register(jf_date2);
		jp_p1_top_2.add(jf_date2);
		jp_p1_top_2.add(new JLabel("�ͻ����ƣ�"));
		ret1=new CastUtil().vectorToCustomer(new CastUtil().objectToVector(new CustomDao().getCustomers()).get(0));
		tf_name.setText(ret1.getName());
		tf_name.setEditable(false);
		jp_p1_top_2.add(tf_name);
		jp_p1_top_2.add(btn_look);
		jp_p1_top_2.add(btn_check);
		bg.add(rbtn_date);
		bg.add(rbtn_type);
		jp_p1_top_2.add(rbtn_date);
		jp_p1_top_2.add(rbtn_type);
		jp_p1_top.add(jp_p1_top_2);
		jp_p1.add(jp_p1_top,BorderLayout.NORTH);
		jp_p1_center.setLayout(new GridLayout(2,1));
		jp_p1_center.add(jp_p1_center_p1);
		jp_p1_center_p1.setLayout(new GridLayout(1,1));
		jp_p1_center_p1.add(new JScrollPane(table1));
		jp_p1_center.add(jp_p1_center_p2);
		jp_p1_center_p2.setLayout(new BorderLayout());
		jp_p1_center_p2_top.setLayout(new GridLayout(1,7));
		jp_p1_center_p2_top.add(new JLabel("���ݵ���ϸ��Ϣ��"));
		jp_p1_center_p2_top.add(new JLabel());
		jp_p1_center_p2_top.add(new JLabel());
		jp_p1_center_p2_top.add(new JLabel());
		jp_p1_center_p2_top.add(new JLabel());
		jp_p1_center_p2_top.add(new JLabel());
		jp_p1_center_p2_top.add(new JLabel());
		jp_p1_center_p2.add(jp_p1_center_p2_top,BorderLayout.NORTH);
		jp_tabbed_center_p1.setLayout(new GridLayout(1,1));
		jp_tabbed_center_p1.add(new JScrollPane(table2));
		tabbed_center.add("������Ʒ��Ϣ",jp_tabbed_center_p1);
		jp_tabbed_center_p2.setLayout(new GridLayout(1,1));
		jp_tabbed_center_p2.add(new JScrollPane(table3));
		tabbed_center.add("���ݸ�����Ϣ",jp_tabbed_center_p2);
		jp_p1_center_p2_center.setLayout(new GridLayout(1,1));
		jp_p1_center_p2_center.add(tabbed_center);
		jp_p1_center_p2.add(jp_p1_center_p2_center,BorderLayout.CENTER);
		jp_p1_center.setBorder(BorderFactory.createTitledBorder("���������б�"));
		jp_p1.add(jp_p1_center,BorderLayout.CENTER);
		/**
		 * ѡ�2
		 * @author yc
		 */
		tabbed.add("�ͻ��������",jp_p2);
		jp_p2.setLayout(new BorderLayout());
		jp_p2_top.setLayout(new GridLayout(2,1));
		jp_p2_top_p1.setLayout(new GridLayout(1,8));
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
		jp_p2_top_p2.add(new JLabel("��ѯʱ�䣺"));
		tf_p2_1.setText(dc3.getStrDate());
		dc3.register(tf_p2_1);
		jp_p2_top_p2.add(tf_p2_1);
		jp_p2_top_p2.add(new JLabel("��"));
		tf_p2_2.setText(dc4.getStrDate());
		dc4.register(tf_p2_2);
		jp_p2_top_p2.add(tf_p2_2);
		jp_p2_top_p2.add(new JLabel("�ͻ�����"));
		ret2=new CastUtil().vectorToCustomer(new CastUtil().objectToVector(new CustomDao().getCustomers()).get(0));
		tf_p2_3.setText(ret2.getName());
		tf_p2_3.setEditable(false);
		jp_p2_top_p2.add(tf_p2_3);
		jp_p2_top_p2.add(btn_p2_5);
		jp_p2_top_p2.add(btn_p2_6);
		jp_p2_top.add(jp_p2_top_p2);
		jp_p2.add(jp_p2_top,BorderLayout.NORTH);
		jp_p2_center.setBorder(BorderFactory.createTitledBorder("��Ʒ�����ܻ�"));
		jp_p2_center.setLayout(new GridLayout(2,1));
		jp_p2_center_p1.setLayout(new GridLayout(1,1));
		jp_p2_center_p1.add(new JScrollPane(table4));
		jp_p2_center.add(jp_p2_center_p1);
		jp_p2_center_p2.setLayout(new GridLayout(1,1));
		jp_p2_center_p2.add(new JScrollPane(table5));
		jp_p2_center.add(jp_p2_center_p2);
		jp_p2.add(jp_p2_center,BorderLayout.CENTER);
		/**
		 * ѡ�3
		 * @author yc
		 */
		tabbed.add("�ͻ��˻���Ϣ",jp_p3);
		jp_p3.setLayout(new BorderLayout());
		jp_p3_top.setLayout(new GridLayout(2,1));
		jp_p3_top_p1.setLayout(new GridLayout(1,8));
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
		jp_p3_top_p2.add(new JLabel("ͳ��ʱ�䣺"));
		tf_p3_1.setText(dc5.getStrDate());
		dc5.register(tf_p3_1);
		jp_p3_top_p2.add(tf_p3_1);
		jp_p3_top_p2.add(new JLabel("��"));
		tf_p3_2.setText(dc6.getStrDate());
		dc6.register(tf_p3_2);
		jp_p3_top_p2.add(tf_p3_2);
		jp_p3_top_p2.add(new JLabel("�ͻ�����"));
		ret3=new CastUtil().vectorToCustomer(new CastUtil().objectToVector(new CustomDao().getCustomers()).get(0));
		tf_p3_3.setText(ret3.getName());
		tf_p3_3.setEditable(false);
		jp_p3_top_p2.add(tf_p3_3);
		jp_p3_top_p2.add(btn_p3_4);
		jp_p3_top_p2.add(btn_p3_5);
		jp_p3_top.add(jp_p3_top_p2);
		jp_p3.add(jp_p3_top,BorderLayout.NORTH);
		jp_p3_center.setLayout(new GridLayout(1,1));
		jp_p3_center.add(new JScrollPane(table6));
		jp_p3.add(jp_p3_center,BorderLayout.CENTER);
		/**
		 * ѡ�4
		 * @author yc
		 */
		tabbed.add("�ͻ�������ϸ",jp_p4);
		jp_p4.setLayout(new BorderLayout());
		jp_p4_top.setLayout(new GridLayout(2,1));
		jp_p4_top_p1.setLayout(new GridLayout(1,8));
		jp_p4_top_p1.setBorder(BorderFactory.createTitledBorder(""));
		jp_p4_top_p1.add(btn_p4_1);
		jp_p4_top_p1.add(btn_p4_2);
		jp_p4_top_p1.add(btn_p4_3);
		jp_p4_top_p1.add(btn_p4_4);
		jp_p4_top_p1.add(new JLabel());
		jp_p4_top_p1.add(new JLabel());
		jp_p4_top_p1.add(new JLabel());
		jp_p4_top_p1.add(new JLabel());
		jp_p4_top_p1.add(new JLabel());
		jp_p4_top.add(jp_p4_top_p1);
		jp_p4_top_p2.add(new JLabel("��ѯʱ�䣺"));
		tf_p4_1.setText(dc7.getStrDate());
		dc7.register(tf_p4_1);
		jp_p4_top_p2.add(tf_p4_1);
		jp_p4_top_p2.add(new JLabel("��"));
		tf_p4_2.setText(dc8.getStrDate());
		dc8.register(tf_p4_2);
		jp_p4_top_p2.add(tf_p4_2);
		jp_p4_top_p2.add(new JLabel("�ͻ�����"));
		ret4=new CastUtil().vectorToCustomer(new CastUtil().objectToVector(new CustomDao().getCustomers()).get(0));
		tf_p4_3.setText(ret4.getName());
		tf_p4_3.setEditable(false);
		jp_p4_top_p2.add(tf_p4_3);
		jp_p4_top_p2.add(btn_p4_5);
		jp_p4_top_p2.add(btn_p4_6);
		jp_p4_top.add(jp_p4_top_p2);
		jp_p4.add(jp_p4_top,BorderLayout.NORTH);
		jp_p4_center.setLayout(new GridLayout(1,1));
		jp_p4_center.add(new JScrollPane(table7));
		jp_p4.add(jp_p4_center,BorderLayout.CENTER);
		/**
		 * ��������
		 * @author yc
		 */
		/**
		 * �鿴���ݼ���
		 * @author yc
		 */
		btn_p1_2.addActionListener(new ActionListener() {//ѡ�1
			public void actionPerformed(ActionEvent e) {
				data1=new SellOrdersDao().getSellOrdersInfo();
				table1model=new DefaultTableModel(data1,columnNames1);
				table1.setModel(table1model);
				table1.updateUI();
			}
		});
		btn_p4_1.addActionListener(new ActionListener() {//ѡ�4
			public void actionPerformed(ActionEvent e) {
				data7=new SellOrdersDao().getCustomPayInfo();
				table7model=new DefaultTableModel(data7,columnNames7);
				table7.setModel(table7model);
				table7.updateUI();
			}
		});
		/**
		 * ɾ������
		 * @author yc
		 */
		btn_p1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String str=data1.get(table1.getSelectedRow()).get(2).toString().trim();
					if(new SellOrdersDao().deleteOrder(str)==true) {
						JOptionPane.showMessageDialog(null, "�Ѿ��ɹ�ɾ��������");
						data1=new SellOrdersDao().getSellOrdersInfo();
						table1model=new DefaultTableModel(data1,columnNames1);
						table1.setModel(table1model);
						table1.updateUI();
					}else {
						JOptionPane.showMessageDialog(null, "ɾ������ʧ��!��");
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "��ѡ����Ҫɾ���Ķ�����");
				}
				
				
			}
		});
		/**
		 * ѡ�2�ĸ߼���ѯ
		 * @author yc
		 */
		btn_p2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "��ѡ�����������ѯ��");
			}
		});
		/**
		 * ����
		 * @author yc
		 */
		btn_p1_6.addActionListener(new ActionListener() {//ѡ�1
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser fchooser2=new JFileChooser();
					fchooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fchooser2.showSaveDialog(null);
					String str=fchooser2.getSelectedFile().getAbsolutePath();
					Vector<Vector> da=new Vector<Vector>();
					da.add(columnNames1);
					da.addAll(data1);
					ImportExportHelp.ExportData(da, str);
					JOptionPane.showMessageDialog(null, "�����ɹ���");
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
		btn_p2_2.addActionListener(new ActionListener() {//ѡ�2
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser fchooser2=new JFileChooser();
					fchooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fchooser2.showSaveDialog(null);
					String str=fchooser2.getSelectedFile().getAbsolutePath();
					Vector<Vector> da=new Vector<Vector>();
					da.add(columnNames4);
					da.addAll(data4);
					ImportExportHelp.ExportData(da, str);
					JOptionPane.showMessageDialog(null, "�����ɹ���");
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
		btn_p3_1.addActionListener(new ActionListener() {//ѡ�3
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser fchooser2=new JFileChooser();
					fchooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fchooser2.showSaveDialog(null);
					String str=fchooser2.getSelectedFile().getAbsolutePath();
					Vector<Vector> da=new Vector<Vector>();
					da.add(columnNames6);
					da.addAll(data6);
					ImportExportHelp.ExportData(da, str);
					JOptionPane.showMessageDialog(null, "�����ɹ���");
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
		btn_p4_2.addActionListener(new ActionListener() {//ѡ�4
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser fchooser2=new JFileChooser();
					fchooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fchooser2.showSaveDialog(null);
					String str=fchooser2.getSelectedFile().getAbsolutePath();
					Vector<Vector> da=new Vector<Vector>();
					da.add(columnNames7);
					da.addAll(data7);
					ImportExportHelp.ExportData(da, str);
					JOptionPane.showMessageDialog(null, "�����ɹ���");
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
		 *��ӡ
		 *@author yc
		 */
		btn_p1_7.addActionListener(new ActionListener() {//ѡ�1
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "δ��⵽��ӡ�����������ӡ�豸��");
			}
		});
		btn_p2_3.addActionListener(new ActionListener() {//ѡ�2
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "δ��⵽��ӡ�����������ӡ�豸��");
			}
		});
		btn_p3_2.addActionListener(new ActionListener() {//ѡ�3
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "δ��⵽��ӡ�����������ӡ�豸��");
			}
		});
		btn_p4_3.addActionListener(new ActionListener() {//ѡ�4
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "δ��⵽��ӡ�����������ӡ�豸��");
			}
		});
		/**
		 * 4��ѡ���ͻ����Ƶĵ���¼�
		 * �õ��ͻ��������Ϣ
		 * @author yc
		 * 
		 */
			btn_look.addActionListener(new ActionListener() {//ѡ�һ
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
					//JOptionPane.showMessageDialog(null, "��ѡ������");
				}
				}
			});
			btn_p2_5.addActionListener(new ActionListener() {//ѡ�2
				public void actionPerformed(ActionEvent e) {
					CustomInfoModelWindow cimw = new CustomInfoModelWindow();
					ret2=cimw.ret;
				try {
					if(cimw.table.isRowSelected(cimw.table.getSelectedRow())){
						ret2 = new CastUtil().vectorToCustomer(cimw.retData);
						tf_p2_3.setText(ret2.getName());
					}else{
						tf_p2_3.setText(cimw.ret.getName());
					}
					
				} catch (java.lang.NullPointerException e2) {
					//JOptionPane.showMessageDialog(null, "��ѡ������");
				}
				}
			});
			btn_p3_4.addActionListener(new ActionListener() {//ѡ�3
				public void actionPerformed(ActionEvent e) {
					CustomInfoModelWindow cimw = new CustomInfoModelWindow();
					ret3=cimw.ret;
				try {
					if(cimw.table.isRowSelected(cimw.table.getSelectedRow())){
						ret3 = new CastUtil().vectorToCustomer(cimw.retData);
						tf_p3_3.setText(ret3.getName());
					}else{
						tf_p3_3.setText(cimw.ret.getName());
					}
					
				} catch (java.lang.NullPointerException e2) {;
					//JOptionPane.showMessageDialog(null, "��ѡ������");
				}
				}
			});
			btn_p4_5.addActionListener(new ActionListener() {//ѡ�4
				public void actionPerformed(ActionEvent e) {
					CustomInfoModelWindow cimw = new CustomInfoModelWindow();
					ret4=cimw.ret;
				try {
					if(cimw.table.isRowSelected(cimw.table.getSelectedRow())){
						ret4 = new CastUtil().vectorToCustomer(cimw.retData);
						tf_p4_3.setText(ret4.getName());
					}else{
						tf_p4_3.setText(cimw.ret.getName());
					}
					
				} catch (java.lang.NullPointerException e2) {;
					//JOptionPane.showMessageDialog(null, "��ѡ������");
				}
				}
			});
			
			/**
			 * ��ѯ�����¼�
			 * btn_check
			 * ��ѯȫ���Ŀͻ����е���
			 * һ��Ϊ4��ѡ��Ĳ�ѯ����¼�
			 * ��ȡ���ݿ��е������Ϣ
			 * @author yc
			 */
			btn_check.addActionListener(new ActionListener() {//ѡ�1
				public void actionPerformed(ActionEvent e) {
					String str1=jf_date1.getText().trim();
					String str2=jf_date2.getText().trim();
					data1=new SellOrdersDao().getSellOrdersInfo(str1, str2);
					table1model=new DefaultTableModel(data1,columnNames1);
					table1.setModel(table1model);
					table1.updateUI();
				}
			});
			btn_p2_6.addActionListener(new ActionListener() {//ѡ�2
				public void actionPerformed(ActionEvent e) {
					String str1=tf_p2_1.getText().trim();
					String str2=tf_p2_2.getText().trim();
					data4=new GoodsDao().getGoodsInToAccount(str1,str2);
					table4model=new DefaultTableModel(data4,columnNames4);
					table4.setModel(table4model);
					table4.updateUI();
				}
			});
			btn_p3_5.addActionListener(new ActionListener() {//ѡ�3
				public void actionPerformed(ActionEvent e) {
					data6=new CustomDao().getCustomAccount();
					table6model=new DefaultTableModel(data6,columnNames6);
					table6.setModel(table6model);
					table6.updateUI();
				}
			});
			btn_p4_6.addActionListener(new ActionListener() {//ѡ�4
				public void actionPerformed(ActionEvent e) {
					String str1=tf_p4_1.getText().trim();
					String str2=tf_p4_2.getText().trim();
					data7=new CustomDao().getCustomPayInfo(str1,str2);
					table7model=new DefaultTableModel(data7,columnNames7);
					table7.setModel(table7model);
					table7.updateUI();
				}
			});
			
			/**
			 * �����ı������ݱ仯
			 * �����ı�������ݲ��������Ϣ
			 * @author yc
			 */
			tf_name.getDocument().addDocumentListener(new DocumentListener() {
				public void removeUpdate(DocumentEvent e) {
					data1=new SellOrdersDao().getSellOrdersAccountAllByCustom(ret1);
					table1model=new DefaultTableModel(data1,columnNames1);
					table1.setModel(table1model);
					table1.updateUI();
				}
				public void insertUpdate(DocumentEvent e) {}
				public void changedUpdate(DocumentEvent e) {	
				}
			});
			tf_p2_3.getDocument().addDocumentListener(new DocumentListener() {
				public void removeUpdate(DocumentEvent e) {
					String str1=tf_p4_1.getText().trim();
					String str2=tf_p4_2.getText().trim();
					data4=new  SellOrdersDao().getGoodsInToAccount(ret2);
					table4model=new DefaultTableModel(data4,columnNames4);
					table4.setModel(table4model);
					table4.updateUI();
				}
				public void insertUpdate(DocumentEvent e) {}
				public void changedUpdate(DocumentEvent e) {	
				}
			});
			
			/**
			 * �Ա����������
			 * ���������ѡ������
			 * ���±�չʾ�����Ϣ
			 * @author yc
			 */
			table1.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {}
				public void mousePressed(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {
					if(e.getButton()==1&&e.getClickCount()==2){
						String str =data1.get(table1.getSelectedRow()).get(2).toString();
						data2=new GoodsDao().getGoodsInToAccount(str);
						table2model=new DefaultTableModel(data2,columnNames2);
						table2.setModel(table2model);
						table2.updateUI();
					}
					
				}
			});
			table4.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {}
				public void mousePressed(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {
					if(e.getButton()==1&&e.getClickCount()==2) {
						String str=data4.get(table4.getSelectedRow()).get(0).toString();
						data5=new SellOrdersDao().getHuiZhongInfo(str);
						table5model=new DefaultTableModel(data5, columnNames5);
						table5.setModel(table5model);
						table5.updateUI();
				}
				}
			});
			//�˳�
			btn_p1_8.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				CurrentAccountsModelWindow.this.setVisible(false);
				}
			});
			btn_p2_4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				CurrentAccountsModelWindow.this.setVisible(false);
				}
			});
			btn_p3_3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				CurrentAccountsModelWindow.this.setVisible(false);
				}
			});
			btn_p4_4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				CurrentAccountsModelWindow.this.setVisible(false);
				}
			});
			
		this.add(tabbed);
		this.setTitle("�������񣨿ͻ���");	
		this.setBounds(300, 100, 850, 550);
		this.setModal(true);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new CurrentAccountsModelWindow();
	}
}
