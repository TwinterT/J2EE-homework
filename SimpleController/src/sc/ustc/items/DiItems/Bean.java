package sc.ustc.items.DiItems;

import java.util.List;

public class Bean {

	private String name;
	private String className;
	private Field field;
	
	public void setField(Field field) {
		this.field = field;
	}
	public Field getField() {
		return field;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassName() {
		return className;
	}
	
}
