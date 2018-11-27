package sc.ustc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

import sc.ustc.items.Action;
import sc.ustc.items.Interceptor;
import sc.ustc.items.InterceptroREF;
import sc.ustc.items.Result;

public class ControllerXMLHelper {

	private Document document;
	private DocumentBuilderFactory domfac;
	private DocumentBuilder domBuilder;

	/**
	 * 仅用于测试
	 * @param xmlPath
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public ControllerXMLHelper(String xmlPath)
			throws ParserConfigurationException, SAXException, IOException {
		domfac = DocumentBuilderFactory.newInstance();
		domBuilder = domfac.newDocumentBuilder();
		InputStream is = new FileInputStream(new File(xmlPath));
		document = domBuilder.parse(is);
		is.close();
	}

	/**
	 * 
	 * @param xmlPath
	 * @param context
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public ControllerXMLHelper(String xmlPath, ServletContext context)
			throws ParserConfigurationException, SAXException, IOException {
		domfac = DocumentBuilderFactory.newInstance();
		domBuilder = domfac.newDocumentBuilder();
		InputStream is = context.getResourceAsStream(xmlPath);
		document = domBuilder.parse(is);
		is.close();
	}
	
	/**
	 * 获得XML中的所有Action结点
	 * 
	 * @return
	 */
	public List<Action> getActions() {
		List<Action> list = new ArrayList<>();
		NodeList nodeList = document.getElementsByTagName(Action.ACTION_TAG);
		int length = nodeList.getLength();

		for (int i = 0; i < length; i++) {
			Node node = nodeList.item(i);
			Action action = new Action();
			initActionAttributes(action, node.getAttributes());
			initActionResultsAndInterceptroREFs(action, node.getChildNodes());
			list.add(action);
		}

		return list;
	}

	/**
	 * 初始化action的所有属性
	 * 
	 * @param action
	 * @param attrs
	 */
	public void initActionAttributes(Action action, NamedNodeMap attrs) {

		for (int i = 0; i < attrs.getLength(); i++) {
			Node attr = attrs.item(i);
			String value = attr.getNodeValue();
			switch (attr.getNodeName()) {
			case Action.NAME:
				action.setName(value);
				break;
			case Action.CLASS_NAME:
				action.setClassName(value);
				break;
			case Action.METHOD_NAME:
				action.setMethodName(value);
				break;
			}
		}
	}

	/**
	 * 获得action的所有的result和interceptroREF结点并初始化
	 * 
	 * @param action
	 * @param actionChildren
	 */
	public void initActionResultsAndInterceptroREFs(Action action, NodeList actionChildren) {
		int len = actionChildren.getLength();
		List<InterceptroREF> interceptroREFList = new ArrayList<>();
		List<Result> resultList = new ArrayList<>();
		for (int i = 0; i < len; i++) {
			Node node = actionChildren.item(i);
			if (node instanceof Element) {
				switch (node.getNodeName()) {
				case Result.RESULT_TAG:
					resultList.add(initResult(node));
					break;

				case InterceptroREF.INTERCEPTRO_REF:
					interceptroREFList.add(initInterceptroREF(node));
					break;
				}
			}
		}
		action.setInterceptroRefNodes(interceptroREFList);
		action.setResultNodes(resultList);
	}

	/**
	 * 初始化result的所有属性
	 * 
	 * @param node
	 * @return
	 */
	public Result initResult(Node node) {
		Result result = new Result();
		NamedNodeMap attrs = node.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			Node attr = attrs.item(i);
			String value = attr.getNodeValue();
			switch (attr.getNodeName()) {
			case Result.NAME:
				result.setName(value);
				break;
			case Result.TYPE_NAME:
				result.setType(value);
				break;
			case Result.VALUE_NAME:
				result.setValue(value);
				break;
			}
		}
		return result;
	}

	/**
	 * 初始化InterceptroREF的所有属性
	 * 
	 * @param node
	 * @return
	 */
	public InterceptroREF initInterceptroREF(Node node) {
		InterceptroREF interceptroREF = new InterceptroREF();
		NamedNodeMap attrs = node.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			Node attr = attrs.item(i);
			String value = attr.getNodeValue();
			switch (attr.getNodeName()) {
			case InterceptroREF.NAME:
				interceptroREF.setName(value);
				break;
			}
		}
		return interceptroREF;
	}

	public List<Interceptor> getInterceptors() {
		List<Interceptor> list = new ArrayList<>();
		NodeList nodeList = document.getElementsByTagName(Interceptor.INTERCEPTOR_TAG);
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			Interceptor interceptor = new Interceptor();
			initInterceptorAttributes(interceptor,node.getAttributes());
			list.add(interceptor);
		}
		return list;
	}

	public void initInterceptorAttributes(Interceptor interceptor,NamedNodeMap attrs) {
		for (int i = 0; i < attrs.getLength(); i++) {
			Node attr = attrs.item(i);
			String value = attr.getNodeValue();
			switch (attr.getNodeName()) {
			case Interceptor.NAME:
				interceptor.setName(value);
				break;
			case Interceptor.CLASS_NAME:
				interceptor.setClassName(value);
				break;
			case Interceptor.PRE_DO_NAME:
				interceptor.setPreMethodName(value);
				break;
			case Interceptor.AFTER_DO_NAME:
				interceptor.setAfterMethodName(value);
				break;
			}
		}
	}
}
