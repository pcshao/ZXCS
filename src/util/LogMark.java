package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import service.AdminService;


/**
 * 日志功能
 * 	日志在程序所在文件目录的文件夹logs中，请务必保证此文件夹存在并具有可写权限
 * @author pcshao
 * 
 */
public class LogMark {
	
	private static String pathname = System.getProperty("user.dir");	//获取当前程序所在路径
	private static String pattern = "yyyy-MM-dd HH:mm:ss";				//日志日期格式
	private static File file;
	private static PrintWriter pw;
	private static SimpleDateFormat sdf,fdf;
	
	static {
		//格式器初始化
		sdf = new SimpleDateFormat(pattern);
		fdf = new SimpleDateFormat("yyMMdd");
		//日志初始文件初始化
		file = new File(pathname+"\\logs\\"+fdf.format(new Date())+".log");
		try {
			if(!file.exists())
				file.createNewFile();
			pw = new PrintWriter(new FileOutputStream(file,true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void LogWrite(String content) {
		//日志暂定为	当前系统操作员（后台orPOS）+日志描述
		String log = AdminService.admin.getName()+content;
		pw.println(addDate()+log);
		pw.flush();
	}
	public static void LogWrite(String content ,Boolean loginOperate) {
		pw.println(addDate()+content);
		pw.flush();
	}
	
	private static String addDate() {
		return sdf.format(new Date())+" ";
	}
	
}
