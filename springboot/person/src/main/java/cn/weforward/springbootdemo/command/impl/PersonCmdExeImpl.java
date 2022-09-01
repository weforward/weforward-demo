package cn.weforward.springbootdemo.command.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.weforward.springbootdemo.command.PersonCmdExe;
import cn.weforward.springbootdemo.dto.PersonNameCountQuery;
import cn.weforward.springbootdemo.dto.PersonNameCountResult;
import cn.weforward.springbootdemo.mapper.OnePersonMapper;

@Component
public class PersonCmdExeImpl implements PersonCmdExe {
	@Autowired
	protected OnePersonMapper onePersonMapper;

	@Override
	public List<PersonNameCountResult> execute(PersonNameCountQuery cmd) {
		return onePersonMapper.nameCount(cmd);
	}
}
