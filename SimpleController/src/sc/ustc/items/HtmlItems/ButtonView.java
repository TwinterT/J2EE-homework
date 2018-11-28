package sc.ustc.items.HtmlItems;

public class ButtonView extends Widget{
	
	public static final String BUTTON_TAG = "buttonView";
	
	@Override
	public String produceHTML() {
		StringBuilder result = new StringBuilder();
		result.append("<p><button");
		if(getName()!=null)result.append(" type=\"button\""+" name=\""+getName()+"\"");
		result.append(">");
		if(getLabel()!=null)result.append(getLabel());
		result.append("</button></p>");
		return result.toString();
	}
	
}
