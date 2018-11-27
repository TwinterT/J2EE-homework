package sc.ustc.utils;

import cn.T.winter.Interface.ExecutHelper;
import sc.ustc.items.Action;

public class ExecutHelperImp implements ExecutHelper{

	@Override
	public String doNothing(Action action) {
		
		System.out.println("I do nothing");
		
		return "I do nothing";
	}

}
