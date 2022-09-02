package cloud.weforward.user.command;

import cloud.weforward.user.dto.UserNameCountQuery;
import cloud.weforward.user.dto.UserNameCountResult;

/**
 * @author daibo
 * @date 2022/9/2 13:43
 */
public interface UserCmdExe {

    UserNameCountResult nameCount(UserNameCountQuery query);

}
