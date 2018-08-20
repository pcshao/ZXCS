package gui;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import gui.lxh.OldGoodsADDModelWindow;

public class Index_bottom_SomeButtonUI extends JPanel{

	JButton btn_1,btn_2,btn_3,btn_4,btn_5;
	JPanel jp;
	
	public Index_bottom_SomeButtonUI() {
		jp = new JPanel(new GridLayout(1, 5));
		
		jp.add(btn_1 = new JButton("商品管理")); 
		btn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new OldGoodsADDModelWindow(null);
			}
		});
		jp.add(btn_2 = new JButton("供货商管理")); 
		jp.add(btn_3 = new JButton("客户管理")); 
		jp.add(btn_4 = new JButton("仓库设置")); 
		jp.add(btn_5 = new JButton("员工信息")); 
		this.add(jp);
		//图标
		ImageIcon ic_goods = new ImageIcon(MainUI.class.getResource("/icon/barscode3.png"));
		ic_goods.setImage(ic_goods.getImage().getScaledInstance(20, 20,Image.SCALE_DEFAULT ));
		ImageIcon factory3 = new ImageIcon(MainUI.class.getResource("/icon/factory3.png"));
		factory3.setImage(factory3.getImage().getScaledInstance(20, 20,Image.SCALE_DEFAULT ));
		ImageIcon store = new ImageIcon(MainUI.class.getResource("/icon/store.png"));
		store.setImage(store.getImage().getScaledInstance(20, 20,Image.SCALE_DEFAULT ));
		ImageIcon store11 = new ImageIcon(MainUI.class.getResource("/icon/store11.png"));
		store11.setImage(store11.getImage().getScaledInstance(20, 20,Image.SCALE_DEFAULT ));
		ImageIcon user58 = new ImageIcon(MainUI.class.getResource("/icon/user58.png"));
		user58.setImage(user58.getImage().getScaledInstance(20, 20,Image.SCALE_DEFAULT ));
		btn_1.setIcon(ic_goods);
		btn_2.setIcon(store11);
		btn_3.setIcon(store);
		btn_4.setIcon(factory3);
		btn_5.setIcon(user58);
		
		btn_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Manage_ParentUI("供应商管理");
			}
		});
		btn_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Manage_ParentUI("客户管理");
			}
		});
		btn_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Manage_ParentUI("仓库管理");
			}
		});
		btn_5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Manage_ParentUI("员工管理");
			}
		});
	}
}
