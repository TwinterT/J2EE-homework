package cn.T.winter.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import sc.ustc.items.HtmlItems.Body;
import sc.ustc.items.HtmlItems.Form;
import sc.ustc.items.HtmlItems.HTMLEntity;
import sc.ustc.items.HtmlItems.Header;
import sc.ustc.items.HtmlItems.Widget;
import sc.ustc.utils.XMLToHTMLHelper;

public class TestXMLToHTMLHelper {

	public static final String PATH = "src/success_view.xml";

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		InputStream is = new FileInputStream(new File(PATH));
		XMLToHTMLHelper helper = new XMLToHTMLHelper(is);
		HTMLEntity entity = helper.parseXML();

		Header header = entity.getHeader();
		System.out.println("header: " + header.getTitle());

		Body body = entity.getBody();
		List<Form> forms = body.getForms();
		for (Form form : forms) {
			System.out.println("\tform:");
			System.out.println("\t\tname" + form.getName());
			System.out.println("\t\taction" + form.getAction());
			System.out.println("\t\tmethod" + form.getMethod());
			List<Widget> widgets = form.getWidgets();
			for (Widget widget : widgets) {
				System.out.println("\t\twidget:");
				System.out.println("\t\t\tname"+widget.getName());
				System.out.println("\t\t\tlabel"+widget.getLabel());
			}
		}
		System.out.println(entity.produceHTML());
	}
}
