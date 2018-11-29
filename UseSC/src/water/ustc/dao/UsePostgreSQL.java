package water.ustc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsePostgreSQL {

	
	public static final String TABLE_NAME = "scuser";
	
	public static final String TABLE_USER_NAME = "name";
	
	public static final String TABLE_USER_PASSWORD = "password";
	
	Connection connection;
	
	Statement statement;
	
	public UsePostgreSQL() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated constructor stub
		
		Class.forName("org.postgresql.Driver");
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/j2ee", "postgres", "winter1996");
		statement = connection.createStatement();
	}
	
	public void create() throws SQLException {
		String sql = "CREATE TABLE scuser (name TEXT PRIMARY KEY NOT NULL,password TEXT NOT NULL)";
		statement.executeUpdate(sql);
	}
	
	public boolean insert(String name,String passwrod) {
		String sql = "INSERT INTO "+TABLE_NAME+" ("+TABLE_USER_NAME+","+TABLE_USER_PASSWORD+") VALUES ('"+name+"','"+passwrod+"');";
		System.out.println(sql);
		try {
			statement.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public UserBean query(String name) {
		String sql = "select * from "+TABLE_NAME+" where "+TABLE_USER_NAME+"='"+name+"'";
		System.out.println(sql);
		try {
			ResultSet resultSet = statement.executeQuery(sql);
			if(!resultSet.next())return null;
			UserBean usr = new UserBean();
			usr.setUserName(resultSet.getString(TABLE_USER_NAME));
			usr.setUserPass(resultSet.getString(TABLE_USER_PASSWORD));
			return usr;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public boolean delete(String name) {
		String sql = "delete from "+TABLE_NAME+" where "+TABLE_USER_NAME+" like '"+name+"'";
		System.out.println(sql);
		try {
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean update(String name,String password) {
		String sql = "update "+TABLE_NAME+" set " + TABLE_USER_PASSWORD +"='"+password+"' where "+TABLE_USER_NAME+"='"+name+"'";
		System.out.println(sql);
		try {
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
