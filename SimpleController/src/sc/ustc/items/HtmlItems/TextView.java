package sc.ustc.items.HtmlItems;

public class TextView extends Widget{

	public static final String TEXT_VIEW_TAG = "textView";
	
	public static final String VALUE_TAG = "value";
	
	private String value;
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String produceHTML() {
		StringBuilder result = new StringBuilder();
		if(getLabel()!=null)result.append("<p><tr><td>"+getLabel()+"</td><td>");
		result.append("<input");
		if(getName()!=null)result.append(" type=\"text\" name=\""+getName()+"\"");
		if(value!=null)result.append(" value=\""+value+"\"");
		//if(getLabel()!=null)result.append("");
		result.append(">");
		
		if(getLabel()!=null)result.append("</td></tr></p>");
		return result.toString();
	}
}
