package sc.ustc.items;

public class Interceptor {
	
	public static final String INTERCEPTOR_TAG = "interceptor";
	
	public static final String NAME = "name";
	public static final String CLASS_NAME = "class";
	public static final String AFTER_DO_NAME = "afterdo";
	public static final String PRE_DO_NAME = "predo";
	public static final String PRE_ACTION_METHOD = "preAction";
	public static final String AFTER_ACTION_METHOD = "afterAction";
	
	private String name;
	private String className;
	private String afterMethodName;
	private String preMethodName;
	
	public String getAfterMethodName() {
		return afterMethodName;
	}
	
	public void setAfterMethodName(String afterMethodName) {
		this.afterMethodName = afterMethodName;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPreMethodName() {
		return preMethodName;
	}
	
	public void setPreMethodName(String preMethodName) {
		this.preMethodName = preMethodName;
	}
	
}
