package water.ustc.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import sc.ustc.dao.BaseDAO;
import sc.ustc.dao.Conversation;

public class UserDAO extends BaseDAO{
	
	public static final String TABLE_NAME = "scuser";
	
	public static final String TABLE_USER_ID = "id";
	
	public static final String TABLE_USER_NAME = "name";
	
	public static final String TABLE_USER_PASSWORD = "password";

	private Statement statement;
	
//	/**
//	 * 	初始化属性，并且建立连接
//	 * @param driver
//	 * @param url
//	 * @param userName
//	 * @param userPassword
//	 * @throws SQLException
//	 * @throws ClassNotFoundException
//	 */
//	public UserDAO(String driver,String url,String userName,String userPassword) throws SQLException, ClassNotFoundException {
//		this.url = url;
//		this.userName = userName;
//		this.driver = driver;
//		this.userPassword = userPassword;
//		
//		Class.forName(driver);
//		openDBConnection();
//		statement = connection.createStatement();
//	}

	/**
	 * 	删除一个用户
	 */
	@Override
	public boolean delete(String name) {
		try {
			return Conversation.deleteObject(new UserBean(name));
		} catch (ClassNotFoundException | ParserConfigurationException | SAXException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
//		String sql = "delete from "+TABLE_NAME+" where "+TABLE_USER_NAME+" like '"+name+"'";
//		System.out.println(sql);
//		try {
//			statement.executeUpdate(sql);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
	}

	/**
	 * 	插入一个用户
	 */
	@Override
	public boolean insert(String name,String password) {
		
		try {
			return Conversation.insert(new UserBean(name,password));
		} catch (ClassNotFoundException | ParserConfigurationException | SAXException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
//		String sql = "insert into "+TABLE_NAME+"("+TABLE_USER_NAME+","+TABLE_USER_PASSWORD+") values('"+name+"','"+password+"')";
//		System.out.println(sql);
//		try {
//			statement.executeUpdate(sql);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
	}

	/**
	 * user的name来查询
	 */
	@Override
	public UserBean query(String name) {

		try {
			return new UserBean(name, (Conversation.query(new UserBean(name)).getString("password")));
		} catch (ClassNotFoundException | ParserConfigurationException | SAXException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

//		String sql = "select * from "+TABLE_NAME+" where "+TABLE_USER_NAME+"='"+name+"'";
//		
//		
//		System.out.println(sql);
//		
//		ResultSet result = null;
//		try {
//			result = statement.executeQuery(sql);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
//		
//		UserBean ans = new UserBean();
//		try {
//			if(!result.next())return null;
//			ans.setUserId(result.getString(TABLE_USER_ID));
//			ans.setUserName(result.getString(TABLE_USER_NAME));
//			ans.setUserPass(result.getString(TABLE_USER_PASSWORD));
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
//		return ans;
	}

	/**
	 * 	根据名字修改密码
	 * @param name
	 * @param password
	 * @return
	 */
	@Override
	public boolean update(String name,String password) {

		try {
			return Conversation.update(new UserBean(name,password));
		} catch (ClassNotFoundException | ParserConfigurationException | SAXException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
//		String sql = "update "+TABLE_NAME+" set " + TABLE_USER_PASSWORD +"='"+password+"' where "+TABLE_USER_NAME+"='"+name+"'";
//		System.out.println(sql);
//		try {
//			statement.executeUpdate(sql);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
	}
}
