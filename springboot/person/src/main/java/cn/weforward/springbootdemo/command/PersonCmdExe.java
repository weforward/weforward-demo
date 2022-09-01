package cn.weforward.springbootdemo.command;

import java.util.List;

import cn.weforward.springbootdemo.dto.PersonNameCountQuery;
import cn.weforward.springbootdemo.dto.PersonNameCountResult;


/**
 * 命令执行器 CQRS模式
 * 
 * @author daibo
 *
 */
public interface PersonCmdExe {

	List<PersonNameCountResult> execute(PersonNameCountQuery cmd);
}
