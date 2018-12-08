package cn.T.winter.test;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import sc.ustc.dao.Configuration;
import sc.ustc.items.JDBCItems.Property;

public class TestConfiguration {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		Configuration configuration = new Configuration();
		System.out.println(configuration.getUrl());
		System.out.println(configuration.getDatabase());
		System.out.println(configuration.getDriver());
		System.out.println(configuration.getPassword());
		
		Map<String,Property> map = configuration.getProperties();
		
		Property property = map.get(configuration.MAP_USER_NAME);
		System.out.println(property.getType());
		System.out.println(property.getColumn());
		System.out.println(property.isLazy());
	}
}
