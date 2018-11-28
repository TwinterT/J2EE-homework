package sc.ustc.items.HtmlItems;

public class Header implements HTMLProduce{
	
	public static final String HEADER_TAG = "header";
	
	public static final String TITLE_TAG = "title";
	
	private String title;

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String produceHTML() {
		StringBuilder result = new StringBuilder();
		result.append("<head>");
		result.append("<title>"+title+"</title>");
		result.append("</head>");
		return result.toString();
	}
}
