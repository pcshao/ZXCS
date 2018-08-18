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
 * ���ݵ���ģ��
 * 	������� excel .jxl
 * 	�������� Vector
 * @author pcshao
 *  2018-08-08
 *  2018-08-09
 *  ϵͳ�������·��
 *  FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()
 */
public class ImportExportHelp extends JDialog{

	private JPanel jp_left,jp_right,jp_btn,jp_stage;
	private JButton btn_Excel,btn_open,btn_pre,btn_next,btn_cancel,btn_alldone;
	private File templeateFile;
	private File importFile;
	private String templeateFilePath = "templeate/zxcs.xls";
	public Vector<Vector> data; //������ɴ���ȡ���������

	public ImportExportHelp() {
		this.setTitle("���ݵ���ģ��");
		
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
		
		//��ť��
		jp_btn = new JPanel();
		jp_btn.add(btn_Excel = new JButton("Excel\u6A21\u677F\u4E0B\u8F7D"));
		jp_btn.add(btn_open = new JButton("\u6253\u5F00\u6587\u4EF6"));
		jp_btn.add(btn_pre = new JButton("��һ��"));
		jp_btn.add(btn_next = new JButton("\u4E0B\u4E00\u6B65"));
		jp_btn.add(btn_cancel = new JButton("\u53D6\u6D88"));
		btn_pre.setEnabled(false);
		btn_next.setEnabled(false);
		jp_right.add(jp_btn, BorderLayout.SOUTH);
		//���
		btn_alldone = new JButton("���");
		btn_alldone.setEnabled(false);
		jp_btn.add(btn_alldone);
		btn_alldone.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ImportExportHelp.this.setVisible(false);
			}
		});
		
		//��һ��  
		jp_right.add(jp_stage = new First(), BorderLayout.CENTER);
		//���ļ�
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
		//��һ��
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
		//��һ��
		btn_next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				data = checkIsRightData(data, new GoodsDao().getGoodsMainInfoByVector());
				//���ӱ�����
				data.get(0).add("��ʾ��Ϣ");
				//ȥ��չʾ���ݵĵ�һ�ж������
				Vector<Vector> showData = (Vector<Vector>) data.clone();
				showData.remove(0);
				//��ʾ
				jp_stage.removeAll();
				jp_stage.add(new Second(showData, data.firstElement()),BorderLayout.CENTER);
				jp_stage.updateUI();
				btn_pre.setEnabled(true);
				btn_next.setEnabled(false);
				btn_alldone.setEnabled(true);
			}
		});
		//ȡ��
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(0==JOptionPane.showConfirmDialog(ImportExportHelp.this, "�Ƿ�ȷ���˳�?","",0))
					ImportExportHelp.this.setVisible(false);
			}
		});
		//����ģ���ļ�
		btn_Excel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				templeateFile = new File(templeateFilePath);
				if(!templeateFile.exists()) {
					JOptionPane.showMessageDialog(ImportExportHelp.this, "����Excelģ���ļ������ڣ�����ϵ����Ա");
					return;
				}
				 JFileChooser save = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath());
				 save.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				 save.showSaveDialog(ImportExportHelp.this);
				 //
				 File outFile = save.getSelectedFile();
				 if(outFile==null)
					 return;
				 outFile = new File(outFile.getAbsolutePath()+"/����ģ���ļ�.xls");
				 if(!outFile.exists()) {
					try {
						outFile.createNewFile();
						fileCopyTo(templeateFile, outFile);
						new JOptionPane().showMessageDialog(ImportExportHelp.this, "����ɹ�\n"+outFile.getAbsolutePath());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				 }else {
					 new JOptionPane().showMessageDialog(ImportExportHelp.this, "��ѡ·���ļ��Ѵ���");
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
	 * ��ȡexcel�ļ�
	 * 	���Vector
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
		//�����Workbook����֮�󣬾Ϳ���ͨ�����õ�Sheet��������������
        sheets = wb_in.getSheets();
        cells = sheets[0].getRow(0);
		if(sheets!=null&&sheets.length>0){
			//��ÿ�����������ѭ�� ����ֻ����һ��sheet
			for(int i=0;i<1;i++){
			    //�õ���ǰ�����������
				for(int j=0;j<sheets[i].getRows();j++){
				    //�õ���ǰ�е����е�Ԫ��
					cells = sheets[i].getRow(j);
					if(cells!=null&&cells.length>0){
					    //��ÿ����Ԫ�����ѭ��
						v = new Vector();
						for(int k=0;k<cells.length;k++){
						    //��ȡ��ǰ��Ԫ���ֵ
							v.add(cells[k].getContents());
						}
						ret.add(v);
				    }
		        }
		    }
		}
        //���ر���Դ���ͷ��ڴ�     
		wb_in.close();	
		return ret;
	}
	/**
	 * �������ݰ���
	 *  ����Vector<Vector> ExportData
	 *  ����String ����ļ���·����Ŀ¼��
	 *  ����File���� excel
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	public static File ExportData(Vector<Vector> exportData, String exportPath) throws IOException, RowsExceededException, WriteException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		File ret = new File(exportPath+"/"+sdf.format(new Date())+"��������.xls");
		if(!ret.exists())
			ret.createNewFile();
		WritableWorkbook wwb = Workbook.createWorkbook(ret);
		WritableSheet wsheet = wwb.createSheet("��Ʒ����", 0);
		Label label;
		//д��
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
	 * ��������Ƿ�ɵ���
	 * 	�����Ʒ���
	 * 	�����Ʒ����
	 * 	����ԭʼVector<Vector>������һ�б�ʶ
	 */
	private static Vector<Vector> checkIsRightData(Vector<Vector> in, Vector<Vector> standard) {
		String a1 = "��Ʒ������",a2 = "���ݴ�������",a3 = "�ɵ���";
		Boolean isExist = false;
		//��in�ĵڶ��м��±�1��ʼ
		for(int j=1;j<in.size();j++) {
			isExist = false;
			for(int i=0;i<standard.size();i++) {
				//�жϱ���ǲ�����standard��
				if(standard.get(i).get(0).toString().equals(in.get(j).get(0).toString())) {
					isExist = true;
					//�ж������Ƿ����
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
	 * �ļ�����
	 * @throws IOException 
	 */
	private void fileCopyTo(File file, File outFile) throws IOException {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		 //�ļ�����
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
		//�ڶ��� ���ݼ�顢����
		JPanel second = new JPanel();
		second.setBorder(BorderFactory.createTitledBorder("�ڶ��������ݼ�顢���룩"));
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		JTable table = new JTable(model);
		table.setEnabled(false);
		this.add(new JScrollPane(table));
	}
}
class First extends JPanel{
	public First() {
		//��һ��
		JPanel first = new JPanel();
		first.setBorder(BorderFactory.createTitledBorder("��һ����׼���������ݣ�"));
		
		JTextArea textArea = new JTextArea();
		textArea.setText("����˵����\n\t1���༭������Ҫ�����Excel�����ļ�����ʽ�����*����ṩ��Excelģ��*�����ݵĸ�ʽһ�¡����򣬻ᵼ�µ������ʧ�ܡ�"
				+ "\n\t2����������ļ�����ť������������õ�Excel�����ļ����������һ������ť�������¸������������塣"
				+ "\n\t3���ڸ�����ϣ�ÿ�����ݵĵ�һ�еķ��ţ�����ʾ������ҡ�"
				+ "\n\t4�����������ɡ��������ɡ���ť��������ǰ������");
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
        return file.isDirectory() || name.toLowerCase().endsWith(".xls") || name.toLowerCase().endsWith(".xlsx");  // ����ʾĿ¼��xls��xlsx�ļ�  
    }    
}