package gui.pos;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;
import java.util.logging.LogManager;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import bean.Customer;
import bean.Depot;
import bean.Employee;
import bean.Goods;
import bean.PayWay;
import bean.Vip;
import gui.LoginUI;
import service.AdminService;
import service.PosService;
import util.CastUtil;
import util.LogMark;

import javax.swing.JComboBox;

public class POS extends JFrame{
	
	private JTable table_main;
	private JTable table_pop;
	private JTextField tf_operate;
	private JTextField tf_goodsid;
	private JComboBox cobx_customer,cobx_agent,cobx_payway,cobx_depot;
	private DefaultComboBoxModel model_customer,model_agent,model_payway,model_depot;
	private DefaultTableModel model_pop;
	Vector colunNames;
	//右键菜单
	JPopupMenu menu_pop;
	JMenuItem item_remove;
	//会员
	Vip vip;
	boolean isVip = false;
	String vipPhone = "";

	//注册服务
	PosService posService;
	
	public POS() {
		setTitle("POS机终端模拟");
		
		posService = new PosService();
		colunNames = new Vector<>();
		colunNames.add("编号");
		colunNames.add("商品条码");
		colunNames.add("商品名称");
		colunNames.add("商品单位");
		colunNames.add("商品折扣");
		colunNames.add("商品价格");
		colunNames.add("商品特价");
		colunNames.add("VIP折扣");
		colunNames.add("VIP特价");
		colunNames.add("数量");
		
		//右键
		menu_pop = new JPopupMenu();
		item_remove = new JMenuItem("移除商品");
		menu_pop.add(item_remove);
		
		this.setBounds(0,0,800,600);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel jp_north = new JPanel();
		getContentPane().add(jp_north, BorderLayout.NORTH);
		
		JLabel lb_operate = new JLabel("\u64CD\u4F5C\u5458");
		jp_north.add(lb_operate);
		
		tf_operate = new JTextField();
		jp_north.add(tf_operate);
		tf_operate.setColumns(10);
		
		JPanel jp_south = new JPanel();
		getContentPane().add(jp_south, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		jp_south.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel jp_popID = new JPanel();
		panel_1.add(jp_popID, BorderLayout.CENTER);
		jp_popID.setLayout(new GridLayout(0, 1, 0, 0));
		
		table_pop = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jp_popID.add((table_pop));
		
		table_main = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		DefaultTableModel model_main = new DefaultTableModel(new Vector<>(), colunNames);
		table_main.setModel(model_main);
		
		JPanel panel = new JPanel();
		panel_1.add(panel, BorderLayout.SOUTH);
		
		JLabel lblNewLabel = new JLabel("\u8F93\u5165\u5546\u54C1\u7F16\u53F7");
		panel.add(lblNewLabel);
		
		tf_goodsid = new JTextField();
		panel.add(tf_goodsid);
		tf_goodsid.setColumns(10);
		
		JButton btn_confirm = new JButton("\u786E\u5B9A(enter)");
		panel.add(btn_confirm);
		
		JButton btn_vip = new JButton("\u4F1A\u5458\u7ED3\u8D26(insert)");
		panel.add(btn_vip);
		
		JButton btn_done = new JButton("\u7ED3\u8D26(F5)");
		panel.add(btn_done);
		
		cobx_payway = new JComboBox();
		panel.add(cobx_payway);
		//结账
		btn_done.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double wantMoney = 0;
				double sellPrice = 0;
				double speciallPrice = 0;
				double disCount = 0;
				double vipSpeciall = 0;
				double vipDisCount = 0;
				//获取table_main中的数据 第一列的编号
				HashSet<Goods> goods = new HashSet<Goods>();
				DefaultTableModel model = (DefaultTableModel) table_main.getModel();
				int len = model.getRowCount();
				for(int i=0;i<len;i++) {
					int g = (int) table_main.getModel().getValueAt(i, 0);
					int tempNum = (int) table_main.getModel().getValueAt(i, 9);
					disCount = (double) table_main.getModel().getValueAt(i, 4);
					sellPrice = (double) table_main.getModel().getValueAt(i, 5);
					speciallPrice = (double) table_main.getModel().getValueAt(i, 6);
					vipSpeciall = (double) table_main.getModel().getValueAt(i, 7);
					vipDisCount = (double) table_main.getModel().getValueAt(i, 8);
					goods.add(new Goods(g, tempNum, sellPrice));
					if(isVip) {
						wantMoney += vip.getDiscount()*vipSpeciall*vipDisCount*tempNum;
					}
					else
						wantMoney += disCount*speciallPrice*tempNum;
				}
				System.out.println(wantMoney);
				//获取客户信息(普通客户)
				Customer c = (Customer) cobx_customer.getSelectedItem();
				//获取导购员信息（员工）
				Employee agent = (Employee) cobx_agent.getSelectedItem();
				//获取支付方式
				PayWay payway = (PayWay) cobx_payway.getSelectedItem();
				//获取仓库
				Depot depot = (Depot) cobx_depot.getSelectedItem();
				posService.checkOut(goods, AdminService.admin, c, agent, payway, depot, wantMoney, isVip, vip);
				LogMark.LogWrite("POS销售发生，"+depot.getName()+"卖出"+wantMoney+"(只做参考)\n");
				//清空会员信息
				isVip = false;
				vipPhone = "";
				//
				new JOptionPane().showMessageDialog(POS.this, "欢迎下次光临！");
				model_main.setRowCount(0);
				table_main.setModel(model_main);
				table_main.updateUI();
			}
		});
		btn_vip.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insertVip();
			}
		});

		JLabel lb_customer = new JLabel("\u5BA2\u6237");
		jp_north.add(lb_customer);
		
		JPanel jp_center = new JPanel();
		getContentPane().add(jp_center, BorderLayout.CENTER);
		jp_center.setLayout(new GridLayout(0, 1, 0, 0));

		jp_center.add(new JScrollPane(table_main));
		
		model_customer = new DefaultComboBoxModel(posService.getCustomers());
		cobx_customer = new JComboBox(model_customer);
		jp_north.add(cobx_customer);
		
		JLabel lblNewLabel_1 = new JLabel("\u5BFC\u8D2D\u5458");
		jp_north.add(lblNewLabel_1);
		
		cobx_agent = new JComboBox();
		jp_north.add(cobx_agent);
		
		JLabel label = new JLabel("\u4ED3\u5E93");
		jp_north.add(label);
		
		cobx_depot = new JComboBox();
		jp_north.add(cobx_depot);
		
		this.setVisible(true);
		/**
		 * 键盘监听
		 */
		tf_goodsid.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar()==KeyEvent.VK_ENTER)
					btn_confirm.doClick();
				if(e.getKeyChar()==KeyEvent.VK_F5)
					btn_done.doClick();
				if(e.getKeyChar()==KeyEvent.VK_INSERT)
					btn_vip.doClick();
			}
		});
		/**
		 * 商品输入
		 */
		btn_confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tf_goodsid.getText()==null||tf_goodsid.getText().equals(""))
					return;
				Vector<Vector> data = new CastUtil().objectToVector(posService.getGoodsLikeID(Integer.parseInt(tf_goodsid.getText().trim())), new Goods());
			    model_pop = new DefaultTableModel(data, colunNames);
				table_pop.setModel(model_pop);
				table_pop.updateUI();
			}
		});
		//加载操作员
		tf_operate.setText(AdminService.admin.getName());
		tf_operate.setEditable(false);
		// 加载客户列表 默认普通客户
		model_customer = new DefaultComboBoxModel(posService.getCustomers());
		cobx_customer.setModel(model_customer);
		// 加载导购员 即员工
		model_agent = new DefaultComboBoxModel<>(posService.getAgents());
		cobx_agent.setModel(model_agent);
		//加载支付方式
		model_payway = new DefaultComboBoxModel<>(posService.getPayWays());
		cobx_payway.setModel(model_payway);
		//加载仓库
		model_depot = new DefaultComboBoxModel<>(posService.getDepots());
		cobx_depot.setModel(model_depot);
		/*
		 * 选择POP商品
		 */
		table_pop.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
				if(e.getClickCount()==2) {
					//双击弹出的待选框后将该商品添加到主table
					Vector goods = (Vector) model_pop.getDataVector().get(table_pop.getSelectedRow());
					insertTable(goods);
				}
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
			}
		});
		/**
		 * 右键菜单
		 * 	移除多选商品
		 */
		table_main.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
				if(e.getButton()==3) {
					menu_pop.show(table_main, e.getX(), e.getY());
				}
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
			}
		});
		item_remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Vector goods = (Vector) model_main.getDataVector().get(table_main.getSelectedRow());
				removeFromTable(goods);
			}
		});
		//全局监听
		//....
	}
	/**
	 * 插入会员
	 */
	protected void insertVip() {
		vipPhone = new JOptionPane().showInputDialog(POS.this, "请输入会员手机号");
		if(vipPhone==null)
			return;
		if((vip= posService.getVipByPhone(vipPhone))==null)
			return;
		if(vip!=null) {
			if(JOptionPane.YES_OPTION==new JOptionPane().showConfirmDialog(POS.this, "是否会员："+vip.getName()
					+ "\n联系电话："+vip.getPhone(), "会员:"+vip.getName(), JOptionPane.YES_NO_OPTION))
				isVip = true;
		}
	}
	/**
	 * 动态插入和更新table_main
	 * @param args
	 */
	private void insertTable(Vector goods) {
		DefaultTableModel model = (DefaultTableModel) table_main.getModel();
		for(int i=0;i<model.getRowCount();i++) {
			if(model.getValueAt(i, 0).equals(goods.get(0))) {
				model.setValueAt(Integer.parseInt(model.getValueAt(i, 9).toString())+1, i, 9);
				table_main.setModel(model);
				table_main.updateUI();
				return;
			}
		}
		model.addRow(goods);
		table_main.setModel(model);
		table_main.updateUI();
	}
	/**
	 * 动态删除和更新table_main
	 * @param args
	 */
	private void removeFromTable(Vector goods) {
		DefaultTableModel model = (DefaultTableModel) table_main.getModel();
		model.removeRow(table_main.getSelectedRow());
		table_main.setModel(model);
		table_main.updateUI();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater ( new Runnable ()
        {
            public void run ()
            {
            	 try {
					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
				} catch (Exception e) {
					e.printStackTrace();
				}
         		new AdminService().Login("admin", "123");
        		new POS();
            }
        } );
	}
}
