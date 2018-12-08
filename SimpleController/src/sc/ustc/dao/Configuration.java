package sc.ustc.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import sc.ustc.items.JDBCItems.Property;



public class Configuration {

	public static final String XML_PATH = "or_mapping.xml";

	public static final String JDBC_TAG = "jdbc";

	public static final String CLASS_TAG = "class";

	public static final String NAME_TAG = "name";

	public static final String VALUE_TAG = "value";

	public static final String TABLE_TAG = "table";

	public static final String PROPERTY_TAG = "property";

	public static final String DRIVER = "driver_class";

	public static final String URL = "url_path";

	public static final String DATABASE = "db_username";

	public static final String PASSWORD = "db_userpassword";
	
	public static final String PROPERTY_NAME = "name";

	public static final String PROPERTY_COLUM = "column";

	public static final String PROPERTY_TYPE = "type";

	public static final String PROPERTY_LAZY = "lazy";
	
	public static final String MAP_USER_NAME = "userName";
	
	public static final String MAP_USER_PASS = "userPass";

	private Document doc;

	private String driver;
	private String url;
	private String database;
	private String password;

	private String className;
	private String tableName;

	private Map<String, Property> properties = new HashMap<>();
	private Map<String, Property> lazyProperties = new HashMap<>();
	
	/**
	 * 
	 * @param path
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public Configuration() throws ParserConfigurationException, SAXException, IOException {
		// TODO Auto-generated constructor stub
		
		String path = this.getClass().getClassLoader().getResource("/").getPath().replaceFirst("/", "");
		
		//将path的编码变成utf-8的编码 防止空格出现%20的情况
		try {
			path = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println(path+XML_PATH);
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder domBuilder = domfac.newDocumentBuilder();
		InputStream is = new FileInputStream(new File(path+XML_PATH));
		doc = domBuilder.parse(is);
		is.close();
		ConfigurateJDBC();
		configurateClass();
	}

	/**
	 * 初始化jdbc的参数列表
	 */
	private void ConfigurateJDBC() {
		Node jdbcNode = doc.getElementsByTagName(JDBC_TAG).item(0);

		NodeList jdbcProperties = jdbcNode.getChildNodes();

		for (int i = 0; i < jdbcProperties.getLength(); i++) {
			Node property = jdbcProperties.item(i);

			if (property instanceof Element) {
				initJDBCPropertyByNode(property);
			}
		}

	}

	private void initJDBCPropertyByNode(Node node) {
		NodeList attrs = node.getChildNodes();
		String value = null;
		String name = null;

		for (int i = 0; i < attrs.getLength(); i++) {
			Node attr = attrs.item(i);
			if (attr instanceof Element) {
				
				if (attr.getNodeName().equals(NAME_TAG)) {
					name = attr.getTextContent();
				} else if (attr.getNodeName().equals(VALUE_TAG)) {
					value = attr.getTextContent();
				}
				
				switch (name) {
				case DRIVER:
					driver = value;
					break;
				case URL:
					url = value;
					break;
				case PASSWORD:
					password = value;
					break;
				case DATABASE:
					database = value;
					break;
				}
			}
			
		}
	}

	private void configurateClass() {
		NodeList list = doc.getElementsByTagName(CLASS_TAG);
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if(node instanceof Element) {
				NodeList classlist = node.getChildNodes();
				for(int j=0;j<classlist.getLength();j++) {
					Node classNode = classlist.item(j);
					if (classNode instanceof Element) {
						switch (classNode.getNodeName()) {
						case NAME_TAG:
							className = classNode.getTextContent();
							break;
						case TABLE_TAG:
							tableName = classNode.getTextContent();
							break;
						case PROPERTY_TAG:
							initClassPropertyByNode(classNode);
							break;
						}
					}	
				}	
			}
			
		}
	}

	private void initClassPropertyByNode(Node node) {
		NodeList list = node.getChildNodes();

		Property property = new Property();
		String name = null;
		String column = null;
		String type = null;
		Boolean lazy = null;
		for (int i = 0; i < list.getLength(); i++) {
			Node attr = list.item(i);
			switch (attr.getNodeName()) {
			case PROPERTY_NAME:
				name = attr.getTextContent();
				break;
			case PROPERTY_COLUM:
				property.setColumn(attr.getTextContent());
				break;
			case PROPERTY_TYPE:
				property.setType(attr.getTextContent());
				break;
			case PROPERTY_LAZY:
				property.setLazy(Boolean.parseBoolean(attr.getTextContent()));
			}
		}
		
		properties.put(name, property);
		if(property.isLazy())lazyProperties.put(name, property);
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setProperties(Map<String, Property> properties) {
		this.properties = properties;
	}
	
	public Map<String, Property> getProperties() {
		return properties;
	}
	
	public Property getPropertyByName(String name) {
		return properties.get(name);
	}
	
	public void setLazyProperties(Map<String, Property> lazyProperties) {
		this.lazyProperties = lazyProperties;
	}
	
	public Map<String, Property> getLazyProperties() {
		return lazyProperties;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getTableName() {
		return tableName;
	}
	
}
