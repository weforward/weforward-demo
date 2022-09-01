package cn.weforward.springbootdemo.dto;

import cn.weforward.protocol.doc.annotation.DocAttribute;

public class PersonNameCountResult {
	@DocAttribute(description = "名称")
	protected String name;
	@DocAttribute(description = "数量")
	protected int count;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
