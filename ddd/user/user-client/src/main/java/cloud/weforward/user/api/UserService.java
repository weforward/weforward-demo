package cloud.weforward.user.api;

import cloud.weforward.user.dto.UserNameCountQuery;
import cloud.weforward.user.dto.UserNameCountResult;

/**
 * @author daibo
 * @date 2022/9/2 11:35
 */
public interface UserService {

    UserNameCountResult nameCount(UserNameCountQuery query);
}
