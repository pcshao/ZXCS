package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.border.EmptyBorder;


public class PassWordManageEditDialog extends JDialog{

	JButton btn_ok,btn_cancel;
	JTextField tf_id,tf_name,tf_password,tf_job;
	private String password;
	
	public String getPassword() {
		return password;
	}

	public PassWordManageEditDialog(Vector selected) {
		setTitle("√‹¬Îπ‹¿Ì");
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel jp_north = new JPanel();
		getContentPane().add(jp_north, BorderLayout.NORTH);
		
		JPanel jp_title = new JPanel();
		jp_north.add(jp_title);
		
		JLabel lblNewLabel = new JLabel("\u5BC6\u7801\u7BA1\u7406");
		jp_title.add(lblNewLabel);
		
		JPanel jp_center = new JPanel();
		getContentPane().add(jp_center, BorderLayout.CENTER);
		jp_center.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel jp_content_l = new JPanel();
		jp_content_l.setBorder(new EmptyBorder(15, 5, 15, 0));
		jp_center.add(jp_content_l);
		jp_content_l.setLayout(new GridLayout(4, 1, 0, 0));
		
		JPanel panel = new JPanel();
		jp_content_l.add(panel);
		
		JLabel label_1 = new JLabel("\u7F16\u53F7");
		panel.add(label_1);
		
		
		JPanel panel_1 = new JPanel();
		jp_content_l.add(panel_1);
		
		JLabel label_2 = new JLabel("\u540D\u79F0");
		panel_1.add(label_2);
		
		
		JPanel panel_2 = new JPanel();
		jp_content_l.add(panel_2);
		
		JLabel label_3 = new JLabel("\u5BC6\u7801");
		panel_2.add(label_3);
		
		JPanel panel_3 = new JPanel();
		jp_content_l.add(panel_3);
		
		JLabel label_4 = new JLabel("\u804C\u52A1");
		panel_3.add(label_4);
		
		JPanel jp_content_r = new JPanel();
		jp_content_r.setBorder(new EmptyBorder(15, 0, 15, 10));
		jp_center.add(jp_content_r);
		jp_content_r.setLayout(new GridLayout(4, 1, 0, 0));
		
		JPanel panel_5 = new JPanel();
		jp_content_r.add(panel_5);
		panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		tf_id = new JTextField();
		panel_5.add(tf_id);
		tf_id.setColumns(10);
		
		JPanel panel_11 = new JPanel();
		jp_content_r.add(panel_11);
		
		tf_name = new JTextField();
		panel_11.add(tf_name);
		tf_name.setColumns(10);
		
		JPanel panel_7 = new JPanel();
		jp_content_r.add(panel_7);
		
		tf_password = new JTextField();
		panel_7.add(tf_password);
		tf_password.setColumns(10);
		
		JPanel panel_8 = new JPanel();
		jp_content_r.add(panel_8);
		
		tf_job = new JTextField();
		panel_8.add(tf_job);
		tf_job.setColumns(10);
		
		JPanel jp_south = new JPanel();
		getContentPane().add(jp_south, BorderLayout.SOUTH);
		
		JButton btn_confirm = new JButton("\u786E\u5B9A");
		jp_south.add(btn_confirm);
		btn_confirm.setHorizontalAlignment(SwingConstants.LEFT);
		
		JButton btn_cancel = new JButton("\u53D6\u6D88");
		jp_south.add(btn_cancel);
		btn_cancel.setHorizontalAlignment(SwingConstants.RIGHT);
		//∞¥≈•
		btn_confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				password = tf_password.getText().trim();
				PassWordManageEditDialog.this.setVisible(false);
			}
		});
		btn_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PassWordManageEditDialog.this.setVisible(false);
			}
		});
		//
		tf_id.setText(selected.get(0).toString());
		tf_id.setEditable(false);
		tf_name.setText(selected.get(1).toString());
		tf_name.setEditable(false);
		tf_password.setText(selected.get(2).toString());
		tf_job.setText(selected.get(3).toString());
		tf_job.setEditable(false);
		
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setBounds(0, 0, 300, 350);
		this.setLocationRelativeTo(null);
		
		this.setModal(true);
		this.setVisible(true);
	}
}
