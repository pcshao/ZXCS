package gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.BorderLayout;

public class Report_ParentUI extends JFrame {
	private JPanel jp_1,jp_2,jp_3,jp_4;
	private JPanel panel;
	private JPanel panel_1;

	public Report_ParentUI(String title) {
		setTitle(title);
		
		
		this.setBounds(0,0,800,600);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane_1);
		
		jp_1 = new JPanel();
		tabbedPane_1.addTab("New tab", null, jp_1, null);
		jp_1.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		jp_1.add(panel_1, BorderLayout.NORTH);
		
		panel = new JPanel();
		jp_1.add(panel, BorderLayout.CENTER);
		jp_2 = new JPanel();
		tabbedPane_1.addTab("New tab", null, jp_2, null);
		jp_3 = new JPanel();
		tabbedPane_1.addTab("New tab", null, jp_3, null);
		jp_4 = new JPanel();
		tabbedPane_1.addTab("New tab", null, jp_4, null);
		
		
		this.setVisible(true);
	}
}
