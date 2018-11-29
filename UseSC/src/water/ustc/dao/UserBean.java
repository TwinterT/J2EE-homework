package water.ustc.dao;

import java.sql.SQLException;

public class UserBean {
	
	public static final String SQL_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String SQL_URL = "jdbc:mysql://localhost:3306/j2ee?useSSL=false&serverTimezone=UTC";
	public static final String SQL_USER_NAME = "root";
	public static final String SQL_PASSWORD = "winter1996";
	
	private String userId;
	private String userName;
	private String userPass;
	
	/**
	 * 	实现查询数据库的操作，并且匹配
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean signIn() throws ClassNotFoundException, SQLException {
		
//		UserDAO userDAO = new UserDAO(SQL_DRIVER, SQL_URL, SQL_USER_NAME, SQL_PASSWORD);
//		
//		UserBean queryUser = userDAO.query(userName);
//		
//		userDAO.closeDBConnection();
		
		
		//使用postgresql数据库
		UsePostgreSQL usePostgreSQL = new UsePostgreSQL();
		UserBean queryUser = usePostgreSQL.query(userName);
		
		
		if(queryUser!=null) {
			if(queryUser.getUserPass().equals(userPass)) {
				return true;
			}
		}
		return false;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserPass() {
		return userPass;
	}
	
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
}
