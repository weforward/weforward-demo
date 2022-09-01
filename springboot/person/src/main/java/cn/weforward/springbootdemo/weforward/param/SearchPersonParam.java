package cn.weforward.springbootdemo.weforward.param;

import cn.weforward.framework.doc.DocPageParams;
import cn.weforward.protocol.doc.annotation.DocAttribute;

public class SearchPersonParam extends DocPageParams {

	@DocAttribute(description = "名称")
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
