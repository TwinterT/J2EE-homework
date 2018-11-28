package sc.ustc.items.HtmlItems;

import java.util.ArrayList;
import java.util.List;

public class Body implements HTMLProduce{
	
	public static final String BODY_TAG = "body";
	
	public static final String FORM_TAG = "form";

	List<Form> forms = new ArrayList<>();
	
	public void setForms(List<Form> forms) {
		this.forms = forms;
	}
	
	public List<Form> getForms() {
		return forms;
	}

	@Override
	public String produceHTML() {
		StringBuilder result = new StringBuilder();
		result.append("<body>");
		for(Form form:forms) {
			result.append(form.produceHTML());
		}
		result.append("</body>");
		return result.toString();
	}
	
}
