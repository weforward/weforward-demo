package cn.weforward.product.weforward.view;

import cn.weforward.framework.doc.DocPageParams;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

@DocObject(description = "搜索参数")
public class SearchProductParam extends DocPageParams {

	protected String m_Keywords;

	protected int m_Option;

	public void setKeywords(String ks) {
		m_Keywords = ks;
	}

	@DocAttribute(description = "搜索关键字")
	public String getKeywords() {
		return m_Keywords;
	}

	public void setOption(int option) {
		m_Option = option;
	}

	@DocAttribute(description = "选项  0=全部 ,1=上架中,2=下架中", example = "0")
	public int getOption() {
		return m_Option;
	}

}
