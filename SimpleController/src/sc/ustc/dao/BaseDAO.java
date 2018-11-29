package sc.ustc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseDAO {

	//SQL的驱动类名
	protected String driver;
	
	//SQL的url
	protected String url;
	
	//SQL数据库的用户名
	protected String userName;
	
	//SQL数据库的密码
	protected String userPassword;
	
	//数据库的连接
	protected Connection connection;
	
	/**
	 * 	建立一个数据库连接
	 * @return
	 * @throws SQLException
	 */
	protected void openDBConnection() throws SQLException {
		connection = DriverManager.getConnection(url, userName, userPassword);
	}
	
	/**
	 * 	关闭连接
	 * @param connection
	 * @return
	 */
	public boolean closeDBConnection(){
		try {
			connection.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	abstract public Object query(String name);
	
	abstract public boolean insert(String name,String password);
	
	abstract public boolean update(String name,String password);
	
	abstract public boolean delete(String name);
	
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
}
