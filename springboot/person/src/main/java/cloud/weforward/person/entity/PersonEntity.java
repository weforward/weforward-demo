package cloud.weforward.person.entity;

import cn.weforward.data.mybatisplus.support.AbstractEntity;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author daibo
 * @since 2022-09-28 20:14:12
 */
@TableName("person")
public class PersonEntity extends AbstractEntity {

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
        return "PersonEntity{" +
            "name=" + name +
            ", age=" + age +
        "}";
    }
}
