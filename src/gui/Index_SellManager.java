package gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.border.EmptyBorder;

import com.lzw.BackgroundPanel;

import gui.yc.CurrentAccountsModelWindow;
import gui.yc.CustomReturnGoodsModelWindow;
import gui.yc.GoodSellModelWindow;
import gui.yc.SalesDocumentsCheckModelWindow;
import gui.yc.StockCheckModelWindow;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Index_SellManager extends JPanel {

	JButton btn_1,btn_2,btn_3,btn_4,btn_5,btn_6;
	JPanel jp_1,jp_2;
	private JPanel jp_center;
	public Index_SellManager() {
		setLayout(new BorderLayout(0, 0));

		//背景图
		BackgroundPanel bk = new BackgroundPanel();
		add(bk);
		bk.setImage(Toolkit.getDefaultToolkit().getImage(Index_InManagerUI.class.getResource(LoginUI.BKPATH)));
		bk.setLayout(new BorderLayout(0, 0));
		
		jp_center = new JPanel();
		bk.add(jp_center, BorderLayout.NORTH);
		jp_center.setOpaque(false);
		jp_center.setBorder(new EmptyBorder(100, 100, 100, 100));
		jp_center.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		jp_1 = new JPanel(new GridLayout(2, 1));
		jp_center.add(jp_1);
		
		jp_1.add(btn_1 = new JButton("商品销售"));
		btn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GoodSellModelWindow();
			}
		});
		jp_1.add(btn_2 = new JButton("顾客退货"));
		btn_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CustomReturnGoodsModelWindow();
			}
		});
		jp_2 = new JPanel(new GridLayout(3, 1));
		jp_center.add(jp_2);
		jp_2.add(btn_3 = new JButton("往来账务"));
		btn_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CurrentAccountsModelWindow();
			}
		});
		jp_2.add(btn_4 = new JButton("销售单据查询"));
		btn_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SalesDocumentsCheckModelWindow();
			}
		});
		jp_2.add(btn_5 = new JButton("当前库存查询"));
		btn_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new StockCheckModelWindow();
			}
		});
		Index_bottom_SomeButtonUI index_bottom_SomeButtonUI = new Index_bottom_SomeButtonUI();
		bk.add(index_bottom_SomeButtonUI, BorderLayout.SOUTH);
	}
}
