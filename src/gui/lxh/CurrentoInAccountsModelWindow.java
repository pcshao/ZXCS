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

//�������񣨿ͻ���
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
	String[] arr1={"����������","����","����","����","Ӧ�����","ʵ�����","������","����Ա"};
	String[] arr2={"��Ʒ���","��Ʒ����","��λ","����","���","����","�ܽ��"};
	String[] arr3={"����������","����","����","����","������","������","����Ա","��ע"};
	
	
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
	//String[] arr4={"��Ʒ���","��Ʒ����","��λ","����","�ܽ��","����ͺ�","��ɫ","��������","��ע"};
	//String[] arr5={"���ݺ�","��������","��Ʒ���","��Ʒ����","����","����","�ܽ��","��λ","����ͺ�","��ɫ","�ֿ�","������","����������"};
	
	JPanel jp_p3_top,jp_p3_center;
	JButton btn_p3_1,btn_p3_2,btn_p3_3,btn_p3_4,btn_p3_5;
	JPanel jp_p3_top_p1,jp_p3_top_p2;
	JTextField tf_p3_1,tf_p3_2,tf_p3_3;
	Vector columnNames6;
	Vector<Vector> data6;
	DefaultTableModel table6model;
	JTable table6;
	//String[] arr6={"��Ʒ���","��Ʒ����","��λ","��������","�ɱ���","�����ܽ��","����","ë���ʣ�%��","����ͺ�","��ɫ","��������","��ע"};
	
	JPanel jp_p4_top,jp_p4_center;
	JButton btn_p4_2,btn_p4_3,btn_p4_4,btn_p4_5,btn_p4_6;
	JPanel jp_p4_top_p1,jp_p4_top_p2;
	JTextField tf_p4_1,tf_p4_2,tf_p4_3;
	Vector columnNames7;
	Vector<Vector> data1,data7;
	DefaultTableModel table7model;
	JTable table7;
	String[] arr7={"����������","��Ʒ���۶�","��Ʒ�˻���","�ҷ�Ӧ�����","�ҷ�ʵ�����","δ�����"};
	
	//jp_p5 ��Ķ���
		JPanel p5_1,p51_11,p51_12,p5_2;
		JButton btn_select,btn_in,btn_out,btn_exit,btn_fdj,btn_looks;
		JTextField tf_date1,tf_date2,tf_supplier;
		String[] arr8={"���ݱ��","��������","���ʽ","������","����������","������","����Ա","��ע"};
		Vector columnNames8;
		Vector<Vector> data8;
		DefaultTableModel table8model;
		JTable table8;
		
		MyDateChooser date1,date2,date3,date4,date5,date6,date7,date8,date9,date10;			//��������ѡ����
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
		jp_p1=new JPanel();//���������е���
		/*jp_p2=new JPanel();//�����̹������
		jp_p3=new JPanel();//�������������
		jp_p4=new JPanel();//����������
		jp_p5=new JPanel();//�ҷ�������ϸ
*/		jp_p1_top=new JPanel();
		jp_p1_center=new JPanel();
		//btn_p1_1=new JButton("�ҷ�����");
		//btn_p1_2=new JButton("�鿴����");
		btn_delete=new JButton("ɾ������");
		//btn_p1_4=new JButton("�����˻�");
		//btn_p1_5=new JButton("���ݹ���");
		btn_output=new JButton(" ��    �� ");
		btn_p1_7=new JButton(" ��    ӡ ");
		btn_p1_8=new JButton(" ��    �� ");
		jp_p1_top_1=new JPanel();
		jp_p1_top_2=new JPanel();
		jf_date1=new JTextField(10);
		jf_date2=new JTextField(10);
		tf_name=new JTextField(8);
		btn_look=new JButton("��   ��");
		btn_check=new JButton("�� ѯ");
		bg=new ButtonGroup();
		rbtn_date=new JButton("����������(����)");
		//rbtn_type=new JRadioButton("����������");
		jp_p1_center_p1=new JPanel();
		jp_p1_center_p2=new JPanel();
		//��1
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
		//��2
		columnNames2=new Vector();
		for (String str:arr2) {
			columnNames2.add(str);
		}
		table2model=new DefaultTableModel(null,columnNames2);
		table2=new JTable(table2model);
		//��3
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
		btn_p2_1=new JButton("�߼���ѯ");
		btn_p2_2=new JButton(" ��    �� ");
		btn_p2_3=new JButton(" ��    ӡ ");
		btn_p2_4=new JButton(" ��    �� ");
		tf_p2_1=new JTextField(10);
		tf_p2_2=new JTextField(10);
		tf_p2_3=new JTextField(10);
		tf_shop1=new JTextField(10);
		btn_p2_5=new JButton("������");
		btn_p2_6=new JButton("��  ѯ");
		jp_p2_center_p1=new JPanel();
		jp_p2_center_p2=new JPanel();
		/*//��4
		table4=new JTable(table4model);
		columnNames4=new Vector();
		data4=new Vector<Vector>();
		for (String str:arr4) {
			columnNames4.add(str);
		}
		table4model=new DefaultTableModel(data4,columnNames4);
		table4=new JTable(table4model);
		//��5
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
		btn_p3_1=new JButton(" ��    �� ");
		btn_p3_2=new JButton(" ��    ӡ ");
		btn_p3_3=new JButton(" ��    �� ");
		tf_p3_1=new JTextField(10);
		tf_p3_2=new JTextField(10);
		tf_p3_3=new JTextField(10);
		tf_shop2=new JTextField(10);
		btn_p3_4=new JButton("������");
		btn_p3_5=new JButton("��ѯ");
		/*//��6
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
		btn_p4_2=new JButton(" ��    �� ");
		btn_p4_3=new JButton(" ��    ӡ ");
		btn_p4_4=new JButton(" ��    �� ");
		tf_p4_1=new JTextField(10);
		tf_p4_2=new JTextField(10);
		tf_p4_3=new JTextField(10);
		btn_p4_5=new JButton("������");
		btn_p4_6=new JButton("�� ѯ");
		/*//��7
		table7=new JTable(table7model);
		columnNames7=new Vector();
		data7=new Vector<Vector>();
		for (String str:arr7) {
			columnNames7.add(str);
		}
		table7model=new DefaultTableModel(data7,columnNames7);
		table7=new JTable(table7model);
		
		//��8
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
		 * ѡ�1
		 * @author lxh
		 */
		tabbed.add("���������е���",jp_p1);
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
		jp_p1_top_2.add(new JLabel("��ѯʱ�䣺"));
		jf_date1.setText(date1.getStrDate());
		date1.register(jf_date1);
		jp_p1_top_2.add(jf_date1);
		jp_p1_top_2.add(new JLabel("��"));
		jf_date2.setText(date2.getStrDate());
		date2.register(jf_date2);
		jp_p1_top_2.add(jf_date2);
		jp_p1_top_2.add(btn_look);
		jp_p1_top_2.add(new JLabel("���������ƣ�"));
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
		 * @author lxh
		 *//*
		tabbed.add("�����̹������",jp_p2);
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
		jp_p2_top_p2.add(new JLabel("��ѯʱ�䣺"));
		tf_p2_1.setText(date3.getStrDate());
		date3.register(tf_p2_1);
		jp_p2_top_p2.add(tf_p2_1);
		jp_p2_top_p2.add(new JLabel("��"));
		tf_p2_2.setText(date4.getStrDate());
		date4.register(tf_p2_2);
		jp_p2_top_p2.add(tf_p2_2);
		jp_p2_top_p2.add(new JLabel("���������ƣ�"));
		jp_p2_top_p2.add(tf_shop1);
		jp_p2_top_p2.add(btn_p2_5);
		jp_p2_top_p2.add(btn_p2_6);
		jp_p2_top.add(jp_p2_top_p2);
		jp_p2.add(jp_p2_top,BorderLayout.NORTH);

		jp_p2_center.setLayout(new GridLayout(2,1));
		jp_p2_center_p1.setBorder(BorderFactory.createTitledBorder("�ܻ��"));
		jp_p2_center_p1.setLayout(new GridLayout(1,1));
		jp_p2_center_p1.add(new JScrollPane(table4));
		jp_p2_center.add(jp_p2_center_p1);
		jp_p2_center_p2.setBorder(BorderFactory.createTitledBorder("��ˮ��"));
		jp_p2_center_p2.setLayout(new GridLayout(1,1));
		jp_p2_center_p2.add(new JScrollPane(table5));
		jp_p2_center.add(jp_p2_center_p2);
		jp_p2.add(jp_p2_center,BorderLayout.CENTER);
		*//**
		 * ѡ�3
		 * @author yc
		 *//*
		tabbed.add("��������Ʒ���������",jp_p3);
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
		jp_p3_top_p2.add(new JLabel("ͳ��ʱ�䣺"));
		tf_p3_1.setText(date5.getStrDate());
		date5.register(tf_p3_1);
		jp_p3_top_p2.add(tf_p3_1);
		jp_p3_top_p2.add(new JLabel("��"));
		tf_p3_2.setText(date6.getStrDate());
		date6.register(tf_p3_2);
		jp_p3_top_p2.add(tf_p3_2);
		jp_p3_top_p2.add(new JLabel("���������ƣ�"));
		jp_p3_top_p2.add(tf_shop2);
		jp_p3_top_p2.add(btn_p3_4);
		jp_p3_top_p2.add(btn_p3_5);
		jp_p3_top.add(jp_p3_top_p2);
		jp_p3.add(jp_p3_top,BorderLayout.NORTH);
		jp_p3_center.setLayout(new GridLayout(1,1));
		jp_p3_center.add(new JScrollPane(table6));
		jp_p3.add(jp_p3_center,BorderLayout.CENTER);
		*//**
		 * ѡ�4
		 * @author yc
		 *//*
		tabbed.add("����������",jp_p4);
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
		jp_p4_top_p2.add(new JLabel("���������ƣ�"));
		jp_p4_top_p2.add(tf_p4_2);
		jp_p4_top_p2.add(btn_p4_5);
		jp_p4_top_p2.add(btn_p4_6);
		jp_p4_top.add(jp_p4_top_p2);
		jp_p4.add(jp_p4_top,BorderLayout.NORTH);
		jp_p4_center.setLayout(new GridLayout(1,1));
		jp_p4_center.add(new JScrollPane(table7));
		jp_p4.add(jp_p4_center,BorderLayout.CENTER);
		
		
		 * *lxh  
		 * �ҷ�������ϸ
		 	
		p5_1=new JPanel();
		p51_11=new JPanel();
		p51_12=new JPanel();
		p5_2=new JPanel();
		btn_select=new JButton("�鿴����");
		btn_in=new JButton(" ��     �� ");
		btn_out=new JButton(" ��     ӡ ");
		btn_exit=new JButton(" ��     �� ");
		btn_fdj=new JButton(" ��     ��");
		btn_looks=new JButton("ȷ����ѯ");
		
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
		p51_12.add(new JLabel("��ѯʱ�䣺"));
		tf_date1.setText(date7.getStrDate());
		date7.register(tf_date1);
		p51_12.add(tf_date1);
		p51_12.add(new JLabel("��"));
		tf_date2.setText(date8.getStrDate());
		date8.register(tf_date2);
		p51_12.add(tf_date2);
		p51_12.add(new JLabel("----------------------"));
		p51_12.add(new JLabel("���������ƣ�"));
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
		tabbed.add("�ҷ�������ϸ",jp_p5);*/

		//����
		btn_p1_8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CurrentoInAccountsModelWindow.this.setVisible(false);
			}
		});
		/**
		 * ɾ������
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
		 * ����
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
					JOptionPane.showMessageDialog(null, "�����ɹ�");
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
					JOptionPane.showMessageDialog(null, "�����ݾ��棡");
				}
				
			}
		});
		/**
		 * ��ӡ
		 */
		btn_p1_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(null, "�����ӡ�豸�Ƿ����ӳɹ���");
				
			}
		});
		/**
		 * ��ѯָ�����ڵĶ���
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
		 * �����������Ʋ�ѯ
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
		 * �������������
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
		this.setTitle("�������񣨹����̣�");	
		this.setBounds(300, 100, 1000, 550);
		this.setModal(true);
		this.setVisible(true);
	}/***
	 * 
	 * ���1�ĵ���¼�
	 * ��ʹ���2��3�����仯
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
				 * ��2��
				 */
				Vector vector_boss=inorder_dao.getGoodsInfo(isSelectTable1().get(2)+"");
				table2model=new DefaultTableModel(vector_boss,columnNames2);
				table2.setModel(table2model);
				table2.updateUI();
				/**
				 * ������
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
	 * ���ر��1ѡ�е���
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
