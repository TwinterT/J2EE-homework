package sc.ustc.items.JDBCItems;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.mysql.cj.xdevapi.Result;

import sc.ustc.dao.Configuration;
import sc.ustc.dao.Conversation;

public class BaseBean {

	protected String userId;
	protected String userName;
	protected String userPass;
	
	public BaseBean() {
		
	}
	
	public BaseBean(String name) {
		userName = name;
	}
	
	public BaseBean(String name,String pass) {
		userName = name;
		userPass = pass;
	}
	
	public boolean signIn() throws ClassNotFoundException, ParserConfigurationException, SAXException, IOException, SQLException {
		BaseBean baseBean = new BaseBean();
		baseBean.setUserName(userName);
		
		BaseBean result = Conversation.getObject(baseBean);
		String pass = result.getUserPass();
		if(pass == null)return false;
		if(pass.equals(userPass))return true;
		else return false;
//		ResultSet pass = Conversation.query(baseBean);
		
//		if(pass == null)return false;
//		if(pass.equals(userPass)) {
//			return true;
//		}else return false;
		
//		UserDAO userDAO = null;
//		try {
//			userDAO = new UserDAO(SQL_DRIVER, SQL_URL, SQL_USER_NAME, SQL_PASSWORD);
//			
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		userDAO = new UserDAO();
//		UserBean queryUser = userDAO.query(userName);
//		System.out.println(queryUser.userPass);
		
//		userDAO.closeDBConnection();
		
		
//		//使用postgresql数据库
//		UsePostgreSQL usePostgreSQL = new UsePostgreSQL();
//		UserBean queryUser = usePostgreSQL.query(userName);
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
		System.out.println("set pass !!!!");
		this.userPass = userPass;
	}
}
