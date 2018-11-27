package sc.ustc.items;


public class Result {

	public static final String RESULT_TAG = "result";
	
	public static final String NAME = "name";
	public static final String TYPE_NAME = "type";
	public static final String VALUE_NAME = "value";
	
	private String name;
	private String type;
	private String value;
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
