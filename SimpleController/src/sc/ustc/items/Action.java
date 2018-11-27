package sc.ustc.items;

import java.util.ArrayList;
import java.util.List;

public class Action {
	
	public static final String ACTION_TAG = "action";
	
	public static final String NAME = "name";
	public static final String CLASS_NAME = "class";
	public static final String METHOD_NAME = "method";


	private String name;
	private String className;
	private String methodName;
	
	private String result;
	
	private List<Result> resultNodes = new ArrayList<>();
	private List<InterceptroREF> interceptroRefNodes = new ArrayList<>();
	
	private List<Interceptor> interceptors;
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getMethodName() {
		return methodName;
	}
	
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public List<Result> getResultNodes() {
		return resultNodes;
	}
	
	public void setResultNodes(List<Result> resultNodes) {
		this.resultNodes = resultNodes;
	}
	
	public List<InterceptroREF> getInterceptroRefNodes() {
		return interceptroRefNodes;
	}
	
	public void setInterceptroRefNodes(List<InterceptroREF> interceptroRefNodes) {
		this.interceptroRefNodes = interceptroRefNodes;
	}
	
	public List<Interceptor> getInterceptors() {
		return interceptors;
	}

	
	/**
	 * 	在一个Interceptor的列表中按顺序找出action中定义的拦截器
	 * @param list
	 * @return
	 */
	public void searchInterceptors(List<Interceptor> list){
		interceptors = new ArrayList<>();
		for(InterceptroREF interceptroREF:interceptroRefNodes) {
			for(Interceptor interceptor:list) {
				if(interceptor.getName().equals(interceptroREF.getName())) {
					interceptors.add(interceptor);
				}
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("name: "+name);
		s.append(" class: "+className);
		s.append(" methodName:"+methodName+"\n");
		for(Result result:resultNodes) {
			s.append("result: "+result.toString());
		}
		s.append("\n");
		for(InterceptroREF interceptorREF:interceptroRefNodes) {
			s.append(" interceptor: "+interceptorREF.toString());
		}
		return s.toString();
	}

}
