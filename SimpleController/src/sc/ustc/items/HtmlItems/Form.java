package sc.ustc.items.HtmlItems;

import java.util.List;

public class Form implements HTMLProduce{

	public static final String FORM_TAG = "form";
	
	public static final String NAME_TAG = "name";
	
	public static final String ACTION_TAG = "action";
	
	public static final String METHOD_TAG = "method";
	
	private String name;
	private String action;
	private String method;
	private List<Widget> widgets;
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Widget> getWidgets() {
		return widgets;
	}
	
	public void setWidgets(List<Widget> widgets) {
		this.widgets = widgets;
	}

	@Override
	public String produceHTML() {
		StringBuilder result = new StringBuilder();
		result.append("<form");
		if(name!=null)result.append(" name=\""+name+"\"");
		if(action!=null)result.append(" action=\""+action+"\"");
		if(method!=null)result.append(" method=\""+method+"\"");
		result.append(">");
		for(Widget widget:widgets) {
			result.append(widget.produceHTML());
		}
		result.append("</form>");
		return result.toString();
	}
}
