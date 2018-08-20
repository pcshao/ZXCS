package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.lzw.BackgroundPanel;

import gui.lxh.BuyDocumentsCheckModelWindow;
import gui.lxh.BuyReturnGoodsModelWindow;
import gui.lxh.CurrentoInAccountsModelWindow;
import gui.lxh.InputGoodsModelWindow;
import gui.yc.StockCheckModelWindow;
import java.awt.Toolkit;
import java.awt.FlowLayout;

public class Index_InManagerUI extends JPanel {
	
	JButton btn_1,btn_2,btn_3,btn_4,btn_5;
	JPanel jp1,jp2;
	JPanel jp_center;
	BackgroundPanel bk;
	
	public Index_InManagerUI() {
		setLayout(new BorderLayout());
		
				//背景图
				bk = new BackgroundPanel();
				add(bk, BorderLayout.CENTER);
				bk.setImage(Toolkit.getDefaultToolkit().getImage(Index_InManagerUI.class.getResource(LoginUI.BKPATH)));
				bk.setLayout(new BorderLayout(0, 0));
				
				jp1 = new JPanel(new GridLayout(2, 1));
				jp2 = new JPanel(new GridLayout(3, 1));
				jp_center = new JPanel();
				jp_center.setOpaque(false);	//透明
				bk.add(jp_center);
				jp_center.setBorder(new EmptyBorder(100, 100, 100, 100));
				jp1.add(btn_1 = new JButton("采购进货"));
				jp1.add(btn_2 = new JButton("采购退货"));
				
				btn_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new BuyReturnGoodsModelWindow();
					}
				});
				jp2.add(btn_3 = new JButton("往来账务"));
				btn_3.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new CurrentoInAccountsModelWindow();
					}
				});
				jp2.add(btn_4 = new JButton("采购单据查询"));
				btn_4.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new BuyDocumentsCheckModelWindow(null);
					}
				});
				jp2.add(btn_5 = new JButton("当前库存查询"));
				btn_5.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new StockCheckModelWindow();
					}
				});
				
				jp_center.add(jp1);
				jp_center.add(jp2);
				
				Index_bottom_SomeButtonUI index_bottom_SomeButtonUI = new Index_bottom_SomeButtonUI();
				bk.add(index_bottom_SomeButtonUI, BorderLayout.SOUTH);
				
				//监听
				btn_1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						new InputGoodsModelWindow();
					}
				});
	}
}
