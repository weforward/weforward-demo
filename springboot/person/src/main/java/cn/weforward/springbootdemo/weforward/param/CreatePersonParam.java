package cn.weforward.springbootdemo.weforward.param;

import cn.weforward.protocol.doc.annotation.DocAttribute;

public class CreatePersonParam {
	@DocAttribute(description = "名称")
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
