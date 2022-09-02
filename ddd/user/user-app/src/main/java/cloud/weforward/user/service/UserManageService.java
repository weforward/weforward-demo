package cloud.weforward.user.service;

import cloud.weforward.user.dto.param.UserAddParam;
import cloud.weforward.user.dto.view.UserView;

/**
 * @author daibo
 * @date 2022/9/2 11:43
 */
public interface UserManageService {


    UserView add(UserAddParam param);
}
