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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import util.*;
import dao.*;
import service.*;
import bean.*;
import bean.orders.*;

public class LoginUI extends JFrame{

	public static final String BKPATH = "/img/bk1.jpg";
	private JLabel lab_name,lab_passwd;
	private JTextField jtf_count;
	private JPasswordField jtf_passwd;
	private JButton btn_login,btn_reset;
	private JPanel jp_title,jp_count,jp_passwd,jp_function;
	
	private AdminService adminService;
	
	public LoginUI(String title) {
		super(title);
		adminService = new AdminService();
		
		lab_name = new JLabel("�˻���:");
		lab_passwd = new JLabel("\u5BC6  \u7801:");
		jtf_count = new JTextField(15);
		jtf_count.setText("admin");
		jtf_passwd = new JPasswordField(15);
		btn_login = new JButton("��¼");
		btn_reset = new JButton("����");
		jp_title = new JPanel();
		jp_count = new JPanel();
		jp_passwd = new JPanel();
		jp_function = new JPanel();
		
		getContentPane().setLayout(new GridLayout(4, 1));
		
		jp_title.add(new JLabel("�û���¼"),BorderLayout.SOUTH);
		jp_count.add(lab_name);jp_count.add(jtf_count);
		jp_passwd.add(lab_passwd);jp_passwd.add(jtf_passwd);
		jp_function.add(btn_login);jp_function.add(btn_reset);
		
		getContentPane().add(jp_title);
		getContentPane().add(jp_count);
		getContentPane().add(jp_passwd);
		getContentPane().add(jp_function);
		
		this.setBounds(500, 200, 300, 350);
//		this.pack();����Ӧ
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btn_login.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * ��¼У��
				 */
				if(adminService.Login(jtf_count.getText().trim(),jtf_passwd.getText().trim())) {
					LoginUI.this.setVisible(false);
					new JOptionPane().showMessageDialog(LoginUI.this, "��ӭ��\n"+adminService.admin.toWelcome());
					new MainUI();
				} else{
					new JOptionPane().showMessageDialog(LoginUI.this,"��¼ʧ��");
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
	 * �Զ������õ�¼�������
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
		SwingUtilities.invokeLater ( new Runnable ()
        {
            public void run ()
            {
            	 try {
					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
				} catch (Exception e) {
					e.printStackTrace();
				}
            	new LoginUI("zxcs���й���ϵͳ");
            }
        } );
	}
}