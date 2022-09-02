package cloud.weforward.user.domain.store;

import cloud.weforward.user.domain.User;

/**
 * @author daibo
 * @date 2022/9/2 14:13
 */
public interface UserRepository {

    void update(User user);

    User get(String id);
}
