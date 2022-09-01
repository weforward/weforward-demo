package cloud.weforward.person.command.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cloud.weforward.person.command.PersonCmdExe;
import cloud.weforward.person.dto.PersonNameCountQuery;
import cloud.weforward.person.dto.PersonNameCountResult;
import cloud.weforward.person.mapper.OnePersonMapper;

@Component
public class PersonCmdExeImpl implements PersonCmdExe {
	@Autowired
	protected OnePersonMapper onePersonMapper;

	@Override
	public List<PersonNameCountResult> execute(PersonNameCountQuery cmd) {
		return onePersonMapper.nameCount(cmd);
	}
}
