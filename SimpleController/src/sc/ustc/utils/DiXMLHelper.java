package sc.ustc.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import sc.ustc.items.DiItems.Bean;
import sc.ustc.items.DiItems.Field;

public class DiXMLHelper {

	public static final String DI_XML_FILE_NAME = "di.xml";

	public static final String BEAN_TAG = "bean";

	public static final String ID_TAG = "id";

	public static final String CLASS_TAG = "class";

	public static final String NAME_TAG = "name";

	public static final String REF_TAG = "bean-ref";

	private Map<String, Bean> diData;

	private Document document;

	/**
	 * 初始化
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public DiXMLHelper() throws ParserConfigurationException, SAXException, IOException {
		String path = this.getClass().getClassLoader().getResource("").getPath().replaceFirst("/", "");

		// 将path的编码变成utf-8的编码 防止空格出现%20的情况
		try {
			path = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		File file = new File(path + DI_XML_FILE_NAME);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.parse(file);

		diData = new HashMap<>();
		getBeans();
	}

	/**
	 * 在xml中寻找bean结点并且初始化
	 */
	private void getBeans() {
		NodeList nodeList = document.getElementsByTagName(BEAN_TAG);
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				Bean bean = getBeanByNode(node);
				diData.put(bean.getName(), bean);
			}
		}
	}

	/**
	 * 获取node结点的属性并且初始化bean
	 * @param node
	 * @return
	 */
	private Bean getBeanByNode(Node node) {
		Bean bean = new Bean();
		NamedNodeMap attrs = node.getAttributes();
		//初始化bean的属性
		for (int i = 0; i < attrs.getLength(); i++) {
			Node attr = attrs.item(i);
			String value = attr.getNodeValue();
			switch (attr.getNodeName()) {
			case ID_TAG:
				bean.setName(value);
				break;
			case CLASS_TAG:
				bean.setClassName(value);
				break;
			}
		}
		//如果存在则初始化子结点filed的属性
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node child = list.item(i);
			if (child != null && child instanceof Element) {
				Field field = new Field();
				NamedNodeMap childAttrs = child.getAttributes();
				for (int j = 0; j < childAttrs.getLength(); j++) {
					Node attr = childAttrs.item(j);
					String value = attr.getNodeValue();
					switch (attr.getNodeName()) {
					case NAME_TAG:
						field.setName(value);
						break;
					case REF_TAG:
						field.setRef(value);
						break;
					}
				}
				bean.setField(field);
			}
		}
		return bean;
	}
	
	public Map<String, Bean> getDiData() {
		return diData;
	}
}
