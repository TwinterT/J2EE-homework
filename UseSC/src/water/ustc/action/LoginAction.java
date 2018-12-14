package water.ustc.action;

import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import water.ustc.dao.UserBean;

public class LoginAction {
	
	public static final String LOGIN_SUCCESS = "success";
	
	public static final String LOGIN_FAILED = "failure"; 
	
	private UserBean userBean;
	
//	private String userName;
//	
//	private String userPassword;
	
	public String handleLogin() throws ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, IOException {
		//TODO login logic
		
//		//新建一个user对象
//		UserBean user = new UserBean();
//		user.setUserName("jack");
//		user.setUserPass("lalala");

		//获得登录处理结果
		if(userBean == null)return LOGIN_FAILED;
		else{
			System.out.println("name:"+userBean.getUserName());
			System.out.println("pass:"+userBean.getUserPass());
			boolean result = userBean.signIn();
			return result?LOGIN_SUCCESS:LOGIN_FAILED;
		}
	}
	
	public UserBean getUserBean() {
		return userBean;
	}
	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
}
