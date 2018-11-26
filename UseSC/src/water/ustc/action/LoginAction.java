package water.ustc.action;

public class LoginAction {
	
	public static final String LOGIN_SUCCESS = "success";
	
	public static final String LOGIN_FAILED = "failure"; 
	
	public String handleLogin() {
		//TODO login logic
		System.out.println("deal with login logical!");
		return LOGIN_FAILED;
	}
}
