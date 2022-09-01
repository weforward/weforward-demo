package cn.weforward.product.weforward;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import cn.weforward.framework.doc.DocObjectProvider;
import cn.weforward.protocol.datatype.DataType;
import cn.weforward.protocol.doc.DocAttribute;
import cn.weforward.protocol.doc.DocObject;
import cn.weforward.protocol.support.doc.DocAttributeVo;

@Component
public class GlobalDocObject implements DocObjectProvider, DocObject {

	@Override
	public DocObject get() {
		return this;
	}

	@Override
	public String getName() {
		return "_global";
	}

	@Override
	public String getDescription() {
		return "全局参数（在所有请求里面带上）";
	}

	@Override
	public List<DocAttribute> getAttributes() {
		DocAttributeVo vo = new DocAttributeVo();
		vo.name = "merchant";
		vo.description = "商家,固定4个字符";
		vo.type = DataType.STRING.value;
		return Collections.singletonList(vo);
	}

}
