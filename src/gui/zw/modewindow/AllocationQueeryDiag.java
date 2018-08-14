package gui.zw.modewindow;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;




public class AllocationQueeryDiag extends JDialog{
		
		JPanel p_up,p_up1,p_up2,p_up3,p_up4,p_up5,p_up6,p_up7,p_down;													//分上下两个面板
		JTextField tf_type,tf_number,tf_name,tf_bar,tf_spec,tf_unit,tf_sum,tf_color,tf_inprice,tf_outprice,tf_bz,tf_adress;
		JButton btn_look,btn_ok,btn_cancel;
		public AllocationQueeryDiag  (){
			//-----------初始化-----------------
			p_up1=new JPanel();
			tf_type=new JTextField(20);
			btn_look=new JButton("查询");
			p_up1.add(btn_look);
			p_up1.add(tf_type);
			
			
			btn_look.addActionListener(new ActionListener() {
				//查询事件
				@Override
				public void actionPerformed(ActionEvent e) {
					
					
				}
			});
			/*
			this.setLayout(new BorderLayout());
			this.add(p_up,BorderLayout.CENTER);
			this.add(p_down,BorderLayout.SOUTH);*/
			this.add(p_up1);
			this.setTitle("操作单据号");
			this.setBounds(200, 200, 300, 300);
			//this.setLocationRelativeTo(null);
			this.setModal(true);
			this.setVisible(true);
		}
		public static void main(String[] args) {
			new AllocationQueeryDiag();
		}
}
