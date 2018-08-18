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
	//���˵�
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
		btn_1=new JButton("ȷ��");
		btn_2=new JButton("�˳�");
		this.setLayout(new BorderLayout());
		jp1.setLayout(new GridLayout(1,1));
		jp1.setBorder(BorderFactory.createTitledBorder("��Ʒ���"));
		jp1.add(typetree);
		this.add(jp1,BorderLayout.CENTER);
		jp2.add(btn_1);
		jp2.add(btn_2);
		this.add(jp2,BorderLayout.SOUTH);
		/**
		 * �����¼�
		 * @author yc
		 * 
		 */
		btn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				node=(DefaultMutableTreeNode) typetree.tree_type.getLastSelectedPathComponent();;//����ѡ��ڵ�
			    type=(GoodsType) node.getUserObject();							//�ѽڵ�תΪGoodsType���� ����type
			    TypeTreeModelWindow.this.setVisible(false);
			}
		});
		this.setTitle("��Ʒ���");
		this.setSize(350, 300);
		this.setLocationRelativeTo(null);
		this.setModal(true);
	}
}
