package cloud.weforward.user.dto;

import cn.weforward.protocol.doc.annotation.DocAttribute;

public class UserNameCountQuery {
	@DocAttribute(description = "名称")
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
