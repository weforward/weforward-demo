package cloud.weforward.user.repository.impl;

import org.springframework.stereotype.Component;

import cloud.weforward.user.domain.User;
import cloud.weforward.user.domain.store.UserRepository;
import cloud.weforward.user.mapper.UserMapper;
import cn.weforward.data.persister.PersisterFactory;

/**
 * @author daibo
 * @date 2022/9/2 14:15
 */
@Component
public class UserRepositoryImpl implements UserRepository {

    protected UserMapper userMapper;

    public UserRepositoryImpl(PersisterFactory persisterFactory) {
    }

    @Override
    public void update(User user) {
//        if (null == user.getId()) {
//            user.setId(String.valueOf(System.currentTimeMillis()));
//            userMapper.insert(UserConvertor.toDataObject(user));
//        } else {
//            userMapper.updateById(UserConvertor.toDataObject(user));
//        }
    }

    @Override
    public User get(String id) {
//        cloud.weforward.user.entity.User user = userMapper.selectById(id);
//        return UserConvertor.toDomain(user);
        return null;
    }


}
