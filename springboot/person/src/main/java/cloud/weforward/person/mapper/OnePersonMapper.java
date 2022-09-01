package cloud.weforward.person.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cloud.weforward.person.dto.PersonNameCountQuery;
import cloud.weforward.person.dto.PersonNameCountResult;
import cloud.weforward.person.entity.OnePerson;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author daibo
 * @since 2022-08-31 11:49:57
 */
public interface OnePersonMapper extends BaseMapper<OnePerson> {

	List<PersonNameCountResult> nameCount(PersonNameCountQuery cmd);

}
