package sc.ustc.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Stack;

import sc.ustc.items.Action;
import sc.ustc.items.Interceptor;
import sc.ustc.items.ProxyStackElement;

public class ProxyHandler implements InvocationHandler {
	
	Object target;
	
	public ProxyHandler(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		//接口的第一个参数为Action
		Action action = (Action) args[0];
		
		//获得需要执行的拦截器
		List<Interceptor> list = action.getInterceptors();
		
		//拦截器存在stack中用于执行后面的after方法
		Stack<ProxyStackElement> stack = new Stack<>();
		
		
		//执行pre方法
		for(Interceptor interceptor:list) {
			Class<?> interceptorClass = Class.forName(interceptor.getClassName());
			Object interceptorInstance = interceptorClass.newInstance();
			Method pre = interceptorClass.getMethod(interceptor.getPreMethodName(), Action.class);
			Method after = interceptorClass.getMethod(interceptor.getAfterMethodName(), Action.class);
			pre.invoke(interceptorInstance, action);
			
			//暂存after方法
			stack.push(new ProxyStackElement(interceptorInstance,after));
		}
		
		//执行action方法
		// 获得类名和方法名
		String className = action.getClassName();
		String methodName = action.getMethodName();

		// 利用反射创建类对象
		Class<?> actionClass = Class.forName(className);
		Method actionMethod = actionClass.getMethod(methodName, null);
		Object object = actionClass.getDeclaredConstructor(null).newInstance(null);
		
		//在反射类中注入action中的bean对象
		Method setMethod = actionClass.getMethod("setUserBean",Class.forName("water.ustc.dao.UserBean"));
		setMethod.invoke(object, action.getUserBean());
		
		//获得结果
		String result = (String)actionMethod.invoke(object);
		action.setResult(result);
		System.out.println(result);
		//执行after方法
		while(!stack.isEmpty()) {
			ProxyStackElement element = stack.pop();
			element.getAfterMethod().invoke(element.getInterceptorInstance(),action);
		}
		
		return result;
	}

}
