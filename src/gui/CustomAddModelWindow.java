package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import bean.Customer;

import dao.CustomDao;

public class CustomAddModelWindow extends JDialog{
	JPanel jp1,jp2,jp3,jp4,jp5,jp6,jp7,jp8,jp9;
	JTextField tf_p1,tf_p2,tf_p3,tf_p4,tf_p5,tf_p6,tf_p7;
	JRadioButton btn_f,btn_s;
	ButtonGroup bg;
	JLabel jl;
	JButton btn_ok,btn_exit;
	Customer ret;
	public CustomAddModelWindow(){
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		jp4=new JPanel();
		jp5=new JPanel();
		jp6=new JPanel();
		jp7=new JPanel();
		jp8=new JPanel();
		jp9=new JPanel();
		tf_p1=new JTextField(20);
		tf_p2=new JTextField(20);
		tf_p3=new JTextField(20);
		tf_p4=new JTextField(20);
		tf_p5=new JTextField(20);
		tf_p6=new JTextField(10);
		tf_p7=new JTextField(20);
		btn_f=new JRadioButton("默认客户");
		btn_s=new JRadioButton("禁用");
		bg=new ButtonGroup();
		jl=new JLabel("(初期应收：新增此客户时,此客户目前欠我方的货款金额.)");
		btn_ok=new JButton("确定");
		btn_exit=new JButton("退出");
		this.setLayout(new GridLayout(9,1));
		jp9.add(new JLabel("客户编号:"));
		jp9.add(tf_p7);
		jp1.add(new JLabel("客户名称:"));
		jp1.add(tf_p1);
		jp2.add(new JLabel("联   系 人："));
		jp2.add(tf_p2);
		jp3.add(new JLabel("联系电话:"));
		jp3.add(tf_p3);
		jp4.add(new JLabel("联系地址:"));
		jp4.add(tf_p4);
		jp5.add(new JLabel("备         注:"));
		jp5.add(tf_p5);
		jp6.add(new JLabel("初期应收："));
		jp6.add(tf_p6);
		bg.add(btn_f);
		bg.add(btn_s);
		jp6.add(btn_f);
		jp6.add(btn_s);
		jl.setForeground(Color.red);
		jp7.add(jl);
		jp8.setLayout(new GridLayout(1,5));
		jp8.add(new JLabel());
		jp8.add(btn_ok);
		jp8.add(new JLabel());
		jp8.add(btn_exit);
		jp8.add(new JLabel());
		this.add(jp9);
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);
		this.add(jp5);
		this.add(jp6);	
		this.add(jp7);
		this.add(jp8);
		
		/**
		 * 监听事件
		 * @author yc
		 */
		//文本框只能输入数字
		tf_p7.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {	
				if(e.getKeyChar()>KeyEvent.VK_0 &&e.getKeyChar()<KeyEvent.VK_9){
				
				}else if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE){
					
				}else{
					JOptionPane.showMessageDialog(CustomAddModelWindow.this, "请输入整数!!");
					e.consume();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {}
		});
		
		//确定
		btn_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str1=tf_p7.getText().trim();
				int i=Integer.parseInt(str1);
				String str2=tf_p1.getText().trim();
				String str3=tf_p2.getText().trim();
				String str4=tf_p3.getText().trim();
				String str5=tf_p4.getText().trim();
				String str6=tf_p5.getText().trim();
				if(str1.isEmpty()){
					JOptionPane.showMessageDialog(CustomAddModelWindow.this, "客户名称不能为空");
				}else{
					if(str2.isEmpty()){
						str2=" ";
					}
					if(str3.isEmpty()){
						str3=" ";
					}
					if(str4.isEmpty()){
						str4=" ";
					}
					if(str5.isEmpty()){
						str5=" ";
					}
					if(str6.isEmpty()){
						str6=" ";
					}
					Customer c=new Customer();
					c.setCid(i);
					c.setName(str2);
					c.setContact(str3);
					c.setPhone(str4);
					c.setAddress(str5);
					c.setBz(str6);
					new CustomDao().addCustomersInfo(c);
					ret=c;//返回这个对象；
					CustomAddModelWindow.this.setVisible(false);
				}
				
				
				
			}
		});
		//退出
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomAddModelWindow.this.setVisible(false);
			}
		});
		this.setTitle("增加客户");
		this.setBounds(500,250,360,360);
		this.setModal(true);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new CustomAddModelWindow();
	}
}
