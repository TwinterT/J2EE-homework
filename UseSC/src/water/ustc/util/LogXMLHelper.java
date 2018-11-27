package water.ustc.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class LogXMLHelper {
	
	public static final String ACTION_TAG = "action";
	public static final String LOG_TAG = "log";
	public static final String NAME_TAG = "name";
	public static final String START_TIME_TAG = "s-time";
	public static final String END_TIME_TAG = "e-time";
	public static final String RESULT_TAG = "result";
	
	public static final String INIT_XML_CONTEXT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<log>\n</log>";

	File file;
	
	public LogXMLHelper(String path) throws ParserConfigurationException, TransformerException, IOException {
		file = new File(path);
		if(!file.exists()) {
			createFile();
		}
	}
	
	private void createFile() throws IOException {
		file.createNewFile();
		FileWriter os = new FileWriter(file);
		os.write(INIT_XML_CONTEXT);
		os.flush();
		os.close();
	}
	
	public void appendLogData(String name,String startTime,String endTime,String result) throws ParserConfigurationException, TransformerException, SAXException, IOException {
		if(!file.exists())createFile();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(file);
		
		Element root = (Element) document.getElementsByTagName(LOG_TAG).item(0);
		Element action = document.createElement(ACTION_TAG);
		
		//创建名字结点
		Element nameNode = document.createElement(NAME_TAG);
		nameNode.setTextContent(name);
		
		//创建开始时间结点
		Element startTimeNode = document.createElement(START_TIME_TAG);
		startTimeNode.setTextContent(startTime);
		
		//创建结束时间结点
		Element endTimeNode = document.createElement(END_TIME_TAG);
		endTimeNode.setTextContent(endTime);
		
		//创建结果结点
		Element resultNode = document.createElement(RESULT_TAG);
		resultNode.setTextContent(result);
		
		//处理每个结点的关系
		root.appendChild(action);
		action.appendChild(nameNode);
		action.appendChild(startTimeNode);
		action.appendChild(endTimeNode);
		action.appendChild(resultNode);
		
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transformer = transFactory.newTransformer();
		
		transformer.setOutputProperty("encoding", "utf-8");
		
		DOMSource source = new DOMSource(document);
		
		Result target = new StreamResult(file);
		transformer.transform(source, target);
	}
}
