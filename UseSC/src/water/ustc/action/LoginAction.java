package water.ustc.action;

import java.sql.SQLException;

import water.ustc.dao.UserBean;

public class LoginAction {
	
	public static final String LOGIN_SUCCESS = "success";
	
	public static final String LOGIN_FAILED = "failure"; 
	
	private String userName;
	
	private String userPassword;
	
	public String handleLogin() throws ClassNotFoundException, SQLException {
		//TODO login logic
		
		//新建一个user对象
		UserBean user = new UserBean();
		user.setUserName("jack");
		user.setUserPass("lalala");
		
		//获得登录处理结果
		boolean result = user.signIn();
		
		System.out.println("deal with login logical!");
		
		return result?LOGIN_SUCCESS:LOGIN_FAILED;
	}
}
