package sc.ustc.items;

import java.lang.reflect.Method;

/**
 * 	用于暂存proxy中stack的元素，包含一个拦截器实例和一个after方法
 * @author T_winter
 *
 */
public class ProxyStackElement {

	private Object interceptorInstance;
	private Method afterMethod;

	public ProxyStackElement(Object interceptorInstance, Method afterMethod) {
		this.interceptorInstance = interceptorInstance;
		this.afterMethod = afterMethod;
	}
	
	public Method getAfterMethod() {
		return afterMethod;
	}
	
	public Object getInterceptorInstance() {
		return interceptorInstance;
	}
	
}
