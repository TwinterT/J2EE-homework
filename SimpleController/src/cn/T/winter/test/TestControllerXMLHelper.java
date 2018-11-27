package cn.T.winter.test;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import sc.ustc.items.Action;
import sc.ustc.items.Interceptor;
import sc.ustc.items.InterceptroREF;
import sc.ustc.items.Result;
import sc.ustc.utils.ControllerXMLHelper;

public class TestControllerXMLHelper {

	public static final String XML_PATH = "src\\controller.xml";
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		ControllerXMLHelper helper = new ControllerXMLHelper(XML_PATH);
		
		List<Action> actions = helper.getActions();
		List<Interceptor> interceptors = helper.getInterceptors();
		for(Action action:actions) {
			System.out.println("action:");
			System.out.println("\t"+action.getName());
			System.out.println("\t"+action.getClassName());
			System.out.println("\t"+action.getMethodName());
			for(Result result:action.getResultNodes()) {
				System.out.println("\tresult:");
				System.out.println("\t\t"+result.getName());
				System.out.println("\t\t"+result.getType());
				System.out.println("\t\t"+result.getValue());
			}
			for(InterceptroREF interceptroREF:action.getInterceptroRefNodes()) {
				System.out.println("\tinterceptroREF:");
				System.out.println("\t\t"+interceptroREF.getName());
			}
		}
		for(Interceptor interceptor:interceptors) {
			System.out.println("interceptor:");
			System.out.println("\t"+interceptor.getName());
			System.out.println("\t"+interceptor.getClassName());
			System.out.println("\t"+interceptor.getPreMethodName());
			System.out.println("\t"+interceptor.getAfterMethodName());
		}
	}
}
