package cloud.weforward.user.entity;

import cn.weforward.data.mybatisplus.support.AbstractEntity;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author daibo
 * @since 2022-09-28 20:26:56
 */
@TableName("user")
public class UserEntity extends AbstractEntity {

    private String name;

    private String mobile;

    private Integer age;

    private String email;

    private Long marks;

    private Integer sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Long getMarks() {
        return marks;
    }

    public void setMarks(Long marks) {
        this.marks = marks;
    }
    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
            "name=" + name +
            ", mobile=" + mobile +
            ", age=" + age +
            ", email=" + email +
            ", marks=" + marks +
            ", sex=" + sex +
        "}";
    }
}
