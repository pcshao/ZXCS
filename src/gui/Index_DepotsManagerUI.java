package gui;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Checkbox;

import javax.swing.border.EmptyBorder;

import com.lzw.BackgroundPanel;

import gui.yc.StockCheckModelWindow;
import gui.zw.warehouse.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Index_DepotsManagerUI extends JPanel {

	JButton btn_1,btn_2,btn_3,btn_4;
	JPanel jp_content;
	private JPanel jp_2;
	private JPanel jp_1;
	
	public Index_DepotsManagerUI() {
		setLayout(new BorderLayout(0, 0));

		//±³¾°Í¼
		BackgroundPanel bk = new BackgroundPanel();
		add(bk, BorderLayout.CENTER);
		bk.setImage(Toolkit.getDefaultToolkit().getImage(Index_InManagerUI.class.getResource(LoginUI.BKPATH)));
		bk.setLayout(new BorderLayout(0, 0));
		
		jp_content = new JPanel();
		jp_content.setOpaque(false);
		bk.add(jp_content, BorderLayout.NORTH);
		jp_content.setBorder(new EmptyBorder(100, 100, 100, 100));
		
		jp_1 = new JPanel();
		jp_content.add(jp_1);
		jp_1.setLayout(new GridLayout(2, 1, 0, 0));
		
		jp_content.add(btn_1 = new JButton("¿â´æµ÷²¦"));
		btn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AllocationoneWindow();
			}
		});
		jp_1.add(btn_1);
		jp_content.add(btn_4 = new JButton("¿â´æ±¨¾¯"));
		btn_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new WarningWindow();
			}
		});
		jp_1.add(btn_4);
		
		jp_2 = new JPanel();
		jp_content.add(jp_2);
		jp_2.setLayout(new GridLayout(1, 1, 0, 0));
//		jp_content.add(btn_2 = new JButton("¿â´æÅÌµã"));
//		btn_2.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				new CheckWindow();
//			}
//		});
//		jp_2.add(btn_2);
		jp_content.add(btn_3 = new JButton("¿â´æ±ä¶¯"));
		btn_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new StockCheckModelWindow();
			}
		});
		jp_2.add(btn_3);
		
		Index_bottom_SomeButtonUI index_bottom_SomeButtonUI = new Index_bottom_SomeButtonUI();
		bk.add(index_bottom_SomeButtonUI, BorderLayout.SOUTH);
	}
}
