package cloud.weforward.person.command;

import java.util.List;

import cloud.weforward.person.dto.PersonNameCountQuery;
import cloud.weforward.person.dto.PersonNameCountResult;


/**
 * 命令执行器 CQRS模式
 * 
 * @author daibo
 *
 */
public interface PersonCmdExe {

	List<PersonNameCountResult> execute(PersonNameCountQuery cmd);
}
