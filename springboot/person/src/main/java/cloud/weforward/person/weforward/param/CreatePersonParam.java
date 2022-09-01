package cloud.weforward.person.weforward.param;

import javax.validation.constraints.NotEmpty;

import cn.weforward.protocol.doc.annotation.DocAttribute;

public class CreatePersonParam {
	@DocAttribute(description = "名称")
	@NotEmpty
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
