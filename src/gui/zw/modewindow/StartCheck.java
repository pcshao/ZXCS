package gui.zw.modewindow;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StartCheck extends JDialog{

	JPanel jp_top,jp_center,jp_low,jp_center_right_top,
	jp_center_left,jp_center_right,jp_center_left_top;
	JPanel jp_top1,jp_top2,jp_top3;
	JButton btn1,btn2,btn3,btn4;
	
	JTable table;
	DefaultTableModel model;
	Vector<Vector> rowData=new Vector<Vector>();	
	Vector columnNames=new Vector();
	public   StartCheck(){
		columnNames.add("�̵㵥��");
		columnNames.add("����");
		columnNames.add("�ֿ�����");
		columnNames.add("����Ա");
		columnNames.add("��Ʒ����");
		//columnNames.add("��ע");
		model=new DefaultTableModel(rowData, columnNames);
		table=new JTable(model);
		
		btn1=new JButton("��һ��");
		btn2=new JButton("�˳�");
		
		jp_top=new JPanel();
		jp_center=new JPanel();
		jp_low=new JPanel();
		
		jp_top.add(new JLabel("��һ��:ѡ���б�Ҫ�̵�ĵ���"));
		jp_center.setBorder(BorderFactory.createTitledBorder("ѡ���̵㵥"));
		jp_center.add(new JScrollPane(table));
		jp_low.add(btn1);jp_low.add(btn2);
		
		
		
		btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//��һ��
				new StartCheckTwo();
			}
		});
		btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				StartCheck.this.setVisible(false);
			}
		});
		
		
		
		this.setLayout(new BorderLayout());	
		this.add(jp_top,BorderLayout.NORTH);
		this.add(jp_center,BorderLayout.CENTER);
		this.add(jp_low,BorderLayout.SOUTH);
		this.setTitle("��ӯ�̿�");
		this.setBounds(100, 100, 600, 600);
		//this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new StartCheck();
	}
		
}
