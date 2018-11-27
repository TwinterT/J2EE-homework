package sc.ustc.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import cn.T.winter.Interface.ExecutHelper;
import sc.ustc.items.Action;
import sc.ustc.items.Interceptor;
import sc.ustc.items.Result;
import sc.ustc.utils.ControllerXMLHelper;
import sc.ustc.utils.ExecutHelperImp;
import sc.ustc.utils.ProxyHandler;

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
	
	private Boolean isAction = true;
	private Boolean isResult = true;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		// url 的格式为 http://localhost:8080/UseSC/Login.sc

		// 用于判断是否有匹配的action和result

		String url = req.getRequestURL().toString();
		System.out.println("url: " + url);

		// 获得Login.sc
		String actionString = url.substring(url.lastIndexOf("/") + 1);

		// 获得Login
		actionString = actionString.substring(0, actionString.indexOf("."));
		System.out.println("action:" + actionString);
		
		ServletContext context = req.getSession().getServletContext();

		// 开始解析XML文件
		try {
			ControllerXMLHelper helper = new ControllerXMLHelper(XML_RELATIVE_PATH, context);
			
			//获得所有的action结点
			List<Action> actions = helper.getActions();
			
			//获得所有的拦截器结点
			List<Interceptor> interceptors = helper.getInterceptors();
			
			//查询结点
			Action action = searchAction(actions, actionString);
			
			String resultString = getResultStringByActionProxy(action,interceptors);
			
			//String resultString = getResultStringByAction(action);
			
			Result result = searchResult(action, resultString);
			
			doWithResult(result,req,resp,context);
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		doWithFailed(resp);
	}
	
	/**
	 * 查询匹配的action
	 * @param actions
	 * @param s
	 * @return
	 */
	public Action searchAction(List<Action> actions,String s) {
		for(Action action:actions) {
			if(action.getName().equals(s))return action;
		}
		
		//如果没找到
		isAction = false;
		return null;
	}
	
	/**
	 * 利用反射得到结果
	 * @param action
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	public String getResultStringByAction(Action action) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		if(action == null)return null;
		
		// 获得类名和方法名
		String className = action.getClassName();
		String methodName = action.getMethodName();

		// 利用反射创建类对象
		Class<?> c = Class.forName(className);
		Object obj = c.getDeclaredConstructor(null).newInstance(null);
		
		Method method = c.getMethod(methodName, null);
		return (String) method.invoke(obj, null);
	}
	
	public String getResultStringByActionProxy(Action action,List<Interceptor> interceptors) {
		if(action == null)return null;
		//初始化获得要执行的拦截器列表
		action.searchInterceptors(interceptors);
		
		//要被代理的对象，实际上只是实现了一个什么也不做的接口
		//所有要做的内容都在代理中实现了，包括获得结果的方法，在代理中也已经使用反射实现
		ExecutHelperImp executer = new ExecutHelperImp();
		ProxyHandler handler = new ProxyHandler(executer);
		ExecutHelper imp = (ExecutHelper) Proxy.newProxyInstance(ExecutHelperImp.class.getClassLoader(), executer.getClass().getInterfaces(), handler);
		return imp.doNothing(action);
	}
	
	
	public Result searchResult(Action action,String resultString) {
		if(action == null || resultString == null) {
			return null;
		}
		List<Result> results = action.getResultNodes();
		for(Result result:results) {
			if(result.getName().equals(resultString))return result;
		}
		
		//没有找到
		isResult = false;
		return null;
	}
	
	/**
	 *	根据结果来跳转
	 * @param result
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doWithResult(Result result,HttpServletRequest req, HttpServletResponse resp,ServletContext context) throws ServletException, IOException {
		if(result == null)return;
		
		InputStream is = context.getResourceAsStream(result.getValue());
		
		if (result.getType().equals(TYPE_FORWARD)) {
			// 使用forward方式
			req.getRequestDispatcher(result.getValue()).forward(req, resp);
		} else {
			// 使用redirect方式
			resp.sendRedirect(result.getValue());
		}
	}
	
	/**
	 *	失败的处理
	 * @param resp
	 * @throws IOException
	 */
	public void doWithFailed(HttpServletResponse resp) throws IOException {
		if (isAction == false || isResult == false) {
			String message = (isAction == false ? "Can't find the request of action!"
					: "No resource of request(result)!");
			PrintWriter out = resp.getWriter();
			out.write("<html><head><title>COS</title></head><body>" + message + "</body></html>");
		}
	}
	
}
