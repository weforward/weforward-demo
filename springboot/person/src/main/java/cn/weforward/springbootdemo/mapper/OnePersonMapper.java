package cn.weforward.springbootdemo.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.weforward.springbootdemo.dto.PersonNameCountQuery;
import cn.weforward.springbootdemo.dto.PersonNameCountResult;
import cn.weforward.springbootdemo.entity.OnePerson;

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
