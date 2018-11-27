package cn.T.winter.Interface;

import sc.ustc.items.Action;

public interface LogWriteInterface {
	void preAction(Action action);
	void afterAction(Action action);
}
