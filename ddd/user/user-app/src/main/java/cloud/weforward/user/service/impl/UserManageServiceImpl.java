package cloud.weforward.user.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cloud.weforward.user.domain.Sex;
import cloud.weforward.user.domain.User;
import cloud.weforward.user.domain.store.UserRepository;
import cloud.weforward.user.dto.param.UserAddParam;
import cloud.weforward.user.dto.view.UserView;
import cloud.weforward.user.service.UserManageService;

/**
 * @author daibo
 * @date 2022/9/2 11:36
 */
@Service
public class UserManageServiceImpl implements UserManageService {

    @Autowired
    protected UserRepository userRepository;

    @Override
    public UserView add(UserAddParam param) {
        User user = new User(null, param.getName(), param.getAge(), param.getMobile(), param.getEmail(), Sex.findById(param.getSex()));
        userRepository.update(user);
        return UserView.valueOf(user);
    }
}
