package water.ustc.action;

import water.ustc.dao.UserBean;

public class RegisterAction {
	
	public static final String REGIST_SUCCESS = "success";
	
	public static final String REGIST_FAILED = "failure";

	private UserBean userBean;
	
	public String handleRegister() {
		//TODO register logic
		System.out.println("dealing with regist logic!");
		return REGIST_SUCCESS;
	}
	
	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
	
	public UserBean getUserBean() {
		return userBean;
	}
}
