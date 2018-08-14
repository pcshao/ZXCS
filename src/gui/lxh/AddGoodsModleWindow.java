package gui.lxh;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import bean.GoodsType;

import dao.GetAllGoodsDao;

import oracle.jdbc.driver.DBConversion;

import util.TypeWindow;


public class AddGoodsModleWindow extends JDialog{
		TypeWindow p_tree_type=null;
	    InputGoodsModelWindow igm=null;
		GetAllGoodsDao  goodsdao=null;
		JPanel p_up,p_up1,p_up2,p_up3,p_up4,p_up5,p_up6,p_up7,p_down;													//分上下两个面板
		public JTextField tf_type,tf_number,tf_name,tf_bar,tf_spec,tf_unit,tf_sum,tf_color,tf_inprice,tf_outprice,tf_bz,tf_adress;
		JButton btn_look,btn_ok,btn_cancel;
		
		public AddGoodsModleWindow(InputGoodsModelWindow igm){
			this.igm=igm;
			p_tree_type=new TypeWindow();
			goodsdao=new GetAllGoodsDao();
			//-----------初始化-----------------
			p_up=new JPanel();
			p_down=new JPanel();
			p_up1=new JPanel();
			p_up2=new JPanel();
			p_up3=new JPanel();
			p_up4=new JPanel();
			p_up5=new JPanel();
			p_up6=new JPanel();
			p_up7=new JPanel();
			
			tf_type=new JTextField(10);
			tf_type.setEditable(false);	
			tf_type.setText("点击查看选择类别");
			tf_number=new JTextField(16);
			
			tf_name=new JTextField(16);
			tf_bar=new JTextField(16);
			tf_spec=new JTextField(16);
			tf_unit=new JTextField(16);
			tf_sum=new JTextField(16);
			tf_color=new JTextField(16);
			tf_inprice=new JTextField(16);
			tf_outprice=new JTextField(16);
			tf_bz=new JTextField(40);
			tf_adress=new JTextField(40);
			
			btn_look=new JButton("查看");
			btn_ok=new JButton("确定");
			btn_cancel=new JButton("退出");
			
			//----------添加-------------
			//把up分为3个面板
	
			p_up1.add(new JLabel("所属类别："));
			p_up1.add(tf_type);
			p_up1.add(btn_look);
			p_up1.add(new JLabel("   "));
			p_up1.add(new JLabel("商品编号："));
			tf_number.setText(goodsdao.getNowGoodId()+"");
			p_up1.add(tf_number);	
			
			tf_number.setEnabled(false);	
			p_up2.add(new JLabel("商品名称："));
			p_up2.add(tf_name);
			p_up2.add(new JLabel("   "));
			p_up2.add(new JLabel("商品条码："));
			p_up2.add(tf_bar);
			
			p_up3.add(new JLabel("规格型号："));
			p_up3.add(tf_spec);
			p_up3.add(new JLabel("   "));
			p_up3.add(new JLabel("单         位："));
			p_up3.add(tf_unit);
			
			p_up4.add(new JLabel("库存下限："));
			p_up4.add(tf_sum);
			p_up4.add(new JLabel("   "));
			p_up4.add(new JLabel("颜          色："));
			p_up4.add(tf_color);
			
			p_up5.add(new JLabel("预设进价："));
			p_up5.add(tf_inprice);
			p_up5.add(new JLabel("   "));
			p_up5.add(new JLabel("预设售价："));
			p_up5.add(tf_outprice);
			
			p_up6.add(new JLabel("备         注："));
			p_up6.add(tf_bz);
			p_up7.add(new JLabel("生产厂商："));
			p_up7.add(tf_adress);
			
			p_up.setLayout(new GridLayout(7,1));
			p_up.add(p_up1);
			p_up.add(p_up2);
			p_up.add(p_up3);
			p_up.add(p_up4);
			p_up.add(p_up5);
			p_up.add(p_up6);
			p_up.add(p_up7);
			
			//up完毕，开始down
			p_down.setLayout(new GridLayout(1,5));
			p_down.add(new JLabel(""));
			p_down.add(btn_ok);
			p_down.add(new JLabel(""));
			p_down.add(btn_cancel);
			p_down.add(new JLabel(""));
			
			
			p_up1.setBorder(BorderFactory.createTitledBorder(""));
			p_up2.setBorder(BorderFactory.createTitledBorder(""));
			p_up3.setBorder(BorderFactory.createTitledBorder(""));
			p_up4.setBorder(BorderFactory.createTitledBorder(""));
			p_up5.setBorder(BorderFactory.createTitledBorder(""));
			p_up6.setBorder(BorderFactory.createTitledBorder(""));
			p_up7.setBorder(BorderFactory.createTitledBorder(""));
			p_up.setBorder(BorderFactory.createTitledBorder(""));
			p_down.setBorder(BorderFactory.createTitledBorder(""));
			this.setLayout(new BorderLayout());
			this.add(p_up,BorderLayout.CENTER);
			this.add(p_down,BorderLayout.SOUTH);
			this.setTitle("增加商品");
			this.setBounds(0, 0, 600, 600);
			this.setLocationRelativeTo(null);
			/**
			 * 查看所有类别
			 */
			btn_look.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null,new JScrollPane(p_tree_type));
				}
			});
			/**
			 * typeWindow的点击事件
			 */
			
			p_tree_type.tree_type.addTreeSelectionListener(new TreeSelectionListener() {
				public void valueChanged(TreeSelectionEvent e) {
					DefaultMutableTreeNode node =(DefaultMutableTreeNode) p_tree_type.tree_type.getLastSelectedPathComponent();
					if(node!=null){
						GoodsType type=(GoodsType) node.getUserObject();
						String id=type.getSelf_id()+"";
						tf_type.setText(id);
					
					}
				}
			});
			/*
			 * 确定事件
			 */
			btn_ok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						
						String gname=tf_name.getText();
						String goodsid=tf_bar.getText();
						String norms=tf_spec.getText();
						String unit=tf_unit.getText();
						int type= Integer.parseInt(tf_type.getText());
						int alertNum=Integer.parseInt(tf_sum.getText());
						float in_price=Float.parseFloat(tf_inprice.getText());
						float sell_price=Float.parseFloat(tf_outprice.getText());
						String bz=tf_bz.getText();
						String dname=AddGoodsModleWindow.this.igm.cbox_depot.getSelectedItem().toString();
						goodsdao.insertNewGoods(type, gname, goodsid, norms, unit, alertNum, in_price, sell_price, bz,dname);	
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "编号，名字，id，进售价格等不能为空");
					}
					
				}
			});
			/**
			 * 取消事件
			 */
			btn_cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AddGoodsModleWindow.this.setVisible(false);
				}
			});
			/**
			 * 键盘事件
			 */
			tf_type.addKeyListener(new MyKeyInt());
			tf_sum.addKeyListener(new MyKeyInt());
			tf_inprice.addKeyListener(new MyKeyFloat());
			tf_outprice.addKeyListener(new MyKeyFloat());
			this.setModal(true);
			this.setVisible(true);
		}
		
		/**
		 * 只能输入数字
		 * 
		 *
		 */
		public class MyKeyInt extends KeyAdapter{
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar()<48||e.getKeyChar()>57){
					e.consume();
				}
			}
		}
		/**
		 * 只能输入数字和小数点
		 * 
		 *
		 */
		public class MyKeyFloat extends KeyAdapter{
			public void keyTyped(KeyEvent e) {
				if((e.getKeyChar()<48||e.getKeyChar()>57)&&e.getKeyChar()!=46){
					e.consume();
				}
			}
		}
}
