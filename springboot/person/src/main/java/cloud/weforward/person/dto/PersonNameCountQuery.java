package cloud.weforward.person.dto;

import cn.weforward.protocol.doc.annotation.DocAttribute;

public class PersonNameCountQuery {
	@DocAttribute(description = "名称")
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
