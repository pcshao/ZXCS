package gui;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import java.awt.FlowLayout;

/**
 * 快速选择年月跨度
 * 	get方法返回选择的值(int)
 * @author pcshao
 * 2018-080=-09
 */
public class DateFromToPanel extends JPanel{
	
	private JPanel jp_search;
	private JSpinner spin_from_year;
	private JSpinner spin_from_day;
	private JSpinner spin_to_year;
	private JSpinner spin_to_day;

	public DateFromToPanel() {
		
		SimpleDateFormat sdfY = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdfM = new SimpleDateFormat("MM");
		Date date = new Date();
		
		jp_search = new JPanel();
		jp_search.setBorder(BorderFactory.createTitledBorder("选择日期控件"));
		jp_search.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		spin_from_year = new JSpinner();
		spin_from_year.setModel(new SpinnerNumberModel(Integer.parseInt(sdfY.format(date)), null, null, new Integer(1)));
		jp_search.add(spin_from_year);
		
		JLabel label_1 = new JLabel("\u5E74");
		jp_search.add(label_1);
		
		spin_from_day = new JSpinner();
		jp_search.add(spin_from_day);
		spin_from_day.setModel(new SpinnerNumberModel(Integer.parseInt(sdfM.format(date)), 1, 12, 1));
		
		JLabel label_2 = new JLabel("\u6708 \u81F3");
		jp_search.add(label_2);
		
		spin_to_year = new JSpinner();
		spin_to_year.setModel(new SpinnerNumberModel(Integer.parseInt(sdfY.format(date)), null, null, new Integer(1)));
		jp_search.add(spin_to_year);
		
		JLabel lblNewLabel = new JLabel("\u5E74");
		jp_search.add(lblNewLabel);
		
		spin_to_day = new JSpinner();
		spin_to_day.setModel(new SpinnerNumberModel(Integer.parseInt(sdfM.format(date)), 1, 12, 1));
		jp_search.add(spin_to_day);
		
		this.add(jp_search);
		
	}
	
	public String getSpin_from_year() {
		return  spin_from_year.getValue().toString() ;
	}

	public String getSpin_from_day() {
		String ret = spin_from_day.getValue().toString();
		return  ret.length()==2?ret:"0"+ret;
	}

	public String getSpin_to_year() {
		return  spin_to_year.getValue().toString() ;
	}

	public String getSpin_to_day() {
		String ret = spin_to_day.getValue().toString();
		return ret.length()==2?ret:"0"+ret;
	}
}
