package cloud.weforward.person.mapper;

import java.util.List;

import cloud.weforward.person.dto.PersonNameCountQuery;
import cloud.weforward.person.dto.PersonNameCountResult;
import cloud.weforward.person.entity.PersonEntity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author daibo
 * @since 2022-09-28 20:14:12
 */
public interface PersonMapper extends BaseMapper<PersonEntity> {

    List<PersonNameCountResult> nameCount(PersonNameCountQuery query);
}
