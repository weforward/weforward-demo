package cloud.weforward.user.mapper;

import cloud.weforward.user.dto.UserNameCountQuery;
import cloud.weforward.user.dto.UserNameCountResult;
import cloud.weforward.user.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author daibo
 * @since 2022-09-28 20:26:56
 */
public interface UserMapper extends BaseMapper<UserEntity> {

    UserNameCountResult nameCount(UserNameCountQuery query);
}
