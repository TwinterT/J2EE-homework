package sc.ustc.utils;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.Map;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import sc.ustc.dao.Conversation;
import sc.ustc.items.JDBCItems.BaseBean;
import sc.ustc.items.JDBCItems.Property;

public class ProxyUserBeanHandler implements MethodInterceptor {

	private Map<String, Property> lazyProperties;

	public ProxyUserBeanHandler(Map<String, Property> map) {
		lazyProperties = map;
	}
	
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("check for lazy loading!!!");
		if(method.getName().startsWith("get") && proxy.invokeSuper(obj, args) == null) {
			//当调用的是get方法并且获得的对象是null时，启动懒加载
			
			String fieldName = method.getName().substring(3);
			fieldName = fieldName.substring(0, 1).toLowerCase()+fieldName.substring(1);
			
			System.out.println("filedName:"+fieldName);
			Property property = lazyProperties.get(fieldName);
			if(property !=null) {
				//查询获得结果集合
				ResultSet resultSet = Conversation.query((BaseBean) obj);
				
				//查找并且调用set方法
				String setMethodName = "set" +method.getName().substring(3); 
				System.out.println("setMethodName:"+setMethodName);
				Method[] methods = obj.getClass().getDeclaredMethods();
				for(Method proxyMethod:methods) {
					System.out.println("method name is :"+proxyMethod.getName());
					if(proxyMethod.getName().equals(setMethodName)) {
						proxyMethod.invoke(obj, resultSet.getString(property.getColumn()));
					}
				} 
				
			}
		}

		//返回方法的执行结果
		return proxy.invokeSuper(obj, args);
	}
}
