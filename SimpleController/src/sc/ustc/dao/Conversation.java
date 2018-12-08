package sc.ustc.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import net.sf.cglib.proxy.Enhancer;
import sc.ustc.items.JDBCItems.BaseBean;
import sc.ustc.utils.ProxyUserBeanHandler;

public class Conversation {
	
	public static final String path = "src//or_mapping.xml";

	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static BaseBean getObject(BaseBean user) throws ParserConfigurationException, SAXException, IOException, SQLException, ClassNotFoundException {
		
		Configuration configuration = new Configuration();

		//获得代理类
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(BaseBean.class);
		enhancer.setCallback(new ProxyUserBeanHandler(configuration.getLazyProperties()));
		BaseBean proxy = (BaseBean) enhancer.create();
		
		proxy.setUserName(user.getUserName());
	
		//初始化user中的name属性
		ResultSet resultSet = query(user);
		
		//判断是否时懒加载的属性
		if(!configuration.getPropertyByName(Configuration.MAP_USER_PASS).isLazy()) {
			String password = resultSet.getString(configuration.getPropertyByName(Configuration.MAP_USER_PASS).getColumn());
			if(password == null)return null;
			proxy.setUserPass(password);
		}
		return proxy;
	}
	
	
	/**
	 * user的name来查询
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static ResultSet query(BaseBean user) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, SQLException {
		Configuration configuration = new Configuration();
		Class.forName(configuration.getDriver());
		Connection connection = DriverManager.getConnection(configuration.getUrl(),
								configuration.getDatabase(),configuration.getPassword());
		Statement statement = connection.createStatement();
		
		String name = user.getUserName();
		
		String sql = "select * from "+configuration.getTableName()+" where "+
					configuration.getPropertyByName(configuration.MAP_USER_NAME).getColumn()+"='"+name+"'";
		
		
		System.out.println(sql);
		
		ResultSet result = null;
		try {
			result = statement.executeQuery(sql);
			if(!result.next())return null;
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 	插入一个用户
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static boolean insert(BaseBean user) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, SQLException {
		Configuration configuration = new Configuration();
		Class.forName(configuration.getDriver());
		Connection connection = DriverManager.getConnection(configuration.getUrl(),
								configuration.getDatabase(),configuration.getPassword());
		Statement statement = connection.createStatement();
		String name = user.getUserName();
		String password = user.getUserPass();
		String sql = "insert into "+configuration.getTableName()+"("+
					configuration.getPropertyByName(configuration.MAP_USER_NAME).getColumn()+","+
					configuration.getPropertyByName(configuration.MAP_USER_PASS).getColumn()+
					") values('"+name+"','"+password+"')";
		System.out.println(sql);
		try {
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		connection.close();
		return true;
	}
	
	/**
	 * 	根据名字修改密码
	 * @param name
	 * @param password
	 * @return
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static boolean update(BaseBean user) throws ParserConfigurationException, SAXException, IOException, SQLException, ClassNotFoundException {
		
		Configuration configuration = new Configuration();
		Class.forName(configuration.getDriver());
		Connection connection = DriverManager.getConnection(configuration.getUrl(),configuration.getDatabase(),
								configuration.getPassword());
		Statement statement = connection.createStatement();
		String name = user.getUserName();
		String password = user.getUserPass();
		String sql = "update "+configuration.getTableName()+" set " + 
					configuration.getPropertyByName(configuration.MAP_USER_PASS).getColumn() +
					"='"+password+"' where "+configuration.getPropertyByName(configuration.MAP_USER_NAME).getColumn()+
					"='"+name+"'";
		System.out.println(sql);
		try {
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		connection.close();
		return true;
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static boolean deleteObject(BaseBean user) throws ParserConfigurationException, SAXException, IOException, SQLException, ClassNotFoundException {
		
		Configuration configuration = new Configuration();
		Class.forName(configuration.getDriver());
		Connection connection = DriverManager.getConnection(configuration.getUrl(),configuration.getDatabase(),
								configuration.getPassword());
		Statement statement = connection.createStatement();
		String name = user.getUserName();
		
		String sql = "delete from "+configuration.getTableName()+" where "+ 
					configuration.getPropertyByName(configuration.MAP_USER_NAME).getColumn()+
					" like '"+name+"'";
		System.out.println(sql);
		try {
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		connection.close();
		return true;
	}
}
