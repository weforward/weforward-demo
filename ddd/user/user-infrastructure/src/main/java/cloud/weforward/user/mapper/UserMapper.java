package cloud.weforward.user.mapper;

import cloud.weforward.user.dto.UserNameCountQuery;
import cloud.weforward.user.dto.UserNameCountResult;
import cloud.weforward.user.entity.User;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author daibo
 * @since 2022-09-02 13:42:16
 */
public interface UserMapper extends BaseMapper<User> {

    UserNameCountResult nameCount(UserNameCountQuery query);
}
