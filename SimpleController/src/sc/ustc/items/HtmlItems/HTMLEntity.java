package sc.ustc.items.HtmlItems;

public class HTMLEntity implements HTMLProduce{

	private Header header;
	
	private Body body;
	
	public Header getHeader() {
		return header;
	}
	
	public void setHeader(Header header) {
		this.header = header;
	}

	public Body getBody() {
		return body;
	}
	
	public void setBody(Body body) {
		this.body = body;
	}

	/**
	 * 传入一个空的StringBuilder
	 */
	@Override
	public String produceHTML() {
		StringBuilder result = new StringBuilder();
		result.append("<html>");
		result.append(header.produceHTML());
		result.append(body.produceHTML());
		result.append("</html>");
		return result.toString();
	}
}
