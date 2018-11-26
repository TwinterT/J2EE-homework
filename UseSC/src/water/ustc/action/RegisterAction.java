package water.ustc.action;

public class RegisterAction {
	
	public static final String REGIST_SUCCESS = "success";
	
	public static final String REGIST_FAILED = "failure";

	public String handleRegister() {
		//TODO register logic
		System.out.println("dealing with regist logic!");
		return REGIST_SUCCESS;
	}
}
