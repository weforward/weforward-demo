package cloud.weforward.user.command.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cloud.weforward.user.command.UserCmdExe;
import cloud.weforward.user.dto.UserNameCountQuery;
import cloud.weforward.user.dto.UserNameCountResult;
import cloud.weforward.user.mapper.UserMapper;

/**
 * @author daibo
 * @date 2022/9/2 13:44
 */
@Service
public class UserCmdExeImpl implements UserCmdExe {

    @Autowired
    protected UserMapper userMapper;
    @Override
    public UserNameCountResult nameCount(UserNameCountQuery query) {
        return userMapper.nameCount(query);
    }
}
