package gui;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Index_ReportsUI extends JPanel {

	JButton btn_1,btn_2,btn_3,btn_4,btn_5;
	JButton btn_6,btn_7,btn_8,btn_9,btn_10;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel jp_btn;
	
	public Index_ReportsUI() {
		setLayout(new BorderLayout(0, 0));
		
		jp_btn = new JPanel();
		jp_btn.setBorder(new EmptyBorder(100, 100, 100, 100));
		add(jp_btn, BorderLayout.CENTER);
		
		panel_1 = new JPanel();
		panel_1.setLayout(new GridLayout(3, 1, 0, 0));
		panel_1.add(btn_1 = new JButton("��Ӧ��ͳ��"));
		panel_1.add(btn_2 = new JButton("��Ʒ�ɹ�ͳ��"));
		panel_1.add(btn_3 = new JButton("ҵ��Ա�ɹ�"));
		
		panel_2 = new JPanel();
		panel_2.setLayout(new GridLayout(2, 1, 0, 0));
		panel_2.add(btn_4 = new JButton("���ɱ�ͳ��"));
		panel_2.add(btn_5 = new JButton("���䶯��"));
		
		panel_3 = new JPanel();
		panel_3.setLayout(new GridLayout(5, 1, 0, 0));
		panel_3.add(btn_6 = new JButton("�ͻ�����ͳ��"));
		panel_3.add(btn_7 = new JButton("ҵ��Ա����ͳ��"));
		panel_3.add(btn_8 = new JButton("��Ʒ����ͳ��"));
		panel_3.add(btn_9 = new JButton("��Ʒ��������"));
		panel_3.add(btn_10 = new JButton("����Ӫҵ����"));
		jp_btn.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		jp_btn.add(panel_1);
		jp_btn.add(panel_2);
		jp_btn.add(panel_3);
		
		//����
		//���ɱ�ͳ��
		btn_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Report_depotsBase(Index_ReportsUI.this);
			}
		});
	}
	
	
}
