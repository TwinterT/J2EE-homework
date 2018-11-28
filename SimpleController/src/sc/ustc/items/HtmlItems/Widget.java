package sc.ustc.items.HtmlItems;

public abstract class Widget implements HTMLProduce{
	
	public static final String NAME_TAG = "name";

	public static final String LABEL_TAG = "label";
	
	private String name;
	private String label;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	@Override
	public abstract String produceHTML();
}
