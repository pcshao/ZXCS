package gui;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import bean.Depot;
import dao.DepotsDao;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import net.miginfocom.swing.MigLayout;
import service.DepotService;
import util.ImportExportHelp;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.CardLayout;

public class Report_depotsBase extends JDialog{
	
	private JPanel jp_center,jp_chart,panel_1;
	private DefaultTableModel tableModel;
	private JTable table;
	private DateFromToPanel dataHelp;
	private JComboBox<Depot> cobx_depots;
	private Vector<Depot> depots;
	DefaultCategoryDataset dataset;
	JFreeChart chart;
	Vector<Vector> data;
	String[] chartWay = {new String("库存成本"),new String("库存数量"),new String("销售利润")};
	DepotService depotService;

	public Report_depotsBase(JComponent parent) {
		this.setTitle("库存成本统计");
		
		//依赖于传进来的父组件定位
		this.setLocationRelativeTo(parent);
		depotService = new DepotService();
		
		Vector columnNames = new Vector();
		columnNames.add("结算月份");
		columnNames.add("仓库名称");
		columnNames.add("库存数量");
		columnNames.add("库存总成本");
		columnNames.add("进货数量");
		columnNames.add("进货总金额");
		columnNames.add("销售数量");
		columnNames.add("销售总金额");
		columnNames.add("销售利润");
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setBounds(0,0,711, 620);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel jp_top = new JPanel();
		getContentPane().add(jp_top, BorderLayout.NORTH);
		jp_top.setLayout(new MigLayout("", "[684px]", "[23px]"));
		
		JButton button = new JButton("\u5BFC\u51FA");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector<Vector> exportData = new Vector<Vector>();
				if(data==null)
					return;
				exportData.add(columnNames);
				exportData.addAll(data);
				//提示输入路径，之后导出数据
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(JFileChooser.CANCEL_OPTION==chooser.showSaveDialog(Report_depotsBase.this))
					return;
				String export = chooser.getSelectedFile().getAbsolutePath();
				try {
					if(ImportExportHelp.ExportData(exportData, export)!=null)
						new JOptionPane().showMessageDialog(Report_depotsBase.this, "导出成功");
				} catch (RowsExceededException e1) {
					e1.printStackTrace();
				} catch (WriteException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		jp_top.add(button, "flowx,cell 0 0,growx,aligny top");
		
		JButton button_1 = new JButton("\u9000\u51FA");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Report_depotsBase.this.setVisible(false);
			}
		});
		jp_top.add(button_1, "cell 0 0,alignx left,aligny top");
		
		jp_center = new JPanel();
		getContentPane().add(jp_center, BorderLayout.CENTER);
		jp_center.setLayout(new BorderLayout(0, 0));
		
		JPanel jp_search = new JPanel();
		//加载日期选择控件
		jp_search.add(dataHelp = new DateFromToPanel());
		jp_center.add(jp_search, BorderLayout.NORTH);
		jp_search.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel label = new JLabel("\u7EDF\u8BA1\u6708\u4EFD");
		jp_search.add(label);
		
		JLabel label_3 = new JLabel("\u4ED3\u5E93\u540D\u79F0\uFF1A");
		jp_search.add(label_3);
		
		//加载仓库列表
		depots = new DepotsDao().getDepots();
		cobx_depots = new JComboBox(depots);
		jp_search.add(cobx_depots);
		
		JButton button_2 = new JButton("\u67E5\u8BE2");
		jp_search.add(button_2);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		jp_center.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel jp_table = new JPanel();
		tabbedPane.addTab("\u8868\u683C\u663E\u793A", null, jp_table, null);
		
		tableModel = new DefaultTableModel();
		jp_table.setLayout(new GridLayout(0, 1, 0, 0));
		table = new JTable();
		jp_table.add(new JScrollPane(table));
		
		jp_chart = new JPanel();
		tabbedPane.addTab("\u56FE\u6807\u663E\u793A", null, jp_chart, null);
		this.setVisible(true);
		
		//事件
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * 出口数据
				 * 	从DateFromToPanel窗口取到成员
				 */
				String from = dataHelp.getSpin_from_year()+dataHelp.getSpin_from_day();
				String to = dataHelp.getSpin_to_year()+dataHelp.getSpin_to_day();
				String depotName = ((Depot)cobx_depots.getSelectedItem()).getName();
				if((data = depotService.getDepotCostThroughMonths(from,to,depotName))!=null)
					tableModel = new DefaultTableModel(data, columnNames);
				table.setModel(tableModel);
				table.updateUI();
			}
		});
	     jp_chart.setLayout(new BorderLayout(0, 0));
	     
	     JPanel jp_chartWay = new JPanel();
	     jp_chart.add(jp_chartWay, BorderLayout.NORTH);
	     jp_chartWay.setLayout(new BorderLayout(0, 0));
	     
	     JPanel panel = new JPanel();
	     jp_chartWay.add(panel, BorderLayout.EAST);
	     panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	     
	     JLabel label_1 = new JLabel("\u7EDF\u8BA1\u5185\u5BB9\uFF1A");
	     panel.add(label_1);
	     
	     JComboBox cobx_chartWay = new JComboBox(chartWay);
	     panel.add(cobx_chartWay);
	     
			//图表显示
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			chart = ChartFactory.createBarChart3D(
					"库存成本统计", "月份", "库存成本", dataset, PlotOrientation.VERTICAL, true, false, false);
		    CategoryPlot plot=chart.getCategoryPlot();//获取图表区域对象 
		    CategoryAxis domainAxis=plot.getDomainAxis();     //水平底部列表 
		     domainAxis.setLabelFont(new Font("黑体",Font.BOLD,14));     //水平底部标题 
		     domainAxis.setTickLabelFont(new Font("宋体",Font.BOLD,12)); //垂直标题 
		     ValueAxis rangeAxis=plot.getRangeAxis();//获取柱状 
		     rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15)); 
		     chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15)); 
		     chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体 
		     jp_chart.updateUI();
	     
	     panel_1 = new JPanel();
	     jp_chart.add(panel_1, BorderLayout.CENTER);
	     panel_1.setLayout(new GridLayout(0, 1, 0, 0));
	     ChartPanel chartPanel = new ChartPanel(chart);
	     panel_1.add(chartPanel);
	     
	     cobx_chartWay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String type = (String) cobx_chartWay.getSelectedItem();
				int len = data.size();
				double[] y = new double[len];
				String[] x = new String[len];
				if("库存成本".equals(type)) {
					for(int i=0;i<len;i++) {
						x[i] = (String) data.get(i).get(0);
						if(data.get(i).get(3)==null)
							y[i]=0;
						else
						y[i] = Double.parseDouble(data.get(i).get(3).toString());
					}
				}else if("库存数量".equals(type)) {
					for(int i=0;i<len;i++) {
						x[i] = (String) data.get(i).get(0);
						if(data.get(i).get(2)==null)
							y[i]=0;
						else
						y[i] = Double.parseDouble(data.get(i).get(2).toString());
					}
				}else if("销售利润".equals(type)) {
					for(int i=0;i<len;i++) {
						x[i] = (String) data.get(i).get(0);
						if(data.get(i).get(8)==null)
							y[i]=0;
						else
						y[i] = Double.parseDouble(data.get(i).get(8).toString());
					}
				}
				refreshChart(y, x, type);
			}
		});
	     //Echart
	}
	private void refreshChart(double[] y, String[] x, String type ) {
		dataset = new DefaultCategoryDataset();
		for(int i=0;i<x.length;i++) 
			dataset.addValue(y[i], type, x[i]);
		//图表显示
		chart = ChartFactory.createBarChart3D(
				type+"统计", "时间区间", type, dataset, PlotOrientation.VERTICAL, true, false, false);
	    CategoryPlot plot=chart.getCategoryPlot();//获取图表区域对象 
	    CategoryAxis domainAxis=plot.getDomainAxis();     //水平底部列表 
	     domainAxis.setLabelFont(new Font("黑体",Font.BOLD,14));     //水平底部标题 
	     domainAxis.setTickLabelFont(new Font("宋体",Font.BOLD,12)); //垂直标题 
	     ValueAxis rangeAxis=plot.getRangeAxis();//获取柱状 
	     rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15)); 
	     chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15)); 
	     chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体 
	     panel_1.removeAll();
	     panel_1.add(new ChartPanel(chart));
	     panel_1.updateUI();
	}
	
	public static void main(String[] args) {
		new Report_depotsBase(null);
	}
}
