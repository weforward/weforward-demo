package cloud.weforward.person.entity;

import cn.weforward.data.mybatisplus.support.MyBatisPlusBusinessVo;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author daibo
 * @since 2022-08-31 11:49:57
 */
@TableName("one_person")
public class OnePerson extends MyBatisPlusBusinessVo {

    private String name;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "OnePerson{" +
            "name=" + name +
            ", age=" + age +
        "}";
    }
}
