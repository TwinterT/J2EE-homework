package sc.ustc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import sc.ustc.items.HtmlItems.Body;
import sc.ustc.items.HtmlItems.ButtonView;
import sc.ustc.items.HtmlItems.Form;
import sc.ustc.items.HtmlItems.HTMLEntity;
import sc.ustc.items.HtmlItems.Header;
import sc.ustc.items.HtmlItems.TextView;
import sc.ustc.items.HtmlItems.Widget;

public class XMLToHTMLHelper {

	private Document document;
	private DocumentBuilderFactory domfac;
	private DocumentBuilder domBuilder;

	/**
	 * only for test
	 * @param is
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public XMLToHTMLHelper(InputStream is) throws ParserConfigurationException, SAXException, IOException {
		domfac = DocumentBuilderFactory.newInstance();
		domBuilder = domfac.newDocumentBuilder();
		document = domBuilder.parse(is);
	}
	
	public XMLToHTMLHelper(String name,ServletContext context) throws ParserConfigurationException, SAXException, IOException {
		domfac = DocumentBuilderFactory.newInstance();
		domBuilder = domfac.newDocumentBuilder();
		InputStream is = context.getResourceAsStream(name);
		document = domBuilder.parse(is);
		is.close();
	}

	/**
	 * 获得HTML实体
	 * 
	 * @return
	 */
	public HTMLEntity parseXML() {
		HTMLEntity entity = new HTMLEntity();

		entity.setHeader(parseHeader());

		entity.setBody(parseBody());

		return entity;
	}

	/**
	 * 获得header部分内容
	 * 
	 * @return
	 */
	public Header parseHeader() {
		Header header = new Header();

		Element headNode = (Element) document.getElementsByTagName(Header.HEADER_TAG).item(0);

		NodeList nodes = headNode.getChildNodes();

		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if ((node instanceof Element) && node.getNodeName().equals(Header.TITLE_TAG)) {
				header.setTitle(node.getTextContent());
				break;
			}
		}
		return header;
	}

	/**
	 * 获得body的内容
	 * 
	 * @return
	 */
	public Body parseBody() {
		Body body = new Body();

		List<Form> forms = new ArrayList<>();

		Element bodyNode = (Element) document.getElementsByTagName(Body.BODY_TAG).item(0);

		NodeList list = bodyNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node instanceof Element && node.getNodeName().equals(Form.FORM_TAG)) {
				forms.add(parseForm(node));
			}
		}

		body.setForms(forms);
		return body;
	}

	/**
	 * 获得Form的内容
	 * 
	 * @param formNode
	 * @return
	 */
	public Form parseForm(Node formNode) {

		Form form = new Form();

		List<Widget> widgets = new ArrayList<>();

		NodeList list = formNode.getChildNodes();

		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node instanceof Element) {
				switch (node.getNodeName()) {
				case Form.NAME_TAG:
					form.setName(node.getTextContent());
					break;
				case Form.ACTION_TAG:
					form.setAction(node.getTextContent());
					break;
				case Form.METHOD_TAG:
					form.setMethod(node.getTextContent());
					break;
				default:
					Widget widget = parseWidget(node);
					if (widget != null)
						widgets.add(widget);
				}
			}
		}

		form.setWidgets(widgets);
		return form;
	}

	/**
	 * 获得一个Widget
	 *  
	 * @param node
	 * @return
	 */
	public Widget parseWidget(Node node) {
		switch (node.getNodeName()) {
		case TextView.TEXT_VIEW_TAG:
			return parseTextView(node);
		case ButtonView.BUTTON_TAG:
			return parseButtonView(node);
		default:
			return null;
		}
	}

	/**
	 * 获得一个TextView
	 * 
	 * @param textViewNode
	 * @return
	 */
	public TextView parseTextView(Node textViewNode) {
		TextView textView = new TextView();

		NodeList list = textViewNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node instanceof Element) {
				switch (node.getNodeName()) {
				case TextView.NAME_TAG:
					textView.setName(node.getTextContent());
					break;
				case TextView.LABEL_TAG:
					textView.setLabel(node.getTextContent());
					break;
				case TextView.VALUE_TAG:
					textView.setValue(node.getTextContent());
					break;
				}
			}
		}
		return textView;
	}

	/**
	 * 获得一个ButtonView
	 * 
	 * @param buttonViewNode
	 * @return
	 */
	public ButtonView parseButtonView(Node buttonViewNode) {
		ButtonView buttonView = new ButtonView();

		NodeList list = buttonViewNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node instanceof Element) {
				switch (node.getNodeName()) {
				case ButtonView.NAME_TAG:
					buttonView.setName(node.getTextContent());
					break;
				case ButtonView.LABEL_TAG:
					buttonView.setLabel(node.getTextContent());
					break;
				}
			}
		}
		return buttonView;
	}
}
