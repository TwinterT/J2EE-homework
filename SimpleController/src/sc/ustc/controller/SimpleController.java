package sc.ustc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import sc.ustc.utils.ParseXML;

public class SimpleController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String XML_RELATIVE_PATH = "\\WEB-INF\\classes\\controller.xml";

	public static final String SUCCESS = "sucess";

	public static final String FAILURE = "failure";

	public static final String TYPE_FORWARD = "forward";

	public static final String TYPE_REDIRECT = "redirect";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

//		String html = "<html>\n\t<head>\n\t\t<title>SimpleController</title>\n\t</head>"
//					+ "\n\t</body>欢迎使用SimpleController!</body>\n</html>";
//		
//		resp.setContentType("text/html; charset=UTF-8"); //设置返回值的类型
//		resp.getOutputStream().write(html.getBytes("UTF-8"));

		// url 的格式为 http://localhost:8080/UseSC/Login.sc

		// 用于判断是否有匹配的action和result
		Boolean isAction = true;
		Boolean isResult = true;

		String url = req.getRequestURL().toString();
		System.out.println("url: " + url);

		// 获得Login.sc
		String action = url.substring(url.lastIndexOf("/") + 1);
		System.out.println("last action:" + action);

		// 获得Login
		action = action.substring(0, action.indexOf("."));
		System.out.println("action:" + action);

		// 开始解析本地的XML文件来获得和action匹配的标签

		try {
			ParseXML parseXML = new ParseXML(XML_RELATIVE_PATH, req.getSession().getServletContext());

			Node actionNode = parseXML.findActionNode(action);
			if (actionNode == null) {
				// 说明没有找到匹配的action结点
				isAction = false;
			} else {
				// 找到了匹配的action结点

				// 获得action结点的属性的键值
				Map<String, String> mapAction = parseXML.getAttributionOfActionNode(actionNode);

				// 获得类名和方法名
				String className = mapAction.get(ParseXML.CLASS_TAG);
				String methodName = mapAction.get(ParseXML.METHOD_TAG);

				// 利用反射创建类对象
				Class<?> c = Class.forName(className);
				Method method = c.getMethod(methodName, null);
				String result = (String) method.invoke(c.getDeclaredConstructor(null).newInstance(null), null);

				System.out.println(result);

				Node resultNode = parseXML.findResultNodeFromActionNode(actionNode, result);
				if (resultNode == null) {
					// 没有找到匹配的result
					isResult = false;
				} else {

					// 找到了匹配的result
					Map<String, String> mapResult = parseXML.getAttributionOfResultNode(resultNode);
					System.out.println(mapResult.get("name"));
					System.out.println(mapResult.get("type"));
					System.out.println(mapResult.get("value"));

					String type = mapResult.get(ParseXML.TYPE_TAG);
					String value = mapResult.get(ParseXML.VALUE_TAG);

					if (type.equals(TYPE_FORWARD)) {
						// 使用forward方式
						req.getRequestDispatcher(value).forward(req, resp);
					} else {
						// 使用redirect方式
						resp.sendRedirect(value);
					}
				}
			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isAction == false || isResult == false) {
			String message = (isAction == false ? "Can't find the request of action!"
					: "No resource of request(result)!");
			PrintWriter out = resp.getWriter();
			out.write("<html><head><title>COS</title></head><body>" + message + "</body></html>");
		}

	}

}
