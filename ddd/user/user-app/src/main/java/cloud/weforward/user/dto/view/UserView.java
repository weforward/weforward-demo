package cloud.weforward.user.dto.view;

import cloud.weforward.user.domain.User;

/**
 * @author daibo
 * @date 2022/9/2 14:07
 */
public class UserView {

    protected User user;

    public UserView(User user) {
        this.user = user;
    }

    public static UserView valueOf(User user) {
        return null == user ? null : new UserView(user);
    }
}
