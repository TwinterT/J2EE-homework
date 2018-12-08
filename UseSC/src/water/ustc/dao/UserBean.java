package water.ustc.dao;

import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import sc.ustc.dao.Conversation;
import sc.ustc.items.JDBCItems.BaseBean;

public class UserBean extends BaseBean{
	
	public static final String SQL_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String SQL_URL = "jdbc:mysql://localhost:3306/j2ee?useSSL=false&serverTimezone=UTC";
	public static final String SQL_USER_NAME = "root";
	public static final String SQL_PASSWORD = "winter1996";
	
	public UserBean() {

	}
	
	public UserBean(String name) {
		super(name);
	}
	
	public UserBean(String name,String pass) {
		super(name,pass);
	}
}
