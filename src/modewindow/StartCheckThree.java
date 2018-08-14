package modewindow;





import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StartCheckThree extends JDialog{

	JPanel jp_top,jp_center,jp_low,jp_center_right_top,
	jp_center_left,jp_center_right,jp_center_left_top;
	JPanel jp_top1,jp_top2,jp_top3;
	JButton btn1,btn2,btn3,btn4;
	
	JTable table;
	DefaultTableModel model;
	Vector<Vector> rowData=new Vector<Vector>();	
	Vector columnNames=new Vector();
	public   StartCheckThree(){
		columnNames.add("商品编号");
		columnNames.add("商品名称");
		columnNames.add("单位");
		columnNames.add("盘点数量");
		columnNames.add("库存数量");
		columnNames.add("差异数量");
		columnNames.add("所在仓库");
		//columnNames.add("备注");
		model=new DefaultTableModel(rowData, columnNames);
		table=new JTable(model);
		
		btn1=new JButton("上一步");
	
		btn2=new JButton("修正");
		btn3=new JButton("退出");
		
		jp_top=new JPanel();
		jp_center=new JPanel();
		jp_low=new JPanel();
		
		jp_top.add(new JLabel("第三步:盘点数量-库存数量=差异数量，根据差异情况修正库存"));
		jp_center.setBorder(BorderFactory.createTitledBorder("盘点差异表"));
		jp_center.add(new JScrollPane(table));
		jp_low.add(btn1);
		jp_low.add(btn2);
		jp_low.add(btn3);
		
		
		
		btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//上一步
			}
		});
	btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//下一步
			}
		});
	btn3.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//下一步
			System.exit(0);
		}
	});
	
		
		
		this.setLayout(new BorderLayout());	
		this.add(jp_top,BorderLayout.NORTH);
		this.add(jp_center,BorderLayout.CENTER);
		this.add(jp_low,BorderLayout.SOUTH);
		this.setTitle("盘盈盘亏");
		this.setBounds(100, 100, 600, 600);
		//this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new StartCheckThree();
	}
		
}
