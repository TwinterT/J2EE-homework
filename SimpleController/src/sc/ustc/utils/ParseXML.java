package sc.ustc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParseXML {

	public static final String ACTION_TAG = "action";
	public static final String NAME_TAG = "name";
	public static final String METHOD_TAG = "method";
	public static final String CLASS_TAG = "class";
	
	public static final String RESULT_TAG = "result";
	public static final String TYPE_TAG = "type";
	public static final String VALUE_TAG = "value";
	
	private String xmlPath;
	private Document document;
	
	public ParseXML(String xmlPath,ServletContext context)throws ParserConfigurationException, SAXException, IOException {
		this.xmlPath = xmlPath;
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder domBuilder = domfac.newDocumentBuilder();
		InputStream is = context.getResourceAsStream(xmlPath);
		document = domBuilder.parse(is);
		is.close();
	}
	
	/**
	 * 	返回action标签的所有结点
	 * @return
	 */
	public NodeList getActionNodeList() {
		return document.getElementsByTagName(ACTION_TAG);
	}
	
	/**
	 * 	返回一个action结点的所有属性
	 * 	Map中包含的Key值有class,method,name
	 * @return
	 */
	public Map<String, String> getAttributionOfActionNode(Node actionNode){
		Map<String, String> map = new HashMap<>();
		NamedNodeMap attrs = actionNode.getAttributes();
		for(int i=0;i<attrs.getLength();i++) {
			Node node = attrs.item(i);
			map.put(node.getNodeName(), node.getNodeValue());
		}
		return map;
	}
	
	
	/**
	 * 	判断一个结点是否包含指定的action的name
	 * @param actionNode 要查找的结点
	 * @param action name
	 * @return
	 */
	public boolean isNodeContainAction(Node actionNode,String action) {
		NamedNodeMap attrs = actionNode.getAttributes();
		for(int i=0;i<attrs.getLength();i++) {
			Node node = attrs.item(i);
			if(node.getNodeName().equals(NAME_TAG) && node.getNodeValue().equals(action)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 	返回包含指定action的name结点，如果不存在则为空
	 * @param action name
	 * @return
	 */
	public Node findActionNode(String action) {
		NodeList nodeList = getActionNodeList();
		for(int i=0;i<nodeList.getLength();i++) {
			Node node = nodeList.item(i);
			//使用instanceof来排除错误的读取
			if(node instanceof Element && isNodeContainAction(node, action))return node;
		}
		return null;
	}
	
	
	
	/**
	 * 	判断result结点是否包含要查找的result
	 * @param resultNode 要判断的结点
	 * @param result 要查找到result的name
	 * @return
	 */
	public boolean isNodeContainResult(Node resultNode,String result) {
		NamedNodeMap attrs = resultNode.getAttributes();
		for(int i=0;i<attrs.getLength();i++) {
			Node node = attrs.item(i);
			if(node.getNodeName().equals(NAME_TAG) && node.getNodeValue().equals(result)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 *	 返回包含指定result的结点，如果不存在则为空
	 * @param actionNode 要查找的action结点
	 * @param result 要查找的result的name
	 * @return 
	 */
	public Node findResultNodeFromActionNode(Node actionNode,String result) {
		NodeList nodeList = actionNode.getChildNodes();
		System.out.println(nodeList.getLength());
		for(int i=0;i<nodeList.getLength();i++) {
			System.out.println(i);
			Node node = nodeList.item(i);
			//使用instanceof来排除错误的读取
			if(node instanceof Element && isNodeContainResult(node,result))return node;
		}
		return null;
	}
	
	
	/**
	 * 	返回一个result结点的所有属性
	 * 	Map中包含的Key值有type,value,name
	 * @param resultNode
	 * @return
	 */
	public Map<String, String> getAttributionOfResultNode(Node resultNode){
		Map<String, String> map = new HashMap<>();
		NamedNodeMap attrs = resultNode.getAttributes();
		for(int i=0;i<attrs.getLength();i++) {
			Node node = attrs.item(i);
			map.put(node.getNodeName(), node.getNodeValue());
		}
		return map;
	}
}
