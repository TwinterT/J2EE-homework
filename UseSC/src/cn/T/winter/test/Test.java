package cn.T.winter.test;

import java.sql.SQLException;

import water.ustc.dao.UsePostgreSQL;
import water.ustc.dao.UserBean;
import water.ustc.dao.UserDAO;

public class Test {

	public static final String driver = "com.mysql.cj.jdbc.Driver";
	public static final String url = "jdbc:mysql://localhost:3306/j2ee?useSSL=false&serverTimezone=UTC";
	public static final String userName = "root";
	public static final String userPassword = "winter1996";
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
//		UserDAO userDAO = new UserDAO(driver, url, userName, userPassword);
//		UserBean user= new UserBean();
		

		UsePostgreSQL usePostgreSQL = new UsePostgreSQL();

		usePostgreSQL.insert("jack", "lalala");
	}
}
