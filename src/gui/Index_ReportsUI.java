package gui;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import javax.swing.border.EmptyBorder;

import com.lzw.BackgroundPanel;

import gui.yc.CurrentAccountsModelWindow;
import gui.yc.StockCheckModelWindow;

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
		//����ͼ
		BackgroundPanel bk = new BackgroundPanel();
		add(bk, BorderLayout.CENTER);
		bk.setImage(Toolkit.getDefaultToolkit().getImage(Index_InManagerUI.class.getResource(LoginUI.BKPATH)));
		bk.setLayout(new BorderLayout(0, 0));
		
		jp_btn = new JPanel();
		jp_btn.setOpaque(false);
		bk.add(jp_btn, BorderLayout.CENTER);
		jp_btn.setBorder(new EmptyBorder(100, 100, 100, 100));
		
		panel_1 = new JPanel();
		panel_1.setLayout(new GridLayout(3, 1, 0, 0));
//		panel_1.add(btn_1 = new JButton("��Ӧ��ͳ��"));
//		panel_1.add(btn_2 = new JButton("��Ʒ�ɹ�ͳ��"));
//		panel_1.add(btn_3 = new JButton("ҵ��Ա�ɹ�"));
		
		panel_2 = new JPanel();
		panel_2.setLayout(new GridLayout(2, 1, 0, 0));
		panel_2.add(btn_4 = new JButton("���ɱ�ͳ��"));
		panel_2.add(btn_5 = new JButton("���䶯��"));
		btn_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new StockCheckModelWindow();
			}
		});
		
		panel_3 = new JPanel();
		panel_3.setLayout(new GridLayout(1, 1, 0, 0));
		panel_3.add(btn_6 = new JButton("��������ͳ��"));
		btn_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CurrentAccountsModelWindow();
			}
		});
		
//		panel_3.add(btn_6 = new JButton("�ͻ�����ͳ��"));
//		panel_3.add(btn_7 = new JButton("ҵ��Ա����ͳ��"));
//		panel_3.add(btn_8 = new JButton("��Ʒ����ͳ��"));
//		panel_3.add(btn_9 = new JButton("��Ʒ��������"));
//		panel_3.add(btn_10 = new JButton("����Ӫҵ����"));
		jp_btn.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		jp_btn.add(panel_1);
		jp_btn.add(panel_2);
		jp_btn.add(panel_3);
		Index_bottom_SomeButtonUI index_bottom_SomeButtonUI = new Index_bottom_SomeButtonUI();
		bk.add(index_bottom_SomeButtonUI, BorderLayout.SOUTH);
		
		//����
		//���ɱ�ͳ��
		btn_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Report_depotsBase(Index_ReportsUI.this);
			}
		});
	}
	
	
}
