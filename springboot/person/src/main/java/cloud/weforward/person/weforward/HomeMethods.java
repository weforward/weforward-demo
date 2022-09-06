package cloud.weforward.person.weforward;

import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import cloud.weforward.person.command.PersonCmdExe;
import cloud.weforward.person.dto.PersonNameCountQuery;
import cloud.weforward.person.dto.PersonNameCountResult;
import cloud.weforward.person.domain.Person;
import cloud.weforward.person.domain.PersonService;
import cloud.weforward.person.weforward.param.CreatePersonParam;
import cloud.weforward.person.weforward.param.SearchPersonParam;
import cloud.weforward.person.weforward.view.PersonView;
import cn.weforward.common.ResultPage;
import cn.weforward.common.util.TransResultPage;
import cn.weforward.framework.ApiException;
import cn.weforward.framework.ExceptionHandler;
import cn.weforward.framework.WeforwardMethod;
import cn.weforward.framework.WeforwardMethods;
import cn.weforward.protocol.doc.annotation.DocMethod;

@Validated
@WeforwardMethods
public class HomeMethods implements ExceptionHandler {

	@Autowired
	protected PersonService m_PersonService;

	@Autowired
	protected PersonCmdExe m_PersonCmdExe;

	@DocMethod(description = "创建", index = 1)
	@WeforwardMethod
	public PersonView create(@Valid CreatePersonParam param) {
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

	@Override
	public Throwable exception(Throwable error) {
		if (error instanceof ConstraintViolationException) {
			ConstraintViolationException constraintViolationException = (ConstraintViolationException) error;
			StringBuilder sb = new StringBuilder();
			for (ConstraintViolation<?> e : constraintViolationException.getConstraintViolations()) {
				if (sb.length() > 0) {
					sb.append(" ");
				}
				String template = e.getMessageTemplate();
				if (template.charAt(0) == '{' && template.charAt(template.length() - 1) == '}') {
					Path.Node lastNode = null;
					for (Path.Node node : e.getPropertyPath()) {
						lastNode = node;
					}
					if (null == lastNode) {
						sb.append(e.getMessage());
					} else {
						sb.append(lastNode.getName());
						sb.append(e.getMessage());
					}
				} else {
					sb.append(e.getMessage());
				}
			}
			return new ApiException(ApiException.CODE_ILLEGAL_ARGUMENT, sb.toString());
		}
		return error;
	}
}
