package gui.lxh;
import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultTreeModel;

import bean.Admin;
import bean.Depot;
import bean.Employee;
import bean.PayWay;
import bean.Supplier;
import bean.orders.InOrder;

import service.AdminService;
import service.InService;
import util.CastUtil;
import util.ImportExportHelp;
import util.MyDateChooser;

import dao.DepotsDao;
import dao.EmployeesDao;
import dao.OrderDao;
import dao.PayWaysDao;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class InputGoodsModelWindow extends JDialog{
	OrderDao order_dao=null;
	String dh=null;															//��������
	int money=0;
	MyDateChooser date1=null;	
	DepotsDao  depots_dao=null;												//�ֿ��dao����				
	PayWaysDao pay_ways_dao=null;											//֧����ʽ��dao������
	EmployeesDao employees_dao=null;										//�����ˣ�Ա��dao��				
	//1.ѡ���һ��ѡ���õ��Ķ���
	JTabbedPane tabbed;   													//ѡ�������
	JPanel p_input,p_auditing;												//ѡ��������ѡ�����
	
	JPanel p_input_up,p_input_down;											//��һ��������Ϊ����up����down���
	JPanel p_up1,p_up2,p_down1,p_down2,p_down3;								//up�ְ�����������壬down��������
	JPanel p_down31,p_down32;
	
	JTextField tf_supplier,tf_date,tf_should_pay,tf_reality_pay,tf_number,tf_bz;  
	
	
	/**
	 * commboBox ��ȡ�ֿ����ƣ�֧����ʽ��������																					//����������
	 */	
	ButtonGroup bg;																			//��ȡ�ֿ�
	JComboBox cbox_depot;
	DefaultComboBoxModel cbox_depot_model;
																							//֧����ʽ
	JComboBox cbox_pay;
	DefaultComboBoxModel combox_model;
																							//������
	JComboBox cbox_employees;
	DefaultComboBoxModel combox_employees_model;
	
	JButton btn_select,btn_old_add,btn_new_add,btn_in_out,btn_ok,btn_exit;
	JLabel lable_d;
	String[] strs={"��Ʒ���","��Ʒ����","��λ","���","����","����","�ܽ��"};				//��������
	
	JTable table;																			//������ά���У��Լ�model����
	Vector<Vector> vectorboss;
	Vector vector;											
	DefaultTableModel model;
	
	JPopupMenu pop_in_out;
	JMenuItem mitem_in,mitmem_out;															//ѡ�
	
	//2.ѡ��ڶ���ѡ���õ��Ķ���
	JPanel p_north,p_center,p_n1,p_c1,p_c11,p_c2,p_c21;	
	
	//��pane����-->2��pane,��pane����--->3��pane
	JButton btn_n1_look,btn_n1_delete,btn_n1_auditing,btn_n1_output,btn_n1_stamp,btn_n1_exit,	//����İ�ť
	btn_n2_look,btn_n2_select,btn_c2_add,btn_c2_alter,btn_c2_delete;
	
	JTextField tf_supplier2,tf_select;
	
	JLabel label_no;
	//��������Լ����ǵ���
	JTable table_s_number,table_g_number;
	String[] s_column={"����������","����","����","Ӧ�����","ʵ�����","Ƿ����","�Żݽ��","������","����Ա"};
	String[] g_column={"��Ʒ���","��Ʒ����","��λ","����","����","�ܽ��","��������","�����ڣ��죩","����ͺ�"};
	
	//��������װ�е�vector,model,��
	Vector vector_s, vector_g;
	DefaultTableModel medol_s,medol_g;
	
	/**
	 * Vector v ������
	 */
	Vector v=null;
	public InputGoodsModelWindow() {
		order_dao=new OrderDao();
		date1=MyDateChooser.getInstance("yyyy-MM-dd");
		
		/**
		 * ��ʼ��dao����
		 */
		depots_dao=new DepotsDao();
		pay_ways_dao=new PayWaysDao();
		employees_dao=new EmployeesDao();
		//1.��һѡ��õ��Ķ���
		
		//��ʼ����񣬰ѱ�����һ���������
		vector=new Vector();
		for(String str:strs){												//��strsȫ������ά�������棬Ȼ���ά���з���model������
			vector.add(str);
		}
		vectorboss=new Vector<Vector>();
		model=new DefaultTableModel(vectorboss,vector);
		table=new JTable(model);
		
		//����ѡ�
		tabbed=new JTabbedPane();
		p_input=new JPanel();
		p_auditing=new JPanel();
		
		tabbed.add("�ɹ�����",p_input);										//Ϊ��������������ѡ���壩
		//tabbed.add("�������",p_auditing);
		
		//�������
		p_input_up=new JPanel();
		p_input_down=new JPanel();
		p_up1=new JPanel();
		p_up2=new JPanel();
		p_down1=new JPanel();
		p_down2=new JPanel();
		p_down3=new JPanel();
		p_down31=new JPanel();
		p_down32=new JPanel();
		p_down3.setBorder(BorderFactory.createTitledBorder(""));
		
		
		//�����ı���
		tf_supplier=new JTextField(12);
		tf_supplier.setText("���ұ�ѡ�񹩻���--��");
		tf_supplier.setEditable(false);
		tf_date=new JTextField(8);
		tf_should_pay=new JTextField(8);
		tf_reality_pay=new JTextField(8);
		tf_number=new JTextField(10);
		tf_bz=new JTextField(33);
		
		
		cbox_depot_model=new DefaultComboBoxModel(depots_dao.getDepots());					//����combobox���ڴ�ȡ�ֿ�����
		cbox_depot=new JComboBox(cbox_depot_model);
																							//����combobox���ڴ�ȡ֧����ʽ
		combox_model=new DefaultComboBoxModel(pay_ways_dao.getPayWaysInfo());
		cbox_pay=new JComboBox(combox_model);
																							//����combobox���ڴ�ȡ������
		combox_employees_model=new DefaultComboBoxModel(employees_dao.getEmployees());
		cbox_employees=new JComboBox(combox_employees_model);
		
		//���尴ť
		btn_select=new JButton("����");
		btn_new_add=new JButton("����Ʒ���");
		btn_old_add=new JButton("����Ʒ���");
		btn_in_out=new JButton("���뵼��");
		btn_ok=new JButton("ȷ��");
		btn_exit=new JButton("�˳�");
		
		//�����Ǹ���ɫ��ʾ�ĵ���
	    dh=order_dao.getInOrderId();													//�����Ǹ���ɫ��ʾ�ĵ�����ȫ��dhװ��
		lable_d=new JLabel("���ţ�"+dh);
		lable_d.setForeground(Color.red);
		
		//p_input_up�����Ӹ��ֶ���
		p_up1.add(new JLabel("------------------------------------------------�ɹ�����--------------------------------------------------------------------------"));
		p_up1.add(new JLabel("           "));
		p_up1.add(lable_d);
		
		p_up2.add(new JLabel("��Ӧ�̣�"));	
		p_up2.add(tf_supplier);
		p_up2.add(btn_select);
		p_up2.add(new JLabel("     "));
		p_up2.add(new JLabel("�ջ��ֿ⣺"));
		p_up2.add(cbox_depot);
		p_up2.add(new JLabel("�������ڣ�"));
		tf_date.setText(date1.getStrDate());
		date1.register(tf_date);
		p_up2.add(tf_date);
		p_up2.setBorder(BorderFactory.createTitledBorder(""));
		
		p_input_up.setLayout(new BorderLayout());
		p_input_up.add(p_up1,BorderLayout.NORTH);
		p_input_up.add(p_up2,BorderLayout.CENTER);
		
		
		//p_input_down�����Ӹ��ֶ���
		p_down1.setLayout(new GridLayout(1,3));
		p_down1.add(btn_old_add);
		p_down1.add(btn_new_add);
		p_down1.add(btn_in_out);
		p_down1.setBorder(BorderFactory.createTitledBorder(""));
		
		p_down2.setLayout(new GridLayout());
		p_down2.add(new JScrollPane(table));
		p_down31.add(new JLabel("Ӧ����"));
		p_down31.add(tf_should_pay);
		p_down31.add(new JLabel("ʵ����"));
		p_down31.add(tf_reality_pay);
		
		p_down31.add(new JLabel("���ʽ��"));
		p_down31.add(cbox_pay);
		/*p_down31.add(new JLabel("ԭʼ���ţ�"));
		tf_number.setText("��Ҫ��ͨ��Ա�����ã�");
		tf_number.setEditable(false);
		p_down31.add(tf_number);*/
		
		p_down31.add(new JLabel("�����ˣ�"));
		p_down31.add(cbox_employees);
		p_down32.add(new JLabel("��ı�ע��"));
		p_down32.add(tf_bz);
		p_down32.add(new JLabel("      "));
		p_down32.add(btn_ok);
		p_down32.add(new JLabel("      "));
		p_down32.add(btn_exit);
		
		p_down3.setLayout(new GridLayout(2,1));
		p_down3.add(p_down31);
		p_down3.add(p_down32);
		
		//Ϊp_input_down ���p_down1,p_down2,p_down3 �����
		p_input_down.setLayout(new BorderLayout());
		p_input_down.add(p_down1,BorderLayout.NORTH);
		p_input_down.add(p_down2,BorderLayout.CENTER);
		p_input_down.add(p_down3,BorderLayout.SOUTH);
		
		//�ٰѴ����ӵ�p_inputѡ���
		p_input.setLayout(new BorderLayout());
		p_input.add(p_input_up,BorderLayout.NORTH);
		p_input.add(p_input_down,BorderLayout.CENTER);
		p_input.setBorder(BorderFactory.createTitledBorder(""));
		
		//�������˵������ڵ��뵼����
		pop_in_out=new JPopupMenu();
		mitem_in=new JMenuItem("��excel�е���");
		mitmem_out=new JMenuItem("��excel�е���");
		pop_in_out.add(mitem_in);
		pop_in_out.add(mitmem_out);
		
		
		//2.ѡ��ڶ���ѡ���õ��Ķ���
		//����,��ѡ���Ϊ������壬��һ���������2�����ڱ������ڶ��������������м�
		p_north=new JPanel();
		p_center=new JPanel();
		p_n1=new JPanel();
		p_c1=new JPanel();
		p_c11=new JPanel();
		p_c2=new JPanel();
		p_c21=new JPanel();
		p_north.setBorder(BorderFactory.createTitledBorder(""));
		p_center.setBorder(BorderFactory.createTitledBorder(""));
		p_n1.setBorder(BorderFactory.createTitledBorder(""));
		p_c11.setBorder(BorderFactory.createTitledBorder(""));
		p_c2.setBorder(BorderFactory.createTitledBorder(""));
		p_c21.setBorder(BorderFactory.createTitledBorder(""));
		
		//����İ�ťŶ
		btn_n1_look=new JButton("�鿴����");
		btn_n1_delete=new JButton("ɾ������");
		btn_n1_auditing=new JButton("�������");
		btn_n1_output=new JButton("��   ��");
		btn_n1_stamp=new JButton("��    ӡ");
		btn_n1_exit=new JButton("��    ��");
		
		btn_n2_look=new JButton("�鿴");
		btn_n2_select=new JButton("��ѯ");
		
		btn_c2_add=new JButton("������Ʒ");
		btn_c2_alter=new JButton("�޸���Ʒ");
		btn_c2_delete=new JButton("ɾ����Ʒ");
		
		//��ʼ���ı���
		tf_supplier2=new JTextField(8);
		tf_select=new JTextField(12);
		
		label_no=new JLabel("��");
		label_no.setForeground(Color.blue);
		
		//��ʼ�����
		vector_s=new Vector();
		vector_g=new Vector();
		for(String str:s_column){
			vector_s.add(str);
		}
		for(String str:g_column){
			vector_g.add(str);
		}
		medol_s=new DefaultTableModel(null,vector_s);
		medol_g=new DefaultTableModel(null,vector_g);
		table_s_number=new JTable(medol_s);
		table_g_number=new JTable(medol_g);
		
		//��ӿؼ���ѡ���,���ڵ��⣬���ϵ��·����
		p_n1.add(btn_n1_look);
		p_n1.add(btn_n1_delete);
		p_n1.add(btn_n1_auditing);
		p_n1.add(btn_n1_output);
		p_n1.add(btn_n1_stamp);
		p_n1.add(btn_n1_exit);
		
		p_north.setLayout(new BorderLayout());									//�Ѱ�����6����ť��������p_north�����
		p_north.add(p_n1,BorderLayout.WEST);
		//���ѡ����ϲ����Ѿ��㶨
		
		//p_c11.setLayout(new GridLayout(1, 7));
		p_c11.add(new JLabel("------------"));//��p_c11��Ӹ��еĶ���
		p_c11.add(new JLabel("�����̣�"));
		p_c11.add(tf_supplier2);
		p_c11.add(btn_n2_look);
		p_c11.add(new JLabel("-------------------------"));
		p_c11.add(new JLabel("���뵥�������Ϣ���в�ѯ��"));
		p_c11.add(tf_select);
		p_c11.add(btn_n2_select);
														
		
		p_c21.setLayout(new GridLayout(1,8));								//��p_c2����Ӷ���
		p_c21.add(new JLabel("�����̣�"));
		p_c21.add(label_no);
		p_c21.add(new JLabel("  "));
		p_c21.add(new JLabel("  "));
		p_c21.add(new JLabel("  "));
		p_c21.add(btn_c2_add);
		p_c21.add(btn_c2_alter);
		p_c21.add(btn_c2_delete);
		
		p_c1.setLayout(new BorderLayout());									//�м������ϲ���
		p_c1.add(p_c11,BorderLayout.NORTH);
		p_c1.add(new JScrollPane(table_s_number),BorderLayout.CENTER);
		
		p_c2.setLayout(new BorderLayout());									//�м������²���
		p_c2.add(p_c21,BorderLayout.NORTH);
		p_c2.add(new JScrollPane(table_g_number),BorderLayout.CENTER);
		
		//��ʼ��p_center ��Ӷ���
		p_center.setLayout(new GridLayout(2,1));
		p_center.add(p_c1);
		p_center.add(p_c2);
		
		//�ڰѱ����������ӵ�ѡ���
		p_auditing.setLayout(new BorderLayout());
		p_auditing.add(p_north,BorderLayout.NORTH);
		p_auditing.add(p_center,BorderLayout.CENTER);
		
		
		this.add(tabbed);
		this.setBounds(0, 0, 800, 600);
		this.setLocationRelativeTo(null);									//������ʾ
		
		//����Ʒ���
		btn_old_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new OldGoodsADDModelWindow(InputGoodsModelWindow.this);
			}
		});
		//����Ʒ���
		btn_new_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddGoodsModleWindow(InputGoodsModelWindow.this);
			}
		});
		//���Ҽ���
		btn_select.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				SupplierInfoModelWindow sp=new SupplierInfoModelWindow();
				 v=sp.retData;
				try {
					tf_supplier.setText(v.get(1).toString());
				} catch (Exception e2) {
					
				}
				
				
			}
		});
		/**
		 * ����
		 */
		mitem_in.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImportExportHelp a = new ImportExportHelp();
				//ȡa�����data����
				Vector<Vector> corectData = new Vector<>();
				for(int i=0;i<a.data.size();i++)
					if(a.data.get(i).lastElement().toString().trim().equals("�ɵ���"))
						corectData.add(a.data.get(i));
					table.setModel(new DefaultTableModel(corectData, vector));
				//����Ӧ�����
				double wantMoney = 0;
				for(int i=0;i<corectData.size();i++) {
					wantMoney += Double.parseDouble(corectData.get(i).lastElement().toString());
				}	
				tf_should_pay.setText(wantMoney+"");
				tf_reality_pay.setText(wantMoney+"");
			}
		});
		/**
		 * ����
		 */
		mitmem_out.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
				JFileChooser out_file=new JFileChooser();
				out_file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				out_file.showSaveDialog(null);
				String str=out_file.getSelectedFile().getAbsolutePath();
				Vector<Vector> da=new Vector<Vector>();
				da.add(vector);
				da.addAll(vectorboss);
				
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
		//���뵼���˵�
		btn_in_out.addMouseListener(new mouseClicked());
		/*
		 * ȷ���¼�
		 * ���ɶ���
		 */
		btn_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String date=tf_date.getText().trim();
					Depot depot=(Depot) cbox_depot.getSelectedItem();
					Double wantMoney=Double.parseDouble(tf_should_pay.getText().trim());
					Double payMoney=Double.parseDouble(tf_reality_pay.getText().trim());
					
					Employee agent=(Employee) cbox_employees.getSelectedItem();
					Admin operator=AdminService.admin;
					
					String bz=tf_bz.getText().trim();
					PayWay payWay=(PayWay) cbox_pay.getSelectedItem();
					Supplier supplier=new CastUtil().VectorToSupplier(v);
					InOrder in_order=new InOrder(dh, date, depot, wantMoney, payMoney, agent, operator, bz, payWay, supplier);
					new InService().addOrder(in_order,new CastUtil().VerctorToHashSet(vectorboss));
					JOptionPane.showMessageDialog(null, "�ύ�ɹ�!");
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "�ύʧ��!");
				}
			}
		});
		
		/**
		 * btn_exit�˳�
		 */
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InputGoodsModelWindow.this.setVisible(false);
			}
		});
		this.setModal(true);												//����Ϊģʽ����
		this.setVisible(true);
	}
	
	//���뵼���˵�
	public class mouseClicked extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			if(e.getButton()==1){
				pop_in_out.show(btn_in_out, e.getX(), e.getY());
			}
			
		}
	}
	public static void main(String[] args) {
		new AdminService().Login("admin", "123");
		new InputGoodsModelWindow();
	}
}
