package cloud.weforward.user.convertor;

import org.springframework.beans.BeanUtils;

import cloud.weforward.user.domain.Sex;
import cloud.weforward.user.entity.User;

/**
 * @author daibo
 * @date 2022/9/2 15:06
 */
public class UserConvertor {


    public static User toDataObject(cloud.weforward.user.domain.User user) {
        User userDB = new User();
        BeanUtils.copyProperties(user, userDB);
        userDB.setSex(user.getSex().ordinal());
        return userDB;
    }

    public static cloud.weforward.user.domain.User toDomain(User user) {
        cloud.weforward.user.domain.User userDomain = new cloud.weforward.user.domain.User();
        BeanUtils.copyProperties(userDomain, user);
        userDomain.setSex(Sex.findById(user.getSex()));
        return userDomain;
    }

}
