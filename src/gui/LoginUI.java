package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.*;
import dao.*;
import service.*;
import bean.*;
import bean.orders.*;;


public class LoginUI extends JFrame{

	private JLabel lab_name,lab_passwd;
	private JTextField jtf_count;
	private JPasswordField jtf_passwd;
	private JButton btn_login,btn_reset;
	private JPanel jp_title,jp_count,jp_passwd,jp_function;
	
	private AdminService adminService;
	
	public LoginUI(String title) {
		super(title);
		adminService = new AdminService();
		
		lab_name = new JLabel("账户名:");
		lab_passwd = new JLabel("密    码:");
		jtf_count = new JTextField(15);
		jtf_count.setText("admin");
		jtf_passwd = new JPasswordField(15);
		btn_login = new JButton("登录");
		btn_reset = new JButton("重置");
		jp_title = new JPanel();
		jp_count = new JPanel();
		jp_passwd = new JPanel();
		jp_function = new JPanel();
		
		getContentPane().setLayout(new GridLayout(4, 1));
		
		jp_title.add(new JLabel("用户登录"),BorderLayout.SOUTH);
		jp_count.add(lab_name);jp_count.add(jtf_count);
		jp_passwd.add(lab_passwd);jp_passwd.add(jtf_passwd);
		jp_function.add(btn_login);jp_function.add(btn_reset);
		
		getContentPane().add(jp_title);
		getContentPane().add(jp_count);
		getContentPane().add(jp_passwd);
		getContentPane().add(jp_function);
		
		this.setBounds(500, 200, 300, 350);
//		this.pack();自适应
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btn_login.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * 登录校验
				 */
				if(adminService.Login(jtf_count.getText().trim(),jtf_passwd.getText().trim())) {
					LoginUI.this.setVisible(false);
					new JOptionPane().showMessageDialog(LoginUI.this, "欢迎您\n"+adminService.admin.toWelcome());
					new MainUI();
				} else{
					new JOptionPane().showMessageDialog(LoginUI.this,"登录失败");
				}
			}
		});
		btn_reset.addActionListener(new ResetActionListener());
		jtf_passwd.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar()==KeyEvent.VK_ENTER)
					btn_login.doClick();
			}
		});
	}

	/**
	 * 自定义重置登录框监听器
	 * @author pcshao
	 */
	class ResetActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			jtf_count.setText("");
			jtf_passwd.setText("");
		}
	}
	
	public static void main(String[] args) {
		new LoginUI("zxcs超市管理系统");
	}
}