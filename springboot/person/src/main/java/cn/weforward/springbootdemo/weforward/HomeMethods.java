package cn.weforward.springbootdemo.weforward;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.weforward.common.ResultPage;
import cn.weforward.common.util.TransResultPage;
import cn.weforward.framework.WeforwardMethod;
import cn.weforward.framework.WeforwardMethods;
import cn.weforward.protocol.doc.annotation.DocMethod;
import cn.weforward.springbootdemo.command.PersonCmdExe;
import cn.weforward.springbootdemo.dto.PersonNameCountQuery;
import cn.weforward.springbootdemo.dto.PersonNameCountResult;
import cn.weforward.springbootdemo.person.Person;
import cn.weforward.springbootdemo.person.PersonService;
import cn.weforward.springbootdemo.weforward.param.CreatePersonParam;
import cn.weforward.springbootdemo.weforward.param.SearchPersonParam;
import cn.weforward.springbootdemo.weforward.view.PersonView;

@WeforwardMethods
public class HomeMethods {

	@Autowired
	protected PersonService m_PersonService;

	@Autowired
	protected PersonCmdExe m_PersonCmdExe;

	@DocMethod(description = "创建", index = 1)
	@WeforwardMethod
	public PersonView create(CreatePersonParam param) {
		return PersonView.valueOf(m_PersonService.create(param.getName()));
	}

	@DocMethod(description = "尝试创建(测试事务，50%成功率)", index = 2)
	@WeforwardMethod
	public PersonView tryCreate(CreatePersonParam param) {
		return PersonView.valueOf(m_PersonService.tryCreate(param.getName()));
	}

	@DocMethod(description = "搜索", index = 3)
	@WeforwardMethod
	public ResultPage<PersonView> search(SearchPersonParam param) {
		ResultPage<? extends Person> rp = m_PersonService.search(param.getName());
		return TransResultPage.valueOf(rp, PersonView::valueOf);
	}

	@DocMethod(description = "名称统计", index = 4)
	@WeforwardMethod
	public List<PersonNameCountResult> nameCount(PersonNameCountQuery param) {
		return m_PersonCmdExe.execute(param);
	}
}
