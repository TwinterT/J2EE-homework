package water.ustc.interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import cn.T.winter.Interface.LogWriteInterface;
import sc.ustc.items.Action;
import water.ustc.util.LogXMLHelper;;

public class LogInterceptor implements LogWriteInterface{
	
	public static final String LOG_XML_FILE_NAME = "log.xml";

	private LogXMLHelper helper;
	private String name;
	private String startTime;
	private String endTime;
	private String result;
	
	//在action之前的处理
	@Override
	public void preAction(Action action) {
		String path = this.getClass().getClassLoader().getResource("/").getPath().replaceFirst("/", "");
		
		//将path的编码变成utf-8的编码 防止空格出现%20的情况
		try {
			path = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println(path+LOG_XML_FILE_NAME);
		
		//初始化log编辑器
		try {
			helper = new LogXMLHelper(path+LOG_XML_FILE_NAME);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//初始化某些属性
		name = action.getName();

		startTime = new Date().toString();
		
		System.out.println("preAction:done!!!!!!!!!");
	}
	
	
	//在action之后的处理
	@Override
	public void afterAction(Action action) {
		endTime = new Date().toString();
		
		result = action.getResult();
		
		if(helper!=null && name!=null && startTime !=null && endTime !=null && result!=null) {
			try {
				helper.appendLogData(name, startTime, endTime, result);
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Writting log done!");
		}
		else {
			System.out.println("Writting log failed!");
		}
		
	}


}
