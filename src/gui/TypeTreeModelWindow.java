package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import bean.GoodsType;
import util.TypeWindow;

public class TypeTreeModelWindow extends JDialog{
	//树菜单
	JPanel jp1,jp2;
	JButton btn_1,btn_2;
	TypeWindow typetree;
	DefaultMutableTreeNode node;
	JTree tree;
	public GoodsType type;
	
	public TypeTreeModelWindow() {
		typetree=new TypeWindow();
		jp1=new JPanel();
		jp2=new JPanel();
		btn_1=new JButton("确定");
		btn_2=new JButton("退出");
		this.setLayout(new BorderLayout());
		jp1.setLayout(new GridLayout(1,1));
		jp1.setBorder(BorderFactory.createTitledBorder("商品类别"));
		jp1.add(typetree);
		this.add(jp1,BorderLayout.CENTER);
		jp2.add(btn_1);
		jp2.add(btn_2);
		this.add(jp2,BorderLayout.SOUTH);
		/**
		 * 监听事件
		 * @author yc
		 * 
		 */
		btn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(1);
				node=(DefaultMutableTreeNode) typetree.tree_type.getLastSelectedPathComponent();;//返回选择节点
			    type=(GoodsType) node.getUserObject();							//把节点转为GoodsType类型 返回type
			    TypeTreeModelWindow.this.setVisible(false);
			}
		});
		this.setTitle("商品类别");
		this.setSize(350, 300);
		this.setLocationRelativeTo(null);
		this.setModal(true);
	}
}
