package util;

import gui.lxh.AddGoodsModleWindow;
import gui.lxh.OldGoodsADDModelWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import dao.GetAllGoodsDao;
import dao.GoodsTypeDao;

import bean.GoodsType;



public class TypeWindow extends JPanel{	
	//这里是右键后的菜单
	JPopupMenu pop;
	JMenuItem mitem_add,mitem_alter,mitem_delete;
	GoodsTypeDao dao=null;

	//这里是树有关的
	public JTree tree_type;
	DefaultTreeModel  tree_model;
	DefaultMutableTreeNode node_alltype;
	

	public TypeWindow(){
		pop=new JPopupMenu();
		mitem_add=new JMenuItem("添加");
		mitem_alter=new JMenuItem("修改");
		mitem_delete=new JMenuItem("删除");
		pop.add(mitem_add);
		pop.add(mitem_alter);
		pop.add(mitem_delete);
		dao=new GoodsTypeDao();
		
		//添加子树
		node_alltype=new DefaultMutableTreeNode(new GoodsType(1,"仓库",1));
		node_alltype=(dao.getNodeFromDB(1,node_alltype));
		tree_model=new DefaultTreeModel(node_alltype);
		tree_type=new JTree(tree_model);
		
		this.add(tree_type);
		
		//右键点击事件
		tree_type.addMouseListener(new myMouse());	
		
		//添加一个节点
		mitem_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					DefaultMutableTreeNode node=(DefaultMutableTreeNode) tree_type.getLastSelectedPathComponent();
					String str=new JOptionPane().showInputDialog("请输入要添加的类别名字：").trim();
					if(str==null||str.equals("")){	
						JOptionPane.showMessageDialog(tree_type, "请不要输入空值！！");
					}else{
						if(node==null){													//这个node是全局node,代表被点击的那个节点
							JOptionPane.showMessageDialog(tree_type, "请选中节点再添加！！");
						}else{
							GoodsType type=(GoodsType) node.getUserObject();						//获取选中的节点
							int superid=dao.getSuperId(type.getSelf_id());				//获取选中的节点父亲的id
							int selfid=dao.getIdNumber();								//设置新节点的id
							dao.insertInfo(selfid,type.getSelf_id(),str);
							node.add(new DefaultMutableTreeNode(new GoodsType(selfid,str,type.getSelf_id())));

							tree_type.setModel(tree_model);
							tree_type.updateUI();
						}
					}
				}catch (Exception ex) {
					
				}	
			}
		});
		
		//修改
		mitem_alter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				DefaultMutableTreeNode node=(DefaultMutableTreeNode) tree_type.getLastSelectedPathComponent();
					String new_name=new JOptionPane().showInputDialog("请输入要修改后的类别名字：").trim();
					if(new_name==null||new_name.equals("")){	
						JOptionPane.showMessageDialog(tree_type, "请不要输入空值！！");
					}else{
						if(node==null){													//这个node是全局node,代表被点击的那个节点
							JOptionPane.showMessageDialog(tree_type, "请选中节点再点击修改！！");
						}else{
							GoodsType type=(GoodsType) node.getUserObject();						//获取选中的节点
							dao.alterNode(type.getSelf_id(), new_name);
							
							DefaultMutableTreeNode node1=new DefaultMutableTreeNode(new GoodsType(1,"仓库",1));
							node1=dao.getNodeFromDB(1,node1);
							tree_model=new DefaultTreeModel(node1);
							DefaultTreeModel sb=tree_model;
							tree_type.setModel(sb);
							tree_type.updateUI();

						}
					}
				}catch (Exception ex) {
					
				}		
			}
		});
		//删除，必须是叶子节点，必须没有商品
		mitem_delete.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				try{
					DefaultMutableTreeNode node=(DefaultMutableTreeNode) tree_type.getLastSelectedPathComponent();
					GoodsType type=(GoodsType) node.getUserObject();
					if(node.isLeaf()){
						dao.deleteNode(type.getSelf_id());
						
						//这里是刷新步骤哦
						DefaultMutableTreeNode node1=new DefaultMutableTreeNode(new GoodsType(1,"仓库",1));
						node1=dao.getNodeFromDB(1,node1);
						tree_model=new DefaultTreeModel(node1);
						DefaultTreeModel sb=tree_model;
						tree_type.setModel(tree_model);
						tree_type.updateUI();
						//刷新结束
					}else{
						JOptionPane.showMessageDialog(tree_type, "只能删除叶子节点");
					}
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(tree_type, "请选中节点再点击右键！");
				}
				
				
			}
		});
	
	}
	
		//实现右键点击需要的类
	public class myMouse extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			if(e.getButton()==3){
				pop.show(tree_type, e.getX(), e.getY());	
			}
		}
	}
}
