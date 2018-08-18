package util;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

import dao.GoodsDao;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

/**
 * 数据导入模块
 * 	入口数据 excel .jxl
 * 	出口数据 Vector
 * @author pcshao
 *  2018-08-08
 *  2018-08-09
 *  系统桌面绝对路径
 *  FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()
 */
public class ImportExportHelp extends JDialog{

	private JPanel jp_left,jp_right,jp_btn,jp_stage;
	private JButton btn_Excel,btn_open,btn_pre,btn_next,btn_cancel,btn_alldone;
	private File templeateFile;
	private File importFile;
	private String templeateFilePath = "templeate/zxcs.xls";
	public Vector<Vector> data; //操作完成从这取导入的数据

	public ImportExportHelp() {
		this.setTitle("数据导入模块");
		
		this.setLocation(0, 0);
		this.setSize(700, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		jp_left = new JPanel();
		getContentPane().add(jp_left, BorderLayout.WEST);

		jp_right = new JPanel();
		getContentPane().add(jp_right, BorderLayout.CENTER);
		jp_right.setLayout(new BorderLayout(0, 0));
		
		JLabel label = new JLabel("\u6570\u636E\u64CD\u4F5C");
		jp_left.add(label);
		
		//按钮区
		jp_btn = new JPanel();
		jp_btn.add(btn_Excel = new JButton("Excel\u6A21\u677F\u4E0B\u8F7D"));
		jp_btn.add(btn_open = new JButton("\u6253\u5F00\u6587\u4EF6"));
		jp_btn.add(btn_pre = new JButton("上一步"));
		jp_btn.add(btn_next = new JButton("\u4E0B\u4E00\u6B65"));
		jp_btn.add(btn_cancel = new JButton("\u53D6\u6D88"));
		btn_pre.setEnabled(false);
		btn_next.setEnabled(false);
		jp_right.add(jp_btn, BorderLayout.SOUTH);
		//完成
		btn_alldone = new JButton("完成");
		btn_alldone.setEnabled(false);
		jp_btn.add(btn_alldone);
		btn_alldone.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ImportExportHelp.this.setVisible(false);
			}
		});
		
		//第一步  
		jp_right.add(jp_stage = new First(), BorderLayout.CENTER);
		//打开文件
		btn_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setFileFilter(new ExcelFileFilter());
				chooser.showOpenDialog(ImportExportHelp.this);
				importFile = chooser.getSelectedFile();
				if(importFile!=null&&importFile.canRead()) {
					btn_next.setEnabled(true);
					//
					data = TransExcelToVector(importFile);
//					for(Vector v : data)
//						System.out.println(v);
				}
			}
		});
		//上一步
		btn_pre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jp_stage.removeAll();
				jp_stage.add(new First(), BorderLayout.CENTER);
				jp_stage.updateUI();
				btn_next.setEnabled(false);
				btn_pre.setEnabled(false);
			}
		});
		//下一步
		btn_next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				data = checkIsRightData(data, new GoodsDao().getGoodsMainInfoByVector());
				//增加标题列
				data.get(0).add("提示信息");
				//去除展示数据的第一行多余标题
				Vector<Vector> showData = (Vector<Vector>) data.clone();
				showData.remove(0);
				//显示
				jp_stage.removeAll();
				jp_stage.add(new Second(showData, data.firstElement()),BorderLayout.CENTER);
				jp_stage.updateUI();
				btn_pre.setEnabled(true);
				btn_next.setEnabled(false);
				btn_alldone.setEnabled(true);
			}
		});
		//取消
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(0==JOptionPane.showConfirmDialog(ImportExportHelp.this, "是否确认退出?","",0))
					ImportExportHelp.this.setVisible(false);
			}
		});
		//下载模板文件
		btn_Excel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				templeateFile = new File(templeateFilePath);
				if(!templeateFile.exists()) {
					JOptionPane.showMessageDialog(ImportExportHelp.this, "本地Excel模板文件不存在，请联系管理员");
					return;
				}
				 JFileChooser save = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath());
				 save.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				 save.showSaveDialog(ImportExportHelp.this);
				 //
				 File outFile = save.getSelectedFile();
				 if(outFile==null)
					 return;
				 outFile = new File(outFile.getAbsolutePath()+"/导入模板文件.xls");
				 if(!outFile.exists()) {
					try {
						outFile.createNewFile();
						fileCopyTo(templeateFile, outFile);
						new JOptionPane().showMessageDialog(ImportExportHelp.this, "保存成功\n"+outFile.getAbsolutePath());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				 }else {
					 new JOptionPane().showMessageDialog(ImportExportHelp.this, "所选路径文件已存在");
				 }
			}
		});
		
		this.setModal(true);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		ImportExportHelp a =new ImportExportHelp();
		for(Vector v:a.data)
			System.out.println(v);
	}
	/**
	 * 读取excel文件
	 * 	输出Vector
	 */
	public static Vector<Vector> TransExcelToVector(File excel){
		Vector<Vector> ret = new Vector<>();
		Vector v = new Vector();
		Workbook wb_in = null;
		Sheet[] sheets;
		Cell[] cells;
		try {
			wb_in = Workbook.getWorkbook(excel);
		} catch (Exception e) {
			
		}
		if(wb_in==null)
			return null;
		//获得了Workbook对象之后，就可以通过它得到Sheet（工作表）对象了
        sheets = wb_in.getSheets();
        cells = sheets[0].getRow(0);
		if(sheets!=null&&sheets.length>0){
			//对每个工作表进行循环 这里只读第一张sheet
			for(int i=0;i<1;i++){
			    //得到当前工作表的行数
				for(int j=0;j<sheets[i].getRows();j++){
				    //得到当前行的所有单元格
					cells = sheets[i].getRow(j);
					if(cells!=null&&cells.length>0){
					    //对每个单元格进行循环
						v = new Vector();
						for(int k=0;k<cells.length;k++){
						    //读取当前单元格的值
							v.add(cells[k].getContents());
						}
						ret.add(v);
				    }
		        }
		    }
		}
        //最后关闭资源，释放内存     
		wb_in.close();	
		return ret;
	}
	/**
	 * 导出数据帮助
	 *  传入Vector<Vector> ExportData
	 *  传入String 输出文件的路径（目录）
	 *  返回File对象 excel
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	public static File ExportData(Vector<Vector> exportData, String exportPath) throws IOException, RowsExceededException, WriteException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		File ret = new File(exportPath+"/"+sdf.format(new Date())+"导出数据.xls");
		if(!ret.exists())
			ret.createNewFile();
		WritableWorkbook wwb = Workbook.createWorkbook(ret);
		WritableSheet wsheet = wwb.createSheet("商品数据", 0);
		Label label;
		//写入
		int rows = exportData.size();
		int cols = exportData.get(0).size();
		for(int j=0;j<rows;j++) {
			for(int i=0;i<cols;i++) {
				Object a = exportData.get(j).get(i);
				if(a==null)
					a = "";
				label = new Label(i, j, a.toString());
				wsheet.addCell(label);
			}
		}
		wwb.write();
		if(wwb!=null)
			wwb.close();
		return ret;
	}
	/**
	 * 检查数据是否可导入
	 * 	检查商品编号
	 * 	检查商品名称
	 * 	返回原始Vector<Vector>增加新一列标识
	 */
	private static Vector<Vector> checkIsRightData(Vector<Vector> in, Vector<Vector> standard) {
		String a1 = "商品不存在",a2 = "数据存在问题",a3 = "可导入";
		Boolean isExist = false;
		//从in的第二行即下标1开始
		for(int j=1;j<in.size();j++) {
			isExist = false;
			for(int i=0;i<standard.size();i++) {
				//判断编号是不是在standard里
				if(standard.get(i).get(0).toString().equals(in.get(j).get(0).toString())) {
					isExist = true;
					//判断名称是否相等
					if(standard.get(i).get(1).equals(in.get(j).get(1))){
						in.get(j).add(a3);
					}
					in.get(j).add(a2);
				}
			}
			if(!isExist)
				in.get(j).add(a1);
		}
		return in;
	}
	/**
	 * 文件拷贝
	 * @throws IOException 
	 */
	private void fileCopyTo(File file, File outFile) throws IOException {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		 //文件拷贝
		fis = new FileInputStream(file);
		fos = new FileOutputStream(outFile);
		byte[] b = new byte[512];
		int c = 0;
	    while((c=fis.read(b, 0, b.length))!=-1)
	    	fos.write(b, 0, c);
		if(fos!=null)
			fos.close();
		if(fis!=null)
			fis.close();
	}

}
class Second extends JPanel{
	public Second(Vector data, Vector columnNames) {
		//第二步 数据检查、导入
		JPanel second = new JPanel();
		second.setBorder(BorderFactory.createTitledBorder("第二步（数据检查、导入）"));
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		JTable table = new JTable(model);
		table.setEnabled(false);
		this.add(new JScrollPane(table));
	}
}
class First extends JPanel{
	public First() {
		//第一步
		JPanel first = new JPanel();
		first.setBorder(BorderFactory.createTitledBorder("第一步（准备导入数据）"));
		
		JTextArea textArea = new JTextArea();
		textArea.setText("操作说明：\n\t1、编辑处理您要导入的Excel数据文件。格式必须和*软件提供的Excel模板*中数据的格式一致。否则，会导致导入操作失败。"
				+ "\n\t2、点击“打开文件”按钮，留蓝打开整理好的Excel数据文件。点击“下一步”按钮，进入下个操作步骤的面板。"
				+ "\n\t3、在该面板上，每行数据的第一列的符号，将标示出尼玛币。"
				+ "\n\t4、导入操作完成。点击“完成”按钮，结束当前操作。");
		textArea.setColumns(50);
		textArea.setRows(10);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		first.add(textArea);
		this.add(first);
	}
}
class ExcelFileFilter extends FileFilter {    
    public String getDescription() {    
        return "*.xls;//*.xlsx";    
    }    
    
    public boolean accept(File file) {    
        String name = file.getName();    
        return file.isDirectory() || name.toLowerCase().endsWith(".xls") || name.toLowerCase().endsWith(".xlsx");  // 仅显示目录和xls、xlsx文件  
    }    
}