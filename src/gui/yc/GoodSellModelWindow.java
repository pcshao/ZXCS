package gui.yc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import bean.Admin;
import bean.Customer;
import bean.Depot;
import bean.Employee;
import bean.PayWay;
import bean.orders.SellOrder;

import dao.CustomDao;
import dao.DepotsDao;
import dao.EmployeesDao;
import dao.GoodsDao;
import dao.OrderDao;
import dao.PayWaysDao;
import dao.SellOrdersDao;

import service.AdminService;
import service.SellService;
import util.CastUtil;
import util.ImportExportHelp;
import util.MyDateChooser;
//��Ʒ����
public class GoodSellModelWindow extends JDialog{
	MyDateChooser dc;
	JTabbedPane tabbed;
	JPanel jp_goodsell;							
	JPanel jp_goodsell_top,jp_goodsell_center,jp_goodsell_bottom;
	JPanel jp_goodsell_top_top,jp_goodsell_top_center;			
	JPanel jp_goodsell_center_top,jp_goodsell_center_center;						
	JPanel jp_goodsell_bottom1,jp_goodsell_bottom2;									
	JLabel lbl_ordernumber,lbl_goodsell;
	JTextField tf_name,tf_selldate;
	DefaultComboBoxModel aModel1;
	JComboBox cobx_depots;
	JTextField tf_wantmoney,tf_paymoney,tf_order,tf_bz;
	Vector aModel1Vector2,aModel1Vector3;
	DefaultComboBoxModel aModel2,aModel3;
	JComboBox cobx_agent,cobx_pay;
	JButton btn_seek,btn_addgoods,btn_inout,btn_ok,btn_exit;
	Vector columnNames;
	Vector<Vector> data;
	DefaultTableModel tablemodel;
	JTable table;
	
	JPanel jp_sellorderscheck;					
	JPanel jp_soc_top,jp_soc_center;			
	JPanel jp_soc_center_p1,jp_soc_center_p2;	
	JPanel jp_p1_top,jp_p1_center;
	JPanel jp_p2_top,jp_p2_center;
	JButton btn_look,btn_delete,btn_check,btn_out,btn_stamp,btn_tui;
	JTextField tf_soc_name,tf_soc_check;
	JButton btn_soc_check1,btn_soc_check2;
	Vector<Vector> date1,date2;
	Vector columnNames1,columnNames2;
	DefaultTableModel table1model,table2model;
	JTable table1,table2;
	JButton btn_add,btn_alter,btn_del;//
	String[] arr1={"�ͻ�����","����","����","Ӧ�����","ʵ�����","�Żݽ��","Ƿ����","������","֧����ʽ","����Ա","��ע  "};
	String[] arr2={"��Ʒ���","��Ʒ����","��λ","����","����","�ܽ��","����ͺ�","��ɫ"};
	//���뵼��
	JPopupMenu pop_in_out;
	JMenuItem mitem_in,mitmem_out;
	
