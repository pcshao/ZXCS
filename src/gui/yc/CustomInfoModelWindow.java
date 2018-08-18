package gui.yc;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import bean.Customer;

import util.CastUtil;


import dao.CustomDao;
//�ͻ���Ϣ
public class CustomInfoModelWindow extends JDialog{
	public Vector retData;
	JPanel jp_center,jp_bottom;
	Vector<Vector> data;
	Vector columnNames;
	DefaultTableModel tablemodel;
	JTable table;
	JTextField tf_check;
	JButton btn_add,btn_alter,btn_ok,btn_exit;
	CustomDao cd;
	Customer ret;
	
	public CustomInfoModelWindow(){
		
		cd=new CustomDao();
		jp_center=new JPanel();
		jp_bottom=new JPanel();
		tf_check=new JTextField(15);
		btn_add=new JButton("�����ͻ�");
		btn_alter=new JButton("�޸�");
		btn_ok=new JButton("ȷ��");
		btn_exit=new JButton("�˳�");
		columnNames=new Vector();
		columnNames.add("���");
		columnNames.add("�ͻ�����");
		columnNames.add("��ϵ��");
		columnNames.add("��ϵ�绰");
		columnNames.add("��ϵ��ַ");
		//data=new Vector<Customer>();
		
		data = new CastUtil().objectToVector(cd.getCustomers());
		tablemodel = new DefaultTableModel(data, columnNames);
		/**
		 * �ѱ������Ϊ���ɱ༭��
		 * ��дisCellEditable����
		 * @author yc
		 */
		table=new JTable(tablemodel)
		{
			public boolean isCellEditable(int row,int clumn){
				return false;
			}
		};
		jp_center.setLayout(new GridLayout(1,1));
		jp_center.add(new JScrollPane(table));
		jp_bottom.setBorder(BorderFactory.createTitledBorder(""));
		jp_bottom.add(new JLabel("���ҿͻ���"));
		jp_bottom.add(tf_check);
		jp_bottom.add(btn_add);
		jp_bottom.add(btn_alter);
		jp_bottom.add(btn_ok);
		jp_bottom.add(btn_exit);
		
/*-------------------------------------����------------------------------------*/
		//�����ͻ�	
			btn_add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CustomAddModelWindow camw=new CustomAddModelWindow();
					ret=camw.ret;
					CustomInfoModelWindow.this.setVisible(false);
				}
			});
			
		//ȷ��
			btn_ok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						retData  = data.get(table.getSelectedRow());
						CustomInfoModelWindow.this.setVisible(false);
					} catch (Exception e2) {	
					}
					
				}
			});
		/**
		 * table����
		 * ����¼���˫����
		 * ʹ�õ���Ӧ�Ĺ˿�����
		 * @author yc
		 */
			table.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {}
				public void mousePressed(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {
					try {if(e.getButton()==1&&e.getClickCount()==2) {
							retData  = data.get(table.getSelectedRow());
							CustomInfoModelWindow.this.setVisible(false);
						}
						} catch (Exception e2) {	
						}
				}
			});;
		/**
		 * �˳�
		 * @author yc
		 */
			btn_exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CustomInfoModelWindow.this.setVisible(false);
				}
			});
		/**
		 * ���ҿͻ�
		 * @author yc
		 */
			tf_check.getDocument().addDocumentListener(new DocumentListener() {
				public void removeUpdate(DocumentEvent e) {
					String str=tf_check.getText();
					tablemodel=new DefaultTableModel(new CastUtil().objectToVector(cd.getCustomersInfoByContactorName(str)),columnNames);
					table.setModel(tablemodel);
					table.updateUI();
				}
				public void insertUpdate(DocumentEvent e) {
					String str=tf_check.getText();
					tablemodel=new DefaultTableModel(new CastUtil().objectToVector(cd.getCustomersInfoByContactorName(str)),columnNames);
					table.setModel(tablemodel);
					table.updateUI();
				}
				public void changedUpdate(DocumentEvent e) {}
			});
		/**
		 * �޸�
		 * @author yc
		 */
			btn_alter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}
			});
		
		this.setLayout(new BorderLayout());
		this.add(jp_center,BorderLayout.CENTER);
		this.add(jp_bottom,BorderLayout.SOUTH);
		this.setBounds(310, 250, 600, 300);
		this.setModal(true);
		this.setVisible(true);
		

		
	}
}