	//���ɶ�����������
	Customer ret1,ret2;				//�ͻ�		
	Vector aModel1Vector;		//�ֿ�
	String iddan;					//������		
								//�����嵥
	Vector<Vector> customdata;
	public GoodSellModelWindow(){
		dc=MyDateChooser.getInstance("yyyy-MM-dd");
		tabbed=new JTabbedPane();		//ѡ�
		jp_goodsell=new JPanel();		//��Ʒ�������
		jp_sellorderscheck=new JPanel();//����������
		tabbed.add("��Ʒ����",jp_goodsell);
//		tabbed.add("���۵����",jp_sellorderscheck);
		jp_sellorderscheck.setLayout(new BorderLayout());	//�����������Ϊ�߽粼��
		jp_goodsell.setLayout(new BorderLayout());			//�����������Ϊ�߽粼��
		
		/**
		 * ��Ʒ����ѡ�
		 * @author yc
		 * */		
		//�������
		jp_goodsell_top=new JPanel();
		jp_goodsell_top_top=new JPanel();
		lbl_goodsell=new JLabel("��Ʒ����");
		iddan=new SellOrdersDao().getSellOrderId();
		lbl_ordernumber=new JLabel("���ţ�"+iddan);
		jp_goodsell_top_center=new JPanel();
		tf_name=new JTextField(15);
		btn_seek=new JButton("����");
		aModel1Vector=new DepotsDao().getDepots();
		aModel1=new DefaultComboBoxModel(aModel1Vector);
		cobx_depots=new JComboBox(aModel1);
		tf_selldate=new JTextField(15);
		jp_goodsell.add(jp_goodsell_top,BorderLayout.NORTH);	//���ö�������λ��
		jp_goodsell_top.setLayout(new BorderLayout());			//���ö������Ĳ���
		lbl_goodsell.setFont(new Font("΢���ź�",Font.BOLD,17));//������Ʒ���۵�����
		jp_goodsell_top_top.setLayout(new GridLayout(1,5));
		jp_goodsell_top_top.add(new JLabel());
		jp_goodsell_top_top.add(new JLabel());
		jp_goodsell_top_top.add(lbl_goodsell);
		lbl_ordernumber.setForeground(Color.red);
		jp_goodsell_top_top.add(lbl_ordernumber);
		jp_goodsell_top_top.add(new JLabel()); 	
		jp_goodsell_top_center.setBorder(BorderFactory.createTitledBorder(""));
		jp_goodsell_top_center.add(new JLabel("�ͻ����ƣ�"));
		ret1=new CastUtil().vectorToCustomer(new CastUtil().objectToVector(new CustomDao().getCustomers()).get(0));
		tf_name.setText(ret1.getName());
		tf_name.setEditable(false);
		jp_goodsell_top_center.add(tf_name);
		jp_goodsell_top_center.add(btn_seek);
		jp_goodsell_top_center.add(new JLabel("�����ֿ�"));
		jp_goodsell_top_center.add(new JScrollPane(cobx_depots));
		jp_goodsell_top_center.add(new JLabel("��������"));
		dc.register(tf_selldate);
		tf_selldate.setText(dc.getStrDate());
		jp_goodsell_top_center.add(tf_selldate);
		
		jp_goodsell_top.add(jp_goodsell_top_top,BorderLayout.NORTH);
		jp_goodsell_top.add(jp_goodsell_top_center,BorderLayout.CENTER);
		//�в����
		jp_goodsell_center=new JPanel();
		btn_addgoods=new JButton("�����Ʒ");
		//�������˵������ڵ��뵼����
		pop_in_out=new JPopupMenu();
		mitem_in=new JMenuItem("��excel�е���");
		mitmem_out=new JMenuItem("��excel�е���");
		pop_in_out.add(mitem_in);
		pop_in_out.add(mitmem_out);
		
		btn_inout=new JButton("���뵼��");
		jp_goodsell_center_top=new JPanel();
		jp_goodsell_center_center=new JPanel();
		jp_goodsell_center.setLayout(new BorderLayout());
		jp_goodsell_center_top.setLayout(new GridLayout(1,6));
		columnNames=new Vector();
		columnNames.add("��Ʒ���");
		columnNames.add("��Ʒ����");
		columnNames.add("��λ");
		columnNames.add("����ͺ�");
		columnNames.add("����");
		columnNames.add("����");
		columnNames.add("�ܽ��");
		data=new Vector<Vector>();
		tablemodel=new DefaultTableModel(data, columnNames);
		table=new JTable(tablemodel)
		{
			public boolean isCellEditable(int row,int clumn){
				return false;
			}
		};
		jp_goodsell_center_top.setBorder(BorderFactory.createTitledBorder(""));
		jp_goodsell_center_top.add(btn_addgoods);
		jp_goodsell_center_top.add(btn_inout);
		jp_goodsell_center_top.add(new JLabel());
		jp_goodsell_center_top.add(new JLabel());
		jp_goodsell_center_top.add(new JLabel());
		jp_goodsell_center_top.add(new JLabel());
		jp_goodsell_center.add(jp_goodsell_center_top,BorderLayout.NORTH);
		jp_goodsell_center_center.setLayout(new GridLayout(1,1));
		jp_goodsell_center_center.add(new JScrollPane(table));
		jp_goodsell_center.add(jp_goodsell_center_center);
		jp_goodsell.add(jp_goodsell_center,BorderLayout.CENTER);
		
		//�ײ����----------------------------------------------------
		jp_goodsell_bottom=new JPanel();
		jp_goodsell_bottom.setLayout(new GridLayout(2,1));
		jp_goodsell_bottom1=new JPanel();
		tf_wantmoney=new JTextField(6);
		tf_paymoney=new JTextField(6);
		aModel1Vector2=new EmployeesDao().getEmployees();
		aModel2=new DefaultComboBoxModel(aModel1Vector2);
		cobx_agent=new JComboBox(aModel2);
		aModel1Vector3=new PayWaysDao().getPayWaysInfo();
		aModel3=new DefaultComboBoxModel(aModel1Vector3);
		cobx_pay=new JComboBox(aModel3);
		tf_order=new JTextField(10);
		jp_goodsell_bottom2=new JPanel();
		tf_bz=new JTextField(48);
		btn_ok=new JButton("ȷ��");
		btn_exit=new JButton("�˳�");
		jp_goodsell_bottom1.add(new JLabel("Ӧ�ս�"));
		tf_wantmoney.setText("0.00");
		jp_goodsell_bottom1.add(tf_wantmoney);
		jp_goodsell_bottom1.add(new JLabel("ʵ�ս�"));
		tf_paymoney.setText("0.00");
		jp_goodsell_bottom1.add(tf_paymoney);
		jp_goodsell_bottom1.add(new JLabel("֧����ʽ��"));
		jp_goodsell_bottom1.add(cobx_pay);
		jp_goodsell_bottom1.add(new JLabel("�����ˣ�"));
		jp_goodsell_bottom1.add(new JScrollPane(cobx_agent));
		jp_goodsell_bottom1.add(new JLabel("ԭʼ���ţ�"));
		
		jp_goodsell_bottom1.add(tf_order);
		jp_goodsell_bottom.add(jp_goodsell_bottom1);
		jp_goodsell_bottom2.add(new JLabel("��ע��"));
		jp_goodsell_bottom2.add(tf_bz);
		jp_goodsell_bottom2.add(btn_ok);
		jp_goodsell_bottom2.add(btn_exit);
		jp_goodsell_bottom.add(jp_goodsell_bottom2);
		jp_goodsell.add(jp_goodsell_bottom,BorderLayout.SOUTH);
		/**
		 *���۵����ѡ�
		 *@author yc 
		 */
		//�������------------------------------------------------------
		jp_soc_top=new JPanel();
		btn_look=new JButton("�鿴����");
		btn_delete=new JButton("ɾ������");
		btn_check=new JButton("�������");
		btn_out=new JButton("����");
		btn_stamp=new JButton("��ӡ");
		btn_tui=new JButton("�˳�");
		jp_soc_top.setLayout(new GridLayout(1,9));
		jp_soc_top.setBorder(BorderFactory.createTitledBorder(""));
		jp_soc_top.add(btn_look);
		jp_soc_top.add(btn_delete);
		jp_soc_top.add(btn_look);
		jp_soc_top.add(btn_check);
		jp_soc_top.add(btn_stamp);
		jp_soc_top.add(btn_tui);
		jp_soc_top.add(new JLabel());
		jp_soc_top.add(new JLabel());
		jp_soc_top.add(new JLabel());
		jp_sellorderscheck.add(jp_soc_top,BorderLayout.NORTH);
		
		//�в����-----------------------------------------------------
		jp_soc_center=new JPanel();
		jp_soc_center_p1=new JPanel();
		jp_soc_center_p2=new JPanel();
		jp_p1_top=new JPanel();
		jp_p1_center=new JPanel();
		tf_soc_name=new JTextField(15);
		btn_soc_check1=new JButton("����");
		tf_soc_check=new JTextField(15);
		btn_soc_check2=new JButton("��ѯ");
		columnNames1=new Vector();
		columnNames2=new Vector();
		for (String str:arr1) {
			columnNames1.add(str);
		}
		for (int i = 0; i < arr2.length; i++) {
			columnNames2.add(arr2[i]);
		}
		table1model=new DefaultTableModel(date1,columnNames1);
		table2model=new DefaultTableModel(date2,columnNames2);
		table1=new JTable(table1model);
		table2=new JTable(table2model);
		jp_p2_top=new JPanel();
		jp_p2_center=new JPanel();
		btn_add=new JButton("������Ʒ");
		btn_alter=new JButton("�޸���Ʒ");
		btn_del=new JButton("ɾ����Ʒ");
		jp_soc_center.setLayout(new GridLayout(2,1));
		jp_p1_top.add(new JLabel("�ͻ����ƣ�"));
		ret2=new CastUtil().vectorToCustomer(new CastUtil().objectToVector(new CustomDao().getCustomers()).get(0));
		tf_soc_name.setText(ret2.getName());
		jp_p1_top.add(tf_soc_name);
		jp_p1_top.add(btn_soc_check1);
		jp_p1_top.add(new JLabel("    ���뵥�������Ϣ���в�ѯ��"));
		jp_p1_top.add(tf_soc_check);
		jp_p1_top.add(btn_soc_check2);
		jp_soc_center_p1.setLayout(new BorderLayout());
		jp_soc_center_p1.add(jp_p1_top,BorderLayout.NORTH);
		jp_p1_center.setLayout(new GridLayout(1,1));
		jp_p1_center.add(new JScrollPane(table1));
		jp_soc_center_p1.add(jp_p1_center,BorderLayout.CENTER);
		jp_soc_center.add(jp_soc_center_p1);
		
		jp_p2_top.setLayout(new GridLayout(1,9));
		jp_p2_top.setBorder(BorderFactory.createTitledBorder(""));
		jp_p2_top.add(new JLabel("�����̣�"));
		jp_p2_top.add(new JLabel("��ͨ������"));
		jp_p2_top.add(new JLabel());
		jp_p2_top.add(new JLabel());
		jp_p2_top.add(new JLabel());
		jp_p2_top.add(btn_add);
		jp_p2_top.add(btn_alter);
		jp_p2_top.add(btn_del);
		jp_p2_top.add(new JLabel());
		jp_soc_center_p2.setLayout(new BorderLayout());
		jp_soc_center_p2.add(jp_p2_top,BorderLayout.NORTH);
		jp_p2_center.setLayout(new GridLayout(1,1));
		jp_p2_center.add(new JScrollPane(table2));
		jp_soc_center_p2.add(jp_p2_center,BorderLayout.CENTER);
		jp_soc_center.add(jp_soc_center_p2);
		jp_sellorderscheck.add(jp_soc_center,BorderLayout.CENTER);
		/**
		 * ��������
		 * @author ycd
		 */
		//����seek
		btn_seek.addActionListener(new ActionListener() {
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
		//�����Ʒ
		btn_addgoods.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddGoodsModelWindow	agmw=new AddGoodsModelWindow("������Ʒ(��Ʒ����)");
				Vector<Vector> data3=agmw.data3;
				double wantMoney=0;
				for( Vector data_1:data3){
					Vector data_1_1=new Vector();
					data_1_1.add(0, data_1.get(0));
					data_1_1.add(1, data_1.get(1));
					data_1_1.add(2, data_1.get(2));
					data_1_1.add(3, "");//���
					data_1_1.add(4, data_1.get(3));
					data_1_1.add(5, data_1.get(6));
					data_1_1.add(6, data_1.get(7));
					data.add(data_1_1);
					wantMoney += Double.parseDouble(data_1.get(7).toString());
				}
				tf_wantmoney.setText(wantMoney+"");
				tf_paymoney.setText(wantMoney+"");
				tablemodel=new DefaultTableModel(data,columnNames);
				table.setModel(tablemodel);
				table.updateUI();
				
			}
		});

		//�˳�
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				GoodSellModelWindow.this.setVisible(false);
			}
		});
		btn_tui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GoodSellModelWindow.this.setVisible(false);
			}
		});
		/**
		 * ���뵼���˵�
		 */
		btn_inout.addMouseListener(new mouseClicked());
		//����
		mitem_in.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImportExportHelp a = new ImportExportHelp();
				//ȡa�����data����
				Vector<Vector> corectData = new Vector<>();
				for(int i=0;i<a.data.size();i++)
					if(a.data.get(i).lastElement().toString().trim().equals("�ɵ���"))
						corectData.add(a.data.get(i));
					table.setModel(new DefaultTableModel(corectData, columnNames));
				//����Ӧ�����
				double wantMoney = 0;
				for(int i=0;i<corectData.size();i++) {
					wantMoney += Double.parseDouble(corectData.get(i).lastElement().toString());
				}	
				tf_wantmoney.setText(wantMoney+"");
				tf_paymoney.setText(wantMoney+"");
			}
		});
		//����
		mitmem_out.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ImportExportHelp();
			}
		});
		/**
		 *��������
		 *@author yc 
		 */
		btn_soc_check1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomInfoModelWindow cimw = new CustomInfoModelWindow();
					ret2=cimw.ret;
				try {
					if(cimw.table.isRowSelected(cimw.table.getSelectedRow())){
						ret2 = new CastUtil().vectorToCustomer(cimw.retData);
						tf_soc_name.setText(ret2.getName());
					}else{
						tf_soc_name.setText(cimw.ret.getName());
					}
					
				} catch (java.lang.NullPointerException e2) {
					//JOptionPane.showMessageDialog(null, "��ѡ������");
				}
				
			
			}
		});
		
		//ȷ���γɵ���
		btn_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {	
					data.get(0);
					String id=iddan;
					String odate=GoodSellModelWindow.this.tf_selldate.getText().trim();
					Double wantMoney=Double.parseDouble(GoodSellModelWindow.this.tf_wantmoney.getText().trim());
					Double payMoney=Double.parseDouble(GoodSellModelWindow.this.tf_paymoney.getText().trim());
					Depot depot=(Depot) GoodSellModelWindow.this.cobx_depots.getSelectedItem();
					Employee agent=(Employee) GoodSellModelWindow.this.cobx_agent.getSelectedItem();
					Admin operator=AdminService.admin;
					String bz=GoodSellModelWindow.this.tf_bz.getText().trim();
					PayWay payWay=(PayWay) GoodSellModelWindow.this.cobx_pay.getSelectedItem();
					Customer customer=GoodSellModelWindow.this.ret1;
					SellOrder sellorder=new SellOrder(id, odate, depot, wantMoney, payMoney, agent, operator, bz, payWay, customer);
					new  SellService().addOrder(sellorder, new CastUtil().vectorToGoods_sell(data), true);
					JOptionPane.showMessageDialog(null, "������۵��ݳɹ�!!!");
					GoodSellModelWindow.this.setVisible(false);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "������û��ҵ���ܱ���");
				}
				
			}
		});
		
		this.setTitle("��Ʒ����");
		getContentPane().add(tabbed);
		this.setBounds(300, 100, 900, 550);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setVisible(true);	
	}
		
	
	//���뵼���˵�
	public class mouseClicked extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			if(e.getButton()==1){
				pop_in_out.show(btn_inout, e.getX(), e.getY());
			}
			
		}
	}
	public static void main(String[] args) {
		new AdminService().Login("admin", "123");
		new GoodSellModelWindow();
	}
}
